<template>
	<el-button type="primary" @click="router.push('/dashboard/clue/add')">录入线索</el-button>
	<el-button type="success" @click="importFile">通过 Excel 批量导入线索</el-button>
	<el-button type="danger">批量删除</el-button>

  	<br><br>

	<el-table 
		:data="clueList" 
		style="width: 100%" 
		stripe
	>
		<el-table-column type="selection" width="30" />

		<el-table-column label="序号" type="index" width="60" :index="startRow"/>

		<el-table-column prop="owner" label="负责人" width="80" show-overflow-tooltip/>

		<el-table-column prop="activity" label="所属活动" show-overflow-tooltip />

		<el-table-column prop="fullName" label="姓名" width="60" show-overflow-tooltip />

		<el-table-column prop="appellation" label="称呼" width="60" show-overflow-tooltip />

		<el-table-column prop="phone" label="手机" show-overflow-tooltip />

		<el-table-column prop="wechat" label="微信" show-overflow-tooltip />
		
		<el-table-column prop="needLoan" label="是否贷款" width="80" show-overflow-tooltip />

		<el-table-column prop="intentionState" label="意向状态" width="100" show-overflow-tooltip />
		
		<el-table-column prop="productName" label="意向产品" show-overflow-tooltip />
		
		<el-table-column prop="state" label="线索状态" show-overflow-tooltip />
		
		<el-table-column prop="source" label="线索来源" width="80" show-overflow-tooltip />
		
		<el-table-column prop="nextContactTime" label="下次联系时间" show-overflow-tooltip />

		<el-table-column label="操作" width="250">
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
		@current-change="loadClues"
	/>

	<!-- 备注修改对话框 -->
	<el-dialog
		v-model="importDialogVisable"
		title="上传 Excel 文件"
		draggable
	>
		<el-upload
			ref="uploadRef"
			:http-request="uploadFile"
			:auto-upload="false"
		>
			<template #trigger>
				<el-button type="primary">选择 Excel 文件</el-button>
			</template>

			仅支持 .xls 或者 .xlsx 的文件

			<template #tip>
				<div style="margin-top: 15px; margin-bottom: 30px;">
					提示:
					<ul>
						<li>不要上传其他格式的文件</li>
						<li>Excel 文件的第一行将作为字段名称进行识别</li>
						<li>请确认文件大小不超过 50 MB</li>
						<li>日期请使用文本格式，格式为：yyyy-MM-dd</li>
						<li>携带时间的格式为：yyyy-MM-dd HH:mm:ss</li>
					</ul>
				</div>
			</template>
		</el-upload>

		<el-button type="primary" @click="uploadSubmit">提交文件</el-button>
		<el-button type="danger" plain @click="importDialogVisable = false">关闭</el-button>
  	</el-dialog>
</template>

<script setup>
import { onMounted, ref, inject } from 'vue';
import { doGet, doPost } from '../../../http/httpRequestUtils';
import { messageTip } from '../../../utils/utils';
import { useRouter } from 'vue-router';

const router = useRouter()

const clueList = ref([])
const totalCount = ref(0)
const startRow = ref(1)

const importDialogVisable = ref(false)
const uploadRef = ref()

const reload = inject('reload')

function loadClues(current) {
	doGet("/api/clue/page/" + current, {}).then(response => {
		if (response.data.code === 200) { 
			clueList.value = response.data.data.list
			totalCount.value = response.data.data.total
			startRow.value = response.data.data.startRow
		}
	})
}

function importFile() {
	importDialogVisable.value = true
}

function uploadFile(param) {
	let formData = new FormData()
	formData.append("file", param.file)

	doPost("/api/clue/importExcel", formData).then(response => {
		if (response.data.code === 200) {
			messageTip("导入成功", "success")
			uploadRef.value.clearFiles()
			reload()
		} else {
			messageTip("导入失败", "error")
		}
	})
}

function uploadSubmit() {
	if (uploadRef.value) { 
		uploadRef.value.submit()
	} else {
		messageTip("您还没有选择文件", "warning")
	}
}

onMounted(() => {
	loadClues(1)
})
</script>

<style>

</style>