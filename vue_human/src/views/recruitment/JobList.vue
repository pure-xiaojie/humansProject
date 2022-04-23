<template>
  <div>
    <div class="menu">
      <el-menu default-active="2" class="el-menu-demo"
               mode="horizontal" @select="handleSelect">
        <el-menu-item index="1">
          <a href="/#/website">首页</a>
        </el-menu-item>
        <el-menu-item index="2">职位列表</el-menu-item>
        <el-menu-item index="3">
          <a href="/#/login">登录</a>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="banner">和优秀的人，做有挑战的事</div>

    <div class="main">
      <!-- 侧边栏过滤选择 -->
      <div class="aside-filter">
        <div class="header">
          <span>操作</span>
          <span class="clear" @click="clearFilter">清空</span>
        </div>
        <el-row style="margin-bottom: 10px">
          <div class="search">
            <el-input placeholder="请输入职位名称搜索…" style="margin-right: 10px" size="small"
                      prefix-icon="el-icon-search" v-model="searchWords"
                      @keydown.enter.native="initJobList" clearable
                      @clear="initJobList"></el-input>
            <el-button type="success" size="small" icon="el-icon-search"
                       @click="initJobList" style="margin-right: 5px">搜索</el-button>
          </div>
        </el-row>
        <!--行业筛选-->
        <el-row>
          <div>
            <el-select v-model="job.sort" placeholder="请选择所属行业"
                       size="small" @change="initJobList">
              <el-option
                  v-for="(item,index) in industry"
                  :key="item"
                  :label="item"
                  :value="index">
              </el-option>
            </el-select>
            <span style="margin-left: 15px">行业</span>
          </div>
        </el-row>

        <!--地区筛选-->
        <el-row>
          <div class="divwrap">
            <v-distpicker hide-area :province="province" :city="city"
                          @province="onChangeProvince"
                          @city="onChangeCity">
            </v-distpicker>
          </div>
        </el-row>
      </div>

      <!-- 主体内容 -->
      <div class="content">
        <h2 class="content-title">开启新的职位 ({{total}})</h2>
        <ul class="content-list" v-for="(job,index) in jobList">
          <li class="content-item">
            <router-link :to="{path:'jobDetail',query:{id:job.id}}">
              <h3 class="title">{{job.p_name}}</h3>

              <div class="subTitle">
                <span class="city">{{job.p_location}}</span>&nbsp;|
                <span class="job_category">{{job.p_key}}</span>&nbsp;|
                <span class="recruitment_channel">{{job.p_years}}</span>
              </div>
              <p class="desc">{{job.p_jobDetail | ellipsis}}</p>
            </router-link>
          </li>
        </ul>
        <!--分页-->
        <div class="pageInfo">
          <el-pagination background
                         layout="sizes, prev, pager, next, jumper, ->, total, slot"
                         :total="total" :page-sizes="[5, 10, 15, 20, 30]"
                         @size-change="sizeChange"
                         @current-change="pageChange">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import VDistpicker from "v-distpicker";
  export default {
    components: {
      "v-distpicker":VDistpicker
    },
    name: "JobList",
    filters: {
      ellipsis (value) {
        if (!value) return '';
        if (value.length > 100) {
          return value.slice(0,100) + '...'
        }
        return value
      }
    },
    data() {
      return {
        jobList: [],
        total: 0,
        page: 1,
        size: 10,
        searchWords: '',
        //职位实体
        job: {
          id: null,
          hr_id: null,
          p_name: "",
          p_key: "",
          p_salary:"",
          p_location: "",
          p_languages: "",
          p_years: "",
          p_tags: "",
          p_jobDetail: "",
          p_jobContent: "",
          p_createDate: "",
          status: 0,
          sort: null,
        },
        //行业
        industry: ["不限", "研发", "测试", "数据", "算法", "前端", "产品", "运营"],
        //地区
        province: "省",
        city: "市",
      };
    },
    mounted() {
      this.initJobList();
    },
    methods: {
      //初始化
      init() {
        this.job = {
          id: null,
          hr_id: null,
          p_name: "",
          p_key: "",
          p_salary:"",
          p_location: "",
          p_languages: "",
          p_years: "",
          p_tags: "",
          p_jobDetail: "",
          p_jobContent: "",
          p_createDate: "",
          status: 0,
          sort: null,
        };
        this.searchWords = "";
        this.$route.query.keyword = null;
        this.initJobList();
      },
      //清空
      clearFilter() {
        this.init();
      },
      //获取职位列表
      initJobList() {
        let url = '/recurit/position/?page=' + this.page +"&size=" + this.size;

        if(this.$route.query.keyword != null) {
          this.searchWords = this.$route.query.keyword;
        }

        //职位名搜索
        if(this.searchWords != "") {
          url += "&p_name=" + this.searchWords;
        }

        //行业搜索
        if(this.job.sort != null) {
          url += "&sort=" + this.job.sort;
          console.log(this.job.sort);
        }

        //地区搜索
        if(this.job.p_location != "市") {
          var split = this.job.p_location.split("市");
          url += "&p_location=" + split[0];
          console.log(this.job.p_location);
        }

        this.getRequest(url).then(res=>{
          console.log(res);
          this.jobList = res.data;
          this.total = res.total;
        })
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initJobList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initJobList();
      },
      //地区
      onChangeProvince(data) {
        this.province = data.value;
      },
      onChangeCity(data) {
        this.city = data.value;
        this.job.p_location = this.city;
        this.initJobList();
      }
    }
  };
</script>
<style lang="less" scoped>
  .banner {
    height: 400px;
    line-height: 400px;
    color: #fff;
    width: 100%;
    min-width: 1300px;
    background-image: url("//sf1-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/mainland/joblistbanner2x.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    text-align: center;
    font-size: 32px;
  }
  .search-wrapper {
    width: 500px;
    position: center;
    padding: 0 200px;
    transform: translateY(-50%);
    z-index: 100;

  }
  .main {
    width: 1300px;
    margin: 50px auto;
    padding: 0 100px 0 200px;
    .aside-filter {
      float: left;
      width: 300px;
      padding-right: 50px;
      .header {
        font-size: 14px;
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;
        border-bottom: 1px solid #ebeef5;
        padding-bottom: 10px;
        width: 100%;
      }
      .clear {
        margin-right: 10px;
      }
    }
    .content {
      min-height: 500px;
      padding-left: 20px;
      border-left: 1px solid #ebeef5;
      margin-left: 300px;
      &-title {
        margin-bottom: 30px;
        margin-left: 20px;
      }
      &-item {
        margin-bottom: 10px;
        padding: 10px 20px;
        cursor: pointer;
        list-style: none;
        a {
          text-decoration: none;
        }
        .title {
          color: #000000;
          text-decoration: none;
          margin: 12px 0;
        }
        .subTitle {
          color: #606266;
        }
        .desc {
          white-space: pre-line;
          font-size: 14px;
          color: #909399;
          text-overflow: clip;
        }
        &:hover {
          border-radius: 3px;
          background: #fff;
          box-shadow: 0 10px 30px 0 rgba(136, 150, 171, 0.15);
        }
      }
      .pagination-wrapper {
        margin-top: 50px;
        text-align: right;
      }
    }
    .a{
      color: -webkit-link;
      cursor: pointer;
      text-decoration: underline;
    }
  }
  .pageInfo{
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
  }
  .search{
    display: flex;
    justify-content: space-between;
  }
  .divwrap{
    margin-top: 15px;

    .distpicker-address-wrapper{
      select {
        height: 30px !important;
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
