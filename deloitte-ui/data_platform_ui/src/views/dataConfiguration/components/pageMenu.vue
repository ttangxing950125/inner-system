<template>
  <div class="content-box">
    <div class="flex-row">
      <el-menu
        v-if="showMenu"
        mode="horizontal"
        :default-active="letfActiveIndex"
        text-color="#e5e5e5"
        active-text-color="#fff"
        class="flex-row pleft-menu"
      >
        <el-menu-item
          v-for="(item, index) in dataSetingMenu"
          class="left-menu"
          :key="index"
          :index="index + ''"
          @click="leftMenuItem(item.path, index)"
          >{{ item.name }}</el-menu-item
        >
      </el-menu>
    </div>
    <el-input
      v-model="query"
      size="mini"
      class="query-input mt20"
      placeholder="输入关键字查询"
      prefix-icon="el-icon-search"
      clearable
      :maxlength="64"
      @change="getBtn"
      @keyup.enter.native="getBtn"
    ></el-input>
    <el-collapse class="mt20" v-model="activeNames">
      <el-menu
        :default-active="activeIndex"
        class="menu"
        text-color="#FFF"
        active-text-color="#FFB400"
      >
        <el-submenu
          v-for="(item, idx) in menuList"
          :key="idx + 's'"
          :index="idx + ''"
        >
          <template slot="title">{{ item.title }}</template>
          <div v-if="!btnShow">
            <el-menu-item
              v-for="(i, x) in item.child"
              :key="x + 'c'"
              :index="idx + '-' + x"
              @click="handleMenuItem(i.title)"
              >{{ i.title }}</el-menu-item
            >
          </div>
          <div v-else>
            <div class="flex ml10">
              <div
                v-for="(item, index) in searData"
                :key="index"
                class="Symbol mr10"
                @click="getData(item)"
              >
                <nobr>{{ item.name }}</nobr>
              </div>
            </div>
          </div>
        </el-submenu>
      </el-menu>
    </el-collapse>
  </div>
</template>
  
  <script>
import { list } from "@/api/dataSeting";
export default {
  name: "pageMenu",
  props: {
    menus: {
      type: Array,
      default: null,
    },
    dataSetingMenu: {
      type: Array,
      default: null,
    },
    showMenu: {
      type: Boolean,
      default: false,
    },
    index: {
      type: Number,
      default: 2,
    },
  },
  data() {
    return {
      activeNames: ["1"],
      activeIndex: "0-0",
      letfActiveIndex: "0",
      menuList: this.menus,
      query: "",
      btnShow: false,
      searData: [],
    };
  },
  methods: {
    handleMenuItem(title) {
      console.log(title, "页面菜单选中项目");
    },
    leftMenuItem(title) {
      console.log(title, "左侧菜单选中项目");
    },
    // 获取左侧菜单搜索btn
    getBtn() {
      if (this.query.length === 0) {
        this.btnShow = false;
      } else {
        this.btnShow = true;
        try {
          this.$modal.loading("Loading...");
          const parmas = {
            hierarchy: this.index,
            searchName: this.query,
            pageNum: 1,
            pageSize: 20,
          };
          list(parmas).then((res) => {
            const { data } = res;
            this.searData = data.records;
          });
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
          this.$modal.closeLoading();
        }
      }
    },
    // 获取左侧菜单数据 传给父组件
    getData(row) {
      this.$eventBus.$emit("getData", row);
    },
    menusFun(row) {
      this.menuList = row;
    },
  },
};
</script>
  
  <style lang='scss' scoped>
.content-box {
  width: 220px;
  min-height: 100%;
  padding: 0 10px;
  background-image: linear-gradient(296deg, #707c94 0%, #566272 99%);
}

//格式化样式
::v-deep .el-collapse {
  border: none;
}
::v-deep .el-collapse-item__header {
  background: none;
  color: #fff;
  font-size: 14px;
  font-weight: 400;
  padding-left: 10px;
}

//菜单栏样式格式化
::v-deep .el-menu {
  background: rgba(68, 78, 90, 0.34);
  border-radius: 4px;
  border: none;
  .el-menu-item {
    height: 36px;
    line-height: 36px;
    font-size: 12px;
    padding-left: 30px !important;
  }
  .el-menu-item:hover {
    background: #444e5a !important;
  }
  .el-menu-item.is-active {
    background: #444e5a !important;
    color: #fff;
  }
  .el-submenu__title {
    background: rgba(68, 78, 90, 0.34) !important;
    height: 36px;
    line-height: 36px;
    color: #e5e5e5;
    font-size: 12px !important;
    padding-left: 10px !important;

    i {
      color: #bfc3c7 !important;
      font-size: 14px;
    }
    .el-submenu__icon-arrow {
      right: 10px !important;
    }
  }
  .left-menu {
    padding-left: 10px !important;
    padding-right: 10px !important;
    margin-right: 10px;
  }
}

// 数据配置 头部菜单格式化
.pleft-menu {
  background: none !important;
  ::v-deep .el-menu-item.is-active {
    background: none !important;
    color: #fff;
  }
}

::v-deep .el-collapse-item__wrap {
  background: none;
  border: none;
}
::v-deep .el-collapse-item__header {
  border: none;
}
.query-input {
  ::v-deep .el-input__inner {
    &::placeholder {
      color: #ffffff;
      font-size: 12px;
    }
    color: #ffffff;
    background-color: #707c94 !important;
    border: none;
    border-bottom: none;
  }
}
.flex {
  flex-wrap: wrap;
  align-content: flex-start;
}
.Symbol {
  height: 26px;
  background-image: linear-gradient(168deg, #ffffff 0%, #b2c1d2 100%);
  border-radius: 2px;
  font-family: MicrosoftYaHei;
  font-size: 12px;
  color: #6d798f;
  letter-spacing: 0;
  font-weight: 400;
  line-height: 23px;
  cursor: pointer;
  padding: 0 6px;
  margin-top: 10px;
}
</style>