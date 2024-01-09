<template>
  <el-card v-loading="loading" >
    <div ref="chart" style="width: 100%;height: 30vh">
    </div>
  </el-card>
</template>

<script setup>
import {nextTick, onMounted, ref, watchEffect} from "vue";
import simpleUtils from "../../../utils/simpleUtils";
import {ElMessage} from "element-plus";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
let chart = ref(null)
let loading = ref(true)
let hasRender = ref(false)
let renderData = ref({})
let data = ref()
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
const prop = defineProps({
  type:{
    type:String,
    required:true
  },
  data:{
    type:Object,
    required: true
  }

})
let render = () => {
  var option = {
    tooltip: {
      trigger: 'axis',
      position: function (pt) {
        return [pt[0], '10%'];
      }
    },
    title: {
      left: 'center',
      text: '积分变化'
    },
    dataZoom: [
      {
        type: 'inside',
        start: 0,
        end: 20
      },
      {
        start: 0,
        end: 20
      }
    ],
    yAxis: {
      type: 'value'
    },
  };
  let minTime = new Date().getTime()
  for(let key in renderData.value) {
    renderData.value[key] = renderData.value[key].sort((a,b) => {
      return a.startTimeStamp - b.startTimeStamp
    })
    if(renderData.value[key].length > 0) {
      minTime = Math.min(minTime,renderData.value[key][0].startTimeStamp*1000)
    }
  }
  let arr = simpleUtils.generateMonthArray(minTime,new Date().getTime())

  let xData = []
  for(var item of arr) {
    xData.push(item.year + "-" + item.month)
  }
  let series = [
  ]
  for(let key in renderData.value) {
    let data = []
    let nowRating = 0
    let i = 0
    for(let item of arr) {
      while(i!==renderData.value[key].length) {
        let nt = new Date(renderData.value[key][i].startTimeStamp*1000)
        if(nt.getUTCFullYear() <= item.year && nt.getMonth()+1 <= item.month) {
          if(renderData.value[key][i].diff !== 0) {
            nowRating = renderData.value[key][i].rating
          }
          i++
        }else{
          break
        }
      }
      data.push(nowRating)
    }
    series.push({
      data: data,
      type: 'line',
      smooth:true,
      name: key
    })
  }
  let e = echarts.init(chart.value)
  e.setOption({
    ...option,
    xAxis: {
      type: 'category',
      data: xData
    },
    series: series
  })
  closer.value = () => {
    e.resize();
  }
  loading.value = false
  hasRender.value = true
}
watchEffect(()=>{
  if(prop.data) {
    if (renderData.value !== prop.data) {
      renderData.value = prop.data
      nextTick(() => {
        render()
      })
    }
  }
})
</script>

<style scoped>

</style>