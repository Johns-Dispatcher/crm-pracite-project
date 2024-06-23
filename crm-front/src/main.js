import { createApp } from 'vue'

// import './style.css'

// 导入 ElementPlus 到项目中
import ElementPlus from 'element-plus'
// 导入 ElementPlus 提供的 CSS 样式
import 'element-plus/dist/index.css'

import App from './App.vue'

// 让 Vue 使用 ElementPlus
// Vue 的挂载一定是最后一步
createApp(App).use(ElementPlus).mount('#app')
