<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    {{name + "是" + (age > 30 ? "妇女" : "少女")}}  <!--  文本插值：双花括号{{}}  -->
    <div><input type="text" v-bind:value="name" ></div>
    <div><input type="text" :value="age" ></div>  <!-- 属性绑定：v-bind:value=""，这里的v-bind可以省略，只留一个冒号 -->
    <!-- 事件绑定：为元素绑定对应的事件。v-on:click=""，其中v-on:可以简写为@ -->
    <div><input type="button" value="保存" v-on:click="handleSave">&nbsp; &nbsp; &nbsp;<input type="button" value="取消" @click="handleCancel"></div>
    <!-- 双向绑定：表单输入项和data()中的属性绑定，任意一方改变都回同步给对方 -->
    <div><input type="text" v-model="age" ><input type="button" value="年龄+1" @click="handleChange"></div> <!--  注意这里的方法，无参方法可以直接省略括号  -->
    <!-- 条件渲染：根据表达式的值来动态渲染页面元素。v-if、v-else、v-else-if -->
    {{name + "是"}}<div v-if="age > 0 && age <= 5">宝宝</div><div v-if="age > 5 && age <= 11">小学生</div><div v-if="age > 11 && age <= 17">中学生</div><div v-if="age > 17 && age <= 21">大学生</div><div v-if="age > 21">牛马</div>
    <input type="button" value="发送登录请求" @click="handleSendPost" />&nbsp; &nbsp; &nbsp;<input type="button" value="直接获取商店状态" @click="handleSendGeneral" />&nbsp; &nbsp; &nbsp;<input type="button" value="获取商店状态" @click="handleSendGet" />
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "HelloWorld",
  props: {
    msg: String
  },
  data() {
    return {
      name: "小红",
      age: 1,
    }
  },
  methods: {
    handleSave() {
      alert(this.name + this.age + "已保存!")
    },
    handleCancel() {
      alert(this.name + this.age + "已取消!")
    },
    handleChange() {
      this.age += 1;
    },
    handleSendPost() {
      // 通过axios的post方法发送请求
      axios.post("/api/admin/employee/login", {
        username: "admin",
        password: "123456"
      }).then(res => {
        console.log(res.data);
      }).catch(err => {
        console.log(err);
      })
    },
    handleSendGet() {
      // 通过axios的get方法发送请求
      axios.get("/api/admin/shop/status", {
        headers: { // 在请求头里面配置token
          token: "eyJhbGciOiJIUzI1NiJ9.eyJlbXBJZCI6MSwiZXhwIjoxNzIxMzcyMjEwfQ.Epmtf_xtBtXjFdhCp6m9GLenRCrFDD371UJ0UhyAoNY"
        }
      }).then(res => {
        console.log(res.data);
      }).catch(err => {
        console.log(err);
      })
    },
    handleSendGeneral() {
      // 通过axios发送统一请求方式
      axios({
        method: "post",
        url: "/api/admin/employee/login",
        data: {
          username: "admin",
          password: "123456"
        }
      }).then(res => {
        console.log(res.data.data.token);
        axios({
          method: "get",
          url: "/api/admin/shop/status",
          headers: {
            token: res.data.data.token
          }
        })
      }).catch(err => {
        console.log(err);
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
input {
  text-align: center;
}
</style>
