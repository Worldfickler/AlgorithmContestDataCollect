<script setup>
import { onMounted, reactive , ref} from "vue";
import { ElMessage, ElMessageBox } from 'element-plus'
import api from "../utils/axiosUtil"
import InfoUtils from "../utils/InfoUtils";
let schoolList = ref([])
onMounted(()=>{
    InfoUtils.getSchoolMap().then((value)=>{
        schoolList.value = value.schoolList
    })
})
let addOne = ()=>{
    ElMessageBox.prompt('请输入学校名称', '新增学校', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputErrorMessage: '输入异常',
  }).then(({ value }) => {
    if (value == null || value.length == 0) {
        ElMessage.error("请输入学校名称")
        return
    }
    api.addSchool(value).then(res => {
        if (res.data.code === 200) {
            ElMessage.success("添加成功")
            schoolList.value.push(res.data.result)
            window.localStorage.setItem("schoolList", JSON.stringify(schoolList.value))
            window.localStorage.setItem("schoolListExpire", Date.now() + 1000 * 60 * 60 )
        }else{
            ElMessage.error(res.data.msg)
        }
    }).catch(err => {
        ElMessage.error(err)
    })
  }).catch(() => {
    console.log('取消')
  })
}
let deleteOne = (id)=>{
    api.deleteSchool(id).then(res=>{
        if (res.data.code === 200) {
            ElMessage.success("删除成功")
            schoolList.value = schoolList.value.filter(item => item.id != id)
            window.localStorage.setItem("schoolList", JSON.stringify(schoolList.value))
            window.localStorage.setItem("schoolListExpire", Date.now() + 1000 * 60 * 60 )
        }else{
            ElMessage.error(res.data.msg)
        }
    }).catch(err=>{
        ElMessage.error(err)
    })
}
</script>
<template>
<el-table :data="schoolList" border>
    <el-table-column fixed prop="id" label="Id" align="center" />
    <el-table-column fixed prop="name" label="Name" align="center" />
    <el-table-column fixed align="center" >
        <template #header>
            <el-button type="success" @click="addOne">增加</el-button>
        </template>
        <template #default="scope">
            <el-button type="danger" @click="deleteOne(scope.row.id)">删除</el-button>
        </template>
    </el-table-column>
</el-table>
</template>

<style>

</style>

