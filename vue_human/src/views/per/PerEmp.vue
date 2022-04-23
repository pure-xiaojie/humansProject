<template>
  <div>
    <!--输入框与按钮-->
    <div>
      <!--top button-->
      <div  class="topInfo">
        <!--left button-->
        <div>
          <el-input placeholder="请输入职工姓名搜索…" class="topLeft" size="small"
                    prefix-icon="el-icon-search" v-model="searchWords"
                    @keydown.enter.native="initEmpList" clearable
                    @clear="initEmpList" :disabled="advancedVisible"></el-input>
          <el-button type="success" size="small" icon="el-icon-search"
                     @click="initEmpList" :disabled="advancedVisible">搜索</el-button>
          <el-button type="success" size="small"
                     :icon="advancedVisible?'el-icon-caret-top':'el-icon-caret-bottom'"
                     @click="advancedVisible = !advancedVisible">高级搜索</el-button>
        </div>
        <!--right button-->
        <div>
          <el-upload action="/employee/basic/import" class="uploadInfo"
                     :show-file-list="false" :on-success="onSuccess"
                     :on-error="onError" :before-upload="beforeUpload"
                     :disabled="buttonVisible">
            <el-button type="success" size="small" :icon="icon" :disabled="buttonVisible">
              {{text}}
            </el-button>
          </el-upload>
          <el-button type="success" size="small" icon="el-icon-download" @click="exportExcel">导出数据</el-button>
          <el-button type="success" size="small" icon="el-icon-plus"
                     @click="showAddView">添加职工</el-button>
        </div>
      </div>
      <!--advanced search-->
      <div class="advancedSearch" v-show="advancedVisible">
        <el-row>
          <el-col :span="4">
            职位：
            <el-select v-model="searchParams.posId" placeholder="职位" size="mini" style="width: 130px">
              <el-option
                  v-for="item in positions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            职称：
            <el-select v-model="searchParams.jobLevelId" placeholder="职称" size="mini" style="width: 130px">
              <el-option
                  v-for="item in jobLevels"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            民族：
            <el-select v-model="searchParams.nationId" placeholder="民族" size="mini" style="width: 130px">
              <el-option
                  v-for="item in nations"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            政治面貌：
            <el-select v-model="searchParams.politicId" placeholder="政治面貌" size="mini" style="width: 130px">
              <el-option
                  v-for="item in politics"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>

        </el-row>
        <el-row  style="margin-top: 15px">
          <el-col :span="4">
            部门：
            <el-popover placement="right"
                        title="请选择部门" width="220"
                        trigger="manual" v-model="sDepVisible">
              <!--数据展示在tree中-->
              <el-tree :data="depList" :props="defaultProps"
                       default-expand-all @node-click="SearchHandleNodeClick"></el-tree>

              <!--部门选择按钮-->
              <div slot="reference" style="width: 130px" class="departmentClass" @click="showDepView(1)">
                {{inputDepName}}
              </div>
            </el-popover>
          </el-col>
          <el-col :span="6"  style="margin-top: 8px">
            聘用形式：
            <el-radio-group v-model="searchParams.engageForm">
              <el-radio label="劳动合同">劳动合同</el-radio>
              <el-radio label="劳务合同">劳务合同</el-radio>
            </el-radio-group>
          </el-col>
          <el-col :span="10" style="margin-top: 3px">
            入职日期：
            <el-date-picker size="mini" value-format="yyyy-MM-dd"
                            v-model="searchParams.beginDateScope"
                            type="datetimerange"
                            :picker-options="pickerOptions"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            align="right">
            </el-date-picker>
          </el-col>
          <el-col :span="4">
            <el-button size="mini" type="primary" @click="advancedVisible = false">取消</el-button>
            <el-button size="mini" type="primary" @click="initEmpList('advanceSearch')">搜索</el-button>
          </el-col>
        </el-row>
      </div>

    </div>

    <!--表格数据展示-->
    <div class="containerInfo">
      <el-table :data="empList" stripe border style="width: 100%"
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100" fixed></el-table-column>
        <el-table-column label="头像">
          <template width="90" slot-scope="scope">
            <img style="width:30px;height:30px;border:none;" :src="avatar(scope.row.imgUrl)">
          </template>
        </el-table-column>
        <el-table-column prop="workID" label="工号" width="120"></el-table-column>
        <el-table-column prop="gender" label="性别" width="80"></el-table-column>
        <el-table-column prop="birthday" label="出生日期" width="130"></el-table-column>
        <el-table-column prop="idCard" label="身份证号码" width="180"></el-table-column>
        <el-table-column prop="wedlock" label="婚姻状况" width="80"></el-table-column>
        <el-table-column prop="nation.name" label="民族" width="80"></el-table-column>
        <el-table-column prop="nativePlace" label="籍贯" width="100"></el-table-column>
        <el-table-column prop="politicsstatus.name" label="政治面貌" width="100"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
        <el-table-column prop="phone" label="手机号" width="150"></el-table-column>
        <el-table-column prop="address" label="联系地址" width="200"></el-table-column>
        <el-table-column prop="department.name" label="所属部门" width="100"></el-table-column>
        <el-table-column prop="position.name" label="职位" width="120"></el-table-column>
        <el-table-column prop="jobLevel.name" label="职称" width="120"></el-table-column>
        <el-table-column prop="engageForm" label="聘用形式" width="100"></el-table-column>
        <el-table-column prop="beginDate" label="入职日期" width="120"></el-table-column>
        <el-table-column prop="conversionTime" label="转正日期" width="120"></el-table-column>
        <el-table-column prop="beginContract" label="合同起始日期" width="150"></el-table-column>
        <el-table-column prop="endContract" label="合同终止日期" width="150"></el-table-column>
        <el-table-column prop="contractTerm" label="合同期限" width="100">
          <template slot-scope="scope">
            {{scope.row.contractTerm}}年
          </template>
        </el-table-column>
        <el-table-column prop="tiptopDegree" label="最高学历" width="100"></el-table-column>
        <el-table-column prop="school" label="毕业院校" width="150"></el-table-column>
        <el-table-column prop="specialty" label="专业" width="150"></el-table-column>
        <el-table-column prop="workState" label="在职状态" width="80"></el-table-column>

        <el-table-column label="操作" fixed="right" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" style="padding: 3px" @click="editEmp(scope.row)">编辑</el-button>
            <el-button size="mini" type="primary" style="padding: 3px">查看更多</el-button>
            <el-button size="mini" type="danger"  style="padding: 3px" @click="delEmp(scope.row)">删除</el-button>
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

    <!--添加对话框-->
    <el-dialog :title="title" :visible.sync="dialogVisible"
               width="80%">
      <!--添加数据区-->
      <div>
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
                <el-select v-model="emp.posId" placeholder="职位" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="职称" prop="jobLevelId">
                <el-select v-model="emp.jobLevelId" placeholder="职称" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in jobLevels"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属部门" prop="departmentId">
                <!--手动弹框-->
                <el-popover placement="right"
                            title="请选择部门" width="200"
                            trigger="manual" v-model="depVisible">
                  <!--数据展示在tree中-->
                  <el-tree :data="depList" :props="defaultProps"
                           default-expand-all @node-click="handleNodeClick"></el-tree>

                  <!--部门选择按钮-->
                  <div slot="reference" class="departmentClass" @click="showDepView(2)">
                    {{inputDepName}}
                  </div>
                </el-popover>
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
                                placeholder="入职日期" style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="转正日期" prop="conversionTime">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.conversionTime" type="date"
                                placeholder="转正日期" style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同起始日期" prop="beginContract">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.beginContract" type="date"
                                placeholder="合同起始日期" style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同终止日期" prop="endContract">
                <el-date-picker size="mini" value-format="yyyy-MM-dd"
                                v-model="emp.endContract" type="date"
                                placeholder="合同终止日期" style="width: 180px">
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
                <el-radio-group v-model="emp.engageForm" style="margin-top: 14px">
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
      </div>
      <!--添加与取消按钮-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addEmployee">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: "EmpBasic",
    data() {
      return{
        loading: false,
        empList: [],
        total: 0,
        page: 1,
        size: 10,
        searchWords: '',
        dialogVisible: false,
        //员工实体
        emp: {
          name:"张帅",
          gender:"男",
          birthday:"1999-04-12",
          idCard:"431127199904126731",
          wedlock:"未婚",
          nationId:1,
          nativePlace:'湖南',
          politicId:13,
          email:"1952175699@qq.com",
          phone:"15874423919",
          address:"湖南永州",
          departmentId:4,
          jobLevelId:9,
          posId:36,
          engageForm:"劳务合同",
          tiptopDegree:"本科",
          specialty:"软件工程",
          school:"吉首大学",
          beginDate:"2017-09-01",
          workState:"在职",
          workID:"00001942",
          contractTerm:2,  //合同期限
          conversionTime:"2021-01-01",
          notWorkDate:null,
          beginContract:"2021-01-01",
          endContract:"2021-01-01",
          workAge:null
        },
        nations: [],
        jobLevels: [],
        politics: [],
        positions: [],
        degrees: ['本科','大专','硕士','博士','高中','初中','小学','其它'],
        depVisible: false,
        sDepVisible: false,
        //部门tree
        depList: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        //部门名称
        inputDepName: '所属部门',
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
        //编辑员工
        title: '',
        //文件上传
        icon: 'el-icon-upload2',
        text: '导入数据',
        buttonVisible: false,
        //高级搜索
        searchParams: {
          posId: null,
          jobLevelId: null,
          nationId: null,
          politicId: null,
          departmentId: null,
          engageForm: null,
          beginDateScope: null
        },
        advancedVisible: false,
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        }
      }
    },
    mounted() {
      this.initEmpList();
      this.initData();
      //获取职位列表
      this.initPositions();
    },
    methods: {
      //初始化职工实体为空
      initEmpty(){
        this.emp = {
          name:"张帅",
          gender:"男",
          birthday:"1999-04-12",
          idCard:"431127199904126731",
          wedlock:"未婚",
          nationId:1,
          nativePlace:'湖南',
          politicId:13,
          email:"1952175699@qq.com",
          phone:"15874423919",
          address:"湖南永州",
          departmentId:4,
          jobLevelId:9,
          posId:36,
          engageForm:"劳务合同",
          tiptopDegree:"本科",
          specialty:"软件工程",
          school:"吉首大学",
          beginDate:"2017-09-01",
          workState:"在职",
          workID:"00001942",
          contractTerm:2,  //合同期限
          conversionTime:"2021-01-01",
          notWorkDate:null,
          beginContract:"2021-01-01",
          endContract:"2021-01-01",
          workAge:null
        };
        this.inputDepName = '';
      },
      //编辑职工
      editEmp(data) {
        this.initPositions();
        this.title = '编辑员工信息';
        this.emp = data;
        this.inputDepName = data.department.name;
        this.dialogVisible = true;
      },
      //添加与更改
      addEmployee() {
        //编辑职工
        if(this.emp.id) {
          //form表单数据校验
          this.$refs['addForm'].validate(valid=>{
            if(valid) {
              this.putRequest("/employee/basic/",this.emp).then(res=>{
                if(res) {
                  this.dialogVisible = false;
                  this.initEmpList();
                }
              })
            }
          })
        }
        //添加职工
        else {
          //form表单数据校验
          this.$refs['addForm'].validate(valid=>{
            if(valid) {
              this.postRequest("/employee/basic/",this.emp).then(res=>{
                if(res) {
                  this.dialogVisible = false;
                  this.initEmpList();
                }
              })
            }
          })
        }
      },
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
        //获取职称
        if(!window.sessionStorage.getItem("jobLevels")) {
          this.getRequest("/employee/basic/levels").then(res=>{
            if(res) {
              this.jobLevels = res;
              window.sessionStorage.setItem("jobLevels",JSON.stringify(res));
            }
          })
        }else{
          //从session获取
          this.jobLevels = JSON.parse(window.sessionStorage.getItem("jobLevels"));
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

        //获取部门信息
        if(!window.sessionStorage.getItem("depList")) {
          this.getRequest("/employee/basic/deps").then(res=>{
            if(res) {
              this.depList = res;
              window.sessionStorage.setItem("depList",JSON.stringify(res));
            }
          })
        }else{
          //从session获取
          this.depList = JSON.parse(window.sessionStorage.getItem("depList"));
        }
      },
      //初始化职工列表数据
      initEmpList(type) {
        this.loading = true;
        let url = '/employee/basic/?page=' + this.page +"&size=" + this.size ;

        if(type && type == 'advanceSearch') {
          //高级条件加载
          if(this.searchParams.posId != null)
            url += '&posId=' + this.searchParams.posId;
          if(this.searchParams.jobLevelId != null)
            url += '&jobLevelId=' + this.searchParams.jobLevelId;
          if(this.searchParams.nationId != null)
            url += '&nationId=' + this.searchParams.nationId;
          if(this.searchParams.politicId != null)
            url += '&politicId=' + this.searchParams.politicId;
          if(this.searchParams.engageForm != null)
            url += '&engageForm=' + this.searchParams.engageForm;
          if(this.searchParams.departmentId != null)
            url += '&departmentId=' + this.searchParams.departmentId;
          if(this.searchParams.beginDateScope != null)
            url += '&beginDateScope=' + this.searchParams.beginDateScope;

        } else {
          //普通加载与姓名搜索
          url += "&name=" + this.searchWords;
        }

        this.getRequest(url).then(res=>{
          if(res) {
            this.empList = res.data;
            this.total = res.total;
            console.log(this.empList);
          }
          this.loading = false;
        })
      },
      //弹出增加框
      showAddView() {
        this.initEmpty();
        this.title = '添加员工';
        this.dialogVisible = true;
        //获取最大工号
        this.initWorkId();
      },
      //展示部门选择树
      showDepView(num) {
        if(num == 1) {
          this.sDepVisible = !this.sDepVisible;
        }
        else {
          this.depVisible = !this.depVisible;
        }
      },
      //删除职工
      delEmp(data){
        this.$confirm('确定永久删除【'+data.name+'】职工！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/employee/basic/"+data.id).then(res=> {
            if(res) {
              this.initEmpList();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //部门tree选择属性
      handleNodeClick(data) {
        this.depVisible = !this.depVisible;
        this.inputDepName = data.name;
        this.emp.departmentId = data.id;
      },
      //搜索部门选择属性
      SearchHandleNodeClick(data) {
        this.sDepVisible = !this.sDepVisible;
        this.inputDepName = data.name;
        this.searchParams.departmentId = data.id;
      },
      //获取职位列表
      initPositions() {
        this.getRequest("/employee/basic/positions").then(res=>{
          if(res) {
            this.positions = res;
          }
        });
      },
      //获取最大工号
      initWorkId() {
        this.getRequest("/employee/basic/wordId").then(res=>{
          if(res) {
            this.emp.workID = res.obj;
          }
        });
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initEmpList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initEmpList();
      },
      //导出数据
      exportExcel() {
        //在当前也打开导出地址
        window.open('/employee/basic/export',"_parent");
      },
      //导入数据
      beforeUpload() {
        this.text = '正在导入中……';
        this.icon = 'el-icon-loading';
        this.buttonVisible = true;
      },
      onSuccess(response, file, fileList) {
        this.text = '导入数据';
        this.icon = 'el-icon-upload2';
        this.buttonVisible = false;
        this.initEmpList();
      },
      onError(err, file, fileList) {
        this.text = '导入数据';
        this.icon = 'el-icon-upload2';
        this.buttonVisible = false;
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
