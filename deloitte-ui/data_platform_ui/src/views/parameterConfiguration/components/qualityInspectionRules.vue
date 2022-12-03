<template>
  <!-- 基础层 -->
  <div class="container-info padding30">
    <div class="info-content">
      <icon-title>钩稽关系清单</icon-title>
      <!-- 字段信息 -->
      <div class="query mt20">
        <!-- <el-form ref="form" :model="query" inline>
          <el-form-item label-width="0px"> -->
        <el-input
          size="mini"
          v-model="queryParams.searchName"
          placeholder="输入关键字进行搜索"
          prefix-icon="el-icon-search"
          style="width: 282px; margin-right: 20px"
          clearable
          @keyup.native.enter="handleQuery"
          @change="handleQuery"
        ></el-input>
        <!-- </el-form-item>
          <el-form-item label-width="0" style="margin-left: 20px"> -->
        <el-button
          @click="handleAdd"
          icon="el-icon-plus"
          size="mini"
          class="add-btn"
          >新增校验规则</el-button
        >
        <!-- </el-form-item>
        </el-form> -->
      </div>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        stripe
        style="width: 100%; margin-top: 20px"
        :header-cell-style="headerStyle"
        :cell-style="cellStyles"
      >
        <el-table-column label="序号" type="index" align="left" width="70px" />
        <el-table-column prop="checkFormula" label="检验规则" align="left" />
        <el-table-column label="数据校验详情" align="left" width="100px">
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
        @pagination="getList"
      />
    </div>
    <rules-dialog
      :title="diaTitle"
      :visible="diavisible"
      :data="rules"
      @close="diaclose"
    ></rules-dialog>
  </div>
</template>

<script>
import rulesDialog from "./rulesDialog.vue";
import { modelDataCheckList } from "@/api/paramsSeting";

export default {
  components: { rulesDialog },
  props: {
    menuCode: {
      type: String,
    },
  },
  data() {
    return {
      diaTitle: "新增校验规则",
      diavisible: false,
      total: 0,
      queryParams: {
        searchName: "", //关键字
        pageNum: 1,
        pageSize: 10,
        // entityType: this.menuCode,
      },
      tableData: [],
      rules: {},
    };
  },

  methods: {
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    getList() {
      try {
        this.$modal.loading("Loading...");
        modelDataCheckList(this.queryParams).then((res) => {
          const { data } = res;
          this.tableData = data.records;
          this.total = data.total;
        });
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },

    //新增
    handleAdd() {
      this.diaTitle = "新增校验规则";
      this.diavisible = true;
    },
    //修改
    handleUpdate(row) {
      this.rules = row;
      this.diaTitle = "修改校验规则";
      this.diavisible = true;
    },
    diaclose() {
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