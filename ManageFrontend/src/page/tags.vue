<template>
  <el-table :data="pageData" border>
    <el-table-column prop="id" label="id">
    </el-table-column>
    <el-table-column prop="name" label="名称">
    </el-table-column>
    <el-table-column label="操作">
      <template #header="scope">
        <el-button type="success" @click="addOne">增加</el-button>
      </template>
      <template #default="scope">
        <el-button>查看展示端</el-button>
        <el-button @click="editMember(scope.row.id)">修改成员</el-button>
        <el-button @click="editName(scope.row.id)">改名</el-button>
        <el-button @click="deleteOne(scope.row.id)" type="danger">删除</el-button>
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="data.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
  <tag-user-transfer :stu-list="stuList" ref="transfer" :tid="tid" v-if="dsp" @handleClose="handleClose"></tag-user-transfer>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, nextTick,computed} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage,ElMessageBox } from "element-plus";
import TagUserTransfer from "../components/tagUserTransfer.vue";
import router from "../router/router";
const data = ref([])
const stuList = ref([])
const transfer = ref(null)
const tid = ref(0)
const dsp = ref(false)
let pageSize = ref(20)
const currentPage = ref(1)
const pageData = computed(()=>{
  return data.value.slice((currentPage.value-1)*pageSize.value, currentPage.value*pageSize.value)
})
onMounted(()=>{
  axiosUtil.getStudentList().then(res=>{
    if(res.data.code === 200) {
      stuList.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络错误")
    console.log(err)
  })
  axiosUtil.getTagList().then(res=>{
    if(res.data.code === 200) {
      data.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("获取标签列表失败")
    console.log(err)
  })
})
const addOne = () => {
  ElMessageBox.prompt('请输入标签名称', '新增标签', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputErrorMessage: '输入异常',
  }).then(({value}) => {
    if (value == null || value.length == 0) {
      ElMessage.error("请输入标签名称")
      return
    }
    axiosUtil.addTag(value).then(res => {
      if (res.data.code === 200) {
        ElMessage.success("添加成功")
        data.value.push(res.data.result)
      } else {
        ElMessage.error(res.data.msg)
      }
    }).catch(err => {
      ElMessage.error(err)
    })
  }).catch(() => {
    console.log('取消')
  })
}
const deleteOne = (id) => {
  axiosUtil.deleteTag(id).then(res => {
    if (res.data.code === 200) {
      ElMessage.success("删除成功")
      data.value = data.value.filter(item => item.id !== id)
    } else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err => {
    ElMessage.error(err)
  })
}
const editName = (id) => {
  ElMessageBox.prompt('请输入新的标签名称', '修改标签名称', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputErrorMessage: '输入异常',
  }).then(({value}) => {
    if (value == null || value.length == 0) {
      ElMessage.error("请输入新的标签名称")
      return
    }
    axiosUtil.editTagName(id, value).then(res => {
      if (res.data.code === 200) {
        ElMessage.success("修改成功")
        data.value = data.value.map(item => {
          if (item.id === id) {
            item.name = value
          }
          return item
        })
      } else {
        ElMessage.error(res.data.msg)
      }
    }).catch(err => {
      ElMessage.error("修改失败")
      console.log(err)
    })
  }).catch(() => {
    console.log('取消')
  })
}
const editMember = (id) => {
  router.push("editTagMember/"+id)
}
const handleClose = () => {
  dsp.value = false
}
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>