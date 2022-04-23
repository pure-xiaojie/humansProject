import axios from 'axios';
import {Message} from "element-ui";
import router from '../router'

//拦截返回信息进行封装,res.data为服务端返回的data信息
axios.interceptors.response.use(res=>{
  //服务端（业务）错误
  if(res.status && res.status == 200 && res.data.status == 500) {
    Message.error({message:res.data.msg})
    return;
  }

  if(res.data.msg) {
    Message.info(res.data.msg)
  }
  //成功返回信息
  return res.data;

},error => {
  if(error.response.status == 504 || error.response.status == 404) {
    Message.error({message:'服务端宕机了！'})
  } else if(error.response.status == 403) {
    Message.error({message:'权限不足，请联系管理员！'})
  } else if(error.response.status == 401) {
    Message.error({message:'尚未登录，请登录！'});
    router.replace('/');
  } else {
    if(error.response.data.msg) {
      Message.error({message:error.response.data.msg})
    } else {
      Message.error({message:'未知错误！'})
    }
  }
  return;
});

//请求封装
let base = '';

//post请求封装，Key、Value形式
export const postKeyValueRequest=(url,params)=>{
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = '';
      for(let i in data) {
        ret += encodeURIComponent(i)+'='+encodeURIComponent(data[i])+'&';
      }
      console.log(ret);
      return ret;
    }],
    headers: {
      'Content-Type':'application/x-www-form-urlencoded'
    }
  })
};

//post请求封装，json形式
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}

//put请求封装
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params
  })
}

//get请求封装
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    params: params
  })
}

//delete请求封装
export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    params: params
  })
}

