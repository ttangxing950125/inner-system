<template>
  <!-- 参数配置 -->
  <div class="container flex-row">
    <page-menu @clickMenu="clickMenu"></page-menu>
    <!-- 基础层 -->
    <foundation-layer
      v-if="activeType == '1'"
      :menuCode="menuCode"
      ref="foundation"
    ></foundation-layer>
    <!-- 中间层 -->
    <mesosphere
      v-if="activeType == '2'"
      :menuCode="menuCode"
      ref="mesosphere"
    ></mesosphere>
    <!-- 指标层 -->
    <indicator-layer
      v-if="activeType == '3'"
      :menuCode="menuCode"
      ref="indicator"
    ></indicator-layer>
    <!-- 质检规则 -->
    <quality-inspection-rules
      v-if="activeType == '4'"
      :menuCode="menuCode"
      ref="rules"
    ></quality-inspection-rules>
  </div>
</template>

<script>
import pageMenu from "./components/pageMenu.vue";
import mesosphere from "./components/mesosphere.vue";
import foundationLayer from "./components/foundationLayer.vue";
import indicatorLayer from "./components/indicatorLayer.vue";
import QualityInspectionRules from "./components/qualityInspectionRules.vue";
export default {
  components: {
    pageMenu,
    mesosphere,
    foundationLayer,
    indicatorLayer,
    QualityInspectionRules,
  },
  data() {
    return {
      activeType: "", //1基础层   2中间层   3指标层
      menuCode: "",
      pageName: "",
      pageTypeList: {
        base_data_dic: "1",
        middle_data_dic: "2",
        apply_data_dic: "3",
        model_check: "4", //质检规则
      },

      titleType: {
        1: "基础层字段表_",
        2: "中间层字段表_",
        3: "指标层字段表_",
      },
    };
  },
  methods: {
    //数据来源
    changeSource(val) {
      console.log(val, "数据来源");
    },
    //选中菜单
    clickMenu(i) {
      this.activeType = this.pageTypeList[i.parentCode];
      this.pageName = this.titleType[this.activeType] + i.name;
      this.menuCode = i.code;
      this.$nextTick(() => {
        this.activeType == "1" && this.$refs.foundation.handleQuery(); //基础层
        this.activeType == "2" && this.$refs.mesosphere.handleQuery(); //中间层
        this.activeType == "3" && this.$refs.indicator.handleQuery(); //中间层
        this.activeType == "4" && this.$refs.rules.handleQuery(); //质检规则
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
}
.container-info {
  width: calc(100% - 220px);
  height: 100%;
  overflow-y: scroll;
}
.info-content {
  background: #fff;
  width: 100%;
  padding: 20px 20px 0 20px;
}
.query-input {
  width: 282px;
  font-size: 12px;
}
.add-btn {
  background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
  color: #fff;
  font-size: 12px;
}
</style>