<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="用户昵称" prop="name">
      <el-input v-model="user.name"/>
    </el-form-item>
    <el-form-item label="手机号码" prop="phone">
      <el-input v-model="user.phone" maxlength="11"/>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="user.address" maxlength="50"/>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="user.remark" maxlength="50"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    props: {
      users: {
        type: Object
      }
    },
    data() {
      return {
        user: this.users,
        // 表单校验
        rules: {
          name: [
            {required: true, message: "用户昵称不能为空", trigger: "blur"}
          ],
          address: [
            {required: true, message: "地址不能为空", trigger: "blur"}
          ],
          phone: [
            {required: true, message: "手机号码不能为空", trigger: "blur"},
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: "请输入正确的手机号码",
              trigger: "blur"
            }
          ]
        }
      };
    },
    methods: {
      //提交修改
      submit() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.putRequest("/user/info", this.user).then(resp => {
              if (resp) {
                this.initUser();
              }
            })
          }
        });
      },
      //重新获取
      initUser() {
        this.getRequest('/user/info').then(resp => {
          if (resp) {
            this.user = resp;
            window.sessionStorage.setItem("user", JSON.stringify(resp));
            this.$store.commit('INIT_CURRENT_USER', resp);
          }
        })
      },
      close() {
        this.$router.push({path: "/index"});
      }
    }
  };
</script>
