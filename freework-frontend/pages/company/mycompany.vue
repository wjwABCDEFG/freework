<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /无数据提示 开始-->
    <section class="no-data-wrap" v-if="!loginInfo.companyId || !companyInfo">
      <em class="icon30 no-data-ico">&nbsp;</em>
      <span class="c-666 fsize14 ml10 vam">暂未加入企业，请添加...</span>
      <!-- 加入企业按钮 -->
      <div>
        <el-form :inline="true" :model="companyQuery" class="demo-form-inline">
          <el-form-item label="企业名称">
            <el-select v-model="companyQuery.companyName" filterable placeholder="请选择">
              <el-option
                v-for="item in companyList"
                :key="item.id"
                :label="item.companyName"
                :value="item.companyName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="加入码">
            <el-input v-model="companyQuery.id" placeholder="请联系所在企业获取加入码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="becomeHR">加入该企业hr</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!-- 申请企业审批按钮 -->
      <a href="/company/applycompany" title="注册">
        <el-button type="text">没有找到所在企业，点击申请</el-button>
      </a>
    </section>
    <!-- /无数据提示 结束-->
    <!-- 公司介绍 开始 -->
    <section class="container" v-if="!!loginInfo.companyId && !!companyInfo">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">公司信息</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 公司基本信息 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img :src="handleLogo(companyInfo.logo)" />
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{companyInfo.companyName}}</span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg">{{companyInfo.industry}}</span>
            </section>
            <!-- <section class="t-infor-txt">
              <p class="mt20">简介：{{companyInfo.companyDesc}}</p>
            </section>-->
            <section class="t-infor-txt">
              <p class="mt20">规模：{{companyInfo.scale}}</p>
            </section>
            <section class="t-infor-txt">
              <p
                class="mt20"
              >工作时间：{{handleWorkDay("" + companyInfo.workDay)}} {{companyInfo.workTime}}</p>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">地址：{{companyInfo.address}}</p>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">街道：{{companyInfo.detailAddress}}</p>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">法人：{{companyInfo.representative}}</p>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">注册资金：{{companyInfo.registeredCapital}}万元</p>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">注册时间：{{companyInfo.registeredTime}}</p>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">公司简介</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">{{companyInfo.companyDesc}}</p>
            </section>
          </header>
        </div>
      </section>
    </section>
    <!-- /公司介绍 结束 -->
  </div>
</template>

<script>
import axios from "axios";
import { strToHuman } from "@/utils/workday";
import { addCity } from "@/utils/address.js";
import cookie from "js-cookie";
import qs from "qs";

export default {
  data() {
    return {
      loginInfo: {},
      companyInfo: {},
      companyQuery: {},
      companyList: [],
    };
  },

  methods: {
    // 查询
    getCompanyInfo(id) {
      axios
        .post(`http://localhost:9999/job/company/findById/${id}`)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.companyInfo = resp.data.data;
        });
    },
    // 处理图片清晰度
    handleLogo(thumb) {
      thumb = "" + thumb;
      if (thumb.indexOf("_60x60") != -1) {
        thumb = thumb.replace("_60x60", "");
      }
      return thumb;
    },
    // 格式化工作时间
    handleWorkDay(src) {
      return strToHuman(src);
    },
    // 地址只显示省市
    handleAddress(address) {
      return addCity(address);
    },
    // 从cookie中获取已登陆用户信息
    getUserInfo() {
      let jsonStr = cookie.get("user_info");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
        // console.log(this.loginInfo);
      }
    },
    // 获取公司列表
    getCompanyList() {
      axios
        .get(`http://localhost:9999/job/company/findNameAndId`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.companyList = resp.data.data;
        });
    },
    // 加入企业HR
    becomeHR() {
      axios
        .post(
          `http://localhost:9999/job/user/becomeHR`,
          qs.stringify({
            hrId: this.loginInfo.id,
            companyId: this.companyQuery.id,
            companyName: this.companyQuery.companyName,
          })
        )
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: resp.data.msg,
            });
            return;
          }
          this.$message({
            type: "success",
            message: "加入成功",
          });
          this.loginInfo.companyId = this.companyQuery.id;
          this.loginInfo.company = this.companyQuery.companyName;
          // 更新cookie
          cookie.set("user_info", this.loginInfo, {
            domain: "localhost",
          });
          console.log(this.loginInfo);
          this.getCompanyInfo(this.loginInfo.companyId);
        });
    },
  },

  created() {
    this.getUserInfo();
    if (!!this.loginInfo.companyId) {
      const id = this.loginInfo.companyId;
      this.getCompanyInfo(id);
    } else {
      this.getCompanyList();
    }
  },
};
</script>

<style>
.card {
  background-color: white;
}

.card-box-rec {
  padding: 10px;
}

.pos-title {
  line-height: 35px;
  font-size: 15px;
}

.pos-salary {
  line-height: 35px;
  font-size: 17px;
  color: #ff7e00;
}

.logo-height-css {
  height: 60px; /* logo外层div限定高度 */
}

.logo-height-css > img {
  height: 100%; /* logo本身会扩大至外层同高 */
  vertical-align: middle;
}

.com-title {
  font-size: 15px;
  padding-left: 5px;
  padding-right: 5px;
  color: rgb(90, 87, 87);
}

hr {
  margin: 5px 10px 0px 10px;
  border-top: 1px solid #ccc;
  border-bottom: 0px;
}
</style>