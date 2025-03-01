<template>

	<el-button type="primary" @click="router.push('/dashboard/clue/add')">录入线索</el-button>
	<el-button type="success" @click="importFile">通过 Excel 批量导入线索</el-button>
	<el-button type="danger" @click="bulkDelete">批量删除</el-button>

  	<br><br>

	<!-- 线索展示表格 -->
	<el-table 
		:data="clueList" 
		style="width: 100%" 
		stripe
		@selection-change="updateSelected"
	>
		<el-table-column type="selection" width="30" />

		<el-table-column label="序号" type="index" width="60" :index="startRow"/>

		<el-table-column prop="owner" label="负责人" width="80" show-overflow-tooltip/>

		<el-table-column prop="activity" label="所属活动" show-overflow-tooltip />

		<el-table-column prop="fullName" label="姓名" width="60" show-overflow-tooltip>
			<template #default="scope">
				<el-link 
					type="primary" 
					:underline="false" 
					href="javacript:void(0);" 
					@click="router.push('/dashboard/clue/info/' + scope.row.id)"
				>
					{{ scope.row.fullName }}
				</el-link>
			</template>
		</el-table-column>

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
				<el-button type="primary" @click="router.push('/dashboard/clue/info/' + scope.row.id)">详情</el-button>

				<el-button type="success" @click="router.push('/dashboard/clue/edit/' + scope.row.id)">修改</el-button>

				<el-button type="danger" @click="deleteClue(scope.row.id)">删除</el-button>
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

	<!-- 上传 Excel 对话框 -->
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
import { doDelete, doGet, doPost } from '../../../http/httpRequestUtils';
import { messageConfirm, messageTip } from '../../../utils/utils';
import { useRouter } from 'vue-router';

// 路由器
const router = useRouter()

// 线索列表
const clueList = ref([])
// 总计查询条数
const totalCount = ref(0)
// 当前开始行数
const startRow = ref(1)

// 导入对话框是否可见
const importDialogVisable = ref(false)
// 上传表单对象
const uploadRef = ref()

// 刷新主页面
const reload = inject('reload')

// 临时存储选择的 id
let ids = []

/**
 * 加载信息数据，分页查询
 * @param current 当前页数
 */
function loadClues(current) {
	doGet("/api/clue/page/" + current, {}).then(response => {
		if (response.data.code === 200) { 
			clueList.value = response.data.data.list
			totalCount.value = response.data.data.total
			startRow.value = response.data.data.startRow
		}
	})
}

/**
 * 打开上传文件对话框
 */
function importFile() {
	importDialogVisable.value = true
}

/**
 * 上传文件
 * @param param 文件参数
 */
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

/**
 * 收到上传提交
 */
function uploadSubmit() {
	if (uploadRef.value) { 
		uploadRef.value.submit()
	} else {
		messageTip("您还没有选择文件", "warning")
	}
}

/**
 * 删除指定线索
 * @param id 线索 id
 */
function deleteClue(id) {
	messageConfirm(
		"您确认要删除这条线索及其跟进信息吗？",
		"删除确认",
		"warning",
		() => { 
			doDelete("/api/clue/" + id, {}).then(response => { 
				if (response.data.code === 200) {
					messageTip("删除成功", "success")
					reload()
				} else {
					messageTip("删除失败: " + response.data.msg, "error")
				}
			})
		},
		() => { 
			messageTip("取消删除", "info")
		}
	)
}

/**
 * 更新所选信息
 * @param selectedItems 当前选择项
 */
function updateSelected(selectedItems) {
	ids = []
	selectedItems.forEach(selceted => {
		ids.push(selceted.id)
	});
}

/**
 * 批量删除
 */
function bulkDelete() {
	
	if (ids.length <= 0) {
		messageTip("您还没有选择数据", "warning")
	} else { 
		messageConfirm(
			"您确认要删除这些线索及其跟进信息吗？",
			"删除确认",
			"warning",
			() => { 
				doDelete("/api/clue/bulk/" + ids.join("-"), {}).then(response => {
					if (response.data.code === 200) {
						messageTip("删除成功", "success")
						reload()
					} else { 
						messageTip("删除失败: " + response.data.msg, "error")
					}
				})
			},
			() => { 
				messageTip("取消删除", "info")
			}
		)
	}
}

onMounted(() => {
	loadClues(1)
})
</script>

<style>

</style>