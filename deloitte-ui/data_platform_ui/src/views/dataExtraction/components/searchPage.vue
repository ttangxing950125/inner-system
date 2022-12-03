<template>
  <!-- 数据提取 -->
  <div class="container">
    <div class="container-info padding30">
      <div class="info-content">
        <icon-title>{{ info.name }}</icon-title>
        <!-- 条件查询 -->
        <div class="query">
          <el-form ref="form" :model="queryParams" inline>
            <el-form-item label-width="0px">
              <el-input
                size="mini"
                v-model="queryParams.keyWord"
                placeholder="输入关键字进行搜索"
                prefix-icon="el-icon-search"
                style="width: 282px; margin-right: 20px"
                @keyup.native.enter="handleQuery"
                @change="handleQuery"
                clearable
              ></el-input>
            </el-form-item>
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
              <!-- 接口没写好 -->
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

        <!-- 整理table -->
        <el-table
          :data="tableData"
          stripe
          style="width: 100%"
          :header-cell-style="headerStyle"
          :cell-style="cellStyles"
          v-loading="loading"
        >
          <el-table-column
            v-for="(item, index) in tableHeader"
            :key="index"
            :prop="item.props"
            :label="item.label"
            :align="item.align"
            :min-width="item.width"
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
  baseDataDetail,
  middleDataDetail,
  applyDataDetail,
} from "@/api/dataExtraction/index.js";
export default {
  props: {
    info: {
      type: Object,
    },
  },
  data() {
    return {
      pageType: this.info.type, //1基础  2中间 3指标
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWord: "", //关键字
        years: [], //年份
        source: [], //数据来源
      },
      loading: true,
      tableData: [],
      total: 0,
    };
  },
  computed: {
    tableHeader() {
      //基础层
      if (this.info.type == 1) {
        let arr1 = [
          {
            label: "主体名称",
            props: "entityName",
            align: "left",
            width: "220px",
          },
          {
            label: "主体代码",
            props: "entityCode",
            align: "left",
            width: "220px",
          },
          {
            label: "数据时间",
            props: "reportDate",
            align: "left",
            width: "140px",
          },
          {
            label: "推荐数据",
            props: "suggestValue",
            align: "left",
            width: "220px",
          },
          {
            label: "Wind",
            props: "windRate",
            align: "left",
            width: "120px",
          },
          {
            label: "同花顺",
            props: "flushRate",
            align: "left",
            width: "120px",
          },
          {
            label: "自动化",
            props: "ocrRate",
            align: "left",
            width: "120px",
          },
          {
            label: "人工补录",
            props: "artificialAddRecordRate",
            align: "left",
            width: "120px",
          },
        ];
        return arr1;
      }
      //中间层
      if (this.info.type == 2) {
        let arr1 = [
          {
            label: "主体名称",
            props: "entityName",
            align: "left",
            width: "220px",
          },
          {
            label: "主体代码",
            props: "entityCode",
            align: "left",
            width: "220px",
          },
          {
            label: "数据时间",
            props: "reportDate",
            align: "left",
            width: "220px",
          },
          {
            label: "推荐数据",
            props: "suggestValue",
            align: "left",
            width: "220px",
          },
        ];
        return arr1;
      }
      //指标层
      if (this.info.type == 3) {
        let arr1 = [
          {
            label: "主体名称",
            props: "entityName",
            align: "left",
            width: "220px",
          },
          {
            label: "主体代码",
            props: "entityCode",
            align: "left",
            width: "220px",
          },
          {
            label: "数据时间",
            props: "reportDate",
            align: "left",
            width: "220px",
          },
          {
            label: "推荐数据",
            props: "suggestValue",
            align: "left",
            width: "220px",
          },
        ];
        return arr1;
      }
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.handleQuery();
    });
  },
  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      this.loading = true;
      let query = {
        code: this.info.code,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        years: this.queryParams.years,
        sources: this.queryParams.sources,
        keyWord: this.queryParams.keyWord,
      };
      //页面为基础层的时候
      this.info.type == 1 && this.baseDataList(query);
      //页面为中间层的时候
      this.info.type == 2 && this.middleDataList(query);
      //页面为指标层的时候
      this.info.type == 3 && this.applyData(query);
    },
    //获取基础层数据列表
    baseDataList(query) {
      baseDataDetail(query).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
          this.loading = false;
        }
      });
    },

    //获取中间层数据列表
    middleDataList(query) {
      middleDataDetail(query).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
          this.loading = false;
          console.log(res.data, "数据提取中间层表格--查询");
        }
      });
    },
    //获取指标层数据列表
    applyData(query) {
      applyDataDetail(query).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
          this.loading = false;
          console.log(res.data, "数据提取指标层表格--查询");
        }
      });
    },
    //导出
    handleExport() {
      let query = {
        code: this.info.code,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        years: this.queryParams.years,
        sources: this.queryParams.sources,
        keyWord: this.queryParams.keyWord, //页面表头的查询条件
      };
      this.info.type == 1 && this.exportBaseData(query);
      this.info.type == 2 && this.exportMiddleData(query);
      this.info.type == 3 && this.exportApplyData(query);
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

    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.handleQuery();
    },
    //数据来源
    changeSource(val) {
      this.queryParams.source = val;
      this.handleQuery();
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
  width: 100%;
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
.query {
  margin: 10px 0 0px 0;
}
.export-btn {
  background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
  color: #fff;
}

::v-deep .el-button--text {
  font-size: 12px;
  color: #6d798f;
  font-weight: 400;
  text-decoration: underline;
}
</style>