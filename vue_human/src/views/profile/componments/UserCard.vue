<template>
  <el-card style="margin-bottom:20px;" v-if="user!=null">
    <div slot="header" class="clearfix">
      <span>个人信息</span>
    </div>
    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="avatar(user.userface)" :height="'100px'" :width="'100px'" :hoverable="false">
          <el-link type="primary" class="change-avatar" @click="dialogVisible = true">更换头像
          </el-link>
        </pan-thumb>
      </div>
      <div class="box-center">
        <div class="user-name text-center">{{ user.username }}</div>
        <div class="user-role text-center text-muted">
          <span>{{user.remark}}</span>
        </div>

      </div>
    </div>
    <div class="user-bio">
      <div class="user-education user-bio-section">
        <div class="user-bio-section-header">
          <i class="fa fa-id-card-o" style="color: cadetblue;margin-right: 5px"/>
          <span>个人信息</span>
        </div>
        <div class="user-bio-section-body">
          <ul class="list-group list-group-striped">
            <li class="list-group-item">
              <i class="el-icon-user" style="color: cadetblue;margin-right: 5px"></i>
              用户名称
              <div class="pull-right">{{ user.name }}</div>
            </li>
            <li class="list-group-item">
              <i class="el-icon-phone" style="color: cadetblue;margin-right: 5px"></i>
              手机号码
              <div class="pull-right">{{ user.phone }}</div>
            </li>
            <li class="list-group-item">
              <i class="el-icon-s-home" style="color: cadetblue;margin-right: 5px"></i>
              用户地址
              <div class="pull-right">{{ user.address }}</div>
            </li>
            <li class="list-group-item">
              <i class="el-icon-user-solid" style="color: cadetblue;margin-right: 5px"></i>
              所属角色
              <div class="pull-right">
                <span v-if="user.roles" v-for="role in user.roles">
                  {{ role.nameZh}}
                </span>
                <span v-else>无角色</span>
              </div>
            </li>
            <li class="list-group-item">
              <i class="el-icon-info" style="color: cadetblue;margin-right: 5px"></i>
              用户备注
              <div class="pull-right">{{ user.remark == null ? "无" :  user.remark}}</div>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!--头像修改-->
    <avatar
      :dialog-visible="dialogVisible"
      @close="dialogVisible = false"
      @success="changeSuccess"
    />
  </el-card>
</template>

<script>
  import Avatar from "./Avatar";
  import PanThumb from "./PanThumb"
  export default {
    components: {Avatar,PanThumb},
    props: {
      users: {
        type: Object
      }
    },
    data() {
      return {
        user: this.users,
        dialogVisible: false,
        page: {
          width: window.screen.width * 0.5,
          height: window.screen.height * 0.5
        }
      }
    },
    methods: {
      initUser() {
        this.getRequest('/user/info').then(resp => {
          if (resp) {
            this.user = resp;
            window.sessionStorage.setItem("user", JSON.stringify(resp));
            this.$store.commit('INIT_CURRENT_USER', resp);
          }
        })
      },
      //修改图片成功回调
      changeSuccess(avatar) {
        this.dialogVisible = false;
        this.initUser();
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

<style lang="scss" scoped>
  .box-center {
    margin: 0 auto;
    display: table;
  }

  .text-muted {
    color: #777;
  }

  .user-profile {

  .user-name {
    font-weight: bold;
    align-content: center;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

  .el-table {
    border-top: 1px solid #dfe6ec;
  }

  }
  .user-follow {
    padding-top: 20px;
  }

  }
  .user-bio {
    margin-top: 20px;
    color: #606266;

  span {
    padding-left: 4px;
  }

  .logo-wrapper {
    display: inline-block;
    margin: 10px 0;

    img {
      width: 1.4rem;
      display: inline-block;
      margin: 0 .6rem;
      cursor: pointer;

      &
      .unbind {
        -webkit-filter: grayscale(100%);
        -moz-filter: grayscale(100%);
        -o-filter: grayscale(100%);
        filter: grayscale(100%);
      }

      &
      .radius {
        border-radius: 50%;
      }

    }
  }
  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
  }
  .list-group {
    padding-left: 0px;
    list-style: none;
  }

  .list-group-item {
    border-bottom: 1px solid #e7eaec;
    border-top: 1px solid #e7eaec;
    margin-bottom: -1px;
    padding: 11px 0px;
    font-size: 13px;
  }
  .text-center {
    text-align: center !important;
  }
</style>
