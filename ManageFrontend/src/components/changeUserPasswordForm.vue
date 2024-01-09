<template>
  <el-dialog v-model="display" @close="close">
    <el-form ref="formRef"
             :rules="rules"
             label-width="120px"
             :model="userinfo"
    >
      <el-form-item label="newPassword" prop="password">
        <el-input v-model="userinfo.password"></el-input>
      </el-form-item>
      <el-form-item >
        <el-button type="primary" @click="submit" :loading="loading">
          提交
        </el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>

import {defineExpose, reactive, ref} from "vue";
import simpleUtils from "../utils/simpleUtils";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
const display = ref(false)
const prop = defineProps({
  uid: {
    type: Number,
    required: true
  }
})
let formRef = ref(null)
defineExpose({open(){
    display.value = true
  }})
let close = ()=>{
  display.value = false
  userinfo.value = {
    password:""
  }
}
const loading = ref(false)
const rules = reactive({
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {min: 8, message: "密码长度必须大于8位", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumber(value)) {
          callback()
        } else {
          callback(new Error("密码只能包含数字与字母"))
        }
      }, trigger: "blur"
    }
  ],
})
const userinfo = ref({
  password:"",
})
let submit = () => {
  formRef.value.validate((valid) => {
    if(!valid) return
    loading.value = true
    axiosUtil.changeUserPassword(prop.uid,simpleUtils.SHA512(userinfo.value.password)).then(res=>{
      if(res.data.code === 200){
        close()
        ElMessage.success("修改成功")
      }else{
        ElMessage.error("修改失败")
      }
    }).catch(err=>{
      console.log(err)
      ElMessage.error("修改失败")
    }).finally(()=>{
      loading.value = false
    })
  })

}
</script>

<style scoped>

</style>