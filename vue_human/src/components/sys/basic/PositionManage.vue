<template>
  <div>
    <!--添加输入框 -->
    <div>
      <el-input v-model="pos.name" placeholder="添加职位…" prefix-icon="el-icon-plus"
                class="posInput" size="small" @keydown.enter.native="addPosition"></el-input>

      <el-button size="small" type="primary" @click="addPosition">添加</el-button>
    </div>

    <!--数据展示表格 -->
    <div class="posTable">
      <el-table :data="position" border stripe style="width: 100%"
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
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-button size="small" type="primary" style="margin-top: 10px"
      :disabled="multipleSelection.length == 0" @click="delIds">批量删除</el-button>
    </div>

    <!--修改对话框 -->
    <el-dialog title="职位修改" :visible.sync="dialogVisible" width="30%">
      <div>
        职位名称：
        <el-input class="updatePosInput" size="small" v-model="updatePos.name"></el-input>
      </div>
      <div style="margin-top: 25px">
        是否启用：
        <el-switch
            v-model="updatePos.enabled"
            active-text="启用"
            inactive-text="禁用">
        </el-switch>
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
        pos: {
          name : '',
          enabled: false
        },
        updatePos: {
          name : '',
          enabled: false
        },
        position: [],
        dialogVisible: false,
        multipleSelection: []
      }
    },
    mounted() {
      this.initPositions();
    },
    methods: {
      //添加职位
      addPosition() {
        if(this.pos.name) {
          this.postRequest("/system/basic/pos/",this.pos).then(res=>{
            if(res) {
              this.pos.name = '';
              this.initPositions();
            }
          })
        } else {
          this.$message.error("职位名称不可为空！");
        }
      },
      //修改职位
      handleEdit(index,data) {
        Object.assign(this.updatePos,data);
        this.dialogVisible = true;
      },
      update() {
        this.dialogVisible = false;
        if(this.updatePos.name) {
          this.putRequest("/system/basic/pos/",this.updatePos).then(res=>{
            if(res) {
              this.updatePos.name = '';
              this.initPositions();
            }
          })
        }
      },
      //删除职位
      handleDelete(index,data) {
        this.$confirm('确定永久删除【'+data.name+'】！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/pos/"+data.id).then(res=> {
            this.initPositions();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //查询职位列表
      initPositions() {
        this.getRequest("/system/basic/pos/").then(res=> {
          this.position = res;
        })
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
          this.deleteRequest("/system/basic/pos/"+ids).then(res=> {
            if(res) {
              this.initPositions();
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
  .posInput {
    width: 300px;margin-right: 8px
  }
  .posTable {
    margin-top: 20px;
  }
  .updatePosInput {
    width: 300px;
    margin-left: 10px;
  }
</style>
