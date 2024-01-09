<script setup>
import {onBeforeMount, onMounted} from "vue";
import {useRouter} from "vue-router";
import api from "../utils/axiosUtil"
let router = useRouter()
onBeforeMount(()=>{
  if (window.localStorage.getItem("token") == null || parseInt(window.localStorage.getItem("expire")) < Date.now()) {
    window.localStorage.removeItem("token")
    window.localStorage.removeItem("expire")
    router.push("/login")
  }
})
let reflush = ()=>{
  api.flushToken()
}
</script>

<template>
  <div class="wrapper" >
    <el-container class="container">
      <el-aside class="aside">
        <el-menu :router="true" class="menu">
          <el-menu-item index="/index" >
            <span class="menuItemText">index</span>
          </el-menu-item>
          <el-menu-item index="/user">
            <span class="menuItemText">用户管理</span>
          </el-menu-item>
          <el-menu-item index="/application">
            <span class="menuItemText">工單管理</span>
          </el-menu-item>
          <el-menu-item index="/tags">
            <span class="menuItemText">标签管理</span>
          </el-menu-item>
          <el-menu-item index="/codeforcesAccountManage">
            <span class="menuItemText">codeforces账号管理</span>
          </el-menu-item>
          <el-menu-item index="/atcoderAccountManage">
            <span class="menuItemText">atcoder账号管理</span>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title><span class="menuItemText">智能训练相关</span></template>
            <el-menu-item index="/intelligentTraining/trainListManage">
              <span class="menuItemText">训练列表</span>
            </el-menu-item>
            <el-menu-item index="/intelligentTraining/strategyManage">
              <span class="menuItemText">策略管理</span>
            </el-menu-item>
            <el-menu-item index="/intelligentTraining/generateFunc">
              <span class="menuItemText">生成函数管理</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="1">
            <template #title><span class="menuItemText">系统管理</span></template>
            <el-menu-item index="/admin/school"><span class="menuItemText">学校管理</span></el-menu-item>
            <el-menu-item index="/admin/manager"><span class="menuItemText">管理员管理</span></el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view>
        </router-view>
      </el-main>
    </el-container>
  </div>
</template>



<style scoped lang="scss">
  .wrapper{
    .container{
      width: 100vw;
      .aside{
        width: 15%;
        .menu{
          min-height: 100vh;
          height: 100%;
          width: 100%;
          .menuItemText{
            width: 100%;
            text-align: center;
          }
        }
      }
    }
  }
</style>