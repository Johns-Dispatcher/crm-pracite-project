<template>
	<el-button type="primary">添加用户</el-button>
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
			<el-button type="primary">详情</el-button>
			<el-button type="primary">修改</el-button>
			<el-button type="danger">删除</el-button>
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

</template>

<script setup>
import { onMounted, ref } from 'vue';
import { doGet } from '../../../http/httpRequestUtils';

/* == 数据 == */

const users = ref([])
const totalCount = ref(0)
const startRow = ref(1)

/* == 函数 == */

function queryUsers(current) {
	doGet("/api/user/page/" + current, {}).then(response => {
		console.log(response)
		if (response.data.code === 200) {
			users.value = response.data.data.list
			totalCount.value = response.data.data.total
			startRow.value = response.data.data.startRow
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