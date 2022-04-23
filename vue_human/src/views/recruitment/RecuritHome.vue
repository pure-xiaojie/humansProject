<template>
  <div class="home">
    <!--企业招聘栏-->
    <div ref="banner" class="banner content-item-block">
      <div class="menu">
        <el-menu default-active=1 class="el-menu-demo"
                 mode="horizontal">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">
            <a href="/#/jobList">职位列表</a>
          </el-menu-item>
          <el-menu-item index="3">
            <a href="/#/login">登录</a>
          </el-menu-item>
        </el-menu>
      </div>
      <!--视频-->
      <div class="video-wrapper">
        <video
            ref="video"
            class="video"
            preload="auto"
            muted
            loop
            playsinline
            autoplay
            poster="//sf3-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/mainland/video-poster_1576231362701.png"
            src="//sf1-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/mainland/gongquhunjian_1080.min.mp4"
            tabindex="-1"
        ></video>
      </div>
      <h1 class="banner-title">
        和优秀的人 做有挑战的事
        <div class="subTitle">JOIN BYTEDANCE</div>
      </h1>
      <div class="search">
        <input-search @search="searchJobs" placeholder="输入城市或职位进行搜索"></input-search>
      </div>
      <div class="bottom-tips">
        <span class="arrow-down">
          <i>⤓</i>
        </span>
        <span class="vertical-dashed"></span>
      </div>
    </div>

    <!-- 企业文化介绍 -->
    <div class="main">
      <!-- 企业生活 -->
      <div class="byteLife content-item-block">
        <h1 class="byteLife-title">企业生活</h1>
        <div class="block-item block-item-one">
          <div class="block-item-column block-item-column-text">
            <h2 class="block-item-column-title">在扁平开放的氛围 里工作</h2>
            <div class="content">
              <div class="content-item">
                <h2 class="content-item-title">务实扁平的工作氛围</h2>
                <p class="content-item-desc">不讲 title 和层级，敢于授权新人，专注完成业务目标，没有领地意识</p>
              </div>
              <div class="content-item">
                <h2 class="content-item-title">务实扁平的工作氛围</h2>
                <p class="content-item-desc">不讲 title 和层级，敢于授权新人，专注完成业务目标，没有领地意识</p>
              </div>
              <div class="content-item">
                <h2 class="content-item-title">务实扁平的工作氛围</h2>
                <p class="content-item-desc">不讲 title 和层级，敢于授权新人，专注完成业务目标，没有领地意识</p>
              </div>
            </div>
          </div>
          <div class="block-item-column block-item-column-image">
            <img src="https://sf3-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/mainland/life_1.png" alt />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import InputSearch from '../../components/recruit/InputSearch'
  export default {
    name: "RecuritHome",
    components: {InputSearch},
    data() {
      return {
        search: "",
      };
    },
    methods: {
      searchJobs(keyword) {
        this.$router.push({path: '/jobList?keyword='+keyword});
      },
    },
  };
</script>

<style lang="less" scoped>
  @keyframes verticalMotion {
    0% {
      transform: translateY(6px);
    }
    50% {
      transform: translateY(-6px);
    }
    100% {
      transform: translateY(6px);
    }
  }
  @keyframes scrollToRight {
    0% {
      left: -100%;
    }
    100% {
      left: 100%;
    }
  }
  .home {
    .main {
      width: 1300px;
      margin: auto;
    }
    .content-item-block {
      .title {
        font-size: 32px;
      }
    }
  }
  .banner {
    height: 100vh;
    min-height: 400px;
    min-width: 1300px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: rgba(51, 112, 255, 0.2);
    position: relative;
    .video-wrapper {
      font-size: 0;
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      z-index: -1;
      overflow: hidden;
      .video {
        object-fit: cover;
        width: 100%;
        height: 100%;
      }
    }
    &-title {
      color: #fff;
      font-size: 60px;
      text-align: center;
      .subTitle {
        &::after,
        &::before {
          content: "";
          position: absolute;
          background-image: linear-gradient(
              270deg,
              hsla(0, 0%, 100%, 0),
              #fff 47%,
              hsla(0, 0%, 100%, 0)
          );
          height: 2px;
          width: 89px;
          top: 50%;
          transform: translateY(-50%);
        }
        &:before {
          right: 10px;
        }
        &:after {
          left: 10px;
        }
        position: relative;
        margin-top: 10px;
        font-size: 14px;
        letter-spacing: 20px;
      }
    }
    .search {
      width: 500px;
      margin-top: 100px;
      margin-bottom: 40px;
    }
    .bottom-tips {
      position: absolute;
      bottom: 30px;
      width: 50px;
      height: 50px;
      flex-shrink: 0;
      text-align: center;
      .arrow-down {
        display: block;
        margin-bottom: 4px;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        border: 1px solid #fff;
        text-align: center;
        line-height: 40px;
        color: #fff;
        cursor: pointer;
        font-size: 21px;
        i {
          animation: verticalMotion 1.3s infinite linear;
          display: inline-block;
          font-style: normal;
        }
      }
      // 分割竖线
      .vertical-dashed {
        border-left: 1px dashed #fff;
        height: 30px;
        display: inline-block;
      }
    }
  }
  // 产品
  .product {
    margin-top: 40px;
    text-align: center;
    padding: 40px 40px;
    background: url(//sf1-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/mainland/global.png);
    background-size: 100%;
    background-repeat: no-repeat;
    background-position: 0 370px;
    .desc {
      margin: 40px 0;
    }
    &-list {
      display: flex;
      justify-content: space-between;
      width: 100%;
    }
    &-item {
      width: 90px;
      // height: 90px;
      cursor: pointer;
      &:nth-child(1),
      &:nth-child(9) {
        margin-top: 116px;
      }
      &:nth-child(2),
      &:nth-child(8) {
        margin-top: 80px;
      }
      &:nth-child(3),
      &:nth-child(7) {
        margin-top: 55px;
      }
      &:nth-child(4),
      &:nth-child(6) {
        margin-top: 30px;
      }
      &:hover {
        color: #3370ff;
        img {
          border-radius: 19px;
          transform: scale(1.2);
          transform-origin: bottom;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }
      }
      img {
        width: 90px;
        height: 90px;
        border-radius: 19px;
        margin-bottom: 10px;
        transition: transform 0.3s;
      }
    }
    .more {
      margin: auto;
      margin-top: 170px;
      width: 149px;
    }
  }
  // 职位
  .job-category {
    text-align: center;
    margin-top: 90px;
    &-list {
      margin-top: 70px;
    }
    &-item {
      float: left;
      margin-bottom: 16px;
      .image {
        width: 250px;
        height: 250px;
        line-height: 250px;
        background-repeat: no-repeat;
        background-size: 100%;
        border-radius: 50%;
        margin: 0 auto;
        transition: all 0.3s;
        background-position: center;
        overflow: hidden;
        &:hover {
          box-shadow: 0 0 8px 1px #ccc;
          background-size: 110%;
          & + .name {
            color: #3370ff;
          }
        }
        .overlay {
          display: inline-block;
          width: 100%;
          height: 100%;
          background-color: rgba(0, 0, 0, 0.5);
          color: #fff;
          font-size: 40px;
        }
      }
      .name {
        margin-top: 23px;
        font-size: 22px;
      }
      margin: 0 2.5%;
      margin-bottom: 30px;
      width: 20%;
    }
  }
  // 字节范
  .byteStandard {
    padding: 40px 40px;
    overflow: hidden;
    .title {
      margin-bottom: 30px;
    }
    .desc {
      margin-bottom: 40px;
    }
    .content {
      .image {
        .layer {
          // left: -100%;
          width: 100%;
          top: 0;
          bottom: 0;
          background: rgba(255, 255, 255, 0.8);
          position: absolute;
          animation: scrollToRight 0.8s ease-in-out;
        }
        position: relative;
        float: left;
        width: 700px;
        height: 450px;
        margin-right: 100px;
        overflow: hidden;
        img {
          border-radius: 6px;
          object-fit: cover;
        }
      }
    }
    .indicator {
      color: #aaa;
      &-item {
        margin: 32px 0;
        h3 {
          &.active {
            color: black;
            font-size: 32px;
            font-weight: 400;
          }
          cursor: pointer;
        }
      }
    }
  }
  // 字节生活
  .byteLife {
    // width:1200px;
    // overflow: hidden;
    &-title {
      margin-bottom: 70px;
    }
    padding-left: 10%;
    .block-item {
      overflow: hidden;
      margin-bottom: 100px;
      &:nth-child(odd) {
        .block-item-column {
          &-text {
            margin-right: 9%;
          }
          float: left;
        }
      }
      &:nth-child(even) {
        .block-item-column {
          &-image {
            margin-right: 9%;
          }
          float: right;
        }
      }
      &-column {
        &-title {
          margin-bottom: 40px;
          padding-bottom: 40px;
          border-bottom: 1px solid #ccc;
        }
        &-text {
          width: 30%;
        }
        &-image {
          height: 400px;
          width: 59%;
          border-radius: 8px;
          overflow: hidden;
          img {
            height: 100%;
            object-fit: cover;
          }
        }
      }
    }
  }
  // 员工故事
  .staffStory {
    padding: 0 140px;
    .title {
      text-align: center;
      margin-bottom: 10px;
    }
    &-list {
      text-align: center;
      display: flex;
      justify-content: space-between;
      margin-top: 30px;
    }
    &-item {
      // height: 450px;
      overflow: hidden;
      padding: 30px;
      width: 30%;
      cursor: pointer;
      &:hover {
        box-shadow: 0 10px 30px 0 rgba(136, 150, 171, 0.15);
        .circular-button {
          color: #3370ff;
        }
      }
      .avatar {
        width: 70%;
        margin: auto;
      }
      .text {
        .title {
          color: #1f2329;
          font-weight: 400;
          font-size: 22px;
          margin: 10px 0;
          white-space: nowrap;
        }
        .desc {
          color: #1f2329;
          margin: 12px 0;
          font-size: 14px;
        }
        .remark {
          font-size: 14px;
          color: #606266;
          -webkit-box-orient: vertical;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      .circular-button {
        border-radius: 50%;
        width: 30px;
        height: 30px;
        border: 1px solid;
        line-height: 30px;
        margin: auto;
        margin-top: 20px;
        color: #ccc;
      }
    }
  }
  .menu{
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 50px;
    line-height: 50px;
    font-weight: bolder;
    text-align: center;
    a{text-decoration:none}
  }
  .el-menu {
    background-color: rgba(0, 0, 0, -0.6);
  }
  .el-menu.el-menu--horizontal {
    border-bottom: solid 0px #e6e6e6;
    float: right;
    margin-right: 10%;
    .el-menu-item{
      color: #f2f6fc;
    }
  }
  .el-menu--horizontal>.el-menu-item:not(.is-disabled):focus, .el-menu--horizontal>.el-menu-item:not(.is-disabled):hover, .el-menu--horizontal>.el-submenu .el-submenu__title:hover {
    background-color:rgba(0, 0, 0, -0.6);
  }
  .el-menu--horizontal>.el-menu-item.is-active {
    border-bottom: 5px solid #409EFF;
    color: #409EFF;
  }
</style>
