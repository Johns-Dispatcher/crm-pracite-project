<template>
	<!-- 线索信息展示 -->
 	<el-descriptions
		title="活动详细信息"
		:column="4"
		border
	>
		<el-descriptions-item width="150">
			<template #label>
				<el-icon><Document /></el-icon> 线索 ID
			</template>
			{{ clue.id }}
		</el-descriptions-item>

		<el-descriptions-item  width="150">
			<template #label>
				<el-icon><Avatar /></el-icon> 负责人
			</template>
			{{ clue.owner }}
		</el-descriptions-item>

		<el-descriptions-item :span="2"  width="150">
			<template #label>
				<el-icon><Aim /></el-icon> 所属活动
			</template>
			{{ clue.activity }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><user /></el-icon> 姓名
			</template>
			{{ clue.fullName }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><ChatDotSquare /></el-icon> 称呼
			</template>
			{{ clue.appellation }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Phone /></el-icon> 电话
			</template>
			{{ clue.phone }}
		</el-descriptions-item>

		<el-descriptions-item  width="150">
			<template #label>
				<el-icon><Service /></el-icon> QQ
			</template>
			{{ clue.qq }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><PhoneFilled /></el-icon> 微信
			</template>
			{{ clue.wechat }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><TakeawayBox /></el-icon> 邮箱
			</template>
			{{ clue.email }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><MoreFilled /></el-icon> 年龄
			</template>
			{{ clue.age }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Suitcase /></el-icon> 工作
			</template>
			{{ clue.job }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><WalletFilled /></el-icon> 年收入
			</template>
			{{ clue.yearIncome }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><LocationFilled /></el-icon> 地址
			</template>
			{{ clue.address }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Wallet /></el-icon> 是否需要贷款
			</template>
			{{ clue.needLoan }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><PriceTag /></el-icon> 意向状态
			</template>
			{{ clue.intentionState }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Goods /></el-icon> 意向产品
			</template>
			{{ clue.productName }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><Flag /></el-icon> 线索状态
			</template>
			{{ clue.state }}
		</el-descriptions-item>

		<el-descriptions-item>
			<template #label>
				<el-icon><MagicStick /></el-icon> 线索来源
			</template>
			{{ clue.source }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Document /></el-icon> 线索描述
			</template>
			{{ clue.description }}
		</el-descriptions-item>

		<el-descriptions-item :span="4">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 下次联系时间
			</template>
			{{ clue.nextContactTime }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Stamp /></el-icon> 创建人
			</template>
			{{ clue.creator }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 创建时间
			</template>
			{{ clue.createTime }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><Stamp /></el-icon> 修改人
			</template>
			{{ clue.editor }}
		</el-descriptions-item>

		<el-descriptions-item :span="2">
			<template #label>
				<el-icon><InfoFilled /></el-icon> 修改时间
			</template>
			{{ clue.editTime }}
		</el-descriptions-item>
	</el-descriptions>

	<div class="ops">
		<el-button type="primary" @click="onTransform" :disabled="clue.customer">转换为客户</el-button>
		<el-button type="danger" plain @click="router.back()">返回</el-button>
	</div>

	<!-- 提交线索跟进信息表单 -->
	<el-form 
		:model="clueRemarkData" 
		ref="clueRemarkForm" 
		:rules="rules"
	>
		<el-form-item label="跟踪记录" prop="noteContent">
			<el-input 
				v-model="clueRemarkData.noteContent" 
				type="textarea" 
				:autosize="{ minRows: 5, maxRows: 10 }"
			/>
		</el-form-item>

		<el-form-item label="跟踪方式" prop="noteWay">
			<el-select 
				v-model="clueRemarkData.noteWay"
				placeholder="请选择跟踪方式" 
				clearable
			>
				<el-option v-for="noteWay in noteWays"
					:key="noteWay.id"
					:label="noteWay.value"
					:value="noteWay.id">
				</el-option>
			</el-select>
		</el-form-item>
		
		<el-form-item>
			<el-button type="primary" @click="addClueRemark">录入跟踪方式</el-button>
		</el-form-item>
	</el-form>

	<!-- 线索跟踪信息展示表格 -->
	<el-table 
		:data="clueRemarks" 
		style="width: 100%" 
		stripe
	>
		<el-table-column type="index" :index="startRow"/>

		<el-table-column prop="noteContent" label="线索跟踪信息内容" show-overflow-tooltip />
		
		<el-table-column prop="noteWayDetail" label="线索跟踪信息方式" show-overflow-tooltip />

		<el-table-column prop="creator" label="创建者" show-overflow-tooltip />

		<el-table-column prop="editTime" label="编辑时间" show-overflow-tooltip />

		<el-table-column prop="editor" label="编辑人" show-overflow-tooltip />

		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="success" @click="onEditRemark(scope.row.id)">修改</el-button>
				<el-button type="danger" @click="deleteRemark(scope.row.id)">删除</el-button>
			</template>
		</el-table-column>
	</el-table>
	
	<br/>

	<!-- 分页 -->
	<el-pagination
		layout="prev, pager, next"
		:page-size="10"
		:total="remarkTotal"
		@current-change="loadClueRemarks"
	/>

	<!-- 线索跟踪修改对话框 -->
	<el-dialog
		v-model="remarkDialogVisable"
		title="修改活动备注"
		draggable
	>
  		<!-- 线索跟踪信息修改表单 -->
		<el-form 
			:model="remarkDialogData" 
			label-width="auto" 
			style="padding: auto; margin: 20px;"
  			:rules="rules"
			ref="remarkDialogForm"
		>
			<el-form-item label="修改跟踪内容" prop="noteContent">
				<el-input 
					v-model="remarkDialogData.noteContent" 
					type="textarea"
					:autosize="{ minRows: 5, maxRows: 10 }"
				/>
			</el-form-item>

			<el-form-item label="修改跟踪方式" prop="noteWay">
				<el-select 
					v-model="remarkDialogData.noteWay"
					placeholder="请选择跟踪方式" 
					clearable
				>
					<el-option v-for="noteWay in noteWays"
						:key="noteWay.id"
						:label="noteWay.value"
						:value="noteWay.id">
					</el-option>
				</el-select>
			</el-form-item>
		</el-form>

		<template #footer>
			<div class="dialog-footer">
				<el-button @click="remarkDialogVisable = false">取消</el-button>
				<el-button type="primary" @click="editRemark">确认修改</el-button>
			</div>
		</template>
	</el-dialog>

	<!-- 提升为客户对话框 -->
	<el-dialog
		v-model="transformDialogVisable"
		title="将线索提升为客户"
		draggable
	>
  		<!-- 提升为客户表单 -->
		<el-form 
			:model="transformData" 
			label-width="auto" 
			style="padding: auto; margin: 20px;"
  			:rules="transformRules"
			ref="transformForm"
		>
			<el-form-item label="选择意向产品" prop="product">
				<el-select 
					v-model="transformData.product"
					placeholder="请选择意向产品" 
					clearable
				>
					<el-option v-for="product in products"
						:key="product.id"
						:label="product.name"
						:value="product.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="添加客户描述" prop="description">
				<el-input 
					v-model="transformData.description" 
					type="textarea"
					:autosize="{ minRows: 5, maxRows: 10 }"
				/>
			</el-form-item>

			<el-form-item label="下次见面时间" prop="nextContactTime">
				<el-date-picker
					v-model="transformData.nextContactTime"
					type="datetime"
					placeholder="选择下次见面时间"
					format="YYYY-MM-DD HH:mm:ss"
					date-format="MMM DD, YYYY"
					time-format="HH:mm"
					value-format="YYYY-MM-DD HH:mm:ss"
				/>
			</el-form-item>
		</el-form>

		<template #footer>
			<div class="dialog-footer">
				<el-button @click="transformDialogVisable = false">取消</el-button>
				<el-button type="primary" @click="transformToCustomer">确认转换</el-button>
			</div>
		</template>
	</el-dialog>
	
</template>
<script setup>
import { inject, onMounted, ref } from 'vue';
import { doDelete, doGet, doPost, doPut } from '../../../http/httpRequestUtils';
import { useRoute, useRouter } from 'vue-router';
import { messageConfirm, messageTip } from '../../../utils/utils';

// 线索信息
const clue = ref({})
// 当前线索 id
const clueId = ref(0)

// 路由
const route = useRoute()
// 路由器
const router = useRouter()

// 线索跟进表单数据
const clueRemarkData = ref({})
// 线索跟进表单对象
const clueRemarkForm = ref()

// 跟踪方式列表
const noteWays = ref([])
// 验证规则
const rules = ref({
	noteContent: [
		{ required: true, message: '请输入跟踪信息', trigger: 'blur' },
		{ min: 5, max: 255, message: '跟踪信息的长度应在 5 - 255 字符内', trigger: 'blur' }
	],
	noteWay: [ { required: true, message: '请提供跟踪方式', trigger: 'blur' } ]
})

// 跟进信息对话框是否可见
const remarkDialogVisable = ref(false)

// 跟进信息列表
const clueRemarks = ref([])
// 当前开始行
const startRow = ref(1)
// 跟进信息总数
const remarkTotal = ref(0)
// 修改跟进信息表单数据
const remarkDialogData = ref({})
// 修改跟进信息表单对象
const remarkDialogForm = ref()

// 提升客户对话框可见性
const transformDialogVisable = ref(false)

// 提升客户表单数据
const transformData = ref({})
// 提升客户表单对象
const transformForm = ref()
// 产品信息
const products = ref({})

// 验证规则
const transformRules = ref({
	description: [
		{ required: true, message: '请填写客户描述', trigger: 'blur' },
		{ min: 5, max: 255, message: '客户描述的长度应在 5 - 255 字符内', trigger: 'blur' }
	],
	product: [ { required: true, message: '请选择意向产品', trigger: 'blur' } ],
	nextContactTime: [ { required: true, message: '请提供跟踪方式', trigger: 'blur' } ]
})

// 刷新页面
const reload = inject("reload")

/**
 * 加载线索信息
 * @param id 线索 id
 */
function loadClueInfo(id) {
	doGet("/api/clue/" + id, {}).then(response => { 
		if (response.data.code === 200) {
			clue.value = response.data.data
		}
	})
}

/**
 * 加载跟进方式
 */
function loadNoteWays() {
	doGet("/api/dic/noteWay", {}).then(response => {
		if (response.data.code === 200) {
			noteWays.value = response.data.data.values
		}
	})
}

/**
 * 添加线索跟进信息
 */
function addClueRemark() {
	clueRemarkForm.value.validate(isVaild => {
		if (isVaild) { 
			doPost("/api/clue/remark/", {
				clueId: clueId.value,
				noteWay: clueRemarkData.value.noteWay,
				noteContent: clueRemarkData.value.noteContent,
			}).then(response => {
				if (response.data.code === 200) {
					messageTip("添加成功", "success")
					clueRemarkData.value = {}
					loadClueRemarks(1)
				} else {
					messageTip("添加失败", "error")
				}
			})
		}
	})
}

/**
 * 加载线索跟进数据，分页查询
 * @param current 当前页码
 */
function loadClueRemarks(current) {
	doGet("/api/clue/remark/" + current + "/" + clueId.value, {}).then(response => {
		if (response.data.code === 200) {
			clueRemarks.value = response.data.data.list
			remarkTotal.value = response.data.data.total
			startRow.value = response.data.data.startRow
		}
	})
} 

/**
 * 打开修改对话框，并查询数据
 * @param id 线索跟进 id
 */
function onEditRemark(id) {
	doGet("/api/clue/remark/info/" + id, {}).then(response => {
		if (response.data.code === 200) {
			remarkDialogData.value = response.data.data
			remarkDialogVisable.value = true
		}
	})
}

/**
 * 修改跟进数据
 */
function editRemark() {
	doPut("/api/clue/remark/", remarkDialogData.value).then(response => {
		if (response.data.code === 200) {
			messageTip("修改成功", "success")
			loadClueRemarks(1)
			remarkDialogVisable.value = false
		} else {
			messageTip("修改失败", "error")
		}
	})
}

/**
 * 删除跟进数据
 * @param id 线索跟进 id
 */
function deleteRemark(id) {
	messageConfirm(
		"您确认要删除这条跟踪信息吗？",
		"删除确认",
		"warning",
		() => { 
			doDelete("/api/clue/remark/" + id, {}).then(response => {
				if (response.data.code === 200) {
					messageTip("删除成功", "success")
					loadClueRemarks(1)
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
 * 打开提升客户对话框
 */
function onTransform() {
	loadProducts()
	transformDialogVisable.value = true
}

/**
 * 加载产品信息
 */
function loadProducts() {
	doGet('/api/product/names', {}).then(response => {
		if (response.data.code === 200) {
			products.value = response.data.data
		}
	})
}

/**
 * 提升客户
 */
function transformToCustomer() {
	transformForm.value.validate(isVaild => {
		if (isVaild) {
			doPost("/api/customer/", {
				clueId: clueId.value,
				product: transformData.value.product,
				description: transformData.value.description,
				nextContactTime: transformData.value.nextContactTime,
			}).then(response => {
				if (response.data.code === 200) {
					messageTip("提升为客户成功", "success")
					reload()
				} else {
					messageTip("提升失败", "error")
				}
			})
		}
	})
}

onMounted(() => {
	clueId.value = route.params.id
	loadClueInfo(route.params.id)
	loadNoteWays()
	loadClueRemarks(1)
})

</script>
<style scoped>
.el-icon {
	margin: auto;
	padding: auto;
}
.ops {
	margin: 20px 10px;
}
</style>