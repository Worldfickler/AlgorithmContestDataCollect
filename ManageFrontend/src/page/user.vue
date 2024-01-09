<template>
  <el-dropdown
    split-button
    type="primary"
    :disabled="batchNoShow"
    style="float: left"
  >
    批量设置用户状态
    <template v-slot:dropdown>
      <el-dropdown-menu>
        <el-dropdown-item
          v-for="(status, index) in userStatus"
          :key="index"
          @click="updateUsersStatus(index)"
          >{{ status }}</el-dropdown-item
        >
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  <el-table
    :data="pageData"
    border
    @filter-change="filterChange"
    ref="multipleTable"
    @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column label="id" prop="id" width="100"></el-table-column>
    <el-table-column label="用户名" prop="username">
      <template #header="scope">
        {{ scope.column.label }}
        <el-input v-model="usernameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column label="真实姓名" prop="realname">
      <template #header="scope">
        {{ scope.column.label }}
        <el-input v-model="realnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      label="学校"
      prop="school"
      :filters="schoolFilter"
      column-key="school"
    >
      <template #default="scope">
        {{ schoolMap[scope.row.school] }}
      </template>
    </el-table-column>
    <el-table-column label="学号" prop="stuNo">
      <template #header="scope">
        {{ scope.column.label }}
        <el-switch v-model="duplicateFilter" active-text="查重"> </el-switch>
      </template>
    </el-table-column>
    <el-table-column
      label="年级"
      prop="year"
      :filters="yearFilter"
      column-key="year"
    ></el-table-column>
    <el-table-column label="班级" prop="classname">
      <template #header="scope">
        {{ scope.column.label }}
        <el-input v-model="classnameFilter"></el-input>
      </template>
      <template #default="scope">
        {{ scope.row.classname }}
      </template>
    </el-table-column>
    <el-table-column label="用户状态" prop="statys">
      <template #default="scope">
        <!-- <el-tag v-if="scope.row.status===1" type="success">
          启用
        </el-tag>
         -->
        <el-radio-group
          v-model="scope.row.status"
          @change="updateUserStatus(scope.row)"
        >
          <el-radio-button :label="0">禁用</el-radio-button>
          <el-radio-button :label="1">启用</el-radio-button>
          <el-radio-button :label="2">退役</el-radio-button>
        </el-radio-group>
      </template>
    </el-table-column>
    <el-table-column label="操作" prop="id" width="300">
      <template #header="scope">
        <el-button type="primary" link size="small" @click="addUser"
          >新增用户</el-button
        >
        <el-button @click="goToAddUserBatch">批量新增用户</el-button>
        <!-- <el-button size="mini" @click="deletes">批量设置用户状态</el-button> -->
        <!--        <el-button type="primary" link size="small" @click="addUserBatch">批量新增用户</el-button>-->
      </template>
      <template #default="scope">
        <el-button
          size="small"
          @click="modifyUserPassword(scope.row)"
          type="info"
          >修改密码</el-button
        >
        <el-button
          size="small"
          @click="modifyUserInfo(scope.row)"
          type="primary"
          >编辑</el-button
        >
        <el-button size="small" @click="deleteUser(scope.row)" type="danger"
          >删除</el-button
        >
      </template>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="dspData.length"
          v-model:page-size="pageSize"
          v-model:current-page="currentPage"
        >
        </el-pagination>
      </div>
    </template>
  </el-table>
  <add-user-form ref="form" @reload="reload"></add-user-form>
  <change-user-password-form
    :uid="changePasswordUid"
    ref="changePasswordForm"
  ></change-user-password-form>
  <NormalUserInfoEditModal
    @editSuccess="edisSuccess"
    ref="ModifyUserInfoModal"
  ></NormalUserInfoEditModal>
</template>

<script setup>
import { toRaw } from "@vue/reactivity";
import { computed, onMounted, ref } from "vue";
import axiosUtil from "../utils/axiosUtil";
import { ElMessage, genFileId } from "element-plus";
import InfoUtils from "../utils/InfoUtils";
import simpleUtils from "../utils/simpleUtils";
import { useRouter } from "vue-router";
import AddUserForm from "../components/addUserForm.vue";
import ChangeUserPasswordForm from "../components/changeUserPasswordForm.vue";
import NormalUserInfoEditModal from "../components/NormalUserInfoEditModal.vue";
let userStatus = ref(["禁用", "启用", "退役"]);
let batchNoShow = ref(true);
let batchUpdateStatusUsers = ref([]);
let usernameFilter = ref("");
let realnameFilter = ref("");
let classnameFilter = ref("");
let duplicateFilter = ref(false);
let currentPage = ref(1);
let pageSize = ref(10);
const userList = ref([]);
const loading = ref(false);
const schoolMap = ref({});
const schoolList = ref([]);
const upload = ref();
let router = useRouter();
let form = ref(null);
let changePasswordUid = ref(0);
let changePasswordForm = ref(null);
let ModifyUserInfoModal = ref(null);
let yearFilter = computed(() => {
  let yearList = [];
  for (let item of userList.value) {
    if (!yearList.includes(item.year)) {
      yearList.push(item.year);
    }
  }
  let ret = [];
  for (let year of yearList.sort((a, b) => b - a)) {
    ret.push({ text: "" + year, value: year });
  }
  return ret;
});
let schoolFilter = computed(() => {
  let res = [];
  for (let key in schoolMap.value) {
    res.push({
      text: schoolMap.value[key],
      value: Number.parseInt(key),
    });
  }
  return res;
});

let dspData = computed(() => {
  let retData = userList.value;
  retData = retData.filter((item) => {
    if (
      usernameFilter.value !== "" &&
      !item.username.includes(usernameFilter.value)
    ) {
      return false;
    }
    if (
      realnameFilter.value !== "" &&
      !item.realname.includes(realnameFilter.value)
    ) {
      return false;
    }
    if (
      classnameFilter.value !== "" &&
      !item.classname.includes(classnameFilter.value)
    ) {
      return false;
    }
    let allowRet = true;
    for (let key in filter.value) {
      if (
        filter.value[key].length > 0 &&
        !filter.value[key].includes(item[key])
      ) {
        allowRet = false;
      }
    }
    return allowRet;
  });
  if (duplicateFilter.value) {
    var stuNos = retData.map((cur) => cur.stuNo);
    var uniqueArr = [...new Set(stuNos)];
    if (uniqueArr.length !== retData.length) {
      retData = retData.filter(
        (item, index) =>
          stuNos.indexOf(item.stuNo) !== stuNos.lastIndexOf(item.stuNo)
      );
      retData.sort((a, b) => b.stuNo - a.stuNo);
    } else return [];
  }
  return retData;
});
let pageData = computed(() => {
  return dspData.value.slice(
    (currentPage.value - 1) * pageSize.value,
    currentPage.value * pageSize.value
  );
});
let reload = () => {
  axiosUtil
    .getStudentList()
    .then((res) => {
      if (res.data.code === 200) {
        userList.value = res.data.result;
      } else {
        ElMessage.error(res.data.msg);
      }
      // console.log(userList);
    })
    .catch((err) => {
      ElMessage.error("获取用户列表失败");
      console.log(err);
    })
    .finally(() => {
      loading.value = false;
    });
};
onMounted(() => {
  axiosUtil
    .getStudentList()
    .then((res) => {
      if (res.data.code === 200) {
        userList.value = res.data.result;
      } else {
        ElMessage.error(res.data.msg);
      }
      // console.log(userList);
    })
    .catch((err) => {
      ElMessage.error("获取用户列表失败");
      console.log(err);
    })
    .finally(() => {
      loading.value = false;
    });
  InfoUtils.getSchoolMap().then((value) => {
    schoolMap.value = value.schoolMap;
    schoolList.value = value.schoolList;
  });
});
let goToAddUserBatch = () => {
  router.push("/addUserBatch");
};
let modifyUserPassword = (row) => {
  changePasswordUid.value = row.id;
  changePasswordForm.value.open();
};
let edisSuccess = (uid, newInfo) => {
  userList.value.forEach((value) => {
    if (value.id === uid) {
      value.realname = newInfo.realname;
      value.stuNo = newInfo.stuNo;
      value.year = newInfo.year;
      value.classname = newInfo.classname;
    }
  });
};
let modifyUserInfo = (row) => {
  ModifyUserInfoModal.value.open(row.id, { ...row });
};
let deleteUser = (row) => {
  axiosUtil
    .deleteUser(row.id)
    .then((res) => {
      if (res.data.code === 200) {
        ElMessage.success("删除成功");
        userList.value = userList.value.filter((item) => item.id !== row.id);
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      ElMessage.error("删除失败");
      console.log(err);
    });
};
let updateUserStatus = (row) => {
  row = JSON.parse(JSON.stringify(row));
  axiosUtil
    .updateUserStatus(row.id, row.status)
    .then((res) => {
      if (res.data.code === 200) {
        ElMessage.success("更新成功");
        // userList.value = userList.value.filter((item) => item.id !== row.id);
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      ElMessage.error("删除失败");
      console.log(err);
    });
};
let updateUsersStatus = (status) => {
  let ids = [];
  for (let index = 0; index < batchUpdateStatusUsers.length; index++) {
    ids.push(batchUpdateStatusUsers[index].id);
  }
  if (ids.length <= 0 || status >= userStatus || status < 0)
    ElMessage.error("系统出现错误修改失败");
  axiosUtil
    .updateUsersStatus(ids, status)
    .then((res) => {
      if (res.data.code === 200) {
        ElMessage.success("更新成功");
        for (let index = 0; index < pageData.value.length; index++) {
          if (ids.indexOf(pageData.value[index].id) != -1)
            pageData.value[index].status = status;
        }
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((err) => {
      ElMessage.error("删除失败");
      console.log(err);
    });
};
let handleSelectionChange = (row) => {
  row = JSON.parse(JSON.stringify(row));
  batchUpdateStatusUsers = row;
  batchNoShow.value = batchUpdateStatusUsers.length != 0 ? false : true;
};
let addUser = () => {
  form.value.open();
};
let filter = ref({});
const filterChange = (prop) => {
  filter.value = { ...filter.value, ...prop };
};
</script>

<style scoped lang="scss">
.pag {
  display: flex;
  justify-content: center;
}
</style>