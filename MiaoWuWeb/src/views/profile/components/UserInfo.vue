<template>
<!--todo 过滤器 <-->
  <el-form>
  <el-form-item label="昵称">
    <el-input v-model="user.name" />
  </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="user.password" />
    </el-form-item>
    <el-form-item label="身份证">
      <el-input v-model="user.idCode" :disable="false"
      />
    </el-form-item>
    <el-form-item label="电话号码">
      <el-input v-model="user.phone" />
    </el-form-item>
<!--  <el-form-item label="Email">-->
<!--    <el-input v-model.trim="user.email" />-->
<!--  </el-form-item>-->
  <el-form-item>
    <el-button type="primary" @click="submit">提交修改</el-button>
  </el-form-item>
  </el-form>
</template>

<script>
  import request from '@/utils/request'
  import settings from '@/settings'
  import {ResultCode} from '@/utils/ResultCode'
  import qs from 'querystring'
    export default {
      name: "userInfo",
      props: {
        user: {
          type: Object,
          default: () => {
            return {
              name: "",
              idCode: "",
              password: "",
              phone: "",
            }
          }
        }
      },
      methods: {
        submit() {
          //todo invalidate 未测试
          let url = settings.apiUrl + 'user/updateUserInfo';
          request.request({
            url,
            method: "post",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: qs.stringify(this.user)
          }).then((result,error)=>{
            if(error){
              this.loading = false;
              return ;
            }
            if(result.code === ResultCode.SuccessCode){
              // this.$router.push({ path: this.redirect || '/', query: this.otherQuery });
              this.$message.success("更新信息成功啦喵");
              // self.$store.state.userInfo = result.data;
              // window.localStorage.setItem("auth",result.data);
              this.loading = false;
            } else if (result.code === ResultCode.BadRequest){
              this.$message.error('参数错误');
              this.loading = false;
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
