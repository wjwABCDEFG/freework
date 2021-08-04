<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search-css">
      <el-form :inline="true" :model="userQuery" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="userQuery.name" placeholder="温嘉伟"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="userQuery.phone"></el-input>
        </el-form-item>
      </el-form>
    </div>

    <!-- 公司列表table -->
    <div>
      <el-table :data="userList" style="width: 100%">
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="手机号" prop="phone" width="250"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="性别">
          <template slot-scope="scope">
            <el-tag :type="genderColor(scope.row.gender)">{{ scope.row.gender == '0' ? "男" : "女" }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="注册时间" prop="createTime"></el-table-column>
        <el-table-column align="right">
          <div slot-scope="scope">
            <el-button type="danger" icon="el-icon-delete" @click="deleteUser(scope.row.id)"></el-button>
          </div>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页导航 -->
    <div class="pagination-css">
      <el-pagination
        background
        layout="total, prev, pager, next"
        @current-change="pageUser"
        :current-page.sync="curPage"
        :page-size="pageSize"
        :total="totalNum"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userList: [],
      curPage: 1,
      pageSize: 5,
      totalNum: 0,
      userQuery: {},
    };
  },
  methods: {
    // 条件查询
    pageUser() {
      this.$http
        .post(
          `http://localhost:9999/job/user/findPage/${this.curPage}/${this.pageSize}`,
          this.userQuery
        )
        .then((resp) => {
          //console.log(resp.data);
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "查询失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.userList = resp.data.data.records;
          this.totalNum = resp.data.data.total;
        });
    },
    // 删除
    deleteUser(id) {
      this.$confirm("是否要将该用户拉进黑名单，操作将不可逆", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http
          .delete(`http://localhost:9999/job/user/remove/${id}`)
          .then((resp) => {
            // console.log(resp.data);
            if (resp.data.code != 2000) {
              //操作错误，友好提示
              this.$message({
                type: "error",
                message: "删除失败! 错误码:" + resp.data.code,
              });
            } else {
              this.pageUser(); // 刷新页面
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            }
          });
      });
    },
    // 用户性别标签
    genderColor(gender) {
      return gender == "0" ? "" : "success";
    },
  },
  created() {
    this.pageUser(this.curPage, this.pageSize);
  },
  // mounted(){   // 调试时候方便在控制台通过vue.dataName输出data中的值
  //   window.vue = this
  // },
  watch: {
    userQuery: {
      handler(newVal, oldVal) {
        this.pageUser(this.curPage, this.pageSize);
      },
      deep: true, // 监控对象内所有属性的变化
    },
  },
};
</script>

<style>
.search-css {
  margin: 10px;
}

.pagination-css {
  float: right;
  margin: 20px;
}

/* 图片框 */
.logo-height-css {
  height: 60px; /* logo外层div限定高度 */
}

/* 图片 */
.logo-height-css > img {
  height: 100%; /* logo本身会扩大至外层同高 */
  vertical-align: middle;
}
</style>