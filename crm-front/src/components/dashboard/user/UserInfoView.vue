<template>
	<el-descriptions
		title="用户详细信息"
		:column="3"
		border
	>
		<template #extra>
			<el-button type="danger" @click="router.back">返回</el-button>
		</template>

		<el-descriptions-item :width="150" :span="3">
			<template #label>
				<el-icon><Document /></el-icon> 用户 ID
			</template>
			{{ id }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><user /></el-icon> 姓名
			</template>
			{{ name }}
		</el-descriptions-item>

		<el-descriptions-item align="center" :span="2">
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

		<el-descriptions-item align="center" :span="2">
			<template #label>
				<el-icon><Message /></el-icon> 邮箱
			</template>
			{{ email }}
		</el-descriptions-item>

		<el-descriptions-item :span="3">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 账户已启用
			</template>
			{{ accountEnabled ? '是' : '否' }}
		</el-descriptions-item>

		<el-descriptions-item :span="3">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 账户未过期
			</template>
			{{ accountNoExpired ? '是' : '否' }}
		</el-descriptions-item>
		
		<el-descriptions-item :span="3">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 账户未锁定
			</template>
			{{ accountNoLocked ? '是' : '否' }}
		</el-descriptions-item>
		
		<el-descriptions-item :span="3">
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

		<el-descriptions-item align="center" :span="2">
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

		<el-descriptions-item align="center" :span="2">
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

// 路由器
const router = useRouter()
// 路由
const route = useRoute()
// 用户 id
const id = ref(1)
// 用户名称
const name = ref("")
// 登录名
const loginActno = ref("")
// 手机
const phone = ref("")
// 邮箱
const email = ref("")
// 创建者 id
const createBy = ref("")
// 创建人
const createTime = ref("")
// 修改者 id
const editBy = ref("")
// 修改人
const editTime = ref("")
// 最后登录信息
const lastLoginTime = ref("")
// 账户是否启用
const accountEnabled = ref(true)
// 账户是否过期
const accountNoExpired = ref(true)
// 账户是否锁定
const accountNoLocked = ref(true)
// 凭证是否过期
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

/* == 钩子函数 == */

onMounted(() => {
	getUser(route.params.loginAct)
})
</script>

<style scope>

</style>