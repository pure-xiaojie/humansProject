<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="登录日志" name="first"><LoginLog></LoginLog></el-tab-pane>
      <el-tab-pane label="邮箱日志" name="second"><MailLog></MailLog></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  //导入组件
  import LoginLog from '../../components/sys/log/LoginLog'
  import MailLog from '../../components/sys/log/MailLog'
  export default {
    name: "SysLog",
    data() {
      return {
        activeName: 'first'
      }
    },
    components: {
      LoginLog,MailLog
    }
  }
</script>

<style scoped>

</style>
