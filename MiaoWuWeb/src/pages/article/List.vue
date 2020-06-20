<template>
  <el-row>
    <el-col :span="8" v-for="(article, index) in articles" :key="index" :offset="2">
      <el-card :body-style="{ padding: '0px' }" style="">
        <img src="@/static/defaultCat.jpg"
             class="image"  @click.native="toArticleDetail(article.id)">
        <div style="padding: 14px;">
          <el-link type="primary" @click="toArticleDetail(article.id)">{{article.title}}</el-link>
          <a @click="clipArticle(article)">
            <i :class="article.clip !== null ?'el-icon-star-on':'el-icon-star-off'" ></i>
          </a>
          <div class="bottom clearfix" style="font-size: 11px">
            <time class="time">{{new Date(article.lastUpdate).getTime() | parseTime('{y}-{m}-{d} {h}:{i}')}}</time>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  import request from '@/utils/request'
  import settings from '@/settings'
  import {ResultCode} from '@/utils/ResultCode'
  import qs from 'querystring'
  import {parseTime} from "@/utils/index";

  export default {
    name: 'List',
    data() {
      return {
        articles: [],
        restaurants: [],
        state: '',
        timeout: null
      }
    },
    created() {
      if(this.$store.getters.id){
        request.get(settings.apiUrl + 'articles/findAllWithClipByUserIdOrderByUpdateDesc' + '?userId=' + this.$store.getters.id).then((result) => {
          this.articles = result.data;
        })
      } else{ //游客
        request.get(settings.apiUrl + 'articles/findAll').then(result =>{
          this.articles = result.data;
        })
      }

    },
    methods: {
      toPublishArticle() {
        //跳转到编辑页面
        this.$router.push({path: '/article/editor'})
      },
      loadAll() {

      },
      querySearchAsync(queryString, cb) {
        var restaurants = this.restaurants;
        var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item);
      },
      handleLike() {

      },
      toArticleDetail(articleId) {
        this.$router.push({path: 'articleDetail', query: {articleId: articleId}})
      },
      clipArticle(article) {
        if (article.clip === null) {
          let self = this;
          let url = settings.apiUrl + "clips"
          if(!self.$store.state.user){
            self.$message.error("尚未登录");
          }
          let form = {
            'userId': self.$store.getters.id,
            'articleId': article.id
          }
          request.request({
            url,
            method: "post",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',

            },
            data: qs.stringify(form)
          }).then((result, error) => {
            if (error) {
              this.$message.error('服务器出错喵~');
            } else if (result.code === ResultCode.SuccessCode) {
              article.clip = {};
              this.$message.success("收藏文章成功喵~");
            } else if (result.code === ResultCode.ServerInnerError) {
              this.$message.error('服务器出错喵~');
            }
          })
        }else {
          article.clip = false;
          let self = this;
          let url = settings.apiUrl + "clips"
          let form = {
            'userId': self.$store.getters.id,
            'articleId': article.id
          }
          request.request({
            url,
            method: "DELETE",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',

            },
            data: qs.stringify(form)
          }).then((result, error) => {
            if (error) {
              this.$message.error('服务器出错喵~');
            }else if (result.code === ResultCode.SuccessCode) {
              this.$set(article, "clip", null);
              this.$message.success("删除收藏成功喵~");
            } else if (result.code === ResultCode.ServerInnerError) {
              this.$message.error('服务器出错喵~');
            }
          })
        }
      },
      mounted() {
      },
    }
  }
</script>

<style scoped>
.image {
  height: 200px;
}
</style>
