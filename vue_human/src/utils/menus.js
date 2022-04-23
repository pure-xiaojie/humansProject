import {getRequest} from "./api";
import Home from '../views/Home.vue'

//菜单初始化方法
export const initMenu = (router,store)=>{
  if(store.state.routes.length > 0) {
    return;
  }
  //获取菜单
  getRequest("/system/config/menu").then(data=>{
    if(data) {
      let fmtRoutes = formatRoutes(data);

      router.addRoutes(fmtRoutes);
      store.commit('initRoutes',fmtRoutes);
    }
  });
  //初始化websocket
  store.dispatch('connect');
};

//菜单数据格式转换
export const formatRoutes = (routes) => {
  let fmRoutes = [];

  routes.forEach(router=>{
    //批量变量定义
    let {
        path,
        component,
        name,
        meta,
        iconCls,
        children
    } = router;

    //递归调用格式化
    if(children && children instanceof Array) {
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      name: name,
      iconCls: iconCls,
      meta: meta,
      children: children,
      component(resolve) {
        if(component.startsWith("Home")) {
          require(['../views/'+component+'.vue'],resolve);
        } else if(component.startsWith("Emp")) {
          require(['../views/emp/'+component+'.vue'],resolve);
        } else if(component.startsWith("Per")) {
          require(['../views/per/'+component+'.vue'],resolve);
        } else if(component.startsWith("Sal")) {
          require(['../views/sal/'+component+'.vue'],resolve);
        } else if(component.startsWith("Sta")) {
          require(['../views/sta/'+component+'.vue'],resolve);
        } else if(component.startsWith("Sys")) {
          require(['../views/sys/'+component+'.vue'],resolve);
        }else if(component.startsWith("Pos")) {
          require(['../views/recruitment/'+component+'.vue'],resolve);
        }else if(component.startsWith("Res")) {
          require(['../views/recruitment/'+component+'.vue'],resolve);
        }
      }
    };

    fmRoutes.push(fmRouter);
  });
  return fmRoutes;
};
