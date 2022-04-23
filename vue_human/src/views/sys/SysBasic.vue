<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="部门管理" name="first"><DepManage></DepManage></el-tab-pane>
      <el-tab-pane label="职位管理" name="second"><PositionManage></PositionManage></el-tab-pane>
      <el-tab-pane label="职称管理" name="third"><JobLevelManage></JobLevelManage></el-tab-pane>
      <el-tab-pane label="权限组" name="five"><PermissManage></PermissManage></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  //导入组件
  import DepManage from '../../components/sys/basic/DepManage'
  import JobLevelManage from '../../components/sys/basic/JobLevelManage'
  import PermissManage from '../../components/sys/basic/PermissManage'
  import PositionManage from '../../components/sys/basic/PositionManage'

  export default {
    name: "SysBasic",
    data() {
      return {
        activeName: 'first'
      }
    },
    components: {
      DepManage,JobLevelManage,PositionManage,PermissManage
    }
  }
</script>

<style scoped>

</style>
