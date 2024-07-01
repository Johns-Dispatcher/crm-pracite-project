<template>
	<el-descriptions
		title="用户信息详细"
		:column="1"
		width="50"
		border
	>
		<template #extra>
			<el-button type="danger" @click="goBack">返回</el-button>
		</template>

		<el-descriptions-item width="5px">
			<template #label>
				<el-icon><Document /></el-icon> 用户ID
			</template>
			{{ id }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><user /></el-icon> 姓名
			</template>
			{{ name }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Avatar /></el-icon> 账户名称
			</template>
			{{ loginActno }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Phone /></el-icon> 手机
			</template>
			{{ phone }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Message /></el-icon> 邮箱
			</template>
			{{ email }}
		</el-descriptions-item>

		<el-descriptions-item >
			<template #label>
				<el-icon><InfoFilled /></el-icon> 账户已启用
			</template>
			{{ accountEnabled ? '是' : '否' }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><InfoFilled /></el-icon> 账户未过期
			</template>
			{{ accountNoExpired ? '是' : '否' }}
		</el-descriptions-item>
		
		<el-descriptions-item>
			<template #label>
				<el-icon><InfoFilled /></el-icon> 账户未锁定
			</template>
			{{ accountNoLocked ? '是' : '否' }}
		</el-descriptions-item>
		
		<el-descriptions-item>
			<template #label>
				<el-icon><InfoFilled /></el-icon> 凭证未过期
			</template>
			{{ credentialsNoExpired ? '是' : '否' }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Stamp /></el-icon> 创建者
			</template>
			{{ createBy }}
		</el-descriptions-item>

		<el-descriptions-item label-align="left" align="center">
			<template #label>
				<el-icon><Timer /></el-icon> 创建时间
			</template>
			{{ createTime }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Stamp /></el-icon> 修改者
			</template>
			{{ editBy }}
		</el-descriptions-item>

		<el-descriptions-item label-align="left" align="center">
			<template #label>
				<el-icon><Timer /></el-icon> 修改时间
			</template>
			{{ editTime }}
		</el-descriptions-item>

		<el-descriptions-item label-align="left" align="center">
			<template #label>
				<el-icon><Timer /></el-icon> 登录时间
			</template>
			{{ lastLoginTime }}
		</el-descriptions-item>

	</el-descriptions>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { doGet } from '../../../http/httpRequestUtils';

/* == 数据 == */

const router = useRouter()
const route = useRoute()
const id = ref(1)
const name = ref("")
const loginActno = ref("")
const phone = ref("")
const email = ref("")
const createBy = ref("")
const createTime = ref("")
const editBy = ref("")
const editTime = ref("")
const lastLoginTime = ref("")
const accountEnabled = ref(true)
const accountNoExpired = ref(true)
const accountNoLocked = ref(true)
const credentialsNoExpired = ref(true)

/* == 函数 == */

/**
 * 按照登录用户名向后端获取信息
 * @param loginAct 登录名称
 */
function getUser(loginAct) {
	doGet("/api/user/" + loginAct, {}).then(response => {
		if (response.data.code === 200) {
			id.value = response.data.data.id
			name.value = response.data.data.name
			loginActno.value = response.data.data.loginAct
			phone.value = response.data.data.phone
			email.value = response.data.data.email
			createBy.value = response.data.data.creatorName
			createTime.value = response.data.data.createTime
			editBy.value = response.data.data.editorName
			editTime.value = response.data.data.editTime
			lastLoginTime.value = response.data.data.lastLoginTime
			accountEnabled.value = response.data.data.accountEnabled
			accountNoExpired.value = response.data.data.accountNoExpired
			accountNoLocked.value = response.data.data.accountNoLocked
			credentialsNoExpired.value = response.data.data.credentialsNoExpired
		}
	})
}

/**
 * 返回上一级
 */
function goBack() {
	router.back()
}

/* == 钩子函数 == */

onMounted(() => {
	getUser(route.params.loginAct)
})
</script>

<style scope>

</style>