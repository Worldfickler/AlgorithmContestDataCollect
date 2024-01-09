<template>
  <el-table :data="data">
    <el-table-column prop="id" label="id">
    </el-table-column>
    <el-table-column prop="name" label="名称">
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button @click="router.push('/graph/tag/'+scope.row.id)">查看</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
const router = useRouter()
const data = ref([])

onMounted(() => {
  axiosUtil.getTagList().then(res => {
    if (res.data.code === 200) {
      data.value = res.data.result
    } else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err => {
    ElMessage.error("获取标签列表失败")
    console.log(err)
  })
})
</script>

<style scoped>

</style>