<template>
  <div class="card-css">
    <el-form
      :model="recruitmentInfo"
      :rules="rules"
      label-position="left"
      ref="recruitmentInfo"
      label-width="100px"
    >
      <el-form-item label="公司名称" prop="company" required>
        <el-select
          filterable
          placeholder="请选择"
          v-model="recruitmentInfo.company"
          value-key="id"
          @change="handleCompanyChg"
        >
          <el-option
            v-for="item in this.companyList"
            :key="item.id"
            :label="item.companyName"
            :value="item"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地址" required>
        <el-col :span="8">
          <el-form-item prop="address">
            <el-cascader
              v-model="formattedAddress"
              :options="address"
              @change="handleChangeAddress"
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item prop="detailAddress">
            <el-input v-model="recruitmentInfo.detailAddress" placeholder="详细地址（街道和门牌号）"></el-input>
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item label="职位" prop="position" required>
        <el-cascader
          v-model="positions"
          placeholder="试试搜索：前端"
          :options="industry"
          filterable
          clearable
          :filter-method="filterWithoutCase"
          :props="{ expandTrigger: 'hover' }"
          @change="handlePositionChg"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="职位描述" prop="positionDesc" required>
        <div>
          <tinymce v-model="recruitmentInfo.positionDesc" :height="300" />
        </div>
      </el-form-item>
      <el-form-item label="工作日" required>
        <el-slider
          v-model="workdays"
          range
          show-stops
          :min="1"
          :max="7"
          :marks="workDayMarks"
          @change="handleWorkdayChg"
        ></el-slider>
      </el-form-item>
      <el-form-item label="工作时间" required>
        <el-time-picker
          is-range
          v-model="workTimes"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="HH:mm"
          value-format="HH:mm"
          @change="handleWorkTimeChg"
        ></el-time-picker>
      </el-form-item>
      <el-form-item label="学历要求" prop="degreeReq" required>
        <el-select v-model="recruitmentInfo.degreeReq" placeholder="请选择">
          <el-option v-for="(item, i) in degrees" :key="i" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="经验要求" prop="experienceReq" required>
        <el-select v-model="recruitmentInfo.experienceReq" placeholder="请选择">
          <el-option v-for="(item, i) in experiences" :key="i" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否校招" required>
        <el-switch v-model="recruitmentInfo.campus" active-color="#FF7E00"></el-switch>
      </el-form-item>
      <el-form-item label="薪资(千)" required>
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
            v-model="recruitmentInfo.salaryMonth"
            :step="1.0"
            :min="0"
            controls-position="right"
            class="salary-input-css"
          ></el-input-number>
        </el-col>
        <el-col :span="4">月</el-col>

        <!-- <el-col :span="2">-</el-col>
        <el-col :span="6">
            <el-input-number v-model="num" :precision="1" :step="1.0" :max="10"  controls-position="right"></el-input-number>
        </el-col>
        <el-col :span="6">
            <el-input-number v-model="num" :precision="1" :step="1.0" :max="10"  controls-position="right"></el-input-number>
        </el-col>-->
      </el-form-item>
      <el-form-item label="福利标签" required>
        <el-tag
          v-for="(tag, idx) in welfareTags"
          :key="idx"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
        >{{ tag }}</el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        ></el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
      </el-form-item>
      <el-form-item label="结束日期" prop="endTime" required>
        <el-date-picker
          type="date"
          placeholder="选择日期"
          v-model="recruitmentInfo.endTime"
          style="width: 100%;"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate('recruitmentInfo')">保存</el-button>
        <el-button @click="resetForm('recruitmentInfo')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { regionDataPlus } from "element-china-area-data";
import { slideToStr, strToSlide } from "@/utils/workday";
import { addToStr, strToAdd } from "@/utils/address";
import Tinymce from "@/components/Tinymce";

export default {
  components: { Tinymce },
  data() {
    return {
      recruitmentInfo: {
        company: "",
        companyId: "",
        address: "",
        campus: false,
        hrId: "1352149079043358727", //TODO修改发布招聘信息hrId为从当前登录用户获取
        salaryMonth: 12,
        workDay: "1111100",
        workTime: "09:00-17:00",
        position: "",
        positionDesc: "",
        welfareTag: "['五险一金','包三餐','住房补贴']",
      },
      formattedAddress: "",
      companyList: [],
      address: regionDataPlus,
      industry: [],
      degrees: [
        "不限",
        "初中及以下",
        "中专/中技",
        "高中",
        "大专",
        "本科",
        "硕士",
        "博士",
      ],
      experiences: [
        "不限",
        "在校生",
        "应届生",
        "1年以内",
        "1-3年",
        "3-5年",
        "5-10年",
        "10年以上",
      ],
      positions: [],
      salarys: [0.0, 0.0],
      workdays: [1, 5],
      workTimes: ["Tue May 18 1999 09:00", "Tue May 18 1999 17:00"],
      workDayMarks: {
        1: "一",
        2: "二",
        3: "三",
        4: "四",
        5: "五",
        6: "六",
        7: "日",
      },
      welfareTags: ["五险一金", "包三餐", "住房补贴"],
      inputVisible: false,
      inputValue: "",

      rules: {
        company: [
          {
            required: true,
            message: "请输入公司名称，若没有可先申请创建",
            trigger: "change",
          },
        ],
        address: [{ required: true, message: "请选择分类", trigger: "change" }],
        detailAddress: [
          { required: true, message: "请输入详细地址", trigger: "blur" },
        ],
        position: [
          { required: true, message: "请输入职位", trigger: "change" },
        ],
        positionDesc: [
          { required: true, message: "请输入职位描述", trigger: "blur" },
        ],
        degreeReq: [
          { required: true, message: "请选择学历要求", trigger: "change" },
        ],
        experienceReq: [
          { required: true, message: "请选择经验要求", trigger: "change" },
        ],
        endTime: [
          { required: true, message: "请填写截止日期", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 获取公司列表
    getCompanyList() {
      this.$http
        .get(`http://localhost:9999/job/company/findNameAndId`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.companyList = resp.data.data;
        });
    },
    // 地址拼接
    handleChangeAddress(value) {
      this.recruitmentInfo.address = addToStr(value);
    },
    // 请求行业json
    getIndustry() {
      this.$http
        .get(`http://localhost:9999/job/company/static`, {
          params: {
            fileName: "industry.json",
          },
        })
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "获取行业数据失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.industry = JSON.parse(resp.data.data);
        });
    },
    // 处理企业id变动
    handleCompanyChg(val) {
      // console.log(val);
      this.recruitmentInfo.companyId = val.id;
    },
    // 处理岗位格式
    handlePositionChg(val) {
      this.recruitmentInfo.position = val.join("-");
    },
    // 处理工作日格式
    handleWorkdayChg(val) {
      // console.log(val)
      this.recruitmentInfo.workDay = slideToStr(val);
    },
    // 处理工作时间格式
    handleWorkTimeChg(val) {
      // console.log(val)
      this.recruitmentInfo.workTime = val.join("-");
    },
    // 搜索忽略大小写
    filterWithoutCase(node, data) {
      //   console.log('node', node, 'data', data)  //该方法会自动遍历所有节点，node是遍历到的当前节点，data是输入的文本
      if (!node) return true;
      return node.label.toLowerCase().indexOf(data.toLowerCase()) !== -1;
    },
    // 删除标签
    handleClose(tag) {
      this.welfareTags.splice(this.welfareTags.indexOf(tag), 1);
    },
    // 添加标签:打开输入框
    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus(); //使编辑框聚焦
      });
    },
    // 添加标签:关闭输入框
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.welfareTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
    // 保存和更新
    saveOrUpdate(formName) {
      this.save(formName);
    },
    // 保存
    save(formName) {
      //   console.log(formName);
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return false;
        }
        this.$http
          .post(
            `http://localhost:9999/job/recruitment/add`,
            this.recruitmentInfo
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
            this.$message({
              type: "success",
              message: "成功发布",
            });
            // 回到页面
            this.$router.push({ path: "/recruitment/list" });
          });
      });
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.positions = [];
      this.formattedAddress = "";
    },
  },
  created() {
    this.getCompanyList();
    this.getIndustry();
  },
  mounted() {
    // 调试时候方便在控制台通过vue.dataName输出data中的值
    window.vue = this;
  },
  watch: {
    salarys: {
      handler(newVal, oldVal) {
        this.recruitmentInfo.salary = newVal[0] + "k-" + newVal[1] + "k";
        // console.log(this.recruitmentInfo.salary)
      },
      deep: true, // 监控对象内所有属性的变化
    },
    welfareTags: {
      handler(newVal, oldVal) {
        this.recruitmentInfo.welfareTag = JSON.stringify(newVal);
      },
      deep: true, // 监控对象内所有属性的变化
    },
  },
};
</script>

<style>
.card-css {
  margin: 20px 250px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.salary-input-css {
  width: 90px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
