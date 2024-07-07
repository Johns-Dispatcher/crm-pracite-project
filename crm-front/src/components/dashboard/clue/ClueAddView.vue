<template>
	<div style="width: 85%; margin: auto;">

		<h1>新增线索信息</h1>

		<el-button type="danger" plain @click="router.back()">返回</el-button>
		

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
				<el-input v-model="clueData.phone" placeholder="请填写电话" clearable/>
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
				<el-button type="primary" @click="addClue">新增线索信息</el-button>
				<el-button type="danger" @click="clueData = {}">重置表单</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { doGet, doPost } from '../../../http/httpRequestUtils';
import { messageTip } from '../../../utils/utils';
import { useRouter } from 'vue-router';

const clueData = ref({})
const clueForm = ref()

const activityList = ref([])
const appellations = ref([])
const needLoans = ref([])
const intentionStates = ref([])
const products = ref([])
const clueStates = ref([])
const sources = ref([])

const router = useRouter()

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


function loadActivityNames() {
	doGet("/api/activity/ongoing", {}).then(response => {
		if (response.data.code === 200) {
			activityList.value = response.data.data
		}
	})
}

function loadAppellations() {
	doGet("/api/dic/appellation", {}).then(response => {
		if (response.data.code === 200) {
			appellations.value = response.data.data.values
		}
	})
}

function loadNeedLoans() {
	doGet("/api/dic/needLoan", {}).then(response => {
		if (response.data.code === 200) {
			needLoans.value = response.data.data.values
		}
	})
}

function loadIntentionStates() {
	doGet("/api/dic/intentionState", {}).then(response => {
		if (response.data.code === 200) {
			intentionStates.value = response.data.data.values
		}
	})
}

function loadClueState() {
	doGet("/api/dic/clueState", {}).then(response => {
		if (response.data.code === 200) {
			clueStates.value = response.data.data.values
		}
	})
}

function loadSources() {
	doGet("/api/dic/source", {}).then(response => {
		if (response.data.code === 200) {
			sources.value = response.data.data.values
		}
	})
}

function loadProducts() {
	doGet("/api/product/names", {}).then(response => {
		if (response.data.code === 200) {
			products.value = response.data.data
		}
	})
}

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

function validatePhone(rule, value, callback) {
	if (value) {
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

onMounted(() => {
	loadActivityNames()
	loadAppellations()
	loadNeedLoans()
	loadIntentionStates()
	loadClueState()
	loadSources()
	loadProducts()
})
</script>

<style scope>

</style>