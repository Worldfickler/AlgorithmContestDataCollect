<template>
  <el-table :data="pageData" border @sort-change="sortChange" @filter-change="filterChange" :header-cell-style="{'text-align':'center'}" >
    <el-table-column fixed prop="name" label="比赛名" align="center" >
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="nameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column fixed label="开始时间" align="center" sortable="custom" prop="startTimestamp" >
      <template #default="scope">
        {{ simpleUtils.timeStampToLocalTime(scope.row.startTimestamp) }}
      </template>
    </el-table-column>
    <el-table-column fixed label="结束时间" align="center" sortable="custom" prop="endTimestamp" >
      <template #default="scope">
        {{ simpleUtils.timeStampToLocalTime(scope.row.endTimestamp) }}
      </template>
    </el-table-column>
    <el-table-column fixed label="总人数" align="center" sortable="custom" prop="PNum" ></el-table-column>
    <el-table-column fixed label="累计解题数量" align="center" sortable="custom" prop="SNum" ></el-table-column>
    <el-table-column fixed label="累计题目分数" align="center" sortable="custom" prop="SRating" ></el-table-column>
    <el-table-column fixed label="比赛状态"  align="center" :filters="statusFilter"  column-key="status" width="300px">
      <template #default="scope" >
        <el-button @click="lookContestRank(scope.row.id)">榜单</el-button>
        <el-button @click="lookSubmit(scope.row.id)">记录</el-button>
        <el-tooltip
          class="item"
          effect="dark"
          :content="scope.row.msg"
          placement="right"
          :disabled="!scope.row.disable"
          v-if="!hasJoins.has(scope.row.id)"
        >
          <span>
            <el-button
              type="danger"
              @click="join(scope.row.id)"
              :disabled="scope.row.disable"
              v-loading="loading[scope.row.id]"
              >加入</el-button
            >
          </span>
        </el-tooltip>
        <el-button
          type="info"
          @click="queryProblem(scope.row.id)"
          v-else
          :disabled="!isLogin"
          >题目</el-button
        >
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="dspData.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import simpleUtils from "../../utils/simpleUtils";
import { computed, onMounted, ref } from "vue";
import axiosUtil from "../../utils/axiosUtil";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import infoUtils from "../../utils/InfoUtils";
const statusRule=ref([{"tag":"success","msg":"正在进行"},{"tag":"info","msg":"已过时"}])
const nameFilter = ref('')
let filter = ref({})
let loading = ref([]);
let router = useRouter();
let data = ref([]);
let currentPage = ref(1);
let pageSize = ref(20);
let isLogin = ref(false);
let sortProp = ref("startTimestamp")
let order = ref("descending")
const dspData = computed(()=>{
  var retData = data.value

  retData.sort((a,b) => {
    if (order.value === 'descending') {
      return b[sortProp.value] - a[sortProp.value]
    } else {
      return a[sortProp.value] - b[sortProp.value]
    }
  })
  retData = retData.filter((item) => {
    if (nameFilter.value !== '' && !item.name.includes(nameFilter.value)) {
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
let hasJoins = ref(new Set());
const pageData = computed(()=>{
   return dspData.value.slice((currentPage.value-1)*pageSize.value, currentPage.value*pageSize.value)
})
let sortChange = (info)=>{
  sortProp.value = info.prop
  order.value = info.order
}
let statusFilter = computed(() => {
  let res = [];
  for (let key in statusRule.value) {
    res.push({
      text: statusRule.value[key].msg,
      value: Number.parseInt(key),
    });
  }
  // console.log(res)
  return res;
});
onMounted(() => {
  axiosUtil.intelligentAPI
    .getContestList()
    .then((res) => {
      if (res.data.code === 200) {
        let now = new Date().getTime();
        for (var i = 0; i < res.data.result.length; i++) {
          var item=res.data.result[i];
          if (item.startTimestamp > now || item.endTimestamp < now) {
            item.disable = true;
            item.msg = "比赛时间不符";
            item.status=1;
          }else{
            item.status=0;
          }
          if (!isLogin.value) {
            item.disable = true;
            item.msg = "尚未登陆";
          }
        }
        
        data.value = res.data.result;
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      ElMessage.error("网络异常");
    });
  if (infoUtils.isLogin()) {
    isLogin.value = true;
    axiosUtil.intelligentAPI
      .getUserQuestionList(infoUtils.getUserId())
      .then((res) => {
        if (res.data.code === 200) {
          res.data.result.forEach((item) => {
            hasJoins.value.add(item.tid);
          });
        }
      });
  }
});
let lookContestRank = (id) => {
  router.push(`/intelligent_training/contest/rank/${id}`);
};
let lookSubmit = (id) => {
  router.push(`/intelligent_training/contest/submit/${id}`);
};
let join = (id) => {
  loading.value[id] = true;
  axiosUtil.intelligentAPI
    .joinTraining(id)
    .then((res) => {
      loading.value[id] = false;
      if (res.data.code === 200) {
        ElMessage.success("加入成功");
        hasJoins.value.add(id);
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      loading.value[id] = false;
      ElMessage.error("网络异常");
    });
};
let queryProblem = (id) => {
  router.push(`/intelligent_training/contest/${id}/problem`);
};
const filterChange = (prop) => {
  filter.value = {...filter.value,...prop}
}
</script>

<style scoped>
span.el-tooltip__trigger {
  margin-left: 12px;
}
</style>