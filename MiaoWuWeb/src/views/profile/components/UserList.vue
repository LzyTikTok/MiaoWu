<template>
  <el-row>
    <el-col :span="8" v-for="(user, index) in users" :key="index" :offset="2">
      <el-card :body-style="{ padding: '0px' }" style>
        <img class="avator" src="@/static/profile.jpg" />
        <el-button type="primary" v-if="follows">取消关注</el-button>
        <div style="padding: 14px;">
          <p type="primary" @click="toArticleDetail(article.id)">{{user.name}}</p>
          <div class="bottom clearfix" style="font-size: 11px"></div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "userList",
  props: {
    fans: {
      type: Array,
      deafult: () => []
    },
    follows: {
      type: Array,
      deafult: () => []
    }
  },
  watch: {
    fans(val) {
      this.users = val;
    },
    follows(val) {
      this.users = val;
    }
  },
  data() {
    return {
      users: []
    };
  }
};
</script>

<style scoped>
.avator {
  width: 50px;
  border-radius: 50%;
}
</style>
