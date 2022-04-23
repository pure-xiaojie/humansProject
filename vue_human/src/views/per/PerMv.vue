<template>
  <div>
    <!--表格数据-->
    <div class="topInfo">
      <el-table :data="removeList" border stripe>
        <el-table-column type="selection" align="left" width="55"></el-table-column>
        <el-table-column prop="employee.workID" label="工号" width="100"></el-table-column>
        <el-table-column prop="employee.name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="employee.gender" label="性别" width="60"></el-table-column>
        <el-table-column prop="department.name" label="调后部门" width="120"></el-table-column>
        <el-table-column prop="jobLevel.name" label="调后职称" width="120"></el-table-column>
        <el-table-column prop="removeDate" label="调动时间" width="130"></el-table-column>
        <el-table-column prop="reason" label="调动原因" width="280"></el-table-column>
        <!--编辑与删除-->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="editRemove(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="delRemove(scope.row)">删除</el-button>
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
    <el-dialog title="调动修改" :visible.sync="dialogVisible"
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
          <td>部门：</td>
          <td>
            <!--手动弹框-->
            <el-popover placement="right"
                        title="请选择部门" style="width: 350px"
                        trigger="manual" v-model="depVisible3">
              <!--数据展示在tree中-->
              <el-tree :data="depList" :props="defaultProps"
                       default-expand-all @node-click="handleNodeClick"></el-tree>

              <!--部门选择按钮-->
              <div slot="reference" class="departmentClass" @click="showDepView3">
                {{inputDepName}}
              </div>
            </el-popover>
          </td>
        </tr>
        <tr>
          <td>职称：</td>
          <td>
            <el-select v-model="remove.afterJobId" placeholder="职称" size="mini" style="width: 350px">
              <el-option
                  v-for="item in jobLevels"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
        </tr>
        <tr>
          <td>调动时间：</td>
          <td>
            <el-date-picker v-model="remove.removeDate" size="small"
                            type="date" style="width:350px"
                            placeholder="选择日期">
            </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>调动原因：</td>
          <td><el-input v-model="remove.reason" placeholder="奖惩原因" size="small"
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
    name: "PerMv",
    data() {
      return {
        removeList: [],
        total: 0,
        page: 1,
        size: 10,
        //修改弹窗
        dialogVisible: false,

        //调动实体
        remove: {
          id: null,
          eid: null,
          afterDepId: null,
          afterJobId: null,
          removeDate: '',
          reason: ''
        },
        //员工实体
        emp: {
          id: null,
          name:"",
          workID:"",
        },
        //部门
        depList: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        depVisible3: false,
        inputDepName: '',
        //职称
        jobLevels: []
      }
    },
    mounted() {
      this.initRemoveList();
      this.initData();
    },
    methods: {
      //初始化调动数据
      initRemoveList() {
        this.getRequest("/personnel/remove/?page=" + this.page + '&size=' + this.size).then(res => {
          this.removeList = res.data;
          this.total = res.total;
        })
      },
      //修改调动
      editRemove(params) {
        this.remove = params;
        this.emp.name = params.employee.name;
        this.emp.workID = params.employee.workID;
        this.inputDepName = params.department.name;
        this.dialogVisible = true;
      },
      //删除调动
      delRemove(remove) {
        this.$confirm('确定删除','提示',{
          cancelButtonText: '取消',
          confirmButtonText: '确定'
        }).then(() => {
          this.deleteRequest("/personnel/remove/"+remove.id).then(res=>{
            this.initRemove();
            this.initRemoveList();
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
        this.putRequest("/personnel/remove/",this.remove).then(res=>{

        });
        this.dialogVisible = false;
        this.initRemoveList();
        this.initRemove();
      },
      initRemove() {
        this.remove = {
          id: null,
          eid: null,
          afterDepId: null,
          afterJobId: null,
          removeDate: '',
          reason: ''
        }
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initRemoveList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initRemoveList();
      },
      //初始化工属性
      initData() {
        console.log("loading info……");
        //获取职称
        if(!window.sessionStorage.getItem("jobLevels")) {
          this.getRequest("/employee/basic/levels").then(res=>{
            if(res) {
              this.jobLevels = res;
              window.sessionStorage.setItem("jobLevels",JSON.stringify(res));
            }
          })
        }else{
          //从session获取
          this.jobLevels = JSON.parse(window.sessionStorage.getItem("jobLevels"));
        }
        //获取部门信息
        if(!window.sessionStorage.getItem("depList")) {
          this.getRequest("/employee/basic/deps").then(res=>{
            if(res) {
              this.depList = res;
              window.sessionStorage.setItem("depList",JSON.stringify(res));
            }
          })
        }else{
          //从session获取
          this.depList = JSON.parse(window.sessionStorage.getItem("depList"));
        }
      },
      //部门tree选择属性
      handleNodeClick(data) {
        this.depVisible3 = !this.depVisible3;
        this.inputDepName = data.name;
        this.remove.afterDepId = data.id;
      },
      //展示部门选择树
      showDepView3() {
        this.depVisible3 = !this.depVisible3;
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
  .departmentClass{
    width: 150px;
    height: 26px;
    padding-left: 8px;
    display: inline-flex;
    font-size: 13px;
    border: 1px solid #dedede;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 5px;
    align-items: center;
    box-sizing: border-box;
  }
</style>
