<template>
  <!-- 指标层 -->
  <div class="container-info padding30">
    <div class="info-content">
      <icon-title>指标层字段配置</icon-title>
      <!-- 字段信息 -->
      <div class="query mt20">
        <!-- <el-form ref="form" :model="queryParams" inline>
          <el-form-item label-width="0px"> -->
        <el-input
          size="mini"
          v-model="queryParams.crux"
          placeholder="输入关键字进行搜索"
          prefix-icon="el-icon-search"
          clearable
          style="width: 282px; margin-right: 20px"
          @change="handleQuery"
          @keyup.native.enter="handleQuery"
        ></el-input>
        <!-- </el-form-item> -->
        <!-- <el-form-item label="数据来源" style="margin-left: 12px">
            <sources-select @change="changeSource"></sources-select>
          </el-form-item> -->
        <!-- <el-form-item label-width="0" style="margin-left: 20px"> -->
        <!-- <el-button
          @click="handleAdd"
          icon="el-icon-plus"
          size="mini"
          class="add-btn"
          >新增指标层字段</el-button
        > -->
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
          label="字段代码"
          align="left"
          width="100px"
        />
        <el-table-column prop="name" label="字段名称" align="center" />
        <el-table-column
          prop="changeRateUpper"
          label="变动率上限"
          align="left"
        />
        <el-table-column prop="thresholdValue" label="值域" align="left" />
        <el-table-column prop="accuracy" label="精度" align="left" />
        <el-table-column prop="businessScene" label="使用场景" align="left" />
        <el-table-column
          prop="formulaDescribe"
          label="已配置公式"
          align="left"
        />
        <el-table-column
          prop="abnormalValueHandle"
          label="异常值处理"
          align="left"
        />
        <el-table-column label="修改" align="center" width="80px">
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
    <indicator-layer-dialog
      :title="diaTitle"
      :visible="diavisible"
      :info="diainfo"
      @close="diavisible = false"
    ></indicator-layer-dialog>
  </div>
</template>

<script>
import indicatorLayerDialog from "./indicatorLayerDialog.vue";
import { list } from "@/api/paramsSeting";
export default {
  components: { indicatorLayerDialog },
  props: {
    menuCode: {
      type: String,
    },
  },
  data() {
    return {
      diaTitle: "新增指标层字段",
      diavisible: false,
      total: 0,
      queryParams: {
        crux: "", //关键字
        pageNum: 1,
        pageSize: 10,
      },
      tableData: [],
      diainfo: {},
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
        const parmas = {
          entityType: this.menuCode,
          hierarchy: 3,
          searchName: this.queryParams.crux,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        list(parmas).then((res) => {
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
    //数据来源
    changeSource(val) {
      console.log(val, "数据来源");
      this.handleQuery();
    },
    //新增
    handleAdd() {
      this.diaTitle = "新增指标层字段";
      this.diavisible = true;
    },
    //修改
    handleUpdate(row) {
      this.diainfo = row;
      this.diaTitle = "修改指标层字段";
      this.diavisible = true;
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