<template>
	<el-button type="primary" @click="showAddDialog">添加用户</el-button>
	<el-button type="danger" @click="bulkDelte">删除所选</el-button>

  	<br><br>

	<!-- 用户展示表格 -->
	<el-table 
		:data="users" 
		style="width: 100%" 
		stripe
		@selection-change="handleSelectionChange"
	>
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

				<el-button type="success" @click="showEditDialog(scope.row.loginAct)">修改</el-button>

				<el-button type="danger" 
					:disabled="getLoginId() === scope.row.id"
					@click="deleteUser(scope.row.id)"
				>删除</el-button>
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

  	<!-- 用户信息对话框 -->
	<!-- 新增与修改用户共用这个对话框 -->
	<el-dialog
		v-model="userDialogVisable"
		:title="editMode ? '修改用户' : '新增用户'"
		draggable
	>
  		<!-- 用户表单 -->
		<el-form 
			:model="userForm" 
			label-width="auto" 
			style="padding: auto; margin: 20px;"
  			:rules="addRules"
			ref="userInfoForm"
		>
			<el-form-item label="登录账户名" prop="loginAct">
				<el-input v-model="userForm.loginAct" />
			</el-form-item>
			<el-form-item :label="editMode ? '登录密码（留空表示不进行修改）' : '登录密码'" prop="loginPwd">
				<el-input v-model="userForm.loginPwd" type="password"/>
			</el-form-item>
			<el-form-item label="确认密码" prop="loginPwdCheck">
				<el-input v-model="userForm.loginPwdCheck" type="password"/>
			</el-form-item>
			<el-form-item label="姓名" prop="name">
				<el-input v-model="userForm.name" />
			</el-form-item>
			<el-form-item label="电话" prop="phone">
				<el-input v-model="userForm.phone" />
			</el-form-item>
			<el-form-item label="邮箱" prop="email">
				<el-input v-model="userForm.email" />
			</el-form-item>
			<el-form-item label="账户是否启用" prop="accountEnabled">
				<el-select v-model="userForm.accountEnabled" placeholder="请选择启用状态" >
					<el-option label="账户已启用" :value="true"></el-option>
					<el-option label="账户未启用" :value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="账户是否锁定" prop="accountNoLocked">
				<el-select v-model="userForm.accountNoLocked" placeholder="请选择锁定状态">
					<el-option label="账户未锁定" :value="true"></el-option>
					<el-option label="账户已锁定" :value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="账户是否过期" prop="accountNoExpired">
				<el-select v-model="userForm.accountNoExpired" placeholder="请选择过期状态">
					<el-option label="账户未过期" :value="true"></el-option>
					<el-option label="账户已过期" :value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="凭证是否过期" prop="credentialsNoExpired">
				<el-select v-model="userForm.credentialsNoExpired" placeholder="请选择凭证状态">
					<el-option label="凭证未过期" :value="true"></el-option>
					<el-option label="凭证已过期" :value="false"></el-option>
				</el-select> 
			</el-form-item>
		</el-form>

		<template #footer>
			<div class="dialog-footer">
				<el-button @click="userDialogVisable = false">取消</el-button>
				<el-button type="primary" @click="addUser" v-if="!editMode">确认提交</el-button>
				<el-button type="primary" @click="editUser" v-if="editMode">确认修改</el-button>
			</div>
		</template>
  </el-dialog>

</template>

<script setup>
import { inject, onMounted, reactive, ref } from 'vue';
import { doDelete, doGet, doPost, doPut } from '../../../http/httpRequestUtils';
import { useRouter } from 'vue-router';
import { messageConfirm, messageTip } from '../../../utils/utils';

/* == 数据 == */

// 用户信息列表
const users = ref([])
// 数据总数
const totalCount = ref(0)
// 当前开始行
const startRow = ref(1)
// 当前选择 id
let selectedIds = []

// 路由器
const router = useRouter()

// 对话框是否可见
const userDialogVisable = ref(false)
// 用户表单信息
const userForm = reactive({})
// 用户表单对象
const userInfoForm = ref()
// 编辑模式
const editMode = ref(false)
// 检测规则
const addRules = reactive({
	// 登录用户的校验规则
	loginAct: [
		{ required: true, message: '请输入登录用户名称', trigger: 'blur' },
		{ validator: validateLoginActExist, trigger: 'blur'}
	],
	// 登录密码的校验规则
	loginPwd: [{ validator: validatePassword, trigger: 'blur' }],
	// 确认密码的校验规则
	loginPwdCheck: [{ validator: validateLoginPwdCheck, trigger: 'blur' }],
	// 用户名
	name: [
		{ required: true, message: '请输入登录用户名称', trigger: 'blur' }
	],
	// 电话
	phone: [
		{ required: true, message: '请输入电话', trigger: 'blur' },
		{
			pattern: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,
			message: '请输入正确的电话格式',
			trigger: 'blur'
		}
	],
	// 邮箱
	email: [
		{ required: true, message: '请输入邮箱', trigger: 'blur' },
		{
			pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
			message: '请输入正确的邮箱格式',
			trigger: 'blur'
		}
	],
	accountEnabled: [ { required: true, message: '请指定账户是否启用', trigger: 'blur' } ],
	accountNoLocked: [ { required: true, message: '请指定账户是否锁定', trigger: 'blur' } ],
	accountNoExpired: [ { required: true, message: '请指定账户是否过期', trigger: 'blur' } ],
	credentialsNoExpired: [ { required: true, message: '请指定凭据是否过期', trigger: 'blur' } ],
})


/* == 函数 == */

// 重载函数
const reload = inject('reload')
// 获取当前登录用户 id
const getLoginId = inject('getLoginId')

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
 * 跳转至用户详情
 * @param loginAct 账户名称 
 */
function toUserInfo(loginAct) {
	router.push('/dashboard/user/' + loginAct)
}

/**
 * 检测密码是否正确
 * @param rule 
 * @param value 
 * @param callback 
 */
function validatePassword(rule, value, callback) {
	if (!editMode.value && !value && value === '') {
		callback(new Error('密码不能为空'))
	} else if (value && (value.length < 6 || value.length > 16)) {
		callback(new Error('密码长度应该在 6 - 16 位之间'))
	} else {
		callback()
	}
}

/**
 * 检测确认密码是否正确
 * @param rule 
 * @param value 
 * @param callback 
 */
function validateLoginPwdCheck(rule, value, callback) {
	if (!editMode.value && !value && value === '') {
		callback(new Error("请输入确认密码"))
	} else if (value !== userForm.loginPwd) {
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
	// 如果是编辑模式 而且没有改名 不进行检测
	if (editMode.value && value === userForm.loginActOld) {
		callback()
	} else {
		doGet('/api/user/checkAct/' + value).then(response => {
			if (response.data.data) {
				callback(new Error("用户名已经存在"))
			} else {
				callback()
			}
		})
	}
}

/**
 * 显示新增对话框
 * 重置当前对话框的值
 */
function showAddDialog() {
	userForm.loginAct = ''
	userForm.loginPwd = ''
	userForm.loginPwdCheck = ''
	userForm.name = ''
	userForm.phone = ''
	userForm.email = ''
	userForm.accountEnabled = true
	userForm.accountNoLocked = true
	userForm.accountNoExpired = true
	userForm.credentialsNoExpired = true

	editMode.value = false
	userDialogVisable.value = true
}

/**
 * 显示修改对话框
 * @param loginAct 对应的用户登录名
 */
function showEditDialog(loginAct) {
	doGet('/api/user/' + loginAct, {}).then(response => {
		if (response.data.code === 200) {

			userForm.id = response.data.data.id
			userForm.loginAct = response.data.data.loginAct
			userForm.loginActOld = response.data.data.loginAct
			userForm.name = response.data.data.name
			userForm.phone = response.data.data.phone
			userForm.email = response.data.data.email
			userForm.accountEnabled = response.data.data.accountEnabled
			userForm.accountNoLocked = response.data.data.accountNoLocked
			userForm.accountNoExpired = response.data.data.accountNoExpired
			userForm.credentialsNoExpired = response.data.data.credentialsNoExpired

			editMode.value = true
			userDialogVisable.value = true
		}
	})
}

/**
 * 发送请求新增用户
 */
function addUser() {
	userInfoForm.value.validate((isValid) => {
		if (isValid) {

			let fromdata = new FormData()

			fromdata.append("loginAct", userForm.loginAct)
			fromdata.append("loginPwd", userForm.loginPwd)
			fromdata.append("name", userForm.name)
			fromdata.append("phone", userForm.phone)
			fromdata.append("email", userForm.email)
			fromdata.append("accountEnabled", userForm.accountEnabled)
			fromdata.append("accountNoLocked", userForm.accountNoLocked)
			fromdata.append("accountNoExpired", userForm.accountNoExpired)
			fromdata.append("credentialsNoExpired", userForm.credentialsNoExpired)


			doPost('/api/user', fromdata).then(response => {
				if (response.data.code === 200) {
					console.log(response);
					messageTip("新增成功", "success")
					reload()
				} else {
					messageTip("添加出现错误，请稍后重试", "error")
				}
			})
		}
	})
}

/**
 * 发送请求修改用户
 */
function editUser() {
	userInfoForm.value.validate((isValid) => {
		if (isValid) {

			let fromdata = new FormData()

			fromdata.append("id", userForm.id)
			fromdata.append("loginAct", userForm.loginAct)
			fromdata.append("loginPwd", userForm.loginPwd)
			fromdata.append("name", userForm.name)
			fromdata.append("phone", userForm.phone)
			fromdata.append("email", userForm.email)
			fromdata.append("accountEnabled", userForm.accountEnabled)
			fromdata.append("accountNoLocked", userForm.accountNoLocked)
			fromdata.append("accountNoExpired", userForm.accountNoExpired)
			fromdata.append("credentialsNoExpired", userForm.credentialsNoExpired)

			doPut('/api/user', fromdata).then(response => {
				if (response.data.code === 200) {
					messageTip("修改成功", "success")
					reload()
				} else {
					messageTip("修改出现错误，请稍后重试", "error")
				}
			})
		}
	})
}

/**
 * 删除指定 id 的用户
 * @param id 用户 id
 */
function deleteUser(id) {
	console.log(id);
	messageConfirm(
		"确认删除用户吗",
		"删除警告",
		"warning",
		() => {
			doDelete('/api/user/' + id, {}).then(response => {
				if (response.data.code === 200) {
					messageTip("删除成功", "success")
					reload()
				} else {
					messageTip("删除出现问题: " + response.data.msg, "warning")
				}
			})
		},
		() => {
			messageTip("取消删除", "info")
		}
	)
}

/**
 * 批量删除
 */
function bulkDelte() {
	if (selectedIds.length <= 0) {
		messageTip("您还没有选择数据", "warning")
	} else if (selectedIds.indexOf(getLoginId()) != -1) {
		messageTip("不能选择当前登录用户", "error")
	} else { 
		messageConfirm(
			"确认要删除这些数据吗?",
			"批量删除确认",
			"warning",
			() => {
				doDelete('/api/user/bulk/' + selectedIds.join("-"), {}).then(response => {
					if (response.data.code === 200) {
						messageTip("批量删除成功", "success")
						reload()
					} else {
						messageTip("批量删除出现问题: " + response.data.msg, "warning")
					}
				})
			},
			() => {
				messageTip("批量删除取消", "info")
			}
		)
	}
}

/**
 * 当选择项修改时触发的函数
 */
function handleSelectionChange(selectedItems) {
	selectedIds = []
	selectedItems.forEach(selceted => {
		selectedIds.push(selceted.id)
	});
}

/* == 钩子函数 == */

onMounted(() => {
	queryUsers(1)
})
</script>

<style scoped>

</style>