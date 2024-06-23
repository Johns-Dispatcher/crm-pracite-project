import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [vue()],
	server: {
		// IP 地址
		host: '0.0.0.0',
		// 启动后的端口号
		port: 8081,
		// 是否自动开启浏览器
		open: false,
	}
})
