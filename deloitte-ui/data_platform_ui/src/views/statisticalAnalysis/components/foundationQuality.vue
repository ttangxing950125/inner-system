<template>
  <!-- 基础质量 -->
  <div class="container">
    <icon-1-title>{{ pageName }}</icon-1-title>
    <!--  -->
    <div class="flex-row" style="margin: 14px 0 30px 0">
      <div class="margin-right70">
        <span class="font1-700">数据质检频率：</span
        ><span class="font2-400">{{ updateFrequency }}</span>
      </div>
      <div class="margin-right70">
        <span class="font1-700">质检更新时间：</span
        ><span class="font2-400">{{ parseTime(updatedTime) }}</span>
      </div>
      <div>
        <span class="font1-700">数据质检说明：</span
        ><span class="font2-400">{{ remark }}</span>
      </div>
    </div>
    <!-- 条建查询 -->
    <line-title class="margin-b10">检测范围</line-title>
    <div class="query">
      <el-form ref="form" :model="queryParams" inline>
        <el-form-item label-width="0px">
          <el-input
            size="mini"
            clearable
            v-model="queryParams.keyWord"
            placeholder="输入关键字进行搜索"
            prefix-icon="el-icon-search"
            style="width: 282px; margin-right: 20px"
            @keyup.native.enter="handleQuery"
            @change="handleQuery"
          ></el-input>
        </el-form-item>
        <el-form-item label="年份">
          <year-select @change="changeYear" style="width: 130px"></year-select>
        </el-form-item>

        <!-- <el-form-item style="margin-left: 12px" label-width="0px">
          <span class="data-btn">数据质量统计</span>
        </el-form-item> -->
      </el-form>
    </div>
    <!-- 表格 -->
    <line-title class="margin-b10">质检结果</line-title>
    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      :header-cell-style="headerStyles"
      :cell-style="cellStyles"
      v-loading="loading"
    >
      <el-table-column prop="code" label="字段代码" align="left" />
      <el-table-column prop="name" label="字段中文名称" align="left" />
      <el-table-column prop="reportDate" label="数据时间" align="center" />
      <el-table-column prop="accuracy" label="精度" align="center">
        <template slot-scope="{ row }">
          {{ accuracyObj[row.accuracy] || "-" }}
        </template>
      </el-table-column>
      <el-table-column prop="thresholdValue" label="值域" align="center" />
      <el-table-column
        prop="dataPriority"
        label="数据优先级"
        align="center"
        v-if="pageType == 1"
      />
      <!-- <el-table-column
        prop="suggestSourceRate"
        label="推荐数据缺失率"
        align="center"
        width="80px"
      /> -->
      <el-table-column
        prop="migrationRate"
        label="迁徙率"
        align="center"
        width="80px"
      />
      <el-table-column
        prop="isSystemInspection"
        label="是否通过系统质检"
        align="center"
        width="80px"
        v-if="pageType == 1"
      />
      <el-table-column
        prop="isHookArticulation"
        label="勾稽关系是否通过"
        align="center"
        width="80px"
        v-if="pageType == 1"
      />
      <el-table-column
        prop="isArtificialRecording"
        label="是否进行人工补录"
        align="center"
        width="80px"
        v-if="pageType == 1"
      />
      <el-table-column
        prop="isArtificialInspection"
        label="是否通过人工质检"
        align="center"
        width="80px"
        v-if="pageType == 1"
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
</template>

<script>
import {
  qualityBase,
  qualityMiddle,
  qualityApply,
} from "@/api/statisticalAnalysis/index.js";
import { updateInfo } from "@/api/dataDictionary/index.js";
import { accuracyObj } from "@/menu/index.js";
export default {
  props: {
    menuCode: {
      type: String,
      require: true,
    },
    pageType: {
      require: true,
    },
    pageName: {
      type: String,
    },
  },
  data() {
    return {
      accuracyObj: accuracyObj, //精度字点
      total: 0,
      queryParams: {
        hierarchy: this.pageType, //数据层级

        keyWord: "", //关键字
        years: [], //年份
        code: this.menuCode, //菜单code
        pageNum: 1,
        pageSize: 10,
      },
      loading: true,
      //上面基础信息
      updatedTime: "-",
      updateFrequency: "-",
      remark: "-",
      tableData: [],
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.qualityBase();
    });
    this.getUpdateInfo();
  },
  methods: {
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      this.pageType == 1 && this.qualityBase();
      this.pageType == 2 && this.qualityMiddle();
      this.pageType == 3 && this.qualityApply();
    },
    //基础层
    qualityBase() {
      try {
        this.loading = true;
        qualityBase(this.queryParams).then((res) => {
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
        });
      } finally {
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      }
    },
    //中间层
    qualityMiddle() {
      try {
        this.loading = true;
        qualityMiddle(this.queryParams).then((res) => {
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
        });
      } finally {
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      }
    },
    //指标层
    qualityApply() {
      try {
        this.loading = true;
        qualityApply(this.queryParams).then((res) => {
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
        });
      } finally {
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      }
    },
    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.handleQuery();
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
    //表头背景色
    headerStyles({ columnIndex }) {
      if (columnIndex > 7) {
        return {
          fontWeight: "700",
          color: "#35343A",
          background: "#E6F4F8",
          border: "none",
        };
      }
      if (columnIndex > 5) {
        return {
          fontWeight: "700",
          color: "#35343A",
          background: "#F0F8ED",
          border: "none",
        };
      }
      return {
        fontWeight: "700",
        color: "#35343A",
        background: "rgba(88,151,236,0.04)",
        border: "none",
      };
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
  padding: 20px 20px 0 20px;
}
.query {
  margin: 10px 0 0px 0;
}
.query-input {
  width: 282px;
  font-size: 12px;
}
</style>