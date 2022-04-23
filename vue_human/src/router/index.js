import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import EmpHome from '../views/EmpHome'
import FriendChat from '../views/chat/FriendChat.vue'
import ProFile from '../views/profile/index.vue'
import EmpProFile from '../components/empProfile/index.vue'
import RecuritHome from '../views/recruitment/RecuritHome'
import JobList from '../views/recruitment/JobList'
import jobDetail from '../views/recruitment/JobDetail'
import pdfView from '../views/recruitment/PdfView'
import EmpSalary from "../views/emp/EmpSalary";
import EmpBasic from "../views/emp/EmpBasic";
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: '首页',
    component: RecuritHome,
    hidden : true
  },
  {
    path: '/login',
    name: '登录',
    component: Login,
    hidden : true
  },
    //系统首页
  {
    path: '/home',
    name: 'Home',
    component: Home,
    hidden : true,
    children: [
      {
        path: '/chat',
        name: '在线聊天',
        component: FriendChat,
        hidden : true
      },
      {
        path: '/profile',
        name: '个人中心',
        component: ProFile,
        hidden: true
      }
    ]
  },
    //员工首页
  {
    path: '/empHome',
    name: 'EmpHome',
    component: EmpHome,
    hidden : true,
    children: [
      {
        path: '/empProfile',
        name: '个人中心',
        component: EmpProFile,
        hidden: true
      },
      {
        path: '/empBasic',
        name: '员工资料',
        component: EmpBasic,
        hidden: true
      },
      {
        path: '/empSalary',
        name: '员工薪资',
        component: EmpSalary,
        hidden: true
      }
    ]
  },
  {
    path: '/website',
    name: '人才招聘',
    component: RecuritHome,
    hidden : true,
  },
  {
    path: '/jobList',
    name: '职位列表',
    component: JobList,
    hidden : true,
  },
  {
    path: '/jobDetail',
    name: '职位详情',
    component: jobDetail,
    hidden : true,
  },
  {
    path: '/pdf',
    name: '简历预览',
    component: pdfView,
    hidden : true,
  }
];

const router = new VueRouter({
  routes
});

export default router
