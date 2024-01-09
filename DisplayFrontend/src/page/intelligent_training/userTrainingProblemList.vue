<template>
  <el-table :data="dspData" >
    <el-table-column prop="cid" label="比赛id" align="center">
    </el-table-column>
    <el-table-column prop="qindex" label="问题编号" align="center">
    </el-table-column>
    <el-table-column prop="name" label="题目名称" align="center">
      <template #default="scope">
        <a :href="`https://codeforces.com/contest/${scope.row.cid}/problem/${scope.row.qindex}`" target="_blank" @click="handleLinkClick(scope.row)">{{scope.row.name}}</a>
      </template>
    </el-table-column>
    <el-table-column prop="difficulty" label="题目难度" align="center">
    </el-table-column>
    <el-table-column prop="tags" label="题目标签" align="center">
      <template #default="scope" >
        <div v-if="scope.row.tags">
          <el-tag  v-for="tag in String(scope.row.tags).split(',')" :key="tag" type="info" :closable="false">{{tag}}</el-tag>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="generateName" label="生成规则" align="center" show-overflow-tooltip>
      <template #default="scope">
        <span>{{ generateRule[scope.row.generateName] }}</span>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import infoUtils from "../../utils/InfoUtils";
import {ElMessage} from "element-plus";
import {useRoute, useRouter} from "vue-router";
let router = useRouter();
let route = useRoute();
let dspData = ref([]);
let generateRule=ref({
  "RatingBasedRandomGenerateFunc":"根据分数随机推荐题目：假设队员是1800分，那么随机选择高于1800分，低于2200分的没有做过的题目推荐",
  "LessTagSolveBasedGenerateFunc":"推荐补充专题：记录该队员的所有专题，推荐一些该队员还没有做过的专题的题目，从分数低到分数高推荐",
  "HardTagBasedGenerateFunc":"根据专题推荐：记录该队员在CF比赛某专题解题的最高rating，假设是1600分，那么继续推荐该专题分数高于该rating的题目，相同专题的更难的题目",
  "CFContestBaseGenerateFunc":"根据CF比赛推荐：例如上次cf div3做出来4题，那么可以推荐5道没做过的cf div3的E题，难度上一点，不用管专题类别，随机选题；上次cf div2做出来3题，那么可以推荐5道没做过的cf div2的D题"
})
onMounted(()=>{
  if(!infoUtils.isLogin()){
    ElMessage.error("请先登录");
    router.push("/index")
    return
  }
  axiosUtil.intelligentAPI.getUserQuestionListDetail(infoUtils.getUserId(),route.params.id).then(res=>{
    if(res.data.code===200){
      dspData.value = res.data.result
      
      console.log(dspData.value)
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
})
let handleLinkClick=(row) =>{
    axiosUtil.intelligentAPI.addQuestionClickTime(row.cid,row.qindex,row.generateName);
}
</script>

<style scoped>

</style>