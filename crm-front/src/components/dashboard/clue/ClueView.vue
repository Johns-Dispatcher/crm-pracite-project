<template>
	<el-button type="primary">录入线索</el-button>
	<el-button type="success">通过 Excel 批量导入线索</el-button>
	<el-button type="danger">批量删除</el-button>

  	<br><br>

	<el-table 
		:data="clueList" 
		style="width: 100%" 
		stripe
	>
		<el-table-column type="selection" width="50px" />

		<el-table-column label="序号" type="index" width="50px" :index="startRow"/>

		<el-table-column prop="loginAct" label="负责人" show-overflow-tooltip/>

		<el-table-column prop="name" label="所属活动" show-overflow-tooltip />

		<el-table-column prop="phone" label="姓名" show-overflow-tooltip />

		<el-table-column prop="email" label="称呼" show-overflow-tooltip />

		<el-table-column prop="createTime" label="手机" show-overflow-tooltip />

		<el-table-column prop="createTime" label="微信" show-overflow-tooltip />
		
		<el-table-column prop="createTime" label="是否贷款" show-overflow-tooltip />

		<el-table-column prop="createTime" label="意向状态" show-overflow-tooltip />
		
		<el-table-column prop="createTime" label="意向产品" show-overflow-tooltip />
		
		<el-table-column prop="createTime" label="线索状态" show-overflow-tooltip />
		
		<el-table-column prop="createTime" label="线索来源" show-overflow-tooltip />
		
		<el-table-column prop="createTime" label="下次联系时间" show-overflow-tooltip />

		<el-table-column label="操作" width="150">
			<template #default="scope">
				<el-button type="primary">详情</el-button>

				<el-button type="success">修改</el-button>

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
</template>

<script setup>
import { ref } from 'vue';

const clueList = ref([])
</script>

<style>

</style>