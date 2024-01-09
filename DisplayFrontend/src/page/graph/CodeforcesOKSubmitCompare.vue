<template>
  <el-card :shadow="false">
    <el-row>
      <el-col :span="18">
        <el-select-v2
            v-model="selected"
            filterable
            :options="options"
            placeholder="Please select"
            style="width: 100%"
            multiple
        ></el-select-v2>
      </el-col>
      <el-col :span="6">
        <el-button @click="load">加载</el-button>
      </el-col>
    </el-row>
  </el-card>
  <el-divider></el-divider>
  <el-card v-loading="loading">
    <div style="height: 150vh;width: 100%">
      <CodeforcesOKSubmitScatterGraph :fin="stuFin&&tagFin" :tags="tags" :student-map="studentMap" :ok-submit-list="submitList" ref="graph"></CodeforcesOKSubmitScatterGraph>
    </div>
  </el-card>

</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import CodeforcesOKSubmitScatterGraph from "../../components/infoComponents/Graphs/CodeforcesOKSubmitScatterGraph.vue";
let loading = ref(false)
let stuFin = ref(false)
let stuList = ref([])
let graph = ref(null)
let studentMap = computed(()=>{
  let map = {}
  stuList.value.forEach(item=>{
    map[item.id] = item
  })
  return map
})
let submitList = ref([])
let tags = ref([])
let tagFin = ref(false)
const selected = ref([])
let options = computed(()=>{
  return stuList.value.map((item)=>{
    return {
      value: item.id,
      label: item.classname + " " + item.realname
    }
  })
})
onMounted(()=>{
  axiosUtil.getAllUser().then(res => {
    if(res.data.code === 200) {
      stuList.value = res.data.result
      stuFin.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
    console.log(err)
  })
  axiosUtil.codeforcesAPI.getProblemTags().then(res=>{
    if(res.data.code===200) {
      tags.value = res.data.result
      tagFin.value = true
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常,标签获取失败")
  })
})
const load = ()=>{
  loading.value = true
  axiosUtil.codeforcesAPI.getOKSubmitByUids(selected.value.join(",")).then(res=>{
    if(res.data.code===200) {
      submitList.value = res.data.result
      graph.value.reload()
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
    console.log(err)
  }).finally(()=>{
    loading.value = false
  })
}
</script>

<style scoped>

</style>