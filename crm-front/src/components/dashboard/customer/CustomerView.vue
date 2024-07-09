<template>

	<el-table 
		:data="customerList" 
		style="width: 95%; margin: auto;" 
		stripe
		@selection-change=""
	>
		<el-table-column type="selection" width="30" />

		<el-table-column label="序号" type="index" width="60" :index="startRow"/>

		<el-table-column prop="viewClue.owner" label="负责人" width="80" show-overflow-tooltip/>

		<el-table-column prop="viewClue.activity" label="所属活动" show-overflow-tooltip />

		<el-table-column prop="viewClue.fullName" label="姓名" width="60" show-overflow-tooltip>
			<template #default="scope">
				<el-link 
					type="primary" 
					:underline="false" 
					href="javacript:void(0);" 
					@click=""
				>
					{{ scope.row.viewClue.fullName }}
				</el-link>
			</template>
		</el-table-column>

		<el-table-column prop="viewClue.appellation" label="称呼" width="60" show-overflow-tooltip />

		<el-table-column prop="viewClue.phone" label="手机" show-overflow-tooltip />

		<el-table-column prop="viewClue.wechat" label="微信" show-overflow-tooltip />
		
		<el-table-column prop="viewClue.needLoan" label="是否贷款" width="80" show-overflow-tooltip />

		<el-table-column prop="viewClue.intentionState" label="意向状态" width="100" show-overflow-tooltip />
		
		<el-table-column prop="productName" label="意向产品" show-overflow-tooltip />
		
		<el-table-column prop="viewClue.state" label="线索状态" width="100" show-overflow-tooltip />
		
		<el-table-column prop="viewClue.source" label="线索来源" width="80" show-overflow-tooltip />
		
		<el-table-column prop="nextContactTime" label="下次联系时间" show-overflow-tooltip />

		<el-table-column label="操作" width="150">
			<template #default="scope">
				<el-button type="primary" @click="">详情</el-button>
			</template>
		</el-table-column>
	</el-table>

	<br><br>

	<!-- 分页 -->
	<el-pagination
		layout="prev, pager, next"
		:page-size="10"
		:total="totalCount"
		@current-change="loadCustomerList"
	/>

</template>

<script setup>
import { onMounted, ref } from 'vue';
import { doGet } from '../../../http/httpRequestUtils';

const customerList = ref([])
const totalCount = ref(0)
const startRow = ref(1)

function loadCustomerList(current) {
	doGet("/api/customer/page/" + current, {}).then(response => {
		if (response.data.code === 200) {
			customerList.value = response.data.data.list
			totalCount.value = response.data.data.total
			startRow.value = response.data.data.startRow
		}
	})
}

onMounted(() => {
	loadCustomerList(1)
})
</script>

<style>

</style>