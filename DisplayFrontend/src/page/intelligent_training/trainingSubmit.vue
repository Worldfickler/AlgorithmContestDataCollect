<template>
<el-card shadow="never" style="height: 80px">
    <el-row justify="space-around" align="middle" style="height: 100%">
      <el-col :span="4" class="center-content">
        比赛名称: {{ contestInfo.name }}
      </el-col>
      <el-col :span="4" class="center-content">
        开始时间:
        {{ simpleUtils.timeStampToLocalTime(contestInfo.startTimestamp) }}
      </el-col>
      <el-col :span="4" class="center-content">
        结束时间:
        {{ simpleUtils.timeStampToLocalTime(contestInfo.endTimestamp) }}
      </el-col>
    </el-row>
  </el-card>
  <el-table :data="pageData" border @sort-change="sortChange" >
    <el-table-column prop="username" label="用户名" align="center">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="usernameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="classname" label="班级" align="center">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="classnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="realname" label="姓名" align="center">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="realnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="name" label="题目编号" align="center">
      <template #default="scope">
        <a
          :href="`https://codeforces.com/contest/${scope.row.cid}/problem/${scope.row.qindex}`"
          target="_blank"
          >{{ scope.row.problemId }}</a
        >
      </template>
    </el-table-column>
    <!-- <el-table-column label="题目分数" align="center" sortable>
      <template #default="scope">
        {{ scope.row.difficulty }}
      </template>
    </el-table-column> -->
    <el-table-column prop="difficulty" label="题目分数" sortable align="center">
    </el-table-column>
    <!-- <el-table-column label="分类" align="center">
      <template #default="scope">
        {{ scope.row.tagNames }}
      </template>
    </el-table-column> -->
    <el-table-column label="分类" align="center">
      <template #default="scope">
        <el-tag
          v-for="tag in String(scope.row.tagNames).split(',')"
          :key="tag"
          type="info"
          :closable="false"
          >{{ tag }}</el-tag
        >
      </template>
    </el-table-column>
    <el-table-column label="结果" align="center">
      <template #default="scope">
        {{ scope.row.status }}
      </template>
    </el-table-column>
    <el-table-column label="语言" align="center">
      <template #default="scope">
        {{ scope.row.language }}
      </template>
    </el-table-column>
    <el-table-column prop="submitTime" label="提交时间" sortable align="center">
      <template #default="scope">
        {{ simpleUtils.timeStampToLocalTime(scope.row.submitTime * 1000) }}
      </template>
    </el-table-column>
    <el-table-column label="查看代码" align="center">
      <template #default="scope">
        <el-button @click="lookupCode(scope.row.sid)">查看代码</el-button>
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="dspData.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
  <CodeDisplay :code-data="codeData" ref="codeDisplay"></CodeDisplay>
</template>

<script setup>
import { computed, onMounted, ref } from "vue";
import axiosUtil from "../../utils/axiosUtil";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import simpleUtils from "../../utils/simpleUtils";
import CodeDisplay from "../../../src/components/ContestComponent/Table/CodeDisplay.vue";
let route = useRoute();
let strategyInfo = ref({});
let contestInfo = ref({});
let submitInfo = ref([]);
let solveProblemInfo = ref({});
let totalQuestion = ref(0);
let codeData = ref("");
let codeDisplay = ref(null);
let totalIdx = computed(() => {
  let ret = [];
  let useTwo = false;
  if (totalQuestion.value > 26) {
    useTwo = true;
  }
  for (let i = 0; i < totalQuestion.value; i++) {
    let first = String.fromCharCode(65 + i / 26);
    let sec = String.fromCharCode(65 + (i % 26));
    if (useTwo) {
      ret.push(first + sec);
    } else {
      ret.push(sec);
    }
  }
  return ret;
});
let lookupCode = (sid) => {
  axiosUtil.codeforcesAPI.getCodeforcesSubmitCode(sid).then((res) => {
    if (res.data.code === 200) {
      codeData.value = res.data.result.code;
      codeDisplay.value.open();
    } else {
      ElMessage.error(res.data.msg);
    }
  });
};
const currentPage = ref(1);
let pageSize = ref(20);
let filter = ref({});
const classnameFilter = ref('');
const realnameFilter = ref('');
const usernameFilter = ref('');
const sortProp = ref();
let sortOrder = ref('descending');
const sortChange = (prop) => {
  console.log(prop)
  sortProp.value = prop.prop
  sortOrder.value = prop.order
};
const pageData = computed(()=>{
   return dspData.value.slice((currentPage.value-1)*pageSize.value, currentPage.value*pageSize.value)
})
const dspData = computed(()=>{
  var retData = submitInfo.value;
  console.log(sortProp,sortOrder);
  retData.sort((a,b) => {
    if (sortOrder.value === 'descending') {
      return b[sortProp.value] - a[sortProp.value]
    } else {
      return a[sortProp.value] - b[sortProp.value]
    }
  })
  retData = retData.filter((item) => {
    if (usernameFilter.value !== '' && !item.username.includes(usernameFilter.value)) {
      return false
    }
    if (realnameFilter.value !== '' && !item.realname.includes(realnameFilter.value)) {
      return false
    }
    if (classnameFilter.value !== '' && !item.classname.includes(classnameFilter.value)) {
      return false
    }
    let allowRet = true
    for (let key in filter.value) {
      if (filter.value[key].length > 0 && !filter.value[key].includes(item[key])) {
        allowRet = false
      }
    }
    return allowRet
  })
  return retData;
})
onMounted(() => {
  if (route.params.id === undefined) {
    route.push("/intelligent_training/list");
    ElMessage.error("参数错误");
    return;
  }
  axiosUtil.intelligentAPI
    .getIntelligentTrainContestSubmitInfo(route.params.id)
    .then((res) => {
      if (res.data.code === 200) {
        contestInfo.value = res.data.result.contestInfo;
        for(var i=0;i<res.data.result.submitInfo.length;i++){
          const matches = res.data.result.submitInfo[i].problemId.match(/^(\d+)(.*)$/);
          res.data.result.submitInfo[i].cid=matches[1];
          res.data.result.submitInfo[i].qindex=matches[2];
        }
        submitInfo.value = res.data.result.submitInfo;
        console.log(submitInfo.value);
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      ElMessage.error("网络异常");
      console.log(err);
    });
});
let judgeSolve = (row, i) => {
  let question = row.problems[i];
  // console.log(row,i);
  // console.log(question, solveProblemInfo.value[row.uid]);
  if (!solveProblemInfo.value.hasOwnProperty(row.uid)) {
    return false;
  }
  let ls = solveProblemInfo.value[row.uid].split(",");
  return ls.indexOf(question) !== -1;
};

</script>

<style scoped>
.el-card /deep/ .el-card__body {
  height: 50%;
}
</style>