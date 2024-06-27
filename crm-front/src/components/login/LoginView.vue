<template>
	<div class="bg">
		<el-container>
			<el-aside></el-aside>
			<el-main>
				<h1>欢迎使用 CRM 客户管理系统</h1>
				<!-- 登录表单 -->
				<el-form 
					ref="loginForm"
					:model="user"
					label-width="auto"
					style="max-width: 400px"
					:rules="loginRules"
				>
					<el-form-item label="用户名" prop="loginAct"><el-input v-model="user.loginAct"/></el-form-item>
					<el-form-item label="密码" prop="loginPwd"><el-input v-model="user.loginPwd" type="password"/></el-form-item>
					<el-form-item label=" ">
						<el-row :gutter="10" justify="space-between">
							<el-col :span="6"><el-button type="primary" @click="login">登录</el-button></el-col>
							<el-col :span="6"><el-checkbox label="免登录" name="rememberMe" v-model="user.rememberMe"/></el-col>
						</el-row>
					</el-form-item>
				</el-form>
			</el-main>
		</el-container>
	</div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { doPost } from '../../http/httpRequestUtils';
import { getTokenKey, messageTip } from '../../utils/utils'
import { useRouter } from 'vue-router'

// 绑定登录数据
const user = reactive({})

// 定义表单规则
const loginRules = reactive({
	// 登录用户的校验规则
	loginAct: [ { required: true, message: '请输入登录用户名称', trigger: 'blur' } ],
	// 登录密码的校验规则
	loginPwd: [
		{ required: true, message: '请输入登录密码', trigger: 'blur' },
		{ min: 6, max: 16, message: '密码需要在 6 ~ 16 位之间', trigger: 'blur' }
	]
})

const loginForm = ref()

const router = useRouter()

function login() {
	// 执行定义好的验证规则
	loginForm.value.validate((isValid) => {
		// 使用 FormData 封装数据
		let fromData = new FormData();

		fromData.append("loginAct", user.loginAct)
		fromData.append("loginPwd", user.loginPwd)
		fromData.append("rememberMe", user.rememberMe)

		// 验证后调用 传入验证结果
		if (isValid) {
			doPost("/api/login", fromData).then(
				response => {
					if (response.data.code === 200) {
						// 登录成功
						messageTip("登录成功", "success")
						// 存储后端传来的 JWT 串
						if (user.rememberMe) {
							window.localStorage.setItem(getTokenKey(), response.data.data)
						} else {
							window.sessionStorage.setItem(getTokenKey(), response.data.data)
						}
						// 进行路由跳转
						router.push('/dashboard')
					} else {
						// 登录失败
						messageTip("登录失败，请检查登录信息", "error")
					}
				}
			)
		}
	})
}
</script>

<style scoped>
.el-aside {
	width: 60%;
}
.el-main {
	backdrop-filter: blur(6px);
	background: rgba(180, 180, 180, 0.6);
	height: calc(100vh);
	padding: 50px;
}
.el-form {
	margin: auto;
}
.el-button {
	width: 80px;
}
.bg {
	background-image: url("../../assets/background.png");
	/* 等比例缩放背景图片 */
	background-size: cover;
	/* 防止背景图片重复平铺 */
	background-repeat: no-repeat; 
	/* 将背景图片水平垂直居中 */
	background-position: center; 
}
h1 {
	margin: 50px auto;
	text-align: center;
}
</style>