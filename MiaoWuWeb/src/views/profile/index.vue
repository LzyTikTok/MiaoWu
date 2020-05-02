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
              <el-tab-pane label="UserInfo" name="用户信息">
                <userInfo :user="this.$store.state.userInfo"/>
              </el-tab-pane>
              <el-tab-pane label="Activity" name="activity">
                <activity/>
              </el-tab-pane>
              <el-tab-pane label="Account" name="account">
                <account :user="user"/>
              </el-tab-pane>
              <el-tab-pane label="ClipArticle" name="收藏文章列表">
                <account :articles="this.$store.state.clipArtilces"/>
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
  import Activity from './components/Activity'
  import Account from './components/Account'
  import UserInfo from './components/UserInfo'
  import ClipArticle from './components/ClipArticle'
  import request from '@/utils/request'
  import settings from '../../settings'
  import qs from 'querystring'


  export default {
    name: 'Profile',
    components: {UserCard, Activity, Account, UserInfo, ClipArticle},
    data() {
      return {
        user: {},
        activeTab: 'activity'
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
        let self = this;
        let url = settings.apiUrl + 'article/getClipArticleByUserIdDesc';
        request.request({
          url,
          method: "post",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          data: qs.stringify(self.userInfo.id)
        }).then((result, error) => {
          if (error) {
            this.loading = false;
            return;
          }
          if (result.code === ResultCode.SuccessCode) {
            this.$store.state.clipArtilces = result.data;
          } else if (result.code === ResultCode.ServerInnerError) {
            this.$message.error('服务器出错喵~');
            this.loading = false;
          }
        })
      }
    }
  }
</script>
