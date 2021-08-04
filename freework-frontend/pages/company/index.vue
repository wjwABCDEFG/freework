<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 企业列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部企业</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="!companyList || companyList.length == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="i-teacher-list" v-if="companyList">
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
                      :href="'/company/'+item.id"
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
    <!-- /企业列表 结束 -->
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      companyList: [],
      curPage: 1,
      pageSize: 8,
      totalNum: 0,
      totalPage: 0,
    };
  },
  methods: {
    // 分页查询
    pageCompany(pageNum, limit = 8) {
      axios
        .post(`http://localhost:9999/job/company/findPage/${pageNum}/${limit}`)
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
          this.companyList = resp.data.data.records;
          this.totalNum = resp.data.data.total;
          this.totalPage = resp.data.data.pages;
        });
    },
    // 跳转某页
    gotoPage(newPage) {
      this.curPage = newPage;
      this.pageCompany(newPage, this.pageSize);
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
    this.pageCompany(this.curPage, this.pageSize);
  },
  // mounted(){   // 调试时候方便在控制台通过vue.dataName输出data中的值
  //   window.vue = this
  // },
};
</script>

<style>
.pic-css {
  width: 140px;
  height: 140px;
}
</style>