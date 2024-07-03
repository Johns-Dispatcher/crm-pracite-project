<template>
	<!-- 筛选数据表单 -->
	<el-form 
		:inline="true" 
		:model="activityForm" 
		:rules="rules"
		ref="activityFilterForm"
	>
		<el-form-item label="负责人">
			<el-select 
				v-model="activityForm.ownerId" 
				placeholder="请选择负责人"
				clearable
				style="width: 150px;"
				@click="loadOwner"
			>
				<el-option 
					v-for="owner in ownerList"
					:key="owner.ownerId"
					:label="owner.owner"
					:value="owner.ownerId"
				/>
			</el-select>
		</el-form-item>

		<el-form-item label="活动名称">
			<el-input v-model="activityForm.name" placeholder="请输入活动名称" clearable/>
		</el-form-item>

		<el-form-item label="活动时间">
			<el-date-picker
				v-model="activityForm.time"
				type="datetimerange"
				start-placeholder="开始时间"
				end-placeholder="结束时间"
				format="YYYY-MM-DD HH:mm:ss"
				value-format="YYYY-MM-DD HH:mm:ss"
				date-format="YYYY/MM/DD ddd"
				time-format="A hh:mm:ss"
			/>
		</el-form-item>

		 <el-form-item label="活动预算" prop="cost">
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
				value-format="YYYY-MM-DD HH:mm:ss"
			/>
		</el-form-item>

		<el-form-item>
			<el-button type="primary" plain @click="onSearch(1)">搜索</el-button>
			<el-button type="danger" plain @click="onReset" >重置</el-button>
		</el-form-item>
	</el-form>

	<el-button type="primary" @click="addActivity">录入市场活动信息</el-button>
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
				<el-button type="success" @click="editActivity(scope.row.id)">修改</el-button>
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
		@current-change="changePage"
	/>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { doGet, doPost } from '../../../http/httpRequestUtils';
import { messageTip } from '../../../utils/utils';
import { useRouter } from 'vue-router';

/* == 数据 == */

const activityForm = ref({})
const activities = ref([])
const totalCount = ref(0)
const startRow = ref(1)
const ownerList = ref([])
const rules = {
	cost: [ { pattern: /^[0-9]+(.[0-9]{1,2})?$/, trigger: 'blur', message: '请输入两位小数' } ]
}
const activityFilterForm = ref()
const router = useRouter()

/* == 函数 == */

function changePage(currentPage) {
	if (Object.keys(activityForm.value).length === 0) {
		queryActivies(currentPage)
	} else {
		onSearch(currentPage)
	}
}

function queryActivies(currentPage) {
	doGet('/api/activity/page/' + currentPage, {}).then(response => {
		if (response.data.code === 200) {
			updateActivityList(response.data.data)
		}
	})
}

function loadOwner() {
	doGet('/api/activity/owner', {}).then(response => {
		if (response.data.code === 200) {
			ownerList.value = response.data.data
		}
	})
}

function onSearch(currentPage) {
	activityFilterForm.value.validate(isValidated => {
		if (isValidated) {
			console.log(activityForm.value);
			if (Object.keys(activityForm.value).length === 0) {
				messageTip("您还没有选择筛选条件", "info")
			} else { 
				doPost("/api/activity/search", {
					ownerId: activityForm.value.ownerId ? activityForm.value.ownerId : null,
					name: activityForm.value.name ? activityForm.value.name : null,
					startTime: activityForm.value.time ? activityForm.value.time[0] : null,
					endTime: activityForm.value.time ? activityForm.value.time[1] : null,
					cost: activityForm.value.cost ? activityForm.value.cost : null,
					createTime: activityForm.value.createTime ? activityForm.value.createTime : null,
					current: currentPage,
				}).then(response => {
					if (response.data.code === 200) {
						updateActivityList(response.data.data)
					}
				})
			}
		}
	})
}

function updateActivityList(data) {
	activities.value = data.list
	totalCount.value = data.total
	startRow.value = data.startRow
}

function onReset() {
	activityForm.value = {}
	queryActivies(1)
}

function addActivity() {
	router.push('/dashboard/activity/add')
}

function editActivity(id) {
	router.push('/dashboard/activity/edit/' + id)
}

/* == 钩子函数 == */

onMounted(() => {
	queryActivies(1)
})

</script>

<style scope>

</style>