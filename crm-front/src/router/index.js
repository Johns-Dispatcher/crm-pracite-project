import { createRouter, createWebHashHistory } from 'vue-router'

import LoginView from '../components/login/LoginView.vue'
import DashboardView from '../components/dashboard/DashboardView.vue'
import UserView from '../components/dashboard/user/UserView.vue'
import UserInfoView from '../components/dashboard/user/UserInfoView.vue'
import ActivityView from '../components/dashboard/activity/ActivityView.vue'
import ActivityRecordView from '../components/dashboard/activity/ActivityRecordView.vue'
import ActivityInfoView from '../components/dashboard/activity/ActivityInfoView.vue'
import ClueView from '../components/dashboard/clue/ClueView.vue'


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
			component: LoginView,
		},
		{
			path: "/dashboard",
			component: DashboardView,
			children: [
				{
					path: "user",
					component: UserView,
				},
				{
					path: "user/:loginAct",
					component: UserInfoView,
				},
				{
					path: "activity",
					component: ActivityView,
				},
				{
					path: "activity/add",
					component: ActivityRecordView,
				},
				{
					path: "activity/edit/:id",
					component: ActivityRecordView,
				},
				{
					path: "activity/view/:id",
					component: ActivityInfoView,
				},
				{
					path: "clue",
					component: ClueView,
				}
			]
		},
	]
})