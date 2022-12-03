<template>
  <div class="padding20">
    <icon-1-title>{{ info.entityName }}</icon-1-title>
    <!-- 条件查询 -->
    <div class="query">
      <el-form ref="form" :model="queryParams" inline>
        <el-form-item label-width="0px">
          <el-input
            size="mini"
            clearable
            v-model="queryParams.crux"
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
        <!-- 指标层显示 -->
        <el-form-item
          label="使用场景"
          style="margin-left: 12px"
          v-if="info.pageType == 3"
        >
          <choice-alone
            :options="overviewOption"
            @change="changeOverview"
            style="width: 130px"
          ></choice-alone>
        </el-form-item>
      </el-form>
    </div>
    <!-- 图表 -->
    <!-- 图表 -->
    <div class="chart-group flex-row" v-if="info.pageType == 1">
      <!-- 数据来源覆盖度 -->
      <coverage-bar
        :xdata="coverageX"
        :ydata="coverageY"
        @change="changeRadio"
      ></coverage-bar>

      <!-- 饼图 -->
      <div
        id="chart2"
        v-loading="chart2Loading"
        style="width: 18%; height: 200px"
      ></div>
      <div
        id="chart3"
        v-loading="chart3Loading"
        style="width: 18%; height: 200px"
      ></div>
    </div>
    <!-- 表格 -->
    <el-table
      :data="tableData"
      stripe
      style="width: 100%; margin-top: 20px"
      :header-cell-style="headerStyles"
      :cell-style="cellStyles"
      v-loading="tableLoading"
    >
      <el-table-column prop="entityCode" label="主体" align="left" />
      <el-table-column prop="code" label="字段代码" align="left" />
      <el-table-column prop="name" label="字段中文名称" align="left" />
      <el-table-column prop="hierarchy" label="数据层级" align="center">
        <template slot-scope="{ row }">
          {{ hierarchyMap[row.hierarchy] }}
        </template>
      </el-table-column>
      <el-table-column prop="reportDate" label="数据时间" align="center" />
      <el-table-column prop="accuracy" label="精度" align="center" />
      <!-- 中间层不显示 -->
      <el-table-column
        prop="dataPriority"
        label="数据优先级"
        align="center"
        v-if="info.pageType == 3"
      />
      <el-table-column prop="useScenarios" label="使用场景" align="center" />
      <el-table-column
        prop="suggestValue"
        label="推荐数据"
        align="center"
        min-width="80px"
      />
      <!-- <el-table-column
        prop="date"
        label="推荐数据是否缺失"
        align="center"
        width="80px"
      /> -->
      <el-table-column
        prop="isArtificialRecording"
        label="是否需要人工补录"
        align="center"
        width="80px"
        v-if="info.pageType == 2"
      />
      <el-table-column
        prop="artificialRecordingData"
        label="人工补录数据"
        align="center"
        width="80px"
        v-if="info.pageType == 2"
      />
      <el-table-column
        prop="ocrValue"
        label="自动化补录数据"
        align="center"
        width="80px"
        v-if="info.pageType == 2"
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
import chartStyle from "../mixins/singleBody"; //所有chart 样式配置
import {
  overviewList,
  sourceCoverage,
  recordingAll,
  missRateAll,
  getOverview,
} from "@/api/statisticalAnalysis/index.js";
import coverageBar from "@/components/echart/coverageBar.vue"; //数据来源
import { hierarchyMap } from "@/menu/index.js";
export default {
  mixins: [chartStyle],
  components: { coverageBar },
  props: {
    info: {
      type: Object,
    },
  },
  data() {
    return {
      hierarchyMap: hierarchyMap, //数据层级字典
      coverageX: [], //数据来源覆盖度
      coverageY: [], //数据来源覆盖度
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        crux: "", //关键字
        years: [],
        scene: "", //使用场景
        coverage: "1", //数据来源覆盖度 1全部 2推荐
      },
      overviewOption: [], //使用场景 选项
      tableData: [],
      tableLoading: true,
      total: 0,
      chart2: null,
      chart3: null,
      chart2Loading: true,
      chart3Loading: true,
    };
  },
  mounted() {
    this.getList();
    //只有基础层的时候 又图表
    if (this.info.pageType == 1) {
      this.$nextTick(() => {
        this.getCoverage();
        this.recordingAll();
        this.missRateAll();
      });
    }
    //只有指标层的时候  才有场景选项
    if (this.info.type == 3) {
      this.getOverview();
    }
    window.addEventListener("resize", () => {
      this.chart2 && this.chart2.resize();
      this.chart3 && this.chart3.resize();
    });
  },
  methods: {
    //获取数据来源覆盖度
    changeRadio(val) {
      this.queryParams.coverage = val;
      this.getCoverage();
    },
    getCoverage() {
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
        code: this.info.code, //单个字段
        sources: this.queryParams.sources,
        entityType: this.menuCode, //菜单的code
        coverageType: this.queryParams.coverage, // 1:全部数据 2:推荐数据
      };
      sourceCoverage(query).then((res) => {
        this.chartLoading = false;
        if (res.code == 200) {
          let { value, title } = res.data;
          let a = value.map((item) => {
            return parseFloat(item);
          });
          this.coverageX = title;
          this.coverageY = a;
        }
      });
    },
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
      //当页面为基础层的时候需要更新图表数据
      if (this.info.pageType == 1) {
        this.getCoverage();
        this.recordingAll();
        this.missRateAll();
      }
    },
    //获取表格数据
    getList() {
      this.$modal.loading("Loading...");
      this.tableLoading = true;
      this.tableLoading = true;
      let query = {
        entityCode: this.info.entityCode,
        hierarchy: this.info.pageType, //数据层级
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        businessScene: this.queryParams.scene, //使用场景
        years: this.queryParams.years, //年份
        searchName: this.queryParams.crux, //关键字
        // sources: this.queryParams.sources, //数据来源
      };
      try {
        overviewList(query).then((res) => {
          this.tableLoading = false;
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.table = res.data.table;
            this.tableLoading = false;
          }
        });
      } finally {
        this.tableLoading = false;
        this.$modal.closeLoading();
      }
    },
    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.handleQuery();
    },
    //使用场景 只有指标层才显示
    getOverview() {
      getOverview({ hierarchy: 3 }).then((res) => {
        let { code, data } = res;
        if (code == 200 && data != null) {
          this.overviewOption = [];
          data.forEach((item) => {
            this.overviewOption.push({
              label: item.name,
              value: item.code,
            });
          });
        }
      });
    },
    //使用场景
    changeOverview(val) {
      this.queryParams.scene = val;
      this.handleQuery();
    },
    //饼图 1
    missRateAll() {
      this.chart2Loading = true;
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
        searchName: this.queryParams.crux, //关键字
        code: this.info.code, //单个字段
        sources: this.queryParams.sources,
        entityType: this.menuCode, //菜单的code
        coverageType: this.queryParams.coverage, // 1:全部数据 2:推荐数据
      };
      missRateAll(query).then((res) => {
        if (res.code == 200) {
          let { dataMissRate, noArtificialInspection } = res.data;
          this.chart2Loading = false;
          this.drawChart2(dataMissRate, noArtificialInspection);
        }
      });
    },
    drawChart2(val1, val2) {
      let chartDom = document.getElementById("chart2");
      this.chart2 = this.$echarts.init(chartDom);
      let option = {
        tooltip: this.chart3Style.tooltip,
        series: [
          {
            type: "pie",
            color: this.chart3Style.color,
            radius: this.chart3Style.radius,
            label: this.chart3Style.label,
            radius: this.chart3Style.radius,
            data: [
              { value: val1, name: "数据缺失" },
              { value: val2, name: "未通过数据质检" },
            ],
          },
        ],
      };
      option && this.chart2.setOption(option);
      this.chart2Loading = false;
    },
    //饼图 2
    recordingAll() {
      this.chart3Loading = true;
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
        searchName: this.queryParams.crux, //关键字
        code: this.info.code, //单个字段
        sources: this.queryParams.sources,
        entityType: this.menuCode, //菜单的code
        coverageType: this.queryParams.coverage, // 1:全部数据 2:推荐数据
      };
      recordingAll(query).then((res) => {
        if (res.code == 200) {
          let { alredyRecording, recordingIng } = res.data;
          this.drawChart3(alredyRecording, recordingIng);
          this.chart3Loading = false;
        }
      });
    },
    drawChart3(value1, value2) {
      let chartDom = document.getElementById("chart3");
      this.chart3 = this.$echarts.init(chartDom);
      let option = {
        tooltip: this.chart3Style.tooltip,
        series: [
          {
            type: "pie",
            color: this.chart3Style.color,
            radius: this.chart3Style.radius,
            label: this.chart3Style.label,
            radius: this.chart3Style.radius,
            data: [
              { value: value1, name: "补录中" },
              { value: value2, name: "已人工补录" },
            ],
          },
        ],
      };
      option && this.chart3.setOption(option);
      this.chart3Loading = false;
    },
    // drawChart3() {
    //   let chartDom = document.getElementById("chart3");
    //   this.chart3 = this.$echarts.init(chartDom);
    //   let option = {
    //     tooltip: this.chart3Style.tooltip,
    //     series: [
    //       {
    //         type: "pie",
    //         color: this.chart3Style.color,
    //         radius: this.chart3Style.radius,
    //         label: this.chart3Style.label,
    //         radius: this.chart3Style.radius,
    //         data: [
    //           { value: 44, name: "补录中" },
    //           { value: 30, name: "已人工补录" },
    //         ],
    //       },
    //     ],
    //   };
    //   option && this.chart3.setOption(option);
    // },

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

<style lang='scss' scoped>
@import "@/assets/styles/radio.scss";
.padding20 {
  padding: 0 20px 20px 20px;
}
.query {
  margin: 10px 0 10px 0;
}
.chart-group {
  width: 100%;
  height: 200px;
}
</style>