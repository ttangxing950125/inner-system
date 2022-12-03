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
          <!-- <year-select></year-select> -->
          <choice-all
            :options="yearOption"
            @change="changeYear"
            style="width: 130px"
          ></choice-all>
        </el-form-item>
        <!-- 基础层的时候筛选条件 -->
        <el-form-item
          label="数据来源"
          style="margin-left: 12px"
          v-if="info.pageType == 1"
        >
          <sources-select
            @change="changeSources"
            style="width: 160px"
          ></sources-select>
        </el-form-item>
        <!-- 指标层的筛选条件 -->
        <el-form-item
          label="使用场景"
          style="margin-left: 12px"
          v-if="info.pageType == 3"
        >
          <select-check-box
            :options="scene"
            @selectChange="changeScene"
            style="width: 130px"
          ></select-check-box>
        </el-form-item>
      </el-form>
    </div>

    <!-- 图表 -->
    <!-- 指标层才有图表数据 -->
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
        style="width: 18%; height: 200px"
        v-loading="chart2Loading"
      ></div>
      <div
        id="chart3"
        style="width: 18%; height: 200px"
        v-loading="chart3Loading"
      ></div>
      <!-- 人工补录字段情况 -->
      <div
        v-loading="chart4Loading"
        id="chart4"
        style="width: 26%; height: 200px; margin-left: 62px"
      ></div>
    </div>
    <!-- 表格 -->
    <el-table
      :data="tableData"
      stripe
      style="width: 100%; margin-top: 20px"
      :header-cell-style="headerStyles"
      :cell-style="cellStyles"
    >
      <el-table-column prop="code" label="字段代码" align="left" />
      <el-table-column prop="name" label="字段中文名称" align="left" />
      <el-table-column prop="hierarchy" label="数据层级" align="center">
        <template slot-scope="{ row }">
          {{ hierarchyMap[row.hierarchy] }}
        </template>
      </el-table-column>
      <el-table-column
        prop="useScenarios"
        label="使用场景"
        align="center"
        v-if="info.pageType == 3"
      />
      <el-table-column prop="reportDate" label="数据时间" align="center" />
      <el-table-column prop="suggestValue" label="推荐数据" align="center" />

      <el-table-column
        prop="windValue"
        label="WIND"
        align="center"
        v-if="info.pageType == 1"
      />
      <el-table-column
        prop="flushValue"
        label="同花顺"
        align="center"
        v-if="info.pageType == 1"
      />
      <el-table-column
        prop="ocrValue"
        label="自动化"
        align="center"
        v-if="info.pageType == 1"
      />
      <el-table-column
        prop="isArtificialRecording"
        label="是否需要人工补录"
        align="center"
        width="80px"
        v-if="info.pageType == 1"
      />
      <!-- <el-table-column
        prop="date"
        label="人工补录数据"
        align="center"
        width="80px"
      />
      <el-table-column
        prop="date"
        label="自动化补录数据"
        align="center"
        width="80px"
      /> -->
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
import { hierarchyMap } from "@/menu/index.js";
import {
  overviewList,
  sourceCoverage,
  getYears3,
  artificialRecordingBar,
  recordingAll,
  missRateAll,
} from "@/api/statisticalAnalysis/index.js";
import coverageBar from "@/components/echart/coverageBar.vue"; //数据来源
export default {
  components: {
    coverageBar,
  },
  mixins: [chartStyle],
  props: {
    info: {
      type: Object,
    },
  },
  data() {
    return {
      hierarchyMap: hierarchyMap, // 数据层级字典
      coverageX: [],
      coverageY: [],
      yearOption: [], //年份
      queryParams: {
        crux: "", //关键字
        pageNum: 1,
        pageSize: 10,
        years: [], //年份
        sources: [], //数据来源
        coverage: "1", //数据来源覆盖度 1全部 2推荐
        scene: [], //使用场景
      },
      total: 0,
      tableData: [],
      //使用场景
      scene: [
        {
          label: "场景1",
          value: "1",
        },
      ],
      chart2Loading: true,
      chart3Loading: true,
      chart4Loading: true,
      chart2: null,
      chart3: null,
      chart4: null,
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.getList();
      this.getYears3();
      //基础层的时候需要展示图表数据
      if (this.info.pageType == 1) {
        this.getCoverage(); //数据来源覆盖度
        this.getRecordingBar(); //人工补录字段情况
        this.recordingAll();
        this.missRateAll();
      }
    });

    window.addEventListener("resize", () => {
      this.chart2 && this.chart2.resize();
      this.chart3 && this.chart3.resize();
      this.charts4 && this.charts4.resize();
    });
  },
  methods: {
    changeRadio(val) {
      this.queryParams.coverage = val;
      this.getCoverage();
    },
    //获取数据来源覆盖度
    getCoverage() {
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
        entityCode: this.info.entityCode, //单个主体
        sources: this.queryParams.sources,
        entityType: this.menuCode, //菜单的code
        searchName: this.queryParams.crux, //关键字
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
    //获取表格数据
    getList() {
      this.tableLoading = true;
      let query = {
        entityCode: this.info.entityCode,
        hierarchy: this.info.pageType, //数据层级
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        years: this.queryParams.years, //年份
        searchName: this.queryParams.crux, //关键字
        sources: this.queryParams.sources, //数据来源
        businessScene: this.queryParams.scene, //使用场景
      };
      try {
        this.$modal.loading("Loading...");
        overviewList(query).then((res) => {
          this.tableLoading = false;
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
      //基础层时候 需要去更新图表数据
      if (this.info.pageType == 1) {
        this.getCoverage(); //数据来源覆盖度
        this.getRecordingBar(); //人工补录字段情况
        this.recordingAll();
        this.missRateAll();
      }
    },
    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.handleQuery();
    },
    //获取年份
    getYears3() {
      getYears3({ hierarchy: this.info.pageType }).then((res) => {
        if (res.code == 200) {
          this.yearOption = [];
          res.data.forEach((item) => {
            this.yearOption.push({
              label: item,
              value: item,
            });
          });
        }
      });
    },
    //数据来源
    changeSources(val) {
      this.queryParams.sources = val;
      this.handleQuery();
    },
    //使用场景
    changeScene(val) {
      console.log(val, "使用场景");
    },
    //饼图 1
    missRateAll() {
      this.chart2Loading = true;
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
        searchName: this.queryParams.crux, //关键字
        entityCode: this.info.entityCode, //单个主体
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
        entityCode: this.info.entityCode, //单个主体
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
    //人工补录字段情况
    getRecordingBar() {
      this.chart4Loading = true;
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
        searchName: this.queryParams.crux, //关键字
        entityCode: this.info.entityCode, //单个主体
        sources: this.queryParams.sources,
        entityType: this.menuCode, //菜单的code
        coverageType: 1, // 1:全部数据 2:推荐数据
      };
      artificialRecordingBar(query).then((res) => {
        if (res.code == 200) {
          let { title, noArtificialRecordinge, alreadyArtificialRecordinge } =
            res.data;
          this.drawChart4(
            title,
            alreadyArtificialRecordinge,
            noArtificialRecordinge
          );
          this.chart4Loading = false;
        }
      });
    },
    drawChart4(title, value1, value2) {
      let chartDom = document.getElementById("chart4");
      this.chart4 = this.$echarts.init(chartDom);
      let option = {
        title: this.chart4Style.title,
        tooltip: this.chart4Style.tooltip,
        legend: this.chart4Style.legend,
        grid: this.chart4Style.grid,
        xAxis: {
          type: "category",
          data: title,
          axisTick: {
            show: false, // 不显示坐标轴刻度线
          },
        },
        yAxis: this.chart4Style.yAxis,
        series: [
          {
            name: "已人工补录",
            type: "bar",
            stack: "total",
            barWidth: 20,
            data: value1,
            itemStyle: this.chart4Style.series.itemStyle1,
          },
          {
            name: "尚未人工补录",
            type: "bar",
            stack: "total",
            barWidth: 20,
            itemStyle: this.chart4Style.series.itemStyle2,
            data: value2,
          },
        ],
      };
      option && this.chart4.setOption(option);
      this.chart4Loading = false;
    },
    //表头背景色
    headerStyles({ columnIndex }) {
      if (columnIndex > 9) {
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
.chart1-title {
  width: 100%;
  text-align: center;
  font-size: 12px;
  color: #35343a;
  font-weight: 700;
}
</style>