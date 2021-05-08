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
      <el-table-column prop="userName" label="求职者姓名" show-overflow-tooltip min-width="15%"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" min-width="12%" sortable></el-table-column>
      <el-table-column prop="modifiedTime" label="最后修改时间" min-width="12%" sortable></el-table-column>
      <el-table-column label="操作" min-width="16%">
        <template slot="header">
          <span style="padding-right:30px;">操作</span>
          <el-button type="plain" icon="el-icon-delete" circle @click="deleteBatch"></el-button>
        </template>
        <div slot-scope="scope">
          <router-link :to="'/user/resumeedit/' + scope.row.id">
            <el-link icon="el-icon-view" :underline="false" class="edit-btn-css">查看</el-link>
          </router-link>
          <el-link icon="el-icon-delete" :underline="false" @click="deleteRes(scope.row.id)">删除</el-link>
        </div>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      resumeList: [],
      multipleSelection: [],
    };
  },

  methods: {
    // 获取简历列表
    getResumes() {
      this.$http
        .get(`http://localhost:9000/job/resume/findAll`)
        .then((resp) => {
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
      this.$http
        .post(`http://localhost:9000/job/resume/removeBatch`, rids)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "删除失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.getResumes(); // 刷新页面
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
        this.$http
          .delete(`http://localhost:9000/job/resume/remove/${id}`)
          .then((resp) => {
            if (resp.data.code != 2000) {
              //操作错误，友好提示
              this.$message({
                type: "error",
                message: "删除失败! 错误码:" + resp.data.code,
              });
              return;
            }
            this.getResumes(); // 刷新页面
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          });
      });
    },
  },

  created() {
    this.getResumes();
  },
  mounted() {
    // 调试时候方便在控制台通过vue.dataName输出data中的值
    window.vue = this;
  },
};
</script>

<style>
.edit-btn-css {
  margin-right: 20px;
}
</style>