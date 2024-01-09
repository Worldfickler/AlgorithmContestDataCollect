<template>
  <el-card :shadow="false">
    <el-row>
      <el-col :span="12">
        <el-row>
          <el-col :span="6">
            <el-select v-model="schoolSelect" placeholder="标签选择">
              <el-option label="全体人员" :value="-1"></el-option>
              <el-option
                  v-for="item in schoolList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
              />
            </el-select>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-card>
  <el-divider></el-divider>
  <div id="container">
    <GradeCompareGraph :data-fin="atcoderFin&&codeforcesFin" :atcoder-data="atcoderData" :codeforces-data="codeforcesData"></GradeCompareGraph>
  </div>
</template>

<script setup>

import {nextTick, onBeforeMount, onMounted, ref, watchEffect, watch, computed} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import GradeCompareGraph from "../../components/infoComponents/Graphs/GradeCompareGraph.vue";
import {useRoute} from "vue-router";
import infoUtils from "../../utils/InfoUtils";
let atcoderFin = ref(false)
let codeforcesFin = ref(false)
let allCodeforcesData = ref([])
let allAtcoderData = ref([])
let route = useRoute()
let schoolSelect = ref(-1)
let schoolList = ref([])
let schoolMap = ref({})
let codeforcesData = computed(()=>{
if(schoolSelect.value===-1){
    return allCodeforcesData.value
  }else{
    return allCodeforcesData.value.filter(item=>item.school==schoolSelect.value)
  }
})
let atcoderData = computed(()=>{
  if(schoolSelect.value===-1){
    return allAtcoderData.value
  }else{
    return allAtcoderData.value.filter(item=>item.school==schoolSelect.value)
  }
})
onBeforeMount(()=>{
  let school = null
  if(route.params.school) {
    schoolSelect.value = Number.parseInt(route.params.school)
  }else{
    schoolSelect.value = -1
  }
  infoUtils.getSchoolMap().then(r=>{
    schoolMap.value = r.schoolMap
    schoolList.value = r.schoolList
    for(let item of r.schoolList) {
      if (item.name === "北京化工大学") {
        schoolSelect.value = item.id
      }
    }
  })
  axiosUtil.atcoderAPI.getAllAtcoderAccount().then((res) => {
    if(res.data.code === 200) {
      if(school === null) {
        allAtcoderData.value = res.data.result
      }
      atcoderFin.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常,atcoder账户信息获取失败")
    console.log(err)
  })
  axiosUtil.codeforcesAPI.getAllCodeforcesAccount().then((res) => {
    if(res.data.code === 200) {
      if(school === null) {
        allCodeforcesData.value = res.data.result
      }
      codeforcesFin.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常,codeforces账户信息获取失败")
  })
})

</script>

<style scoped>
#container{
  height: calc( 100vh - 60px);
  width: 100%;
}
</style>