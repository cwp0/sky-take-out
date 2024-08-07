/**
 * @File: index.js
 * @Description: 路由页面
 * @Author: cwp0
 * @CreatedTime: 2024/07/19 13:56
 * @Version: 1.0
 */

import Vue from "vue"
import VueRouter from "vue-router"
import HomeView from "../views/HomeView.vue"

// 路由器
Vue.use(VueRouter)

// vue属于单页面应用，所谓的路由，就是根据浏览器路径不同，用不同的视图组件替换这个页面的内容
// 路由表
const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView
    },
    {
        path: "/about",
        name: "about",
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited. 动态导入，按需加载
        component: () => import(/* webpackChunkName: "about" */ "../views/AboutView.vue")
    },
    {
        path: "/c",
        name: "c",
        component: () => import(/* webpackChunkName: "about" */ "../views/Container/ContainerVue.vue"),
        redirect: "/c/p1",
        // 嵌套路由(子路由)，对应的组件会展示在当前组件内部
        children: [
            {
                path: "/c/p1",
                component: () => import(/* webpackChunkName: "about" */ "../views/Container/P1Vue.vue")
            },
            {
                path: "/c/p2",
                component: () => import(/* webpackChunkName: "about" */ "../views/Container/P2Vue.vue")
            },
            {
                path: "/c/p3",
                component: () => import(/* webpackChunkName: "about" */ "../views/Container/P3Vue.vue")
            },
        ]
    },
    {
        path: "/404",
        name: "404",
        component: () => import(/* webpackChunkName: "about" */ "../views/404View.vue")
    },
    {
        path: "*",
        redirect: "/404"
    }
]


const router = new VueRouter({
    routes
})

export default router



