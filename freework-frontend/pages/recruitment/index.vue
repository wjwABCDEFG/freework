<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /招聘信息列表 开始 -->
    <section class="container">
      <!-- 头部 -->
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部招聘信息</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <!-- 筛选 -->
        <section class="c-s-dl">
          <el-form :inline="true" :model="recriutmentQuery" class="demo-form-inline">
            <el-form-item label="公司名称">
              <el-input v-model="recriutmentQuery.companyName" placeholder="freework"></el-input>
            </el-form-item>
            <el-form-item label="地址">
              <el-cascader
                v-model="selectedAddress"
                :options="address"
                @change="handleChangeAddress"
              ></el-cascader>
            </el-form-item>
            <el-form-item label="行业">
              <el-select v-model="recriutmentQuery.industry" clearable placeholder="请选择">
                <el-option
                  v-for="item in industry"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>

          <div class="clear"></div>
        </section>
        <!-- 排序 -->
        <!-- <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li>
                <a title="关注度" href="#">关注度</a>
              </li>
              <li>
                <a title="最新" href="#">最新</a>
              </li>
              <li class="current bg-orange">
                <a title="价格" href="#">
                  价格&nbsp;
                  <span>↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>-->
        <!-- 招聘信息数据 -->
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="!recruitmentsData || recruitmentsData.length == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list rec-list" v-if="recruitmentsData">
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
                        <i class="c-999 f-fA">{{item.address}}</i>
                        |
                        <i class="c-999 f-fA">{{item.experienceReq}}</i>
                        |
                        <i class="c-999 f-fA">{{item.degreeReq}}</i>
                      </span>
                      <div class="cc-mask">
                        <a
                          :href="'/recruitment/'+item.id"
                          title="查看详情"
                          class="comm-btn c-btn-1"
                        >查看详情</a>
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
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a :class="{undisable: curPage == 1}" href="#" title="首页" @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: curPage == 1}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(curPage - 1)"
            >&lt;</a>
            <a
              v-for="page in totalPage"
              :key="page"
              :class="{current: curPage == page, undisable: curPage == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)"
            >{{ page }}</a>
            <a
              :class="{undisable: curPage == totalPage}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(curPage + 1)"
            >&gt;</a>
            <a
              :class="{undisable: curPage == totalPage}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(totalPage)"
            >末</a>
            <div class="clear" />
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /招聘信息列表 结束 -->
  </div>
</template>

<script>
import axios from "axios";
const {
  regionDataPlus,
  CodeToText,
} = require("element-china-area-data/dist/app.commonjs");

export default {
  data() {
    return {
      recriutmentQuery: {},
      address: regionDataPlus,
      selectedAddress: [],
      industry: {},
      recruitmentsData: [],
      curPage: 1,
      pageSize: 9,
      totalNum: 0,
      totalPage: 0,
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
    // 获取招聘信息
    getRecruitmentsData(pageNum, limit = 9) {
      axios
        .post(
          `http://localhost:9999/job/recruitment/findPage/${pageNum}/${limit}`,
          this.recriutmentQuery
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
          this.recruitmentsData = resp.data.data.records;
          this.totalNum = resp.data.data.total;
          this.totalPage = resp.data.data.pages;
        });
    },
    // 跳转某页
    gotoPage(newPage) {
      this.curPage = newPage;
      this.getRecruitmentsData(newPage, this.pageSize);
    },
    // 获取行业静态json
    getIndustry() {
      axios
        .get(`http://localhost:9999/job/company/static`, {
          params: {
            fileName: "industry.json",
          },
        })
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
          } else {
            this.industry = JSON.parse(resp.data.data);
          }
        });
    },
    //级联选择器地址改变时触发条件查询
    handleChangeAddress(value) {
      // console.log(value[0] == '', value[1] == '', value[2] == null)
      let addressText = value[0] == "" ? "" : CodeToText[value[0]];
      for (let i = 1; i < value.length; i++) {
        if (value[i] != "")
          addressText = addressText + "-" + CodeToText[value[i]];
      }
      this.recriutmentQuery.address = addressText;
      this.getRecruitmentsData(this.curPage, this.pageSize);
    },
  },

  created() {
    this.getRecruitmentsData(this.curPage, this.pageSize);
    this.getIndustry();
  },

  watch: {
    recriutmentQuery: {
      handler(newVal, oldVal) {
        this.getRecruitmentsData(this.curPage, this.pageSize);
      },
      deep: true, // 监控对象内所有属性的变化
    },
  },
};
</script>

<style>
.rec-list li {
  float: left;
  width: 33.33%;
}

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
</style>