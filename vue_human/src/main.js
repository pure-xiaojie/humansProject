import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import {initMenu} from "./utils/menus";
import 'font-awesome/css/font-awesome.min.css'
import VCharts from 'v-charts';
import VDistpicker from 'v-distpicker'

import {postRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {postKeyValueRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";



Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;

Vue.use(ElementUI);
Vue.use(VCharts);
Vue.use(VDistpicker);
Vue.config.productionTip = false;


//前置导航守卫，监听跳转
router.beforeEach((to,from,next) => {
  if(to.path == '/' ||to.path == '/login' || to.path == '/website' || to.path == '/jobList'
      || to.path == '/jobDetail' || to.path == '/pdf' || to.path == '/empHome'
      || to.path == '/empProfile' || to.path == '/empBasic' || to.path == '/empSalary') {
    next();
  } else {
    if(window.sessionStorage.getItem("user")) {
      initMenu(router,store);
      next();
    } else {
      next("/?redirect=" + to.path);
    }
  }
});


//让路由router、状态管理store生效
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
