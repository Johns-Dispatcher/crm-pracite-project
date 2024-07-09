<template>
	<div style="width: 85%; margin: auto;">

		<h1 v-if="!editMode">新增线索信息</h1>
		<h1 v-if="editMode">修改线索信息</h1>

		<el-button type="danger" plain @click="router.back()">返回</el-button>
		
		<!-- 线索信息表单 -->
		<el-form
			:model="clueData"
			label-width="auto"
			:rules="rules"
			ref="clueForm"
		>
			<el-form-item label="所属活动">
				<el-select v-model="clueData.activityId" placeholder="请选择活动">
					<el-option v-for="activity in activityList"
						:key="activity.id"
						:label="activity.name"
						:value="activity.id">
					</el-option>
				</el-select>
			</el-form-item>
			
			<el-form-item label="姓名" prop="fullName">
				<el-input v-model="clueData.fullName" placeholder="请指定姓名" clearable/>
			</el-form-item>

			<el-form-item label="称呼">
				<el-select v-model="clueData.appellationDicId" placeholder="请选择称呼">
					<el-option v-for="appellation in appellations"
						:key="appellation.id"
						:label="appellation.value"
						:value="appellation.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="手机" prop="phone">
				<el-input v-model="clueData.phone" placeholder="请填写电话" clearable :disabled="editMode"/>
			</el-form-item>

			<el-form-item label="QQ" prop="qq">
				<el-input v-model="clueData.qq" placeholder="请填写 QQ" clearable/>
			</el-form-item>

			<el-form-item label="邮箱" prop="email">
				<el-input v-model="clueData.email" placeholder="请填写邮箱" clearable/>
			</el-form-item>

			<el-form-item label="年龄">
				<el-input v-model.number="clueData.age" placeholder="请填写年龄" clearable/>
			</el-form-item>

			<el-form-item label="职业">
				<el-input v-model="clueData.job" placeholder="请填写职业" clearable/>
			</el-form-item>

			<el-form-item label="年收入" prop="yearIncome">
				<el-input v-model="clueData.yearIncome" placeholder="请填写年收入" clearable/>
			</el-form-item>

			<el-form-item label="住址">
				<el-input v-model="clueData.address" placeholder="请填写住址" clearable/>
			</el-form-item>

			<el-form-item label="贷款情况">
				<el-select v-model="clueData.needLoadDicId" placeholder="请选择是否需要贷款">
					<el-option v-for="needLoad in needLoans"
						:key="needLoad.id"
						:label="needLoad.value"
						:value="needLoad.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="意向状态">
				<el-select v-model="clueData.intentionStateDicId" placeholder="请选择意向状态">
					<el-option v-for="intentionState in intentionStates"
						:key="intentionState.id"
						:label="intentionState.value"
						:value="intentionState.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="意向产品">
				<el-select v-model="clueData.intentionProduct" placeholder="请选择意向产品">
					<el-option v-for="product in products"
						:key="product.id"
						:label="product.name"
						:value="product.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="线索状态">
				<el-select v-model="clueData.stateDicId" placeholder="请选择线索状态">
					<el-option v-for="clueState in clueStates"
						:key="clueState.id"
						:label="clueState.value"
						:value="clueState.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="线索来源">
				<el-select v-model="clueData.sourceDicId" placeholder="请选择线索来源">
					<el-option v-for="source in sources"
						:key="source.id"
						:label="source.value"
						:value="source.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="线索描述">
				<el-input
					v-model="clueData.description" 
					placeholder="请输入线索描述"
					clearable
					rows="5"
					type="textarea"
				/>
			</el-form-item>

			<el-form-item label="下次联系实际">
				<el-date-picker
					v-model="clueData.nextContactTime"
					type="datetime"
					placeholder="选择下次联系时间时间"
					format="YYYY-MM-DD HH:mm:ss"
					date-format="MMM DD, YYYY"
					time-format="HH:mm"
					value-format="YYYY-MM-DD HH:mm:ss"
					style="width: 100%;"
				/>
			</el-form-item>

			<el-form-item label=" ">
				<el-button type="primary" @click="addClue" v-if="!editMode">新增线索信息</el-button>
				<el-button type="primary" @click="editClue" v-if="editMode">修改线索信息</el-button>
				<el-button type="danger" @click="loadClueInfo">重置表单</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { doGet, doPost, doPut } from '../../../http/httpRequestUtils';
import { messageTip } from '../../../utils/utils';
import { useRoute, useRouter } from 'vue-router';

// 线索表单数据
const clueData = ref({})
// 线索表单对象
const clueForm = ref()

// 从后端加载的活动信息数据
const activityList = ref([])
// 称呼信息数据
const appellations = ref([])
// 贷款信息数据
const needLoans = ref([])
// 意向状态数据
const intentionStates = ref([])
// 产品数据
const products = ref([])
// 线索状态数据
const clueStates = ref([])
// 线索来源数据
const sources = ref([])

// 路由器
const router = useRouter()
// 路由
const route = useRoute()

// 是否进入了编辑模式
const editMode = ref(false)
// 当前编辑 id
const editClueId = ref(0)

// 验证规则
const rules = ref({
	phone: [
		{ required: true, message: '请输入电话', trigger: 'blur' },
		{
			pattern: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,
			message: '请输入正确的电话格式',
			trigger: 'blur'
		},
		{ validator: validatePhone, trigger: 'blur' }
	],
	fullName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
	qq: [ { pattern: /[1-9][0-9]{4,14}/, message: "请输入正确的QQ号", trigger: 'blur' } ],
	email: [
		{
			pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
			message: '请输入正确的邮箱格式',
			trigger: 'blur'
		}
	],
	yearIncome: [ { pattern: /^[0-9]+(.[0-9]{1,2})?$/, trigger: 'blur', message: '年收入应该为两位小数' } ]
})

/**
 * 加载活动信息
 */
function loadActivityNames() {
	doGet("/api/activity/ongoing", {}).then(response => {
		if (response.data.code === 200) {
			activityList.value = response.data.data
		}
	})
}

/**
 * 加载称呼信息
 */
function loadAppellations() {
	doGet("/api/dic/appellation", {}).then(response => {
		if (response.data.code === 200) {
			appellations.value = response.data.data.values
		}
	})
}

/**
 * 加载贷款状态信息
 */
function loadNeedLoans() {
	doGet("/api/dic/needLoan", {}).then(response => {
		if (response.data.code === 200) {
			needLoans.value = response.data.data.values
		}
	})
}

/**
 * 加载意向状态信息
 */
function loadIntentionStates() {
	doGet("/api/dic/intentionState", {}).then(response => {
		if (response.data.code === 200) {
			intentionStates.value = response.data.data.values
		}
	})
}

/**
 * 加载线索状态信息
 */
function loadClueState() {
	doGet("/api/dic/clueState", {}).then(response => {
		if (response.data.code === 200) {
			clueStates.value = response.data.data.values
		}
	})
}

/**
 * 加载线索来源信息
 */
function loadSources() {
	doGet("/api/dic/source", {}).then(response => {
		if (response.data.code === 200) {
			sources.value = response.data.data.values
		}
	})
}

/**
 * 加载产品信息
 */
function loadProducts() {
	doGet("/api/product/names", {}).then(response => {
		if (response.data.code === 200) {
			products.value = response.data.data
		}
	})
}

/**
 * 添加线索
 */
function addClue() {
	clueForm.value.validate((isValid) => {
		if (isValid) { 
			doPost("/api/clue/", clueData.value).then(response => {
				if (response.data.code === 200) {
					messageTip("添加成功", "success")
					router.back()
				} else {
					messageTip("添加失败", "error")
				}
			})
		}
	})
}

/**
 * 验证手机号是否已经录入过
 * @param rule 规则
 * @param value 当前值
 * @param callback 回调
 */
function validatePhone(rule, value, callback) {
	if (!editMode && value) {
		doGet("/api/clue/phone/" + value, {}).then(response => {
			if (response.data.code === 200) {
				if (response.data.data) {
					callback(new Error("当前手机号已经存在"))
				} else {
					callback()
				}
			} else {
				callback()
			}
		})
	}
}

/**
 * 加载线索信息	
 * @param id 线索 id
 */
function loadClueInfo(id) {
	if (editMode.value) {
		doGet("/api/clue/" + id, {}).then(response => {
			if (response.data.code === 200) {
				clueData.value = response.data.data
				// 默认查询的活动是正在进行的活动
				// 如果活动不存在，会导致修改时没有对应数据
				// 这里将已经过期的活动从线索中拿出并添加进活动列表
				if (!checkActivityExist(response.data.data.activityId)) {
					activityList.value.push({
						id: response.data.data.activityId,
						name: response.data.data.activity
					})
				}
			}
		}) 
	} else {
		clueData.value = {}
	}
}

/**
 * 检测活动是否存在于列表中
 * @param id 活动 id
 */
function checkActivityExist(id) {
	activityList.value.forEach(item => {
		if (item.id === id) return true
	})
	return false
}

/**
 * 修改线索
 */
function editClue() {
	doPut("/api/clue/", clueData.value).then(response => {
		if (response.data.code === 200) {
			messageTip("修改成功", "success")
			router.back()
		} else {
			messageTip("修改失败，请稍后重试", "error")
		}
	})
}

onMounted(() => {
	loadActivityNames()
	loadAppellations()
	loadNeedLoans()
	loadIntentionStates()
	loadClueState()
	loadSources()
	loadProducts()

	if (route.params.id) {
		editMode.value = true
		editClueId.value = route.params.id
		loadClueInfo(editClueId.value)
	}
})
</script>

<style scope>

</style>