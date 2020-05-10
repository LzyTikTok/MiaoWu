<template>
  <div class="article-contains">
    <div v-if="JSON.stringify(articles) === '{}'">
      <span>没有相关文章信息哦~</span>
    </div>
    <div class="articles" v-for="(article,index) in articles" v-bind:key="index">
      <div class="post">
        <div class="user-block">
          <img class="img-circle"
               :src="'https://wpimg.wallstcn.com/57ed425a-c71e-4201-9428-68760c0537c4.jpg'+avatarPrefix">
<!--          <span class="username text-muted">{{article.title}}</span>-->
          <el-link type="primary" @click="toArticleDetail(article.id)">{{article.title}}</el-link>
          <span class="description">{{article.authorName}}     {{new Date(article.lastUpdate).getTime() | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          <el-popconfirm
            confirmButtonText='删除'
            cancelButtonText='手滑了'
            icon="el-icon-info"
            iconColor="red"
            title="确定删除文章吗？"
            @onConfirm="delArticle(article.id)"
          >
            <el-button v-if="article.authorId === userId" slot="reference" type="danger" icon="el-icon-delete" circle
                       style="right: 0px; position: absolute"></el-button>
          </el-popconfirm>
        </div>
        <p>
          {{article.content}}
        </p>
        <ul class="list-inline">
          <li>
          <span class="link-black text-sm">
            {{article.thumbUp}}
            <img src="@/static/like.jpg" @click="handleLike(article)" alt="" style="height:10px"/>
          </span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>

  import request from '@/utils/request'
  import settings from '@/settings'
  import {ResultCode} from '@/utils/ResultCode'
  import qs from 'querystring'
  import {parseTime} from "../../../utils";
  import * as Vue from "autoprefixer/lib/declaration";

  const avatarPrefix = '?imageView2/1/w/80/h/80';
  const carouselPrefix = '?imageView2/2/h/440';

  export default {
    name: "ArticleList",
    data() {
      return {
        avatarPrefix: '',
        carouselPrefix: '',
      }
    },
    props: {
      articles: {
        type: Array,
        default: () => {
          return {
            thumbUp: 0
          }
        }
      }
    },
    computed: {
      userId() {
        return this.$store.state.userInfo.id;
      }
    },
    methods: {
      handleLike(article) {
        debugger;
        let self = this;
        let url = settings.apiUrl + "article/thumbUpOrDown";
        let form = {
          'userId': self.$store.state.userInfo.id,
          'articleId': article.id
        };
        request.request({
          url,
          method: "post",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          data: qs.stringify(form)
        }).then((result, error) => {
          if (error) {
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            //todo 页面不能实时响应
            self.$set(article, 'thumbUp', article.thumbUp + 1);
            // article.thumbUp++;
            // this.$message.success('点赞成功喵~');
          } else if (result.code === ResultCode.CancelSuccessCode) {
            self.$set(article, 'thumbUp', article.thumbUp - 1);
            // article.thumbUp--;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      },
      delArticle(articleId) {
        let self = this;
        let url = settings.apiUrl + "article/deleteById";
        let form = {
          'id': articleId,
        };
        request.request({
          url,
          //todo 页面未响应
          method: "delete",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          data: qs.stringify(form)
        }).then((result, error) => {
          if (error) {
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            this.$message.success('删除文章成功喵~');
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
          }
        })
      },
      toArticleDetail(articleId) {
        this.$router.push({path: '/article/articleDetail', query: {articleId: articleId}})
      },
    },
    mounted() {
    }
  }
</script>


<style lang="scss" scoped>
  .user-block {
    .username,
    .description {
      display: block;
      margin-left: 50px;
      padding: 2px 0;
    }

    .username {
      font-size: 16px;
      color: #000;
    }

    :after {
      clear: both;
    }

    .img-circle {
      border-radius: 50%;
      width: 40px;
      height: 40px;
      float: left;
    }

    span {
      font-weight: 500;
      font-size: 12px;
    }
  }

  .post {
    font-size: 14px;
    border-bottom: 1px solid #d2d6de;
    margin-bottom: 15px;
    padding-bottom: 15px;
    color: #666;

    .image {
      width: 100%;
      height: 100%;

    }

    .user-images {
      padding-top: 20px;
    }
  }

  .list-inline {
    padding-left: 0;
    margin-left: -5px;
    list-style: none;

    li {
      display: inline-block;
      padding-right: 5px;
      padding-left: 5px;
      font-size: 13px;
    }

    .link-black {

      &:hover,
      &:focus {
        color: #999;
      }
    }
  }


  .box-center {
    margin: 0 auto;
    display: table;
  }

  .text-muted {
    color: #777;
  }
</style>

