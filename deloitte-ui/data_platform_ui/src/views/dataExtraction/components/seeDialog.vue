<template>
  <el-dialog
    title="数据详情"
    :visible.sync="visible"
    width="60vw"
    center
    :before-close="close"
    @opened="opened"
  >
    <div style="height: 80vh" class="content-view">
      <icon-title>{{ name }}</icon-title>
      <div class="flex-row mt20">
        <span class="title-span mr20">字段代码：{{ info.code }}</span>
        <span class="title-span">字段中文名称：{{ info.name }}</span>
      </div>
      <!-- 条件查询 -->
      <div class="query mt20">
        <el-form ref="form" :model="queryParams" inline>
          <el-form-item label="年份">
            <year-select
              @change="changeYear"
              style="width: 130px"
            ></year-select>
          </el-form-item>
          <el-form-item label="数据来源" style="margin-left: 12px">
            <sources-select
              @change="changeSource"
              style="width: 160px"
            ></sources-select>
          </el-form-item>
          <el-form-item class="ml20">
            <el-button
              size="mini"
              class="export-btn"
              icon="el-icon-download"
              @click="handleExport"
            >
              导出至Excel
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        stripe
        style="width: 100%"
        :header-cell-style="headerStyle"
        :cell-style="cellStyles"
        v-loading="loading"
      >
        <el-table-column prop="entityName" label="主体名称" align="left" />
        <el-table-column prop="entityCode" label="主体代码" align="left" />
        <el-table-column prop="reportDate" label="数据时间" align="left" />
        <el-table-column prop="suggestValue" label="推荐数据" align="left" />
        <!-- 基础层字段 -->
        <el-table-column
          prop="name"
          label="windRate	"
          align="left"
          v-if="type == 1"
        />
        <el-table-column
          prop="flushRate"
          label="同花顺"
          align="left"
          v-if="type == 1"
        />
        <el-table-column
          prop="ocrRate"
          label="自动化"
          align="left"
          v-if="type == 1"
        />
        <el-table-column
          prop="artificialAddRecordRate"
          label="人工补录"
          align="left"
          v-if="type == 1"
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
  </el-dialog>
</template>

<script>
import iconTitle from "../../../components/iconTitle/iconTitle.vue";
import {
  baseDataDetail,
  middleDataDetail,
  applyDataDetail,
} from "@/api/dataExtraction/index.js";
export default {
  components: { iconTitle },
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    name: {
      type: String,
      default: () => {
        return "-";
      },
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
    type: {
      type: String || Number,
      default: "1",
    },
  },
  data() {
    return {
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        years: [],
        source: [],
      },
      loading: true,
      tableData: [],
    };
  },

  methods: {
    close() {
      this.$emit("close");
    },
    opened() {
      this.queryParams.pageNum = 1;
      this.$nextTick(() => {
        this.getList();
      });
    },
    getList() {
      let query = {
        code: this.info.code,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        years: this.queryParams.years,
        sources: this.queryParams.sources,
      };
      this.loading = true;
      this.type == 1 && this.baseDataDetail(query);
      this.type == 2 && this.middleDataDetail(query);
      this.type == 3 && this.applyDataDetail(query);
    },
    //基础层数据详情
    baseDataDetail(query) {
      baseDataDetail(query).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
        this.loading = false;
      });
    },
    //中间层数据详情
    middleDataDetail(query) {
      middleDataDetail(query).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
        this.loading = false;
      });
    },
    //指标层数据详情
    applyDataDetail(query) {
      applyDataDetail(query).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
        this.loading = false;
      });
    },
    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //数据来源
    changeSource(val) {
      this.queryParams.source = val;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //导出
    handleExport() {
      let query = {
        code: this.info.code,
        pageNum: this.queryParams.pageNum,
        pageSize: 30,
        sources: this.queryParams.source, //数据来源
        years: this.queryParams.years, //年份
      };
      this.type == 1 && this.exportBaseData(query);
      this.type == 2 && this.exportMiddleData(query);
      this.type == 3 && this.exportApplyData(query);
    },

    //导出基础层数据详情
    exportBaseData(query) {
      this.download(
        "/dataExtraction/baseDataDetail/export",
        {
          ...query,
        },
        `baseDataDetail_${new Date().getTime()}.xlsx`
      );
    },
    //导出中间层数据详情
    exportMiddleData(query) {
      this.download(
        "/dataExtraction/middleDataDetail/export",
        {
          ...query,
        },
        `middleDataDetail_${new Date().getTime()}.xlsx`
      );
    },
    //导出指标层数据详情
    exportApplyData(query) {
      this.download(
        "/dataExtraction/applyDataDetail/export",
        {
          ...query,
        },
        `applyDataDetail_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/styles/dialog.scss";
.content-view {
  max-height: 70vh;
  overflow-y: scroll;
}
.title-span {
  height: 24px;
  line-height: 24px;
  padding: 0 16px;
  background-image: linear-gradient(180deg, #fed87e 0%, #ffb400 100%);
  border-radius: 2px;
  font-size: 12px;
  color: #35343a;
  font-weight: 400;
}
.export-btn {
  background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
  color: #fff;
}
</style>