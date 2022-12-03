<template>
  <!-- 总览 -->
  <div class="padding20">
    <icon-1-title>数据总览</icon-1-title>
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
          <choice-all
            :options="yearOption"
            @change="changeYear"
            ref="yearSelect"
            style="width: 130px"
          ></choice-all>
        </el-form-item>
        <el-form-item label="数据层级" style="margin-left: 12px">
          <hierarchy-select
            @change="changeHierarchy"
            style="width: 130px"
          ></hierarchy-select>
        </el-form-item>
        <!-- 基础层的时候显示  -->
        <el-form-item
          label="数据来源"
          style="margin-left: 12px"
          v-if="hierarchyValue == 1"
        >
          <sources-select
            @change="changeSources"
            style="width: 160px"
          ></sources-select>
        </el-form-item>
        <!-- 指标层的时候显示  -->
        <el-form-item
          label="使用场景"
          style="margin-left: 12px"
          v-if="hierarchyValue == 3"
        >
          <choice-all
            :options="overviewOption"
            @change="changeScene"
          ></choice-all>
        </el-form-item>
      </el-form>
    </div>
    <!-- 图表 -->
    <!-- 基础层的时候显示  -->
    <div v-show="hierarchyValue == 1" class="chart-group flex-row">
      <!-- 数据来源覆盖度 -->
      <coverage-bar
        :xdata="coverageX"
        :ydata="coverageY"
        @change="changeRadio"
      ></coverage-bar>
    </div>
    <!-- table 表格 -->
    <el-table
      :data="tableData"
      stripe
      style="width: 100%; margin-top: 20px"
      :header-cell-style="headerStyles"
      :cell-style="cellStyles"
      v-loading="tableLoading"
    >
      <el-table-column prop="entityName" label="主体" align="center">
        <template slot-scope="{ row }">
          <span class="text-button" @click="handleSubject(row)">
            {{ row.entityName }}</span
          >
        </template>
      </el-table-column>
      <el-table-column prop="code" label="字段代码" align="left">
        <template slot-scope="{ row }">
          <span class="text-button" @click="handleCode(row)">
            {{ row.code }}</span
          >
        </template>
      </el-table-column>
      <el-table-column prop="name" label="字段中文名称" align="left" />
      <el-table-column
        prop="hierarchy"
        label="数据层级"
        align="center"
        width="100px"
      >
        <template slot-scope="{ row }">
          {{ hierarchyMap[row.hierarchy] }}
        </template>
      </el-table-column>
      <el-table-column
        prop="useScenarios"
        label="使用场景"
        align="center"
        v-if="hierarchyValue == 3"
      />
      <el-table-column prop="reportDate" label="数据时间" align="center" />
      <el-table-column
        prop="accuracy"
        label="精度"
        align="center"
        width="60px"
      />
      <el-table-column
        prop="dataPriority"
        label="数据优先级"
        align="center"
        v-if="hierarchyValue == 1"
      />
      <el-table-column
        prop="suggestValue"
        label="推荐数据"
        align="center"
        width="100px"
      />
      <el-table-column
        prop="isArtificialRecording"
        label="是否需要人工补录"
        align="center"
        width="80px"
        v-if="hierarchyValue == 1"
      />
      <el-table-column
        prop="artificialRecordingData"
        label="人工补录数据"
        align="center"
        width="80px"
        v-if="hierarchyValue == 1"
      />
      <el-table-column
        prop="ocrValue"
        label="自动化补录数据"
        align="center"
        width="80px"
        v-if="hierarchyValue == 1"
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
import chartStyle from "../mixins/overview.js";
import {
  overviewList,
  sourceCoverage,
  getYears3,
} from "@/api/statisticalAnalysis/index.js";
import { hierarchyMap, accuracyObj } from "@/menu/index.js";
import { getOverview } from "@/api/statisticalAnalysis/index.js";
import coverageBar from "@/components/echart/coverageBar.vue"; //数据来源

export default {
  mixins: [chartStyle],
  components: { coverageBar },
  data() {
    return {
      accuracyObj: accuracyObj,
      hierarchyMap: hierarchyMap,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        crux: "", //关键字
        years: [], //年份
        sources: [], //数据来源
        overview: [], //使用场景
        coverage: "1", //数据来源覆盖度 1全部 2推荐
      },
      hierarchyValue: 1, // 数据层级 默认选中基础层
      tableData: [],
      coverageX: [], //数据来源覆盖度
      coverageY: [], //数据来源覆盖度
      tableLoading: true,
      overviewOption: [], //使用场景options
      yearOption: [], //年份选项
    };
  },
  mounted() {
    //获取表格数据
    this.getList();
    if (this.hierarchyValue == 1) {
      this.getCoverage(); //获取图表
    }
    this.getYears3();
    this.getOverview(); //使用场景
  },
  methods: {
    //点击主体
    handleSubject(row) {
      //数据层级要带过去
      row.pageType = this.hierarchyValue;
      this.$emit("subject", row);
    },
    //点击字段代码
    handleCode(row) {
      //数据层级要带过去
      row.pageType = this.hierarchyValue;
      this.$emit("handleCode", row);
    },

    handleQuery() {
      this.tableLoading = true;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //获取表格数据
    getList() {
      this.tableLoading = true;
      let query = {
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        businessScene: this.queryParams.overview,
        hierarchy: this.hierarchyValue, //数据层级
        years: this.queryParams.years, //年份
        searchName: this.queryParams.crux, //关键字
        sources: this.queryParams.sources, //数据来源
      };
      overviewList(query).then((res) => {
        this.tableLoading = false;
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      });
    },
    //全部数据 推荐数据
    changeRadio(val) {
      this.queryParams.coverage = val;
      this.getCoverage();
    },
    //获取图表
    getCoverage() {
      let query = {
        hierarchy: 1, //数据层级必须为1 基础层
        sources: this.queryParams.sources, //数据来源
        coverageType: this.queryParams.coverage, // 1:全部数据 2:推荐数据
        years: this.queryParams.years, //年份
        searchName: this.queryParams.crux, //关键字
      };
      sourceCoverage(query).then((res) => {
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
      getYears3({ hierarchy: this.hierarchyValue }).then((res) => {
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
    //数据层级
    changeHierarchy(val) {
      this.hierarchyValue = val;
      this.yearOption = [];
      this.$refs.yearSelect.value = [];
      this.queryParams.years = [];
      this.handleQuery(); //查询列表
      this.getYears3();
    },

    // 使用场景
    changeScene(val) {
      this.queryParams.overview = val;
      this.handleQuery();
    },
    //获取使用场景 type==2 中间层 3指标层
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
    //数据来源
    changeSources(val) {
      this.queryParams.sources = val;
      this.handleQuery();
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