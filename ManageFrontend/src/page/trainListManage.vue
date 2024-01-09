<template>
  <el-table :data="trainList" border>
    <el-table-column fixed prop="id" label="Id" align="center" />
    <el-table-column fixed prop="name" label="Name" align="center" />
    <el-table-column fixed prop="strategyId" label="使用策略" align="center" >
      <template #default="scope">
        {{SIdToName[scope.row.strategyId]}}
      </template>
    </el-table-column>
    <el-table-column fixed label="开始时间" align="center" >
      <template #default="scope">
        {{simpleUtils.timeStampToLocalTime(scope.row.startTimestamp)}}
      </template>
    </el-table-column>
    <el-table-column fixed label="结束时间" align="center" >
      <template #default="scope">
        {{simpleUtils.timeStampToLocalTime(scope.row.endTimestamp)}}
      </template>
    </el-table-column>
    <el-table-column fixed align="center" >
      <template #header>
        <el-button type="success" @click="addOne">增加</el-button>
      </template>
      <template #default="scope">
        <el-button type="danger" @click="deleteOne(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <add-train-form ref="form" @reload="load"></add-train-form>
</template>

<script setup>
import {onMounted,ref} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import simpleUtils from "../utils/simpleUtils";
import AddTrainForm from "../components/addTrainForm.vue";


let trainList = ref([])
let form = ref(null)
let SIdToName = ref({})
onMounted(()=>{
  axiosUtil.getStrategyList().then(res=>{
    if(res.data.code === 200){
      res.data.result.forEach(item=>{
        SIdToName.value[item.id] = item.strategyName
      })
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
  load()
})
let load = ()=>{
  axiosUtil.getTrainList().then((res)=>{
    if(res.data.code === 200){
      trainList.value = res.data.result
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
}
let addOne = ()=>{
  form.value.open()
}
let deleteOne = (id)=>{
  axiosUtil.delTrain(id).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("删除成功")
      trainList.value = trainList.value.filter(item=>item.id !== id)
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
}
</script>

<style scoped>

</style>