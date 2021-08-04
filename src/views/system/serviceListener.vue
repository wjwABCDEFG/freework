<template>
  <div>
    <el-card class="box-card">
      <el-table :data="serviceList" style="width: 100%">
        <el-table-column prop="name" label="服务名" width="180"></el-table-column>
        <el-table-column prop="clusterCount" label="集群数目"></el-table-column>
        <el-table-column prop="ipCount" label="实例数目"></el-table-column>
        <el-table-column prop="healthyInstanceCount" label="健康数目"></el-table-column>
      </el-table>
    </el-card>
    <el-link type="primary" class="link-css" href="http://localhost:8848/nacos/#/serviceManagement?dataId=&group=&appName=&namespace=" target="_blank" rel="noopener noreferrer">点击查看详情</el-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      serviceList: [],
    };
  },

  methods: {
    getService() {
      this.$http
        .get(
          `http://localhost:8848/nacos/v1/ns/catalog/services?hasIpCount=true&withInstances=false&pageNo=1&pageSize=10&serviceNameParam=&groupNameParam=&accessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYWNvcyIsImV4cCI6MTYyMDY1MDQ5M30.kzKJUUsuDfAH4j5GTfylotAMeI7gX-qPg7n9UBVWyFQ&namespaceId=`
        )
        .then((resp) => {
          // console.log(resp.data);
          this.serviceList = resp.data.serviceList;
        });
    },
  },

  created() {
    this.getService();
  },
};
</script>

<style>
.box-card{
  width: 70%;
  margin: 20px auto;
}

.link-css{
  margin-left: 15%;
}
</style>