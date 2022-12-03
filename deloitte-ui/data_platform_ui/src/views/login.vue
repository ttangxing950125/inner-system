<template>
  <div class="login">
    <div class="login-form">
      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        class="form-content"
      >
        <h3 class="title">数据资产平台</h3>
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            placeholder="用户名"
            prefix-icon="el-icon-user"
          >
            <!-- <svg-icon
              slot="prefix"
              icon-class="el-icon-user"
              class="el-input__icon input-icon"
            /> -->
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            prefix-icon="el-icon-unlock"
            @keyup.enter.native="handleLogin"
          >
            <!-- <svg-icon
              slot="prefix"
              icon-class="password"
              class="el-input__icon input-icon"
            /> -->
          </el-input>
        </el-form-item>
        <!-- <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon
              slot="prefix"
              icon-class="validCode"
              class="el-input__icon input-icon"
            />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img" />
          </div>
        </el-form-item> -->
        <!-- <el-checkbox
          v-model="loginForm.rememberMe"
          style="margin: 0px 0px 25px 0px"
          >记住密码</el-checkbox
        > -->
        <el-form-item style="width: 100%">
          <el-button
            :loading="loading"
            class="login-btn pointer"
            @click="handleLogin"
            >{{ loading ? "登 录 中..." : "登 录" }}</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
// import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        // code: "",
        // uuid: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
        ],
        // code: [{ required: true, trigger: "change", message: "请输入验证码" }],
      },
      loading: false,
      // 验证码开关
      // captchaEnabled: true,
      // 注册开关
      // register: false,
      redirect: undefined,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    // this.getCode();
    this.getCookie();
  },
  methods: {
    // getCode() {
    //   getCodeImg().then((res) => {
    //     this.captchaEnabled =
    //       res.captchaEnabled === undefined ? true : res.captchaEnabled;
    //     if (this.captchaEnabled) {
    //       this.codeUrl = "data:image/gif;base64," + res.img;
    //       this.loginForm.uuid = res.uuid;
    //     }
    //   });
    // },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          // if (this.loginForm.rememberMe) {
          Cookies.set("username", this.loginForm.username, { expires: 30 });
          Cookies.set("password", encrypt(this.loginForm.password), {
            expires: 30,
          });
          // Cookies.set("rememberMe", this.loginForm.rememberMe, {
          //   expires: 30,
          // });
          // }
          // else {
          //   Cookies.remove("username");
          //   Cookies.remove("password");
          //   Cookies.remove("rememberMe");
          // }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" }).catch(() => {});
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                // this.getCode();
              }
            });
        }
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: flex-end;
  height: 100%;
  background-image: url("../assets/images/log.png");
  background-size: 100% 100%;
}

.login-form {
  background: #ffffff;
  width: 544px;
  margin: 32px 30px 32px 0;
  display: flex;
  align-items: center;
  padding: 0 92px;
  .form-content {
    width: 100%;
    .title {
      font-size: 22px;
      color: #444e5a;
      text-align: center;
      font-weight: 400;
      padding-bottom: 80px;
    }
  }
  .el-input__inner {
    border-radius: 0;
    border-top: none !important;
    border-right: none !important;
    border-left: none !important;
  }
}
.login-btn {
  width: 100%;
  color: #ffffff;
  background: #444e5a;
  border-radius: 18px;
  text-align: center;
  height: 40px;
}
.login-btn:hover {
  width: 100%;
  color: #ffffff;
  background: #444e5a;
  border-radius: 18px;
  text-align: center;
  height: 40px;
}

// .login-code {
//   width: 33%;
//   height: 38px;
//   float: right;
//   img {
//     cursor: pointer;
//     vertical-align: middle;
//   }
// }

// .login-code-img {
//   height: 38px;
// }
</style>
