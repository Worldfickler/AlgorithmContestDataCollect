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
      <el-form-item v-for="(value,key) in sInfo.useFuncs" :label="'策略' + (key+1)">
        <el-select v-model="sInfo.useFuncs[key].id">
          <el-option v-for="item in funcs" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <el-input-number v-model="sInfo.useFuncs[key].num" min="0" max="99" placeholder="题数"></el-input-number>
        <el-button type="danger" @click="sInfo.useFuncs.splice(key,1)">删除</el-button>
      </el-form-item>
      <el-button @click="add()">增加</el-button>
      <el-form-item>
        <el-button type="primary" @click="submit" :loading="loading">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, reactive,defineEmits} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
let loading = ref(false)
let sInfo = ref({
  name:"",
  useFuncs:[]
})
let emit = defineEmits(['reload'])
let funcIdMap = {}
let funcs = ref([])
onMounted(()=>{
  axiosUtil.getFuncs().then(res=>{
    console.log(res)
    if(res.data.code === 200){
      funcs = res.data.result
      funcs.forEach(item=>{
        funcIdMap[item.id] = item
      })
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
})
const display = ref(false)
defineExpose({open(){
    display.value = true
  }})
let close = ()=>{
  display.value = false
  sInfo.value = {
    name:"",
    useFuncs:[]
  }
}
let add = ()=>{
  sInfo.value.useFuncs.push({id:'', num:'' })
}
let submit = ()=>{
  loading.value = true
  if(sInfo.value.name === ""){
    ElMessage.error("请输入策略名称")
    loading.value = false
    return
  }
  if(sInfo.value.useFuncs.length === 0) {
    ElMessage.error("请添加策略")
    loading.value = false
    return
  }
  let useFuncs = []
  sInfo.value.useFuncs.forEach(item=>{
    let usefunc = {
      funcName:funcIdMap[item.id].name,
      num:item.num
    }
    useFuncs.push(usefunc)
  })
  axiosUtil.addStrategy(sInfo.value.name,useFuncs).then(res=>{
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