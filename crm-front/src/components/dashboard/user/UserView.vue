<template>
	<el-button type="primary" @click="addUserDialogVisible = true">添加用户</el-button>
	<el-button type="danger">删除所选</el-button>

  	<br><br>

	<!-- 用户展示表格 -->
	<el-table :data="users" style="width: 100%" stripe >
		<el-table-column type="selection" width="50px" />

		<el-table-column type="index" width="50px" :index="startRow"/>

		<el-table-column prop="loginAct" label="账号名" show-overflow-tooltip/>

		<el-table-column prop="name" label="姓名" show-overflow-tooltip />

		<el-table-column prop="phone" label="电话" show-overflow-tooltip />

		<el-table-column prop="email" label="邮箱" show-overflow-tooltip />

		<el-table-column prop="createTime" label="创建时间" show-overflow-tooltip />

		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="primary" @click="toUserInfo(scope.row.loginAct)">详情</el-button>
				<el-button type="primary">修改</el-button>
				<el-button type="danger">删除</el-button>
			</template>
		</el-table-column>
	</el-table>

	<br><br>

	<!-- 分页 -->
	<el-pagination
		layout="prev, pager, next"
		:page-size="10"
		:total="totalCount"
		@current-change="queryUsers"
	/>

  	<!-- 新增用户的对话框 -->
	<el-dialog
		v-model="addUserDialogVisible"
		title="新增用户"
		draggable
	>
  		<!-- 新增用户表单 -->
		<el-form 
			:model="newUser" 
			label-width="auto" 
			style="padding: auto; margin: 20px;"
  			:rules="addRules"
			ref="addForm"
		>
			<el-form-item label="登录账户名" prop="loginAct">
				<el-input v-model="newUser.loginAct" />
			</el-form-item>
			<el-form-item label="登录密码" prop="loginPwd">
				<el-input v-model="newUser.loginPwd" type="password"/>
			</el-form-item>
			<el-form-item label="确认密码" prop="loginPwdCheck">
				<el-input v-model="newUser.loginPwdCheck" type="password"/>
			</el-form-item>
			<el-form-item label="姓名" prop="name">
				<el-input v-model="newUser.name" />
			</el-form-item>
			<el-form-item label="电话" prop="phone">
				<el-input v-model="newUser.phone" />
			</el-form-item>
			<el-form-item label="邮箱" prop="email">
				<el-input v-model="newUser.email" />
			</el-form-item>
			<el-form-item label="账户是否启用" prop="accountEnabled">
				<el-select v-model="newUser.accountEnabled" placeholder="请选择启用状态" >
					<el-option label="账户已启用" value="true"></el-option>
					<el-option label="账户未启用" value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="账户是否锁定" prop="accountNoLocked">
				<el-select v-model="newUser.accountNoLocked" placeholder="请选择锁定状态">
					<el-option label="账户未锁定" value="true"></el-option>
					<el-option label="账户已锁定" value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="账户是否过期" prop="accountNoExpired">
				<el-select v-model="newUser.accountNoExpired" placeholder="请选择过期状态">
					<el-option label="账户未过期" value="true"></el-option>
					<el-option label="账户已过期" value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="凭证是否过期" prop="credentialsNoExpired">
				<el-select v-model="newUser.credentialsNoExpired" placeholder="请选择凭证状态">
					<el-option label="凭证未过期" value="true"></el-option>
					<el-option label="凭证已过期" value="false"></el-option>
				</el-select> 
			</el-form-item>
		</el-form>

		<template #footer>
			<div class="dialog-footer">
				<el-button @click="addUserDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="addUser">确认提交</el-button>
			</div>
		</template>
  </el-dialog>

</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { doGet, doPost } from '../../../http/httpRequestUtils';
import { useRouter } from 'vue-router';
import { messageTip } from '../../../utils/utils';

/* == 数据 == */

const users = ref([])
const totalCount = ref(0)
const startRow = ref(1)
const router = useRouter()
const addUserDialogVisible = ref(false)
const newUser = reactive({})
const addRules = reactive({
	// 登录用户的校验规则
	loginAct: [
		{ required: true, message: '请输入登录用户名称', trigger: 'blur' },
		{ validator: validateLoginActExist, trigger: 'blur'}
	],
	// 登录密码的校验规则
	loginPwd: [
		{ required: true, message: '请输入登录密码', trigger: 'blur' },
		{ min: 6, max: 16, message: '密码需要在 6 ~ 16 位之间', trigger: 'blur' }
	],
	loginPwdCheck: [{ validator: validateLoginPwdCheck, trigger: 'blur', required: true }],
	name: [ { required: true, message: '请输入登录用户名称', trigger: 'blur' } ],
	phone: [ { required: true, message: '请输入登录用户名称', trigger: 'blur' } ],
	email: [ { required: true, message: '请输入登录用户名称', trigger: 'blur' } ],
})
const addForm = ref()

/* == 函数 == */

/**
 * 查询分页数据
 * @param current 查询页码
 */
function queryUsers(current) {
	doGet("/api/user/page/" + current, {}).then(response => {
		if (response.data.code === 200) {
			users.value = response.data.data.list
			totalCount.value = response.data.data.total
			startRow.value = response.data.data.startRow
		}
	})
}

/**
 * 获取用户信息
 * @param loginAct 账户名称
 */
function toUserInfo(loginAct) {
	console.log(loginAct);
	router.push('/dashboard/user/' + loginAct)
}

/**
 * 检测确认密码是否正确
 * @param rule 
 * @param value 
 * @param callback 
 */
function validateLoginPwdCheck(rule, value, callback) {
	if (!value) {
		callback(new Error("请输入确认密码"))
	} else if (value !== newUser.loginPwd) {
		callback(new Error("两次输入密码不一致"))
	} else {
		callback()
	}
}

/**
 * 发送请求检查后端是否存在对应用户名
 * @param rule 
 * @param value 
 * @param callback 
 */
function validateLoginActExist(rule, value, callback) {
	doGet('/api/user/checkAct/' + value).then(response => {
		if (response.data.data) {
			callback(new Error("用户名已经存在"))
		} else {
			callback()
		}
	})
}

/**
 * 发送请求新增用户
 */
function addUser() {
	addForm.value.validate((isValid) => {
		console.log(isValid);
		if (isValid) {
			doPost('/api/user/', newUser).then(response => {
				if (response.data.code === 200) {
					messageTip("新增成功", "success")
					newUser = {}
				} else {
					messageTip("添加出现错误，请稍后重试", "error")
					newUser = {}
				}
			})
			addUserDialogVisible.value = false
		}
	})
}

/* == 钩子函数 == */

onMounted(() => {
	queryUsers(1)
})
</script>

<style scoped>

</style>