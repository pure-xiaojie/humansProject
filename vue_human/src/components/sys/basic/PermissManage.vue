<template>
  <div>
    <!--权限组添加栏-->
    <div class="addMain">
      <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input size="small" placeholder="请输入角色中文名"
                v-model="role.nameZh" @keydown.enter.native="addRole"></el-input>

      <el-button size="small" type="primary" @click="addRole">添加</el-button>
    </div>

    <!--权限组数据栏-->
    <div class="roleBody">
      <el-collapse accordion @change="change" v-model="activeName">
        <el-collapse-item v-for="(item,index) in roleList" :key="index"
            :title="item.nameZh" :name="item.id">
          <!--对应角色展开的可操作菜单项-->
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的菜单项</span>
              <el-button style="float: right; padding: 3px 0" type="text"
                         icon="el-icon-delete" @click="delRole(item)"></el-button>
            </div>
            <!--树形菜单展示-->
            <div>
              <el-tree :data="menuList" :props="defaultProps" :key="index"
                       show-checkbox ref="tree" node-key="id"
                       :default-checked-keys="selectedMenu" ></el-tree>
              <div class="treeButton">
                <el-button size="mini" @click="activeName=-1">取消</el-button>
                <el-button size="mini" type="primary" @click="update(item.id,index)">确定</el-button>
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
  export default {
    name: "PositionManage",
    data() {
      return {
        role: {
          name: '',
          nameZh: ''
        },
        roleList: [],
        menuList: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        selectedMenu: [],
        activeName: -1
      }
    },
    mounted() {
      this.initRoles();
    },
    methods: {
      //加载角色列表
      initRoles() {
        this.getRequest("/system/basic/permission/").then(res=>{
          if(res) {
            this.roleList = res;
          }
        })
      },
      //展开加载数据
      change(roleId) {
        if(roleId) {
          //角色对应的菜单项
          this.getRequest("/system/basic/permission/menus").then(res=>{
            this.menuList = res;
          });

          //角色可操作的菜单
          this.getRequest("/system/basic/permission/menu/"+roleId).then(res=>{
            this.selectedMenu = res;
          })
        }
      },
      //添加
      addRole() {
        if(this.role.name && this.role.nameZh) {
          this.postRequest("/system/basic/permission/role",this.role).then(res=>{
            if(res) {
              this.role.name = '';
              this.role.nameZh = '';
              this.initRoles();
            }
          })
        } else if(this.role.name) {
          this.$message.error("角色中文名未输入！")
        } else {
          this.$message.error("角色英文名未输入！")
        }
      },
      //修改
      update(rid,index) {
        //获取选中的菜单项id列表
        let selectIds = this.$refs.tree[index].getCheckedKeys(true);

        let url = "/system/basic/permission/?rid="+rid;

        //遍历每个id拼接到请求的URL中
        selectIds.forEach(item=>{
          url += '&menusId='+item;
        });

        this.putRequest(url).then(res=>{
          this.initRoles();
          this.activeName = -1;
        })
      },
      delRole(data) {
        this.$confirm('确定永久删除【'+data.name+'】角色！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/permission/"+data.id).then(res=> {
            this.initRoles();
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
  .addMain{
    display: flex;
    justify-content: flex-start;
  }
  .addMain .el-input {
    width: 300px;
    margin-right: 10px;
  }
  .roleBody{
    width: 55%;
    margin-top: 20px;
  }
  .treeButton{
    margin-top: 10px;
    display: flex;
    justify-content: flex-end;
  }
</style>
