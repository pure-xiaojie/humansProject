<template>
  <div>
    <!--职位详情-->
    <div class="job-detail">
      <h1 class="job-detail-title">{{ jobDetail.p_name }}</h1>
      <div class="job-detail-subTitle" v-if="jobDetail.id">
      <span class="city_info">{{ jobDetail.p_location }}</span
      >&nbsp;|
        <span class="job_category">{{ jobDetail.p_key }}</span
        >&nbsp;|
        <span class="recruit_type">{{ jobDetail.p_years }}</span>
      </div>
      <div class="job-detail-description job-detail-block">
        <h2>职位描述</h2>
        <pre class="textContent">{{ jobDetail.p_jobDetail }}</pre>
      </div>
      <div class="job-detail-requirement job-detail-block">
        <h2>职位要求</h2>
        <pre class="textContent">{{ jobDetail.p_jobContent }}</pre>
      </div>
      <div class="job-detail-button job-detail-block">
        <el-button type="primary" round size="medium" @click="delivery">投递</el-button>
      </div>
    </div>

    <!--简历投递-->
    <el-dialog title="简历投递" :visible.sync="dialogVisible" width="40%">
      <!--添加数据区-->
      <div>
        <el-form ref="resume" :model="resume" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="姓名" prop="name">
                <el-input v-model="resume.name" size="small" placeholder="请输入姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别" prop="gender">
                <el-select v-model="resume.gender" placeholder="性别" size="small">
                  <el-option value="男">男</el-option>
                  <el-option value="女">女</el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="手机" prop="phone">
                <el-input v-model="resume.phone" size="small" placeholder="手机号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="resume.email" size="small" placeholder="email"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="就读院校" prop="school">
                <el-input v-model="resume.school" size="small" placeholder="就读院校"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="毕业时间" prop="graduateDate">
                <el-date-picker size="small"
                    v-model="resume.graduateDate"
                    type="date"
                    placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="就读专业" prop="professional">
                <el-input v-model="resume.professional" size="small" placeholder="专业"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="家庭地址" prop="address">
                <el-input v-model="resume.address" size="small" placeholder="家庭地址"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="个人简历">
            <el-upload action="/recurit/apply/import"
                       :show-file-list="false" :on-success="onSuccess"
                       :on-error="onError" :before-upload="beforeUpload"
                       :disabled="buttonVisible">
              <el-button type="success" size="mini" :icon="icon" :disabled="buttonVisible">
                添加简历
              </el-button>
            </el-upload>
          </el-form-item>
          <el-link v-if="resume_upload != ''" :href="resume_upload" type="success" target="_blank">您的简历已成功添加，请完成投递！</el-link>
        </el-form>

        <div style="display: flex;justify-content: flex-end">
          <el-button type="primary" @click="onSubmit" round>立即投递</el-button>
          <el-button type="info" @click="cancelDelivery" round>取消投递</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    name: "jobDetail",
    data() {
      return {
        //职位信息
        jobDetail: {},
        //弹框
        dialogVisible: false,
        //简历封装
        resume: {
          id: null,
          name: '李杰贵',
          gender: '男',
          phone: '15874423919',
          email: 'lijiegui12@qq.com',
          resume_url: '',
          address: '湖南',
          school: '吉首大学',
          graduateDate: '2021-04-17',
          professional: '软件工程',
          status: 0,
          positionId: null,
          createDate: null,
          updateDate: null
        },
        //添加简历
        icon: 'el-icon-upload2',
        buttonVisible: false,
        resume_upload: '',
        //校验规则
        rules: {
          name: [{required: true, message: '请输入用户名!', trigger: 'blur'}],
          gender: [{required: true,message: '请选择性别!',trigger: 'blur'}],
          phone: [{required: true,message: '请输入手机号!',trigger: 'blur'}],
          email: [{required: true,message: '请输入邮箱!',trigger: 'blur'},
            {type:'email',message: '邮箱格式不正确！',trigger: 'blur'}],
          school: [{required: true,message: '请输入就读院校!',trigger: 'blur'}],
          graduateDate: [{required: true,message: '请选择毕业日期!',trigger: 'blur'}],
          professional: [{required: true,message: '请输入专业!',trigger: 'blur'}],
          address: [{required: true,message: '请输入地址!',trigger: 'blur'}],
        }
      };
    },
    //已经将数据装载到页面上去了，DOM已经生成
    mounted() {
      this.initJobDetail();
    },
    methods: {
      init() {
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
        }
      },
      initJobDetail() {
        this.getRequest("/recurit/position/" + this.$route.query.id).then(res=>{
          this.jobDetail = res;
        });
        //console.log(this.jobDetail);
      },
      delivery() {
        this.dialogVisible = true;
      },
      //立即投递
      onSubmit() {
        this.$refs['resume'].validate(valid=>{
          if(valid) {
            this.resume.positionId = this.$route.query.id;
            this.resume.resume_url = this.resume_upload;
            this.postRequest("/recurit/apply/",this.resume).then(res=>{
              if(res) {
                console.log(res);
              }
            });
            this.dialogVisible = false;
          }
        });

      },
      //取消投递
      cancelDelivery() {
        this.dialogVisible = false;
        this.init();
      },
      //上传简历
      beforeUpload() {
        this.icon = 'el-icon-loading';
        this.buttonVisible = true;
      },
      onSuccess(response, file, fileList) {
        this.icon = 'el-icon-upload2';
        this.buttonVisible = false;
        this.resume_upload = response.obj;
        //console.log(response);
      },
      onError(err, file, fileList) {
        this.icon = 'el-icon-upload2';
        this.buttonVisible = false;
      }
    },
  };
</script>
<style lang="less" scoped>
  .job-detail {
    width: 800px;
    margin: auto;
    margin-top: 50px;
    line-height: 2em;
    &-subTitle {
      font-size: 14px;
      color: #1f2329;
      margin: 20px 0;
    }
    &-description {
      .textContent {
        white-space: pre-line;
      }
    }
    &-block {
      h2 {
        margin-bottom: 20px;
      }
      margin: 40px 0;
      .textContent {
        font-size: 14px;
        color: #1f2329;
      }
    }
  }
</style>
