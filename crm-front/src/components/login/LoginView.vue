<template>
	<div class="bg">
		<el-container>
			<el-aside>
			</el-aside>
			<el-main>
				<h1>欢迎使用 CRM 客户管理系统</h1>
			</el-main>
		</el-container>
	</div>
</template>

<script>
export default {
	name: "CrmLoginView"

}
</script>

<style scoped>
.el-aside {
	width: 60%;
}
.el-main {
	backdrop-filter: blur(6px);
	background: rgba(180, 180, 180, 0.6);
	height: calc(100vh);
}
.bg {
	background-image: url("../../assets/background.png");
	background-size: cover; /* 等比例缩放背景图片 */
	background-repeat: no-repeat; /* 防止背景图片重复平铺 */
	background-position: center; /* 将背景图片水平垂直居中 */
}
</style>