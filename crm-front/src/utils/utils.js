import { ElMessage, ElMessageBox } from 'element-plus'

/**
 * 使用 ElementPlus 进行消息框提示
 * @param {string} msg 提示消息
 * @param {string} type 消息框类型，可以选择 success、error、info,、warning
 */
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

/**
 * 用于显示需要确认的消息框
 * @param {string} msg 消息框主体消息
 * @param {string} title 消息框标题 
 * @param {string} type 消息框类型，可以选择 success、error、info,、warning
 * @param {function} postSuceess 确认后的处理函数
 * @param {function} postCencel 取消后的处理函数
 */
export function messageConfirm(msg, title, type, postSuceess, postCencel) {
	ElMessageBox.confirm(
		msg,
		title,
		{
			confirmButtonText: '确认',
			cancelButtonText: '取消',
			type: type,
		}
	).then(postSuceess).catch(postCencel)
}

/**
 * 清除浏览器中存储的 Token
 */
export function clearToken() {
	window.localStorage.removeItem(getTokenKey())
	window.sessionStorage.removeItem(getTokenKey())
}

/* ======= */
/**
 * 获取存储 Token 使用的键值
 * @returns Token 键值
 */
export function getTokenKey() {
	return "crm_token"
}

/**
 * 获取服务器的根路径地址
 * @returns 服务器的根路径
 */
export function getServerRoot() {
	return "http://localhost:8009"
}