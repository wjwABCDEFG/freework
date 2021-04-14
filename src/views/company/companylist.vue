<template>
<div>
  <!-- 公司列表table -->
  <div class="search-css">
    <el-form :inline="true" :model="companyQuery" class="demo-form-inline">
      <el-form-item label="公司名称">
        <el-input v-model="companyQuery.companyName" placeholder="freework"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-cascader
          v-model="selectedAddress"
          :options="address"
          @change="handleChangeAddress">
        </el-cascader>
      </el-form-item>
      <el-form-item label="行业">
        <el-select v-model="companyQuery.industry" clearable placeholder="请选择">
          <el-option
            v-for="item in industry"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-table
      :data="companyList"
      style="width: 100%">
      <el-table-column
        label="公司名称"
        prop="logo">
        <template slot-scope="scope">
          <div class="logo-height-css">
            <img :src="scope.row.logo" class="logo-css"/>
            <span>{{scope.row.companyName}}</span>        
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="地址"
        prop="address">
      </el-table-column>
      <el-table-column
        label="行业"
        prop="industry">
      </el-table-column>
      <el-table-column
        label="工作日"
        prop="workDay">
      </el-table-column>
      <el-table-column
        label="工作时间"
        prop="workTime">
      </el-table-column>
      <el-table-column
        align="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteCompany(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>


  <!-- 分页导航 -->
  <div class="pagination-css">
    <el-pagination
      background
      layout="total, prev, pager, next"
      @current-change="pageCompany"
      :current-page.sync="curPage"
      :page-size="pageSize"
      :total="totalNum">
    </el-pagination>
  </div>
  
</div>
</template>

<script>
  import { regionDataPlus, CodeToText } from 'element-china-area-data'
  export default {
    data() {
      return {
        companyList: [],
        curPage: 1,
        pageSize: 5,
        totalNum: 0,
        companyQuery: {},
        industry: {},
        address: regionDataPlus,
        selectedAddress: [],

      }
    },
    methods: {
      pageCompany(){
        this.$http.post(`http://localhost:9000/job/company/findPage/${this.curPage}/${this.pageSize}`, this.companyQuery).then((resp)=>{
          //console.log(resp.data);
          if(resp.data.code != 2000){
              //操作错误，友好提示
              this.$message({
                type: 'error',
                message: '查询失败! 错误码:' + resp.data.code
              });
            }else{
              this.companyList = resp.data.data.records
              this.totalNum = resp.data.data.total
            }
        }).catch();
      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      deleteCompany(id) {
        this.$confirm('此操作将永久删除该企业信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'

        }).then(() => {
          this.$http.delete(`http://localhost:9000/job/company/remove/${id}`).then((resp)=>{
            // console.log(resp.data);
            if(resp.data.code != 2000){
              //操作错误，友好提示
              this.$message({
                type: 'error',
                message: '删除失败! 错误码:' + resp.data.code
              });
            }else{
              this.pageCompany(); // 刷新页面
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }

          });
        });
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
      //级联选择器地址改变时触发条件查询
      handleChangeAddress(value) {
        // console.log(value[0] == '', value[1] == '', value[2] == null)
        let addressText = value[0] == '' ? '' : CodeToText[value[0]]
        for (let i = 1; i < value.length; i++) {
          if(value[i] != '') addressText = addressText + '-' + CodeToText[value[i]]
        }
        this.companyQuery.address = addressText
        // console.log(this.companyQuery)
        this.pageCompany()
      },
    },
    created() {
      this.pageCompany(this.curPage, this.pageSize)
      this.getIndustry()
    },
    // mounted(){   // 调试时候方便在控制台通过vue.dataName输出data中的值
    //   window.vue = this
    // },
    watch:{
      companyQuery: {
        handler(newVal,oldVal){
            console.log(newVal.companyName, newVal.address, newVal.industry)
            this.pageCompany()
        },
        deep: true  // 监控对象内所有属性的变化
      },
    }
  }
</script>

<style>
  .search-css{
    margin: 10px;
  }
  .pagination-css{
    float: right;
    margin: 20px;
  }
  .logo-height-css{
    height: 60px;   /* logo外层div限定高度 */
  }
  .logo-css{
    height: 100%;   /* logo本身会扩大至外层同高 */
    vertical-align:middle;
  }
</style>