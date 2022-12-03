<template>
  <div class="homepage">
    <div v-show="!detailsShow">
      <go-back
        home="数据质检"
        :title="entityName"
        @click="$emit('goBack')"
      ></go-back>
      <icon-2-title>{{ entityName }}</icon-2-title>
      <div class="flex-row" style="margin: 14px 0 30px 0">
        <div class="margin-right70">
          <span class="font1-700">数据更新时间：</span
          ><span class="font2-400">{{
            parseTime(updateData.updatedTime)
          }}</span>
        </div>
        <div class="margin-right70">
          <span class="font1-700">数据更新说明：</span
          ><span class="font2-400">{{ updateData.remark }}</span>
        </div>
      </div>
      <!-- 条件查询 -->
      <line-title>检测范围</line-title>
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
            <year-select
              @change="changeYear"
              style="width: 130px"
            ></year-select>
          </el-form-item>
        </el-form>
      </div>
      <!-- 表格 -->
      <line-title>质检结果</line-title>
      <el-table
        :data="tableData"
        stripe
        style="width: 100%; margin-top: 20px"
        :header-cell-style="headerStyles"
        :cell-style="cellStyles"
      >
        <el-table-column
          prop="code"
          label="字段代码"
          show-overflow-tooltip
          align="left"
        >
          <template slot-scope="{ row }">
            <span></span>
            <span class="pointer text-button" @click="handleRowName(row)">{{
              row.code || "-"
            }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="字段中文名称" align="left">
          <template slot-scope="{ row }">
            <span>{{ row.name || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="reportDate" label="数据时间" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.reportDate || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="accuracy" label="精度" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.accuracy || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="accuracy" label="值域" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.thresholdValue || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="数据优先级"
          show-overflow-tooltip
          align="center"
        >
          <template slot-scope="{ row }">
            <span>{{ row.dataPriority || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="推荐数据是否缺失"
          align="center"
          width="80px"
        >
          <template slot-scope="{ row }">
            <!--  === 1 ? "是" : "否" -->
            <span>{{ row.isDataMiss }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="是否超过阈值"
          align="center"
          width="80px"
        >
          <template slot-scope="{ row }">
            <span>{{ row.isThresholdExceeded }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="是否通过系统质检"
          align="center"
          width="80px"
        >
          <template slot-scope="{ row }">
            <span>{{ row.isSystemInspection }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="是否通过人工质检"
          align="center"
          width="80px"
        >
          <!-- 这里后端接口还没有提供 -->
          <template slot-scope="{ row }">
            <span @click="handleAdopt(row)" class="pointer text-button">{{
              row.isArtificialInspection
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="是否进行人工补录"
          align="center"
          width="80px"
        >
          <template slot-scope="{ row }">
            <span>{{ row.isArtificialRecording }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!-- 字段详情页 -->
    <field-details
      v-if="detailsShow"
      :year="year"
      :entityName="nextEntityName"
      :code="code"
      @goBack="detailsShow = false"
    ></field-details>
  </div>
</template>

<script>
import fieldDetails from "./fieldDetails.vue";
import {
  detaileList,
  updateInfo,
  isArtificialInspection,
} from "@/api/dataCheck";

export default {
  components: { fieldDetails },
  props: {
    entityCode: {
      type: String,
      default: "",
    },
    entityName: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      detailsShow: false,
      year: "",
      queryParams: {
        crux: "", //关键字
        pageNum: 1,
        pageSize: 10,
        parmasYear: [],
      },
      total: 0,
      tableData: [],
      code: "",
      updateData: {
        updatedTime: "",
        remark: "",
      },
      nextEntityName: "",
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //是否通过人工质检
    handleAdopt(i) {
      isArtificialInspection(i.year, i.dataId).then((res) => {
        if (res.code == 200) {
          this.$message.scuess("操作成功");
          this.getList();
        }
      });
    },
    getList() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          entityCode: this.entityCode,
          keyword: this.queryParams.crux,
          years: this.queryParams.parmasYear,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        detaileList(parmas).then((res) => {
          const { data } = res;
          this.tableData = data.records;
          this.total = data.total;
        });
        updateInfo({}).then((res) => {
          const { data } = res;
          this.updateData = data || {
            updatedTime: "",
            remark: "",
          };
        });
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },
    //点击字段名称
    handleRowName(row) {
      this.year = row.year;
      this.code = row.code;
      this.nextEntityName = row.name;
      this.detailsShow = true;
    },
    //年份
    changeYear(val) {
      this.queryParams.parmasYear = val;
      this.handleQuery();
    },
    //表头
    headerStyles({ columnIndex }) {
      if (columnIndex == 7 || columnIndex == 6) {
        return {
          fontWeight: "700",
          color: "#35343A",
          background: "#F0F8ED",
          border: "none",
        };
      }
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
.homepage {
  background: #fff;
  min-height: calc(100vh - 180px);
  overflow-y: scroll;
  padding: 20px;
}
.query {
  margin: 10px 0 10px 0;
}
</style>