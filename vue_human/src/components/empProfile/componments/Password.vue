<template>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="80px"
           class="demo-ruleForm">
    <el-form-item label="旧密码" prop="oldPass">
      <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newPass">
      <el-input type="password" v-model="ruleForm.newPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submitForm('ruleForm')">提交</el-button>
      <el-button type="danger"  size="mini" @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    props: {
      user: {
        type: Object
      }
    },
    data() {
      //校验密码
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.newPass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          id: null,
          oldPass: '',
          newPass: '',
          checkPass: ''
        },
        rules: {
          oldPass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          newPass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ]
        },
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ruleForm.id = this.user.id;
            this.putRequest("/empInfo/pass", this.ruleForm).then(resp => {
              //重新登录
              if (resp) {
                window.sessionStorage.removeItem("emp");
                this.$confirm('修改成功，请重新登录！', '提示', {
                  confirmButtonText: '确定',
                  type: 'warning'
                }).then(() => {
                  this.$router.replace("/login");
                });
              }
            })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    }
  };
</script>
