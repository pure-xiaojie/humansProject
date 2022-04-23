<template>
  <div class="login">
    <el-form
        :rules="rules"
        ref="loginForm"
        v-loading="loading"
        element-loading-text="正在登录..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        :model="loginForm"
        class="loginContainer">
      <div style="display: flex;justify-content: center">
        <el-radio class="loginTitleLeft" v-model="radio" label="1">系统登录</el-radio>
        <el-radio class="loginTitleRight" v-model="radio" label="2">员工登录</el-radio>
       <!-- <h3 class="loginTitle">系统登录</h3>
        <h3 class="loginTitle">员工登录</h3>-->
      </div>

      <el-form-item prop="username">
        <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
                  placeholder="账户">
          <i slot="prefix" class="fa fa-user-circle-o  input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                  placeholder="密码" @keydown.enter.native="submitLogin">
          <i slot="prefix" class="fa fa-unlock-alt input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input size="normal" type="text" v-model="loginForm.code" auto-complete="off"
                  placeholder="验证码" @keydown.enter.native="submitLogin" style="width: 250px">
          <i slot="prefix" class="fa fa-check-circle input-icon"/>
        </el-input>
        <img :src="vcUrl" @click="updateVerifyCode" alt="" class="login-code">
      </el-form-item>
      <el-checkbox size="normal" class="loginRemember" v-model="checked">remember me</el-checkbox>

      <el-button size="medium" type="primary" style="width: 100%;" @click="submitLogin">
        <span v-if="!loading">登 录</span>
        <span v-else>登 录 中...</span>
      </el-button>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        loading: false,
        vcUrl: '/verifyCode?time='+new Date(),
        loginForm: {
          username: '15897476136',
          password: '126731',
          code:''
        },
        checked: true,
        rules: {
          username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}],
          code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
        },
        radio: '1'
      }
    },
    methods: {
      updateVerifyCode() {
        this.vcUrl = '/verifyCode?time='+new Date();
      },
      submitLogin() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.loading = true;

            if(this.radio == '1') {
              this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
                this.loading = false;
                if (resp) {
                  //初始化当前登录用户，websocket使用
                  this.$store.commit('INIT_CURRENT_USER',resp.obj);
                  //保存用户到session
                  window.sessionStorage.setItem('user',JSON.stringify(resp.obj));
                  //跳转到home页
                  let path = this.$route.query.redirect;
                  this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
                }else{
                  this.loginForm.code = '';
                  this.vcUrl = '/verifyCode?time='+new Date();
                }
              })
            } else {
              console.log("员工登录……");
              this.postKeyValueRequest('/empInfo/login', this.loginForm).then(resp => {
                this.loading = false;
                if (resp) {
                  //初始化当前登录用户，websocket使用
                  this.$store.commit('INIT_CURRENT_EMP',resp.obj);
                  //保存登录员工到session
                  window.sessionStorage.setItem('emp',JSON.stringify(resp.obj));
                  //跳转到home页
                  this.$router.replace('/empHome');
                }else{
                  this.loginForm.code = '';
                  this.vcUrl = '/verifyCode?time='+new Date();
                }
              })
            }

          } else {
            return false;
          }
        });
      }
    }
  }
</script>

<style>
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-image: url("../assets/images/bg.jpg");
    background-size: cover;
  }

  .loginContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 15px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .loginTitleLeft {
    margin: 15px 0 20px 20px;
    text-align: center;
    color: #707070;
  }
  .loginTitleRight {
    margin: 15px 20px 20px 0;
    text-align: center;
    color: #707070;
  }
  .login-code {
    width: 30%;
    height: 38px;
    float: right;
    cursor: pointer;
    margin-left: 8px;
  }

  .loginRemember {
    text-align: left;
    margin: 0px 0px 15px 0px;
  }
  .el-form-item__content{
    display: flex;
    align-items: center;
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
</style>
