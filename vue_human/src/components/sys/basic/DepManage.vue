<template>
  <div class="departMain">
    <!--搜索框-->
    <el-input prefix-icon="el-icon-search"
        placeholder="请输入部门名称搜索…" v-model="searchText">
    </el-input>

    <!--部门信息展示-->
    <el-tree class="filter-tree" :data="deps" :props="defaultProps"
        :filter-node-method="filterNode" ref="tree"
        :expand-on-click-node="false" style="margin-top: 15px">
      <!--添加删除按钮-->
      <span class="custom-tree-node BtnMain" slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button type="primary" size="mini" class="Btn"
              @click="() => showAddView(data)">
            添加部门
          </el-button>
          <el-button type="danger" size="mini" class="Btn"
              @click="() => deleteDept(data)">
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>

    <!--添加弹框-->
    <el-dialog title="添加部门" :visible.sync="dialogVisible" width="25%">
      <div>
        <table class="dialogTable">
          <tr>
            <td>上级部门：</td>
            <td>{{pName}}</td>
          </tr>
          <tr>
            <td>部门名称：</td>
            <td>
              <el-input v-model="addDep.name" size="small" placeholder="请输入部门名称…"/>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="addDepartment">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "DepManage",
    data() {
      return {
        searchText: '',
        deps: [],
        addDep: {
          name: '',
          parentId: -1
        },
        pName: '',
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        dialogVisible: false
      }
    },
    watch: {
      //监控搜索框searchText值的改变
      searchText(val) {
        //实际是调用filterNode方法
        this.$refs.tree.filter(val);
      }
    },
    mounted() {
      this.initDepartment();
    },
    methods: {
      /**
       * 主要用来搜索展开
       * @param value 传过来的val值
       * @param data  tree树中的json对象
       * @returns {boolean}
       */
      filterNode(value, data) {
        if (!value) return true; //为空，树件会全部展开
        return data.name.indexOf(value) !== -1;   //搜索到
      },
      //初始化部门信息
      initDepartment() {
        this.getRequest("/system/basic/depart/").then(res=>{
          if(res) {
            this.deps = res;
          }
        })
      },
      //弹出添加框
      showAddView(data) {
        this.pName = data.name;
        this.addDep.parentId = data.id;
        this.dialogVisible = true;
      },
      //添加
      addDepartment() {
        this.postRequest("/system/basic/depart/",this.addDep).then(res=>{
          if(res) {
            this.dialogVisible = false;
            this.appendDate(this.deps,res.obj);

            //改变添加数据的值
            this.addDep.parentId = -1;
            this.addDep.name = '';
            this.pName = '';
          }
        })
      },
      //递归实现，将追加的数据动态拼接到初始数据this.deps中
      appendDate(deps,dep) {
        for(let i = 0; i < deps.length; i++) {
          let cur = deps[i];
          if(cur.id == dep.parentId) {
            cur.children = cur.children.concat(dep);
            if(cur.children.length > 0) {
              cur.parent = true;
            }
            return;
          } else {
            this.appendDate(cur.children,dep);
          }
        }
      },
      //删除部门信息
      deleteDept(data) {
        if(data.parent) {
          this.$message.error("父部门不能直接删除！");
        } else {
          this.$confirm('确定永久删除【'+data.name+'】部门！', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteRequest("/system/basic/depart/"+data.id).then(res=> {
              if(res) {
                this.removeDeps(this.deps,data.id);
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        }
      },
      /**
       * 将删除的元素动态移除
       * @param p     当前子tree的父节点
       * @param deps  全局部门信息
       * @param id    要删除的部门id
       */
      removeDeps(p,deps,id) {
        for (let i = 0; i < deps.length; i++) {
          let cur = deps[i];
          if(cur.id == id) {
            deps.splice(i,1);
            //改变父节点
            if(deps.length == 0) {
              p.parent = false;
            }
            return;
          } else {
            this.removeDeps(cur,cur.children,id);
          }
        }
      }
    }
  }
</script>

<style scoped>
  .departMain{
    width: 50%;
  }
  .BtnMain{
    display: flex;
    width: 100%;
    justify-content: space-between;
  }
  .Btn{
    padding: 2px;
  }
  .dialogTable{
    border-collapse:separate;
    border-spacing:5px 15px;
  }

</style>
