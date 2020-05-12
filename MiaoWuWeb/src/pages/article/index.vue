<template>
  <div>
    <h5>欢迎来到喵呜</h5>
    <h5>{{testObject}}</h5>
    <el-button @click="change">改变</el-button>
    <h5 v-show="this.testObject.age === 15">等于15</h5>
  </div>
</template>

<script>
  import request from '@/utils/request'
  import settings from '../../settings'
  import {ResultCode} from '@/utils/ResultCode'
  import qs from 'querystring'
export default {
  name: "index",
  data() {
    return {
      articles: [],
      restaurants: [],
      state: "",
      timeout: null,
      testObject: {
        name: "小王",
        age: "12",
      }
    };
  },
  created() {
    this.login();
  },
  methods: {
    login(){
      let self = this;
      let url = settings.apiUrl + 'user/login';
      request.request({
        url,
        method: "post",
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'auth': window.localStorage.getItem('auth')
        },
      }).then((result, error) => {
        if (error) {
          return;
        }
        if (result.code === ResultCode.SuccessCode) {
          self.$store.state.userInfo = result.data;
        } else if (result.code === ResultCode.ServerInnerError) {
          this.$message.error('服务器出错喵~');
        }
      })
    },
    change(){
        this.testObject.age = "15";
    }
  },
  mounted() {}
};
</script>

<style scoped>
.image {
  height: 200px;
}
</style>
