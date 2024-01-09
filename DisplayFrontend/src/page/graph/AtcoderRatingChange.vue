<template>
  <div id="container">
    <RatingChangeGraph :render-data="AtcoderRatingChangeData" :user-info="userInfo" :data-finished="RatingChangeDataFinished&&userInfoFinished"  type="ac"></RatingChangeGraph>
  </div>

</template>

<script setup>
import {onBeforeMount, onMounted, ref} from "vue";
import RatingChangeGraph from "../../components/infoComponents/Graphs/RatingChangeGraph.vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import InfoUtils from "../../utils/InfoUtils";

let AtcoderRatingChangeData = ref({})
let userInfo = ref({})
let userInfoFinished = ref(false)

let RatingChangeDataFinished = ref(false)
onBeforeMount(()=>{
  InfoUtils.getSchoolMap().then(value => {
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
  axiosUtil.atcoderAPI.getAtcoderMainAccountRatingChange().then(res=>{
    if(res.data.code === 200) {
      AtcoderRatingChangeData.value = res.data.result
      RatingChangeDataFinished.value = true
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("获取Atcoder积分变化失败")
    console.log(err)
  })

})
</script>

<style scoped lang="scss">
 #container {
  width: 100%;
  height: calc(100vh - 60px);
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>