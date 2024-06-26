// 封装 AJAX 请求方式
import axios from "axios";

axios.defaults.baseURL = "http://localhost:8009"

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