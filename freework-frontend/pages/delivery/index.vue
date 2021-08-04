<template>
  <div>
    <!-- 搜索栏 -->
    <!-- <div class="search-css">
      <el-form :inline="true" :model="companyQuery" class="demo-form-inline">
        <el-form-item label="公司名称">
          <el-input v-model="companyQuery.companyName" placeholder="freework"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-cascader v-model="selectedAddress" :options="address" @change="handleChangeAddress"></el-cascader>
        </el-form-item>
        <el-form-item label="行业">
          <el-select v-model="companyQuery.industry" clearable placeholder="请选择">
            <el-option
              v-for="item in industry"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>-->

    <!-- 候选人列表table -->
    <div>
      <el-table :data="candidateList" style="width: 100%">
        <el-table-column label="求职者" prop="user.name"></el-table-column>
        <el-table-column label="投递岗位" prop="position" width="250"></el-table-column>
        <el-table-column label="进度">
          <div slot-scope="scope">
            <el-tag
              :type="showProgress(scope.row.progress).style"
            >{{showProgress(scope.row.progress).res}}</el-tag>
          </div>
        </el-table-column>
        <el-table-column label="投递时间" prop="createTime"></el-table-column>
        <el-table-column align="right">
          <template slot-scope="scope">
            <el-link type="primary" :href="'/resume/'+scope.row.resumeId" target="_blank">查看简历</el-link>
            <el-link type="danger" @click="showDialog(scope.row.id, -1)">淘汰</el-link>
            <el-link type="primary" @click="showDialog(scope.row.id, 3)">面试邀请</el-link>
            <el-link type="success" @click="showDialog(scope.row.id, 0)">发放offer</el-link>
            <el-link type="danger" @click="remove(scope.row.id)">移除</el-link>
          </template>
        </el-table-column>
      </el-table>

      <!-- 留言框 -->
      <el-dialog title="给求职者留言吧，内容将会以邮件方式通知对方" :visible.sync="dialogVisible">
        <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="toEmployer.msg"></el-input>
        <div>{{tipsInfo}}</div>
        <div>{{tipsInfo2}}</div>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateProgress">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <!-- 分页导航 -->
    <!-- <div class="pagination-css">
      <el-pagination
        background
        layout="total, prev, pager, next"
        @current-change="pageCandidates"
        :current-page.sync="curPage"
        :page-size="pageSize"
        :total="totalNum"
      ></el-pagination>
    </div>-->
  </div>
</template>

<script>
import { strToHuman } from "@/utils/workday";
import axios from "axios";
import cookie from "js-cookie";
import qs from "qs";

export default {
  data() {
    return {
      loginInfo: "",
      candidateList: [],
      companyQuery: {},
      toEmployer: {
        msg: "",
        id: "",
        progress: "",
      },
      dialogVisible: false,
      tipsInfo: "",
      tipsInfo2: "",
    };
  },
  methods: {
    // 条件查询
    findCandidates(hrId) {
      axios
        .get(`http://localhost:9999/job/delivery/findCandidatesByUser/${hrId}`)
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
          this.candidateList = resp.data.data;
        });
    },
    showProgress(progress) {
      let res = "";
      let style = "";
      if (progress == 1) {
        res = "简历待筛选";
      } else if (progress == -1) {
        res = "淘汰";
        style = "info";
      } else if (progress == 0) {
        res = "发放offer";
        style = "success";
      } else {
        res = "流程中";
        style = "warning";
      }
      return { res, style };
    },
    // 从cookie中获取已登陆用户信息
    getUserInfo() {
      let jsonStr = cookie.get("user_info");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
      }
    },
    // 显示对话框
    showDialog(id, progress) {
      this.dialogVisible = true;
      this.toEmployer.id = id;
      this.toEmployer.progress = progress;
      this.tips(progress);
    },
    // 更新状态
    updateProgress() {
      axios
        .post(
          `http://localhost:9999/job/delivery/updateProgress`,
          qs.stringify({
            id: this.toEmployer.id,
            progress: this.toEmployer.progress,
            msg: this.toEmployer.msg,
          })
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
          this.dialogVisible = false;
          this.findCandidates(this.loginInfo.id);
          this.$notify({
            title: "info",
            message: "邮件通知成功",
            type: "success",
          });
        });
    },
    // 提示
    tips(progress) {
      if (progress == 3) {
        this.tipsInfo =
          '[模板]你好，诚邀您在6月5日上午10：00参加我们的面试，面试链接为<a target="_blank" href="http://localhost:8080/">点击进入</a>，房间名为freework';
        this.tipsInfo2 =
          "[提示]如果企业有自己的面试平台，可以使用自己的会议链接，若没有自己的视频面试平台，可以使用freework平台提供的面试平台";
      }else{
        this.tipsInfo = "";
        this.tipsInfo2 = "";
      }
    },
  },
  created() {
    this.getUserInfo();
    this.findCandidates(this.loginInfo.id);
  },
  watch: {
    companyQuery: {
      handler(newVal, oldVal) {
        this.pageCandidates();
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