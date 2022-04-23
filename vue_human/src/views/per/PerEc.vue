<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="奖惩列表" name="first"><RewardsList></RewardsList></el-tab-pane>
      <el-tab-pane label="新增奖惩" name="second"><AddRewards></AddRewards></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  //导入组件
  import RewardsList from '../../components/per/perec/RewardsList'
  import AddRewards from '../../components/per/perec/AddRewards'

  export default {
    name: "PerEc",
    data() {
      return {
        activeName: 'first'
      }
    },
    components: {
      RewardsList,AddRewards
    }
  }
</script>

<style scoped>

</style>
