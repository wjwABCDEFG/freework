<template>
  <div>
    <!-- 招聘信息列表 -->
    <el-table
      ref="multipleTable"
      :data="bannerList"
      tooltip-effect="dark"
      style="width: 100%"
      :default-sort="{prop: 'date', order: 'descending'}"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column label="图片" min-width="20%">
        <template slot-scope="scope">
          <div class="logo-height-css">
            <img :src="scope.row.imageUrl" />
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="15%"></el-table-column>
      <el-table-column prop="linkUrl" label="链接地址" min-width="30%"></el-table-column>
      <el-table-column prop="createTime" label="发布时间" min-width="15%" sortable></el-table-column>
      <el-table-column label="操作" min-width="20%">
        <template slot="header">
          <span style="padding-right:30px;">操作</span>
          <el-button type="plain" icon="el-icon-delete" circle @click="deleteBatch"></el-button>
        </template>
        <div slot-scope="scope">
          <el-link
            icon="el-icon-top"
            :underline="false"
            class="option-css"
            @click="updateSort(scope.row.id, -1)"
          ></el-link>
          <el-link
            icon="el-icon-bottom"
            :underline="false"
            class="option-css"
            @click="updateSort(scope.row.id, 1)"
          ></el-link>
          <el-link
            icon="el-icon-edit"
            :underline="false"
            class="option-css"
            @click="openDialog(scope.row.id)"
          >修改</el-link>
          <el-link icon="el-icon-delete" :underline="false" @click="deleteBanner(scope.row.id)">删除</el-link>
        </div>
      </el-table-column>
    </el-table>

    <!-- 详细信息 -->
    <el-dialog title="编辑banner" :visible.sync="dialogVisible">
      <el-form :model="bannerInfo">
        <el-form-item label="轮播图片">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:9003/upload/image"
            :show-file-list="false"
            :on-success="uploadLogoSuccess"
            :before-upload="beforeUpload"
          >
            <img v-if="bannerInfo.imageUrl" :src="bannerInfo.imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="bannerInfo.title"></el-input>
        </el-form-item>
        <el-form-item label="跳转链接">
          <el-input v-model="bannerInfo.linkUrl"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">{{!!selectedId ? "修 改": "添 加"}}</el-button>
      </div>
    </el-dialog>

    <el-button icon="el-icon-plus" circle @click="dialogVisible = true" class="download-css"></el-button>
  </div>
</template>

<script>
import { strToHuman } from "@/utils/workday";
import editrecruitment from "@/views/recruitment/editrecruitment";

export default {
  components: {
    "rec-card": editrecruitment,
  },
  data() {
    return {
      bannerList: [],
      multipleSelection: [],
      selectedId: "",
      dialogVisible: false,
      edit: false,
      bannerInfo: {
        imageUrl: "",
      },
    };
  },

  methods: {
    // 获取招聘信息
    getBannerList() {
      this.$http
        .get(`http://localhost:9999/cms/banner/findAll`)
        .then((resp) => {
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
    findById(id) {
      this.$http
        .get(`http://localhost:9999/cms/banner/findById/${id}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.bannerInfo = resp.data.data;
        });
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 打开对话框
    openDialog(id) {
      this.findById(id);
      this.selectedId = id;
      this.dialogVisible = true;
    },
    // 保存或修改
    saveOrUpdate() {
      if (!!this.selectedId) {
        this.update();
      } else {
        this.save();
      }
    },
    // 保存
    save() {
      this.$http
        .post(`http://localhost:9999/cms/banner/add`, this.bannerInfo)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.dialogVisible = false;
          this.getBannerList();
          this.$message({
            type: "success",
            message: "添加成功",
          });
        });
    },
    // 修改
    update() {
      this.$http
        .post(`http://localhost:9999/cms/banner/update`, this.bannerInfo)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.dialogVisible = false;
          this.getBannerList();
          this.$message({
            type: "success",
            message: "修改成功",
          });
        });
    },
    // 删除招聘信息
    deleteBanner(id) {
      this.$confirm("确认删除该条招聘信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http
          .delete(`http://localhost:9999/cms/banner/remove/${id}`)
          .then((resp) => {
            if (resp.data.code != 2000) {
              //操作错误，友好提示
              this.$message({
                type: "error",
                message: "删除失败! 错误码:" + resp.data.code,
              });
              return;
            }
            this.getBannerList(); // 刷新页面
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          });
      });
    },
    // 批量删除
    deleteBatch() {
      let rids = this.multipleSelection.map((item) => {
        return item.id;
      });
      this.$http
        .post(`http://localhost:9999/cms/banner/removeBatch`, rids)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "删除失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.getBannerList(); // 刷新页面
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        });
    },
    // 调整顺序
    updateSort(id, direct) {
      this.$http
        .post(`http://localhost:9999/cms/banner/updateSort/${id}/${direct}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.getBannerList();
        });
    },
    // 上传成功
    uploadLogoSuccess(res, file) {
      URL.createObjectURL(file.raw);
      if (res.code != 2000) {
        //操作错误，友好提示
        this.$message({
          type: "error",
          message: "失败! 错误码:" + resp.code,
        });
        return;
      }
      this.bannerInfo.imageUrl = res.data.fullpath;
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
  },

  created() {
    this.getBannerList();
  },
  mounted() {
    // 调试时候方便在控制台通过vue.dataName输出data中的值
    window.vue = this;
  },
};
</script>

<style>
.option-css {
  margin-right: 20px;
}

.logo-height-css {
  height: 60px; /* logo外层div限定高度 */
}

.logo-height-css > img {
  height: 100%; /* logo本身会扩大至外层同高 */
  vertical-align: middle;
}

.detail-css .el-dialog__body {
  padding: 0px 0px;
}

.detail-css .el-dialog__body .card-css {
  margin: 0px;
  padding: 20px;
}

/* 添加按钮 */
.download-css {
  position: fixed;
  z-index: 99; /*设置优先级显示，保证不会被覆盖*/
  right: 30px;
  bottom: 50px;
}

/* 上传相关 */
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