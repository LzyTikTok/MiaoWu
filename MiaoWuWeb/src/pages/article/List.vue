<template>
  <div class="article_list">
    <div class="btns">
      <el-button type="primary" size="small">文章管理</el-button>
    </div>
    <el-table :data="articles" style="width: 100%">
      <el-table-column prop="id" label="日期" width="180" />
      <el-table-column prop="name" label="姓名" width="180" />
      <el-table-column prop="address" label="地址" />
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'List',
  data() {
    return {
      tableData: []
    }
  }
}
</script>

<style scoped>

</style>
