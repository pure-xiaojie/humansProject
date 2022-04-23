<template>
  <div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">入职统计</div>
            <ve-line :data="chartData1" :settings="chartSettings1" width="600px" height="400px"></ve-line>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">专业词云</div>
            <ve-wordcloud :data="chartData2" :settings="chartSettings2" width="600px" height="400px"></ve-wordcloud>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">男女占比</div>
            <ve-pie :data="chartData3" width="600px" height="400px"></ve-pie>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">学历分布</div>
            <div style="margin-left: 50px">
              <ve-histogram :data="chartData4" :settings="chartSettings4" width="500px" height="400px"></ve-histogram>
            </div>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">政治面貌</div>
            <ve-waterfall :data="chartData5" :settings="chartSettings5" width="600px" height="400px"></ve-waterfall>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">民族分布</div>
            <div style="margin-left: 50px">
              <ve-ring :data="chartData6" :settings="chartSettings6" width="500px" height="400px"></ve-ring>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "StaEmp",
    data () {
      this.chartSettings1 = {
        labelMap: {
          'value': '入职人数'
        },
      };
      this.chartSettings2 = {
        color: ['#ff0', '#00f', '#f00', '#0ff'],
        shape: 'diamond',
        sizeMin: 30,
        sizeMax: 60
      };

      this.chartSettings4 = {
        showLine: ['value'],
        labelMap: {
          'value': '学历占比'
        },
      };
      this.chartSettings5 = {
        //dataType: 'percent',
        labelMap: {
          'value': '人数'
        },
      };
      this.chartSettings6 = {
        roseType: 'radius'
      };

      return {
        //图表数据
        cityName: '',
        chartData1: {
          columns: ['name', 'value'],
          rows: [
            { 'name': '2017', 'value': 123 },
            { 'name': '2018', 'value': 1223 },
            { 'name': '2019', 'value': 2123 },
            { 'name': '2020', 'value': 4123 },
            { 'name': '2021', 'value': 123 },
          ]
        },
        chartData2: {
          columns: ['name', 'value'],
          rows:
              [{
            'name': 'visualMap',
            'value': 22199
          }, {
            'name': 'continuous',
            'value': 10288
          }, {
            'name': 'contoller',
            'value': 620
          }, {
            'name': 'series',
            'value': 274470
          }]
        },
        chartData3: {
          columns: ['name', 'value'],
          rows: [{ 'name': "", 'value': 1 },
            { 'name': '男', 'value': 1393 },
            { 'name': '女', 'value': 3530 }]
        },
        chartData4: {
          columns: ['name', 'value'],
          rows: [
            { 'name': '初中', 'value': 3530},
            { 'name': '高中', 'value': 2923},
            { 'name': '专科', 'value': 1723},
            { 'name': '本科', 'value': 3792},
            { 'name': '硕士', 'value': 4593},
            { 'name': '博士', 'value': 4593},
            { 'name': '其它', 'value': 10}]
        },
        chartData5: {
          columns: ['name', 'value'],
          rows: [
            { 'name': '共青团员', 'value': 17 },
            { 'name': '中共党员', 'value': 15 },
            { 'name': '普通公民', 'value': 48 },
            { 'name': '民建会员', 'value': 36 },
            { 'name': '民盟盟员', 'value': 54 },
            { 'name': '民革党员', 'value': 170},
          ]
        },
        chartData6: {
          columns: ['name', 'value'],
          rows: [
            { 'name': '汉族', 'value': 1393 },
            { 'name': '回族', 'value': 3530 },
            { 'name': '傣族', 'value': 2923 },
            { 'name': '黎族', 'value': 1723 },
            { 'name': '苗族', 'value': 3792 },
            { 'name': '壮族', 'value': 4593 }
          ]
        }
      }
    },
    created () {
    },
    methods: {
    },
    async mounted () {
      this.getRequest("/statistics/emp/begin").then(res => {
        this.chartData1.rows = res;
        //console.log(this.chartData1.rows);
      });
      this.getRequest("/statistics/emp/word").then(res => {
        this.chartData2.rows = res;
        //console.log(this.chartData2.rows);
      });
      this.getRequest("/statistics/emp/gender").then(res => {
        this.chartData3.rows = res;
        console.log(this.chartData3.rows);
      });
      this.getRequest("/statistics/emp/graduation").then(res => {
        this.chartData4.rows = res;
        //console.log(this.chartData4.rows);
      });
      this.getRequest("/statistics/emp/politics").then(res => {
        this.chartData5.rows = res;
        //console.log(this.chartData5.rows);
      });
      this.getRequest("/statistics/emp/nation").then(res => {
        this.chartData6.rows = res;
        //console.log(this.chartData6.rows);
      });
    }
  }
</script>
<style scoped>
  .schart-box {
    display: inline-block;
    margin: 20px;
    border: 1px;
  }
  .content-title {
    clear: both;
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
  }
  .el-col{
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15)!important;
  }
</style>
