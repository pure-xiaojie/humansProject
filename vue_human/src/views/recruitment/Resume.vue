<template>
  <div>
    <div>
      <!--输入框与按钮-->
      <div  class="topInfo">
        <!--left button-->
        <div>
          <el-input placeholder="请输入姓名搜索…" class="topLeft" size="small"
                    prefix-icon="el-icon-search" v-model="searchWords"
                    @keydown.enter.native="initResumeList" clearable
                    @clear="initResumeList"></el-input>
          <el-button type="success" size="small" icon="el-icon-search"
                     @click="initResumeList">搜索</el-button>
        </div>
      </div>

      <!--表格数据展示-->
      <div class="containerInfo">
        <el-table :data="resumeList" stripe border style="width: 100%"
                  v-loading="loading" tooltip-effect="light"
                  element-loading-text="正在加载..."
                  element-loading-spinner="el-icon-loading"
                  element-loading-background="rgba(0, 0, 0, 0.8)">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="name" label="姓名" width="100"></el-table-column>
          <el-table-column prop="gender" label="性别" width="60"></el-table-column>
          <el-table-column prop="phone" label="手机号" width="120"></el-table-column>
          <el-table-column prop="email" label="邮箱" width="160"></el-table-column>
          <el-table-column prop="school" label="就读院校" width="110"></el-table-column>
          <el-table-column prop="professional" label="专业" width="110"></el-table-column>
          <el-table-column prop="address" label="地址" width="110"></el-table-column>
          <el-table-column prop="graduateDate" label="毕业时间" width="110"></el-table-column>
          <el-table-column prop="createDate" label="投递时间" width="110"></el-table-column>
          <el-table-column label="投递岗位" width="120">
            <template slot-scope="scope" >
              <el-tooltip placement="left" v-if="scope.row.job " effect="light">
                <div slot="content">
                  <el-form label-width="80px" style="width: 300px">
                    <el-form-item label="职位名称">
                      <el-input v-model="scope.row.job.p_name" size="small" placeholder="请输入职位名称"></el-input>
                    </el-form-item>
                    <el-form-item label="职位薪资">
                      <el-input v-model="scope.row.job.p_salary" size="small" placeholder="7-8k"></el-input>
                    </el-form-item>
                    <el-form-item label="经验要求">
                      <el-input v-model="scope.row.job.p_years" size="small" placeholder="社招/应届/实习"></el-input>
                    </el-form-item>

                    <el-form-item label="职位描述">
                      <el-input type="textarea" v-model="scope.row.job.p_jobDetail">  </el-input>
                    </el-form-item>
                    <el-form-item label="岗位职责">
                      <el-input type="textarea" v-model="scope.row.job.p_jobContent"></el-input>
                    </el-form-item>
                  </el-form>
                </div>
                <el-tag>{{scope.row.job.p_name}}</el-tag>
              </el-tooltip>
              <el-tag type="info" v-else>未知</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="updateDate" label="更新时间" width="120"></el-table-column>
          <el-table-column label="状态" width="120">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status == 1">{{condition[scope.row.status]}}</el-tag>
              <el-tag type="success" v-else>{{condition[scope.row.status]}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" fixed="right" width="120">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="padding: 3px"
                         @click="openPreview(scope.row.resume_url)">
                简历预览
              </el-button>
              <el-button size="mini" type="success" style="padding: 3px"
                         @click="editResume(scope.row)">
                编辑
              </el-button>
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
    <el-dialog title="求职状态修改" :visible.sync="dialogVisible" width="20%">
      <!--添加数据区-->
      <div>
        <el-form ref="resume" :model="resume" label-width="80px">
          <el-row>
            <el-form-item label="姓名">
              <el-input v-model="resume.name" size="small" placeholder="请输入职位名称" :disabled="true"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="求职状态">
              <el-select v-model="resume.status" placeholder="请选择状态" size="small">
                <el-option
                    v-for="(item,index) in condition"
                    :key="item"
                    :label="item"
                    :value="index">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-form-item>
            <el-button type="primary" @click="onSubmit()">修改</el-button>
            <el-button @click="cancelAddView">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Resume",
    data() {
      return {
        loading: false,
        resumeList: [],
        total: 0,
        page: 1,
        size: 10,
        searchWords: '',
        dialogVisible: false,
        //职位实体
        resume: {
          id: null,
          name: '',
          gender: '',
          phone: '',
          email: '',
          resume_url: '',
          address: '',
          school: '',
          graduateDate: null,
          professional: '',
          status: 0,
          positionId: null,
          createDate: null,
          updateDate: null
        },
        //状态
        condition: ["投递","简历通过", "人才库", "笔试", "面试", "offer"],
      }
    },
    mounted() {
      this.initResumeList();
    },
    methods: {
      //初始化
      initData() {
        this.resume = {
          id: null,
          name: '',
          gender: '',
          phone: '',
          email: '',
          resume_url: '',
          address: '',
          school: '',
          graduateDate: null,
          professional: '',
          status: 0,
          positionId: null,
          createDate: null,
          updateDate: null
        };
      },
      //获取职位列表
      initResumeList() {
        this.loading = true;

        let url = '/recurit/apply/?page=' + this.page +"&size=" + this.size;

        if(this.searchWords != "") {
          //职位名搜索
          url += "&name=" + this.searchWords;
        }
        this.getRequest(url).then(res=>{
          console.log(res);
          this.resumeList = res.data;
          this.total = res.total;
          this.loading = false;
        })
      },
      //修改
      editResume(temp) {
        this.resume = temp;
        this.dialogVisible = true;
      },
      //添加
      showAddView() {
        this.dialogVisible = true;
      },
      cancelAddView() {
        this.initData();
        this.dialogVisible = false;
      },
      //修改提交
      onSubmit() {
        this.putRequest("/recurit/apply/",this.resume).then(res=>{
          if(res) {
            this.initResumeList();
          }
        });
        this.initData();
        this.dialogVisible = false;
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initResumeList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initResumeList();
      },
      //简历预览
      openPreview(url) {
        window.open(url);
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
