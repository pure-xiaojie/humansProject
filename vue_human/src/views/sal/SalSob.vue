<template>
  <div>
    <!--top button-->
    <div class="topButton">
      <el-button size="small" icon="el-icon-plus"
                 type="primary" @click="showAddView">
        添加工资套账
      </el-button>
      <el-button size="small" icon="el-icon-refresh"
                 type="success" @click="initSalaryList"></el-button>
    </div>

    <!--表格数据-->
    <div class="tableInfo">
      <el-table :data="salaryList" border stripe
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column label="账套名称" prop="name" width="150"></el-table-column>
        <el-table-column label="基本工资" prop="basicSalary" width="80"></el-table-column>
        <el-table-column label="交通补助" prop="trafficSalary" width="80"></el-table-column>
        <el-table-column label="午餐补助" prop="lunchSalary" width="80"></el-table-column>
        <el-table-column label="奖金" prop="bonus" width="80"></el-table-column>
        <el-table-column label="启用时间" prop="createDate" width="120"></el-table-column>
        <el-table-column label="养老金" align="center">
          <el-table-column label="比率" prop="pensionPer" width="80"></el-table-column>
          <el-table-column label="基数" prop="pensionBase" width="80"></el-table-column>
        </el-table-column>
        <el-table-column label="医疗保险" align="center">
          <el-table-column label="比率" prop="medicalPer" width="80"></el-table-column>
          <el-table-column label="基数" prop="medicalBase" width="80"></el-table-column>
        </el-table-column>
        <el-table-column label="公积金" align="center">
          <el-table-column label="比率" prop="accumulationFundPer" width="80"></el-table-column>
          <el-table-column label="基数" prop="accumulationFundBase" width="80"></el-table-column>
        </el-table-column>
        <!--编辑与删除-->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="editSalary(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="delSalary(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <div class="pageInfo">
        <el-pagination background
           layout="sizes, prev, pager, next, jumper, ->, total, slot"
           :total="total" :page-sizes="[5, 8, 10, 15, 20]" :page-size="8"
           @size-change="sizeChange" @current-change="pageChange">
        </el-pagination>
      </div>
    </div>

    <!--添加|修改 弹框-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible"
        width="20%" @close="initEmptySalary">
      <table>
        <tr>
          <td>套账名称：</td>
          <td><el-input v-model="salary.name" placeholder="请输入套账名称" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>基本工资：</td>
          <td><el-input v-model="salary.basicSalary" placeholder="基本工资" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>交通补助：</td>
          <td><el-input v-model="salary.trafficSalary" placeholder="交通补助" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>午餐补助：</td>
          <td><el-input v-model="salary.lunchSalary" placeholder="午餐补助" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>奖金：</td>
          <td><el-input v-model="salary.bonus" placeholder="奖金" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>养老金比率：</td>
          <td><el-input v-model="salary.pensionPer" placeholder="养老金比率" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>养老金基数：</td>
          <td><el-input v-model="salary.pensionBase" placeholder="养老金基数" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>医疗保险比率：</td>
          <td><el-input v-model="salary.medicalPer" placeholder="医疗保险比率" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>医疗保险基数：</td>
          <td><el-input v-model="salary.medicalBase" placeholder="医疗保险基数" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>公积金比率：</td>
          <td><el-input v-model="salary.accumulationFundPer" placeholder="公积金比率" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>公积金基数：</td>
          <td><el-input v-model="salary.accumulationFundBase" placeholder="公积金基数" size="small"
                        style="width:150px"></el-input></td>
        </tr>
      </table>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="initEmptySalary">取 消</el-button>
        <el-button size="small" type="primary" @click="addSalary">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "EmpBasic",
    data() {
      return {
        loading: false,
        salaryList: [],
        dialogVisible: false,
        salary: {
          id: null,
          name: '',
          basicSalary: 0,
          trafficSalary: 0,
          lunchSalary: 0,
          bonus: 0,
          pensionPer: 0.1,
          pensionBase: 0,
          medicalPer: 0.1,
          medicalBase: 0,
          accumulationFundPer: 0.1,
          accumulationFundBase: 0
        },
        dialogTitle: '添加工资账套',
        //分页
        page: 1,
        size: 8,
        total: 0
      }
    },
    mounted() {
      this.initSalaryList();
    },
    methods: {
      //初始化数据为空
      initEmptySalary() {
        this.salary = {
          id: null,
          name: '',
          basicSalary: 0,
          trafficSalary: 0,
          lunchSalary: 0,
          bonus: 0,
          pensionPer: 0.1,
          pensionBase: 0,
          medicalPer: 0.1,
          medicalBase: 0,
          accumulationFundPer: 0.1,
          accumulationFundBase: 0
        };
        this.dialogVisible = false;
      },
      //初始化数据
      initSalaryList() {
        this.loading = true;
        this.getRequest("/salary/sob/?page=" + this.page + '&size=' + this.size).then(res=>{
          this.salaryList = res.data;
          this.total = res.total;
          this.loading = false;
        })
      },
      //弹出添加框
      showAddView() {
        this.dialogTitle = '添加工资账套';
        this.dialogVisible = true;
      },
      //添加工资账套
      addSalary() {
        this.dialogVisible = false;

        if(this.salary.id == null) {
          //修改
          this.postRequest("/salary/sob/",this.salary).then(res=>{
            if(res) {
              this.initEmptySalary();
              this.initSalaryList();
            }
          })
        } else {
          //添加
          this.putRequest("/salary/sob/",this.salary).then(res=>{
            if(res) {
              this.initEmptySalary();
              this.initSalaryList();
            }
          })
        }
      },
      //修改工资账套
      editSalary(data) {
        Object.assign( this.salary,data);
        this.dialogTitle = '修改工资账套';
        this.dialogVisible = true;
      },
      //删除工资账套
      delSalary(data) {
        this.$confirm('确定永久删除【'+data.name+'】账套','提示',{
          cancelButtonText: '取消',
          confirmButtonText: '确定'
        }).then(() => {
          this.deleteRequest("/salary/sob/"+data.id).then(res=> {
            if(res) {
              this.initSalaryList();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initSalaryList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initSalaryList();
      },
    }
  }
</script>

<style scoped>
  .topButton{
    margin-top: 15px;
    display: flex;
    justify-content: space-between;
  }
  .tableInfo{
    margin-top: 15px;
  }
  .addStep{
    display: flex;
    justify-content: space-around;
  }
  .pageInfo{
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
  }
</style>
