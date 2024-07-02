<template>
	<!-- 筛选数据表单 -->
	<el-form 
		:inline="true" 
		:model="activityForm" 
	>
		<el-form-item label="负责人">
			<el-select 
				v-model="activityForm.ownerId" 
				placeholder="请选择负责人"
				clearable
				style="width: 150px;"
			>
				<el-option label="111111111111111" value="111111111111"/>
			</el-select>
		</el-form-item>

		<el-form-item label="活动名称">
			<el-input v-model="activityForm.user" placeholder="请输入活动名称" clearable/>
		</el-form-item>

		<el-form-item label="活动时间">
			<el-date-picker
				v-model="activityForm.time"
				type="datetimerange"
				start-placeholder="开始时间"
				end-placeholder="结束时间"
				format="YYYY-MM-DD HH:mm:ss"
				date-format="YYYY/MM/DD ddd"
				time-format="A hh:mm:ss"
			/>
		</el-form-item>

		 <el-form-item label="活动预算">
			<el-input v-model="activityForm.cost" placeholder="请输入活动预算" clearable/>
		</el-form-item>

		<el-form-item label="创建时间">
			<el-date-picker
				v-model="activityForm.createTime"
				type="datetime"
				placeholder="选择创建时间"
				format="YYYY-MM-DD HH:mm:ss"
				date-format="MMM DD, YYYY"
				time-format="HH:mm"
			/>
		</el-form-item>

		<el-form-item>
			<el-button type="primary" plain @click="onSearch">搜索</el-button>
			<el-button type="danger" plain @click="onReset" >重置</el-button>
		</el-form-item>
	</el-form>

	<el-button type="primary">录入市场活动信息</el-button>
	<el-button type="danger">批量删除市场活动信息</el-button>

	<br/><br/>

	<!-- 活动数据展示表格 -->
	<el-table 
		:data="activities" 
		style="width: 100%" 
		stripe
	>
		<el-table-column type="selection" width="50px" />

		<el-table-column type="index" width="50px" :index="startRow"/>

		<el-table-column prop="name" width="150px" label="活动名" show-overflow-tooltip />

		<el-table-column prop="owner" width="150px" label="活动负责人" show-overflow-tooltip />

		<el-table-column prop="startTime" label="活动开始时间" show-overflow-tooltip />

		<el-table-column prop="endTime" label="活动结束时间" show-overflow-tooltip />

		<el-table-column prop="cost" width="150px" label="活动预算" show-overflow-tooltip />

		<el-table-column prop="createTime" label="创建时间" show-overflow-tooltip />

		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="primary">详情</el-button>
				<el-button type="success">修改</el-button>
				<el-button type="danger">删除</el-button>
			</template>
		</el-table-column>
	</el-table>

	<br/>

	<!-- 分页 -->
	<el-pagination
		layout="prev, pager, next"
		:page-size="10"
		:total="totalCount"
		@current-change="queryActivies"
	/>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { doGet } from '../../../http/httpRequestUtils';

/* == 数据 == */

const activityForm = reactive({})
const activities = ref([])
const totalCount = ref(0)
const startRow = ref(1)

/* == 函数 == */

function queryActivies(currentPage) {
	doGet('/api/activity/page/' + currentPage, {}).then(response => {
		if (response.data.code === 200) {
			console.log(response);
			activities.value = response.data.data.list
			totalCount.value = response.data.data.total
			startRow.value = response.data.data.startRow
		}
	})
}

/* == 钩子函数 == */

onMounted(() => {
	queryActivies(1)
})

</script>

<style scope>

</style>