<template>
  <el-card :shadow="false">
    <el-row>
      <el-col :span="12">
        <el-row>
          <el-col :span="6">
            <el-select v-model="typeSelect" placeholder="标签选择" @change="change">
              <el-option label="Codeforces" :value="1"></el-option>
              <el-option label="Atcoder" :value="2"></el-option>
            </el-select>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-card>
  <div style="width: 100%;">
    <RecentContestParticipateGraph :data="data" :type="typeSelect === 1 ? 'cf':'ac'" :data-fin="dataFin"></RecentContestParticipateGraph>
  </div>
</template>

<script setup>
import RecentContestParticipateGraph from "../../components/infoComponents/Graphs/RecentContestParticipateGraph.vue";
import {onMounted,ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
let typeSelect = ref(1)
let data = ref([])
let dataFin = ref(false)
onMounted(()=>{
  load()
})
let change = () => {
  load()
}
let load = ()=> {
  dataFin.value = false
  if(typeSelect.value === 1) {
    axiosUtil.codeforcesAPI.getCodeforcesContestList().then(res=>{
      if(res.data.code === 200) {
        data.value = res.data.result.sort((a,b)=>{
          return b.startTimeStamp - a.startTimeStamp
        })
        dataFin.value = true
      }
    })
  }else {
    axiosUtil.atcoderAPI.getAtcoderContestList().then(res=>{
      if(res.data.code === 200) {
        data.value = res.data.result.sort((a,b)=>{
          return b.startTimeStamp - a.startTimeStamp
        })
        dataFin.value = true
      }
    })
  }

}
</script>

<style scoped>

</style>