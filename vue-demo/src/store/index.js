import Vue from "vue"
import Vuex from "vuex"
import axios from "axios";

Vue.use(Vuex)

// 集中管理多个组件共享的数据
export default new Vuex.Store({
  // 集中定义共享数据
  state: {
    name: "未登录用户",
  },
  getters: {
  },
  // 通过mutations中定义的函数修改共享数据，必须都是同步操作
  mutations: {
    setName(state, name) {
      state.name = name
    }
  },
  // 通过actions调用mutations，在actions中可以进行异步操作
  actions: {
    setNameByAxios(context) {
      // 通过axios发送统一请求方式
      axios({
        method: "post",
        url: "/api/admin/employee/login",
        data: {
          username: "admin",
          password: "123456"
        }
      }).then(res => {
        if (res.data.code === 1) {
          // 异步操作后，修改共享数据
          context.commit("setName", res.data.data.name)
        }
      }).catch(err => {
        console.log(err);
      })
    }
  },
  modules: {
  }
})
