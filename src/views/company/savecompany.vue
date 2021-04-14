<template>
  <!-- 表单 -->
  <div class="card-css">
    <el-form ref="form" :model="companyInfo" label-width="80px">
    <el-form-item label="企业名称">
        <el-input v-model="companyInfo.companyName"></el-input>
    </el-form-item>
    <el-form-item label="企业logo">
        <el-upload
          action="#"
          list-type="picture-card"
          :auto-upload="false">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{file}">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url" alt=""
              >
              <span class="el-upload-list__item-actions">
                <span
                  class="el-upload-list__item-preview"
                  @click="handlePictureCardPreview(file)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleDownload(file)"
                >
                  <i class="el-icon-download"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
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
            v-for="item in newIndustry"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="工作日">
        <el-slider
          v-model="companyInfo.workDay"
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
          arrow-control
          v-model="value2"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          placeholder="选择时间范围">
        </el-time-picker>
    </el-form-item>
    <el-form-item label="规模">
        <el-input v-model="companyInfo.scale"></el-input>
    </el-form-item>
    <el-form-item label="简介">
        <el-input type="textarea" v-model="companyInfo.companyDesc"></el-input>
    </el-form-item>
    <el-form-item label="营业执照">
        <el-upload
          action="#"
          list-type="picture-card"
          :auto-upload="false">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{file}">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url" alt=""
              >
              <span class="el-upload-list__item-actions">
                <span
                  class="el-upload-list__item-preview"
                  @click="handlePictureCardPreview(file)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleDownload(file)"
                >
                  <i class="el-icon-download"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </el-form-item>
    <el-form-item label="法人">
        <el-input v-model="companyInfo.workDay"></el-input>
    </el-form-item>
    <el-form-item label="注册资金">
        <el-col :span="11">
          <el-input-number v-model="companyInfo.registeredCapital" :precision="4" :step="0.1" :min="0.0001"></el-input-number>
        </el-col>
        <span>万元</span>
    </el-form-item>
    <el-form-item label="注册时间">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="选择日期" v-model="companyInfo.date1" style="width: 100%;"></el-date-picker>
        </el-col>
    </el-form-item>
    <el-form-item>
        <el-button type="primary" @click="onSubmit">立即保存</el-button>
    </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { regionDataPlus, CodeToText } from 'element-china-area-data'

  export default {
    data() {
      return {
        companyInfo: {},
        address: regionDataPlus,
        selectedAddress: [],
        workDayMarks: {
          1: '一',
          2: '二',
          3: '三',
          4: '四',
          5: '五',
          6: '六',
          7: '日',
        }
      }
    },
    methods: {
      pageCompany(){
        this.$http.post(`http://localhost:9000/job/company/add`, this.companyInfo).then((resp)=>{
          console.log(resp.data);
          if(resp.data.code != 2000){
            //操作错误，友好提示
            this.$message({
              type: 'error',
              message: '失败! 错误码:' + resp.data.code
            });
          }else{
            
          }
        }).catch();
      },
      handleChangeAddress(value) {
        // console.log(value[0] == '', value[1] == '', value[2] == null)
        let addressText = value[0] == '' ? '' : CodeToText[value[0]]
        for (let i = 1; i < value.length; i++) {
          if(value[i] != '') addressText = addressText + '-' + CodeToText[value[i]]
        }
        this.companyInfo.address = addressText
      },
      onSubmit() {
        console.log('submit!');
      }
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
</style>