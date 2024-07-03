<template>
	<h2 v-if="editMode">修改活动信息</h2>
	<h2 v-if="!editMode">新增活动信息</h2>

	<el-form  
		:model="record" 
		ref="recordForm"
		label-width="auto"
		:rules="rules"
	>
		<el-form-item label="负责人" prop="ownerId">
			<el-select 
				v-model="record.ownerId" 
				placeholder="请选择负责人"
				clearable
				style="width: 200px"
				@click="loadUserList"
			>
				<el-option 
					v-for="user in userList"
					:key="user.id"
					:label="user.name"
					:value="user.id"
				/>
			</el-select>
		</el-form-item>

		<el-form-item label="活动名称" prop="name">
			<el-input 
				v-model="record.name" 
				placeholder="请输入活动名称" 
				clearable
				style="width: 200px"
			/>
		</el-form-item>

		<el-form-item label="开始时间" prop="startTime">
			<el-date-picker
				v-model="record.startTime"
				type="datetime"
				placeholder="选择开始时间"
				format="YYYY-MM-DD HH:mm:ss"
				date-format="MMM DD, YYYY"
				time-format="HH:mm"
				value-format="YYYY-MM-DD HH:mm:ss"
			/>
		</el-form-item>

		<el-form-item label="结束时间" prop="endTime">
			<el-date-picker
				v-model="record.endTime"
				type="datetime"
				placeholder="选择结束时间"
				format="YYYY-MM-DD HH:mm:ss"
				date-format="MMM DD, YYYY"
				time-format="HH:mm"
				value-format="YYYY-MM-DD HH:mm:ss"
			/>
		</el-form-item>

		 <el-form-item label="活动预算" prop="cost">
			<el-input 
				v-model="record.cost" 
				placeholder="请输入活动预算" 
				clearable
				style="width: 200px"
			/>
		</el-form-item>

		<el-form-item label="活动描述" prop="description">
			<el-input
				v-model.lazy="record.description" 
				type="textarea"
				:autosize="{ minRows: 5, maxRows: 15 }"
				placeholder="描述一下活动..."
			/>
		</el-form-item>

		<el-form-item>
			<el-button type="primary" @click="addActivity" v-if="!editMode">提交</el-button>
			<el-button type="primary" @click="editActivity" v-if="editMode">修改</el-button>
			<el-button type="danger" @click="router.back" >返回</el-button>
		</el-form-item>
	</el-form>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { doGet, doPost, doPut } from '../../../http/httpRequestUtils';
import { useRoute, useRouter } from 'vue-router';
import { messageTip } from '../../../utils/utils';

const record = ref({})
const recordForm = ref()
const userList = ref([])
const rules = {
	ownerId: [ { required: true, message: '请选择一个负责人', trigger: 'blur' } ],
	name: [ { required: true, message: '请输入活动名称', trigger: 'blur' } ],
	startTime: [ { required: true, message: '请指定开始日期', trigger: 'blur' } ],
	endTime: [ { required: true, message: '请指定结束日期', trigger: 'blur' } ],
	cost: [ 
		{ required: true, message: '请输入活动预算', trigger: 'blur' },
		{ pattern: /^[0-9]+(.[0-9]{1,2})?$/, trigger: 'blur', message: '活动预算应当为两位小数' }
	],
	desc: [
		{ required: true, message: '请指定活动描述', trigger: 'blur' },
		{ min: 5, max: 255, message: '活动描述的长度应在 5 - 255 字符内', trigger: 'blur' }
	],
}
const router = useRouter()
const route = useRoute()
const editMode = ref(false)
const currentId = ref(0)

function loadUserList() {
	doGet('/api/user/name', {}).then(response => {
		if (response.data.code === 200) {
			userList.value = response.data.data
		}
	})
}

function addActivity() {
	recordForm.value.validate(isVaild => {
		if (isVaild) {
			doPost('/api/activity/', {
				ownerId: record.value.ownerId,
				name: record.value.name,
				startTime: record.value.startTime,
				endTime: record.value.endTime,
				cost: record.value.cost,
				description: record.value.description,
			}).then(response => {
				if (response.data.code === 200) {
					messageTip("添加成功", "success")
					router.back()
				} else {
					messageTip("添加失败，请稍后重试", "warning")
				}
			})
		}
	})
}

function loadActivity(id) {
	currentId.value = id
	doGet("/api/activity/" + id, {}).then(response => {
		if (response.data.code === 200) {
			record.value = response.data.data
			loadUserList()
		} else {
			messageTip("数据加载失败，请稍后重试", "error")
			router.back()
		}
	})
}

function editActivity() {
	recordForm.value.validate(isVaild => {
		if (isVaild) {
			doPut('/api/activity/', {
				id: record.value.id,
				ownerId: record.value.ownerId,
				name: record.value.name,
				startTime: record.value.startTime,
				endTime: record.value.endTime,
				cost: record.value.cost,
				description: record.value.description,
			}).then(response => {
				if (response.data.code === 200) {
					messageTip("修改成功", "success")
					router.back()
				} else {
					messageTip("修改失败，请稍后重试", "warning")
				}
			})
		}
	})
}

onMounted(() => {
	if (route.params.id) {
		editMode.value = true
		loadActivity(route.params.id)
	}
})
</script>

<style scope>
.el-form {
	padding: 5px;
	margin: 20px;
}

</style>