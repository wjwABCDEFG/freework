<template>
  <div>
    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption">
      <div class="swiper-wrapper">
        <div
          v-for="banner in bannerList"
          :key="banner.id"
          class="swiper-slide"
          style="background: #040B1B;"
        >
          <a target="_blank" :href="banner.linkUrl">
            <img :src="banner.imageUrl" :alt="banner.title" />
          </a>
        </div>
      </div>
      <div class="swiper-pagination swiper-pagination-white"></div>
      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
    </div>
    <!-- 幻灯片 结束 -->

    <div id="aCoursesList">
      <!-- 招聘信息 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门招聘</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
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
                          <a :href="'/recruitment/'+item.id" title="查看详情" class="comm-btn c-btn-1">查看详情</a>
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
            <section class="tac pt20">
              <router-link to="/recruitment" tag="li" active-class="current">
                <a href="#" title="全部课程" class="comm-btn c-btn-2">全部招聘</a>
              </router-link>
            </section>
          </div>
        </section>
      </div>
      <!-- /招聘信息 结束 -->
      <!-- 热门企业 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门企业</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="item in companyList" :key="item.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/company/'+item.id" :title="item.companyName">
                        <img :src="handleLogo(item.logo)" class="pic-css" :alt="item.companyName" />
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a
                        href="/company/1"
                        :title="item.companyName"
                        class="fsize18 c-666"
                      >{{item.companyName}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{item.companyDesc}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">{{item.industry}}</p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <router-link to="/company" tag="li" active-class="current">
                <a href="#" title="全部企业" class="comm-btn c-btn-2">全部企业</a>
              </router-link>
            </section>
          </div>
        </section>
      </div>
      <!-- /热门企业 结束 -->
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { addCity } from "@/utils/address.js";

export default {
  data() {
    return {
      bannerList: [],
      recruitmentsData: [],
      companyList: [],
      swiperOption: {
        //配置分页
        pagination: {
          el: ".swiper-pagination", //分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: ".swiper-button-next", //下一页dom节点
          prevEl: ".swiper-button-prev", //前一页dom节点
        },
      },
    };
  },

  methods: {
    // 获取首页轮播图
    getBanners() {
      axios.get(`http://localhost:9999/cms/banner/findAll`).then((resp) => {
        if (resp.data.code != 2000) {
          //操作错误，友好提示
          this.$message({
            type: "error",
            message: "失败! 错误码:" + resp.data.code,
          });
          return;
        }
        this.bannerList = resp.data.data;
      });
    },
    // 获取首页招聘信息
    getRecruitmentsData() {
      axios
        .get(`http://localhost:9999/job/recruitment/findIndex`)
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
    // 获取首页企业信息
    getCompanies() {
      axios.get(`http://localhost:9999/job/company/findIndex`).then((resp) => {
        if (resp.data.code != 2000) {
          //操作错误，友好提示
          this.$message({
            type: "error",
            message: "查询失败! 错误码:" + resp.data.code,
          });
          return;
        }
        this.companyList = resp.data.data;
      });
    },
    // 处理图片清晰度
    handleLogo(thumb) {
      if (thumb.indexOf("_60x60") != -1) {
        thumb = thumb.replace("_60x60", "");
      }
      return thumb;
    },
  },

  created() {
    this.getBanners();
    this.getRecruitmentsData();
    this.getCompanies();
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

.card-box-com {
  padding-left: 10px;
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

.pic-css {
  width: 140px;
  height: 140px;
}
</style>