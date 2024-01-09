<template>
  <el-table :data="funcList" border>
    <el-table-column fixed prop="id" label="Id" align="center" />
    <el-table-column fixed prop="name" label="Name" align="center" />
    <el-table-column fixed prop="description" label="Description" align="center" />
    <el-table-column fixed align="center" >
      <template #header>
        <el-button type="success" @click="addOne">增加</el-button>
      </template>
      <template #default="scope">
        <el-button type="danger" @click="deleteOne(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <add-generate-func @reload="load" ref="addGenerateFunc"></add-generate-func>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import AddGenerateFunc from "../components/addGenerateFunc.vue";
let funcList = ref([])
let addGenerateFunc = ref(null)
onMounted(()=>{
  load()
})
let load  = ()=>{
  axiosUtil.getFuncs().then(res=>{
    if(res.data.code === 200){
      funcList.value = res.data.result
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
}
let addOne = ()=>{
  addGenerateFunc.value.open()
}
let deleteOne = (id) => {
  axiosUtil.delGenerateFunc(id).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("删除成功")
      load()
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