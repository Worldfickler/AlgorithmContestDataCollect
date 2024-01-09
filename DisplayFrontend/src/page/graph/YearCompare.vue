<template>
  <el-card shadow="never">
    <el-row>
      <el-col :span="12">
        <el-row>
          <el-col :span="6">
            <el-select v-model="schoolSelect" placeholder="标签选择" @change="change">
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
  <el-card v-loading="loading">
    <div id="container">
      <YearCompare :data-fin="codeforcesFin" :codeforces-data="codeforcesData"></YearCompare>
    </div>
  </el-card>
</template>

<script setup>

import {nextTick, onBeforeMount, onMounted, ref, watchEffect, watch, computed} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import YearCompare from "../../components/infoComponents/Graphs/RatingYearCompareGraph.vue";
import {useRoute} from "vue-router";
import infoUtils from "../../utils/InfoUtils";
let loading = ref(false)
let codeforcesFin = ref(false)
let codeforcesData = ref({})
let route = useRoute()
let schoolSelect = ref(1)
let schoolList = ref([])
let schoolMap = ref({})
let req = ref(null)
onBeforeMount(()=>{
  let school = null
  if(route.params.school) {
    schoolSelect.value = Number.parseInt(route.params.school)
  }else{
    schoolSelect.value = 1
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
  loading.value = true
  req.value = axiosUtil.codeforcesAPI.getYearCompare(schoolSelect.value).then(res=>{
    if(res.data.code===200) {
      codeforcesData.value = res.data.result
      codeforcesFin.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络错误")
  }).finally(()=>{
    loading.value = false
    req.value = null
  })
})
let change = ()=>{
  if(req.value) {
    req.value.cancel()
  }
  loading.value = true
  req.value = axiosUtil.codeforcesAPI.getYearCompare(schoolSelect.value).then(res=>{
    if(res.data.code===200) {
      codeforcesData.value = res.data.result
      codeforcesFin.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络错误")
  }).finally(()=>{
    loading.value = false
    req.value = null
  })
}

</script>

<style scoped>
#container{
  height: calc( 100vh - 60px);
  width: 100%;
}
</style>