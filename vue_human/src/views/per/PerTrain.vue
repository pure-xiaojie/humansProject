<template>
  <div>
    <!--top button-->
    <div class="topButton">
      <el-button size="small" icon="el-icon-plus"
                 type="success" @click="showAddView">
        新增培训
      </el-button>
      <el-button size="small" icon="el-icon-refresh"
                 type="success" @click="initTrainList"></el-button>
    </div>

    <!--表格数据-->
    <div class="topInfo">
      <el-table :data="trainList" border stripe>
        <el-table-column type="selection" align="left" width="55"></el-table-column>
        <el-table-column prop="employee.workID" label="工号" width="100"></el-table-column>
        <el-table-column prop="employee.name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="employee.gender" label="性别" width="80"></el-table-column>
        <el-table-column prop="position.name" label="职位" width="120"></el-table-column>
        <el-table-column prop="trainDate" label="培训时间" width="150"></el-table-column>
        <el-table-column prop="trainContent" label="培训内容" width="420"></el-table-column>
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
    <el-dialog title="修改培训" :visible.sync="dialogVisible"
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
          <td>培训时间：</td>
          <td><el-date-picker
              v-model="train.trainDate" size="small"
              type="date" style="width:350px"
              placeholder="选择日期">
          </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>培训内容：</td>
          <td><el-input v-model="train.trainContent" placeholder="奖惩原因" size="small"
                        style="width:350px"></el-input></td>
        </tr>
      </table>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel">取 消</el-button>
        <el-button size="small" type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>

    <!--添加 弹框-->
    <el-dialog title="修改培训" :visible.sync="addDialogVisible"
               width="32%">
      <table>
        <tr>
          <td>职工ID：</td>
          <td><el-input v-model="train.eid"  size="small"
                        style="width:350px" placeholder="职工ID"></el-input></td>
        </tr>
        <tr>
          <td>培训时间：</td>
          <td><el-date-picker
              v-model="train.trainDate" size="small"
              type="date" style="width:350px"
              placeholder="选择日期">
          </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>培训内容：</td>
          <td><el-input v-model="train.trainContent" placeholder="奖惩原因" size="small"
                        style="width:350px"></el-input></td>
        </tr>
      </table>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="addCancel">取 消</el-button>
        <el-button size="small" type="primary" @click="addTrain">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "PerTrain",
    data() {
      return {
        trainList: [],
        total: 0,
        page: 1,
        size: 10,
        //修改弹窗
        dialogVisible: false,
        //增加弹框
        addDialogVisible: false,

        //培训实体
        train: {
          id: null,
          eid: null,
          trainDate: null,
          trainContent: '',
          remark: ''
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
      this.initTrainList();
    },
    methods: {
      //初始化奖惩数据
      initTrainList() {
        this.getRequest("/personnel/train/?page=" + this.page + '&size=' + this.size).then(res => {
          this.trainList = res.data;
          this.total = res.total;
        })
      },
      //修改奖惩
      editRewards(params) {
        this.train = params;
        this.emp.name = params.employee.name;
        this.emp.workID = params.employee.workID;
        this.dialogVisible = true;
      },
      //删除奖惩
      delRewards(train) {
        this.$confirm('确定删除','提示',{
          cancelButtonText: '取消',
          confirmButtonText: '确定'
        }).then(() => {
          this.deleteRequest("/personnel/train/"+train.id).then(res=>{
            this.initTrainList();
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
        //console.log(this.train);
        this.putRequest("/personnel/train/",this.train).then(res=>{

        });
        this.dialogVisible = false;
        this.initTrainList();
        this.initTrain();
      },
      //弹出添加框
      showAddView() {
        this.addDialogVisible = true;
      },
      //添加
      addTrain() {
        this.postRequest("/personnel/train/",this.train).then(res=>{

        });
        this.addDialogVisible = false;
        this.initTrainList();
        this.initTrain();
      },
      //取消添加
      addCancel() {
        this.addDialogVisible = false;
        this.initTrain();
      },
      initTrain() {
        this.train = {
          id: null,
              eid: null,
              trainDate: null,
              trainContent: '',
              remark: ''
        }
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initTrainList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initTrainList();
      }

    }
  }
</script>

<style scoped>
  .topButton{
    margin-top: 15px;
    display: flex;
    justify-content: space-between;
  }
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
