<template>

  <el-dialog v-model="display" title="" :close-on-click-modal="false" @close="close" >
    <el-input @input="onQueryChanged" v-model="query"></el-input>
    <el-checkbox v-model="displayAll" @change="displayAllChange">显示全部用户</el-checkbox>
    <el-tree-v2 :data="stuInfoList" :props="treeProps" show-checkbox check-strictly ref="tree" v-loading="loading" :height="500" :filter-method="filterMethod"></el-tree-v2>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="submit" v-loading="loading">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted, watchEffect, onUpdated, computed, nextTick} from "vue";
import axiosUtil from "../utils/axiosUtil";
import InfoUtils from "../utils/InfoUtils";
import {ElMessage} from "element-plus";
const tree = ref(null)
const query = ref("")
const displayAll = ref(false)
const emit = defineEmits(['handleClose'])
const tid = ref(-1)
let displayAllChange = () => {
  nextTick(()=>{
    tree.value.setCheckedKeys([])
    for(let r of nowTagStuList.value) {
      tree.value.setChecked(r.uid,true)
    }
  })
}
const prop = defineProps({
  stuList: {
    type: Array,
    default: []
  }
})
const treeProps = ref({
  value: 'id',
  label: 'label',
  children: 'children',
})
const loading = ref(true)
const display = ref(false)
const schoolMap = ref({})
const nowTagStuList = ref([])
onMounted(()=>{
  InfoUtils.getSchoolMap().then((res)=>{
    schoolMap.value = res.schoolMap
  })
})
defineExpose({
  open(id){
  loading.value = true
    display.value = true
    tid.value = id
    axiosUtil.getTagStuList(id).then(res=>{
      if(res.data.code === 200) {
        nowTagStuList.value = res.data.result
        nextTick(()=>{
          for(let r of res.data.result) {
            tree.value.setChecked(r.uid,true)
          }
        })
        loading.value = false
      }else{
        ElMessage.error(res.data.msg)
      }
    }).catch(err=>{
      console.log(err)
      ElMessage.error("获取标签学生列表失败")
    })
  }})
let close = ()=>{
  display.value = false
  emit("handleClose")
}
let stuInfoList = computed(()=>{
  let retobj = {}
  let thisYear = (new Date()).getFullYear()
  let thisMonth = (new Date()).getMonth();
  let dsp = prop.stuList
  if(!displayAll.value){
    dsp = dsp.filter((a)=>{
      if(thisMonth >= 6){
        return thisYear - a.year < 4
      }else{
        return thisYear - a.year <= 4
      }
    })
  }
  for(let stu of dsp){
    if (retobj[schoolMap.value[stu.school]] === undefined){
      retobj[schoolMap.value[stu.school]] = {}
    }
    if(retobj[schoolMap.value[stu.school]][stu.year] === undefined) {
      retobj[schoolMap.value[stu.school]][stu.year] = {}
    }
    if(retobj[schoolMap.value[stu.school]][stu.year][stu.classname] === undefined) {
      retobj[schoolMap.value[stu.school]][stu.year][stu.classname] = []
    }
    retobj[schoolMap.value[stu.school]][stu.year][stu.classname].push({id:stu.id,label:stu.stuNo + "-" + stu.realname})
  }
  console.log(retobj)
  let ret = []
  for(let school in retobj){
    let schoolObj = {}
    schoolObj.label = school
    schoolObj.id = school
    schoolObj.disabled = true
    schoolObj.children = []
    for(let year in retobj[school]){
      let yearObj = {}
      yearObj.label = year
      yearObj.id = school +"-" +year
      yearObj.disabled = true
      yearObj.children = []
      for(let className in retobj[school][year]){
        let classObj = {}
        classObj.label = className
        classObj.id = school +"-" +year + "-" + className
        classObj.disabled = true
        classObj.children = []
        for(let stu of retobj[school][year][className]){
          let stuObj = {}
          stuObj.label = stu.label
          stuObj.id = stu.id
          classObj.children.push(stuObj)
        }
        yearObj.children.push(classObj)
      }
      schoolObj.children.push(yearObj)
    }
    ret.push(schoolObj)
  }
  console.log(ret)
  return ret;
})
const onQueryChanged = (query) => {
  console.log(query)
  tree.value.filter(query)
}
const filterMethod = (query, node) => {
  return node.label.includes(query)
}
const submit = () =>{
  let keys = tree.value.getCheckedKeys()
  loading.value = true
  axiosUtil.setTagStuList(tid.value,keys).then(res=>{
    if(res.data.code === 200) {
      ElMessage.success("设置成功")
      close()
    }else{
      ElMessage.error(res.data.msg)
    }
    loading.value = false
  }).catch(err=>{
    ElMessage.error("设置失败")
    console.log(err)
    loading.value = false
  })
}
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>