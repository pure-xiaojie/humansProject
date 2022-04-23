<template>
  <div>
    <!--表格数据-->
    <div class="topInfo">
      <el-table :data="adjustList" border stripe>
        <el-table-column type="selection" align="left" width="55"></el-table-column>
        <el-table-column prop="employee.workID" label="工号" width="100"></el-table-column>
        <el-table-column prop="employee.name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="employee.gender" label="性别" width="60"></el-table-column>
        <el-table-column prop="department.name" label="部门" width="100"></el-table-column>
        <el-table-column prop="position.name" label="职位" width="120"></el-table-column>
        <el-table-column prop="asDate" label="调薪时间" width="120"></el-table-column>
        <el-table-column prop="beforeSalary" label="调薪前" width="100"></el-table-column>
        <el-table-column prop="afterSalary" label="调薪后" width="100"></el-table-column>
        <el-table-column prop="reason" label="调薪原因" width="280"></el-table-column>
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
        <el-pagination background :page-sizes="[5, 10, 15, 20, 30]"
                       layout="sizes, prev, pager, next, jumper, ->, total, slot"
                       :total="total" @current-change="pageChange" @size-change="sizeChange">
        </el-pagination>
      </div>
    </div>

    <!--修改 弹框-->
    <el-dialog title="调薪修改" :visible.sync="dialogVisible"
               width="32%">
      <table>
        <tr>
          <td>职工ID：</td>
          <td><el-input v-model="emp.workID"  size="small"
                        style="width:350px" :disabled="true"></el-input></td>
        </tr>
        <tr>
          <td>职工姓名：</td>
          <td><el-input v-model="emp.name" size="small"
                        style="width:350px" :disabled="true"></el-input></td>
        </tr>
        <tr>
          <td>调薪前：</td>
          <td><el-input v-model="adjust.beforeSalary" size="small"
                        style="width:350px" :disabled="true"></el-input></td>
        </tr>
        <tr>
          <td>调薪后：</td>
          <td><el-input v-model="adjust.afterSalary" size="small"
                        style="width:350px"></el-input></td>
        </tr>
        <tr>
          <td>调薪时间：</td>
          <td>
            <el-date-picker v-model="adjust.asDate" size="small"
              type="date" style="width:350px"
              placeholder="选择日期">
            </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>调薪原因：</td>
          <td><el-input v-model="adjust.reason" placeholder="奖惩原因" size="small"
                        style="width:350px"></el-input></td>
        </tr>
      </table>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel">取 消</el-button>
        <el-button size="small" type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "PerSalary",
    data() {
      return {
        adjustList: [],
        total: 0,
        page: 1,
        size: 10,
        //修改弹窗
        dialogVisible: false,

        //调薪实体
        adjust: {
          id: null,
          eid: null,
          asDate: null,
          beforeSalary: '',
          afterSalary: '',
          reason: ''
        },
        //员工实体
        emp: {
          id: null,
          name:"",
          workID:"",
        }
      }
    },
    mounted() {
      this.initAdjustList();
    },
    methods: {
      //初始化调薪数据
      initAdjustList() {
        this.getRequest("/personnel/salary/?page=" + this.page + '&size=' + this.size).then(res => {
          this.adjustList = res.data;
          this.total = res.total;
        })
      },
      //修改调薪
      editSalary(params) {
        this.adjust = params;
        this.emp.name = params.employee.name;
        this.emp.workID = params.employee.workID;
        this.dialogVisible = true;
      },
      //删除调薪
      delSalary(salary) {
        this.$confirm('确定删除','提示',{
          cancelButtonText: '取消',
          confirmButtonText: '确定'
        }).then(() => {
          this.deleteRequest("/personnel/salary/"+salary.id).then(res=>{
            this.initAdjustList();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      },
      //取消更新
      cancel() {
        this.dialogVisible = false;
      },
      //更新
      update() {
        this.putRequest("/personnel/salary/",this.adjust).then(res=>{

        });
        this.dialogVisible = false;
        this.initAdjustList();
        this.initAdjust();
      },
      initAdjust() {
        this.adjust = {
          id: null,
          eid: null,
          asDate: null,
          beforeSalary: '',
          afterSalary: '',
          reason: ''
        }
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initAdjustList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initAdjustList();
      },
    }
  }
</script>

<style scoped>
  .topInfo{
    margin-top: 20px;
  }
  .pageInfo{
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
  }
</style>
