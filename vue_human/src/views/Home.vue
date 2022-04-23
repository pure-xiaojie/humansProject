<template>
  <div>
    <el-container>
      <el-header class="homeHeader">
        <div class="title">企业管理系统</div>
        <div>
          <!--聊天按钮-->
          <el-button icon="el-icon-bell" type="text" class="chatButton"
                     size="normal" @click="goChat">
          </el-button>

          <!--个人中心-->
          <el-dropdown class="userInfo" @command="commandHandler">
            <span class="el-dropdown-link">
              {{user.name}}<i><img :src="avatar(user.userface)" alt=""></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="setting">设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu router unique-opened>
            <el-menu-item index="../home">
              <i class="el-icon-menu" style="color: cadetblue;margin-left: -5px"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
              <template slot="title">
                <i :class="item.iconCls" style="color: cadetblue;margin-right: 5px"></i>
                <span>{{item.name}}</span>
              </template>
              <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">
                <i :class="child.iconCls" style="color: cadetblue;margin-right: 5px"></i>
                <span>{{child.name}}</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <!--面包屑 -->
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <!--首页home -->
          <DashBoard v-if="this.$router.currentRoute.path=='/home'"></DashBoard>
          <router-view class="homeRouterView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import DashBoard from '../components/dashboard/dashboard'
  export default {
    name: "Home",
    components: {DashBoard},
    data() {
      return {
        //user: JSON.parse(window.sessionStorage.getItem("user"))
      }
    },
    computed: {
      routes() {
        return this.$store.state.routes;
      },
      user() {
        return this.$store.state.currentUser;
      }
    },
    methods: {
      //聊天事件
      goChat() {
        this.$router.push("/chat");
      },
      commandHandler(cmd) {
        if (cmd == 'logout') {
          this.$confirm('此操作将注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.getRequest("/logout");
            this.$store.commit('initRoutes',[]);
            window.sessionStorage.removeItem("user");
            this.$router.replace("/");
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消操作!'
            });
          });
        }else if (cmd == 'userInfo') {
          this.$router.push('/userInfo');
        }else if (cmd == 'profile') {
          this.$router.push('/profile');
        }
      },
      //图像处理
      avatar(url) {
        if(url.length < 20)
          return require(`@/assets/avatar/${url}`);
        else
          return url;
      }
    }
  }
</script>

<style scoped>
  .homeRouterView {
    margin-top: 10px;
  }

  .homeHeader {
    background-color: #5f9ea0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0px 15px;
    box-sizing: border-box;
  }

  .homeHeader .title {
    font-size: 30px;
    font-family: 华文行楷;
    color: #f9ffff
  }

  .homeHeader .userInfo {
    cursor: pointer;
  }

  .el-dropdown-link img {
    width: 38px;
    height: 38px;
    border-radius: 24px;
    margin-left: 8px;
  }

  .el-dropdown-link {
    display: flex;
    align-items: center;
  }
  .chatButton {
    margin-right: 8px;
    color: #000000;
  }

</style>
