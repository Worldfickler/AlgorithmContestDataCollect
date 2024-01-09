<template>
  <el-dialog v-model="display" @close="close">
    <el-form
        ref="formRef"
        :rules="rules"
        label-width="120px"
        :model="userinfo"
    >
      <el-form-item label="username" prop="username">
        <el-input v-model="userinfo.username"/>
      </el-form-item>
      <el-form-item label="password" prop="password">
        <el-input v-model="userinfo.password" type="password"/>
      </el-form-item>
      <el-form-item label="classname" prop="classname">
        <el-input v-model="userinfo.classname"/>
      </el-form-item>
      <el-form-item label="stuno" prop="stuno">
        <el-input v-model="userinfo.stuno"/>
      </el-form-item>
      <el-form-item label="school" prop="school">
        <el-select v-model="userinfo.school">
          <el-option v-for="item in schoolList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="year" prop="year">
        <el-input-number v-model="userinfo.year" :min="2000" :max="(new Date()).getFullYear()"/>
      </el-form-item>
      <el-form-item label="realname" prop="realname">
        <el-input v-model="userinfo.realname"/>
      </el-form-item>
      <el-form-item v-for="(value,key) in codeforcesList" :label="'codeforcesAccount' + (key+1)">
        <el-input v-model="codeforcesList[key]"></el-input> <el-button v-if="key === codeforcesList.length - 1" @click="addOneCodeforces()">增加</el-button>
      </el-form-item>
      <el-form-item v-for="(value,key) in atcoderList" :label="'atcoderAccount' + (key+1)">
        <el-input v-model="atcoderList[key]"></el-input> <el-button v-if="key === atcoderList.length - 1" @click="addOneAtcoder()">增加</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit" :loading="loading">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, reactive} from "vue";
import InfoUtils from "../utils/InfoUtils";
import simpleUtils from "../utils/simpleUtils";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
const display = ref(false)
const formRef = ref(null)
const loading = ref(false)
let userinfo = ref({
  username: "",
  password: "",
  classname: "",
  stuno: "",
  school: 1,
  year: 0,
  realname: ""
})
let codeforcesList = ref([""])
let atcoderList = ref([""])
defineExpose({open(){
    display.value = true
  }})
let close = ()=>{
  display.value = false
  userinfo.value = {
    username: "",
    password: "",
    classname: "",
    stuno: "",
    school: 1,
    year: 0,
    realname: ""
  }

}
const schoolList = ref([])
const emit = defineEmits(["reload"])
const rules = reactive({
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"},
    {min: 4, max: 12, message: "用户名长度必须在4到12位之间", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumberAndUnderscore(value)) {
          callback()
        } else {
          callback(new Error("用户名只能包含数字、字母和下划线"))
        }
      }, trigger: "blur"
    }
  ],
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
  classname: [
    {required: true, message: "请输入班级名称", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkAllChineseAndEnglishAndNumber(value)) {
          callback()
        } else {
          callback(new Error("班级名称不能包含特殊符号"))
        }
      }, trigger: "blur"
    }
  ],
  stuno: [
    {required: true, message: "请输入学号", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumber(value)) {
          callback()
        } else {
          callback(new Error("学号只能包含字母与数字"))
        }
      }, trigger: "blur"
    }
  ],
  school: [
    {required: true, message: "请选择学校", trigger: "blur"}
  ],
  year: [
    {required: true, message: "请输入入学年份", trigger: "blur"},
    {type: "number", message: "请输入数字", trigger: "blur"}
  ],
  realname: [
    {required: true, message: "请输入真实姓名", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkAllChineseAndEnglishAndNumber(value)) {
          callback()
        } else {
          callback(new Error("真实姓名不能包含特殊符号"))
        }
      }, trigger: "blur"
    }
  ]
})

onMounted(() => {
  InfoUtils.getSchoolMap().then(res => {
    schoolList.value = res.schoolList
  })
})
let submit = () => {
  if (!formRef) return
  formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      let codeforcesId = codeforcesList.value.filter(item => item !== "")
      let atcoderId = atcoderList.value.filter(item => item !== "")
      let data = {
        username: userinfo.value.username,
        password: simpleUtils.SHA512(userinfo.value.password),
        classname: userinfo.value.classname,
        stuno: userinfo.value.stuno,
        school: userinfo.value.school,
        year: userinfo.value.year,
        realname: userinfo.value.realname,
        codeforcesId: codeforcesId,
        atcoderId: atcoderId
      }
      axiosUtil.addUser(data).then(res => {
        if (res.data.code === 200) {
          ElMessage.success("添加成功")
          emit("reload")
          close()
        } else {
          ElMessage.error(res.msg)
        }
      }).catch(err=>{
        console.log(err)
        ElMessage.error("添加失败")
      }).finally(()=>{
        loading.value = false
      })
    }
  })
}
let addOneCodeforces = () => {
  codeforcesList.value.push("")
}
let addOneAtcoder = () => {
  atcoderList.value.push("")
}
</script>

<style scoped>

</style>