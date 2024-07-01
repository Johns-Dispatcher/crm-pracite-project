// 封装 AJAX 请求方式
import axios from "axios";
import { clearToken, getServerRoot, getTokenKey, messageConfirm, messageTip } from "../utils/utils";

/* Axios 初始配置 */
// 设置默认请求地根址
axios.defaults.baseURL = getServerRoot()

// 添加请求拦截器
axios.interceptors.request.use(config => {
	// 在发送请求之前做些什么
	// 添加请求头信息，携带 Token 信息
	// Authorization: Bearer [BEARER_TOKEN] 

	let token = window.sessionStorage.getItem(getTokenKey())
	token = token ? token : window.localStorage.getItem(getTokenKey())

	if (token) {
		config.headers['Authorization'] = 'Bearer ' + token
	}

	return config;
}, function (error) {
	// 对请求错误做些什么
	return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(response => {
	// 2xx 范围内的状态码都会触发该函数。
	// 对响应数据做点什么
	// 获取响应结果，根据响应结果进行页面跳转
	let responseURL = response.request.responseURL
	// 退出登录和续约都不做响应
	if (responseURL.split("/").at(-1) === 'logout' || responseURL.split("/").at(-1) === 'renewal') {
		return response;
	}

	if (response.data.code > 900) {
		// 说明 JWT 验证出现问题
		messageConfirm(
			"用户已经过期，是否重新登陆",
			"登录过期",
			"warning",
			() => {
				messageTip('跳转至登录页面', 'success')
				// 清除有错误的 Token
				clearToken()
				window.location.href = "/"
			},
			() => {
				messageTip('取消跳转', 'warning')
			}
		)
	}

	return response;
}, function (error) {
	// 超出 2xx 范围的状态码都会触发该函数。
	// 对响应错误做点什么
	return Promise.reject(error);
});

/* 封装 Axios 请求方法 */

/**
 * 发送 GET 请求
 * @param {string} url 请求地址，相对于服务根的相对路径
 * @param {*} params 请求参数
 * @returns axios 请求结果，可以用于后续处理
 */
export function doGet(url, params) {
	return axios({
		method: "get",
		url: url,
		params: params,
		dataType: "json"
	})
}

/**
 * 发送 POST 请求
 * @param {string} url 请求地址，相对于服务根的相对路径
 * @param {json} data 携带数据
 * @returns axios 请求结果，可以用于后续处理
 */
export function doPost(url, data) {
	return axios({
		method: "post",
		url: url,
		data: data,
		dataType: "json"
	})
}

/**
 * 发送 PUT 请求
 * @param {string} url 请求地址，相对于服务根的相对路径
 * @param {json} data 携带数据
 * @returns axios 请求结果，可以用于后续处理
 */
export function doPut(url, data) {
	return axios({
		method: "put",
		url: url,
		data: data,
		dataType: "json"
	})
}

/**
 * 发送 DELETE 请求
 * @param {string} url 请求地址，相对于服务根的相对路径
 * @param {*} params 请求参数
 * @returns axios 请求结果，可以用于后续处理
 */
export function doDelete(url, params) {
	return axios({
		method: "delete",
		url: url,
		params: params,
		dataType: "json"
	})
}