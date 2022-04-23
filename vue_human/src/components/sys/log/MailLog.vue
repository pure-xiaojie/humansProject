<template>
  <div>
    <!--数据展示表格 -->
    <div class="posTable">
      <el-table :data="logList" border stripe style="width: 100%">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="msgId" label="编号" width="155" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="empId" label="职工id" width="70"></el-table-column>
        <el-table-column label="状态"  width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 0">发送中</el-tag>
            <el-tag v-if="scope.row.status == 1">发成功</el-tag>
            <el-tag v-if="scope.row.status == 2">发失败</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="routeKey" label="routeKey" width="180"></el-table-column>
        <el-table-column prop="exchange" label="exchange" width="180"></el-table-column>
        <el-table-column prop="count" label="重试次数" width="80"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="140"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="140"></el-table-column>

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
    name: "MailLog",
    data() {
      return {
        logList: [],
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted() {
      this.initLogList();
    },
    methods: {
      //查询日志列表
      initLogList() {
        this.getRequest("/system/log/?page="+this.page+"&size=" + this.size).then(res=> {
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
        this.$confirm('确定永久删除【'+data.msgId+'】！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/log/"+data.msgId).then(res=> {
            this.initLogList();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>

<style scoped>
  .posTable {
    margin-top: 20px;
  }
  .pageInfo{
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
  }
</style>
