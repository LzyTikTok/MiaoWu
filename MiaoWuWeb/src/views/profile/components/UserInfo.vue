<template>
<!--todo 过滤器 <-->
  <el-form>
  <el-form-item label="昵称">
    <el-input v-model.trim="user.name" />
  </el-form-item>
    <el-form-item label="密码">
      <el-input v-model.trim="user.password" />
    </el-form-item>
    <el-form-item label="身份证">
      <el-input v-model.trim="user.idCode" :disable="false"
      />
    </el-form-item>
    <el-form-item label="电话号码">
      <el-input v-model.trim="user.phone" />
    </el-form-item>
<!--  <el-form-item label="Email">-->
<!--    <el-input v-model.trim="user.email" />-->
<!--  </el-form-item>-->
  <el-form-item>
    <el-button type="primary" @click="submit">Update</el-button>
  </el-form-item>
  </el-form>
</template>

<script>
    export default {
      name: "userInfo",
      props: {
        user: {
          type: Object,
          default: () => {
            return {
              name: '',
              birthday: "",
              id: 2,
              idCode: "",
              password: "",
              phone: "",
            }
          }
        }
      },
      methods: {
        //todo 更新用户信息
        submit() {
          this.$message({
            message: 'User information has been updated successfully',
            type: 'success',
            duration: 5 * 1000
          })
        }
      }
    }
</script>

<style scoped>

</style>
