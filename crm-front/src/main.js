import { createApp } from 'vue'

// Vue 自带的一些样式，目前不需要
// import './style.css'
// 导入 ElementPlus 提供的 CSS 样式
import 'element-plus/dist/index.css'

// 导入 ElementPlus 到项目中
import ElementPlus from 'element-plus'
// 导入路由配置对象
import router from './router'
// 导入主程序
import App from './App.vue'
// 引入 ElementPlus 提供的图标库
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
	app.component(key, component)
}

// 创建 Vue 主程序
// createApp(App)
app
	// 让 Vue 使用 ElementPlus
	.use(ElementPlus)
	// 让 Vue 使用 路由
	.use(router)
	// Vue 的挂载一定是最后一步
	.mount('#app')
