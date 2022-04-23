<template>
  <div>
    <!--表格数据-->
    <div class="topInfo">
      <el-table :data="salaryTables" border stripe>
        <el-table-column type="selection" align="left" width="55"></el-table-column>
        <el-table-column prop="empName" label="姓名" width="100"></el-table-column>
        <el-table-column prop="basicSalary" label="基本工资" width="100"></el-table-column>
        <el-table-column prop="trafficSalary" label="交通补助" width="100"></el-table-column>
        <el-table-column prop="lunchSalary" label="餐补" width="100"></el-table-column>
        <el-table-column prop="bonus" label="奖金" width="100"></el-table-column>
        <el-table-column label="应发工资" width="100">
          <template slot-scope="scope">
            {{scope.row.allSalary}}
          </template>
        </el-table-column>
        <el-table-column label="养老保险" width="100">
          <template slot-scope="scope">
            {{scope.row.pensionPer*scope.row.pensionBase}}
          </template>
        </el-table-column>
        <el-table-column label="医疗保险" width="100">
          <template slot-scope="scope">
            {{scope.row.medicalPer*scope.row.medicalBase}}
          </template>
        </el-table-column>
        <el-table-column label="公积金" width="100">
          <template slot-scope="scope">
            {{scope.row.accumulationFundBase*scope.row.accumulationFundPer}}
          </template>
        </el-table-column>
        <el-table-column label="实发工资" width="100">
          <template slot-scope="scope">
            {{scope.row.actualSalary}}
          </template>
        </el-table-column>
        <el-table-column label="月份" width="100">
          <template slot-scope="scope">
            <el-tag>{{scope.row.month}}月</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SalSalary",
    data() {
      return {
        salaryTables: [],
        //员工实体
        emp: {},
      }
    },
    mounted() {
      this.emp = JSON.parse(window.sessionStorage.getItem("emp"));
      this.initSalaryList();
    },
    methods: {
      //初始化职工数据
      initSalaryList() {
        let url = "/salaryMonth/" + this.emp.id;

        this.getRequest(url).then(res => {
          this.salaryTables = res;
        })
      }
    }
  }
</script>

<style scoped>
  .topInfo{
    margin-top: 20px;
  }

</style>
