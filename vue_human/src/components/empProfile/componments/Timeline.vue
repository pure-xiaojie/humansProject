<template>
  <div class="block">
    <el-timeline style="height: 600px; overflow: auto"
                 :loading="loading">
      <el-timeline-item v-for="(item,index) of timeline"
                        :key="index" :timestamp=parseTime(item.createDate)
                        placement="top">
        <el-card>
          <p>
            <el-icon class="el-icon-link"/>
            登录IP：{{ item.ip }}
          </p>
          <p>
            <el-icon class="el-icon-location-outline"/>
            登录地点：{{ item.location }}
          </p>
          <p>
            <el-icon class="el-icon-bangzhu"/>
            浏览器类型：{{ item.browser }}
          </p>
          <p>
            <el-icon class="el-icon-monitor"/>
            OS：{{ item.os }}
          </p>
        </el-card>
      </el-timeline-item>
      <p v-if="loading">加载中...</p>
    </el-timeline>
  </div>
</template>

<script>
  export default {
    props: {
      username: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        timeline: [],
        loading: false,
        emp: JSON.parse(window.sessionStorage.getItem("emp"))
      }
    },
    mounted() {
      this.getTimeLine()
    },
    methods: {
      //获取登录日志
      getTimeLine() {
        this.loading = true;
        this.getRequest("/empInfo/loginLog?phone=" + this.emp.phone).then(response => {
          console.log(response);
          this.timeline = response;
          this.loading = false;
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
<style lang="scss" scoped>
  .el-card.is-always-shadow {
    box-shadow: none;
  }

  .el-card {
    border: 1px solid #f1f1f1;
    border-radius: 2px;
  }
</style>
