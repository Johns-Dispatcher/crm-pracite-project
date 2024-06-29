<template>
	<el-container>
		<!-- 左侧 -->
		<el-aside :width="asideWidth">

			<div class="menu-title">@CRM管理系统</div>

			<!-- 菜单组 -->
			<el-menu
				active-text-color="#ff8080"
				background-color="#005a99"
				text-color="#fff"
				:unique-opened="true"
				:collapse="menuFolded"
				:collapse-transition="menuTransition"
				:router="true"
			>
			<!-- 菜单项 市场活动 -->
				<el-sub-menu index="1">
					
					<template #title>
						<el-icon><OfficeBuilding/></el-icon>
						<span>市场活动</span>
					</template>

					<el-menu-item index="1-1">
						<el-icon><Suitcase/></el-icon>市场活动
					</el-menu-item>
					<el-menu-item index="1-2">
						<el-icon><TrendCharts/></el-icon>市场统计
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 线索管理 -->
				<el-sub-menu index="2">
					
					<template #title>
						<el-icon><DataLine/></el-icon>
						<span>线索管理</span>
					</template>

					<el-menu-item index="2-1">
						<el-icon><DataBoard/></el-icon>线索管理
					</el-menu-item>
					<el-menu-item index="2-2">
						<el-icon><DataAnalysis/></el-icon>线索统计
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 客户管理 -->
				<el-sub-menu index="3">

					<template #title>
						<el-icon><UserFilled/></el-icon>
						<span>客户管理</span>
					</template>

					<el-menu-item index="3-1">
						<el-icon><List/></el-icon>客户管理
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 交易管理 -->
				<el-sub-menu index="4">

					<template #title>
						<el-icon><WalletFilled/></el-icon>
						<span>交易管理</span>
					</template>

					<el-menu-item index="4-1">
						<el-icon><Present/></el-icon>交易管理
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 产品管理 -->
				<el-sub-menu index="5">

					<template #title>
						<el-icon><Box/></el-icon>
						<span>产品管理</span>
					</template>

					<el-menu-item index="5-1">
						<el-icon><Goods/></el-icon>产品管理
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 字典管理 -->
				<el-sub-menu index="6">

					<template #title>
						<el-icon><Notebook/></el-icon>
						<span>字典管理</span>
					</template>

					<el-menu-item index="6-1">
						<el-icon><Memo/></el-icon>字典管理
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 用户管理 -->
				<el-sub-menu index="7">

					<template #title>
						<el-icon><User/></el-icon>
						<span>用户管理</span>
					</template>

					<el-menu-item index="/dashboard/user">
						<el-icon><Document/></el-icon>用户管理
					</el-menu-item>

				</el-sub-menu>

				<!-- 菜单项 系统管理 -->
				<el-sub-menu index="8">

					<template #title>
						<el-icon><Setting/></el-icon>
						<span>系统管理</span>
					</template>

					<el-menu-item index="8-1">
						<el-icon><Operation/></el-icon>系统管理
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

				<div class="dropdown">
					<el-dropdown :hide-on-click="false" @visible-change="visibleChange">
						<span class="el-dropdown-link">
							{{ username }}
							<el-icon class="el-icon--right" style="vertical-align: middle">
								<ArrowUp v-if="dropboxVisiable"/>
								<ArrowDown v-if="!dropboxVisiable" />
							</el-icon>
						</span>
						<template #dropdown>
							<el-dropdown-menu>
								<el-dropdown-item>我的资料</el-dropdown-item>
								<el-dropdown-item>修改密码</el-dropdown-item>
								<el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
							</el-dropdown-menu>
						</template>
					</el-dropdown>
				</div>
			</el-header>

			<!-- 主体 -->
			<!-- 使用路由切换 -->
			<el-main><router-view v-if="isRouterAlive"/></el-main>

			<!-- 底部 -->
			<el-footer>@CopyRight 20xx-2xxx ???? | For Super Earth !! | xD</el-footer>

		</el-container>
	</el-container>
</template>

<script setup>
import {computed, nextTick, onMounted, provide, reactive, ref, watch} from 'vue';
import { doGet } from '../../http/httpRequestUtils';
import { clearToken, getTokenKey, messageConfirm, messageTip } from '../../utils/utils';
import { useRouter } from 'vue-router';

/* == 数据 == */

const menuFolded = ref(false)
const menuTransition = ref(true)
const dropboxVisiable = ref(false)
const username = ref("")
const exprieTime = ref(0)
const router = useRouter()
const isRouterAlive = ref(true)

/* == 计算属性 == */

const asideWidth = computed(() => {
  return (menuFolded.value ? 64 : 200) + 'px'
})


/* == 函数 == */

/**
 * 折叠状态更新
 */
function changeMenuFolded() {
  menuFolded.value = !menuFolded.value
}

/**
 * 用于调整图标的切换
 * @param isOpen 是否处于开启状态
 */
function visibleChange(isOpen) {
	dropboxVisiable.value = isOpen
}

/**
 * 向服务器获取登录信息
 */
function getLoginInfo() {
	doGet("/api/login/info", {}).then(
		(response) => {
			username.value = response.data.data.user.name
			exprieTime.value = response.data.data.expireTime
		}
	)
}

/**
 * 退出登录
 */
function logout() {
	messageConfirm(
		"您确认要退出该系统吗？",
		"确认退出",
		"warning",
		() => {
			doGet("/api/logout", {}).then(response => {
				if (response.data.code === 200) {
					clearToken()
					messageTip("退出成功", "success")
					router.push("/")
				} else {
					forceLogout()
				}
			})
		},
		() => {
			messageTip("取消退出", "info")
		}
	)
}

/**
 * 强制退出
 */
function forceLogout() {
	messageConfirm(
		"退出异常，是否要强制退出系统",
		"退出异常",
		"error",
		() => {
			clearToken()
			messageTip("强制退出成功", "warning")
			router.push("/")
		},
		() => {
			messageTip("取消强制退出，请稍后重试", "info")
		}
	)
}

/* == 钩子函数 == */

const expireTimer = ref(undefined)

onMounted(() => {
	getLoginInfo()
	expireTimer.value = setInterval(() => {
		exprieTime.value -= 500
	}, 500)
})

/**
 * 对外提供重载函数
 */
provide('reload',  () => {
	isRouterAlive.value = false
	nextTick(() => {
		isRouterAlive.value = true
	})
})

/* == 监视器 == */

// 延迟调整过度动画，保证展开没有动画，折叠存在动画
watch(menuFolded, () => {
  setTimeout(() => {
    menuTransition.value = !menuFolded.value
  }, 350)
})

watch(exprieTime, () => {
	if (exprieTime.value <= 1000 * 60 * 5) {
		console.log("准备续签");
		clearInterval(expireTimer.value)
		// 检测剩余五分钟自动续期
		// 访问续签接口 获取新 token 以及过期时间
		doGet("/api/login/renewal", {}).then(response => {
			if (response.data.code === 200) {
				// 如果之前存 local 就存 local
				if (window.localStorage.getItem(getTokenKey())) {
					window.localStorage.setItem(getTokenKey(), response.data.data.token)
				} else {
					// 如果存 session 就存 session
					window.sessionStorage.setItem(getTokenKey(), response.data.data.token)
				}
				// 更新后端给出的过期时间
				exprieTime.value = response.data.data.expireTime
				expireTimer.value = setInterval(() => {
					exprieTime.value -= 500
				}, 500)
			}
		})
	}
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
.dropdown {
	float: right;
}
.el-dropdown {
	line-height: 35px;
}
</style>