<script setup>
import {onBeforeMount, onMounted,reactive,ref} from "vue";
import InfoUtils  from "../utils/InfoUtils";
import {ElMessage, ElMessageBox} from "element-plus";
import api from "../utils/axiosUtil";
import jsSHA from "jssha";
let display = ref(false)
let newUser = reactive({
    username:"",
    password:"",
    school:null,
    isSuper:0,
})
let loading = ref(false)
let schoolList = ref([])
let schoolMap = ref({})
let managerList = ref([])
onMounted(()=>{
    api.getManagerList().then(res=>{
        if (res.data.code === 200) {
            managerList.value = res.data.result
        }else{
            console.log(res)
        }
    }).catch(err=>{
        console.log(err)
    })
  InfoUtils.getSchoolMap().then((value)=>{
    schoolList.value = value.schoolList
    schoolMap.value = value.schoolMap
  })

})

let modifyManagerPassword = (row)=>{
    ElMessageBox.prompt('请输入新密码', '修改密码', {
    inputType: 'password',
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputErrorMessage: '输入异常',
  }).then(({ value }) => {
    
    if (value == null || value.length == 0) {
        ElMessage.error("请输入新密码")
        return
    }
    const shaObj = new jsSHA("SHA-512","TEXT",{encoding:"UTF8"})
    shaObj.update(value)
    let encodePassword = shaObj.getHash('HEX')
    api.modifyManagerPassword(row.id,encodePassword).then(res => {
        if (res.data.code === 200) {
            ElMessage.success("修改成功")
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
let deleteManager = (row) => {
    ElMessageBox.confirm('确定删除该管理员吗？', '删除管理员', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        api.deleteManager(row.id).then(res => {
            if (res.data.code === 200) {
                ElMessage.success("删除成功")
                managerList.value = managerList.value.filter(item => item.id != row.id)
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
let addManager = ()=>{
    display.value = true
}
let cancelAdd = (done)=>{
    display.value = false
    while(loading.value){}
    newUser.username = ""
    newUser.password = ""
    newUser.isSuper = 0
    newUser.school = null
    done()
}
let confirmAdd = ()=>{
    if (newUser.username.length == 0) {
        ElMessage.error("请输入用户名")
        return
    }
    if (newUser.password.length == 0) {
        ElMessage.error("请输入密码")
        return
    }
    if (newUser.school == null) {
        ElMessage.error("请选择学校")
        return
    }
    loading.value = true
    const shaObj = new jsSHA("SHA-512","TEXT",{encoding:"UTF8"})
    shaObj.update(newUser.password)
    let encodePassword = shaObj.getHash('HEX')
    api.addManager(newUser.username,encodePassword,newUser.school,newUser.isSuper).then(res => {
        if (res.data.code === 200) {
            ElMessage.success("添加成功")
            managerList.value.push(res.data.result)
            display.value = false
            newUser.username = ""
            newUser.password = ""
            newUser.isSuper = 0
            newUser.school = null
        }else{
            console.log(res)
            ElMessage.error(res.data.msg)
        }
        loading.value = false
    }).catch(err => {
        ElMessage.error("添加失败")
        console.log(err)
        loading.value = false
    })
    display.value = false
}
</script>
<template>
<el-table :data="managerList">
    <el-table-column label="id" prop="id" width="100"></el-table-column>
    <el-table-column label="用户名" prop="username"></el-table-column>
    <el-table-column label="权限" prop="position">
        <template #default="scope">
            <el-tag v-if="scope.row.isSuper===1" type="danger">
                超级管理员
            </el-tag>
            <el-tag v-else type="success">
                {{schoolMap[scope.row.school]}}
            </el-tag>
        </template>
    </el-table-column>
    <el-table-column label="操作" prop="id" width="180">
        <template #header="scope">
            <el-button type="primary" link size="small" @click="addManager">新增管理员</el-button>
        </template>
        <template #default="scope">
            <el-button type="primary" link size="small" @click="modifyManagerPassword(scope.row)">修改密码</el-button>
            <el-button type="primary" link size="small" @click="deleteManager(scope.row)">删除</el-button>
        </template>
    </el-table-column>
</el-table>
<el-dialog v-model="display" title="新增管理员" :before-close="cancelAdd">
    <el-form :model="newUser">
        <el-form-item label="username">
            <el-input v-model="newUser.username" ></el-input>
        </el-form-item>
        <el-form-item label="password">
            <el-input v-model="newUser.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="学校">
            <el-select v-model="newUser.school" placeholder="请选择学校">
                <el-option v-for="item in schoolList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="超级管理员">
            <el-radio-group v-model="newUser.isSuper">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
            </el-radio-group>
        </el-form-item>
    </el-form>
     <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelAdd(()=>{})" :loading="loading">取消</el-button>
        <el-button type="primary" @click="confirmAdd()" :loading="loading">确定</el-button>
      </span>
    </template>
</el-dialog>
</template>