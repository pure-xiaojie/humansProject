<template>
  <div>
    <!--输入框与按钮-->
    <div  class="topInput">
      <!--left button-->
      <div style="display: flex;">
        <el-form label-width="80px">
          <el-form-item label="月份">
            <el-select v-model="month" placeholder="请选择月份" size="small"
                       @change="initSalaryList">
              <el-option
                  v-for="(item,index) in months"
                  :key="item"
                  :label="item"
                  :value="index+1">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </div>
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
        <!--编辑与删除-->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="delSalary(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <div class="pageInfo">
        <el-pagination background :page-sizes="[5, 10, 15, 20, 30]"
                       layout="sizes, prev, pager, next, jumper, ->, total, slot"
                       :total="total" @current-change="pageChange" @size-change="sizeChange">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SalMonth",
    data() {
      return {
        month: null,
        months: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],

        salaryTables: [],
        //分页
        page: 1,
        size: 10,
        total: 0
      }
    },
    mounted() {
      this.initSalaryList();
    },
    methods: {
      //初始化职工数据
      initSalaryList() {
        let url = "/salaryMonth/month?page=" + this.page + '&size=' + this.size;

        //console.log("month:" + this.month);
        if(this.month != null) {
          url += "&month=" + this.month;
        }
        this.getRequest(url).then(res => {
          this.salaryTables = res.data;
          this.total = res.total;
        })
      },
      //分页
      pageChange(page) {
        this.page = page;
        this.initSalaryList();
      },
      sizeChange(size) {
        this.size = size;
        this.initSalaryList();
      }
    }
  }
</script>

<style scoped>
  .topInput{
    margin-left: -40px;
    margin-top: 15px;
  }
  .topLeft{
    width: 300px;
    margin-right: 10px;
  }
  .topInfo{
    margin-top: 0px;
  }
  .pageInfo{
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
  }
</style>
