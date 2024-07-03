<template>
	<!-- 筛选数据表单 -->
	<el-form 
		:inline="true" 
		:model="searchData" 
		:rules="rules"
		ref="searchForm"
	>
		<el-form-item label="负责人">
			<el-select 
				v-model="searchData.ownerId" 
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
			<el-input v-model="searchData.name" placeholder="请输入活动名称" clearable/>
		</el-form-item>

		<el-form-item label="活动时间">
			<el-date-picker
				v-model="searchData.time"
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
			<el-input v-model="searchData.cost" placeholder="请输入活动预算" clearable/>
		</el-form-item>

		<el-form-item label="创建时间">
			<el-date-picker
				v-model="searchData.createTime"
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
				<el-button type="primary" @click="viewActivity(scope.row.id)">详情</el-button>
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

// 查询的活动信息列表
const activities = ref([])
// 查询总数
const totalCount = ref(0)
// 开始行
const startRow = ref(1)
// 负责人列表
const ownerList = ref([])
// 验证规则
const rules = {
	cost: [ { pattern: /^[0-9]+(.[0-9]{1,2})?$/, trigger: 'blur', message: '请输入两位小数' } ]
}

// 搜索表单
const searchForm = ref()
// 搜索数据
const searchData = ref({})

// 路由器
const router = useRouter()

/* == 函数 == */

/**
 * 翻页，修改页数后触发
 * @param {int} currentPage 跳转页码
 */
function changePage(currentPage) {
	// 没有筛选数据就直接查
	if (Object.keys(searchData.value).length === 0) {
		queryActivies(currentPage)
	} else {
		onSearch(currentPage)
	}
}

/**
 * 查询当前页信息
 * @param {int} currentPage 跳转页码
 */
function queryActivies(currentPage) {
	doGet('/api/activity/page/' + currentPage, {}).then(response => {
		if (response.data.code === 200) {
			updateActivityList(response.data.data)
		}
	})
}
/**
 * 进行搜索查询
 * @param {int} currentPage 跳转页码
 */
function onSearch(currentPage) {
	searchForm.value.validate(isValidated => {
		if (isValidated) {
			console.log(searchData.value);
			if (Object.keys(searchData.value).length === 0) {
				messageTip("您还没有选择筛选条件", "info")
			} else { 
				doPost("/api/activity/search", {
					ownerId: searchData.value.ownerId ? searchData.value.ownerId : null,
					name: searchData.value.name ? searchData.value.name : null,
					startTime: searchData.value.time ? searchData.value.time[0] : null,
					endTime: searchData.value.time ? searchData.value.time[1] : null,
					cost: searchData.value.cost ? searchData.value.cost : null,
					createTime: searchData.value.createTime ? searchData.value.createTime : null,
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
/**
 * 重置搜索表单
 */
function onReset() {
	searchData.value = {}
	queryActivies(1)
}
/**
 * 更新活动信息列表
 * @param data 响应的数据结果
 */
function updateActivityList(data) {
	activities.value = data.list
	totalCount.value = data.total
	startRow.value = data.startRow
}



/**
 * 获取负责人信息
 */
function loadOwner() {
	doGet('/api/activity/owner', {}).then(response => {
		if (response.data.code === 200) {
			ownerList.value = response.data.data
		}
	})
}

/**
 * 跳转到新增活动页面
 */
function addActivity() {
	router.push('/dashboard/activity/add')
}
/**
 * 跳转至修改活动页面
 */
function editActivity(id) {
	router.push('/dashboard/activity/edit/' + id)
}

function viewActivity(id) {
	router.push('/dashboard/activity/view/' + id)
}

/* == 钩子函数 == */

onMounted(() => {
	queryActivies(1)
})

</script>

<style scope>

</style>