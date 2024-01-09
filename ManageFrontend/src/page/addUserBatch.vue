<template>
  <el-upload
      ref="upload"
      :auto-upload="false"
      :on-change="handleChange"
  >
    <template #trigger>
      <el-button type="primary" :loading="loading">选择文件</el-button>
    </template>
  </el-upload>
  <el-button @click="uploadAll" :loading="loading">全部上传</el-button>
  <el-button @click="uploadAllSingle" :loading="loading">逐个上传</el-button>
  <el-button @click="clearAll" :loading="loading">清空</el-button>
  <el-button @click="usage.open()">使用说明</el-button>
  <el-button @click="downloadTemplate">csv模板下载</el-button>
  <add-user-batch-usage ref="usage"></add-user-batch-usage>
  <el-table :data="toUpload">
    <el-table-column label="上传状态/已存在">
      <template #default="scope">
        <el-tag v-if="hasUpload.indexOf(scope.row.username) !== -1" type="success">已上传/已存在用户</el-tag>
        <el-tag v-else-if="nowUploading.indexOf(scope.row.username) !== -1" type="warning">正在上传</el-tag>
        <el-tag v-else type="info">未上传</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="用户名" prop="username"></el-table-column>
    <el-table-column label="真实姓名" prop="realname"></el-table-column>
    <el-table-column label="学校" prop="school">
      <template #default="scope">
        {{schoolMap[scope.row.school]}}
      </template>
    </el-table-column>
    <el-table-column label="学号" prop="stuno">
    </el-table-column>
    <el-table-column label="年级" prop="year"></el-table-column>
    <el-table-column label="班级" prop="classname"></el-table-column>
    <el-table-column label="codeforces账号">
      <template #default="scope">
        <div v-for="(value,key) in scope.row.codeforcesId" :key="key">
          <a :href="'http://codeforces.com/profile/'+value" target="_blank">{{value}}</a>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="atcoder账号">
      <template #default="scope">
        <div v-for="(value,key) in scope.row.atcoderId" :key="key">
          <a :href="'https://atcoder.jp/users/'+value" target="_blank">{{value}}</a>
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import simpleUtils from "../utils/simpleUtils";
import {onMounted, ref} from "vue";
import InfoUtils from "../utils/InfoUtils";
import {ElMessage} from "element-plus";
import axiosUtil from "../utils/axiosUtil";
import importTemplate from "../assets/importTemplate.csv?url"
import AddUserBatchUsage from "../components/addUserBatchUsage.vue";
let usage = ref(null)
let loading  = ref(false)
let nowUploading = ref([])
let toUpload = ref([]);
let upload = ref(null)
let schoolMap = ref({})
let usernamePool = ref({})
let hasUpload = ref([])

onMounted(()=>{
  InfoUtils.getSchoolMap().then((value)=>{
    schoolMap.value = value.schoolMap
  })
})
const uploadAll = () => {
  let RemainData = []
  for(let data of toUpload.value){
    if(hasUpload.value.indexOf(data.username) === -1){
      RemainData.push(data)
    }
  }
  axiosUtil.addUserBatch(RemainData).then(res=>{
    if (res.data.code === 200) {
      for(let data of RemainData){
        hasUpload.value.push(data.username)
        nowUploading.value.push(data.username)
      }
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("上传失败")
  }).finally(()=>{
    loading.value = false
    nowUploading.value = nowUploading.value.filter(value => value !== data.username)
  })
}
const downloadTemplate = () => {
  const a = document.createElement('a')
  a.href=importTemplate
  a.download="importTemplate.csv"
  a.click()

}
const uploadAllSingle = () => {
  var requestQueue = new Set();
  let upload = (data) => {
    if(requestQueue.size > 5) {
      setTimeout(() => {
        upload(data)
      }, 50)
      return
    }else{
      requestQueue.add(data.username)
      nowUploading.value.push(data.username)
      axiosUtil.addUser(data).then(res=>{
        if(res.data.code === 200) {
          hasUpload.value.push(data.username)
        }else{
          ElMessage.error(res.data.msg)
        }
      }).catch(err=>{
        ElMessage.error("上传失败")
        console.log(err)
      }).finally(()=>{
        requestQueue.delete(data.username)
        nowUploading.value = nowUploading.value.filter(value => value !== data.username)
        if(requestQueue.size === 0){
          loading.value = false
        }
      })
    }
  }
  for (let item of toUpload.value) {
    if(hasUpload.value.indexOf(item.username) !== -1) continue
    upload(item)
  }
}
const handleChange = (file , files) =>{
  console.log(file,files)
  upload.value.clearFiles()
  let reverseMap = {};
  for(let key in schoolMap.value){
    reverseMap[schoolMap.value[key]] = key
  }
  var reader = new FileReader()
  reader.readAsText(file.raw)
  reader.onload = function (readRes) {
    let userList = []
    //分析csv
    let rows = readRes.target.result.split("\n")
    var len = rows.length
    if (rows[0].endsWith("\r")) {
      rows[0] = rows[0].split('\r')[0]
    }
    let header = rows[0].split(",")
    if(readRes.target.result.endsWith("\n")) {
      len -= 1
    }
    try {
      for (let i = 1; i<len; i++) {
        let info = {}
        info['codeforcesId'] = []
        info['atcoderId'] = []
        if (rows[i].endsWith("\r")) {
            rows[i] = rows[i].split('\r')[0]
        }
        var metas = rows[i].split(",")
        for (var j = 0;j<metas.length;j++){
          if(String(header[j]).startsWith("codeforceID")) {
            if (metas[j] === "") continue
            info['codeforcesId'].push(metas[j])
          }else if(String(header[j]).startsWith("atcoderID")) {
            if (metas[j] === "") continue
            info['atcoderId'].push(metas[j])
          }else if (header[j] === "password") {
            info['password'] = simpleUtils.SHA512(metas[j])
          }else if (header[j] === "school"){
            info['school'] = reverseMap[metas[j]]
          } else {
            info[header[j]] = metas[j]
          }
        }
        userList.push(info)
      }
    }catch (e) {
      ElMessage.error("csv格式异常")
      console.log(e)
    }
    console.log(userList)
    //记录本次上传的用户,替换用户名相同但数据不同的项目
    for(let i = 0;i<userList.length;i++) {
      if(usernamePool.value.hasOwnProperty(userList[i]['username'])){
        toUpload.value[usernamePool.value[userList[i]['username']]] = userList[i]
        continue
      }
      toUpload.value.push(userList[i])
      usernamePool.value[userList[i]['username']] = toUpload.value.length - 1
    }

    //检测用户存在
    let data = []
    for(let item in usernamePool.value) {
      data.push(item)
    }
    axiosUtil.checkUserExist(data).then(res=>{
      if(res.data.code === 200) {
        for(let username of res.data.result) {
          hasUpload.value.push(username)
        }
      }else{
        ElMessage.error(res.data.msg)
      }
    }).catch(err=>{
      console.log(err)
      ElMessage.error("用户存在检测失败")
    })
  }

}
let clearAll = () => {
  toUpload.value = []
  usernamePool.value = {}
  hasUpload.value = []
}
</script>

<style scoped>

</style>