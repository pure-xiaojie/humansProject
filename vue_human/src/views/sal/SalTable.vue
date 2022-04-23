<template>
  <div>
    <!--输入框与按钮-->
    <div  class="topInput">
      <!--left button-->
      <div>
        <el-input placeholder="请输入姓名搜索…" class="topLeft" size="small"
                  prefix-icon="el-icon-search" v-model="searchWords"
                  @keydown.enter.native="initEmpList" clearable
                  @clear="initEmpList"></el-input>
        <el-button type="success" size="small" icon="el-icon-search"
                   @click="initEmpList">搜索</el-button>
      </div>
      <!--right button-->
      <div>
        <el-button type="success" size="small" icon="el-icon-plus"
                   @click="showAddView">发放工资</el-button>
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
        <el-table-column prop="workID" label="养老保险" width="80">
          <template slot-scope="scope">
            {{scope.row.pensionBase}}
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="保险比" width="80">
          <template slot-scope="scope">
            {{scope.row.pensionPer}}
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="医疗保险" width="100">
          <template slot-scope="scope">
            {{scope.row.medicalBase}}
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="医疗比" width="80">
          <template slot-scope="scope">
            {{scope.row.medicalPer}}
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="公积金" width="100">
          <template slot-scope="scope">
            {{scope.row.accumulationFundBase}}
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="公积金比" width="80">
          <template slot-scope="scope">
            {{scope.row.accumulationFundPer}}
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="实发工资" width="100">
          <template slot-scope="scope">
          {{scope.row.actualSalary}}
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

    <!--添加对话框-->
    <el-dialog title="薪资发放" :visible.sync="dialogVisible" width="25%">
      <!--添加数据区-->
      <div>
        <el-form label-width="80px">
          <el-form-item label="月份">
            <el-select v-model="month" placeholder="请选择月份" size="small">
              <el-option
                  v-for="item in months"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" size="mini">确认发放</el-button>
            <el-button @click="cancelAddView"  size="mini">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "SalTable",
    data() {
      return {
        searchWords: '',
        salaryTables: [],
        //分页
        page: 1,
        size: 10,
        total: 0,
        dialogVisible: false,
        month: 1,
        months: [1,2,3,4,5,6,7,8,9,10,11,12]
      }
    },
    mounted() {
      this.initEmpList();
    },
    methods: {
      //初始化职工数据
      initEmpList() {
        let url = "/salary/sobcfg/emp?page=" + this.page + '&size=' + this.size;

        if(this.searchWords != '') {
          url += "&name=" + this.searchWords;
        }
        this.getRequest(url).then(res => {
          this.salaryTables = res.data;
          this.total = res.total;
        })
      },
      showAddView() {
        this.dialogVisible = true;
      },
      onSubmit() {
        this.getRequest("/salary/sobcfg/month?month=" + this.month).then(res=>{

        });
        this.dialogVisible = false;
      },
      cancelAddView() {
        this.dialogVisible = false;
      },
      //分页
      pageChange(page) {
        this.page = page;
        this.initEmpList();
      },
      sizeChange(size) {
        this.size = size;
        this.initEmpList();
      }
    }
  }
</script>

<style scoped>
  .topInput{
    display: flex;
    justify-content: space-between;
    margin-top: 15px;
  }
  .topLeft{
    width: 300px;
    margin-right: 10px;
  }
  .topInfo{
    margin-top: 20px;
  }
  .pageInfo{
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
  }
</style>
