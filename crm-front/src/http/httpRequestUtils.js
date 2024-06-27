// 封装 AJAX 请求方式
import axios from "axios";
import { getTokenKey } from "../utils/utils";

axios.defaults.baseURL = "http://localhost:8009"

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
	return response;
}, function (error) {
	// 超出 2xx 范围的状态码都会触发该函数。
	// 对响应错误做点什么
	return Promise.reject(error);
});

// 发送 GET 请求
export function doGet(url, params) {
	return axios({
		method: "get",
		url: url,
		params: params,
		dataType: "json"
	})
}

// 发送 POST 请求
export function doPost(url, data) {
	return axios({
		method: "post",
		url: url,
		data: data,
		dataType: "json"
	})
}

// 发送 PUT 请求
export function doPut(url, data) {
	return axios({
		method: "put",
		url: url,
		data: data,
		dataType: "json"
	})
}

// 发送 DELETE 请求
export function doDelete(url, params) {
	return axios({
		method: "post",
		url: url,
		params: params,
		dataType: "json"
	})
}