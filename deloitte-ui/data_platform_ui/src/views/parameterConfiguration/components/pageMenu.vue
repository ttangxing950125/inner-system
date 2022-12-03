<template>
  <div class="content-box">
    <el-menu
      :default-active="'1-1-1'"
      text-color="#fff"
      active-text-color="#ffb400"
    >
      <el-submenu
        v-for="(item, index1) in menus"
        :key="index1 + 'a'"
        :index="index1 + 1 + ''"
      >
        <template slot="title">
          <span>{{ item.name }}</span>
        </template>

        <el-submenu
          v-for="(item2, index2) in item.child"
          :key="index2 + 'b'"
          :index="index1 + 1 + '-' + (index2 + 1)"
        >
          <template slot="title">
            <span>{{ item2.name }}</span>
          </template>
          <el-menu-item
            v-for="(item3, index3) in item2.child"
            :index="index1 + 1 + '-' + (index2 + 1) + '-' + (index3 + 1)"
            :key="index3 + 'c'"
            @click="handleMenuItem(item3)"
          >
            <div class="flex-row-bw" style="width: 130%; padding-right: 12px">
              <span> {{ item3.name }}</span>
              <span @click.stop="handlelike" class="like-btn">收藏</span>
            </div>
          </el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-submenu index="12" v-if="ruleMenu.length">
        <template slot="title">
          <span>质检规则</span>
        </template>
        <el-menu-item-group>
          <el-menu-item
            v-for="(item12, index12) in ruleMenu"
            :key="index12 + 'gg'"
            :index="'12' + '-' + index12"
            @click="handleMenuItem(item12)"
            >{{ item12.name }}</el-menu-item
          >
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { menuList } from "@/api/dataDictionary/index.js";
import { getBaseMenu } from "@/api/paramsSeting/index.js";
export default {
  name: "pageMenu",
  props: {
    menu: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      menus: [],
      ruleMenu: [],
    };
  },
  created() {
    this.getMenu();
    this.getBaseMenu();
  },
  methods: {
    handleMenuItem(i) {
      this.$emit("clickMenu", i);
      console.log(i, "点击菜单");
    },

    handlelike() {
      alert("收藏");
    },
    getBaseMenu() {
      getBaseMenu().then((res) => {
        if (res.code == 200) {
          this.ruleMenu = res.data.child;
        }
      });
    },
    //获取menu list
    getMenu() {
      let that = this;
      try {
        this.$modal.loading("Loading...");
        menuList().then((res) => {
          if (res.code == 200) {
            that.menus = res.data.child;
            this.$emit("clickMenu", res.data.child[0].child[0].child[0]);
          }
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
  },
};
</script>

<style lang='scss' scoped>
.like-btn {
  color: #fff;
  font-size: 10px;
  width: 30px;
  text-align: right;
  display: none;
}
.like-btn:hover {
  color: #ffb400;
}
.content-box {
  width: 220px;
  min-height: 100%;
  padding: 10px 10px;
  background-image: linear-gradient(296deg, #707c94 0%, #566272 99%);
}
::v-deep .el-menu {
  background: none !important;
  border-right: none;
  .el-icon-arrow-down {
    color: #fff;
    font-size: 14px;
    font-weight: 400px !important;
  }
  .el-submenu__title {
    height: 40px;
    line-height: 40px;
    font-size: 14px !important;
    font-weight: 400px !important;
    color: #fff !important;
    padding-left: 8px !important;
  }
  .el-menu-item {
    height: 36px;
    line-height: 36px;
    font-size: 12px;
    padding-left: 30px !important;
  }
  .el-submenu__title:hover {
    background: none !important;
  }
  .el-menu-item:hover {
    background: #444e5a !important;
  }
  .el-menu-item.is-active {
    background: #444e5a !important;
    color: #fff;
    .like-btn {
      color: #fff;
      font-size: 10px;
      width: 30px;
      text-align: right;
      display: block;
    }
  }
}
::v-deep .el-menu-item-group__title {
  padding: 0;
}
::v-deep .el-menu--inline {
  background: rgba(68, 78, 90, 0.34) !important;
  border-radius: 6px;

  .el-submenu__title {
    background: rgba(68, 78, 90, 0.34) !important;
    border-top-left-radius: 6px;
    border-top-right-radius: 6px;
    font-size: 12px !important;
    font-weight: 400 !important;
  }
}
</style>