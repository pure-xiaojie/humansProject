<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="系统公告" name="first"><msg-content></msg-content></el-tab-pane>
      <el-tab-pane label="导航设置" name="second"><msg-content></msg-content></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import MsgContent from '../../components/sys/cfg/MsgContent'
  export default {
    name: "SysBasic",
    data(){
      return {
        activeName: 'first'
      }
    },
    components: {
      MsgContent
    }
  }
</script>

<style scoped>

</style>
