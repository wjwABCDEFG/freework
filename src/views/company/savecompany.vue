<template>
  <!-- 表单 -->
  <div class="card-css">
    <el-form ref="form" :model="companyInfo" label-width="80px">
    <el-form-item label="企业名称">
        <el-input v-model="companyInfo.companyName"></el-input>
    </el-form-item>
    <el-form-item label="企业logo">
      <el-upload
        class="avatar-uploader"
        action="http://localhost:9003/upload/image"
        :show-file-list="false"
        :on-success="uploadLogoSuccess"
        :before-upload="beforeUpload">
        <img v-if="companyInfo.logo" :src="this.companyInfo.logo" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-form-item>
    <el-form-item label="地址">
        <el-cascader
          v-model="selectedAddress"
          :options="address"
          @change="handleChangeAddress">
        </el-cascader>
    </el-form-item>
    <el-form-item label="行业">
        <el-select v-model="companyInfo.industry" clearable placeholder="请选择">
          <el-option
            v-for="item in industry"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="工作日">
        <el-slider
          v-model="workdays"
          range
          show-stops
          :min="1"
          :max="7"
          :marks="workDayMarks">
        </el-slider>
    </el-form-item>
    <el-form-item label="工作时间">
        <el-time-picker
          is-range
          v-model="workTimes"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="HH:mm"
          value-format="HH:mm">
        </el-time-picker>
    </el-form-item>
    <el-form-item label="规模">
        <el-select v-model="companyInfo.scale" placeholder="请选择">
          <el-option
            v-for="item in scales"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="简介">
        <el-input type="textarea" v-model="companyInfo.companyDesc"></el-input>
    </el-form-item>
    <el-form-item label="营业执照">
      <el-upload
        class="avatar-uploader"
        action="http://localhost:9003/upload/image"
        :show-file-list="false"
        :on-success="uploadLicenseSuccess"
        :before-upload="beforeUpload">
        <img v-if="companyInfo.license" :src="this.companyInfo.license" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-form-item>
    <el-form-item label="法人">
        <el-input v-model="companyInfo.representative"></el-input>
    </el-form-item>
    <el-form-item label="注册资金">
        <el-col :span="11">
          <el-input-number v-model="companyInfo.registeredCapital" :precision="4" :step="1.0" :min="0.0001"></el-input-number>
        </el-col>
        <span>万元</span>
    </el-form-item>
    <el-form-item label="注册时间">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="选择日期" v-model="companyInfo.registeredTime" style="width: 100%;"></el-date-picker>
        </el-col>
    </el-form-item>
    <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">立即保存</el-button>
    </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { regionDataPlus, CodeToText } from 'element-china-area-data'
  import { slideToStr } from '@/utils/workday'

  export default {
    data() {
      return {
        companyInfo: {
          logo: "", //一定要带上这两个空值，否则图片不能立即显示，可能是elementui的bug
          license: "",
        },
        address: regionDataPlus,
        industry: [],
        selectedAddress: [],
        workdays: [1, 1],
        workTimes: '',
        workDayMarks: {
          1: '一',
          2: '二',
          3: '三',
          4: '四',
          5: '五',
          6: '六',
          7: '日',
        },
        scales: [{
          value: '20人以下',
          label: '20人以下'
        }, {
          value: '20-99人',
          label: '20-99人'
        }, {
          value: '100-499人',
          label: '100-499人'
        }, {
          value: '500-999人',
          label: '500-999人'
        }, {
          value: '1000-3000人',
          label: '1000-3000人'
        }, {
          value: '3000人及以上',
          label: '3000人及以上'
        }],
      }
    },
    methods: {
      saveOrUpdate(){
        if(!this.companyInfo.id){
          this.save()
        }else{
          this.update()
        }
      },

      getCompanyInfo(id){
        this.$http.post(`http://localhost:9000/job/company/findById/${id}`).then((resp)=>{
          console.log(resp.data);
          if(resp.data.code != 2000){
            //操作错误，友好提示
            this.$message({
              type: 'error',
              message: '失败! 错误码:' + resp.data.code
            });
            return ;
          }
          this.companyInfo = resp.data.data
        }).catch();
      },
      // 修改
      update(){
        this.$http.post(`http://localhost:9000/job/company/update`, this.companyInfo).then((resp)=>{
          if(resp.data.code != 2000){
            //操作错误，友好提示
            this.$message({
              type: 'error',
              message: '失败! 错误码:' + resp.data.code
            });
            return ;
          }
          this.$message({
            type: 'success',
            message: '修改成功'
          });
          // 回到页面
          this.$router.push({path:'/company/list'})
        }).catch();
      },
      // 保存
      save(){
        this.companyInfo.workTime = this.workTimes.join("-")
        this.companyInfo.workDay = slideToStr(this.workdays)
        // console.log(this.companyInfo);
        this.$http.post(`http://localhost:9000/job/company/add`, this.companyInfo).then((resp)=>{
          // console.log(resp.data);
          if(resp.data.code != 2000){
            //操作错误，友好提示
            this.$message({
              type: 'error',
              message: '失败! 错误码:' + resp.data.code
            });
            return ;
          }
          this.$message({
            type: 'success',
            message: '添加成功'
          });
          // 回到页面
          this.$router.push({path:'/company/list'})
        }).catch();
      },
      //地址拼接
      handleChangeAddress(value) {
        // console.log(value[0] == '', value[1] == '', value[2] == null)
        let addressText = value[0] == '' ? '' : CodeToText[value[0]]
        for (let i = 1; i < value.length; i++) {
          if(value[i] != '') addressText = addressText + '-' + CodeToText[value[i]]
        }
        this.companyInfo.address = addressText
      },
      getIndustry(){
        this.$http.get(`http://localhost:9000/job/company/static`, {
          params:{
            fileName: "industry.json"
          }
        }).then((resp)=>{
            if(resp.data.code != 2000){
              //操作错误，友好提示
              this.$message({
                type: 'error',
                message: '失败! 错误码:' + resp.data.code
              });
            }else{
              this.industry = JSON.parse(resp.data.data)              
            }

          });
      },
      uploadLogoSuccess(res, file){
        // console.log(res)
        URL.createObjectURL(file.raw);
        if(res.code != 2000){
          //操作错误，友好提示
          this.$message({
            type: 'error',
            message: '失败! 错误码:' + resp.code
          });
          return ;
        }
        this.companyInfo.logo = res.data.fullpath
        // console.log(this.companyInfo.logo)
      },
      uploadLicenseSuccess(res, file){
        URL.createObjectURL(file.raw);
        if(res.code != 2000){
          //操作错误，友好提示
          this.$message({
            type: 'error',
            message: '失败! 错误码:' + resp.code
          });
          return ;
        }
        this.companyInfo.license = res.data.fullpath
      },
      // 图片校验
      beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        const isLt5M = file.size / 1024 / 1024 < 5;

        if (!isJPG && !isPNG) {
          this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
        }
        if (!isLt5M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
        }
        return (isJPG || isPNG) && isLt5M;
      },
      // beforeLicenseUpload(file) {
      //   const isJPG = file.type === 'image/jpeg';
      //   const isPNG = file.type === 'image/png';
      //   const isLt5M = file.size / 1024 / 1024 < 5;

      //   if (!isJPG && !isPNG) {
      //     this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
      //   }
      //   if (!isLt5M) {
      //     this.$message.error('上传头像图片大小不能超过 5MB!');
      //   }
      //   return (isJPG || isPNG) && isLt5M;
      // },
    },
    created() {
      this.getIndustry()
      // 根据路径是否有id判断当前是添加还是修改
      if(this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.getCompanyInfo(id)
      }
    },
    mounted(){   // 调试时候方便在控制台通过vue.dataName输出data中的值
      window.vue = this
    }
  }
</script>

<style>
  .card-css{
    /* border: 1px solid red; */
    margin: 20px 250px;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
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