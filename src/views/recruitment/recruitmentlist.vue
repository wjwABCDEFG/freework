<template>
  <div>
    <!-- 招聘信息列表 -->
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
        <div slot-scope="scope">
          <!-- <el-link icon="el-icon-edit" :underline="false">编辑</el-link> -->
          <el-link icon="el-icon-view" :underline="false" class="option-css" @click="showDrawer(scope.row.id)">查看</el-link>
          <el-link icon="el-icon-delete" :underline="false">删除</el-link>
        </div>
      </el-table-column>
    </el-table>

    <!-- 详细信息 -->
    <el-drawer
      :title="recruitmentInfo.company.companyName + ' 的招聘信息'"
      :before-close="handleClose"
      :visible.sync="drawer"
      custom-class="demo-drawer"
      ref="drawer"
      >
      <div class="demo-drawer__content">
        {{recruitmentInfo}}
        <div class="demo-drawer__footer">
          <el-button @click="cancelForm">取 消</el-button>
          <el-button type="primary" @click="$refs.drawer.closeDrawer()" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { strToHuman } from "@/utils/workday";

export default {
  data() {
    return {
      recruitmentsData: [],
      multipleSelection: [],
      drawer: false,
      loading: false,
      timer: null,
      edit: false,
      selectedId: '',
      recruitmentInfo: {
        company: {
          companyName: "",
        }
      },
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
      let base = salary.substring(0, salary.indexOf('k'));
      base *= month; // 年薪
      if (base < 50) return "info";           // 5w 
      else if (base < 100) return "warning";  // 10w
      else if (base < 150) return "success";  // 15w
      else if (base < 200) return "";         // 20w
      else if (base > 200) return "danger";   // more
      // :color="salaryColor(scope.row.salary, scope.row.salaryMonth)"后期可以改成颜色代码
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection)
    },
    // 打开抽屉
    showDrawer(id){
      this.drawer = true
      this.selectedId = id
      this.getRecruitmentInfo(id)
    },
    // 关闭抽屉
    handleClose(done) {
      if (this.loading) {
        return;
      }
      this.$confirm('确定要提交表单吗？')
        .then(_ => {
          this.loading = true;
          this.timer = setTimeout(() => {
            done();   // 框架封装的关闭抽屉方法
            // 动画关闭需要一定的时间，心理作用
            setTimeout(() => {
              this.loading = false;
            }, 400);
          }, 2000);
        })
        .catch(_ => {});
    },
    // 获取详细信息
    getRecruitmentInfo(){
      this.$http
        .get(`http://localhost:9000/job/recruitment/findById/${this.selectedId}`)
        .then((resp) => {
          if (resp.data.code != 2000) {
            //操作错误，友好提示
            this.$message({
              type: "error",
              message: "失败! 错误码:" + resp.data.code,
            });
            return;
          }
          this.recruitmentInfo = resp.data.data;
        });
    },
    // 取消提交表单
    cancelForm() {
      this.loading = false;
      this.drawer = false;
      clearTimeout(this.timer);
    }
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