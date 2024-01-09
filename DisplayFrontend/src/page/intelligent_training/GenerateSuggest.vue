<template>
  <el-card shadow="never">
    <el-row>
      <el-col :span="6">
        <el-select v-model="funcSelect" placeholder="标签选择">
          <el-option
              v-for="(item,idx) in funcList"
              :key="idx"
              :label="item.name"
              :value="idx"
          />
        </el-select>
      </el-col>
      <el-col :span="6">
        生成数量:
        <el-input-number :min="1" :max="8" v-model="genNum"></el-input-number>
      </el-col>
      <el-col :span="3">
        <el-button type="primary" @click="query" v-loading="loading">查询</el-button>
      </el-col>
    </el-row>
  </el-card>
  <el-divider></el-divider>

  <el-table :data="problemList" >
    <el-table-column prop="cid" label="比赛id" align="center">
    </el-table-column>
    <el-table-column prop="qindex" label="问题编号" align="center">
    </el-table-column>
    <el-table-column prop="name" label="题目名称" align="center">
      <template #default="scope">
        <a :href="`https://codeforces.com/contest/${scope.row.cid}/problem/${scope.row.qindex}`" target="_blank" >{{scope.row.name}}</a>
      </template>
    </el-table-column>
    <el-table-column prop="difficulty" label="题目难度" align="center">
    </el-table-column>
    <el-table-column prop="tags" label="题目标签" align="center">
      <template #default="scope">
        <el-tag v-for="tag in String(scope.row.tags).split(',')" :key="tag" type="info" :closable="false">{{tag}}</el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>

import {computed, onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import infoUtils from "../../utils/InfoUtils";
import {useRoute, useRouter} from "vue-router";
let loading = ref(false)
let router = useRouter()
let funcSelect = ref(null)
let funcList = ref([])
let problemList = ref([])
let genNum = ref(1)
onMounted(()=>{
  if(!infoUtils.isLogin()) {
    ElMessage.error("请先登录")
    router.push("/")
  }
  axiosUtil.intelligentAPI.getFuncs().then(res=>{
    if(res.data.code === 200){
      funcList.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
})
let query = () =>{
  console.log("hello")
  if(funcSelect.value === null){
    ElMessage.error("请选择标签")
    return
  }
  axiosUtil.intelligentAPI.useFunc(funcList.value[funcSelect.value].name,genNum.value).then(res=>{
    if(res.data.code === 200){
      problemList.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
}
</script>

<style scoped>

</style>