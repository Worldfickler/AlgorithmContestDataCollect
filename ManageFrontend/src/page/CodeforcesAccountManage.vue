<template>
  <el-table :data="pageData" border>
    <el-table-column prop="id" label="id">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="idFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="codeforcesId" label="账户名">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="codeforcesIdFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="username" label="使用者">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="usernameFilter"></el-input>
      </template>
      <template #default="scope">
        {{scope.row.username}}
      </template>
    </el-table-column>
    <el-table-column prop="realname" label="使用者姓名">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="realnameFilter"></el-input>
      </template>
      <template #default="scope">
        {{scope.row.realname}}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="danger" @click="deleteAccount(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="dspData.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, computed} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import axiosUtil from "../utils/axiosUtil";
const data = ref([])
const usernameFilter = ref("")
const realnameFilter = ref("")
const codeforcesIdFilter = ref("")
const idFilter = ref("")
const pageSize = ref(20)
const currentPage = ref(1)
const pageData = computed(() => {
  return dspData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value)
})
const dspData = computed(()=>{
  let dt = data.value;
  console.log("sort")
  dt = dt.filter((item)=>{
    if (usernameFilter.value !== '' && !item.username.includes(usernameFilter.value)) {
      return false
    }
    if (realnameFilter.value !== '' && !item.realname.includes(realnameFilter.value)) {
      return false
    }
    if (codeforcesIdFilter.value !== '' && !item.codeforcesId.includes(codeforcesIdFilter.value)) {
      return false
    }
    if (idFilter.value !== '' && !(item.id+"").includes(idFilter.value)) {
      return false
    }
    return true
  })
  return dt;
})
onMounted(()=>{
  axiosUtil.getAllCodeforcesAccount().then(res=> {
    if(res.data.code === 200) {
      data.value = res.data.result
    }else {
      ElMessage.error(res.data.msg)
    }
  }).catch(e=>{
    ElMessage.error(e)
  })
})
const deleteAccount = (id) => {
  ElMessageBox.confirm('此操作将永久删除该账户,并且其比赛数据与提交记录也会随之删除, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    axiosUtil.deleteCodeforcesAccount(id).then(res=>{
      if(res.data.code === 200) {
        ElMessage.success("删除成功")
        data.value = data.value.filter(item=>item.id !== id)
      }else {
        ElMessage.error(res.data.msg)
      }
    }).catch(e=>{
      console.log(e)
      ElMessage.error("删除失败")
    })
  }).catch(() => {
    ElMessage.info('已取消删除')
  });
}
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>