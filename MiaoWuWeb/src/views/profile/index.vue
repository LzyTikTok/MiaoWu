<template>
  <div class="app-container">
    <div v-if="user">
      <el-row :gutter="20">

        <el-col :span="6" :xs="24">
          <user-card :user="user"/>
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="我的信息" name="userInfo">
                <userInfo :user="this.$store.state.userInfo"/>
              </el-tab-pane>
              <el-tab-pane label="收藏文章列表" name="clipArticles">
                <ArticleList :articles="this.clipArticles"/>
              </el-tab-pane>
                <el-tab-pane label="我发布的文章" name="myArticles">
                <ArticleList :articles="this.myArticles"/>
              </el-tab-pane>
              <el-tab-pane label="我关注的人的动态" name="myArticles">
                <ArticleList :articles="this.followArticles"/>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import UserCard from './components/UserCard'
  import UserInfo from './components/UserInfo'
  import ArticleList from './components/ArticleList'
  import request from '@/utils/request'
  import settings from '../../settings'
  import {ResultCode} from '@/utils/ResultCode'
  import qs from 'querystring'


  export default {
    name: 'Profile',
    components: {UserCard, UserInfo, ArticleList},
    data() {
      return {
        user: {},
        activeTab: 'userInfo'
      }
    },
    computed: {
      ...mapGetters([
        'name',
        'avatar',
        'roles'
      ])
    },
    created() {
      this.getUser();
      this.getClipArticle();
      this.getMyArticles();
      // this.getUserInfo();
    },
    methods: {
      getUser() {
        this.user = {
          name: this.name,
          role: this.roles.join(' | '),
          email: 'admin@test.com',
          avatar: this.avatar
        }
      },
      getClipArticle() {
        if (settings.isDebug) {
          this.$store.state.clipArticles = {
            0: {
              title: '英短',
              content: '求收养'
            }
          };
          return;
        }
        let self = this;
        let url = settings.apiUrl + 'article/findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc' + "?userId=" + self.$store.state.userInfo.id ;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
        }).then((result, error) => {
          if (error) {
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            self.clipArticles = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            debugger;
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      },
      getMyArticles(){
        let self = this;
        let url = settings.apiUrl + 'article/findByAuthorIdOrderByUpdateDesc' + "?authorId=" + self.$store.state.userInfo.id ;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
        }).then((result, error) => {
          if (error) {
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            self.myArticles = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      },
      //todo 未测试
      getfollowArticles(){
        let self = this;
        let url = settings.apiUrl + 'article/findFollowsArticleByUserIdOrderByUpdateDesc' + "?userId=" + self.$store.state.userInfo.id ;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
        }).then((result, error) => {
          if (error) {
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            self.myArticles = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      }
    }
  }
</script>
