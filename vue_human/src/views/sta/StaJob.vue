<template>
  <div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">简历投递统计</div>
            <ve-line :data="chartData1" :settings="chartSettings1" width="600px" height="400px"></ve-line>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">薪资阶段统计</div>
            <ve-ring :data="chartData2" :settings="chartSettings2" width="600px" height="400px"></ve-ring>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">求职状态统计</div>
            <ve-funnel :data="chartData3" width="600px" height="400px"></ve-funnel>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">职位访问排行</div>
            <div style="margin-left: 50px">
              <ve-radar :data="chartData4" :settings="chartSettings4" width="500px" height="400px"></ve-radar>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "StaJob",
    data () {
      this.chartSettings1 = {
        labelMap: {
          'value': '投递人数'
        },
      };
      this.chartSettings2 = {
        color: ['#ff0', '#00f', '#f00', '#0ff'],
        shape: 'diamond',
        sizeMin: 30,
        sizeMax: 60
      };

      this.chartSettings4 = {
        labelMap: {
          'visit': '访问量',
          'resume': '投递量',
          'absolutely': '投递率'
        },
        dimension: ['name'],
        metrics: ['visit', 'resume', 'absolutely'],
        dataType: { 'absolutely': 'percent' }
      };
      return {
        //图表数据
        cityName: '',
        chartData1: {
          columns: ['name', 'value'],
          rows: [
            { 'name': '1月', 'value': 0 },
            { 'name': '2月', 'value': 0 },
            { 'name': '3月', 'value': 2 },
            { 'name': '4月', 'value': 4 },
            { 'name': '5月', 'value': 123 },
            { 'name': '6月', 'value': 123 },
            { 'name': '7月', 'value': 1223 },
            { 'name': '8月', 'value': 2123 },
            { 'name': '9月', 'value': 4123 },
            { 'name': '10月', 'value': 123 },
            { 'name': '11月', 'value': 4123 },
            { 'name': '12月', 'value': 123 },
          ]
        },
        chartData2: {
          columns: ['name', 'value'],
          rows: [{ 'name': '2-3k', 'value': 1393 },
            { 'name': '3-5k', 'value': 3530 },
            { 'name': '5-8k', 'value': 2923 },
            { 'name': '8-10k', 'value': 1723 },
            { 'name': '10-16k', 'value': 3792 }]
        },
        chartData3: {
          columns: ['name', 'value'],
          rows: [
            { 'name': "投递", 'value': 25 },
            { 'name': '简历筛选', 'value': 12 },
            { 'name': '人才库', 'value': 4 },
            { 'name': '笔试', 'value': 15 },
            { 'name': '面试', 'value': 2 },
            { 'name': 'offer', 'value': 13 },
            ]
        },
        chartData4: {
          columns: ['name', 'visit', 'resume', 'absolutely'],
          rows: [
            { 'name': '1/1', 'visit': 1393, 'resume': 1093, 'absolutely': 0.32 },
            { 'name': '1/2', 'visit': 3530, 'resume': 3230, 'absolutely': 0.26 },
            { 'name': '1/3', 'visit': 2923, 'resume': 2623, 'absolutely': 0.76 },
            { 'name': '1/4', 'visit': 1723, 'resume': 1423, 'absolutely': 0.49 },
            { 'name': '1/5', 'visit': 3792, 'resume': 3492, 'absolutely': 0.323 },
            { 'name': '1/6', 'visit': 4593, 'resume': 4293, 'absolutely': 0.78 }
          ]
        }
      }
    },
    created () {
    },
    methods: {
    },
    async mounted () {
      this.getRequest("/statistics/job/resume").then(res => {
        this.chartData1.rows = res;
        this.chartData1.rows.splice(0,1);
        //console.log(this.chartData1.rows);
      });
      this.getRequest("/statistics/job/salary").then(res => {
        this.chartData2.rows = res;
        //console.log(this.chartData2.rows);
      });
      this.getRequest("/statistics/job/status").then(res => {
        this.chartData3.rows = res;
        console.log(this.chartData3.rows);
      });
      this.getRequest("/statistics/job/visit").then(res => {
        this.chartData4.rows = res;
        //console.log(this.chartData4.rows);
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
