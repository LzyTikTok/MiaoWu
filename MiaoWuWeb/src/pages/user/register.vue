<template>
  <div class="register-container">

  <el-button type="text" @click="back">返回</el-button>
    <el-form ref="registerForm" :model="registerForm" :rules = "rules" class = "register-form" autocomplete="on" label-position="left">
  <el-form-item prop="phone">
        <span class="svg-container">
          <i class="el-icon-phone"></i>
        </span>
    <el-input
      ref="phone"
      v-model="registerForm.phone"
      placeholder="电话号码"
      name="phone"
      type="text"
      tabindex="1"
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
        placeholder="密码"
        name="password"
        tabindex="2"
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
        v-model="registerForm.username"
        placeholder="昵称"
        name="username"
        type="text"
        tabindex="3"
      />
    </el-form-item>

      <el-form-item prop="idCode">
        <span class="svg-container">
          <i class="el-icon-bank-card"></i>
        </span>
        <el-input
          ref="idCode"
          v-model="registerForm.idCode"
          placeholder="身份证"
          name="idCode"
          type="text"
          tabindex="4"
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
      let phoneRegex = new RegExp("((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");
      let isPhoneMsg = "电话号码不合法";
      let isPhone = (rule, value, callback) => {
        self.validator(rule, value, callback, isPhoneMsg, phoneRegex)
      };
      let idCodeRegex = new RegExp("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
      let isIdCodeMsg = "身份证长度在 15 到 18 个字符";
      let isIdCode = (rule, value, callback) => {
       self.validator(rule,value, callback, isIdCodeMsg, idCodeRegex);
      };
      //第一代15位？？第二代18位
      let pwdRegex = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
      let isPwdCodeMsg = "密码为至少6位，至多16位的包含数字和字母的组合";
      let isPwd = (rule, value, callback) => {
        self.validator(rule, value, callback, isPwdCodeMsg, pwdRegex);
      };
      let userNameRegex = new RegExp("^[a-zA-z\u2E80-\u9FFF]{0,12}$");
      let isUserNameMsg = "用户名长度在 1 到 12 个字符";
      let isUserName = (rule, value, callback) => {
        self.validator(rule, value, callback, userNameRegex, isUserNameMsg);
      }
      return{
        registerForm:{},
        rules: {
          phone: [{required: true, trigger: 'blur'},
            { min: 13, max: 13, message: '电话为长度13位的数字', trigger: 'blur' },
            {validator:isPhone}
            ],
          password: [{required: true, trigger: 'blur'},
            { min: 6, max: 16, message: '密码为至少6位，至多16位的包含数字和字母的组合', trigger: 'blur' },
            {validator: isPwd}
            ],
          idCode: [{required: true, trigger: 'blur'},
            { min: 15, max: 18, message: '长度在 15 到 18 个字符', trigger: 'blur' },
            {validator: isIdCode}
            ],
          userName: [{required: true, trigger: 'blur'},
            { min: 1, max: 12, message: '用户名长度在 1 到 12 个字符', trigger: 'blur' },
            {validator: isUserName}
            ],
        },
        capsTooltip: false,
        passwordType: 'password'
      }

    },
    methods: {
      back(){
        this.$router.go(-1);
      },
      validator(rule, value, callback, message, regex){
        if (!regex.test(value)) {
          return callback(new Error(message))
        } else {
          callback()
        }
      },
      checkCapslock(e) {
        const { key } = e
        this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
      },
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
    }
  }
</script>
<style lang="scss">
  /* 修复input 背景不协调 和光标变色 */
  /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

  $bg:#283443;
  $light_gray:#fff;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .register-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  .register-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg:#2d3a4b;
  $dark_gray:#889aa4;
  $light_gray:#eee;

  .register-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;
    overflow: hidden;

    .register-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
      margin: 0 auto;
      overflow: hidden;
    }

    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }

    .thirdparty-button {
      position: absolute;
      right: 0;
      bottom: 6px;
    }

    @media only screen and (max-width: 470px) {
      .thirdparty-button {
        display: none;
      }
    }
  }

</style>
