<template>
  <div>
    <div class="header">
      <div class="header-left">
        <img src="@/assets/logo.png" alt="">
        <div class="title">在线聊天系统</div>
      </div>
      <div class="header-center">
        <div class="header-nav">
        </div>
      </div>
      <div class="header-right">
        <div>
          <el-dropdown>
            <div class="header-dropdown">
              <img :src="user.avatar" alt="">
              <div style="margin-left: 10px">
                <span style="color: white">{{ user.name }}</span><i class="el-icon-arrow-down"
                  style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div @click="$router.push('/person')">个人中心</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div @click="$router.push('/password')">修改密码</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>

    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background-color: #32325d; width: 250px">
        <el-menu :default-active="$route.path" default-openeds="[1,2,3,4]" router background-color="#32325d"
          text-color="#ECF0F1" active-text-color="#85C1E9">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-home"></i><span>系统首页</span>
            </template>
            <el-menu-item index="/">
              <i class="el-icon-position"></i>
              <span slot="title">系统首页</span>
            </el-menu-item>
          </el-submenu>
          <!--
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-home"></i><span>企业主页</span>
            </template>
            <el-menu-item index="/qiye">
              <i class="el-icon-position"></i>
              <span slot="title">企业主页</span>
            </el-menu-item>
          </el-submenu>
          -->
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-s-custom"></i><span>聊天室</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/imSingle">
                <i class="el-icon-chat-round"></i>
                <span slot="title">单人聊天</span>
              </el-menu-item>
              <el-menu-item index="/imGroup">
                <i class="el-icon-chat-dot-round"></i>
                <span slot="title">群组聊天</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3" v-if="user.role === 'ADMIN'">
            <template slot="title">
              <i class="el-icon-s-tools"></i><span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/user">
                <i class="el-icon-user"></i>
                <span slot="title">用户管理</span>
              </el-menu-item>
              <el-menu-item index="/notice">
                <i class="el-icon-bell"></i>
                <span slot="title">系统公告</span>
              </el-menu-item>
              <el-menu-item index="4">
                <i class="el-icon-switch-button"></i>
                <span @click="logout">退出登录</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-tools"></i><span>好友管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/user">
                <i class="el-icon-user"></i>
                <span slot="title">用户列表</span>
              </el-menu-item>
              <el-menu-item index="/hyyz">
                <i class="el-icon-user"></i>
                <span slot="title">申请列表</span>
              </el-menu-item>
              <el-menu-item index="/hy">
                <i class="el-icon-user"></i>
                <span slot="title">好友列表</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Layout",

  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },

  mounted() {
    this.$bus.$on('updateAvatar', (avatar) => {
      this.user = avatar
      localStorage.setItem("user", JSON.stringify(this.user))
    })
  },

  methods: {
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    }
  }

}
</script>

<style scoped>
.el-menu {
  border-right: none !important;
}

.el-main {
  padding: 30px;
}

.header {
  display: flex;
  height: 60px;
  line-height: 60px;
  background: linear-gradient(to right, #32325d, #344675);
  /*background: #525f7f;*/
}

.header-left {
  width: 280px;
  display: flex;
  align-items: center;
  padding-left: 30px;
}

.header-left img,
.header-dropdown img {
  width: 40px;
  height: 40px;
  border-radius: 50%
}

.header-left .title {
  flex: 1;
  color: #ffffff;
  cursor: pointer;
  margin-left: 10px;
  font-size: 20px;
  font-weight: bold;
}

.header-center {
  flex: 1;
}

.header-right {
  width: 200px;
  padding-right: 20px;
  text-align: right;
}

.header-dropdown {
  display: flex;
  align-items: center;
  justify-content: right;
}

.el-dropdown-menu {
  width: 100px !important;
  text-align: center !important;
}
</style>
