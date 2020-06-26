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
                <userInfo :userInfo="this.$store.state.userInfo"/>
              </el-tab-pane>
              <el-tab-pane label="收藏文章列表" name="clipArticles">
                <ArticleList :articles="this.clipArticles"/>
              </el-tab-pane>
              <el-tab-pane label="我发布的文章" name="myArticles">
                <ArticleList :articles="this.myArticles"/>
              </el-tab-pane>
              <el-tab-pane label="我关注的人的动态" name="followArticles">
                <ArticleList :articles="this.followArticles"/>
              </el-tab-pane>
              <el-tab-pane label="我关注的人" name="follows">
                <UserList :follows="this.follows"/>
              </el-tab-pane>
              <el-tab-pane label="我的粉丝" name="fans">
                <UserList :fans="this.fans"/>
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
  import UserList from './components/UserList'
  import qs from 'querystring'


  export default {
    name: 'Profile',
    components: {UserCard, UserInfo, ArticleList, UserList},
    data() {
      return {
        user: {},
        activeTab: 'userInfo',
        myArticles: [],
        clipArticles: [],
        followArticles: [],
        follows: [],
        fans: []
      }
    },
    computed: {
      ...mapGetters([
        'name',
        'avatar',
        'roles'
      ])
    },
    mounted() {
      this.getUser();
      this.getClipArticle();
      this.getMyArticles();
      this.getFollowArticles();
      this.getFollows();
      this.getFans();
      // this.getUserInfo();
    },
    methods: {
      getUser() {
        this.user = {
          // name: this.$store.state.userInfo.name,
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
        let url = settings.apiUrl + 'clips/'+ self.$store.getters.id;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',

          },
        }).then((result, error) => {
          if (error) {
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            self.clipArticles = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      },
      getMyArticles() {
        let self = this;
        let url = settings.apiUrl + 'articles/authorId=' + self.$store.getters.id;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',

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
      getFollowArticles() {
        let self = this;
        let url = settings.apiUrl + 'articles/fansId=' + self.$store.getters.id;
        request.request({
          url,
          method: "get",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',

          },
        }).then((result, error) => {
          if (error) {
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            self.followArticles = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      },
      getFollows() {
        request.get(settings.apiUrl + "follows/"+ this.$store.state.user.id).then(result => {
          this.follows = result.data
        });
      },
      getFans() {
        request.get(settings.apiUrl + "follows/fans/" + this.$store.state.user.id).then(result => {
          this.fans = result.data
        })
      }

    }
  }
</script>
