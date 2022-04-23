<template>
  <div id="FriendChat">
    <div class="sidebar">
      <card></card>
      <list></list>
    </div>
    <div class="main">
      <message></message>
      <userText></userText>
    </div>
  </div>
</template>

<script>
  import card from '../../components/chat/card.vue'
  import list from '../../components/chat/list.vue'
  import message from '../../components/chat/message.vue'
  import userText from '../../components/chat/usertext.vue'

  export default {
    name: 'FriendChat',
    data () {
      return {

      }
    },
    mounted:function() {
      //初始化数据，调用store下index.js下的actions：initData
      this.$store.dispatch('initData');
    },
    components:{
      card,
      list,
      message,
      userText
    }
  }
</script>

<style lang="scss" scoped>
  #FriendChat {
    margin: 20px auto;
    width: 100%;
    height: 570px;
    overflow: hidden;
    border-radius: 10px;
    .sidebar, .main {
      height: 100%;
    }
    .sidebar {
      float: left;
      color: #f4f4f4;
      background-color: #b9c0ca;
      width: 200px;
    }
    .main {
      position: relative;
      overflow: hidden;
      background-color: #eee;
    }
  }
</style>
