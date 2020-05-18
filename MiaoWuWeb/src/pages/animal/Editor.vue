<template>
  <div class="container">
    <el-button type="text" @click="back">返回</el-button>
    发现动物
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="动物昵称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender" placeholder="请选择动物性别">
          <el-option label="雄性" value="male"></el-option>
          <el-option label="雌性" value="female"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="生日">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="选择生日" v-model="form.date" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">发布</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import request from '@/utils/request'
  import settings from "@/settings";
  import { ResultCode } from "@/utils/ResultCode";
  import qs from "querystring";
  export default {
    name: "Editor",
    data(){
      return{
        form: {}
      }
    },
    methods: {
      back() {
        this.$router.go(-1);
      },
      onSubmit(){
        let self = this;
        self.loading = true;
        let url = settings.apiUrl + "animal/add";
        let form = {
          'name': self.form.name,
          'gender': self.form.gender,
          'age': self.form.age,
          'date': self.form.date
        };
          request.request({
              url,
              method: "post",
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded',

              },
              data: qs.stringify(form)
            }).then((res, error) => {
              if (error) {
                this.loading = false;
                return;
              }
              if (res.code === ResultCode.SuccessCode) {
                 self.$message.success("发现动物成功啦喵");
                 self.loading = false;;
              } else if(res.code === ResultCode.BadRequest){
                 self.$message.error(res.message);
                 self.loading = false;
              }
              else if (res.code === ResultCode.ServerInnerError) {
                this.$message.error('服务器出错喵~');
                 self.loading = false;
              }
          })
      }
    }
  }
</script>

<style scoped>

</style>
