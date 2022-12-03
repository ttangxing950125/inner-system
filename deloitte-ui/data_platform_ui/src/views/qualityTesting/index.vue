<template>
  <!-- 数据质检 -->
  <div class="padding30">
    <!-- 主页 -->
    <keep-alive>
      <div class="homepage" v-show="!detalisShow">
        <line-title>数据质检</line-title>
        <div class="query">
          <el-form ref="form" :model="queryParams" inline>
            <el-form-item label-width="0px">
              <el-input
                size="mini"
                clearable
                v-model="queryParams.crux"
                placeholder="输入主体名称/主体编码进行搜索"
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
            <el-form-item label="是否上市" style="margin-left: 12px">
              <choice-alone
                :options="whether"
                style="width: 100px"
                :clearable="true"
                @change="changeList"
              ></choice-alone>
            </el-form-item>
            <el-form-item label="是否发债" style="margin-left: 12px">
              <choice-alone
                :options="whether"
                style="width: 100px"
                :clearable="true"
                @change="changeBonds"
              ></choice-alone>
            </el-form-item>
          </el-form>
        </div>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width: 100%"
          stripe
          :header-cell-style="headerStyle"
          :cell-style="cellStyles"
        >
          <el-table-column
            width="130px"
            type="index"
            label="序号"
            align="left"
          />
          <el-table-column
            prop="name"
            label="主体名称"
            show-overflow-tooltip
            align="left"
          >
            <template slot-scope="{ row }">
              <span
                class="table-row-name pointer text-button"
                @click="handleName(row)"
                >{{ row.entityName || "-" }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="entityCode" label="主体编码" align="center">
            <template slot-scope="{ row }">
              <span>{{ row.entityCode || "-" }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="code" label="统一社会信用代码" align="left">
            <template slot-scope="{ row }">
              <span>{{ row.creditCode || "-" }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="accuracy" label="是否上市" align="center">
            <template slot-scope="{ row }">
              <span>{{ row.list }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="accuracy" label="是否发债" align="center">
            <template slot-scope="{ row }">
              <span>{{ row.issueBonds }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="date"
            label="质检通过比率"
            align="center"
            width="100px"
          >
            <template slot-scope="{ row }">
              <span>{{ row.totalRate || "-" }}</span>
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
    </keep-alive>

    <!-- 详情页 -->
    <details-view
      v-if="detalisShow"
      :entityCode="entityCode"
      :entityName="entityName"
      @goBack="detalisShow = false"
    ></details-view>
  </div>
</template>

<script>
import detailsView from "./components/detailsView.vue";
import { list } from "@/api/dataCheck";
import { whetherList } from "@/menu/index.js";
export default {
  components: { detailsView },
  data() {
    return {
      total: 0,
      queryParams: {
        crux: "", //关键字
        pageNum: 1,
        pageSize: 10,
        selectYear: [], //选中的年份
      },
      detalisShow: false,
      loading: true,
      tableData: [],
      whether: whetherList, //是否
      entityName: "", //下一个详情页参数
      entityCode: "", //下一个详情页参数
      list: "", //是否上市
      bonds: "", //是否发债
    };
  },
  methods: {
    handleQuery() {
      this.loading = true;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      this.$modal.loading("Loading...");
      try {
        const parmas = {
          list: this.list, //是否上市
          issueBonds: this.bonds, //是否发债
          keyword: this.queryParams.crux, //关键字
          years: this.queryParams.selectYear, //年份
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        list(parmas).then((res) => {
          const { data } = res;
          this.tableData = data.records;
          this.total = data.total;
          //表格渲染有点慢 先应付
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    //点击主体名称
    handleName(row) {
      this.entityCode = row.entityCode;
      this.entityName = row.entityName;
      this.detalisShow = true;
    },
    //年份
    changeYear(val) {
      this.queryParams.selectYear = val;
      this.handleQuery();
    },
    //是否上市
    changeList(val) {
      this.list = val;
      this.handleQuery();
    },
    //是否发债
    changeBonds(val) {
      this.bonds = val;
      this.handleQuery();
    },
  },
};
</script>

<style scoped lang="scss">
.homepage {
  background: #fff;
  min-height: calc(100vh - 180px);
  overflow-y: scroll;
  padding: 20px;
}
.query {
  margin: 10px 0 0px 0;
}

.table-row-name {
  font-size: 12px;
  color: #6d798f;
  letter-spacing: 0;
  font-weight: 400;
  text-decoration: underline;
}
</style>