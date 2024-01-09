<template>
  <el-tabs>
    <el-tab-pane lazy label="codeforces">
      <el-card>
        <template #header="scope">
          <el-switch
              v-model="panel1Switch"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
              active-text="绝对时间折线图"
              inactive-text="相对时间折线图"
          />
        </template>
        <AbsoluteRatingChangeGraph :user-info="userInfo" :contest-data="cfContestInfo" :data-finished="userInfoFinished&&contestFinished" type="Codeforces" v-if="panel1Switch">
        </AbsoluteRatingChangeGraph>
        <RelativeRatingLineGraph :user-info="userInfo" :contest-data="cfContestInfo" :data-finished="userInfoFinished&&contestFinished" type="Codeforces" v-else></RelativeRatingLineGraph>

      </el-card>
      <el-divider></el-divider>
      <el-card>
        <SubmitBarGraph :data-finished="userInfoFinished&&MonthSubmitFinished" :user-info="userInfo" :submit-data="cfMonthSubmit" type="Codeforces"></SubmitBarGraph>
      </el-card>
    </el-tab-pane>
    <el-tab-pane lazy label="atcoder">
      <el-card>
        <template #header="scope">
          <el-switch
              v-model="panel2Switch"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
              active-text="绝对时间折线图"
              inactive-text="相对时间折线图"
          />
        </template>
        <AbsoluteRatingChangeGraph :user-info="userInfo" :contest-data="acContestInfo" :data-finished="userInfoFinished&&contestFinished" type="Atcoder" v-if="panel2Switch">
        </AbsoluteRatingChangeGraph>
        <RelativeRatingLineGraph :data-finished="userInfoFinished&&contestFinished" :contest-data="acContestInfo" :user-info="userInfo" type="Atcoder" v-else></RelativeRatingLineGraph>
      </el-card>
      <el-divider></el-divider>
      <el-card>
        <SubmitBarGraph :data-finished="userInfoFinished&&MonthSubmitFinished" :user-info="userInfo" :submit-data="acMonthSubmit" type="Atcoder"></SubmitBarGraph>
      </el-card>
    </el-tab-pane>
    <el-tab-pane lazy label="solveTag">
      <el-card>
        <div style="height: 150vh; width: 100%">
          <CodeforcesOKSubmitGraphProxy :tid="Number.parseInt(route.params.tid)"></CodeforcesOKSubmitGraphProxy>
        </div>
      </el-card>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import {onMounted,ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import AbsoluteRatingChangeGraph from "../../components/TagComponents/Graph/AbsoluteRatingChangeGraph.vue";
import RelativeRatingLineGraph from "../../components/TagComponents/Graph/RelativeRatingLineGraph.vue";
import SubmitBarGraph from "../../components/TagComponents/Graph/SubmitBarGraph.vue";
import CodeforcesOKSubmitGraphProxy from "../../components/TagComponents/Graph/CodeforcesOKSubmitGraphProxy.vue";
const route = useRoute();
const router = useRouter();
let userInfo = ref({})
let userInfoFinished = ref(false)
let contestFinished = ref(false)
let acContestInfo = ref({})
let cfContestInfo = ref({})
let panel1Switch = ref(false)
let panel2Switch = ref(false)
let MonthSubmitFinished = ref(false)
let acMonthSubmit = ref({})
let cfMonthSubmit = ref({})
onMounted(()=>{
  let tid = route.params.tid
  axiosUtil.getTagUserInfo(tid).then(res=>{
    if(res.data.code === 200){
      let obj = {}
      for(let item of res.data.result){
        obj[item.username] = item
      }
      userInfo.value = obj
      userInfoFinished.value = true
    }else{
      ElMessage.error(res.data.message)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("获取标签用户数据失败")
  })
  axiosUtil.getTagContestInfo(tid).then(res=>{
    if(res.data.code === 200){
      let acobj = {}
      let cfobj = {}
      for(let item of res.data.result.acStuContest){
        if(!acobj.hasOwnProperty(item.username)) {
          acobj[item.username] = []
        }
        acobj[item.username].push(item)
      }
      for(let item of res.data.result.cfStuContest){
        if(!cfobj.hasOwnProperty(item.username)) {
          cfobj[item.username] = []
        }
        cfobj[item.username].push(item)
      }
      acContestInfo.value = acobj
      cfContestInfo.value = cfobj
      contestFinished.value = true
    }else{
      ElMessage.error(res.data.message)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("获取标签用户数据失败")
  })
  axiosUtil.getTagMonthSubmit(tid).then(res => {
    if(res.data.code === 200){
      let acobj = {}
      let cfobj = {}
      for(let item of res.data.result.ac){
        if(!acobj.hasOwnProperty(item.username)) {
          acobj[item.username] = []
        }
        acobj[item.username].push(item)
      }
      for(let item of res.data.result.cf){
        if(!cfobj.hasOwnProperty(item.username)) {
          cfobj[item.username] = []
        }
        cfobj[item.username].push(item)
      }
      acMonthSubmit.value = acobj
      cfMonthSubmit.value = cfobj
      MonthSubmitFinished.value = true
    }else{
      ElMessage.error(res.data.message)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("获取标签月提交数据失败")
  })
})
</script>

<style scoped>

</style>