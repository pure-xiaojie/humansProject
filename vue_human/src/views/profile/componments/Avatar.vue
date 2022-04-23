<template>
  <el-dialog :close-on-click-modal="false"
    class="update-avatar"
    title="更改头像"
    :width="width"
    top="50px"
    :close-on-press-escape="false"
    :visible.sync="isVisible"
  >
    <el-tabs v-model="activeName" class="avatar-tabs">
      <el-tab-pane label="后田花子" name="first">
        <template v-for="(avatar, index) in hthz">
          <div :key="index" class="avatar-wrapper">
            <img :src="ChangeAvatar(avatar)" @click="change(avatar)">
          </div>
        </template>
      </el-tab-pane>

      <el-tab-pane label="脸萌" name="second">
        <template v-for="(avatar, index) in lm">
          <div :key="index" class="avatar-wrapper">
            <img :src="ChangeAvatar(avatar)" @click="change(avatar)">
          </div>
        </template>
      </el-tab-pane>

      <el-tab-pane label="阿里系" name="third">
        <template v-for="(avatar, index) in al">
          <div :key="index" class="avatar-wrapper">
            <img :src="ChangeAvatar(avatar)" @click="change(avatar)">
          </div>
        </template>
      </el-tab-pane>
      <el-tab-pane label="上传" name="fourth">
        <p>点击图片上传</p>
        <el-upload :show-file-list="false"
                   :on-success="onSuccess" :data="user" action="/user/userface">
          <img title="点击修改用户图像" :src="ChangeAvatar(user.userface)"
               style="width: 100px;height: 100px;border-radius: 50px" alt="">
        </el-upload>
      </el-tab-pane>
    </el-tabs>
  </el-dialog>
</template>
<script>

  const hthz = [
    'default.gif', 'hthz_1.jpeg',
    'hthz_2.jpg', 'hthz_3.jpg',
    'hthz_4.jpeg', 'hthz_5.jpg',
    'hthz_6.jpg', 'hthz_7.jpeg',
    'hthz_8.jpeg', 'hthz_9.jpeg',
    'hthz_10.jpeg', 'hthz_11.jpg',
    'hthz_12.jpg', 'hthz_13.jpeg',
    'hthz_14.jpeg', 'hthz_15.jpg',
    'hthz_16.jpg', 'hthz_17.jpeg'
  ];
  const al = [
    'al_2.png', 'al_1.png', 'al_3.png',
    'al_6.png', 'al_5.png', 'al_4.png'
  ];
  const lm = [
    'lm_18.jpg', 'lm_12.jpg', 'lm_19.jpg',
    'lm_13.jpg', 'lm_1.jpg', 'lm_2.jpg',
    'lm__3.jpg', 'lm_4.jpg', 'lm_5.jpg',
    'lm_6.jpg', 'lm_7.jpg', 'lm_8.jpg',
    'lm_10.jpg', 'lm_11.jpg', 'lm_14.jpg',
    'lm_15.jpg', 'lm_16.jpg', 'lm_17.jpg'
  ];
  export default {
    name: 'Avatar',
    props: {
      dialogVisible: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        activeName: 'first',
        screenWidth: 0,
        width: this.initWidth(),
        //头像数组
        hthz,
        al,
        lm,
        //用户对象
        user: JSON.parse(window.sessionStorage.getItem("user"))
      }
    },
    computed: {
      isVisible: {
        get() {
          return this.dialogVisible
        },
        set() {
          this.close()
        }
      }
    },
    mounted() {
      window.onresize = () => {
        return (() => {
          this.width = this.initWidth()
        })()
      };
    },
    methods: {
      //修改头像
      change(avatar) {
        this.user.userface = avatar;

        this.putRequest("/user/info",this.user).then(resp => {
          if (resp) {
            //回调到userCard的changeSuccess方法
            this.$emit('success', avatar);
          }
        })
      },
      //图像处理
      ChangeAvatar(url) {
        if(url.length < 20)
          return require(`@/assets/avatar/${url}`);
        else
          return url;
      },
      //上传成功
      onSuccess() {
        //回调到userCard的changeSuccess方法
        this.$emit('success', '');
      },
      initWidth() {
        this.screenWidth = document.body.clientWidth;
        if (this.screenWidth < 991) {
          return '90%'
        } else if (this.screenWidth < 1400) {
          return '55%'
        } else {
          return '820px'
        }
      },
      close() {
        this.$emit('close')
      }
    }
  }
</script>
<style lang="scss" scoped>
  .update-avatar {
    padding: 0 1rem 1rem 1rem !important;

  .avatar-tabs {

  .avatar-wrapper {
    display: inline-block;

  img {
    width: 5rem;
    border-radius: 50%;
    display: inline-block;
    margin: .5rem;
    cursor: pointer;
  }

  }
  }
  }
</style>
