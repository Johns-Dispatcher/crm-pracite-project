<template>
	<h2 v-if="editMode">修改活动信息</h2>
	<h2 v-if="!editMode">新增活动信息</h2>

	<!-- 填写表单进行新增或者修改 -->
	<el-form  
		:model="recordData" 
		ref="recordForm"
		label-width="auto"
		:rules="rules"
	>
		<el-form-item label="负责人" prop="ownerId">
			<el-select 
				v-model="recordData.ownerId" 
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
				v-model="recordData.name" 
				placeholder="请输入活动名称" 
				clearable
				style="width: 200px"
			/>
		</el-form-item>

		<el-form-item label="开始时间" prop="startTime">
			<el-date-picker
				v-model="recordData.startTime"
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
				v-model="recordData.endTime"
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
				v-model="recordData.cost" 
				placeholder="请输入活动预算" 
				clearable
				style="width: 200px"
			/>
		</el-form-item>

		<el-form-item label="活动描述" prop="description">
			<el-input
				v-model.lazy="recordData.description" 
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

/* == 数据 == */

// 表单数据
const recordData = ref({})
// 信息表单
const recordForm = ref()
// 表单验证规则
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

// 所有用户名的列表
const userList = ref([])

// 路由器
const router = useRouter()
// 路由
const route = useRoute()

// 是否处于修改模式
const editMode = ref(false)
// 当前活动 id，只有修改模式才有效
const currentId = ref(0)

/* == 函数 == */

/**
 * 读取用户名列表
 */
function loadUserList() {
	doGet('/api/user/name', {}).then(response => {
		if (response.data.code === 200) {
			userList.value = response.data.data
		}
	})
}

/**
 * 添加活动
 */
function addActivity() {
	recordForm.value.validate(isVaild => {
		if (isVaild) {
			doPost('/api/activity/', {
				ownerId: recordData.value.ownerId,
				name: recordData.value.name,
				startTime: recordData.value.startTime,
				endTime: recordData.value.endTime,
				cost: recordData.value.cost,
				description: recordData.value.description,
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
/**
 * 修改活动
 */
function editActivity() {
	recordForm.value.validate(isVaild => {
		if (isVaild) {
			doPut('/api/activity/', {
				id: recordData.value.id,
				ownerId: recordData.value.ownerId,
				name: recordData.value.name,
				startTime: recordData.value.startTime,
				endTime: recordData.value.endTime,
				cost: recordData.value.cost,
				description: recordData.value.description,
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
/**
 * 加载活动
 */
function loadActivity(id) {
	currentId.value = id
	doGet("/api/activity/" + id, {}).then(response => {
		if (response.data.code === 200) {
			recordData.value = response.data.data
			loadUserList()
		} else {
			messageTip("数据加载失败，请稍后重试", "error")
			router.back()
		}
	})
}

/* == 钩子函数 == */

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