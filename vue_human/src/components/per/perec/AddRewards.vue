<template>
  <div>
    <!--输入框与按钮-->
    <div>
      <!--top button-->
      <div  class="topInfo">
        <!--left button-->
        <div>
          <el-input placeholder="请输入职工姓名搜索…" class="topLeft" size="small"
                    prefix-icon="el-icon-search" v-model="searchWords"
                    @keydown.enter.native="initEmpList" clearable
                    @clear="initEmpList"></el-input>
          <el-button type="success" size="small" icon="el-icon-search"
                     @click="initEmpList">搜索</el-button>
        </div>
      </div>
    </div>

    <!--表格数据展示-->
    <div class="containerInfo">
      <el-table :data="empList" stripe border
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-table-column type="selection" width="55"></el-table-column>

        <el-table-column prop="name" label="姓名" width="120" fixed></el-table-column>
        <el-table-column prop="workID" label="工号" width="120"></el-table-column>
        <el-table-column prop="gender" label="性别" width="80"></el-table-column>
        <el-table-column prop="birthday" label="出生日期" width="130"></el-table-column>
        <el-table-column prop="idCard" label="身份证号码" width="180"></el-table-column>
        <el-table-column prop="department.name" label="所属部门" width="100"></el-table-column>
        <el-table-column prop="position.name" label="职位" width="120"></el-table-column>

        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" style="padding: 3px" @click="rewards(scope.row,1)">奖励</el-button>
            <el-button size="mini" type="danger"  style="padding: 3px" @click="rewards(scope.row,0)">惩罚</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <div class="pageInfo">
        <el-pagination background
                       layout="sizes, prev, pager, next, jumper, ->, total, slot"
                       :total="total" :page-sizes="[5, 10, 15, 20, 30]"
                       @size-change="sizeChange"
                       @current-change="pageChange">
        </el-pagination>
      </div>
    </div>

    <!--添加|修改 弹框-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible"
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
        <el-button size="small" type="primary" @click="add">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "AddWards",
    data() {
      return{
        loading: false,
        empList: [],
        total: 0,
        page: 1,
        size: 5,
        searchWords: '',
        //弹框属性
        dialogTitle: '',
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
      this.initEmpList();
    },
    methods: {
      //初始化职工列表数据
      initEmpList() {
        this.loading = true;
        let url = '/employee/basic/?page=' + this.page +"&size=" + this.size ;

        if(this.searchWords) {
          url += "&name=" + this.searchWords + "&workID=" + this.searchWords;
        }

        this.getRequest(url).then(res=>{
          if(res) {
            this.empList = res.data;
            this.total = res.total;
          }
          this.loading = false;
        })
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initEmpList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initEmpList();
      },
      //弹框
      rewards(emp,type) {
        if(type == 1) {
          this.dialogTitle = '给予奖励';
          this.employeeec.ecType = 1;
        } else {
          this.dialogTitle = '给予惩罚';
          this.employeeec.ecType = 0;
        }
        this.emp = emp;
        this.dialogVisible = true;
      },
      //取消
      cancel() {
        this.dialogVisible = false;
      },
      //增加
      add() {
        this.employeeec.eid = this.emp.id;
        this.postRequest("/personnel/ec/",this.employeeec).then(res=>{

        });
        this.dialogVisible = false;
      }
    }
  }
</script>

<style scoped>
  .topInfo{
    display: flex;
    justify-content: space-between;
    margin-top: 15px;
  }
  .topLeft{
    width: 300px;
    margin-right: 10px;
  }
  .containerInfo{
    margin-top: 15px;
  }
  .pageInfo{
    display: flex;
    justify-content: flex-start;
    margin-top: 20px;
  }

</style>
