<template>
  <div>
    <!--职工信息-->
    <el-form class="addForm" :model="emp" :rules="rules" ref="addForm">
          <!--第一行-->
          <el-row>
            <el-col :span="6">
              <el-form-item label="姓名" prop="name">
                <el-input prefix-icon="el-icon-edit" v-model="emp.name"
                        placeholder="请输入职工姓名" size="mini"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="emp.gender" style="margin-top: 14px">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女" style="margin-left: -10px">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="出生日期" prop="birthday">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                    v-model="emp.birthday" type="date"
                    placeholder="出生日期" style="width: 150px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="政治面貌" prop="politicId">
                <el-select v-model="emp.politicId" placeholder="政治面貌" size="mini" style="width: 200px">
                  <el-option
                      v-for="item in politics"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第二行-->
          <el-row>
            <el-col :span="6">
              <el-form-item label="民族" prop="nationId">
                <el-select v-model="emp.nationId" placeholder="请选择民族" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="籍贯" prop="nativePlace">
                <el-input prefix-icon="el-icon-edit" v-model="emp.nativePlace" style="width: 150px"
                          placeholder="请输入籍贯" size="mini"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="电子邮箱" prop="email">
                <el-input prefix-icon="el-icon-message" v-model="emp.email" style="width: 150px"
                          placeholder="请输入电子邮箱" size="mini"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="联系地址" prop="address">
                <el-input prefix-icon="el-icon-edit"  v-model="emp.address" placeholder="请输入联系地址…" size="mini" style="width: 200px">

                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第三行-->
          <el-row>
            <el-col :span="6">
              <el-form-item label="职位" prop="posId">
                <el-input v-model="emp.position.name" disabled
                          size="mini" style="width: 150px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="职称" prop="jobLevelId">
                <el-input v-model="emp.jobLevel.name" disabled
                          size="mini" style="width: 150px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属部门" prop="departmentId">
                <!--部门选择按钮-->
                <el-input v-model="emp.department.name" disabled
                          size="mini" style="width: 150px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="电话号码" prop="phone">
                <el-input prefix-icon="el-icon-phone" v-model="emp.phone"
                          size="mini" style="width: 200px" placeholder="请输入手机号…"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第四行-->
          <el-row>
            <el-col :span="6">
              <el-form-item label="工号" prop="workID">
                <el-input prefix-icon="el-icon-edit" v-model="emp.workID"
                          placeholder="工号" size="mini" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="学历" prop="tiptopDegree">
                <el-select v-model="emp.tiptopDegree" placeholder="学历" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in degrees"
                      :label="item"
                      :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="毕业院校" prop="school">
                <el-input prefix-icon="el-icon-edit" v-model="emp.school"
                          placeholder="毕业院校" size="mini"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="专业名称" prop="specialty">
                <el-input prefix-icon="el-icon-edit" v-model="emp.specialty"
                          placeholder="请输入专业名称…" size="mini" style="width: 200px"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第五行-->
          <el-row>
            <el-col :span="6">
              <el-form-item label="入职日期" prop="beginDate">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.beginDate" type="date"
                                placeholder="入职日期" style="width: 130px" disabled>
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="转正日期" prop="conversionTime">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.conversionTime" type="date"
                                placeholder="转正日期" style="width: 130px" disabled>
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同起始日期" prop="beginContract">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.beginContract" type="date"
                                placeholder="合同起始日期" style="width: 130px" disabled>
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同终止日期" prop="endContract">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.endContract" type="date"
                                placeholder="合同终止日期" style="width: 180px" disabled>
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第六行-->
          <el-row>
            <el-col :span="8">
              <el-form-item label="身份证号码" prop="idCard">
                <el-input prefix-icon="el-icon-edit" v-model="emp.idCard"
                          placeholder="身份证号码" size="mini" style="width: 180px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="聘用形式" prop="engageForm">
                <el-radio-group v-model="emp.engageForm" style="margin-top: 14px" disabled>
                  <el-radio label="劳动合同">劳动合同</el-radio>
                  <el-radio label="劳务合同">劳务合同</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="婚姻状况" prop="wedlock">
                <el-radio-group v-model="emp.wedlock" style="margin-top: 14px">
                  <el-radio label="已婚">已婚</el-radio>
                  <el-radio label="未婚">未婚</el-radio>
                  <el-radio label="离异">离异</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

    <!--修改按钮-->
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="updateInfo">更新</el-button>
    </span>
  </div>
</template>

<script>
  export default {
    name: "EmpBasic",
    data() {
      return{
        //员工实体
        emp: {},
        nations: [],
        politics: [],
        degrees: ['本科','大专','硕士','博士','高中','初中','小学','其它'],

        //表单数据校验
        rules: {
          name: [{required: true,message: '请输入用户名!',trigger: 'blur'}],
          gender: [{required: true,message: '请选择性别!',trigger: 'blur'}],
          birthday: [{required: true,message: '请输入出生日期!',trigger: 'blur'}],
          idCard: [{required: true,message: '请输入身份证号!',trigger: 'blur'},
            {pattern:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$ |^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
              message: '身份证格式不正确！',trigger: 'blur'}],
          wedlock: [{required: true,message: '请选择婚姻状况!',trigger: 'blur'}],
          nationId: [{required: true,message: '请选择民族!',trigger: 'blur'}],
          nativePlace: [{required: true,message: '请输入籍贯!',trigger: 'blur'}],
          politicId: [{required: true,message: '请选择政治面貌!',trigger: 'blur'}],
          email: [{required: true,message: '请输入邮箱!',trigger: 'blur'},
            {type:'email',message: '邮箱格式不正确！',trigger: 'blur'}],
          phone: [{required: true,message: '请输入手机号!',trigger: 'blur'}],
          address: [{required: true,message: '请输入地址!',trigger: 'blur'}],
          departmentId: [{required: true,message: '请选择部门!',trigger: 'blur'}],
          jobLevelId: [{required: true,message: '请选择职称!',trigger: 'blur'}],
          posId: [{required: true,message: '请选择职位!',trigger: 'blur'}],
          engageForm: [{required: true,message: '请选择聘用形式!',trigger: 'blur'}],
          tiptopDegree: [{required: true,message: '请选择最高学历!',trigger: 'blur'}],
          specialty: [{required: true,message: '请输入专业!',trigger: 'blur'}],
          school: [{required: true,message: '请输入毕业院校!',trigger: 'blur'}],
          beginDate: [{required: true,message: '请选择入职日期!',trigger: 'blur'}],
          workState: [{required: true,message: '请输入工作状态!',trigger: 'blur'}],
          contractTerm: [{required: true,message: '请输入合同期限!',trigger: 'blur'}],
          conversionTime: [{required: true,message: '请输入转正日期!',trigger: 'blur'}],
          notWorkDate: [{required: true,message: '请输入离职日期!',trigger: 'blur'}],
          beginContract: [{required: true,message: '请输入合同起始日期!',trigger: 'blur'}],
          endContract: [{required: true,message: '请输入合同结束日期!',trigger: 'blur'}],
          workAge: [{required: true,message: '请输入工龄!',trigger: 'blur'}],
        },
      }
    },
    mounted() {
      this.emp = JSON.parse(window.sessionStorage.getItem("emp"));
      this.initData();
    },
    methods: {
      //初始化职工属性
      initData() {
        console.log("loading info……");
        if(!window.sessionStorage.getItem("nations")) {
          //从数据库获取民族
          this.getRequest("/employee/basic/nations").then(res=>{
            if(res) {
              this.nations = res;
              window.sessionStorage.setItem("nations",JSON.stringify(res));
            }
          })
        } else{
          //从session获取
          this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
        }

        //获取政治面貌
        if(!window.sessionStorage.getItem("politics")) {
          this.getRequest("/employee/basic/politics").then(res=>{
            if(res) {
              this.politics = res;
              window.sessionStorage.setItem("politics",JSON.stringify(res));
            }
          })
        }else{
          //从session获取
          this.politics = JSON.parse(window.sessionStorage.getItem("politics"));
        }

      },
      //更新
      updateInfo() {
        this.$refs["addForm"].validate(valid => {
          if (valid) {
            this.putRequest("/empInfo/info", this.emp).then(resp => {
              if (resp) {
                this.initUser();
              }
            })
          }
        });
      },
      //重新获取
      initUser() {
        this.getRequest('/empInfo/' + this.emp.id).then(resp => {
          if (resp) {
            this.emp = resp;
            window.sessionStorage.setItem("emp", JSON.stringify(resp));
            this.$store.commit('INIT_CURRENT_EMP', resp);
          }
        })
      },
    }
  }
</script>

<style scoped>
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
  .pageInfo{
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
  }
  .addForm .el-input{
    width: 150px;
  }
  .departmentClass{
    width: 150px;
    height: 26px;
    padding-left: 8px;
    display: inline-flex;
    font-size: 13px;
    border: 1px solid #dedede;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 5px;
    align-items: center;
    box-sizing: border-box;
  }
  .uploadInfo{
    display: inline-flex;
    margin-right: 10px;
  }
  .advancedSearch {
    border: 1px solid #409eff;
    border-radius: 5px;
    box-sizing: border-box;
    padding: 10px;
    margin: 10px;
  }
</style>
