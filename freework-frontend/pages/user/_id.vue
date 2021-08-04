<template>
  <!-- 表单 -->
  <div class="card-css">
    <el-form ref="form" :model="userInfo" label-width="80px">
      <el-form-item label="姓名">
        <el-input v-model="userInfo.name"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="userInfo.nickname"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="userInfo.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userInfo.email"></el-input>
      </el-form-item>
      <el-form-item label="修改密码">
        <el-col :span="11">
          <el-input v-model="userInfo.oldPassword" placeholder="旧密码" show-password></el-input>
        </el-col>
        <el-col class="line" :span="2">&nbsp;</el-col>
        <el-col :span="11">
          <el-input v-model="userInfo.password" placeholder="新密码" show-password></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
          class="avatar-uploader"
          action="http://localhost:9003/upload/image"
          :show-file-list="false"
          :on-success="uploadLogoSuccess"
          :before-upload="beforeUpload"
        >
          <img v-if="userInfo.avatar" :src="this.userInfo.avatar" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="简介">
        <el-input type="textarea" v-model="userInfo.intro"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="userInfo.gender">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="地址">
        <el-cascader v-model="selectedAddress" :options="address" @change="handleChangeAddress"></el-cascader>
      </el-form-item>
      <el-form-item label="学校">
        <el-input v-model="userInfo.school"></el-input>
      </el-form-item>
      <el-form-item label="公司">
        <el-input v-model="userInfo.company"></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="userInfo.position"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update">立即保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
const { regionDataPlus } = require("element-china-area-data/dist/app.commonjs");
import { slideToStr, strToSlide } from "@/utils/workday";
import { addToStr, strToAdd } from "@/utils/address";
import axios from "axios";
import cookie from "js-cookie";

export default {
  data() {
    return {
      loginInfo: {},
      userInfo: {
        avatar: "", //一定要带上这两个空值，否则图片不能立即显示，可能是elementui的bug
        roleId: "",
      },
      address: regionDataPlus,
      selectedAddress: [],
    };
  },
  methods: {
    // 修改
    update() {
      axios
        .post(`http://localhost:9999/job/user/updateUser`, this.userInfo)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.$message({
            type: "success",
            message: "修改成功",
          });
          // 重置cookie
          cookie.set("user_info", this.userInfo, {
            domain: "localhost",
          });
          // 回到页面
          if(!!this.userInfo.password) this.$router.push({ path: "/login" });
          else this.$router.push({ path: "/" });
        })
        .catch();
    },
    // 地址拼接
    handleChangeAddress(value) {
      this.userInfo.address = addToStr(value);
    },
    uploadLogoSuccess(res, file) {
      // console.log(res)
      URL.createObjectURL(file.raw);
      if (res.code != 2000) {
        //操作错误，友好提示
        this.$message({
          type: "error",
          message: "失败! 错误码:" + resp.code,
        });
        return;
      }
      this.userInfo.avatar = res.data.fullpath;
    },
    // 图片校验
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isJPG && !isPNG) {
        this.$message.error("上传头像图片只能是 JPG/PNG 格式!");
      }
      if (!isLt5M) {
        this.$message.error("上传头像图片大小不能超过 5MB!");
      }
      return (isJPG || isPNG) && isLt5M;
    },
    // 从cookie中获取已登陆用户信息
    getUserInfo() {
      let jsonStr = cookie.get("user_info");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
      }
    },
  },
  created() {
    // 根据路径是否有id判断当前是添加还是修改
    if (this.$route.params && this.$route.params.id) {
      this.getUserInfo();
      this.userInfo = this.loginInfo;
    }
  },
  // mounted(){   // 调试时候方便在控制台通过vue.dataName输出data中的值
  //   window.vue = this
  // }
};
</script>

<style>
.card-css {
  /* border: 1px solid red; */
  margin: 20px 250px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>