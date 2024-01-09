<template>
  <el-table :data="pageData" border>
    <el-table-column fixed prop="id" label="Id" align="center" />
    <el-table-column fixed prop="opertation" align="center" label="操作"/>
    <el-table-column fixed prop="time" align="center" label="申请时间">
      <template #default="scope">
        {{utils.timeStampToLocalTime(scope.row.time)}}
      </template>
    </el-table-column>
    <el-table-column fixed prop="username" label="申请人" align="center" >
      <template #default="scope">
        {{scope.row.username}}
        <span v-if="scope.row.realname && scope.row.realname !=='' ">
          ({{scope.row.realname}})
        </span>
      </template>
    </el-table-column>
    <el-table-column fixed prop="schoolName" label="学校" align="center" >
      <template #default="scope">
        {{schoolMap[scope.row.school]}}
      </template>
    </el-table-column>
    <el-table-column fixed prop="status" label="状态" align="center">
      <template #default="scope">
        <el-tag v-if="scope.row.status===1" type="success">
          通过
        </el-tag>
        <el-tag v-else-if="scope.row.status === 2" type="danger">
          拒绝
        </el-tag>
        <el-tag v-else-if="scope.row.status === 0" type="warning">
          待审核
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column align="center">
      <template #default="scope" >
        <el-button type="primary" @click="handleCheck(scope.row.parameter)">查看参数</el-button>
        <div v-if="scope.row.status === 0">
          <el-button type="danger" @click="accept(scope.row.id)">接受</el-button>
          <el-button type="danger" @click="reject(scope.row.id)">拒绝</el-button>
        </div>
      </template>
    </el-table-column>
    <template #append="scope">
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="dspData.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
  <json-modal :json-data="nowInfo" ref="jsonM"></json-modal>
</template>

<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import { ElMessage, ElMessageBox } from 'element-plus'
import api from "../utils/axiosUtil"
import InfoUtils from "../utils/InfoUtils";
import utils from "../utils/simpleUtils"
import JsonModal from "../components/jsonModal.vue";
let schoolList = ref([])
const applicationList = ref([]);
const loading = ref(false);
const schoolMap = ref({});
let nowInfo = ref("")
let jsonM = ref()
let pageSize = ref(10)
let currentPage = ref(1)
let pageData = computed(()=>{
  return dspData.value.slice((currentPage.value - 1)*pageSize.value,(currentPage.value)*pageSize.value)
})
const dspData = computed(()=>{
  let dsp = applicationList.value
  dsp = dsp.sort((a,b)=>{
    return b.time - a.time
  })
  return dsp
})

onMounted(()=>{
  InfoUtils.getSchoolMap().then((value)=>{
    schoolList.value = value.schoolList
    schoolMap.value = value.schoolMap
  })
  api.getApplicationList().then(res=>{
    if (res.data.code === 200) {
      for(var i=0;i<res.data.result.length;i++){
        var item=res.data.result[i];
        if(item["opertation"]=="createNewUser"){
          item.realname=JSON.parse(item["parameter"])["realname"];
          item.username=JSON.parse(item["parameter"])["username"];
        }
      }
      applicationList.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("请求失败，请检查网络")
  }).finally(()=>{
    loading.value = false
  })
})

let accept = (id)=>{
  ElMessageBox.confirm('确认接受申请？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.acceptApplication(id).then(res=>{
      if (res.data.code === 200) {
        ElMessage.success(res.data.msg)
        applicationList.value = applicationList.value.map(item=>{
          if (item.id === id) {
            item.status = 1
          }
          return item
        })
      }else{
        ElMessage.error(res.data.msg)
      }
    }).catch(err=>{
      console.log(err)
      ElMessage.error("请求失败，请检查网络")
    })
  }).catch(() => {
    ElMessageBox.close()
  });
}
let reject = (id)=>{
  ElMessageBox.confirm('确认拒绝申请？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.rejectApplication(id).then(res=>{
      if (res.data.code === 200) {
        ElMessage.success(res.data.msg)
        applicationList.value = applicationList.value.map(item=>{
          if (item.id === id) {
            item.status = 2
          }
          return item
        })
      }else{
        ElMessage.error(res.data.msg)
      }
    }).catch(err=>{
      console.log(err)
      ElMessage.error("请求失败，请检查网络")
    }).finally(()=>{
      loading.value = false
    })
  }).catch(() => {
    ElMessageBox.close()
  });
}
let handleCheck = (parameter)=>{
  nowInfo.value = JSON.parse(parameter)
  console.log(jsonM.value)
  if (jsonM.value) {
    jsonM.value.open()
  }
}
</script>

<style scoped lang="scss">
  .pag{
    display: flex;
    justify-content: center;
  }
</style>