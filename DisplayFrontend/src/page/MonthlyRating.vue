<template>
  <el-card shadow="never">
    <el-row>
      <el-col :span="12">
        <el-row>
          <el-col :span="12">
            <el-select v-model="timeSpan.startYear" placeholder="请选择年份">
              <el-option
                  v-for="item in yearList"
                  :key="item"
                  :label="item"
                  :value="item"
              />
            </el-select>
          </el-col>
          <el-col :span="12">
            <el-select v-model="timeSpan.startMonth" placeholder="请选择月份">
              <el-option
                  v-for="(item) in monthList"
                  v-show="timeSpan.startYear !== new Date().getFullYear()||item <= new Date().getMonth() + 1"
                  :key="item"
                  :label="item"
                  :value="item"
              />
              <el-option
                  label="S1"
                  :value="-1"
              />
              <el-option
                  label="S2"
                  :value="-2"
              />
              <el-option
                  label="S3"
                  :value="-3"
              />
              <el-option
                label="S4"
                :value="-4"
              />
              <el-option
                  label="H1"
                  :value="-5"
              />
              <el-option
                  label="H2"
                  :value="-6"
              />
              <el-option
                  label="FullYear"
                  :value="-7"
              />
            </el-select>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="6">
        <el-select v-model="tagSelect" placeholder="标签选择">
          <el-option label="全体人员" :value="-1"></el-option>
          <el-option
              v-for="item in tagList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-checkbox v-model="displayAll">展示所有人员</el-checkbox>
      </el-col>
      <el-col :span="3">
        <el-button type="primary" @click="getMonthRating">查询</el-button>
      </el-col>
    </el-row>
  </el-card>
  <el-divider/>
  <el-table :data="pageData" v-loading="loading" border @sort-change="sortChange" @filter-change="filterChange">
    <el-table-column prop="id" label="用户id" fixed>
    </el-table-column>
    <el-table-column prop="username" label="用户名" fixed>
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="usernameFilter"></el-input>
      </template>
      <template #default="scope">
        <router-link :to="`/user/trainInfo/${scope.row.username}`">{{scope.row.username}}</router-link>
      </template>
    </el-table-column>
    <el-table-column prop="realname" label="真实姓名" fixed>
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="realnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="classname" label="班级" fixed>
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="classnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="year" label="年级" :filters="yearFilter"  column-key="year" fixed>
    </el-table-column>
    <el-table-column prop="school" label="学校" :filters="schoolFilter"  column-key="school" fixed>
      <template #default="scope">
        {{schoolMap[scope.row.school]}}
      </template>
    </el-table-column>
        <el-table-column prop="status" label="用户状态" :filters="statusFilter" column-key="status" :filtered-value="[1]">
      <template #default="scope">
        <div v-for="(item,index) in statusRule" :key="index">
          <el-tag   v-if="scope.row.status===index" :type="item.tag">
          {{item.msg}}
        </el-tag>
        </div>
      </template>
    </el-table-column>
    <el-table-column :label="headText.year + headText.month + 'Codeforce比赛数据'" align="center">
      <el-table-column label="Rating">
        <el-table-column prop="cfRating" label="当前cf积分" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="Diff">
        <el-table-column prop="cfRatingChange" label="cf积分变化数据" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="C">
        <el-table-column prop="cfParticipate" label="CF参与次数" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="P">
        <el-table-column prop="cfInContestSolve" label="cf比赛内解题数" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="PP">
        <el-table-column prop="cfAfterSolve" label="cf赛后补题数" sortable>
        </el-table-column>
      </el-table-column>

    </el-table-column>
    <el-table-column :label="headText.year + headText.month + 'Atcoder比赛数据'" align="center">
      <el-table-column label="Rating">
        <el-table-column prop="acRating" label="当前ac积分" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="Diff">
        <el-table-column prop="acRatingChange" label="ac积分变化数据" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="C">
        <el-table-column prop="acParticipate" label="ac参与次数" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="P">
        <el-table-column prop="acInContestSolve" label="ac比赛内解题数" sortable>
        </el-table-column>
      </el-table-column>
      <el-table-column label="PP">
        <el-table-column prop="acAfterSolve" label="ac赛后补题数" sortable>
        </el-table-column>
      </el-table-column>
    </el-table-column>
    <el-table-column prop="score" label="score" sortable fixed="right">
      <template #header="scope">
        <el-popover
            placement="top"
            title="积分计算方法"
            :width="500"
            trigger="hover"
            content="score = cfDiff + acDiff + (cfP + cfPP + acP + acPP)*5 + (cfRating + acRating)*0.1 + (cfC + acC)*20"
        >
          <template #reference>
            {{scope.column.label}}
          </template>
        </el-popover>
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination background layout="total, sizes ,prev, pager, next" :total="displayData.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import InfoUtils from "../utils/InfoUtils";
let headText = ref({
  year: "",
  month: ""
})
let flag =ref(true)//记录是否第一次加载（默认用户状态为启用）
const statusRule=ref([{"tag":"danger","msg":"禁用"},{"tag":"success","msg":"启用"},{"tag":"info","msg":"退役"}])
let pageSize = ref(10)
let data = ref({})
let userData = ref([])
let schoolMap = ref({})
let sortOrder = ref('descending')
let usernameFilter = ref('')
let classnameFilter = ref('')
let realnameFilter = ref('')
let currentPage = ref(1)
let pageData = computed(()=>{
  let start = (currentPage.value - 1) * pageSize.value
  let end = currentPage.value * pageSize.value
  return displayData.value.slice(start, end)
})
let displayAll = ref(false)
let filter = ref({})
let schoolFilter = computed(() => {
  let res = []
  for (let key in schoolMap.value) {
    res.push({
      text: schoolMap.value[key],
      value: Number.parseInt(key)
    })
  }
  console.log(res)
  return res
})
let statusFilter = computed(() => {
  let res=[]
  for (let key in statusRule.value) {
    res.push({
      text: statusRule.value[key].msg,
      value: Number.parseInt(key)
    })
  }
  // console.log(res)
  return res
})
let timeSpan = ref({
  startYear: new Date().getFullYear(),
  startMonth: new Date().getMonth() + 1,
})
let yearFilter = computed(() => {
  let yearList = []
  for(let item of userData.value) {
    if(!yearList.includes(item.year)) {
      yearList.push(item.year)
    }
  }
  let ret = []
  for (let year of yearList.sort((a, b) => b - a)) {
    ret.push({text : "" + year,value: year})
  }
  return ret
})
let req = null
let displayData = computed(()=>{
  if(userData.value.length==0)
    return [];
  if(flag.value){
    filter.value={
      'status':[1]
    };
    flag.value=false;
  }
  let userDataMap = {}
  userData.value.forEach(item => {
    userDataMap[item.id] = item
  })
  let ret = []
  for(let key in data.value){
    if(userDataMap.hasOwnProperty(key)){
      ret.push({...data.value[key],...userDataMap[key]})
    }
  }
  console.log(ret)
  if (sortOrder.value === 'descending') {
    ret.sort((a, b) => {
      return b[sortProp.value]-a[sortProp.value]
    })
  } else {
    ret.sort((a, b) => {
      return a[sortProp.value]-b[sortProp.value]
    })
  }
  if(displayAll.value === false) {
    let thisYear = (new Date()).getFullYear()
    let thisMonth = (new Date()).getMonth();
    ret = ret.filter((a)=>{
      if(thisMonth >= 6){
        return thisYear - a.year < 4
      }else{
        return thisYear - a.year <= 4
      }

    })
  }
  ret = ret.filter(item => {
    if (usernameFilter.value !== '' && !item.username.includes(usernameFilter.value)) {
      return false
    }
    if (realnameFilter.value !== '' && !item.realname.includes(realnameFilter.value)) {
      return false
    }
    if (classnameFilter.value !== '' && !item.classname.includes(classnameFilter.value)) {
      return false
    }
    let allowRet = true
    for (let key in filter.value) {
      if (filter.value[key].length > 0 && !filter.value[key].includes(item[key])) {
        allowRet = false
      }
    }
    return allowRet
  })
  return ret
})
let yearList = ref([])
let monthList = ref([])
let tagList = ref([])
let tagSelect = ref(-1)
let sortProp = ref("score")
let loading  = ref(false)
onMounted(() => {
  monthList.value = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  for (let i = 0; i < 4; i++) {
    yearList.value.push(new Date().getFullYear() - i)
  }
  InfoUtils.getSchoolMap().then(res => {
    schoolMap.value = res.schoolMap
  })
  axiosUtil.getTagList().then(res => {
    if (res.data.code === 200) {
      tagList.value = res.data.result
    } else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err => {
    ElMessage.error("网络错误")
    console.log(err)
  })
  axiosUtil.getAllUser().then(res =>{
    if (res.data.code === 200) {
      userData.value = res.data.result
    } else {
      ElMessage.error(res.data.msg)
    }
  }).catch(err => {
    ElMessage.error("网络错误")
    console.log(err)
  })
  getMonthRating()
})

let getMonthRating = () => {
  let startMonth = new Date();
  let endMonth = new Date();
  startMonth.setFullYear(timeSpan.value.startYear)
  startMonth.setUTCDate(1)
  startMonth.setHours(0,0,0,0)
  endMonth.setFullYear(timeSpan.value.startYear)
  endMonth.setHours(23,59,59,999)
  let head = {
    year: timeSpan.value.startYear.toString() + "年",
    month: ""
  }
  switch (timeSpan.value.startMonth) {
    case -1 :
    {
      startMonth.setMonth(0)
      endMonth.setMonth(2)
      head.month = "1-3月"
      break

    }
    case -2 :
    {
      startMonth.setMonth(3)
      endMonth.setMonth(5)
      head.month = "4-6月"
      break
    }
    case -3 :{
      startMonth.setMonth(6)
      endMonth.setMonth(8)
      head.month = "7-9月"
      break
    }
    case -4 :{
      startMonth.setMonth(9)
      endMonth.setMonth(11)
      head.month = "10-12月"
      break
    }
    case -5 :{
      startMonth.setMonth(0)
      endMonth.setMonth(5)
      head.month = "1-6月"
      break
    }
    case -6:{
      startMonth.setMonth(6)
      endMonth.setMonth(11)
      head.month = "7-12月"
      break
    }
    case -7 :{
      startMonth.setMonth(0)
      endMonth.setMonth(11)
      head.month = "全年"
      break
    }
    default: {
      startMonth.setMonth(timeSpan.value.startMonth - 1)
      endMonth.setMonth(timeSpan.value.startMonth - 1)
      head.month = timeSpan.value.startMonth + "月"
      break
    }
  }
  let arr = [31,28,31,30,31,30,31,31,30,31,30,31]
  endMonth.setUTCDate(arr[endMonth.getMonth()])
  if(endMonth.getMonth() === 1 && (endMonth.getFullYear() %400 ===0||(endMonth.getFullYear()%100!==0&&endMonth.getFullYear()%4 === 0))){
    endMonth.setUTCDate(29)
  }
  let startStamp = Math.floor(startMonth.getTime()/1000)
  let endStamp = Math.floor(endMonth.getTime()/1000);
  if(req){
    req.cancel()
    loading.value = false
  }
  if (tagSelect.value === -1) {
    req = axiosUtil.getSpanMonthlyRating(startStamp, endStamp)
  } else {
    req = axiosUtil.getTagSpanMonthlyRating(tagSelect.value,startStamp, endStamp)
  }
  req.then(res=>{
    if(res.data.code === 200) {
      data.value = res.data.result
      headText.value = head
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络错误")
    console.log(err)
  }).finally(()=>{
    req = null
    loading.value = false
  })
  loading.value = true
}
const sortChange = (prop) => {
  sortProp.value = prop.prop
  sortOrder.value = prop.order
}
const filterChange = (prop) => {
  filter.value = {...filter.value,...prop}
}
</script>

<style scoped lang="scss">
  .pag {
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>