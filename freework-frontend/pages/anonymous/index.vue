<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /招聘信息列表 开始 -->
    <section class="container">
      <!-- 搜索栏 -->
      <div class="search-css">
        <el-form :inline="true" :model="anoQuery" class="demo-form-inline">
          <el-form-item label="公司名称">
            <el-input v-model="anoQuery.companyName" placeholder="freework"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <section class="c-sort-box">
        <!-- 招聘信息数据 -->
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="!anonymousList || anonymousList.length == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list rec-salary-list" v-if="anonymousList">
            <ul class="of" id="bna">
              <li v-for="item in anonymousList" :key="item.id">
                <div class="cc-l-wrap card" style="margin:auto;">
                  <section class="course-img card-box-rec">
                    <div>
                      <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">可信度：{{item.star - item.unstar + 1}}</i>
                      </span>
                      <p class="pos-title">{{item.companyName}}</p>
                      <p class="fr pos-pos">{{item.position}}</p>
                      <p class="pos-salary">{{item.salary}} * {{item.salaryMonth}}薪</p>
                      <p class="fr">
                        <i class="el-icon-star-on rate-on-css" @click="star(item.id)"></i>
                        <i class="el-icon-star-off rate-off-css" @click="unstar(item.id)"></i>
                      </p>
                      <span class="fl jgAttr c-ccc f-fA">
                        <el-tag size="medium">
                          <i class="el-icon-location-outline"></i>
                          {{item.city}}
                        </el-tag>
                      </span>
                    </div>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
          <el-button icon="el-icon-plus" circle @click="showDialog" class="add-css"></el-button>
        </div>
      </section>
    </section>
    <!-- /招聘信息列表 结束 -->
    <!-- 表单 -->
    <div>
      <el-dialog title="薪资爆料" :visible.sync="dialogFormVisible">
        <el-form :model="anonymousData">
          <!-- 公司 -->
          <el-form-item>
            <span slot="label">
              <span class="span-box">
                <i class="el-icon-office-building"></i>
                <span>公司</span>
              </span>
            </span>
            <el-input v-model="anonymousData.companyName" placeholder="公司名称"></el-input>
          </el-form-item>
          <!-- 岗位 -->
          <el-form-item>
            <span slot="label">
              <span class="span-box">
                <i class="el-icon-suitcase-1"></i>
                <span>岗位</span>
              </span>
            </span>
            <el-input v-model="anonymousData.position" placeholder="岗位名称"></el-input>
          </el-form-item>
          <!-- 城市 -->
          <el-form-item>
            <span slot="label">
              <span class="span-box">
                <i class="el-icon-suitcase-1"></i>
                <span>城市</span>
              </span>
            </span>
            <el-input v-model="anonymousData.city" placeholder="岗位所在城市，如北京"></el-input>
          </el-form-item>
          <!-- 薪资 -->
          <el-form-item>
            <span slot="label">
              <span class="span-box">
                <i class="el-icon-money"></i>
                <span>薪资</span>
              </span>
            </span>
            <el-col :span="18">
              <span>&nbsp;</span>
            </el-col>
            <!-- 表单 -->
            <div>
              <el-col :span="4">
                <el-input-number
                  v-model="salarys[0]"
                  :precision="1"
                  :step="1.0"
                  :min="0"
                  controls-position="right"
                  class="salary-input-css"
                ></el-input-number>
              </el-col>
              <el-col :span="1">至</el-col>
              <el-col :span="4">
                <el-input-number
                  v-model="salarys[1]"
                  :precision="1"
                  :step="1.0"
                  :min="salarys[0]"
                  controls-position="right"
                  class="salary-input-css"
                ></el-input-number>
              </el-col>
              <el-col :span="2">k</el-col>
              <el-col :span="2">x</el-col>
              <el-col :span="4">
                <el-input-number
                  v-model="anonymousData.salaryMonth"
                  :step="1.0"
                  :min="0"
                  controls-position="right"
                  class="salary-input-css"
                ></el-input-number>
              </el-col>
              <el-col :span="4">月</el-col>
            </div>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="save">点此爆料！</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      anonymousList: [],
      anoQuery: {},
      dialogFormVisible: false,
      anonymousData: {},
      salarys: [],
    };
  },

  methods: {
    // 获取匿名薪资信息
    getAnonymousList() {
      axios
        .post(`http://localhost:9999/job/anonymous/findAll`, this.anoQuery)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.anonymousList = resp.data.data;
        });
    },
    // 打开对话框
    showDialog() {
      this.dialogFormVisible = true;
    },
    // 保存薪资
    save() {
      this.anonymousData.salary =
        this.salarys[0] + "k-" + this.salarys[1] + "k";
      axios
        .post(`http://localhost:9999/job/anonymous/add`, this.anonymousData)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.$notify({
            title: "Info",
            type: "success",
            message: "爆料成功",
          });
          // 刷新页面
          this.showDialog = false;
          this.getAnonymousList();
        });
    },
    // 点赞
    star(id) {
      axios
        .post(`http://localhost:9999/job/anonymous/star/${id}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          // 刷新页面
          this.getAnonymousList();
        });
    },
    // 点踩
    unstar(id) {
      axios
        .post(`http://localhost:9999/job/anonymous/unstar/${id}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          // 刷新页面
          this.getAnonymousList();
        });
    },
  },

  created() {
    this.getAnonymousList();
  },

  watch: {
    anoQuery: {
      handler(newVal, oldVal) {
        this.getAnonymousList();
      },
      deep: true, // 监控对象内所有属性的变化
    },
  },
};
</script>

<style>
.rec-salary-list li {
  float: left;
  width: 100%;
  margin-bottom: 13px;
}

.rate-on-css {
  font-size: 20px;
  cursor: pointer;
  margin-left: 5px;
}

.rate-off-css {
  font-size: 17px;
  cursor: pointer;
  margin-left: 5px;
}

.rec-salary-list .card {
  background-color: white;
  width: 500px;
}

.card-box-rec {
  padding: 10px;
}

.card-box-com {
  padding-left: 10px;
}

.rec-salary-list .pos-title {
  line-height: 35px;
  font-size: 18px;
}

.rec-salary-list .pos-pos{
  font-size: 18px;
}

.pos-salary {
  line-height: 35px;
  font-size: 17px;
  color: #ff7e00;
}

.salary-input-css {
  width: 90px;
}

.logo-height-css {
  height: 60px; /* logo外层div限定高度 */
}

.search-css{
    margin-top: 5px;
    margin-left: 320px;
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

/* 打印按钮 */
.add-css {
  position: fixed;
  z-index: 99; /*设置优先级显示，保证不会被覆盖*/
  right: 30px;
  bottom: 50px;
}
</style>