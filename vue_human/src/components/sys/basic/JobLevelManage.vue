<template>
  <div>
    <!--添加输入框 -->
    <div>
      <el-input placeholder="添加职称…" prefix-icon="el-icon-plus"
                class="jobLevelInput" size="small" v-model="jobLev.name"></el-input>

      <el-select v-model="jobLev.titleLevel" placeholder="职称等级" size="small" class="selectLev">
        <el-option
            v-for="item in titleLevels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button size="small" type="primary" @click="addJobLevel">添加</el-button>
    </div>

    <!--数据展示表格 -->
    <div class="jobLevelTable">
      <el-table :data="jobData" border stripe style="width: 100%"
                @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="id"
            label="编号"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职位名称"
            width="250">
        </el-table-column>
        <el-table-column
            prop="titleLevel"
            label="职称级别"
            width="180">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间"
            width="280">
        </el-table-column>
        <el-table-column label="是否启用" width="150">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.enabled" type="primary">已启用</el-tag>
            <el-tag v-else type="danger">未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--批量删除-->
      <el-button size="small" type="primary" style="margin-top: 10px"
                 :disabled="multipleSelection.length == 0" @click="delIds">批量删除</el-button>
    </div>

    <!--修改对话框 -->
    <el-dialog title="职称修改" :visible.sync="dialogVisible" width="30%">
      <div>
        <table class="dialogTable">
          <tr>
            <td>职称名称：</td>
            <td><el-input v-model="updateJobLev.name" size="small" class="updateJobInput"/></td>
          </tr>
          <tr>
            <td>职称级别：</td>
            <td>
              <el-select v-model="updateJobLev.titleLevel" placeholder="职称等级" size="small" class="selectLev">
                <el-option
                    v-for="item in titleLevels"
                    :key="item"
                    :label="item"
                    :value="item">
                </el-option>
              </el-select>
            </td>
          </tr>
          <tr>
            <td>是否启用：</td>
            <td>
              <el-switch
                  v-model="updateJobLev.enabled"
                  active-text="启用"
                  inactive-text="禁用">
              </el-switch>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: "PositionManage",
    data() {
      return {
        jobLev: {
          name: '',
          titleLevel: '',
          enabled: false
        },
        updateJobLev: {
          name: '',
          titleLevel: '',
          enabled: false
        },
        titleLevels: [
            '正高级',
            '副高级',
            '中级',
            '初级',
            '员级',
        ],
        jobData: [],
        dialogVisible: false,
        multipleSelection: []
      }
    },
    //钩子函数，在页面加载时执行
    mounted() {
      this.initJobLevels();
    },
    methods: {
      //初始化
      initJobLevels() {
        this.getRequest("/system/basic/job/").then(res=>{
          if(res) {
            this.jobData = res;
          }
        })
      },
      //添加
      addJobLevel() {
        if(this.jobLev.name && this.jobLev.titleLevel) {
          this.postRequest("/system/basic/job/",this.jobLev).then(res=>{
            if(res) {
              this.initJobLevels();
              this.jobLev.name = '';
              this.jobLev.titleLevel = '';
            }
          })
        }else if(this.jobLev.name) {
          this.$message.error("职称级别为选择！");
        }else {
          this.$message.error("职称名称不可为空！");
        }
      },
      //修改
      handleEdit(data) {
        Object.assign(this.updateJobLev,data);
        this.dialogVisible = true;
      },
      update() {
        this.dialogVisible = false;
        if(this.updateJobLev.name && this.updateJobLev.titleLevel) {
          this.putRequest("/system/basic/job/",this.updateJobLev).then(res=>{
            if(res) {
              this.initJobLevels();
            }
          })
        }else if(this.updateJobLev.name) {
          this.$message.error("职称级别为选择！");
        }else {
          this.$message.error("职称名称不可为空！");
        }
      },
      //删除
      handleDelete(data) {
        this.$confirm('确定永久删除【'+data.name+'】职称！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/job/"+data.id).then(res=> {
            this.initJobLevels();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //多选
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      delIds() {
        this.$confirm('确定永久删除【'+this.multipleSelection.length+'】项！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = '?';
          this.multipleSelection.forEach(item=>{
            ids += 'ids=' + item.id + '&';
          })
          //console.log(ids);
          this.deleteRequest("/system/basic/job/"+ids).then(res=> {
            if(res) {
              this.initJobLevels();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>

<style scoped>
  .jobLevelInput{
    width: 300px;
    margin-right: 10px;
  }
  .selectLev{
    margin-right: 8px;
    width: 300px;
  }
  .jobLevelTable{
    margin-top: 10px  ;
  }
  .updateJobInput {
    width: 300px;
    margin-left: 0px;
  }
  .dialogTable{
    border-collapse:separate;
    border-spacing:5px 15px;
  }

</style>
