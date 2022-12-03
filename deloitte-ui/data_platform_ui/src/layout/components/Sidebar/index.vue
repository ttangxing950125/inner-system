<template>
  <div class="flex-row el-menu-view">
    <!-- 预留 -->
    <div class="menu1" v-show="checkShow && statistics">
      <span
        v-for="(item, index) in checkMenu"
        :key="index"
        @click="handleCheck(index)"
        :class="[
          index == checkIndex ? 'menu1-is-check' : 'not-cehck',
          'pointer',
        ]"
      >
        {{ item.name }}
      </span>
    </div>
    <div :style="{ marginLeft: checkShow ? '80px' : '290px' }">
      <el-menu
        mode="horizontal"
        :default-active="activeIndex"
        text-color="#e5e5e5"
        active-text-color="#fff"
        @select="handleChange"
      >
        <el-menu-item
          v-for="(item, index) in urlList"
          :key="index"
          :index="index + ''"
          style="margin-right: 62px"
          @click="handlMenuItem(item.path, index)"
        >
          {{ item.name }}
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
import { EventBus } from "@/evenBus/dataExtractionBus.js";
export default {
  data() {
    return {
      checkIndex: 0,
      hasShow: ["0", "2", "3", "5"],
      statistics: true,
      activeIndex: "0",
      checkShow: true,
      checkMenu: [
        {
          name: "全部",
          type: 1,
        },
        {
          name: "我的库表",
          type: 2,
        },
        {
          name: "最近浏览",
          type: 3,
        },
      ],
      urlList: [
        {
          name: "数据字典",
          path: "/index",
        },
        {
          name: "数据质检",
          path: "/qualityTesting/index",
        },
        {
          name: "数据统计分析",
          path: "/statisticalAnalysis/index",
        },
        {
          name: "数据提取",
          path: "/dataExtraction/index",
        },
        {
          name: "数据配置",
          path: "/dataConfiguration/index",
        },
        {
          name: "参数配置",
          path: "/parameterConfiguration/index",
        },
      ],
    };
  },
  watch: {
    activeIndex(val) {
      this.checkShow = this.hasShow.indexOf(val) != -1 ? true : false;
      console.log(this.checkShow, " this.checkShow this.checkShow");
    },
  },
  created() {
    //退出登陆后 也是上次打开的主菜单
    this.activeIndex = localStorage.getItem("activeIndex") ?? "0";
    //解决数据统计分析时候的 菜单切换的bug
    this.statistics = this.activeIndex == 2 ? false : true;
    //解决刷新介入页面  导航和路由不一致
    let index = Number(this.activeIndex);
    this.$router.push(this.urlList[index].path);
  },
  mounted() {
    let that = this;
    //最上面查询的enter事件
    EventBus.$on("headerQuery", () => {
      that.activeIndex = "3";
      this.$router.push("/dataExtraction/index");
    });
    //数据统计分析的tab切换事件
    EventBus.$on("hanleStatisticsTab", (index) => {
      if (index == 1 || index == 4) {
        this.statistics = true;
      } else {
        this.statistics = false;
      }
    });
  },
  methods: {
    //解决菜单反复跳转 查询的时候不能切换到数据提取
    handleChange(val) {
      this.activeIndex = val;
    },
    //我的库表
    handleCheck(index) {
      this.checkIndex = index;
      //切换 全部 我的库表 最近浏览
      EventBus.$emit("hanleLibrary", this.checkIndex);
    },
    //点击菜单
    handlMenuItem(path, index) {
      this.checkIndex = 0;
      this.statistics = index == 2 ? false : true;
      this.$router.push(path);
      localStorage.setItem("activeIndex", index);
    },
  },
};
</script>
<style scoped lang="scss">
.menu1 {
  width: 200px;
  height: 35px;
  background: #444e5a;
  border-radius: 2px;
  margin-left: 10px;
  font-size: 12px;
  color: #e5e5e5;
  font-weight: 400;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}
.menu1-is-check {
  font-size: 16px;
  color: #ffb400;
  font-weight: 700;
}
.not-check {
  font-size: 12px;
  color: #e5e5e5;
  font-weight: 400;
}
.el-menu-view {
  height: 56px;
  align-items: center;
  // padding-left: 266px;
}
.el-menu {
  background: none;
  border: none;

  .el-menu-item {
    height: 24px;
    line-height: 24px;
    font-size: 12px;
  }
  .el-menu-item :hover {
    background: none;
  }
}
::v-deep .el-menu-item:hover {
  background: none !important;
}
::v-deep .is-active {
  background: none !important;
  color: #fff;
  font-weight: 700;
}
</style>
