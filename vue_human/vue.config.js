//定义代理对象
let proxyObj = {};

//websocket代理,以websocket协议转发
proxyObj['/ws'] = {
  ws: true,
  target: 'ws://localhost:8081',
};

//http代理
proxyObj['/'] = {
  ws: false,
  target: 'http://localhost:8081',
  changeOrigin: true,
  pathRewrite: {
    '^/': ''
  }
};

module.exports = {
  devServer: {
    host: 'localhost',
    port: 8080,
    proxy: proxyObj
  }
};
