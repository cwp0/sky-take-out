// 配置文件修改后要重启项目才能生效

const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 7070, // 修改dev环境下前端的端口号，防止与后端冲突
    proxy: { // 配置代理，由代理转发前端的请求到后端，解决跨域问题
      '/api': { // 当前端匹配到/api开头的请求时，执行下面的操作，将请求转发到后端
        target: 'http://localhost:8080', // 后端接口地址
        pathRewrite: { //将前端请求的/api替换成空字符串
          '^/api': ''
        }
      }
    }
  }
})
