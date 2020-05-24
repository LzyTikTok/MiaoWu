<template>
  <div>
    <h5>欢迎来到喵呜</h5>
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
    };
  },
  created() {
    this.login();
  },
  methods: {
    login(){
      let self = this;
      let url = settings.apiUrl + 'user/info' + '?token=' + window.localStorage.getItem('token');
      request.request({
        url,
        method: "get",
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',

        },
      }).then((result, error) => {
        if (error) {
          return;
        }
        if (result.code === ResultCode.SuccessCode) {
          // debugger;
          // self.$store.state.userInfo = JSON.parse(result.data);
        } else if (result.code === ResultCode.ServerInnerError) {
          this.$message.error('服务器出错喵~');
        }
      })
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
