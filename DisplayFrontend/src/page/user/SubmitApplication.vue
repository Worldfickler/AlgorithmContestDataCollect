<template>
  <div class="container">
    <el-card class="card">
      <el-form>
        <el-form-item label="指令">
          <el-select v-model="type">
            <el-option label="重新获取atcoder比赛数据" value="getAtcoderContestInfo"></el-option>
            <el-option label="重新获取atcoder提交信息" value="getAtcoderSubmit"></el-option>
            <el-option label="获取atcoder补题信息" value="getAtcoderAfterSolve"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            v-if="type === 'getAtcoderContestInfo' || type === 'getAtcoderAfterSolve' || type === 'getAtcoderSubmit'">
          <el-select v-model="acAccount" placeholder="请选择atcoder账号">
            <el-option v-for="item in acAccounts" :key="item.atcoderId" :label="item.atcoderId" :value="item.atcoderId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="type === 'getAtcoderAfterSolve' || type === 'getAtcoderSubmit'">
          <el-input v-model="nickName" placeholder="请输入atcoder比赛昵称"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="submit" class="btn" v-loading="loading">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script setup>
import {ref, onMounted} from "vue";
import infoUtils from "../../utils/InfoUtils";
import router from "../../router/router";
import {ElMessage} from "element-plus";
import axiosUtil from "../../utils/axiosUtil";
let loading = ref(false)
let type = ref("getAtcoderContestInfo")
let nickName = ref("")
let acAccounts = ref(null)
let acAccount = ref(null)
onMounted(() => {
  if (!infoUtils.isLogin()) {
    router.push("/login")
    ElMessage.error("请先登录")
    return
  }
  axiosUtil.atcoderAPI.getThisUserAtcoderAccount().then(res => {
    if (res.data.code === 200) {
      acAccounts.value = res.data.result
    } else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err => {
    ElMessage.error(err)
  })
})
let submit = () => {
  if (type.value === "") {
    ElMessage.error("请选择指令")
    return
  }
  let params = {}
  if(type.value === 'getAtcoderContestInfo' || type.value === 'getAtcoderAfterSolve' || type.value === 'getAtcoderSubmit') {
    if(acAccount.value === null) {
      ElMessage.error("请选择atcoder账号")
      return
    }
    params["atcoderId"] = acAccount.value
  }
  if (type.value === "getAtcoderAfterSolve" || type.value === "getAtcoderSubmit") {
    if (nickName.value === "") {
      ElMessage.error("请输入atcoder比赛昵称")
      return
    }
    params["nickName"] = nickName.value
  }
  loading.value = true
  axiosUtil.SubmitParserApplication(type.value,params).then(res=>{
    if(res.data.code === 200){
      ElMessage.success("提交成功")
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error(err)
  }).finally(()=>{
    loading.value = false
  })
}
</script>

<style scoped lang="scss">
.container {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.btn {
  display: flex;
  justify-content: center;
}
</style>