<template>
  <div>
    <!--输入框与按钮-->
    <div  class="topInfo">
      <!--left button-->
        <el-input placeholder="请输入用户名…" class="topLeft" size="small"
                  prefix-icon="el-icon-search" v-model="userName"
                  @keydown.enter.native="initLogList" clearable
                  @clear="initLogList"></el-input>
        <el-button type="success" size="small" icon="el-icon-search"
                   @click="initLogList">搜索</el-button>
    </div>

    <!--数据展示表格 -->
    <div class="posTable">
      <el-table :data="logList" border stripe style="width: 100%">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="编号" width="50"></el-table-column>
        <el-table-column prop="userName" label="用户名" width="100"></el-table-column>
        <el-table-column label="状态"  width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 0">登录失败</el-tag>
            <el-tag v-if="scope.row.status == 1">登录成功</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="ip" label="ip" width="180"></el-table-column>
        <el-table-column prop="location" label="登录地点" width="150"></el-table-column>
        <el-table-column prop="browser" label="浏览器" width="140"></el-table-column>
        <el-table-column prop="os" label="操作系统" width="140"></el-table-column>
        <el-table-column label="登录时间" width="160">
          <template slot-scope="scope">
            <span v-text="parseTime(scope.row.createDate)"></span>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)">删除</el-button>
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
</template>

<script>
  export default {
    name: "LoginLog",
    data() {
      return {
        logList: [],
        userName: '',
        total: 0,
        page: 1,
        size: 5,
      }
    },
    mounted() {
      this.initLogList();
    },
    methods: {
      //查询日志列表
      initLogList() {
        this.getRequest("/log/page/?page="+this.page+"&size=" + this.size +"&userName=" + this.userName).then(res=> {
          this.logList = res.data;
          this.total = res.total;
          //console.log(res);
        })
      },
      //分页page改变
      pageChange(page) {
        this.page = page;
        this.initLogList();
      },
      //分页size改变
      sizeChange(size) {
        this.size =size;
        this.initLogList();
      },
      //删除日志
      handleDelete(data) {
        this.$confirm('确定永久删除【'+data.createDate+'】日志！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/log/login/"+data.id).then(res=> {
            this.initLogList();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //时间格式化
      parseTime(time, pattern) {
        if (time == null) {
          return "";
        }
        if (arguments.length === 0) {
          return null;
        }
        const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}';
        let date;
        if (typeof time === 'object') {
          date = time
        } else {
          if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
            time = parseInt(time)
          }
          if ((typeof time === 'number') && (time.toString().length === 10)) {
            time = time * 1000
          }
          date = new Date(time)
        }
        const formatObj = {
          y: date.getFullYear(),
          m: date.getMonth() + 1,
          d: date.getDate(),
          h: date.getHours(),
          i: date.getMinutes(),
          s: date.getSeconds(),
          a: date.getDay()
        };
        const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
          let value = formatObj[key];
          // Note: getDay() returns 0 on Sunday
          if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value ] }
          if (result.length > 0 && value < 10) {
            value = '0' + value
          }
          return value || 0
        });
        return time_str;
      }
    }
  }
</script>

<style scoped>
  .topInfo{
    display: flex;
  }
  .topLeft{
    width: 200px;
    margin-right: 10px;
  }
  .posTable {
    margin-top: 20px;
  }
  .pageInfo{
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
  }
</style>
