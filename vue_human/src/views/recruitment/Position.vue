<template>
  <div>
    <div>
      <!--输入框与按钮-->
      <div  class="topInfo">
        <!--left button-->
        <div>
          <el-input placeholder="请输入职位名称搜索…" class="topLeft" size="small"
                    prefix-icon="el-icon-search" v-model="searchWords"
                    @keydown.enter.native="initJobList" clearable
                    @clear="initJobList"></el-input>
          <el-button type="success" size="small" icon="el-icon-search"
                     @click="initJobList">搜索</el-button>
        </div>
        <!--right button-->
        <div>
          <el-button type="success" size="small" icon="el-icon-plus"
                     @click="showAddView">发布职位</el-button>
        </div>
      </div>

      <!--表格数据展示-->
      <div class="containerInfo">
        <el-table :data="jobList" stripe border style="width: 100%"
                  v-loading="loading" tooltip-effect="light"
                  element-loading-text="正在加载..."
                  element-loading-spinner="el-icon-loading"
                  element-loading-background="rgba(0, 0, 0, 0.8)">
          <el-table-column type="selection" width="55"></el-table-column>
         <!-- <el-table-column prop="id" label="编号" width="50" fixed></el-table-column>-->
          <el-table-column prop="p_name" label="职位名称" width="140" fixed :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="p_key" label="关键字" width="100"></el-table-column>
          <el-table-column prop="p_salary" label="薪资" width="120"></el-table-column>
          <el-table-column prop="p_location" label="地点" width="80"></el-table-column>
          <el-table-column prop="p_jobDetail" label="职位描述" width="120" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="p_jobContent" label="职位要求" width="120" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="p_createDate" label="创建时间" width="120"></el-table-column>
          <el-table-column prop="p_languages" label="语言要求" width="80"></el-table-column>
          <el-table-column prop="p_years" label="经验要求" width="120"></el-table-column>
          <el-table-column prop="p_tags" label="标签" width="120" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column label="状态" width="100">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status == 0">{{condition[scope.row.status]}}</el-tag>
              <el-tag type="success" v-else>{{condition[scope.row.status]}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="行业" width="100">
            <template slot-scope="scope">
              <el-tag>{{industry[scope.row.sort]}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="padding: 3px" @click="editJob(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger"  style="padding: 3px" @click="delJob(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

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

    <!--添加对话框-->
    <el-dialog :title="title" :visible.sync="dialogVisible" width="50%">
      <!--添加数据区-->
      <div>
        <el-form ref="job" :model="job" label-width="80px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="职位名称">
                <el-input v-model="job.p_name" size="small" placeholder="请输入职位名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属行业">
                <el-select v-model="job.sort" placeholder="请选择所属行业" size="small">
                  <el-option
                      v-for="(item,index) in industry"
                      :key="item"
                      :label="item"
                      :value="index">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="语言要求">
                <el-input v-model="job.p_languages" size="small" placeholder="语言要求"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="职位薪资">
                <el-input v-model="job.p_salary" size="small" placeholder="7-8k"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="职位地点">
                <el-input v-model="job.p_location" size="small" placeholder="工作地点"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="职位标签">
                <el-input v-model="job.p_tags" size="small" placeholder="六险一金"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="经验要求">
                <el-input v-model="job.p_years" size="small" placeholder="社招/应届/实习"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="关键字">
                <el-input v-model="job.p_key" size="small" placeholder="岗位关键字"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        <el-form-item label="职位描述">
          <el-input type="textarea" v-model="job.p_jobDetail">  </el-input>
        </el-form-item>
          <el-form-item label="岗位职责">
          <el-input type="textarea" v-model="job.p_jobContent"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit(buttonTip)">{{buttonTip}}</el-button>
          <el-button @click="cancelAddView">取消</el-button>
        </el-form-item>
      </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Position",
    data() {
      return {
        loading: false,
        jobList: [],
        total: 0,
        page: 1,
        size: 10,
        searchWords: '',
        dialogVisible: false,
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
        industry: ["其它", "研发", "测试", "数据", "算法", "前端", "产品", "运营"],
        //状态
        condition: ["结束", "在招"],
        //当前登录用户
        user: JSON.parse(window.sessionStorage.getItem("user")),
        //弹框
        title: "",
        buttonTip: ""
      }
    },
    mounted() {
      this.initJobList();
    },
    methods: {
      //初始化
      initData() {
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
          status: 1,
          sort: null,
        };
        this.title = "";
        this.buttonTip = "";
      },
      //获取职位列表
      initJobList() {
        this.loading = true;

        let url = '/recurit/position/?page=' + this.page +"&size=" + this.size;

        if(this.searchWords != "") {
          //职位名搜索
          url += "&p_name=" + this.searchWords;
        }
        this.getRequest(url).then(res=>{
          //console.log(res);
          this.jobList = res.data;
          this.total = res.total;
          this.loading = false;
        })
      },
      //修改
      editJob(temp) {
        this.job = temp;
        this.title = "修改职位";
        this.buttonTip = "立即修改";
        this.dialogVisible = true;
      },
      //添加
      showAddView() {
        this.title = "发布职位";
        this.buttonTip = "立即发布";
        this.dialogVisible = true;
      },
      cancelAddView() {
        this.initData();
        this.dialogVisible = false;
      },
      //添加、修改提交
      onSubmit(flag) {
        if(flag == "立即发布") {
          this.job.hr_id = this.user.id;
          //console.log(this.job);
          this.postRequest("/recurit/position/",this.job).then(res=>{
            if(res) {
              this.initJobList();
            }
          });
        } else {
          this.putRequest("/recurit/position/",this.job).then(res=>{
            if(res) {
              this.initJobList();
            }
          });
        }
        this.initData();
        this.dialogVisible = false;
      },
      //删除职位
      delJob(temp) {
        this.$confirm('确定永久删除【'+temp.p_name+'】职位！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/recurit/position/"+temp.id).then(res=> {
            if(res) {
              this.initJobList();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
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
    }
  }
</script>

<style lang="css">
  .topInfo{
     display: flex;
     justify-content: space-between;
     margin-top: 15px;
   }
  .topLeft{
    width: 300px;
    margin-right: 10px;
  }
  .containerInfo{
    margin-top: 15px;
  }
  .el-tooltip__popper{
    max-width:20%
  }
  .pageInfo{
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
  }
</style>
