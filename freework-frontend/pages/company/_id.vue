<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 公司介绍 开始 -->
    <section class="container">
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
            </section> -->
            <section class="t-infor-txt">
              <p class="mt20">规模：{{companyInfo.scale}}</p>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">工作时间：{{handleWorkDay("" + companyInfo.workDay)}} {{companyInfo.workTime}}</p>
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
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="!recruitmentsData || recruitmentsData.length == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="recruitmentsData">
            <ul class="of">
              <li v-for="item in recruitmentsData" :key="item.id">
                <div class="cc-l-wrap card">
                  <section class="course-img card-box-rec">
                    <div class="img-responsive">
                      <p class="pos-title">{{item.position}}</p>
                      <p class="pos-salary">{{item.salary}} * {{item.salaryMonth}}薪</p>
                      <span class="fr jgTag bg-green" v-show="item.campus">
                        <i class="c-fff fsize12 f-fA">校招</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{handleAddress(item.address)}}</i>
                        |
                        <i class="c-999 f-fA">{{item.experienceReq}}</i>
                        |
                        <i class="c-999 f-fA">{{item.degreeReq}}</i>
                      </span>
                      <div class="cc-mask">
                        <a :href="'/recruitment/'+item.id" title="开始学习" class="comm-btn c-btn-1">查看详情</a>
                      </div>
                    </div>
                  </section>
                  <hr />
                  <div class="hLh30 txtOf logo-height-css card-box-com">
                    <img :src="item.company.logo" :alt="item.company.companyName" />
                    <span class="com-title">{{item.company.companyName}}</span>
                    <span class="c-ccc f-fA">
                      <i class="c-999 f-fA">{{item.company.industry}}</i>
                      |
                      <i class="c-999 f-fA">{{item.company.scale}}</i>
                    </span>
                  </div>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
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

export default {
  data() {
    return {
      companyInfo: {},
      recruitmentsData: [],
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
    // 获取该公司招聘信息
    getRecruitmentsData(id) {
      axios
        .get(`http://localhost:9999/job/recruitment/findByCompanyId/${id}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.recruitmentsData = resp.data.data;
        });
    },
    // 地址只显示省市
    handleAddress(address) {
      return addCity(address);
    },
  },

  created() {
    // 从路径中获取id
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;
      this.getCompanyInfo(id);
      this.getRecruitmentsData(id);
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