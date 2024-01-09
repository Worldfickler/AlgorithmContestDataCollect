<template>
  <div class="container">
    <el-table :data="data">
      <el-table-column prop="id" label="id" fixed align="center">
      </el-table-column>
      <el-table-column prop="year" label="年" fixed align="center">
      </el-table-column>
      <el-table-column prop="contestName" label="比赛名称" fixed align="center">
      </el-table-column>
      <el-table-column prop="level" label="奖项名称" fixed align="center">
        <template #default="scope">
          <el-tooltip
            class="box-item"
            effect="dark"
            :content="prizeRule[scope.row.level]"
            placement="top"
          >
            <svg-icon :name="prizeRule[scope.row.level]"></svg-icon>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="是否展示" fixed align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status === '1'" type="success"> 是 </el-tag>
          <el-tag v-else type="info"> 否 </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" v-if="prop.username === ''">
        <template #header="scope">
          <el-button type="primary" @click="handleAdd()">添加新奖项</el-button>
        </template>
        <template #default="scope">
          <el-button
            v-if="scope.row.status === '0'"
            @click="setStatus(scope.row)"
          >
            设置可见</el-button
          >
          <el-button
            v-if="scope.row.status === '1'"
            @click="setStatus(scope.row)"
          >
            设置不可见</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
  <AddPrize ref="formRef" @submitSucc="submitSucc"></AddPrize>
</template>

<script setup>
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import infoUtils from "../utils/InfoUtils";
import axiosUtil from "../utils/axiosUtil";
import { ElMessage } from "element-plus";
import AddPrize from "./ApplicationComponents/AddPrize.vue";
const prizeRule = ref({
  0: "省三",
  1: "省二",
  2: "省一",
  3: "国三",
  4: "国二",
  5: "国一",
  6: "铜奖",
  7: "银奖",
  8: "金奖",
});
const router = useRouter();
const prop = defineProps({
  username: {
    type: String,
    default: "",
  },
});
let formRef = ref(null);
let data = ref([]);
onMounted(() => {
  if (prop.username === "") {
    if (infoUtils.isLogin()) {
      axiosUtil.getUserPrize().then((res) => {
        if (res.data.code === 200) {
          data.value = res.data.result;
        }
      });
    } else {
      router.push("/login");
    }
  } else {
  }
});
let setStatus = (row) => {
  row.status = row.status === "1" ? "0" : "1";
  axiosUtil
    .updateUserPrize(row)
    .then((res) => {
      if (res.data.code === 200) {
        ElMessage.success("修改成功");
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      ElMessage.error("修改失败");
    });
};
let handleAdd = () => {
  if (!formRef) return;
  formRef.value.open();
};
let submitSucc = () => {
  axiosUtil.getUserPrize().then((res) => {
    if (res.data.code === 200) {
      data.value = res.data.result;
    }
  });
};
</script>

<style scoped>
</style>