<template>
  <div class="page-container">
    <!-- 菜单  业务场景 基础质量   才显示-->
    <my-menu
      @clickMenu="clickMenu"
      v-if="menuShow.indexOf(activeIdex) != -1"
    ></my-menu>
    <div class="page-info">
      <div class="info-container">
        <div class="flex-row menu">
          <span
            :class="['menu-item', index == activeIdex ? 'isActive' : '']"
            :style="{ cursor: index == 2 || index == 3 ? 'text' : 'pointer' }"
            v-for="(item, index) in menuList"
            :key="index + 'q'"
            @click="handleTab(index)"
            >{{ item }}</span
          >
        </div>
        <!-- 总览 -->
        <overview
          v-if="activeIdex == 0"
          @subject="handleSubject"
          @handleCode="handleCode"
        ></overview>
        <!-- 业务场景 -->
        <business
          v-if="activeIdex == 1"
          :menuCode="menuCode"
          :pageType="pageType"
          ref="business"
        ></business>
        <!-- 单个主体 -->
        <single-body v-if="activeIdex == 2" :info="subjectInfo"></single-body>
        <!-- 单个字段 -->
        <single-field v-if="activeIdex == 3" :info="codeInfo"></single-field>
        <!-- 基础质量 -->
        <foundation-quality
          v-if="activeIdex == 4"
          :menuCode="menuCode"
          :pageType="pageType"
          :pageName="pageName"
          ref="foundation"
        ></foundation-quality>
        <!-- 勾稽关系 -->
        <relationship v-if="activeIdex == 5"></relationship>
      </div>
    </div>
  </div>
</template>

<script>
import overview from "./components/overview.vue"; //总览
import business from "./components/business.vue"; //业务场景
import singleBody from "./components/singleBody.vue"; //单个主体
import singleField from "./components/singleField.vue"; //单个字段
import foundationQuality from "./components/foundationQuality.vue"; //基础质量
import relationship from "./components/relationship.vue"; //勾稽关系
import { EventBus } from "@/evenBus/dataExtractionBus.js";
export default {
  components: {
    overview,
    business,
    singleBody,
    singleField,
    foundationQuality,
    relationship,
  },
  data() {
    return {
      activeIdex: 0,
      menuShow: [1, 4], //这些是要显示左边导航的tab的下标 请根据实际情况修改
      menuList: [
        "总览",
        "业务场景",
        "单个主体",
        "单个字段",
        "基础质量",
        "勾稽关系",
      ],
      titleType: {
        1: "基础层字段表_",
        2: "中间层字段表_",
        3: "指标层字段表_",
      },
      //基础层1 中间层2 指标层3
      pageTypeList: {
        base_data_dic: "1",
        middle_data_dic: "2",
        apply_data_dic: "3",
      },
      pageType: "1", //1基础  2中间 3指标
      menuCode: "",
      pageName: "",
      subjectInfo: {}, //单个主体
      codeInfo: {}, //单个字段
    };
  },
  methods: {
    //点击总览里面的单个主体
    handleSubject(row) {
      this.activeIdex = 2;
      this.subjectInfo = row;
    },
    //点击总览里面的字段代码
    handleCode(row) {
      this.activeIdex = 3;
      this.codeInfo = row;
    },
    //
    handleTab(index) {
      //单个主体 单个字段不能点击
      if (index == 2 || index == 3) {
        return;
      }
      this.activeIdex = index;
      EventBus.$emit("hanleStatisticsTab", index);
    },
    //点击左侧菜单
    clickMenu({ code, parentCode, name }) {
      this.menuCode = code;
      this.pageType = this.pageTypeList[parentCode]; //设置默认类型
      this.pageName = this.titleType[this.pageType] + name; //设置默认选中的菜单名字
      //业务场景
      this.$nextTick(() => {
        //更新表格数据
        this.activeIdex == 1 && this.$refs.business.handleQuery();
        this.activeIdex == 4 && this.$refs.foundation.handleQuery();
      });
    },
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