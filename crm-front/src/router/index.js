import { createRouter, createWebHashHistory } from 'vue-router'

import LoginView from '../components/login/LoginView.vue'
import Dashboard from '../components/dashboard/Dashboard.vue'

// 创建路由对象
export default createRouter({
	// 历史设置
	history: createWebHashHistory(),
	// 路由跳转配置
	routes: [
		{
			// 路由映射位置
			parh: "/",
			// 路由组件
			component: LoginView
		},
		{
			path: "/dashboard",
			component: Dashboard
		}
	]
})