import { ElMessage } from 'element-plus'

export function messageTip(msg, type) {
	ElMessage({
		// 是否有关闭按钮
		showClose: true,
		// 提示框的信息
		message: msg,
		// 提示框的类型
		type: type,
		// 文字居中
		center: true,
		// 留存多长时间，单位毫秒
		duration: 5000,
	})
}