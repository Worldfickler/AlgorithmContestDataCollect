<template>
  <el-dialog
    v-model="display"
    @close="close"
    title="申请收录新奖项"
    ref="formRef"
  >
    <el-form :model="data" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="年" prop="year">
        <div class="block">
          <el-date-picker
            value-format="YYYY"
            v-model="data.year"
            type="year"
            placeholder="请输入获奖年限"
          />
        </div>
      </el-form-item>
      <el-form-item label="比赛名称" prop="contestName">
        <el-input
          v-model="data.contestName"
          placeholder="请输入比赛名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="奖项" prop="level">
        <el-select v-model="data.level" class="m-2" placeholder="请选择奖项">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <!-- <el-input v-model="data.level" placeholder="请输入比赛名称"></el-input> -->
      </el-form-item>
      <el-form-item label="是否展示" prop="status">
        <el-radio-group v-model="data.status">
          <el-radio label="0">否</el-radio>
          <el-radio label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close()">取消</el-button>
        <el-button type="primary" @click="submit" :loading="loading"
          >提交</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, onMounted, ref, defineExpose } from "vue";
import { ElMessage } from "element-plus";
import axiosUtil from "../../utils/axiosUtil";
import simpleUtils from "../../utils/simpleUtils";
const options = ref([
  {
    value: "0",
    label: "省三",
  },
  {
    value: "1",
    label: "省二",
  },
  {
    value: "2",
    label: "省一",
  },
  {
    value: "3",
    label: "国三",
  },
  {
    value: "4",
    label: "国二",
  },
  {
    value: "5",
    label: "国一",
  },
  {
    value: "6",
    label: "铜奖",
  },
  {
    value: "7",
    label: "银奖",
  },
  {
    value: "8",
    label: "金奖",
  },
]);
let data = ref({
  year: "",
  contestName: "",
  level: "",
  status: "1",
});
let formRef = ref(null);
let loading = ref(false);
const display = ref(false);
let rules = reactive({
  contestName: [
    { required: true, message: "比赛名称不能为空", trigger: "blur" },
  ],
  year: [
    { required: true, message: "请输入获奖年限", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.isAfter2000(value)) {
          callback();
        } else {
          callback(new Error("请输入2000年以后的年限"));
        }
      },
      trigger: "blur",
    },
  ],
  level: [{ required: true, message: "你是不是忘了点啥?", trigger: "blur" }],
  status: [{ required: true, message: "请选择是否展示", trigger: "blur" }],
});
let submit = () => {
  let that = this;
  if (formRef.value === null) {
    return;
  }
  formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true;
      axiosUtil
        .savePrize(data.value)
        .then((res) => {
          if (res.data.code === 200) {
            ElMessage.success("提交成功");
            emit('submitSucc')
            close();
          } else {
            ElMessage.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("提交失败");
        })
        .finally(() => {
          loading.value = false;
        });
    }
  });
};
defineExpose({
  open() {
    display.value = true;
  },
});
let close = () => {
  data.value = reactive({
    year: "",
    contestName: "",
    level: "",
    status: "1",
  });
  display.value = false;
};
const emit = defineEmits({
    // 在这里需要声明一下事件名称，否则会报警告
    submitSucc: {},
})

</script>

<style scoped>
</style>