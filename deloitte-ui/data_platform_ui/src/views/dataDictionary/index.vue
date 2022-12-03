<template>
  <!-- 数据字典 -->
  <div class="container flex-row">
    <my-menu @clickMenu="clickMenu"></my-menu>
    <!-- <page-menu @clickMenu="clickMenu" :menu="menus"></page-menu> -->
    <div class="container-info padding30">
      <div class="info-content">
        <icon-title>{{ pageName }}</icon-title>
        <!--  -->
        <div class="flex-row" style="margin: 14px 0 30px 0">
          <div class="margin-right70">
            <span class="font1-700">数据更新频率：</span
            ><span class="font2-400">{{ updateFrequency }}</span>
          </div>
          <div class="margin-right70">
            <span class="font1-700">更新时间：</span
            ><span class="font2-400">{{ parseTime(updatedTime) }}</span>
          </div>
          <div>
            <span class="font1-700">数据更新说明：</span
            ><span class="font2-400">{{ remark }}</span>
          </div>
        </div>
        <!-- 概述 -->
        <div>
          <line-title class="margin-b10">概述</line-title>
          <div
            class="font2-400"
            style="margin-bottom: 7px"
            v-for="(item, index) in summary"
            :key="index + 'e'"
          >
            {{ index + 1 + "." + item }}
          </div>
        </div>
        <!-- 字段信息 -->
        <div class="margin-top30">
          <line-title class="margin-b10">字段信息</line-title>
          <el-input
            v-model="queryParams.keyWord"
            size="mini"
            class="query-input"
            placeholder="输入字段代码/字段中文名称关键词进行搜索"
            prefix-icon="el-icon-search"
            clearable
            :maxlength="64"
            @keyup.native.enter="handQuery"
            @change="handQuery"
          ></el-input>
        </div>
        <!-- 表格 -->
        <el-table
          :data="tableData"
          stripe
          style="width: 100%; margin-top: 20px"
          :header-cell-style="headerStyle"
          :cell-style="cellStyles"
          v-loading="loading"
        >
          <el-table-column
            width="100px"
            type="index"
            label="展示顺序"
            align="center"
          />
          <el-table-column
            v-for="(item, index) in tableHeader"
            :key="index + 'a'"
            :prop="item.props"
            :label="item.label"
            :align="item.align"
          />
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import {
  updateInfo,
  baseData,
  middleData,
  applyData,
} from "@/api/dataDictionary/index.js";
export default {
  data() {
    return {
      pageName: "",
      pageType: "1", //1基础  2中间 3指标
      //上面基础信息
      remark: "-", //数据更新说明
      updateFrequency: "-", //数据更新平率
      updatedTime: "-", //更新时间
      menus: [],
      loading: true,
      pageTypeList: {
        base_data_dic: "1",
        middle_data_dic: "2",
        apply_data_dic: "3",
      },

      titleType: {
        1: "基础层字段表_",
        2: "中间层字段表_",
        3: "指标层字段表_",
      },
      menuCode: "",
      //关键字查询
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWord: "",
      },
      total: 0,
      tableData: [],
    };
  },
  computed: {
    tableHeader() {
      //基础层
      if (this.pageType == "1") {
        return [
          {
            label: "字段代码",
            props: "code",
            align: "left",
          },
          {
            label: "字段中文名称",
            props: "name",
            align: "left",
          },
          {
            label: "数据类型",
            props: "dataType",
            align: "left",
          },
          {
            label: "精度",
            props: "accuracy",
            align: "center",
          },
          {
            label: "单位",
            props: "unit",
            align: "center",
          },
          {
            label: "币种",
            props: "currency",
            align: "center",
          },
        ];
      }
      //中间层
      if (this.pageType == "2") {
        return [
          {
            label: "中间层代码",
            props: "code",
            align: "left",
          },
          {
            label: "中间层中文名称",
            props: "name",
            align: "left",
          },
          {
            label: "配置公式",
            props: "evidenceFormulaDescribe",
            align: "left",
          },
          {
            label: "精度",
            props: "accuracy",
            align: "center",
          },
          {
            label: "单位",
            props: "unit",
            align: "center",
          },
        ];
      }
      //指标层
      if (this.pageType == "3") {
        return [
          {
            label: "指标层代码",
            props: "code",
            align: "left",
          },
          {
            label: "指标层中文名称",
            props: "name",
            align: "left",
          },
          {
            label: "使用场景",
            props: "businessScene",
            align: "left",
          },
          {
            label: "配置公式",
            props: "evidenceFormulaDescribe",
            align: "left",
          },
          {
            label: "精度",
            props: "accuracy",
            align: "center",
          },
          {
            label: "单位",
            props: "unit",
            align: "center",
          },
        ];
      }
    },
    //概述
    summary() {
      if (this.pageType == "1") {
        return [
          "基础层字段表，“一般企业/金融机构”，指代报表类型",
          "基础层字段表，“其他”，指与主表无法构成勾稽关系的经营类字段类型",
          "基础层字段表，“其他”，同类数据（如企业层级）不按产品线区分,通过产品标签的字段进行管理",
        ];
      }
      if (this.pageType == "2") {
        return [
          "中间层字段表，“一般企业/金融机构”，指代报表类型",
          "中间层字段表，“其他”，指与主表无法构成勾稽关系的经营类字段类型",
          "中间层字段表，“其他”，同类数据（如企业层级）不按产品线区分,通过产品标签的字段进行管理",
        ];
      }
      if (this.pageType == "3") {
        return [
          "指标层字段表，“一般企业/金融机构”，指代报表类型",
          "指标层字段表，“其他”，指与主表无法构成勾稽关系的经营类字段类型",
          "指标层字段表，“其他”，同类数据（如企业层级）不按产品线区分,通过产品标签的字段进行管理",
        ];
      }
      return [];
    },
  },
  mounted() {
    this.getUpdateInfo(); //获取更新信息
  },
  methods: {
    handQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      // pageType: "1", //1基础  2中间 3指标
      this.pageType == "1" && this.getDaseData();
      this.pageType == "2" && this.getMiddleData();
      this.pageType == "3" && this.getApplyData();
    },

    //获取基础层表格
    getDaseData() {
      try {
        this.$modal.loading("Loading...");

        this.loading = true;
        let query = {
          code: this.menuCode,
          keyWord: this.queryParams.keyWord,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        baseData(query).then((res) => {
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
          this.loading = false;
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    //获取中间层字段表
    getMiddleData() {
      try {
        this.$modal.loading("Loading...");
        this.loading = true;
        let query = {
          code: this.menuCode,
          keyWord: this.queryParams.keyWord,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        middleData(query).then((res) => {
          if (res.code == 200 && res.data != null) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
          this.loading = false;
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    //获取指标层
    getApplyData() {
      try {
        this.$modal.loading("Loading...");
        this.loading = true;
        let query = {
          code: this.menuCode,
          keyWord: this.queryParams.keyWord,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        applyData(query).then((res) => {
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
          this.loading = false;
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    //获取更新信息
    getUpdateInfo() {
      updateInfo().then((res) => {
        if (res.code == 200) {
          let { remark, updateFrequency, updatedTime } = res.data;
          this.remark = remark || "-";
          this.updateFrequency = updateFrequency || "-";
          this.updatedTime = updatedTime || "-";
        }
      });
    },
    //左侧菜单点击事件
    clickMenu(i) {
      this.pageType = this.pageTypeList[i.parentCode];
      this.pageName = this.titleType[this.pageType] + i.name;
      this.menuCode = i.code;
      this.queryParams.pageNum = 1;
      this.queryParams.keyWord = "";
      this.getList();
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
</style>