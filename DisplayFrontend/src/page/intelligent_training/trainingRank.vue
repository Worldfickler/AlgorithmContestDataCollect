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
  <el-table :data="pageData" border @sort-change="sortChange">
    <el-table-column prop="username" label="用户名" align="center">
      <template #header="scope">
        {{ scope.column.label }}
        <el-input v-model="usernameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="classname" label="班级" align="center">
      <template #header="scope">
        {{ scope.column.label }}
        <el-input v-model="classnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="realname" label="姓名" align="center">
      <template #header="scope">
        {{ scope.column.label }}
        <el-input v-model="realnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="solvedNum" label="完成数量" sortable align="center">
    </el-table-column>
    <el-table-column prop="ratingSum" label="分数总和" sortable align="center">
    </el-table-column>
    <el-table-column v-for="(tp, idx) in totalIdx" align="center">
      <template #header="scope">
        {{ tp }}
      </template>
      <template #default="scope">
        <el-tooltip
          class="box-item"
          effect="dark"
          :content="showRating(scope.row, idx)"
          placement="top"
        >
          <el-tag v-if="judgeSolve(scope.row, idx)" type="success">
            已完成
          </el-tag>
          <el-tag v-else type="danger"> 未完成 </el-tag>
        </el-tooltip>
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="dspData.length"
          v-model:page-size="pageSize"
          v-model:current-page="currentPage"
        >
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import simpleUtils from "../../utils/simpleUtils";
import { computed, onMounted, ref } from "vue";
import axiosUtil from "../../utils/axiosUtil";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
let route = useRoute();
let strategyInfo = ref({});
let userInfo = ref([]);
let solveProblemInfo = ref({});
let totalQuestion = ref(0);
let contestInfo = ref([]);
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
const currentPage = ref(1);
let pageSize = ref(20);
let filter = ref({});
let problemMap = ref({});
const classnameFilter = ref("");
const realnameFilter = ref("");
const usernameFilter = ref("");
const sortProp = ref("solvedNum");
let sortOrder = ref("descending");
const sortChange = (prop) => {
  sortProp.value = prop.prop;
  sortOrder.value = prop.order;
};
const pageData = computed(() => {
  return dspData.value.slice(
    (currentPage.value - 1) * pageSize.value,
    currentPage.value * pageSize.value
  );
});
const dspData = computed(() => {
  var retData = userInfo.value;
  console.log(sortProp, sortOrder);
  retData.sort((a, b) => {
    if (sortOrder.value === "descending") {
      return b[sortProp.value] - a[sortProp.value];
    } else {
      return a[sortProp.value] - b[sortProp.value];
    }
  });
  retData = retData.filter((item) => {
    if (
      usernameFilter.value !== "" &&
      !item.username.includes(usernameFilter.value)
    ) {
      return false;
    }
    if (
      realnameFilter.value !== "" &&
      !item.realname.includes(realnameFilter.value)
    ) {
      return false;
    }
    if (
      classnameFilter.value !== "" &&
      !item.classname.includes(classnameFilter.value)
    ) {
      return false;
    }
    let allowRet = true;
    for (let key in filter.value) {
      if (
        filter.value[key].length > 0 &&
        !filter.value[key].includes(item[key])
      ) {
        allowRet = false;
      }
    }
    return allowRet;
  });
  return retData;
});
onMounted(() => {
  if (route.params.id === undefined) {
    route.push("/intelligent_training/list");
    ElMessage.error("参数错误");
    return;
  }
  axiosUtil.intelligentAPI
    .getContestInfo(route.params.id)
    .then((res) => {
      if (res.data.code === 200) {
        contestInfo.value = res.data.result.contestInfo;
        strategyInfo.value = res.data.result.strategyInfo;
        problemMap.value = res.data.result.problemMap;
        strategyInfo.value.useFunction = JSON.parse(
          strategyInfo.value.useFunction
        );
        for (let ques of strategyInfo.value.useFunction) {
          totalQuestion.value += ques.num;
        }
        solveProblemInfo.value = res.data.result.solveProblemInfo;
        for (let user of res.data.result.userInfo) {
          user.problems = JSON.parse(user.problems);
          user.trueProblems = intersection(
            user.problems,
            (solveProblemInfo.value[user.uid]||"").split(',')
          );
          user.solvedNum=user.trueProblems.length;
          user.ratingSum = user.trueProblems.reduce((acc, item) => {
            if (problemMap.value[item]) {
              return acc + problemMap.value[item];
            }
            return acc;
          }, 0);
        }

        userInfo.value = res.data.result.userInfo;
        
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
let showRating = (row, i) => {
  let content = problemMap.value[row.problems[i]] + "";
  return content !== "undefined" ? content : "0";
};
let intersection = (arr1, arr2) => {
  console.log(arr1)
  console.log(arr2)
  return arr1.filter((element) => arr2.includes(element));
};
</script>

<style scoped>
.el-card /deep/ .el-card__body {
  height: 50%;
}
</style>