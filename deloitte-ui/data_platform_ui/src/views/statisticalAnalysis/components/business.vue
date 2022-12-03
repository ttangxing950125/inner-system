<template>
  <!-- 业务场景 -->
  <div class="padding20">
    <icon-1-title>数据总览_IB</icon-1-title>
    <!-- 条件查询 -->
    <div class="query">
      <el-form ref="form" :model="queryParams" inline>
        <el-form-item label-width="0px">
          <el-input
            size="mini"
            v-model="queryParams.searchName"
            placeholder="输入关键字进行搜索"
            prefix-icon="el-icon-search"
            style="width: 282px; margin-right: 20px"
            @keyup.enter.native="handleQuery"
            @change="handleQuery"
          ></el-input>
        </el-form-item>
        <el-form-item label="年份">
          <choice-all
            :options="yearOption"
            :defaultValue="queryParams.years"
            @change="changeYear"
            :isMust="true"
            style="width: 130px"
          ></choice-all>
        </el-form-item>
        <el-form-item label="数据来源" v-if="pageType == 1">
          <sources-select
            @change="changeSources"
            style="width: 160px"
          ></sources-select>
        </el-form-item>
        <!-- 指标层的时候才显示 -->
        <el-form-item
          label="客户"
          style="margin-left: 12px"
          v-if="pageType == 3"
        >
          <el-select
            v-model="queryParams.customer"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词"
            :remote-method="getCustomer"
            :loading="loading"
            size="mini"
            @change="handleQuery"
          >
            <el-option
              v-for="item in customer"
              :key="item.entityCode"
              :label="item.entityName"
              :value="item.entityCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <!-- 图表 -->
    <!-- 图表 -->
    <div class="chart-group flex-row">
      <!-- 缺失率统计 -->
      <defect-bar :data1="defectData1" :data2="defectData2"></defect-bar>
      <!-- 数据来源覆盖度 -->
      <div style="width: 400px; margin-left: 62px" v-if="pageType == 1">
        <coverage-bar
          :xdata="coverageX"
          :ydata="coverageY"
          @change="changeRadio"
        ></coverage-bar>
      </div>
      <!-- 饼图 -->
      <div
        v-if="pageType == 1"
        v-loading="loading3"
        id="chart3"
        style="width: 18%; height: 200px"
      ></div>
      <div style="width: 28%; height: 200px; margin-left: 32px">
        <span class="chart4-title">人工补录字段情况</span>
        <div
          v-if="pageType == 1"
          id="chart4"
          v-loading="loading4"
          style="width: 100%; height: 200px; margin-left: 32px"
        ></div>
      </div>
    </div>
    <!-- 表格 -->
    <el-table
      :data="tableData"
      style="width: 100%; margin-top: 20px"
      :header-cell-style="headerStyles"
      :cell-style="cellStyles"
      stripe
      v-loading="tabLoading"
    >
      <el-table-column prop="entityName" label="主体" align="center" />
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
        v-if="pageType == 3"
      />
      <el-table-column prop="reportDate" label="数据时间" align="center" />
      <el-table-column prop="accuracy" label="精度" align="center">
        <template slot-scope="{ row }">
          {{ accuracyObj[row.accuracy] }}
        </template>
      </el-table-column>
      <!-- 基础层展示 -->
      <el-table-column
        prop="dataPriority"
        label="数据优先级"
        align="center"
        v-if="pageType == 1"
      />
      <!-- 基础层不显示 -->

      <el-table-column
        prop="suggestValue"
        label="推荐数据"
        align="center"
        width="100px"
      />
      <!-- 基础层展示 -->
      <el-table-column
        prop="isArtificialRecording"
        label="是否需要人工补录"
        align="center"
        width="80px"
        v-if="pageType == 1"
      />
      <!-- 基础层展示 -->
      <el-table-column
        prop="ocrValue"
        label="自动化补录数据"
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
import chartStyle from "../mixins/business.js"; //所有chart 样式配置
import {
  overviewList,
  missRate,
  sourceCoverage,
  customerPage,
  getYears3,
  artificiaCircular,
  missRateAll,
} from "@/api/statisticalAnalysis/index.js";
import coverageBar from "@/components/echart/coverageBar.vue"; //数据来源
import defectBar from "@/components/echart/defectBar.vue"; //数据却失率统计
import { hierarchyMap, accuracyObj } from "@/menu/index.js";
export default {
  components: { coverageBar, defectBar },
  mixins: [chartStyle],

  props: {
    //点击菜单 传的菜单的code
    menuCode: {
      type: String,
      default: "",
    },
    //点击菜单 返回的数据层级 1基础层 2中间层 3 指标层
    pageType: {
      type: String,
      default: () => {
        return "1";
      },
    },
  },
  data() {
    return {
      loading: true,
      coverageX: [], //数据来源覆盖度
      coverageY: [], //数据来源覆盖度
      hierarchyMap: hierarchyMap, //数据层级字典
      accuracyObj: accuracyObj, //精度字典
      defectData1: [], //缺失率统计
      defectData2: [], //缺失率统计
      customer: [], //客户
      yearOption: [], //年份
      queryParams: {
        hierarchy: this.pageType, //数据层级
        searchName: "", //关键字
        sources: [], //数据来源
        years: [], //年份
        customer: "", //客户
        coverage: "1", //数据来源覆盖度 1全部 2推荐
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      tabLoading: true,
      tableData: [],
      chart3: null,
      loading3: true,
      chart4: null,
      loading4: true,
    };
  },
  mounted() {
    this.getYears3(); //获取年份选项s
    this.$nextTick(() => {
      this.getMissRate(); //数据缺失率
    });
    window.addEventListener("resize", () => {
      this.chart3 && this.chart3.resize();
      this.chart4 && this.chart4.resize();
    });
  },
  methods: {
    //查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
      this.getMissRate(); //更新数据却失率
      //基础层 需要更新数据来源覆盖度
      if (this.pageType == 1) {
        this.missRateAll(); //
        this.getCoverage(); //更新数据来源覆盖度
        this.artificiaCircular(); //人工补录字段情况
      }
    },
    //获取表格数据
    getList() {
      try {
        this.tabLoading = true;
        let query = {
          entityType: this.menuCode, //菜单的code
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
          hierarchy: this.pageType, //数据层级
          searchName: this.queryParams.searchName, //关键字
          sources: this.queryParams.sources, //数据来源
          years: this.queryParams.years, //年份
          customer: this.queryParams.customer, //客户
        };
        overviewList(query).then((res) => {
          console.log(res, "业务场景 表格数据");
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
        });
      } finally {
        setTimeout(() => {
          this.tabLoading = false;
        }, 1400);
      }
    },

    //缺失率统计
    getMissRate() {
      let query = {
        coverageType: 1, //全部数据
        entityType: this.menuCode, //菜单的code
        hierarchy: this.pageType, //数据层级
        searchName: this.queryParams.searchName, //关键字
        years: this.queryParams.years, //年份
      };
      missRate(query).then((res) => {
        if (res.code == 200) {
          let { value } = res.data;
          let value1 = value; //缺失
          //填充
          let value2 = value.map((i) => {
            return 100 - i;
          });
          this.defectData1 = value1;
          this.defectData2 = value2;
        }
      });
    },
    //获取客户列表
    getCustomer(query) {
      this.loading = true;
      customerPage({ searchName: query, pageNum: 1, pageSize: 500 }).then(
        (res) => {
          if (res.code == 200) {
            this.customer = res.data.records;
            this.loading = false;
          }
        }
      );
    },

    //数据来源
    changeSources(val) {
      this.queryParams.sources = val;
      this.handleQuery();
    },

    //获取数据来源覆盖度s
    changeRadio(val) {
      this.queryParams.coverage = val;
      this.getCoverage();
    },
    getCoverage() {
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层 只有基础层的时候需要去查数据
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
    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.handleQuery();
    },
    //获取年份
    getYears3() {
      getYears3({ hierarchy: this.pageType }).then((res) => {
        if (res.code == 200) {
          this.yearOption = [];
          this.queryParams.years = [res.data[0]]; //设置默认数据
          res.data.forEach((item) => {
            this.yearOption.push({
              label: item,
              value: item,
            });
          });
        }
      });
    },

    //饼图
    missRateAll() {
      this.loading3 = true;
      let query = {
        hierarchy: 1,
        coverageType: 1, // 1:全部数据 2:推荐数据
        entityType: this.menuCode, //菜单的code
      };
      missRateAll(query).then((res) => {
        if (res.code == 200 && res.data != null) {
          let { dataMissRate, noArtificialInspection } = res.data;
          this.drawChart3(dataMissRate, noArtificialInspection);
        }
      });
    },
    drawChart3(val1, val2) {
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
              { value: val1, name: "数据缺失" },
              { value: val2, name: "未通过数据质检" },
            ],
          },
        ],
      };
      option && this.chart3.setOption(option);
      this.loading3 = true;
    },
    //饼图4
    artificiaCircular() {
      try {
        this.loading4 = true;
        let query = {
          hierarchy: 1,
          entityType: this.menuCode, //菜单的code
          sources: this.queryParams.sources, //数据来源
          years: this.queryParams.years, //年份
        };
        artificiaCircular(query).then((res) => {
          if (res.code == 200 && res.data != null) {
            let {
              alredyRecording, //已人工补录
              artificialRecordIng, //人工补录
              dataCheckFailed, //自动化校验未通过
              ocr, //自动化
              ocrIng, //已自动化填充
              recordingIng, //人工补录中
            } = res.data;
            this.drawChart4(
              alredyRecording, //已人工补录
              artificialRecordIng, //人工补录
              dataCheckFailed, //自动化校验未通过
              ocr, //自动化
              ocrIng, //已自动化填充
              recordingIng //人工补录中
            );
          }
        });
      } finally {
        setTimeout(() => {
          this.loading4 = false;
        }, 1000);
      }
    },
    drawChart4(
      alredyRecording, //已人工补录
      artificialRecordIng, //人工补录
      dataCheckFailed, //自动化校验未通过
      ocr, //自动化
      ocrIng, //已自动化填充
      recordingIng //人工补录中
    ) {
      let that = this;
      let chartDom = document.getElementById("chart4");
      this.chart4 = that.$echarts.init(chartDom);
      let option = {
        tooltip: that.chart4Style.tooltip,
        series: [
          {
            type: "pie",
            selectedMode: "single",
            radius: [0, "40%"],
            labelLine: that.chart4Style.labelLine,
            color: this.chart4Style.color2,
            label: {
              formatter: "{b}\n{d}%",
            },
            data: [
              { value: recordingIng, name: "人工补录中" },
              { value: dataCheckFailed, name: "自动化校验未通过" },
              { value: alredyRecording, name: "已人工补录" },
              { value: ocrIng, name: "已自动化填充" },
            ],
          },
          {
            // name: "访问来源",
            type: "pie",
            radius: ["50%", "80%"],
            color: this.chart4Style.color,
            label: {
              formatter: "{b}\n{d}%",
            },
            data: [
              { value: ocr, name: "自动化" },
              { value: artificialRecordIng, name: "人工补录" },
            ],
          },
        ],
      };
      option && this.chart4.setOption(option);
    },

    //表头背景色
    headerStyles({ columnIndex }) {
      if (this.pageType == 2 && columnIndex == 6) {
        return {
          fontWeight: "700",
          color: "#35343A",
          background: "#E6F4F8",
          border: "none",
        };
      }
      if (columnIndex > 6) {
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
.chart4-title {
  font-size: 12;
  color: #35343a;
  font-weight: 700;
}
</style>