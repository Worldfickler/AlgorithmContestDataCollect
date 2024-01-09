<template>
  <div id="container">
    <RatingChangeGraph :render-data="CodeforcesRatingChangeData" :user-info="userInfo" :data-finished="RatingChangeDataFinished&&userInfoFinished"  type="cf"></RatingChangeGraph>
  </div>

</template>

<script setup>
import {onBeforeMount, onMounted, ref} from "vue";
import RatingChangeGraph from "../../components/infoComponents/Graphs/RatingChangeGraph.vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import infoUtils from "../../utils/InfoUtils";

let CodeforcesRatingChangeData = ref({})
let userInfo = ref({})
let userInfoFinished = ref(false)
let schoolMap = ref({})
let RatingChangeDataFinished = ref(false)
onBeforeMount(()=>{
  infoUtils.getSchoolMap().then(value=>{
    axiosUtil.getAllUser().then(res=>{
      if(res.data.code === 200) {
        let obj = {}
        for(let i = 0; i < res.data.result.length; i++) {
          res.data.result[i].school = value.schoolMap[res.data.result[i].school]
          obj[res.data.result[i].id] = res.data.result[i]
        }
        userInfo.value = obj
        userInfoFinished.value = true
      }else{
        ElMessage.error(res.data.msg)
      }
    }).catch(err=>{
      ElMessage.error("获取用户信息失败")
      console.log(err)
    })
  })

  axiosUtil.codeforcesAPI.getCodeforcesMainAccountRatingChange().then(res=>{
    if(res.data.code === 200) {
      CodeforcesRatingChangeData.value = res.data.result
      RatingChangeDataFinished.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("获取Codeforces积分变化失败")
    console.log(err)
  })

})
</script>

<style scoped lang="scss">
 #container {
  width: 100%;
  height: calc(100vh - 60px) ;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>