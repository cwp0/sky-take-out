<template>
  <div id="app">  <!--  template文件中只能有一个根div标签，不能有与之平级的  -->
    <nav>
      <!--   路由具体流程：路由链接组件发出路由请求给路由器 -> 路由器查找路由表 -> 更新视图到路由视图组件   -->
      <!--   路由链接组件   -->
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/c">C</router-link> |
      <router-link to="/test">Test</router-link> |  <!--  路由跳转有两种，一种是标签式，另一种是编程式  -->
      <input type="button" value="编程式路由跳转" @click="jump()" />
    </nav>
    <!--   路由视图组件：占位符，视图展示的位置   -->
    <router-view/>
  </div>
</template>

<script>
export default {
  methods: {
    jump() {
      this.$router.push('/about')
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}


nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
