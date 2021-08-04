<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="freework招聘平台">
            <img src="~/assets/img/logo.png" width="100%" alt="freework招聘平台" />
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/recruitment" tag="li" active-class="current" v-if="loginInfo.roleId != '1352161213957558274'">
              <a>招聘信息</a>
            </router-link>
            <router-link to="/company" tag="li" active-class="current" v-if="loginInfo.roleId != '1352161213957558274'">
              <a>企业信息</a>
            </router-link>
            <router-link to="/resume" tag="li" active-class="current" v-if="loginInfo.roleId != '1352161213957558274'">
              <a>简历中心</a>
            </router-link>
            <router-link to="/video" tag="li" active-class="current" v-if="loginInfo.roleId != '1352161213957558274'">
              <a>视频中心</a>
            </router-link>
            <router-link to="/anonymous" tag="li" active-class="current" v-if="loginInfo.roleId != '1352161213957558274'">
              <a>匿名薪资</a>
            </router-link>
            <router-link to="/company/mycompany" tag="li" active-class="current" v-if="loginInfo.roleId == '1352161213957558274'">
              <a>我的企业</a>
            </router-link>
            <router-link to="/recruitment/myrecruitment" tag="li" active-class="current" v-if="loginInfo.roleId == '1352161213957558274'">
              <a>我的招聘</a>
            </router-link>
            <router-link to="/delivery" tag="li" active-class="current" v-if="loginInfo.roleId == '1352161213957558274'">
              <a>求职进度</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <!-- <li v-if="loginInfo.id" id="is-login-one" class="mr10">
              <a id="headerMsgCountId" href="/chat/Home" title="消息">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li> -->
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
              <a :href="'/user/'+loginInfo.id" title>
                <img :src="loginInfo.avatar" width="30" height="30" class="vam picImg" alt />
                <span id="userName" class="vam disIb">{{ loginInfo.nickname }}</span>
              </a>
              <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <!-- <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="搜索招聘信息" name="queryCourse.courseName" value />
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside> -->
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->

    <nuxt />

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="https://github.com/wjwABCDEFG/freework" title="本项目地址" target="_blank">本项目地址</a>
            </li>
            <li>
              <a href="https://wjw.today/" title="嘉伟博客" target="_blank">嘉伟博客</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：13076271026(韶关)</span>
                <span>Email：975504808@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2021广东海洋大学温嘉伟毕业设计 粤ICP备2021029568号</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt />
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt />
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import cookie from "js-cookie";

export default {
  data() {
    return {
      token: "",
      loginInfo: {
        id: "",
        name: "",
        nickname: "",
        password: "",
        avatar: "",
        intro: "",
        gender: "",
        address: "",
        phone: "",
        email: "",
        school: "",
        company: "",
        position: "",
        roleId: "",
        status: "",
        createTime: "",
        modifiedTime: "",
      },
    };
  },

  created() {
    this.getUserInfo();
  },

  methods: {
    // 从cookie中获取已登陆用户信息
    getUserInfo() {
      let jsonStr = cookie.get("user_info");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
        console.log(this.loginInfo);
      }
    },
    // 登出
    logout() {
      cookie.set("token", "", {
        domain: "localhost",
      });
      cookie.set("user_info", "", {
        domain: "localhost",
      });
      this.loginInfo = {};
      //跳转页面
      window.location.href = "/";
    },
  },
};
</script>