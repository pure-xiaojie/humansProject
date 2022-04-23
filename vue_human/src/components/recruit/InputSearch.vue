<template>
  <div :class="size" class="input-search">
    <i class="prefix-icon el-icon-search"></i>
    <input
        v-bind="$attrs"
        ref="input"
        type="text"
        :value="value"
        @change="e => $emit('change', e.target.value)"
        @keypress.enter="search"
    />
    <span @click="search" class="input-search-button">搜索</span>
  </div>
</template>
<script>
  export default {
    name: "input-search",
    model: {
      event: "change"
    },
    props: {
      value: String,
      size: {
        type: String,
        default: "medium",
        validator(value) {
          return ["small", "medium", "large"].includes(value);
        }
      }
    },
    methods: {
      search() {
        this.$emit("search", this.$refs.input.value);
      }
    }
  };
</script>
<style lang="less" scoped>
  .input-search {
    margin-left: auto;
    margin-right: auto;
    height: 50px;
    width: 100%;
    min-width: 400px;
    max-width: 1300px;
    position: relative;
    border-radius: 30px;
    overflow: hidden;
    border: 1px solid #3370ff;
    font-size: 17px;
    ::-webkit-input-placeholder {
      color:#909399;
    }
    &.small {
      height: 40px;
      font-size: 14px;
    }
    &.large {
      height: 60px;
      font-size: 20px;
    }
    .prefix-icon {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 10px;
      display: flex;
      align-items: center;
    }
    input {
      font-size: inherit;
      height: 100%;
      width: 100%;
      line-height: 100%;
      border: none;
      padding: 1px;
      padding-left: 2em;
    }
    &-button {
      padding: 1px;
      color: #fff;
      text-align: center;
      position: absolute;
      right: 0;
      top: 0;
      width: 100px;
      height: 100%;
      background: #3370ff;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      &:hover {
        // background: rgba(255, 255, 255, 0.5);
        &::before {
          transition: all 0.3s;
          content: "";
          position: absolute;
          width: 100%;
          top: -1px;
          bottom: -1px;
          background: rgba(255, 255, 255, 0.4);
        }
      }
    }
  }
</style>
