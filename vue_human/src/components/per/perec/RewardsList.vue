<template>
  <div>
    <!--表格数据-->
    <div class="topInfo">
      <el-table :data="ecList" border stripe>
        <el-table-column type="selection" align="left" width="55"></el-table-column>
        <el-table-column prop="employee.workID" label="工号" width="100"></el-table-column>
        <el-table-column prop="employee.name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="employee.gender" label="性别" width="80"></el-table-column>
        <el-table-column prop="ecDate" label="奖惩时间" width="150"></el-table-column>
        <el-table-column prop="ecReason" label="奖惩原因" width="220"></el-table-column>
        <el-table-column prop="ecPoint" label="奖罚分" width="80"></el-table-column>
        <el-table-column prop="department.name" label="所属部门" width="120"></el-table-column>
        <el-table-column prop="position.name" label="职位" width="120"></el-table-column>
        <el-table-column label="类型" width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.ecType == 1">奖励</el-tag>
            <el-tag v-else type="danger">惩罚</el-tag>
          </template>
        </el-table-column>
        <!--编辑与删除-->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="editRewards(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="delRewards(scope.row)">删除</el-button>
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
    <el-dialog title="修改奖惩" :visible.sync="dialogVisible"
               width="20%">
      <table>
        <tr>
          <td>职工ID：</td>
          <td><el-input v-model="emp.workID"  size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>职工姓名：</td>
          <td><el-input v-model="emp.name" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>奖惩时间：</td>
          <td><el-date-picker
              v-model="employeeec.ecDate" size="small"
              type="date" style="width:150px"
              placeholder="选择日期">
          </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>奖惩原因：</td>
          <td><el-input v-model="employeeec.ecReason" placeholder="奖惩原因" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>奖罚分：</td>
          <td><el-input v-model="employeeec.ecPoint" placeholder="奖惩分" size="small"
                        style="width:150px"></el-input></td>
        </tr>
        <tr>
          <td>备注：</td>
          <td><el-input v-model="employeeec.remark" placeholder="备注" size="small"
                        style="width:150px"></el-input></td>
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
    name: "RewardsList",
    data() {
      return {
        ecList: [],
        total: 0,
        page: 1,
        size: 10,
        //修改弹窗
        dialogVisible: false,
        //奖惩实体
        employeeec: {
          id: null,
          eid: null,
          ecDate: null,
          ecReason: '',
          ecPoint: 0,
          ecType: null,
          remark: ''
        },
        //员工实体
        emp: {
          id: null,
          name:"",
          workID:"00001942",
        }
      }
    },
    mounted() {
      this.initEcList();
    },
    methods: {
      //初始化奖惩数据
      initEcList() {
        this.getRequest("/personnel/ec/?page=" + this.page + '&size=' + this.size).then(res => {
          this.ecList = res.data;
          this.total = res.total;
        })
      },
      //修改奖惩
      editRewards(rewards) {
        this.employeeec = rewards;
        this.emp.name = rewards.employee.name;
        this.emp.workID = rewards.employee.workID;
        this.dialogVisible = true;
      },
      //删除奖惩
      delRewards(rewards) {
        this.$confirm('确定删除','提示',{
          cancelButtonText: '取消',
          confirmButtonText: '确定'
        }).then(() => {
          this.deleteRequest("/personnel/ec/"+rewards.id).then(res=>{
            this.initEcList();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      },
      //取消
      cancel() {
        this.dialogVisible = false;
      },
      //更新
      update() {
        //console.log(this.employeeec);
        this.putRequest("/personnel/ec/",this.employeeec).then(res=>{

        });
        this.dialogVisible = false;
        this.initEcList();
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initEcList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initEcList();
      }
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
  .topButton{
    margin-top: 15px;
    display: flex;
    justify-content: space-between;
  }
</style>
