<template>
	<!-- 活动信息展示 -->
 	<el-descriptions
		title="活动详细信息"
		:column="4"
		border
	>
		<el-descriptions-item :width="150" :span="4">
			<template #label>
				<el-icon><Document /></el-icon> 活动 ID
			</template>
			{{ activity.id }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><user /></el-icon> 活动名称
			</template>
			{{ activity.name }}
		</el-descriptions-item>

		<el-descriptions-item align="center" :width="100" :span="1">
			<template #label>
				<el-icon><Avatar /></el-icon> 负责人
			</template>
			{{ activity.owner }}
		</el-descriptions-item>

		<el-descriptions-item align="center" :width="100" :span="1">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 活动预算
			</template>
			{{ activity.cost }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Phone /></el-icon> 开始时间
			</template>
			{{ activity.startTime }}
		</el-descriptions-item>

		<el-descriptions-item align="center" :span="2">
			<template #label>
				<el-icon><Message /></el-icon> 结束时间
			</template>
			{{ activity.endTime }}
		</el-descriptions-item>

		<el-descriptions-item :span="4">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 活动描述
			</template>
			{{ activity.description }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Stamp /></el-icon> 创建者
			</template>
			{{ activity.creator }}
		</el-descriptions-item>

		<el-descriptions-item align="center" :span="2">
			<template #label>
				<el-icon><Timer /></el-icon> 创建时间
			</template>
			{{ activity.createTime }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Stamp /></el-icon> 修改者
			</template>
			{{ activity.editor }}
		</el-descriptions-item>

		<el-descriptions-item align="center" :span="2">
			<template #label>
				<el-icon><Timer /></el-icon> 修改时间
			</template>
			{{ activity.editTime }}
		</el-descriptions-item>
	</el-descriptions>

	<hr>

	<!-- 添加备注表单 -->
	<el-form 
		:model="activityRemarkData" 
		label-width="auto" 
		:rules="activityRemarkRules"
		ref="activityRemarkForm"
	>
		<el-form-item label="为活动添加备注信息" prop="noteContent">
			<el-input
				v-model.lazy="activityRemarkData.noteContent" 
				type="textarea"
				:autosize="{ minRows: 5, maxRows: 10 }"
				placeholder="备注信息..."
			/>
		</el-form-item>

		<el-form-item label=" ">
			<el-button type="primary" @click="addActivityRemark">提交</el-button>
			<el-button type="danger" plain @click="router.back">返回</el-button>
		</el-form-item>
	</el-form>

	<!-- 活动备注展示表格 -->
	<el-table 
		:data="activityRemarks" 
		style="width: 100%" 
		stripe
	>
		<el-table-column type="index" width="50px" :index="startRow"/>

		<el-table-column prop="noteContent" width="150px" label="备注内容" show-overflow-tooltip />
		
		<el-table-column prop="createTime" label="备注时间" show-overflow-tooltip />

		<el-table-column prop="creator" width="150px" label="备注人" show-overflow-tooltip />

		<el-table-column prop="editTime" label="编辑时间" show-overflow-tooltip />

		<el-table-column prop="editor" label="编辑人" show-overflow-tooltip />

		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="success" @click="onEdit(scope.row.id, scope.row.noteContent)">修改</el-button>
				<el-button type="danger" @click="onDelete(scope.row.id)">删除</el-button>
			</template>
		</el-table-column>
	</el-table>
	
	<br/>

	<!-- 分页 -->
	<el-pagination
		layout="prev, pager, next"
		:page-size="2"
		:total="remarkTotal"
		@current-change="loadActivityRemarks"
	/>

	<!-- 备注修改对话框 -->
	<el-dialog
		v-model="remarkDialogVisable"
		title="修改活动备注"
		draggable
	>
  		<!-- 备注修改表单 -->
		<el-form 
			:model="remarkDialogData" 
			label-width="auto" 
			style="padding: auto; margin: 20px;"
  			:rules="activityRemarkRules"
			ref="remarkDialogForm"
		>
			<el-form-item label="修改备注内容" prop="noteContent">
				<el-input 
					v-model="remarkDialogData.noteContent" 
					type="textarea"
					:autosize="{ minRows: 5, maxRows: 10 }"
				/>
			</el-form-item>
		</el-form>

		<template #footer>
			<div class="dialog-footer">
				<el-button @click="remarkDialogVisable = false">取消</el-button>
				<el-button type="primary" @click="editRemark">确认修改</el-button>
			</div>
		</template>
  </el-dialog>

</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { doDelete, doGet, doPost, doPut } from '../../../http/httpRequestUtils';
import { messageConfirm, messageTip } from '../../../utils/utils';

/* == 数据 == */

// 路由器
const router = useRouter()
// 路由
const route = useRoute()

// 当前活动 id
const id = ref(0)
// 活动详细信息
const activity = ref({})

// 备注添加表单信息
const activityRemarkData = ref({})
// 备注添加表单对象
const activityRemarkForm = ref()

// 备注修改对话框是否可见
const remarkDialogVisable = ref(false)
// 备注修改表单信息
const remarkDialogData = ref({})
// 备注修改表单对象
const remarkDialogForm = ref()

// 备注检测规则
// 添加修改共用规则
const activityRemarkRules = reactive({
	noteContent: [
		{ required: true, message: '请输入活动备注信息', trigger: 'blur' },
		{ min: 5, max: 255, message: '备注信息的长度应在 5 - 255 字符内', trigger: 'blur' }
	]
})

// 获取的备注列表
const activityRemarks = ref([])
// 备注查询总数
const remarkTotal = ref(0)
// 目前备注信息开始的行数
const startRow = ref(1)

/* == 函数 == */

/**
 * 加载活动信息
 * @param id 活动 id
 */
function loadActivity(id) {
	doGet("/api/activity/" + id, {}).then(response => {
		if (response.data.code === 200) {
			activity.value = response.data.data
		}
	})
}

/**
 * 加载活动备注信息，分页查询
 * @param current 当前页吗
 */
function loadActivityRemarks(current) {
	doGet("/api/activity/remark/" + id.value + "/" + current, {}).then(response => {
		activityRemarks.value = response.data.data.list
		remarkTotal.value = response.data.data.total
		startRow.value = response.data.data.startRow
	})
}

/**
 * 添加活动备注信息
 */
function addActivityRemark() {
	activityRemarkForm.value.validate(isVaild => {
		if (isVaild) {
			doPost("/api/activity/remark/", {
				activityId: activity.value.id,
				noteContent: activityRemarkData.value.noteContent
			}).then(response => {
				if (response.data.code) {
					messageTip("新增备注成功", "success")
					activityRemarkData.value.noteContent = ""
					loadActivityRemarks(1)
				} else {
					messageTip("新增备注失败，请稍后尝试", "error")
				}
			})
		}
	})
}

/**
 * 准备修改备注信息，打开对话框初始化值
 * @param id 备注 id
 * @param noteContent 备注当前内容
 */
function onEdit(id, noteContent) {
	remarkDialogVisable.value = true
	remarkDialogData.value.id = id
	remarkDialogData.value.noteContent = noteContent
}

/**
 * 修改备注信息
 */
function editRemark() {
	remarkDialogForm.value.validate(isVaild => {
		if (isVaild) {
			doPut("/api/activity/remark/", {
				id: remarkDialogData.value.id,
				noteContent: remarkDialogData.value.noteContent
			}).then(response => {
				if (response.data.code === 200) {
					messageTip("修改备注成功", "success")
					remarkDialogVisable.value = false
					remarkDialogData.value = {}
					loadActivityRemarks(1)
				} else {
					messageTip("修改失败", "error")
				}
			})
		}
	})
}

/**
 * 删除备注信息
 * @param id 备注 id
 */
function onDelete(id) {
	messageConfirm(
		"是否要删除该行备注",
		"删除确认",
		"warning",
		() => {
			doDelete("/api/activity/remark/" + id, {}).then(response => {
				if (response.data.code === 200) {
					messageTip("删除成功", "success")
					loadActivityRemarks(1)
				} else {
					messageTip("删除失败，请稍后重试", "warning")
				}
			})
		}, () => {
			messageTip("取消删除", "info")
		}
	)
}

/* == 钩子函数 == */

onMounted(() => {
	id.value = route.params.id
	loadActivity(id.value)
	loadActivityRemarks(1)
})

</script>

<style scope>
hr {
	margin-top: 20px;
	width: 95%;
}
</style>