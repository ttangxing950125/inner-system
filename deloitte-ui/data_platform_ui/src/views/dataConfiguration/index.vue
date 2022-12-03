<template>
  <div class="page-container">
    <!-- 菜单 -->
    <page-menu ref="pageMenu" :menus="menusCenter" :dataSetingMenu="leftMenus" :showMenu="true" :index="activeIdex === 0 ? 2 : 3"></page-menu>
    <div class="page-info">
      <div class="info-container">
        <div v-if="btnShow" class="flex-row menu">
          <span
            :class="['menu-item', index == activeIdex ? 'isActive' : '']"
            v-for="(item, index) in menuList"
            :key="index + 'q'"
            @click="handleTab(index)"
            >{{ item }}</span
          >
        </div>
        <!-- 中间层数据配置 -->
        <centerData v-if="activeIdex == 0" ></centerData>
        <!-- 指标层数据配置 -->
        <indexData v-if="activeIdex == 1" ></indexData>
      </div>
    </div>
  </div>
</template>

<script>
import pageMenu from "./components/pageMenu.vue";
import centerData from "./components/centerData.vue"; //中间层数据配置
import indexData from "./components/indexData.vue"; //指标层数据配置
export default {
  components: {
    pageMenu,
    centerData,
    indexData
  },
  data() {
    return {
      activeIdex: 0,
      menuList: [
        "中间层数据配置",
        "指标层数据配置"
      ],
      // 中间层菜单
      menusCenter: [
        {
          title: "Evidence(计算)",
          child: [],
        },        
      ],
      // 指标菜单
      menusIndex: [          
        {
          title: "内评",
          child: [],
        },
      ],
      leftMenus: [
        {
          name: '指标层字典表',
          path: '/dataConfiguration'
        },
        {
          name: '使用场景',
          path: '/'
        },
      ],
      btnShow: true
    };
  },
  created() {
    let that = this;
    this.$eventBus.$off("hideBtn") // 防止内存泄漏
    this.$eventBus.$on("hideBtn",function(data){
        that.hideBtn(data)
    })
  },
  methods: {
    handleTab(index) {
      this.activeIdex = index;
      this.$refs.pageMenu.query = ''
      this.$refs.pageMenu.searData = []
      this.$refs.pageMenu.btnShow = false
      this.$refs.pageMenu.menusFun(this.activeIdex === 0 ? this.menusCenter : this.menusIndex)
    },
    // 子组件控制btn 展示
    hideBtn(show) {
      this.btnShow = !show
    }
  },
};
</script>

<style scoped lang='scss'>
.page-container {
  height: calc(100vh - 120px);
  display: flex;
  flex-direction: row;
}
.page-info {
  background: #f7f8fa;
  height: 100%;
  flex: 1;
  padding: 30px;
  overflow-y: scroll;
}
.info-container {
  background: #fff;
  min-height: 100%;
}
.menu {
  padding: 20px;
  span:last-child {
    border-right: 1px solid rgba(213, 213, 213, 1);
    border-radius: 0px 4px 4px 0px;
  }
  span:first-child {
    border-radius: 4px 0px 0px 4px;
  }
}
.menu-item {
  width: 120px;
  height: 30px;
  text-align: center;
  line-height: 30px;
  font-size: 12px;
  color: #35343a;
  font-weight: 400;
  border: 1px solid rgba(213, 213, 213, 1);
  border-right: none;
  cursor: pointer;
}
.isActive {
  background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
  color: #ffffff;
}
</style>