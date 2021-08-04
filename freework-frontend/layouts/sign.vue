<template>
  <div class="sign">
    <!--标题-->
    <!-- <div class="bg-btn hunter" :class="{'pick': !isHr}" @click="chgPick(false)">
      <img src="~/assets/img/logo.png" alt="logo" class="logo-css" />
      <p class="caption">我要找工作</p>
    </div>
    <div class="bg-btn hr" :class="{'pick': isHr}" @click="chgPick(true)">
      <img src="~/assets/img/logo.png" alt="logo" class="logo-css" />
      <p class="caption">我要招人才</p>
    </div> -->
    <!--表单-->
    <nuxt />
  </div>
</template>

<script>
import cookie from "js-cookie";

export default {
  data() {
    return {
      isHr: false,
    };
  },
  methods: {
    chgPick(val) {
      this.isHr = val;
      let roleId = this.isHr ? '1352161213957558274' : '1351962010769752065';
      cookie.set("role", roleId, {
        domain: "localhost",
      });
    },
  },
};
</script>

<style>
.logo-css {
  width: 200px;
  margin-top: 200px;
}

.sign .bg-btn {
  position: absolute;
  /* background-color: red; */
  top: -20px;
  cursor: pointer;
  width: 50%;
  height: 100%;
}

.hunter {
}

.hr {
  left: 50%;
}

.pick {
  background-color: #ff7e00;
}

.sign .caption{
  margin-top: 20px;
  font-size: 30px;
}
</style>