<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部视频</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="!videoList || videoList.length == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="videoList">
            <ul class="of" id="bna">
              <li v-for="item in videoList" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img video-card">
                    <img
                      :src="item.videoPic"
                      class="img-responsive"
                      :alt="item.videoTitle"
                    />
                    <div class="cc-mask">
                      <a :href="item.videoPath" target="_blank" title="开始播放" class="comm-btn c-btn-1">开始播放</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="item.videoSourceId" :title="item.videoTitle" class="course-title fsize18 c-333">{{item.videoTitle}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">{{item.uploadUser.nickname}}</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{item.createTime}}</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <!-- <div>
          <div class="paging">
            <a class="undisable" title>首</a>
            <a id="backpage" class="undisable" href="#" title>&lt;</a>
            <a href="#" title class="current undisable">1</a>
            <a href="#" title>2</a>
            <a id="nextpage" href="#" title>&gt;</a>
            <a href="#" title>末</a>
            <div class="clear"></div>
          </div>
        </div> -->
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      videoList: [],
    };
  },

  methods: {
    // 获取视频列表
    getVideoList() {
      axios.get(`http://localhost:9999/job/video/findAll`).then((resp) => {
        if (resp.data.code != 2000) {
          //操作错误，友好提示
          this.$message({
            type: "error",
            message: "失败! 错误码:" + resp.data.code,
          });
          return;
        }
        this.videoList = resp.data.data;
      });
    },
  },

  created() {
    this.getVideoList();
  },
};
</script>

<style>
.video-card{
  width: 270px;
  height: 152px;
}
</style>