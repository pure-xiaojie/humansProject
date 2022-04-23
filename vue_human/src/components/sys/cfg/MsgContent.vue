<template>
  <div>
    <!--添加输入框 -->
    <div>
      <el-button size="small" type="primary" @click="addContent">添加</el-button>
    </div>

    <!--数据展示表格 -->
    <div class="posTable">
      <el-table :data="msgContent" border stripe style="width: 100%"
                @selection-change="handleSelectionChange" tooltip-effect="light">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="编号" width="55"></el-table-column>
        <el-table-column prop="title" label="公告标题" width="250"></el-table-column>
        <el-table-column prop="message" label="公告内容"  width="250" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="140"></el-table-column>

        <el-table-column label="操作" width="150">
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

      <el-button size="small" type="primary" style="margin-top: 10px"
                 :disabled="multipleSelection.length == 0" @click="delIds">批量删除</el-button>
    </div>

    <!--添加、修改对话框 -->
    <el-dialog :title="title" :visible.sync="dialogVisible" width="30%">
      <el-form>
        <el-form-item label="公告标题">
          <el-input class="updatePosInput" size="small" v-model="updateMsg.title"></el-input>
        </el-form-item>

        <el-form-item label="公告内容">
          <el-input type="textarea" class="updatePosInput" size="small" v-model="updateMsg.message"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancelDialog">取 消</el-button>
        <el-button size="small" type="primary" @click="submit">确 定</el-button>
      </span>

    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "MsgContent",
    data() {
      return {
        updateMsg: {
          id: null,
          title : '',
          message: '',
          createDate: null,
          img: ''
        },
        msgContent: [],
        dialogVisible: false,
        title: '',
        multipleSelection: [],

        //用户
        user: JSON.parse(window.sessionStorage.getItem("user")),
      }
    },
    mounted() {
      this.initContents();
    },
    methods: {
      initData() {
        this.updateMsg = {
          id: null,
          title : '',
          message: '',
          createDate: null,
          img: ''
        }
      },
      //添加公告
      addContent() {
        this.title = '发布公告';
        this.dialogVisible = true;
      },
      //修改公告
      handleEdit(data) {
        Object.assign(this.updateMsg , data);
        this.title = '修改公告';
        this.dialogVisible = true;
      },
      //添加、修改提交
      submit() {
        this.updateMsg.img = this.$store.state.currentUser.userface;

        if(this.title == '发布公告') {
          this.postRequest("/content", this.updateMsg).then(res=>{
            this.initContents();
            this.initData();
          });
        } else {
          this.putRequest("/content", this.updateMsg).then(res=>{
            this.initContents();
            this.initData();
          });
        }

        this.dialogVisible = false;
      },
      //删除公告
      handleDelete(data) {
        this.$confirm('确定永久删除【'+data.title+'】！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/content/"+data.id).then(res=> {
            this.initContents();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //查询公告列表
      initContents() {
        this.getRequest("/content").then(res=> {
          this.msgContent = res.data;
          console.log(res);
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
          });
          //console.log(ids);
          this.deleteRequest("/content/"+ids).then(res=> {
            if(res) {
              this.initContents();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      cancelDialog() {
        this.dialogVisible = false;
        this.initData();
      }
    }
  }
</script>

<style scoped>
  .posTable {
    margin-top: 20px;
  }
  .updatePosInput {
    width: 300px;
    margin-left: 10px;
  }
</style>
