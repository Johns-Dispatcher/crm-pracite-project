<template>
	<el-container>
		<!-- 左侧 -->
		<el-aside width="200px">
			<!-- 菜单组 -->
			<el-menu
			active-text-color="#ffd04b"
			background-color="#545c64"
			text-color="#fff"
			>
				<!-- 菜单项 市场活动 -->
				<el-sub-menu index="1">
					<template #title>
						<span>市场活动</span>
					</template>
					<el-menu-item index="1-1">市场活动</el-menu-item>
					<el-menu-item index="1-2">市场统计</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 线索管理 -->
				<el-sub-menu index="2">
					<template #title>
						<span>线索管理</span>
					</template>
					<el-menu-item index="2-1">线索管理</el-menu-item>
					<el-menu-item index="2-2">线索统计</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 客户管理 -->
				<el-sub-menu index="3">
					<template #title>
						<span>客户管理</span>
					</template>
					<el-menu-item index="3-1">客户管理</el-menu-item>
					<el-menu-item index="3-2">客户管理</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 交易管理 -->
				<el-sub-menu index="4">
					<template #title>
						<span>交易管理</span>
					</template>
					<el-menu-item index="4-1">交易管理</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 产品管理 -->
				<el-sub-menu index="5">
					<template #title>
						<span>产品管理</span>
					</template>
					<el-menu-item index="5-1">产品管理</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 字典管理 -->
				<el-sub-menu index="6">
					<template #title>
						<span>字典管理</span>
					</template>
					<el-menu-item index="6-1">字典管理</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 用户管理 -->
				<el-sub-menu index="7">
					<template #title>
						<span>用户管理</span>
					</template>
					<el-menu-item index="7-1">用户管理</el-menu-item>
				</el-sub-menu>
				<!-- 菜单项 系统管理 -->
				<el-sub-menu index="8">
					<template #title>
						<span>系统管理</span>
					</template>
					<el-menu-item index="8-1">系统管理</el-menu-item>
				</el-sub-menu>
			</el-menu>
		</el-aside>

		<!-- 右侧 -->
		<el-container class="right-container">
			<!-- 头部 -->
			<el-header>Header</el-header>
			<!-- 主体 -->
			<el-main>Main</el-main>
			<!-- 底部 -->
			<el-footer>@CopyRight 20xx-2xxx ???? | For Super Earth !! | xD</el-footer>
		</el-container>
	</el-container>
</template>

<script>
	
	export default {
		name: "Dashboard"
	}
</script>

<style scoped>
.el-aside {
	background: rgb(18, 18, 18);
}
.el-header {
	background: rgb(200, 228, 254);
	height: 35px;
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
</style>