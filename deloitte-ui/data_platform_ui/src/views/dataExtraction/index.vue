<template>
  <!-- 数据提取 -->
  <div class="container">
    <!-- 当从tab跳转过来 和 查询条件为主体信息的时候展示的页面 -->
    <div v-if="showPage" class="flex-row container">
      <my-menu @clickMenu="clickMenu" ref="menu"></my-menu>
      <div class="container-info padding30">
        <div class="info-content">
          <icon-title>{{ searchName || pageName }}</icon-title>
          <!-- 条件查询 -->
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

          <!-- 整理table -->
          <el-table
            :data="tableData"
            stripe
            style="width: 100%"
            :header-cell-style="headerStyle"
            :cell-style="cellStyles"
            @selection-change="handleSelectionChange"
            v-loading="loading"
          >
            <el-table-column width="50px" type="selection" align="center" />
            <el-table-column prop="code" label="字段代码" align="left" />
            <el-table-column prop="name" label="字段中文名称" align="left" />
            <el-table-column prop="reportDate" label="数据时间" align="left" />
            <el-table-column
              prop="suggestSource"
              label="推荐数据"
              align="left"
            />
            <el-table-column
              prop="dataMissRate"
              label="数据却失率"
              align="left"
            />
            <el-table-column
              prop="name"
              label="业务场景"
              align="left"
              v-if="pageType != 1"
            />
            <el-table-column
              prop="windRate"
              label="WIND"
              align="left"
              v-if="pageType == 1"
            />
            <el-table-column
              prop="flushRate"
              label="同花顺"
              align="left"
              v-if="pageType == 1"
            />
            <el-table-column
              prop="ocrRate"
              label="自动化"
              align="left"
              v-if="pageType == 1"
            />
            <el-table-column
              prop="artificialAddRecordRate"
              label="人工补录"
              align="left"
              v-if="pageType == 1"
            />
            <el-table-column
              prop="thresholdValue"
              label="数据核查值域"
              align="left"
              v-if="pageType == 1"
            />
            <el-table-column label="数据详情" align="left" width="80px">
              <template slot-scope="{ row }">
                <el-button type="text" @click="handleSee(row)">
                  查看
                </el-button>
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
          <!-- 点击查看的弹窗 -->
          <see-dialog
            :visible="seeDiaVisible"
            :info="visible"
            :name="pageName"
            :type="pageType"
            @close="seeDiaVisible = false"
          ></see-dialog>
        </div>
      </div>
    </div>
    <search-page
      v-if="!showPage"
      :info="searchInfo"
      ref="searchPage"
    ></search-page>
  </div>
</template>

<script>
import seeDialog from "./components/seeDialog.vue";
import { EventBus } from "@/evenBus/dataExtractionBus.js";
import searchPage from "./components/searchPage.vue";
import {
  baseDataList,
  middleDataList,
  applyData,
} from "@/api/dataExtraction/index.js";
export default {
  components: { seeDialog, searchPage },
  data() {
    return {
      seeDiaVisible: false, //查看弹窗
      showPage: true,
      searchName: "",
      searchInfo: {}, //页头查询后带来的参数
      visible: {}, //详情页参数
      pageType: "1", //1基础  2中间 3指标
      pageName: "",
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWord: "", //关键字
        years: [], //年份
        source: [], //数据来源
        entityCode: "", //页面头部查询的code
      },
      menuCode: "", //菜单code
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
      tableData: [],
      total: 0,
      loading: true,
    };
  },
  created() {
    //最上面查询的enter事件
    // let that = this;
    EventBus.$on("headerQueryPage", (msg) => {
      this.showPage = true;
      let { type, name, code } = msg;
      //主体信息类型
      if (type == 4) {
        this.queryParams.entityCode = code;
        this.searchName = name;
        this.handleQuery();
        this.showPage = true;
        console;
      } else {
        //非主体信息  都显示到详情页
        this.$nextTick(() => {
          this.showPage = false;
        });
        this.searchInfo = msg;
      }
    });
  },
  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      let query = {
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        code: this.menuCode, //菜单code
        entityCode: this.queryParams.entityCode, //主体编码
        keyWord: this.queryParams.keyWord, //关键字
        years: this.queryParams.years, //年份
        sources: this.queryParams.source, //来源
      };
      this.loading = true;
      //页面为基础层的时候
      this.pageType == 1 && this.baseDataList(query);
      //页面为中间层的时候
      this.pageType == 2 && this.middleDataList(query);
      //页面为指标层的时候
      this.pageType == 3 && this.applyData(query);
    },
    //获取基础层数据列表
    baseDataList(query) {
      try {
        baseDataList(query).then((res) => {
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

    //获取中间层数据列表
    middleDataList(query) {
      try {
        middleDataList(query).then((res) => {
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
    //获取指标层数据列表
    applyData(query) {
      try {
        applyData(query).then((res) => {
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
    //查看
    handleSee(row) {
      this.visible = row;
      this.seeDiaVisible = true;
    },
    //左侧菜单点击事件
    clickMenu(i) {
      this.pageType = this.pageTypeList[i.parentCode];
      this.pageName = this.titleType[this.pageType] + i.name || "";
      this.menuCode = i.code;
      this.total = 0;
      this.queryParams.pageNum = 1;
      this.queryParams.keyWord = "";
      this.tableData = [];
      this.getList();
    },
    //表格多选
    handleSelectionChange() {
      console.log("表格多选");
    },
    //导出
    handleExport() {
      let query = {
        code: this.menuCode, //菜单code
        entityCode: this.queryParams.entityCode, //主体编码
        keyWord: this.queryParams.keyWord, //关键字
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
        sources: this.queryParams.source, //数据来源
        years: this.queryParams.years, //年份
      };

      //页面为基础层的时候
      this.pageType == 1 && this.exportBaseData(query);
      //页面为中间层的时候
      this.pageType == 2 && this.exportMiddleData(query);
      //页面为指标层的时候
      this.pageType == 3 && this.exportApplyData(query);
    },

    //导出基础层数据
    exportBaseData(query) {
      this.download(
        "/dataExtraction/baseData/export",
        {
          ...query,
        },
        `baseData_${new Date().getTime()}.xlsx`
      );
    },
    //导出中间层数据
    exportMiddleData(query) {
      this.download(
        "/dataExtraction/middleData/export",
        {
          ...query,
        },
        `middleData_${new Date().getTime()}.xlsx`
      );
    },
    //导出指标层数据
    exportApplyData(query) {
      this.download(
        "/dataExtraction/applyData/export",
        {
          ...query,
        },
        `applyData_${new Date().getTime()}.xlsx`
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