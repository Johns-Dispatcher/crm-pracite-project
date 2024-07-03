<template>
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
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { doGet, doPost } from '../../../http/httpRequestUtils';
import { messageTip } from '../../../utils/utils';

const router = useRouter()
const route = useRoute()

const id = ref(0)
const activity = ref({})

const activityRemarkData = ref({})
const activityRemarkForm = ref()

const activityRemarkRules = reactive({
	noteContent: [
		{ required: true, message: '请输入活动备注信息', trigger: 'blur' },
		{ min: 5, max: 255, message: '备注信息的长度应在 5 - 255 字符内', trigger: 'blur' }
	]
})

function loadActivity(id) {
	doGet("/api/activity/" + id, {}).then(response => {
		if (response.data.code === 200) {
			activity.value = response.data.data
		}
	})
}

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
				} else {
					messageTip("新增备注失败，请稍后尝试", "error")
				}
			})
		}
	})
}

onMounted(() => {
	id.value = route.params.id
	loadActivity(id.value)
})

</script>

<style scope>
hr {
	margin-top: 20px;
	width: 95%;
}
</style>