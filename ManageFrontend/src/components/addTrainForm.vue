<template>
  <el-dialog v-model="display" @close="close">
    <el-form
        ref="formRef"
        label-width="120px"
        :model="tInfo"
    >
      <el-form-item label="name" prop="name">
        <el-input v-model="tInfo.name"/>
      </el-form-item>
      <el-form-item label="生成策略" prop="strategyId">
        <el-select v-model="tInfo.strategyId" placeholder="选择生成策略">
          <el-option v-for="item in sList" :key="item.id" :label="item.strategyName" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="持续时间" prop="startDate">
        <el-date-picker
            v-model="tInfo.time_span"
            type="datetimerange"
            range-separator="To"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit" :loading="loading">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, reactive,defineEmits,toRaw } from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
let loading = ref(false)
let tInfo = ref({
  name:"",
  strategyId:'',
  time_span:""
})
let emit = defineEmits(['reload'])
let sList = ref([])
onMounted(()=>{
  axiosUtil.getStrategyList().then(res=>{
    console.log(res)
    if(res.data.code === 200){
      sList.value = res.data.result
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  })
})
let submit = ()=>{
  loading.value = true
  if(tInfo.value.strategyId === '') {
    ElMessage.error("请选择生成策略")
    loading.value = false
    return
  }
  if(tInfo.value.time_span.length === 0){
    ElMessage.error("请选择持续时间")
    loading.value = false
    return
  }
  let startTime = tInfo.value.time_span[0].$d ? tInfo.value.time_span[0].$d.getTime() : tInfo.value.time_span[0].getTime();
  let endTime = tInfo.value.time_span[1].$d ? tInfo.value.time_span[1].$d.getTime() : tInfo.value.time_span[1].getTime();
  axiosUtil.addTrain(tInfo.value.name,tInfo.value.strategyId,startTime,endTime).then(res=>{
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
const display = ref(false)
defineExpose({open(){
    display.value = true
    let tInfo = ref({
      name:"",
      strategyId:'',
      time_span:""
    })
  }})
let close = ()=>{
  display.value = false

}
</script>

<style scoped>

</style>