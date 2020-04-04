<template>
  <div class="register-container">

  <el-button type="text" @click="back">返回</el-button>
  <el-form-item prop="phone">
        <span class="svg-container">
          <i class="el-icon-phone"></i>
        </span>
    <el-input
      ref="phone"
      v-model="registerForm.phone"
      placeholder="Phone"
      name="phone"
      type="text"
      tabindex="1"
      autocomplete="on"
    />
  </el-form-item>

  <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
    <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
      <el-input
        :key="passwordType"
        ref="password"
        v-model="registerForm.password"
        :type="passwordType"
        placeholder="Password"
        name="password"
        tabindex="2"
        autocomplete="on"
        @keyup.native="checkCapslock"
        @blur="capsTooltip = false"
        @keyup.enter.native="handleLogin"
      />
      <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
    </el-form-item>
  </el-tooltip>

    <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
      <el-input
        ref="username"
        v-model="registerForm.phone"
        placeholder="Username"
        name="username"
        type="text"
        tabindex="1"
        autocomplete="on"
      />
    </el-form-item>

  </div>
</template>

<script>
  export default {
    name: "register",
    data(){
      return{
        registerForm:{}
      }
    },
    methods: {
      back(){
        this.$router.go(-1);
      }
    }
  }
</script>

<style scoped>

</style>
