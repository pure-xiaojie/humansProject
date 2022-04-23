<template>
  <div class="app-container">
    <div v-if="emp">
      <el-row :gutter="20">
        <el-col :span="8" :xs="24">
          <user-card :users="emp"/>
        </el-col>
        <el-col :span="16" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="登录时间" name="timeline">
                <timeline :user="emp"/>
              </el-tab-pane>
              <el-tab-pane label="账号信息" name="account">
                <account :users="emp"/>
              </el-tab-pane>
              <el-tab-pane label="个人密码" name="password">
                <password :user="emp"/>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import UserCard from "./componments/UserCard";
  import Password from "./componments/Password";
  import Timeline from "./componments/Timeline";
  import Account from "./componments/Account";

  export default {
    name: 'Profile',
    components: {UserCard, Password, Timeline, Account},
    data() {
      return {
        activeTab: '',
        emp: JSON.parse(window.sessionStorage.getItem("emp"))
      }
    },
    created() {
      this.activeTab = 'timeline';
    },
    methods: {

    }
  }
</script>
<style lang="scss" scoped>
  .app-container {
    margin-top: 20px;
  }

  .el-card.is-always-shadow {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .el-card {
    border: none;
    border-radius: 0;
  }
</style>
