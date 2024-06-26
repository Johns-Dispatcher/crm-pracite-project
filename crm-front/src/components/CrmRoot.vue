<template>
	<!-- 路由位置的占位符 -->
	<router-view></router-view>
</template>

<script>
import Login from './login/LoginView.vue';
export default {
	name: "CrmRoot",
	components: { Login }
}
</script>

<style scoped>

</style>