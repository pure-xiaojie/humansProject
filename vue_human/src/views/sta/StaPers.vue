<template>
  <div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">员工分布统计</div>
            <span>当前选中了: {{ cityName || '-' }}</span>
            <ve-map :data="chartData1" :settings="chartSettings1" width="500px" height="379px" :events="chartEvents"></ve-map>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">职位分布统计</div>
            <ve-pie :data="chartData2" :settings="chartSettings2" width="600px" height="400px"></ve-pie>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">员工专业统计</div>
            <ve-ring :data="chartData3" width="600px" height="400px"></ve-ring>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="schart-box grid-content bg-purple">
            <div class="content-title">员工归属地TOP10</div>
            <ve-bar :data="chartData4" :settings="chartSettings4" width="600px" height="400px"></ve-bar>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "StaPers",
    data () {
      this.chartSettings1 = {
        position: 'china',
        // selectData: true,
        selectedMode: 'single',
        labelMap: {
          'value': '人数'
        },
      };
      this.chartEvents = {
        click: (v) => {
          this.cityName = v.name
        }
      };
      this.chartSettings2 = {
        roseType: 'radius'
      };
      this.chartSettings4 = {
        metrics: ['value'],
        dataOrder: {
          label: 'value',
          order: 'desc'
        },
        labelMap: {
          'value': '人数'
        }
      };

      return {
        //图表数据
        cityName: '',
        chartData1: {
          columns: ['name', 'value'],
          rows: [{"name":"山东","value":2236},
            {"name":"广东","value":2229},
            {"name":"广西","value":2313},
            {"name":"江西","value":2344},
            {"name":"湖北","value":2208},
            {"name":"湖南","value":2248},
            {"name":"福建","value":2304}]
        },
        chartData2: {
          columns: ['name', 'value'],
          rows: [{ 'name': 'Java开发工程师', 'value': 1393 },
            { 'name': '运维工程师', 'value': 3530 },
            { 'name': '技术专家', 'value': 2923 },
            { 'name': '后台开发', 'value': 1723 },
            { 'name': '产品经理', 'value': 3792 },
            { 'name': '前端工程师', 'value': 4593 }]
        },
        chartData3: {
          columns: ['name', 'value'],
          rows: [{ 'name': '私营企业', 'value': 1393 },
            { 'name': '国有企业', 'value': 3530 },
            { 'name': '联营企业', 'value': 2923 },
            { 'name': '外商企业', 'value': 1723 },
            { 'name': '其它企业', 'value': 3792 }]
        },
        chartData4: {
          columns: ['name', 'value'],
          rows: [
            { 'name': '北京', 'value': 3530 },
            { 'name': '上海', 'value': 2923 },
            { 'name': '广州', 'value': 1723 },
            { 'name': '武汉', 'value': 3792 },
            { 'name': '东莞', 'value': 4593 }]
        }
      }
    },
    created () {
    },
    methods: {
    },
    async mounted () {
      this.getRequest("/statistics/personnel/area").then(res => {
        this.chartData1.rows = res;
        //console.log(this.chartData1.rows);
      });
      this.getRequest("/statistics/personnel/position").then(res => {
        this.chartData2.rows = res;
        //console.log(this.chartData2.rows);
      });
      this.getRequest("/statistics/personnel/professional").then(res => {
        this.chartData3.rows = res;
        //console.log(this.chartData3.rows);
      });
      this.getRequest("/statistics/personnel/top10").then(res => {
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
