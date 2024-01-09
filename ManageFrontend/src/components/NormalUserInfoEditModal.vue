<template>
  <el-dialog v-model="display" @close="close">
    <el-form
        :model="userinfo"
        :rules="rules"
        label-width="100px"
        ref="formRef"
    >
      <el-form-item label="username" prop="username">
        <el-input v-model="userinfo.username" :disabled="true"/>
      </el-form-item>
      <el-form-item label="classname" prop="classname">
        <el-input v-model="userinfo.classname" />
      </el-form-item>
      <el-form-item label="stuNo" prop="stuNo">
        <el-input v-model="userinfo.stuNo" />
      </el-form-item>
      <el-form-item label="school" prop="school">
        <el-select v-model="userinfo.school" :disabled="true">
          <el-option v-for="item in schoolList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="year" prop="year">
        <el-input-number v-model="userinfo.year" :min="2000" :max="(new Date()).getFullYear()"
                         :disabled="editStatus === false"/>
      </el-form-item>
      <el-form-item label="realname" prop="realname" >
        <el-input v-model="userinfo.realname" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close()">取消</el-button>
        <el-button type="primary" @click="submit" v-loading="loading">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {onMounted, reactive, ref,defineExpose} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import InfoUtils from "../utils/InfoUtils";
import simpleUtils from "../utils/simpleUtils";
let display = ref(false)
let formRef = ref(null)
let rules = reactive({
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
  stuNo: [
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
let uid = ref(0)
let userinfo = ref({
  "classname": "",
  "school": 1,
  "year": 0,
  "stuNo": "",
  "username": "",
  "realname": ""
})
const loading = ref(false)
const schoolList = ref([])
onMounted(() => {
  InfoUtils.getSchoolMap().then(res => {
    schoolList.value = res.schoolList
  })
})
let emit = defineEmits(['editSuccess'])
defineExpose({
  open: (id,userInfo) => {
    display.value = true
    userinfo.value = userInfo
    uid.value = id
  }
})
let submit = () => {
  if (formRef.value === null) return
  formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      axiosUtil.updateNormalUserInfo(uid.value,userinfo.value.classname,userinfo.value.year,userinfo.value.stuNo,userinfo.value.realname).then(res => {
        if (res.data.code === 200) {
          ElMessage.success("修改成功")
          emit("editSuccess",uid.value,userinfo.value)
          close()
        } else {
          ElMessage.error(res.data.msg)
        }
      }).catch(err => {
        console.log(err)
        ElMessage.error("网络异常")
      }).finally(() => {
        loading.value = false
      })
    }
  })
}
let close = () => {
  userinfo.value = {
    "classname": "",
    "school": 1,
    "year": 0,
    "stuNo": "",
    "username": "",
    "realname": ""
  }
  uid.value = 0
  display.value = false
}
</script>

<style scoped>

</style>