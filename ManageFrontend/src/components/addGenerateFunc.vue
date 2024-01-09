<template>
  <el-dialog v-model="display" @close="close">
    <el-form
        ref="formRef"
        label-width="120px"
        :model="sInfo"
    >
      <el-form-item label="name" prop="name">
        <el-input v-model="sInfo.name"/>
      </el-form-item>
      <el-form-item label="description" prop="description">
        <el-input v-model="sInfo.description"/>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="submit" :loading="loading">提交</el-button>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, reactive,defineEmits} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
let loading = ref(false)
let sInfo = ref({
  name:"",
  description:[]
})
let emit = defineEmits(['reload'])
const display = ref(false)
defineExpose({open(){
    display.value = true
  }})
let close = ()=>{
  display.value = false
  sInfo.value = {
    name:"",
    description:[]
  }
}
let submit = ()=>{
  loading.value = true
  if(sInfo.value.name === ""){
    ElMessage.error("请输入函数名称")
    loading.value = false
    return
  }
  axiosUtil.addGenerateFunc(sInfo.value.name,sInfo.value.description).then(res=>{
    console.log(res)
    if(res.data.code === 200){
      ElMessage.success("添加成功")
      emit("reload")
      close()
    }else {
      ElMessage.error(res.data.msg)
    }
    loading.value = false
  }).catch(err=>{
    ElMessage.error(err)
    loading.value = false
  })
}
</script>

<style scoped>

</style>