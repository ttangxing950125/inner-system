<template>
  <div class="page-header flex-row-bw">
    <!-- 页头 -->
    <div class="flex-row logo">
      <span class="name">Deloitte<span class="icon" /></span>
      <span class="line"></span>
      <span class="title">数据资产平台</span>
    </div>

    <div>
      <!-- <el-autocomplete
        class="search"
        v-model="query"
        prefix-icon="el-icon-search"
        placeholder="输入关键字/英文名称/中文名称"
      ></el-autocomplete> -->
      <el-select
        v-model="query.type"
        style="width: 120px; margin-right: 10px"
        @change="changeSelect"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <!-- <el-input
        v-model="query.keyWord"
        placeholder="输入关键字/英文名称/中文名称"
        prefix-icon="el-icon-search"
        maxlength="32"
        class="search"
        clearable
        @keyup.native.enter="handleQuery"
      >
       :trigger-on-focus="false"
      </el-input> -->
      <!-- <el-autocomplete
        v-model="query.keyWord"
        :fetch-suggestions="querySearchAsync"
        @select="handleSelect"
        placeholder="输入关键字/英文名称/中文名称"
        prefix-icon="el-icon-search"
        :maxlength="32"
        class="search"
      ></el-autocomplete> -->
      <el-select
        v-model="query.keyWord"
        filterable
        remote
        reserve-keyword
        clearable
        placeholder="输入关键字/英文名称/中文名称"
        :remote-method="remoteMethod"
        :loading="loading"
        @change="handleQuery"
        @focus="changePage"
        class="search"
      >
        <el-option
          v-for="(item, index) in restaurants"
          :key="index"
          :label="item.name + '-' + item.code"
          :value="item.code"
        >
        </el-option>
      </el-select>
    </div>
    <div style="color: #fff">
      <i class="el-icon-user-solid" style="font-size: 14px"></i>
      <span class="user-name">{{ userName }}</span>
      <!-- <i class="el-icon-arrow-down pointer" style="font-size: 8px"></i> -->
      <el-dropdown trigger="click">
        <i class="el-icon-arrow-down" style="color: #fff"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item
            divided
            @click.native="logout"
            style="font-size: 12px"
            >退出登录</el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import { EventBus } from "@/evenBus/dataExtractionBus.js";
import { getForTop } from "@/api/dataExtraction/index.js";
// import { mapGetters } from "vuex";
// import Breadcrumb from "@/components/Breadcrumb";
// import TopNav from "@/components/TopNav";
// import Hamburger from "@/components/Hamburger";
// import Screenfull from "@/components/Screenfull";
// import SizeSelect from "@/components/SizeSelect";
// import Search from "@/components/HeaderSearch";
// import deloitteGit from "@/components/Deloitte/Git";
// import deloitteDoc from "@/components/Deloitte/Doc";

export default {
  // components: {
  //   Breadcrumb,
  //   TopNav,
  //   Hamburger,
  //   Screenfull,
  //   SizeSelect,
  //   Search,
  //   deloitteGit,
  //   deloitteDoc,
  // },
  // computed: {
  //   ...mapGetters(["sidebar", "avatar", "device"]),
  //   setting: {
  //     get() {
  //       return this.$store.state.settings.showSettings;
  //     },
  //     set(val) {
  //       this.$store.dispatch("settings/changeSetting", {
  //         key: "showSettings",
  //         value: val,
  //       });
  //     },
  //   },
  //   topNav: {
  //     get() {
  //       return this.$store.state.settings.topNav;
  //     },
  //   },
  // },
  data() {
    return {
      query: {
        keyWord: "",
        type: 0,
        limitNum: 20,
      },
      loading: false,
      restaurants: [],
      options: [
        {
          label: "全部",
          value: 0,
        },
        {
          label: "基础层",
          value: 1,
        },
        {
          label: "中间层",
          value: 2,
        },
        {
          label: "指标层",
          value: 3,
        },
        {
          label: "主体信息",
          value: 4,
        },
      ],
      userName: Cookies.get("username"), //用户名称
    };
  },
  methods: {
    changeSelect() {
      this.query.keyWord = "";
      this.restaurants = [];
    },
    remoteMethod() {
      getForTop(this.query).then((res) => {
        if (res.code == 200) {
          this.restaurants = res.data.length
            ? res.data
            : [{ name: "暂无数据", code: "" }];
        }
      });
    },
    changePage() {
      EventBus.$emit("headerQuery");
      console.log("促发了这里了");
    },
    handleQuery() {
      let res = null;
      this.restaurants.forEach((item) => {
        if (item.code == this.query.keyWord) {
          res = item;
        }
      });
      if (res != null) {
        EventBus.$emit("headerQuery");
        EventBus.$emit("headerQueryPage", res);
      }
      console.log(res, "页头查询将要传递的参数");
    },
    async logout() {
      this.$confirm("确定注销并退出系统吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$store.dispatch("LogOut").then(() => {
          location.href = "/index";
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.page-header {
  height: 64px;
  padding: 0 30px;
  align-items: center;
}
.line {
  width: 1px;
  height: 14px;
  border-right: 1px solid rgba(242, 251, 255, 1);
  margin: 0 20px;
}
.logo {
  align-items: center;
  color: #fff;
  .name {
    font-size: 24px;
    font-weight: 700;
  }
  .title {
    font-size: 18px;
    color: #f2fbff;
    font-weight: 400;
  }
  .icon {
    display: inline-block;
    width: 5.2px;
    height: 5.2px;
    background: #9edf28;
    border-radius: 50%;
  }
}
.search {
  width: 600px;
  height: 32px;
  border-radius: 4px;
}

::v-deep .el-input--medium .el-input__inner {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(112, 124, 148, 1);
  color: #fff;
}
.user-name {
  font-size: 12px;
  color: #ffffff;
  font-weight: 400;
  padding: 0 12px;
}
</style>
