<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="params">
        <el-form-item
          class="input-prepend restyle"
          prop="name"
          :rules="[{ required: true, message: '请输入你的姓名', trigger: 'blur' }]"
        >
          <div>
            <el-input type="text" placeholder="你的姓名" v-model="params.name" />
            <i class="iconfont icon-user" />
          </div>
        </el-form-item>
        <el-form-item
          class="input-prepend restyle"
          prop="nickname"
          :rules="[{ required: true, message: '请输入你的昵称', trigger: 'blur' }]"
        >
          <div>
            <el-input type="text" placeholder="你的昵称" v-model="params.nickname" />
            <i class="iconfont icon-user" />
          </div>
        </el-form-item>

        <el-form-item
          class="input-prepend restyle no-radius"
          prop="phone"
          :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]"
        >
          <div>
            <el-input type="text" placeholder="手机号" v-model="params.phone" />
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item
          class="input-prepend restyle no-radius"
          prop="code"
          :rules="[{ required: true, message: '请输入验证码', trigger: 'blur' }]"
        >
          <div style="width: 100%;display: block;float: left;position: relative">
            <el-input type="text" placeholder="验证码" v-model="params.code" />
            <i class="iconfont icon-phone" />
          </div>
          <div class="btn" style="position:absolute;right: 0;top: 6px;width: 40%;">
            <a
              href="javascript:"
              type="button"
              @click="getCodeFun()"
              :value="codeTest"
              style="border: none;background-color: none"
            >{{codeTest}}</a>
          </div>
        </el-form-item>

        <el-form-item
          class="input-prepend"
          prop="password"
          :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
        >
          <div>
            <el-input type="password" placeholder="设置密码" v-model="params.password" />
            <i class="iconfont icon-password" />
          </div>
        </el-form-item>

        <el-form-item prop="phone">
          <div>
            <el-radio-group v-model="params.roleId">
              <el-radio label="1351962010769752065">我要找工作</el-radio>
              <el-radio label="1352161213957558274">我要招人</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-up-button" value="注册" @click="submitRegister()" />
        </div>
        <p class="sign-up-msg">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br />
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">用户协议</a>
          和
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a> 。
        </p>
      </el-form>
      <!-- 更多注册方式 -->
      <!-- <div class="more-sign">
        <h6>社交帐号直接注册</h6>
        <ul>
          <li>
            <a
              id="weixin"
              class="weixin"
              target="_blank"
              href="http://huaan.free.idcfengye.com/api/ucenter/wx/login"
            >
              <i class="iconfont icon-weixin" />
            </a>
          </li>
          <li>
            <a id="qq" class="qq" target="_blank" href="#">
              <i class="iconfont icon-qq" />
            </a>
          </li>
        </ul>
      </div> -->
    </div>
  </div>
</template>

<script>
import "~/assets/css/sign.css";
import "~/assets/css/iconfont.css";
import axios from "axios";

export default {
  layout: "sign",
  data() {
    return {
      params: {
        name: "",
        phone: "",
        code: "",
        nickname: "",
        password: "",
      },
      sending: true, //是否发送验证码
      second: 60, //倒计时间
      codeTest: "获取验证码",
    };
  },
  methods: {
    // 发送验证码
    getCodeFun() {
      //sending = false
      //this.sending原为true,请求成功，!this.sending == true，主要是防止有人把disabled属性去掉，多次点击；
      if (!this.sending) return;

      //debugger
      this.$refs.userForm.validateField("phone", (errMsg) => {
        if (errMsg == "") {
          // 校验成功
          axios
            .get(`http://localhost:9999/sms/send/${this.params.phone}`)
            .then((resp) => {
              this.sending = false;
              this.timeDown();
              //console.log(resp.data);
              if (resp.data.code != 2000) {
                //操作错误，友好提示
                this.$message({
                  type: "error",
                  message: "发送失败, 请重试! 错误码:" + resp.data.code,
                });
                return;
              }
            });
        }
      });
    },

    // 倒计时
    timeDown() {
      let result = setInterval(() => {
        --this.second;
        this.codeTest = this.second;
        if (this.second < 1) {
          clearInterval(result);
          this.sending = true;
          //this.disabled = false;
          this.second = 60;
          this.codeTest = "获取验证码";
        }
      }, 1000);
    },

    submitRegister() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          axios
            .post(`http://localhost:9999/user/register`, this.params)
            .then((resp) => {
              //console.log(resp.data);
              if (resp.data.code != 2000) {
                //操作错误，友好提示
                this.$message({
                  type: "error",
                  message: "注册失败! 错误码:" + resp.data.code,
                });
                return;
              }
              //提示注册成功
              this.$message({
                type: "success",
                message: "注册成功",
              });
              this.$router.push({ path: "/login" });
            });
        }
      });
    },

    checkPhone(rule, value, callback) {
      //debugger
      if (!/^1[34578]\d{9}$/.test(value)) {
        return callback(new Error("手机号码格式不正确"));
      }
      return callback();
    },
  },
};
</script>

<style >
</style>