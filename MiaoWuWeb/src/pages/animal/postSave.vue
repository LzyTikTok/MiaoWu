<template>
  <div class="container">
    <el-select v-model="form.animalId" placeholder="请选择要提交申请的动物" style="width: 200px;">
      <el-option
        v-for="item in foundAnimals"
        :key="item.id"
        :label="item.name"
        :value="item.id">
      </el-option>
    </el-select>
    <el-select v-model="form.userId" placeholder="请选择要救助的用户" style="width: 200px;">
      <el-option
        v-for="item in followUsers"
        :key="item.id"
        :label="item.name"
        :value="item.id">
      </el-option>
    </el-select>

    <el-popconfirm
      confirmButtonText='提交'
      cancelButtonText='手滑了'
      icon="el-icon-info"
      iconColor="red"
      title="确定提交救助动物吗？"
      @onConfirm="postSave()"
    >
      <el-button v-if="article.authorId === userId" slot="reference" type="danger" icon="el-icon-delete" circle
                 style="right: 0px; position: absolute"></el-button>
    </el-popconfirm>
  </div>
</template>

<script>
  import request from '@/utils/request'
  import settings from '@/settings'
  import {ResultCode} from '@/utils/ResultCode'
  import qs from 'querystring'
    export default {
        name: "postSave",
        data(){
          return{
            form:{},
            foundAnimals: [],
            followUsers: []
          }
        },
      mounted() {
        this.getFoundAnimals();
        this.getFollowUsers();
      },
      methods: {
        getFoundAnimals() {
          let url = settings.apiUrl + "animals/userId=" + this.$store.getters.id;
          let self = this;
          request.request({
            url,
            method: "get",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',

            },
          }).then((res, error) => {
            if (error) {
              this.loading = false;
              return;
            }
            if (res.code === ResultCode.SuccessCode) {
              this.foundAnimals = res.data;
              this.loading = false;
            } else if (res.code === ResultCode.BadRequest) {
              self.$message.error(res.message);
              this.loading = false;
              reject();
            } else if (res.code === ResultCode.ServerInnerError) {
              this.$message.error('服务器出错喵~');
              this.loading = false;
            }
          })
        },
        getFollowUsers(){
            let url = settings.apiUrl + "users/getAllFollowByUserId?userId=" + this.$store.getters.id;
            let self = this;
            request.request({
              url,
              method: "get",
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded',

              },
            }).then((res, error) => {
              if (error) {
                this.loading = false;
                return;
              }
              if (res.code === ResultCode.SuccessCode) {
                this.followUsers = res.data;
                this.loading = false;
              } else if (res.code === ResultCode.BadRequest) {
                self.$message.error(res.message);
                this.loading = false;
                reject();
              } else if (res.code === ResultCode.ServerInnerError) {
                this.$message.error('服务器出错喵~');
                this.loading = false;
              }
            })
        },
        postSave(){
          let url = settings.apiUrl + "saves";
          let self = this;
          let form = {
            'userId': self.form.userId,
            'animalId': self.form.animalId,
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
              this.$message.success("提交救助成功啦喵");
              this.loading = false;
            } else if (res.code === ResultCode.BadRequest) {
              self.$message.error(res.message);
              this.loading = false;
              reject();
            } else if (res.code === ResultCode.ServerInnerError) {
              this.$message.error('服务器出错喵~');
              this.loading = false;
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
