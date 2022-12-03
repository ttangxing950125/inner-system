<template>
  <!-- 基础层 -->
  <div class="container-info padding30">
    <div class="info-content">
      <icon-title>基础层字段配置</icon-title>
      <!-- 字段信息 -->
      <div class="query mt20">
        <!-- <el-form ref="form" :model="queryParams" inline>
          <el-form-item label-width="0px"> -->
        <el-input
          size="mini"
          v-model="queryParams.crux"
          placeholder="输入关键字进行搜索"
          prefix-icon="el-icon-search"
          style="width: 282px; margin-right: 20px"
          clearable
          @keyup.native.enter="handleQuery"
          @change="handleQuery"
        ></el-input>
        <!-- </el-form-item> -->
        <!-- <el-form-item label="数据来源" style="margin-left: 12px">
            <sources-select @change="sourceChange"></sources-select>
          </el-form-item> -->
        <!-- <el-form-item label-width="0" style="margin-left: 20px"> -->
        <el-button
          @click="handleAdd"
          icon="el-icon-plus"
          size="mini"
          class="add-btn"
          >新增基础层字段</el-button
        >
        <!-- </el-form-item> -->
        <!-- </el-form> -->
      </div>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        stripe
        style="width: 100%; margin-top: 20px"
        :header-cell-style="headerStyle"
        :cell-style="cellStyles"
      >
        <el-table-column
          prop="code"
          show-overflow-tooltip
          label="字段代码"
          align="left"
        />
        <el-table-column
          prop="name"
          label="字段名称"
          align="center"
          show-overflow-tooltip
        />
        <el-table-column
          prop="windSeq"
          label="wind优先级推荐"
          align="left"
          width="120"
        />
        <el-table-column
          prop="flushSeq"
          label="同花顺优先级推荐"
          align="left"
          width="150"
        />
        <el-table-column prop="accuracy" label="自动化优先级" align="left" />
        <el-table-column
          prop="artificialRecordingSeq"
          label="人工补录优先级推荐"
          align="left"
          width="150"
        />
        <el-table-column
          prop="changeRateUpper"
          label="变动率上限"
          align="left"
        />
        <el-table-column prop="thresholdValue" label="值域" align="left" />
        <el-table-column prop="accuracy" label="精度" align="left" />
        <el-table-column
          prop="address"
          label="修改"
          align="center"
          width="80px"
        >
          <template slot-scope="{ row }">
            <el-button type="text" @click="handleUpdate(row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        :autoScroll="false"
        @pagination="getList"
      />
    </div>
    <foundation-layer-dialog
      :title="diaTitle"
      :visible="diavisible"
      :info="editRow"
      @close="diaClose"
    ></foundation-layer-dialog>
  </div>
</template>

<script>
import foundationLayerDialog from "./foundationLayerDialog.vue";
import { list } from "@/api/paramsSeting";
export default {
  components: { foundationLayerDialog },
  props: {
    menuCode: {
      type: String,
    },
  },
  data() {
    return {
      diaTitle: "新增基础层字段",
      diavisible: false,
      queryParams: {
        crux: "", //关键字
        // dataSource: [], //数据来源
        pageNum: 1,
        pageSize: 10,
      },
      tableData: [],
      editRow: {},
      total: 0,
    };
  },
  methods: {
    //数据来源
    sourceChange(val) {
      this.queryParams.dataSource = val;
      this.handleQuery();
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          hierarchy: 1,
          entityType: this.menuCode,
          searchName: this.queryParams.crux,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        list(parmas).then((res) => {
          const { data } = res;
          this.tableData = data.records;
          this.total = data.total;
          console.log(res.data, "参数配置 基础层数据");
        });
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },

    //新增
    handleAdd() {
      this.diaTitle = "新增基础层字段";
      this.diavisible = true;
    },
    //修改
    handleUpdate(row) {
      this.diaTitle = "修改基础层字段";
      this.editRow = row;
      this.diavisible = true;
    },
    diaClose() {
      this.diavisible = false;
      this.getList();
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
.add-btn {
  background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
  color: #fff;
  font-size: 12px;
}
::v-deep .el-button--text {
  font-size: 12px;
  color: #6d798f;
  font-weight: 400;
  text-decoration: underline;
}
</style>