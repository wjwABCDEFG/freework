<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>系统日志</span>
      </div>
      <el-form ref="form" :model="log" label-width="80px">
        <el-form-item label="错误日志">
          <el-input v-model="log.errlog"></el-input>
        </el-form-item>
        <el-form-item label="警告日志">
          <el-input v-model="log.warnlog"></el-input>
        </el-form-item>
        <el-form-item label="信息日志">
          <el-input v-model="log.infolog"></el-input>
        </el-form-item>
        <el-form-item label="详细日志">
          <el-input v-model="log.alllog"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ErrorLog",
  data() {
    return {
      log: {
        errlog: "file:///G:/IdeaProjects/freework/logs/log_error.log",
        warnlog: "file:///G:/IdeaProjects/freework/logs/log_warn.log",
        infolog: "file:///G:/IdeaProjects/freework/logs/log_info.log",
        alllog: "file:///G:/IdeaProjects/freework/logs/"
      }
    };
  },
  mounted() {
    // 调试时候方便在控制台通过vue.dataName输出data中的值
    window.vue = this;
  },
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 600px;
  margin: 50px auto;
}
</style>