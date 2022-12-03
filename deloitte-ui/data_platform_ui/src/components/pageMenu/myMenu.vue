<template>
  <div class="content-box">
    <el-menu
      :default-active="defaultindex"
      text-color="#fff"
      active-text-color="#ffb400"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(68, 78, 90, 0.34)"
      @select="hanleMenu"
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
              <span
                v-if="type == 0"
                @click.stop="handlelike(item, index2, index3)"
                class="like-btn"
                >收藏</span
              >
              <span
                v-if="type == 1"
                @click.stop="handleDel(item3)"
                class="like-btn"
                >移除</span
              >
            </div>
          </el-menu-item>
        </el-submenu>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import {
  menuList,
  addMenu,
  myMenu,
  delMyMenu,
} from "@/api/dataDictionary/index.js";
import { EventBus } from "@/evenBus/dataExtractionBus.js";

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
      type: 0, //0全部 1我的库表 2最近浏览
      loading: true,
      defaultindex: "1-1-1",
    };
  },
  created() {
    this.getMenu();
  },

  mounted() {
    EventBus.$on("hanleLibrary", (index) => {
      this.type = index;
      this.menus = [];
      this.defaultindex = "1-1-1"; //解决切换全部 我的库表的时候菜单没有回归到第一个的bug
      index == 0 && this.getMenu(); //获取全部菜单
      index == 1 && this.myLibraryTable(); //获取我的库表 也就是收藏了的菜单
      console.log("选中的index1111", index);
    });
  },
  methods: {
    handleMenuItem(i) {
      this.$emit("clickMenu", i);
    },
    //收藏
    handlelike(i, index2, index3) {
      let query = {
        child: {
          child: {
            code: i.child[index2].child[index3].code,
            parentCode: i.child[index2].child[index3].parentCode,
          },
          code: i.child[index2].code,
          parentCode: i.child[index2].parentCode,
        },
        code: i.code,
        parentCode: i.parentCode,
      };

      addMenu(query).then((res) => {
        res.code == 200
          ? this.$message.success("操作成功")
          : this.$message.error("操作失败");
      });
    },

    //移除
    handleDel(item) {
      delMyMenu(item.id).then((res) => {
        if (res.code == 200) {
          this.$message.success("操作成功");
          this.myLibraryTable();
        } else {
          this.$message.error("操作失败");
        }
      });
    },
    //获取我的库表
    myLibraryTable() {
      try {
        this.loading = true;
        myMenu().then((res) => {
          let { code, data } = res;
          if (code == 200 && data != null) {
            this.menus = res.data.child;
            this.$emit("clickMenu", res.data.child[0].child[0].child[0]);
          }
        });
      } finally {
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      }
    },
    //获取全部菜单
    getMenu() {
      let that = this;
      try {
        this.loading = true;
        menuList().then((res) => {
          if (res.code == 200 && res.data != null) {
            that.menus = res.data.child;
            this.$emit("clickMenu", res.data.child[0].child[0].child[0]);
          }
        });
      } finally {
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      }
    },
    // 解决切换全部 我的库表的时候菜单没有回归到第一个的bug
    hanleMenu(index) {
      this.defaultindex = index;
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