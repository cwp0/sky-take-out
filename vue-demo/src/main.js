import Vue from "vue"
import App from "./App.vue"
import router from "./router"
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import store from "./store"

// 全局使用element-ui，通过这个布局容器实现页面布局
Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  router,
  store, // 使用vuex功能
  render: h => h(App),
}).$mount("#app")
