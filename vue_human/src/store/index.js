import Vue from 'vue'
import Vuex from 'vuex'
import SockJS from  'sockjs-client';
import '../utils/stomp';
import {getRequest} from '../utils/api'
import { Notification } from 'element-ui';

Vue.use(Vuex);

const now = new Date();

/**
 * 定义一个全局实例对象(Vuex.Store)
 * state：存储状态。也就是变量
 * mutations：提交状态修改。这是vuex中唯一修改state的方式，但不支持异步操作。第一个参数默认是state。
 *            外部调用方式：store.commit('initRoutes', data)
 * actions：和mutations类似。不过actions支持异步操作。
 *            外部调用方式：store.dispatch('connect')
 *
 * */
const store = new Vuex.Store({
  //全局变量
  state: {
    //路由
    routes: [],
    //聊天记录
    sessions:[],
    //用户列表
    users: [],
    //列表选中用户
    currentSession: null,
    //当前登录用户
    currentUser: JSON.parse(window.sessionStorage.getItem("user")),
    //当前登录员工
    currentEmp: JSON.parse(window.sessionStorage.getItem("emp")),
    filterKey:'',
    //socket连接
    stomp: null,
    //消息提示红点
    isDot: {}

  },
  //提交状态修改
  mutations: {
    //路由修改
    initRoutes(state,data) {
      state.routes = data
    },
    //改变选中的用户
    changeCurrentSession (state,user) {
      Vue.set(state.isDot, state.currentUser.username + '#' + user.username, false);
      state.currentSession = user;
    },
    //消息发送处理
    addMessage (state,data) {
      let m = state.sessions[state.currentUser.username+'#'+data.to];
      if (!m) {
        //state.sessions[state.currentUser.username+'#'+msg.to] = [];
        //聊天记录变化时，页面实时更新
        Vue.set(state.sessions,state.currentUser.username+'#'+data.to,[]);
      }
      state.sessions[state.currentUser.username+'#'+data.to].push({
        content: data.msg,
        date: new Date(),
        self: !data.notSelf
      })
    },
    //初始化本地聊天记录
    INIT_DATA (state) {
      //浏览器本地的历史聊天记录可以在这里完成
      let data = localStorage.getItem('chat-session');
      //console.log(data)
      if (data) {
        state.sessions = JSON.parse(data);
      }
    },
    //初始化用户数据
    INIT_USER(state,data) {
      state.users = data;
    },
    //初始化当前登录用户
    INIT_CURRENT_USER(state,data) {
      state.currentUser = data;
    },
    //初始化当前登录员工
    INIT_CURRENT_EMP(state,data) {
      state.currentEmp = data;
    }
  },
  //异步操作
  actions: {
    //websocket连接,使用到了stomp对象，连接后监听消息接收
    connect(context) {
      context.state.stomp = Stomp.over(new SockJS('/ws/ep'));
      console.log('connect websocket');
      //建立连接
      context.state.stomp.connect({},success=>{
        //订阅消息，消息接收处理
        context.state.stomp.subscribe('/user/queue/chat',msg=>{
          let receiveMsg = JSON.parse(msg.body);

          //消息提示
          if (!context.state.currentSession || receiveMsg.from != context.state.currentSession.username) {
            Notification.info({
              title: '【'+receiveMsg.nickname+'】发来一条消息！',
              message: receiveMsg.msg.length > 10 ? receiveMsg.msg.substr(0,10):receiveMsg.msg,
              position: 'bottom-right'
            });
            Vue.set(context.state.isDot, context.state.currentUser.username + '#' + receiveMsg.from, true);
          }

          receiveMsg.notSelf = true;
          receiveMsg.to = receiveMsg.from;
          context.commit('addMessage',receiveMsg);
        })
      },error=>{
        console.log('connect websocket error!');
      })
    },
    initData (context) {
      context.commit('INIT_DATA');
      getRequest("/chat/users").then(res=>{
        if(res) {
          context.commit('INIT_USER',res);
        }
      })
    }
  }
});

//数据监听，若sessions数据发生变化，则保存到localStorage中
store.watch(function (state) {
  return state.sessions
},function (val) {
  console.log('CHANGE: ', val);
  console.log('CHANGE: ', JSON.stringify(val));
  localStorage.setItem('chat-session-vue', JSON.stringify(val));
},{
  deep:true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
});


export default store;
