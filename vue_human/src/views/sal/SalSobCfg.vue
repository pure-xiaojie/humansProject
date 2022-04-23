<template>
  <div>
    <!--表格数据-->
    <div class="topInfo">
      <el-table :data="empList" border stripe>
        <el-table-column type="selection" align="left" width="55"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column prop="workID" label="工号" width="120"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
        <el-table-column prop="phone" label="电话号码" width="130"></el-table-column>
        <el-table-column prop="department.name" label="所属部门" width="120"></el-table-column>
        <el-table-column prop="beginDate" label="入职日期" width="120"></el-table-column>
        <el-table-column prop="workState" label="在职状态" width="80"></el-table-column>
        <el-table-column label="账套名称" align="center" width="180">
          <template slot-scope="scope">
            <el-tooltip placement="right" v-if="scope.row.salary">
              <div slot="content">
                <table>
                  <tr>
                    <td>账套名称：</td>
                    <td>{{scope.row.salary.name}}</td>
                  </tr>
                  <tr>
                    <td>基本工资：</td>
                    <td>{{scope.row.salary.basicSalary}}</td>
                  </tr>
                  <tr>
                    <td>交通补助：</td>
                    <td>{{scope.row.salary.trafficSalary}}</td>
                  </tr>
                  <tr>
                    <td>午餐补助：</td>
                    <td>{{scope.row.salary.lunchSalary}}</td>
                  </tr>
                  <tr>
                    <td>奖金：</td>
                    <td>{{scope.row.salary.bonus}}</td>
                  </tr>
                  <tr>
                    <td>启用时间：</td>
                    <td>{{scope.row.salary.createDate}}</td>
                  </tr>
                  <tr>
                    <td>养老金比率：</td>
                    <td>{{scope.row.salary.pensionPer}}</td>
                  </tr>
                  <tr>
                    <td>养老金基数：</td>
                    <td>{{scope.row.salary.pensionBase}}</td>
                  </tr>
                  <tr>
                    <td>医疗保险比率：</td>
                    <td>{{scope.row.salary.medicalPer}}</td>
                  </tr>
                  <tr>
                    <td>医疗保险基数：</td>
                    <td>{{scope.row.salary.medicalBase}}</td>
                  </tr>
                  <tr>
                    <td>公积金比率：</td>
                    <td>{{scope.row.salary.accumulationFundPer}}</td>
                  </tr>
                  <tr>
                    <td>公积金基数：</td>
                    <td>{{scope.row.salary.accumulationFundBase}}</td>
                  </tr>
                </table>
              </div>
              <el-tag>{{scope.row.salary.name}}</el-tag>
            </el-tooltip>
            <el-tag type="info" v-else>暂未设置</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-popover placement="left" title="修改工资账套"
                        width="200" trigger="click"
                        @show="showEditView(scope.row.salary)"
                        @hide="hidePop(scope.row)">
              <div>
                <el-select v-model="salaryId" placeholder="请选择账套" size="mini">
                  <el-option
                      v-for="item in salaryList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </div>
              <el-button slot="reference" size="mini" type="danger">修改工资账套</el-button>
            </el-popover>

          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <div class="pageInfo">
        <el-pagination background :page-sizes="[5, 10, 15, 20, 30]"
            layout="sizes, prev, pager, next, jumper, ->, total, slot"
            :total="total" @current-change="pageChange" @size-change="sizeChange">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "EmpBasic",
    data() {
      return {
        empList: [],
        salaryList: [],
        salaryId: null,
        //分页
        page: 1,
        size: 10,
        total: 0
      }
    },
    mounted() {
      this.initEmpList();
      this.initSalaryList();
    },
    methods: {
      //初始化职工数据
      initEmpList() {
        this.getRequest("/salary/sobcfg/?page=" + this.page + '&size=' + this.size).then(res => {
          this.empList = res.data;
          this.total = res.total;
        })
      },
      //初始化账套数据
      initSalaryList() {
        this.getRequest("/salary/sobcfg/salary").then(res => {
          this.salaryList = res;
        })
      },
      //修改职工账套
      showEditView(data) {
        if(data != null) {
          this.salaryId = data.id;
        }
      },
      //更新
      hidePop(data) {
        if(this.salaryId == null && data.salary == null) {
          return;
        }
        else if(this.salaryId != null && data.salary == null) {
          this.putRequest("/salary/sobcfg/?eid=" + data.id + '&sid=' + this.salaryId).then(res=>{
            this.initEmpList();
          });
        }
        else if(data.salary != null && this.salaryId != data.salary.id) {
          this.putRequest("/salary/sobcfg/?eid=" + data.id + '&sid=' + this.salaryId).then(res=>{
            this.initEmpList();
          });
        }
        this.salaryId = null;
      },
      //分页
      pageChange(page) {
        this.page = page;
        this.initEmpList();
      },
      sizeChange(size) {
        this.size = size;
        this.initEmpList();
      }
    }
  }
</script>

<style scoped>
  .topInfo{
    margin-top: 20px;
  }
  .pageInfo{
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
  }
</style>
