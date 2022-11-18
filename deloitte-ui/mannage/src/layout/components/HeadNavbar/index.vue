<!-- 页面顶部logo部分 -->
<template>
  <div class="top">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-row class="backgroundCss">
      <el-col :span="17">
        <el-row>
          <el-col :span="4"><el-image :src="logo" /></el-col>
          <!-- <el-col :span="20">
            <el-scrollbar wrap-class="scrollbar-wrapper">
              <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :text-color="variables.menuBg"
                :unique-opened="false"
                :active-text-color="variables.menuActiveText"
                :collapse-transition="false"
                mode="horizontal"
              >
                <sidebar-item v-for="route in permission_routes" :key="route.path" :item="route" :base-path="route.path" />
              </el-menu>
            </el-scrollbar>
          </el-col> -->
        </el-row>
      </el-col>
      <el-col :span="7" class="right-menu">
        <el-row>
          <el-col :span="6" style="width: 100%;">
            <el-dropdown trigger="click">
              <div class="avatar">
                <div><a>{{ userName }} </a></div>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="logout">
                  <span style="display:block;">退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Logo from './Logo'
import getters from '@/store'
import variables from '@/styles/variables.scss'
import logo from '@/assets/logo1.png'
import { queryEntityList } from '@/api/firstPage/onePage'
export default {
  components: { Logo },
  data() {
    return {
      inputStatus: true,
      searchData: '',
      logo,
      select: '',
      userName: ''
    }
  },
  computed: {
    ...mapGetters([
      'permission_routes',
      'sidebar',
      'name'
    ]),
    activeMenu() {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.inputStatus = route.path !== '/dashboard'
      },
      immediate: true
    }
  },
  created() {
    this.userName = getters.getters.name
  },
  methods: {
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    // cb回调函数,将处理好的数据推回
    querySearchAsync(queryString, cb) {
      if (queryString === undefined || queryString === null || queryString.trim().length === 0) {
        // var ddClass = document.getElementsByClassName('el-autocomplete-suggestion el-popper is-loading')[0]
        // ddClass.style.display = 'none'
        cb([])
        return
      }
      // 调用远程接口 将返回数据封装成 [{value:xxx,key2:value2},{value:xxx,key2:value2}]这样的形式，其中value关键字是必须的，检索框要根据该字段显示，其余的根据需求而定
      queryEntityList(Object.assign({
        entityName: queryString
      })).then(respones => {
        this.studentBasic = respones.data
        if (this.studentBasic === null) {
          var dd = [{
            value: '暂无数据'
          }]
          this.studentBasic = dd
        }
        if (this.studentBasic !== undefined && this.studentBasic !== null) {
          var results = queryString ? this.studentBasic.filter(this.createStateFilter(queryString)) : this
            .studentBasic
          clearTimeout(this.timeout)
          this.timeout = setTimeout(() => {
            cb(results)
          }, 0.5 * 1000)
        }
      })
    },
    // 根据输入的字段进行筛选
    createStateFilter(queryString) {
      return (state) => {
        // return (state.value.toString().toLowerCase().indexOf(queryString.toLowerCase()) >= 0)
        return true
      }
    },
    // 将其他数据自动补全，采用覆盖的方法
    handleSelect(item) {
      var entityName = item.value
      var id = item.id
      if (entityName !== null && entityName !== '') {
        const routeData = this.$router.resolve({
          path: '/example/entityInfo',
          query: {
            id: id,
            entityName: entityName
          }
        })
        window.open(routeData.href, '_blank')
        // this.$router.replace({ path: '/example/entityInfo', query: { entityName: entityName }});
      }
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep.el-submenu__title {
  padding: 0;
}
::v-deep.el-submenu__icon-arrow {
  right: 5px;
}
::v-deep.el-select .el-input {
    width: 110px;
  }
   ::v-deep.input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
.top{
    z-index: 2000;
    position: fixed;
    width: 100%;
    box-shadow: 0px -2px 7px 0px #888888;
}
.right-menu {
      text-align: right;
      padding-right: 40px;
      .avatar{
        display: flex;
        margin-top: 20px;
        .el-avatar{
          margin-top: 10px;
        }
        div:last-child{
          margin-left: 10px;
        }
      }
      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 60px;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }
    }
.el-image{
  width: 140px;
  height: 44px;
  margin-left: 40px;
  margin-top: 7px;
  margin-right: 40px;
}
.head-search {
  // width: 275px;
  margin-left: 40px;
  text-align: right;
  // margin-top: 7px;
  line-height: 60px;
}
::v-deep.el-menu.el-menu--horizontal {
  border-bottom: none;
}
.backgroundCss{
  //background-color: #268fd3;
  background-color: #ffffff;
}
::v-deep.el-menu {
    background-color: transparent !important;
}

::v-deep .el-input__inner{
  height:35px !important;
}
</style>
