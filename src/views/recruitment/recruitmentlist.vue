<template>
  <div>
    <el-table
      ref="multipleTable"
      :data="recruitmentsData"
      tooltip-effect="dark"
      style="width: 100%"
      :default-sort="{prop: 'date', order: 'descending'}"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column prop="position" label="职位" show-overflow-tooltip min-width="18%"></el-table-column>
      <el-table-column prop="address" label="地址" show-overflow-tooltip min-width="17%"></el-table-column>
      <el-table-column label="薪资" min-width="10%">
        <template slot-scope="scope">
          <el-tag
            :type="salaryColor(scope.row.salary, scope.row.salaryMonth)"
          >{{ scope.row.salary }} * {{ scope.row.salaryMonth }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="工作时间" min-width="10%">
        <template slot-scope="scope">
          <div>{{ handleWorkDay(scope.row.workDay) }}</div>
          <div>{{ scope.row.workTime }}</div>
        </template>
      </el-table-column>
      <el-table-column label="公司名" min-width="10%">
        <template slot-scope="scope">
          <div>
            <img :src="scope.row.company.logo" />
          </div>
          <div>{{ scope.row.company.companyName }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="发布时间" min-width="13%" sortable></el-table-column>
      <el-table-column prop="endTime" label="截止日期" min-width="9%" sortable></el-table-column>
      <el-table-column label="操作" min-width="13%">
        <div>
          <!-- <el-link icon="el-icon-edit" :underline="false">编辑</el-link> -->
          <el-link icon="el-icon-view" :underline="false" class="option-css">查看</el-link>
          <el-link icon="el-icon-delete" :underline="false">删除</el-link>
        </div>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { strToHuman } from "@/utils/workday";

export default {
  data() {
    return {
      recruitmentsData: [],
      multipleSelection: [],
    };
  },

  methods: {
    // 获取招聘信息
    getRecruitmentsData() {
      this.$http
        .get(`http://localhost:9000/job/recruitment/findAll`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.recruitmentsData = resp.data.data;
        });
    },
    // 格式化工作时间
    handleWorkDay(src) {
      return strToHuman(src);
    },
    // 薪资标签颜色TODO
    salaryColor(salary, month) {
      let base = salary.split("-")[0];
      base *= month; // 年薪
      if (base < 50) return "info";           // 5w 
      else if (base < 100) return "warning";  // 10w
      else if (base < 150) return "success";  // 15w
      else if (base < 200) return "";         // 20w
      else if (base > 200) return "danger";   // >20w
      // :color="salaryColor(scope.row.salary, scope.row.salaryMonth)"后期可以改成颜色代码
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection)
    },
  },

  created() {
    this.getRecruitmentsData();
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
</style>