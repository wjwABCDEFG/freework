<template>
  <div>
    <!-- 招聘信息列表 -->
    <el-table
      ref="resumeList"
      :data="resumeList"
      tooltip-effect="dark"
      style="width: 100%"
      :default-sort="{prop: 'date', order: 'descending'}"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column prop="resumeName" label="简历" show-overflow-tooltip min-width="15%"></el-table-column>
      <!-- <el-table-column prop="userName" label="求职者姓名" show-overflow-tooltip min-width="15%"></el-table-column> -->
      <el-table-column prop="createTime" label="创建时间" min-width="12%" sortable></el-table-column>
      <el-table-column prop="modifiedTime" label="最后修改时间" min-width="12%" sortable></el-table-column>
      <el-table-column label="操作" min-width="16%">
        <template slot="header">
          <router-link to="/resume/saveresume">
            <el-button type="success" round>创建简历</el-button>
          </router-link>
          <el-button type="plain" icon="el-icon-delete" circle @click="deleteBatch"></el-button>
        </template>
        <div slot-scope="scope">
          <router-link :to="'/resume/' + scope.row.id">
            <el-link icon="el-icon-view" :underline="false" class="edit-btn-css">查看</el-link>
          </router-link>
          <el-link icon="el-icon-delete" :underline="false" @click="deleteRes(scope.row.id)">删除</el-link>
        </div>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
import cookie from "js-cookie";


export default {
  data() {
    return {
      resumeList: [],
      multipleSelection: [],
      loginInfo: {},
    };
  },

  methods: {
    // 从cookie中获取已登陆用户信息
    getUserInfo() {
      let jsonStr = cookie.get("user_info");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
      }
    },
    // 获取简历列表
    getResumes(id) {
      axios.get(`http://localhost:9999/job/resume/findByUser/${id}`).then((resp) => {
        if (resp.data.code != 2000) {
          //操作错误，友好提示
          this.$message({
            type: "error",
            message: "失败! 错误码:" + resp.data.code,
          });
          return;
        }
        this.resumeList = resp.data.data;
      });
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 批量删除
    deleteBatch() {
      let rids = this.multipleSelection.map((item) => {
        return item.id;
      });
      axios
        .post(`http://localhost:9999/job/resume/removeBatch`, rids)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "删除失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.getResumes(this.loginInfo.id); // 刷新页面
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        });
    },
    // 删除简历信息
    deleteRes(id) {
      this.$confirm("确认删除该条招聘信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios
          .delete(`http://localhost:9999/job/resume/remove/${id}`)
          .then((resp) => {
            if (resp.data.code != 2000) {
              //操作错误，友好提示
              this.$message({
                type: "error",
                message: "删除失败! 错误码:" + resp.data.code,
              });
              return;
            }
            this.getResumes(this.loginInfo.id); // 刷新页面
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          });
      });
    },
  },

  created() {
    this.getUserInfo();
    this.getResumes(this.loginInfo.id);
  },
  mounted() {
    // 调试时候方便在控制台通过vue.dataName输出data中的值
    // window.vue = this;
  },
};
</script>

<style>
.edit-btn-css {
  margin-right: 20px;
}
</style>