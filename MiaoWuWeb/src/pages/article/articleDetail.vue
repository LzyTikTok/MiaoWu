<template>
  <el-row v-loading="loading">
    <el-col :span="24">
      <div style="text-align: left;">
        <el-button type="text" icon="el-icon-back" @click="goBack" style="padding-bottom: 0px;">返回</el-button>
      </div>
    </el-col>
    <el-col :span="24">
      <div>
        <div><h3 style="margin-top: 0px;margin-bottom: 0px">{{article.title}}</h3></div>
        <div style="width: 100%;margin-top: 5px;display: flex;justify-content: flex-end;align-items: center">
<!--          <div style="display: inline; color: #20a0ff;margin-left: 50px;margin-right:20px;font-size: 12px;">-->
<!--            {{article.nickname}}-->
<!--          </div>-->
<!--          todo 浏览次数-->
<!--          <span style="color: #20a0ff;margin-right:20px;font-size: 12px;">浏览 {{article.pageView==null?0:article.pageView}}</span>-->
          <span style="color: #20a0ff;margin-right:20px;font-size: 12px;"> {{new Date(article.lastUpdate).getTime() | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          <el-tag type="success" v-for="(item,index) in article.tags" :key="index" size="small"
                  style="margin-left: 8px">{{item.tagName}}
          </el-tag>
          <span style="margin:0px 50px 0px 0px"></span>
        </div>
      </div>
    </el-col>
    <el-col>
      <div style="text-align: left" v-html="article.htmlContent">
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import request from '@/utils/request'
  import settings from '@/settings'
  import {ResultCode} from '@/utils/ResultCode'
  import {parseTime} from "../../utils";

  export default {

    name: "articleDetail",
    created() {
      this.getArticle();
    },
    data() {
      return {
        article: {},
        loading: ''
      }
    },
    methods: {
      getArticle() {
        let self = this;
        let url = settings.apiUrl + "article/cascadeFindArticleWithId" + "?articleId=" + self.$route.query.articleId;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
        }).then((result, error) => {
          if (error) {
            this.$message.error('服务器出错喵~');
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            this.article = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
          }
        })
      },
      goBack(){
        this.$router.go(-1);
      }
    },
  }
</script>

<style scoped>

</style>
