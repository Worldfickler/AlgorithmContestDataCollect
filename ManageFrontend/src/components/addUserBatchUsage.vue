<template>
  <el-dialog v-model="display" @close="close" title="使用说明" >
    <el-image :src="uploadUsage" alt="上传使用说明">
    </el-image>
    <span class="text">上传使用说明</span>
    <el-divider></el-divider>
    <el-image :src="templateUsage" alt="模板字段说明">
      <template #viewer>
        模板字段说明
      </template>
    </el-image>
    <span class="text">模板字段说明</span>
  </el-dialog>
</template>

<script setup>
import {ref, defineExpose} from "vue";
import uploadUsage from "../assets/上传使用说明.png?url"
import templateUsage from "../assets/模板字段说明.png?url"
const display = ref(false)
defineExpose({open(){
    display.value = true
  }})
let close = ()=>{
  display.value = false
}
</script>

<style scoped>
.text{
  color: #909399;
}
</style>