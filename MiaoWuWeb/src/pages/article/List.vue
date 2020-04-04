<template>
  <div class="article_list">
    <div class="btns">
      <el-button type="primary" size="small" @click="toPublishArticle">发布文章</el-button>
    </div>
    <el-table :data="articles" style="width: 100%">
      <el-table-column prop="id" label="id" width="180" />
      <el-table-column prop="title" label="标题" width="180" />
      <el-table-column prop="authorId" label="作者id" width="180" />
      <el-table-column prop="animalId" label="动物id" width="180" />
      <el-table-column prop="writeDate" label="日期" width="180" />
      <!--      <el-table-column prop="address" label="地址" />-->
    </el-table>
  </div>
</template>

<script>
  import request from '@/utils/request'
export default {
  name: 'List',
  data() {
    return {
      articles:[]
    }
  },
  created() {
    request.get('http://localhost:8088/article/findAll').then((result)=>{
      this.articles = result.data;
      console.log(result.data);
    })
  },
  methods: {
    toPublishArticle(){
      //跳转到编辑页面
      this.$router.push({path:'/article/editor'})
    }
  }
}
</script>

<style scoped>

</style>
