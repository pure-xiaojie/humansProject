<template>
  <div>
    <pdf ref="pdf" src="http://localhost:8080/#/assets/resume/lijiegui.pdf"></pdf>
  </div>
</template>

<script>
  import pdf from 'vue-pdf'
  export default {
    name: "PdfView",
    components:{
      pdf
    },
    data(){
      return {
        //url:pdf.createLoadingTask("http://114.116.255.239:5050/group1/M00/00/00/wKgAQ2BkkQyAFJBwAAGSd_oIlFM195.pdf"),
      }
    }
  }
</script>

<style scoped>

</style>
