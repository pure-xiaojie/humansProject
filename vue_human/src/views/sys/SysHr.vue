<template>
  <div>
    <!--搜索栏-->
    <div class="searchInput">
      <el-input v-model="keySearch" placeholder="输入用户名搜索…"
      prefix-icon="el-icon-search" @keydown.enter.native="search"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="search()">搜索</el-button>
    </div>

    <!--用户数据展示-->
    <div class="containerMain">
      <el-card class="box-card" v-for="(item,index) in users" :key="index">
        <div slot="header" class="hrTop">
          <img :src="item.userface" :alt="item.name" class="imgStyle">
          <span style="margin-top: 15px">{{item.name}}</span>
          <el-button class="cardBtn" type="text" icon="el-icon-delete" @click="delHr(item)"></el-button>
        </div>
        <div>
          <div class="hrInfo">
            <div>用户名：{{item.name}}</div>
            <div>手机号码：{{item.phone}}</div>
            <div>电话号码：{{item.telephone}}</div>
            <div>地址：{{item.address}}</div>
            <div>用户状态：<el-switch @change="updateUser(item)"
                v-model="item.enabled"
                active-text="启用"
                inactive-text="禁用">
            </el-switch></div>
            <div>用户角色：
              <el-tag v-for="(role,indexj) in item.roles"
                      :key="indexj" style="margin-right: 5px" type="info" size="mini">
                {{role.nameZh}}</el-tag>
              <!--修改角色-->
              <el-popover placement="right" title="角色列表"
                  width="200" trigger="click" @show="showSelect(item)"
                          @hide="hideCommit(item)">
                <!--角色选择框-->
                <el-select v-model="selectedRoles" placeholder="角色选择" multiple>
                  <el-option
                      v-for="(role,indexr) in roles"
                      :key="indexr"
                      :label="role.nameZh"
                      :value="role.id">
                  </el-option>
                </el-select>
                <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
              </el-popover>
            </div>
            <div>备注：{{item.remark}}</div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SysBasic",
    data() {
      return {
        keySearch: '',
        users: [],
        roles: [],
        selectedRoles: []
      }
    },
    mounted() {
      this.initUsers();
    },
    methods: {
      //初始化用户数据
      initUsers() {
        this.getRequest("/system/user/hr/?keywords=" + this.keySearch).then(res=>{
          if(res) {
            this.users = res;
          }
        })
      },
      //搜索
      search() {
        this.initUsers();
      },
      //更新
      updateUser(user) {
        this.putRequest("/system/user/hr/",user).then(res=>{
          if(res) {
            this.initUsers();
          }
        })
      },
      //删除
      delHr(hr) {
        this.$confirm('确定永久删除【'+hr.name+'】用户！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/user/hr/"+hr.id).then(res=> {
            this.initUsers();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //展示角色下拉框
      showSelect(hr) {
        //给用户已有角色赋值
        this.selectedRoles = [];
        let roles = hr.roles;
        roles.forEach(role=>{
          this.selectedRoles.push(role.id);
        });

        //获取所有角色
        this.getRequest("/system/user/hr/roles").then(res=>{
          if(res) {
            this.roles = res;
          }
        })
      },
      //角色选中关闭时提交
      hideCommit(hr) {
        let roles = [];
        roles = Object.assign(roles,hr.roles);
        let flag = false;
        //判断是否更改
        if(this.selectedRoles.length != roles.length) {
          flag = true;
        } else{
          for(let i = 0; i < roles.length; i++) {
            let ro = roles[i];
            for(let j = 0; j < this.selectedRoles.length; j++) {
              if(ro.id == this.selectedRoles[j]){
                roles.splice(i,1);
                i--;
                break;
              }
            }
          }
          if(roles.length != 0) {
            flag = true;
          }
        }

        //数据已更改，提交到后台更新
        if(flag) {
          let url = '/system/user/hr/role?hrid=' + hr.id;
          this.selectedRoles.forEach(sr=>{
            url += '&rids=' + sr;
          });
          this.putRequest(url).then(res=>{
            if(res) {
              this.initUsers();
            }
          })
        }
      }
    }
  }
</script>

<style scoped>
  .searchInput{
    margin-top: 20px;
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
  .searchInput .el-input{
    width: 400px;
    margin-right: 10px;
  }
  .cardBtn{
    float: right;
    padding: 3px 0;
    color: #5f9ea0;
  }
  .containerMain{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
  }
  .box-card {
    width: 350px;
    margin-bottom: 20px;
  }
  .hrTop{
    display: flex;
    justify-content: space-between
  }
  .imgStyle{
    width: 50px;
    height: 50px;
    border-radius: 50px;
  }
  .hrInfo div{
    font-size: 12px;
    color: cadetblue;
    margin-bottom: 3px;
  }
</style>
