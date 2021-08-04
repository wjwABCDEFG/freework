<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">招聘信息列表</a>
        \
        <span class="c-333 fsize14">{{getOnlyPosition(recruitmentInfo.position)}}</span>
      </section>
      <div>
        <!-- 福利 -->
        <article class="c-v-pic-wrap intro-css">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{recruitmentInfo.position}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">薪资：</span>
              <b
                class="c-yellow"
                style="font-size:24px;"
              >￥{{recruitmentInfo.salary}} * {{recruitmentInfo.salaryMonth}}薪</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">
                <el-tag type="danger" :hit="false" v-for="item in welfareTags" :key="item">{{item}}</el-tag>
              </span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a
                  class="c-fff vam"
                  title="工作时间"
                  href="#"
                >{{handleWorkDay("" + recruitmentInfo.workDay)}} {{recruitmentInfo.workTime}}</a>
              </span>
            </section>
            <section class="c-attr-mt">
              <a href="#" title="立即投递" class="comm-btn c-btn-3" @click="showResumeList">立即投递</a>
            </section>
          </section>
        </article>
        <!-- 公司 -->
        <aside class="c-attr-wrap" v-if="recruitmentInfo.company">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ recruitmentInfo.company.companyName}}</span>
            </h2>
            <section class="c-attr-jg">
              <!-- <span class="c-fff">价格：</span> -->
              <b class="c-yellow" style="font-size:24px;">{{recruitmentInfo.address}}</b>
              <span class="c-fff fsize14">{{recruitmentInfo.detailAddress}}</span>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">规模：{{recruitmentInfo.company.scale}}</span>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">截止日期：{{recruitmentInfo.endTime}}</span>
            </section>
            <section class="c-attr-mt">
              <!-- <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a> -->
            </section>
          </section>
        </aside>
        <!-- 要求 -->
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol clearfix">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">经验要求</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{recruitmentInfo.experienceReq}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">学历要求</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{recruitmentInfo.degreeReq}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">只招应届</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{recruitmentInfo.campus ? '是' : '否'}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- 招聘正文 -->
      <div class="mt20 c-infor-box">
        <!-- 职位描述 -->
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="招聘说明">招聘说明</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>职位描述</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <div v-html="recruitmentInfo.positionDesc"></div>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>公司介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap" v-if="recruitmentInfo.company">
                    <section class="course-txt-body">{{recruitmentInfo.company.companyDesc}}</section>
                  </div>
                  <section class="mt20"></section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <!-- hr -->
        <aside class="fl col-3" v-if="recruitmentInfo.hr">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">企业HR</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="recruitmentInfo.hr.avatar" width="50" height="50" alt />
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{recruitmentInfo.hr.name}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{recruitmentInfo.hr.email}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <el-dialog title="选择简历" :visible.sync="dialogVisible">
      <el-table :data="resumeList" style="width: 100%">
        <el-table-column prop="resumeName" label="简历名称" width="180"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column prop="modifiedTime" label="修改时间"></el-table-column>
        <el-table-column prop label="操作">
          <div slot-scope="scope">
            <el-button icon="el-icon-s-promotion" circle @click="delivery(scope.row.id)"></el-button>
          </div>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { slideToStr, strToSlide, strToHuman } from "@/utils/workday";
import { addToStr, strToAdd } from "@/utils/address";
import cookie from "js-cookie";
import qs from "qs";

export default {
  data() {
    return {
      recruitmentInfo: [],
      welfareTags: [],
      loginInfo: {},
      resumeList: [],
      dialogVisible: false,
    };
  },

  methods: {
    // 获取详细信息
    getRecruitmentInfo(id) {
      axios
        .get(`http://localhost:9999/job/recruitment/findById/${id}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.recruitmentInfo = resp.data.data;
          if (this.recruitmentInfo.welfareTag)
            this.welfareTags = JSON.parse(this.recruitmentInfo.welfareTag);
        });
    },
    // 只要职位
    getOnlyPosition(src) {
      src = "" + src;
      let a = src.split("-");
      return a[1];
    },
    // 薪资福利列表
    handleWelfareTag(welfareTag) {
      return JSON.parse(welfareTag);
    },
    // 格式化工作时间
    handleWorkDay(src) {
      return strToHuman(src);
    },
    // 展示简历列表
    showResumeList() {
      this.getResumes(this.loginInfo.id);
      this.dialogVisible = true;
    },
    // 从cookie中获取已登陆用户信息
    getUserInfo() {
      let jsonStr = cookie.get("user_info");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
      }
    },
    // 获取简历列表
    getResumes(id) {
      axios
        .get(`http://localhost:9999/job/resume/findSimpleByUser/${id}`)
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
    // 投递
    delivery(resumeId) {
      this.dialogVisible = false;
      this.$notify({
        title: "Info",
        type: "success",
        message: "投递成功",
      });
      axios
        .post(
          `http://localhost:9999/job/delivery/deliResume`,
          qs.stringify({
            userId: this.loginInfo.id,
            recruitmentId: this.$route.params.id,
            resumeId: resumeId,
          })
        )
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
        });
    },
  },

  created() {
    // 从路径中获取id
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;
      this.getRecruitmentInfo(id);
    }
    this.getUserInfo();
  },
};
</script>

<style>
.intro-css {
  background-color: teal;
}
</style>