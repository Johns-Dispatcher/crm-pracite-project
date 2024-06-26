<template>
	<el-container>
		<!-- 左侧 -->
		<el-aside :width="asideWidth">

			<div class="menu-title">
				@CRM管理系统
			</div>
			
			<!-- 菜单组 -->
			<el-menu
			active-text-color="#ff8080"
			background-color="#005a99"
			text-color="#fff"
			:unique-opened="true"
			:collapse="menuFolded"
			:collapse-transition="menutransition"
			>
				<!-- 菜单项 市场活动 -->
				<el-sub-menu index="1">
					<template #title>
						<el-icon><OfficeBuilding /></el-icon>
						<span>市场活动</span>
					</template>
					<el-menu-item index="1-1">
						<el-icon><Suitcase /></el-icon>
						市场活动
					</el-menu-item>
					<el-menu-item index="1-2">
						<el-icon><TrendCharts /></el-icon>市场统计
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 线索管理 -->
				<el-sub-menu index="2">
					<template #title>
						<el-icon><DataLine /></el-icon>
						<span>线索管理</span>
					</template>
					<el-menu-item index="2-1">
						<el-icon><DataBoard /></el-icon>
						线索管理
					</el-menu-item>
					<el-menu-item index="2-2">
						<el-icon><DataAnalysis /></el-icon>
						线索统计
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 客户管理 -->
				<el-sub-menu index="3">
					<template #title>
						<el-icon><UserFilled /></el-icon>
						<span>客户管理</span>
					</template>
					<el-menu-item index="3-1">
						<el-icon><List /></el-icon>
						客户管理
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 交易管理 -->
				<el-sub-menu index="4">
					<template #title>
						<el-icon><WalletFilled /></el-icon>
						<span>交易管理</span>
					</template>
					<el-menu-item index="4-1">
						<el-icon><Present /></el-icon>
						交易管理
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 产品管理 -->
				<el-sub-menu index="5">
					<template #title>
						<el-icon><Box /></el-icon>
						<span>产品管理</span>
					</template>
					<el-menu-item index="5-1">
						<el-icon><Goods /></el-icon>
						产品管理
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 字典管理 -->
				<el-sub-menu index="6">
					<template #title>
						<el-icon><Notebook /></el-icon>
						<span>字典管理</span>
					</template>
					<el-menu-item index="6-1">
						<el-icon><Memo /></el-icon>
						字典管理
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 用户管理 -->
				<el-sub-menu index="7">
					<template #title>
						<el-icon><User /></el-icon>
						<span>用户管理</span>
					</template>
					<el-menu-item index="7-1">
						<el-icon><Document /></el-icon>
						用户管理
					</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 系统管理 -->
				<el-sub-menu index="8">
					<template #title>
						<el-icon><Setting /></el-icon>
						<span>系统管理</span>
					</template>
					<el-menu-item index="8-1">
						<el-icon><Operation /></el-icon>
						系统管理
					</el-menu-item>
				</el-sub-menu>
			</el-menu>
		</el-aside>

		<!-- 右侧 -->
		<el-container class="right-container">
			<!-- 头部 -->
			<el-header>
				<el-icon class="fold-icon" @click="changeMenuFolded">
					<Fold v-if="!menuFolded"/>
					<Expand v-if="menuFolded"/>
				</el-icon>
			</el-header>
			<!-- 主体 -->
			<el-main>Main</el-main>
			<!-- 底部 -->
			<el-footer>@CopyRight 20xx-2xxx ???? | For Super Earth !! | xD</el-footer>
		</el-container>
	</el-container>
</template>

<script setup>
import { computed, ref, watch } from 'vue';

const menuFolded = ref(false)
const menutransition = ref(true)

const asideWidth = computed(() => {
	return (menuFolded.value ? 64 : 200) + 'px'
})

function changeMenuFolded() {
	menuFolded.value = !menuFolded.value
}

// 延迟调整过度动画，保证展开没有动画，折叠存在动画
watch(menuFolded, () => {
	setTimeout(() => {
		menutransition.value = !menuFolded.value
	}, 350)
})
</script>

<style scoped>
.el-aside {
	background: rgb(18, 18, 18);
	/* 默认折叠时间是 300 ms */
	transition: width 0.3s;
	/* 为了能够显示全部的菜单 */
	overflow: hidden;
}
.el-header {
	background: rgb(200, 228, 254);
	height: 35px;
	line-height: 35px;
}
.el-footer {
	background: rgb(189, 192, 255);
	height: 35px;
	text-align: center;
	line-height: 35px;
}
.right-container {
	height: calc(100vh);
}
.menu-title {
	color: #ffb266;
	text-align: center;
	height: 50px;
	line-height: 50px;
}
.el-menu {
	border-right: solid 0px;
}
.fold-icon {
	cursor: pointer;
}
</style>