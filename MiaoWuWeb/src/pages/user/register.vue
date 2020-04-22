<template>
  <div class="register-container">

  <el-button type="text" @click="back">返回</el-button>
    <el-form :rules = "rules" class = "register-container">
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

      <el-form-item prop="idCode">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="idCode"
          v-model="registerForm.idCode"
          placeholder="idCode"
          name="idCode"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
  export default {
    name: "register",
    data(){
      var self = this;
      let phoneRegex = new RegExp("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$");
      let isPhoneMsg = "请输入正确的电话号码";
      let isPhone = (rule, value, callback) => {
        self.validator(rule, value, callback, isPhoneMsg, phoneRegex)
      };
      let idCodeRegex = new RegExp("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
      let isIdCodeMsg = "请输入正确的身份证";
      let isIdCode = (rule, value, callback) => {
       self.validator(rule,value, callback, isIdCodeMsg, idCodeRegex);
      };
      //第一代15位？？第二代18位
      let pwdRegex = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
      let isPwdCodeMsg = "请输入正确的身份证";
      let isPwd = (rule, value, callback) => {
        self.validator(rule, value, callback, isPwdCodeMsg, pwdRegex);
      };
      let userNameRegex = new RegExp("^[a-zA-z\u2E80-\u9FFF]{0,12}$");
      let isUserNameMsg = "请输入正确的用户名";
      let isUserName = (rule, value, callback) => {
        self.validator(rule, value, callback, userNameRegex, isUserNameMsg);
      }
      return{
        registerForm:{},
        rules: {
          phone: [{required: true, message: '请输入电话', trigger: 'blur'},
            { min: 13, max: 13, message: '电话为长度13位的数字', trigger: 'blur' },
            {validator:isPhone}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'},
            { min: 6, max: 16, message: '密码为至少6位，至多16位的包含数字和字母的组合', trigger: 'blur' },
            {validator: isPwd}],
          idCode: [{required: true, message: '请输入身份证', trigger: 'blur'},
            { min: 15, max: 18, message: '长度在 15 到 18 个字符', trigger: 'blur' },
            {validator: isIdCode}],
          userName: [{required: true, message: '请输入用户名', trigger: 'blur'},
            { min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur' },
            {validator: isUserName}],
        }
      }

    },
    methods: {
      back(){
        this.$router.go(-1);
      },
      validator(rule, value, message, callback, regex){
        if (!regex.test(value)) {
          return callback(new Error(message))
        } else {
          callback()
        }
      }
    }
  }
</script>

<style scoped>

</style>
