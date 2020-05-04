<template>
  <div class="article-contains">
    <div class="articles" v-for="article in articles" v-bind:key="article.id" >
      <div class="post">
        <div class="user-block">
          <img class="img-circle"
               :src="'https://wpimg.wallstcn.com/57ed425a-c71e-4201-9428-68760c0537c4.jpg'+avatarPrefix">
           <span class="username text-muted">{{article.title}}</span>
<!--          <span class="title">{{article.title}}</span>-->
          <span class="description">{{article.authorName}}     {{new Date(article.lastUpdate).getTime() | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          <!-- <span class="description">Shared publicly - 7:30 PM today</span> -->
        </div>
        <p>
          {{article.content}}
          <!-- Lorem ipsum represents a long-held tradition for designers,
          typographers and the like. Some people hate it and argue for
          its demise, but others ignore the hate as they create awesome
          tools to help create filler text for everyone from bacon lovers
          to Charlie Sheen fans. -->
        </p>
        <ul class="list-inline">
          <!-- <li>
            <span class="link-black text-sm">
              <i class="el-icon-share"/>
              Share
            </span>
          </li> -->
          <li>
          <span class="link-black text-sm">
            {{article.thumpUp}}
            <img src="@/static/like.jpg" @click="handleLike(article.id)" alt="" style="height:10px"/>
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
        type: Object,
        default: () => {
          return {
          }
        }
      }
    },
    methods: {
      handleLike(articleId) {
        let url = settings.url + "article/thumbUpOrDown";
        let form = {
          userId: self.$store.state.userInfo.id,
          articleId: articleId
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
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            this.$store.state.clipArtilces = JSON.parse(result.data);
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      }
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

      .username{
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

