<template>
  <div>
    <!-- 列表 -->
    <div class="container">
      <!-- 卡片外框 -->
      <div v-for="item in videoList" :key="item.id" class="card-kuang-css">
        <el-card class="box-card">
          <span>{{item.videoTitle}}</span>
          <div>
            <el-image :src="item.videoPic" fit="contain" class="logo-height-css"></el-image>
          </div>
          <el-button type="danger" icon="el-icon-delete" circle @click="delVideo(item.id)"></el-button>
        </el-card>
      </div>
      <!-- 添加视频按钮 -->
      <div>
        <el-button type="text" @click="showForm" class="add-btn-css">添加视频</el-button>
      </div>
    </div>

    <!-- 表单 -->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form ref="video" :model="video" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="video.videoTitle"></el-input>
        </el-form-item>
        <el-form-item label="视频封面">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:9003/upload/image"
            :show-file-list="false"
            :on-success="uploadLogoSuccess"
            :before-upload="beforeUpload"
          >
            <img v-if="video.videoPic" :src="this.video.videoPic" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            action="http://localhost:9004/vod/video/upload"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，
                <br />支持3GP、ASF、AVI、DAT、DV、FLV、F4V、
                <br />GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、
                <br />MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、
                <br />SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveVideo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fileList: [], //上传文件列表,
      videoList: [],
      video: {
        videoPic: "",
        uploader: "1352149079043358728", // TODO 改成当前用户
        playCount: 0,
        videoOriginalName: "",
      },
      dialogVisible: false,
    };
  },
  methods: {
    // 获取视频列表
    getVideoList() {
      this.$http.get(`http://localhost:9000/job/video/findAll`).then((resp) => {
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
    // 打开对话框
    showForm() {
      this.dialogVisible = true;
      this.video = {
        videoPic: "",
        uploader: "1352149079043358728", // TODO 改成当前用户
        playCount: 0,
        videoOriginalName: "",
      };
    },
    // 关闭对话框
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    // 上传封面
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
      this.video.videoPic = res.data.fullpath;
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
    // 视频上传成功回调
    handleVodUploadSuccess(response, file, fileList) {
      //   console.log(response.data);
      this.video.videoSourceId = response.data;
      this.video.videoOriginalName = file.name;
      this.$message({
        type: "success",
        message: "上传成功",
      });
    },
    // 试图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },
    // 删除之前的确认提示
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 删除上传的视频
    handleVodRemove(file, fileList) {
      this.$http
        .delete(
          `http://localhost:9004/vod/video/remove/${this.video.videoSourceId}`
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
          this.fileList = [];
          this.video.videoSourceId = "";
          this.video.videoOriginalName = "";
          this.$message({
            type: "success",
            message: "删除成功",
          });
        });
    },
    // 保存视频
    saveVideo() {
      this.$http
        .post(`http://localhost:9000/job/video/add`, this.video)
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
          this.getVideoList();
          this.$message({
            type: "success",
            message: "添加成功",
          });
        });
    },
    // 删除视频
    delVideo(id) {
      this.$confirm("此操作将永久删除该条视频, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http
          .delete(`http://localhost:9000/job/video/remove/${id}`)
          .then((resp) => {
            if (resp.data.code != 2000) {
              //操作错误，友好提示
              this.$message({
                type: "error",
                message: "失败! 错误码:" + resp.data.code,
              });
              return;
            }
            this.getVideoList();
            this.$message({
              type: "success",
              message: "删除成功",
            });
          });
      });
    },
  },
  created() {
    this.getVideoList();
  },
};
</script>

<style>
.container {
  margin: 10px auto;
  width: 60%;
  overflow: hidden;
  /* background-color: red; */
}

.card-kuang-css {
  float: left;
  margin-top: 10px;
  margin-left: 10px;
  text-align: center;
  height: 260px;
}

/* card */
.box-card {
  width: 407px;
  height: 260px;
}

/* 添加按钮 */
.add-btn-css {
  float: left;
  margin-top: 10px;
  margin-left: 10px;
  text-align: center;
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
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

/* 图片框 */
.logo-height-css {
  margin: 0px auto;
  width: 300px;
  height: 175px;
}

/* 图片 */
.logo-height-css > img {
  /* height: 100%; logo本身会扩大至外层同高 */
  /* vertical-align: middle; */
}
</style>