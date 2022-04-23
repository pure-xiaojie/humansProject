<template>
  <div v-loading="loading" :style="'height:'+ height">
    <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto"/>
  </div>
</template>

<script>
  export default {
    name: "SysInit",
    data() {
      return {
        src: "http://localhost:8081/swagger-ui.html",
        height: document.documentElement.clientHeight - 94.5 + "px;",
        loading: true
      };
    },
    mounted: function () {
      setTimeout(() => {
        this.loading = false;
      }, 230);
      const that = this;
      window.onresize = function temp() {
        that.height = document.documentElement.clientHeight - 94.5 + "px;";
      };
    }
  }
</script>

<style scoped>

</style>
