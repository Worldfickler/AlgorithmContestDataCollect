<template>
  <el-table :data="sList" border>
    <el-table-column fixed prop="id" label="Id" align="center" />
    <el-table-column fixed prop="strategyName" label="Name" align="center" />
    <el-table-column fixed align="center" >
      <template #header>
        <el-button type="success" @click="addOne">增加</el-button>
      </template>
      <template #default="scope">
        <el-button type="info" @click="checkOne(scope.row.id)">查看</el-button>
        <el-button type="danger" @click="deleteOne(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <add-strategy ref="addStrategy" @reload="load"></add-strategy>
  <json-modal ref="jsonModal" :json-data="jsonData"></json-modal>
</template>

<script setup>
import {ref, defineExpose, onMounted} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import AddStrategy from "../components/addStrategy.vue";
import JsonModal from "../components/jsonModal.vue";
let sList = ref([])
let addStrategy = ref(null)
let jsonModal = ref(null)
let jsonData = ref("")
onMounted(()=>{
  load()
})
let load = ()=> {
  axiosUtil.getStrategyList().then(res=>{
    if(res.data.code === 200){
      sList.value = res.data.result
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
}
let addOne = ()=>{
  addStrategy.value.open()
}
let checkOne = (id)=>{
  let item = sList.value.find(item=>item.id === id)
  jsonData.value = JSON.parse(item.useFunction)
  jsonModal.value.open()
}
let deleteOne = (id)=>{
  axiosUtil.delStrategy(id).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("删除成功")
      sList.value = sList.value.filter(item=>item.id !== id)
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