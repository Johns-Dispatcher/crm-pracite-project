<template>
	<el-button type="primary">添加用户</el-button>
	<el-button type="danger">删除所选</el-button>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column type="selection" width="55" />

		<el-table-column type="index" width="50" />

		<el-table-column property="loginAct" label="账号名" width="120" />

		<el-table-column property="name" label="姓名" />

		<el-table-column property="phone" label="电话" />

		<el-table-column property="email" label="邮箱" />

		<el-table-column property="createTime" label="创建时间" />
  </el-table>
</template>

<script setup>
import { onMounted } from 'vue';
import { doGet } from '../../../http/httpRequestUtils';

const users = undefined

onMounted(() => {
	doGet("/api/user", {}).then(response => {
		console.log(response)
	})
})
</script>

<style scoped>

</style>