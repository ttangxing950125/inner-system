<template>
  <div>
    <go-back
      home="数据质检"
      :title="entityName"
      @click="$emit('goBack')"
    ></go-back>
    <icon-2-title>{{ entityName }}</icon-2-title>
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
            @keyup.enter.native="handleQuery"
            @change="handleQuery"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      :header-cell-style="headerStyles"
      :cell-style="cellStyles"
    >
      <el-table-column type="index" label="序号" align="left" width="130px" />
      <el-table-column prop="entityName" label="主体名称" align="left">
        <template slot-scope="{ row }">
          <span>{{ row.entityName || "-" }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="entityCode" label="主体代码" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.entityCode || "-" }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="统一社会信用代码" align="left">
        <template slot-scope="{ row }">
          <span>{{ row.creditCode || "-" }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="accuracy"
        label="是否通过质检"
        align="center"
        width="80px"
      >
        <template slot-scope="{ row }">
          <!--  === 1 ? "是" : "否" -->
          <span>{{ row.isInspection }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="date"
        label="是否通过系统质检"
        align="center"
        width="80px"
      >
        <template slot-scope="{ row }">
          <!--  === 1 ? "是" : "否" -->
          <span>{{ row.isSystemInspection }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="isArtificialInspection"
        label="是否通过人工质检"
        align="center"
        width="80px"
      >
        <template slot-scope="{ row }">
          <!-- === 1 ? "是" : "否" -->
          <span>{{ row.isArtificialInspection }}</span>
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
</template>

<script>
import { entityList } from "@/api/dataCheck";
export default {
  props: {
    year: {
      type: String,
      default: "",
    },
    entityName: {
      type: String,
      default: "",
    },
    code: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      detailsShow: true,
      total: 0,
      queryParams: {
        crux: "", //关键字
        pageNum: 1,
        pageSize: 10,
      },
      tableData: [],
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
    getList() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          code: this.code,
          year: this.year,
          keyword: this.queryParams.crux,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        entityList(parmas).then((res) => {
          const { data } = res;
          this.tableData = data.records;
          this.total = data.total;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },
    //表头
    headerStyles({ columnIndex }) {
      if (columnIndex > 3) {
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
.query {
  margin: 10px 0 0 0;
}
</style>