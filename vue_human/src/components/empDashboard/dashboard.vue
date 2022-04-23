<template>
  <div class="dashboard-container">
    <!--介绍栏-->
    <el-card class="sales-card">
      <div class="clearfix">
        <div class='fl headL'>
          <div class="headImg">
            <img :src="avatar(emp.imgUrl)" style="width: 100px;height: 100px;"/>
          </div>
          <div class="headInfoTip">
            <p class="firstChild">早安，{{emp.name}}，祝你开心每一天！</p>
            <p class="lastChild">企业 职工  |  KK企业中心</p>
          </div>
        </div>

      </div>
    </el-card>
    <!--card-->
    <div style="margin-top:20px;" class="clearfix">
      <!--左侧导航-->
      <div style="width:55%; float:left">
        <!--公告栏-->
        <el-card style="margin-top:0px;">
          <div class="advContent">
            <div class="title">企业公告</div>
            <div class="contentItem">
              <ul class="noticeList">
                <li v-for="(item, index) in msgContent">
                  <div class="item">
                      <img  :src=avatar(item.img) alt="">
                      <div>
                        <p><span class="col">系统推送</span> {{item.title}}</p>
                        <p>{{item.message}}</p>
                        <p>{{item.createDate}}</p>
                      </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </el-card>
      </div>
      <!--右侧导航-->
      <div style="width:44%;float:right;margin-left:1%;">
        <!--日历-->
        <el-card class="box-card">
          <div slot="header" class="header">
            <span>工作日历</span>
          </div>
          <div class="total">
            <DateIndex />
          </div>
        </el-card>
        <!--帮助连接-->
        <el-card class="box-card" style="margin-top:20px;">
            <div class="header headTit">
              <span>帮助链接</span>
            </div>
            <div class="sideLink">
              <el-row>
                <el-col :span="8">
                  <a href="http://114.116.255.239:5050/group1/M00/00/00/wKgAQ2BqmgOActAOAAFeAOncdrY303.pdf" target="_blank">
                    <span class="icon iconGuide"></span>
                    <p>入门指南</p>
                  </a>
                </el-col>
                <el-col :span="8">
                  <a href="http://114.116.255.239:5050/group1/M00/00/00/wKgAQ2BqmVuAFJRKAALzdDkJj3o629.pdf" target="_blank">
                    <span class="icon iconHelp"></span>
                    <p>企业管理手册</p>
                  </a>
                </el-col>
                <el-col :span="8">
                  <a href="#">
                    <span class="icon iconTechnology"></span>
                    <p>联系技术支持</p>
                  </a>
                </el-col>
              </el-row>

            </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import DateIndex from './DateIndex'

export default {
  name: 'dashboard',
  components: {
    DateIndex,
  },
  data() {
    return {
      //公告
      msgContent: []
    }
  },
  computed: {
    emp() {
      return this.$store.state.currentEmp;
    }
  },
  mounted() {
    this.initMsgContent();
  },
  methods: {
    //图像处理
    avatar(url) {
      if(url.length < 20)
        return require(`@/assets/avatar/${url}`);
      else
        return url;
    },
    //获取公告
    initMsgContent() {
      this.getRequest("/content").then(res=> {
        this.msgContent = res.data;
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-container {
  margin: 3px;
  .headImg {
    float: left;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background: #999;
  }

  .headInfoTip {
    padding: 25px 0 0;
    margin-left: 120px;
    p {
      padding: 0 0 15px;
      margin: 0;
      &.firstChild {
        font-size: 24px;
      }
      &.lastChild {
        font-size: 20px;
        color: #7f8c8d;
      }
    }
  }
}

.box-card {
  padding: 5px 10px;
  .header {
    span {
      color: #2c3e50;
      font-size: 24px;
    }
    .item {
      color: #97a8be;
      float: right;
      padding: 3px 0;
    }
  }
  .headTit {
    span {
      border-bottom: 4px solid #8a97f8;
      padding-bottom: 10px;
    }
  }
}

// 销售额
.sales-card {
  position: relative;
  .header {
    position: absolute;
    right: 20px;
    top: 15px;
    z-index: 1;
  }
}

.headInfo {
  width: 100px;
  height: 85px;
  border-radius: 50px;
  padding: 20px 0 0;
  margin: 0 15px;
  display: inline-block;
  color: #fff;
  text-align: center;
  p {
    padding: 0 0 5px;
    margin: 0;
    font-size: 16px;
  }
  .fontS {
    font-size: 30px;
  }
}
.headInfo.numMs {
  background: #8a97f8;
  box-shadow: 1px 1px 5px #8a97f8;
}
.headInfo.numRz {
  background: #ffc100;
  box-shadow: 1px 1px 5px #ffc100;
}
.headInfo.numZ {
  background: #fa8564;
  box-shadow: 1px 1px 5px #fa8564;
}
.advContent {
  background: #fff;
  border-radius: 5px 5px 0px 0px;
  .title {
    font-size: 16px;
    padding: 20px;
    font-weight: bold;
    border-bottom: solid 1px #ccc;
  }
  .contentItem {
    padding: 0 30px;
    min-height: 350px;
    .item {
      display: flex;
      padding:18px 0 10px;
      border-bottom: solid 1px #ccc;
      .col {
        color: #8a97f8;
      }
      img {
        width: 56px;
        height: 56px;
        border-radius: 50%;
        margin-right: 10px;
      }
      p{
        padding: 0 0 8px;
      }
    }
    li, ul, p {
      list-style-type: none;
      margin: 0;
      padding: 0; }
  }
}
.noticeList {
  margin: 0;
  padding: 0;
  list-style-type: none;
}
.sideNav,
.sideLink {
  padding: 30px 0 0px;
  li {
    border: 1px solid #ddd;
    border-radius: 5px;
    width: 137px;
    height: 50px;
    text-align: center;
    line-height: 50px;
    margin: 0 15px 10px 0;
    font-size: 16px;
    float: left;
    a {
      display: block;
      height: 50px;
      color: #777;
    }
    &.addLinks{
      border: 1px dashed #8a97f8;
      a{
        color: #8a97f8;
      }
    }
  }
}
.sideLink {
  text-align: center;
  .icon {
    display: inline-block;
    width: 76px;
    height: 76px;
    background: url('../../assets/images/icon.png') no-repeat;
  }
  .iconGuide {
    background-position: 0 0;
  }
  .iconHelp {
    background-position: -224px 0;
  }
  .iconTechnology {
    background-position: -460px 0;
  }
  li, ul, p {
    list-style-type: none;
    margin: 0;
    padding: 0; }
}
.performInfo {
  border: 1px solid #e9e9e9;
  li {
    width: 33%;
    float: left;
    text-align: center;
    border-right: 1px solid #e9e9e9;
    &:last-child {
      border: 0 none;
    }
    padding: 10px 0;
    p {
      &:first-child {
        font-size: 40px;
      }
    }
  }
}
.radioInfo {
  em {
    display: inline-block;
    width: 6px;
    height: 6px;
    border-radius: 3px;
    vertical-align: middle;
    margin-right: 10px;
    &.user {
      background: #2ec7c9;
    }
    &.department {
      background: #b6a2de;
    }
    &.company {
      background: #5db3ef;
    }
  }
}
.clearfix {
  &:after {
    visibility: hidden;
    display: block;
    font-size: 0;
    content: " ";
    clear: both;
    height: 0;
  }
  list-style-type: none;
  margin: 0;
  padding: 0;
}
.fl {
  float: left;
}
.fr {
  float: right;
}
.icon{
  display: inline-block;
  width: 6px;
  height: 6px;
  border-radius: 3px;
  position: relative;
}
a:link,a:visited{
  text-decoration:none;  /*超链接无下划线*/
}
a:hover{
  text-decoration:underline;  /*鼠标放上去有下划线*/
}
.video {
  object-fit: cover;
  width: 100%;
  height: 90%;
}
</style>
