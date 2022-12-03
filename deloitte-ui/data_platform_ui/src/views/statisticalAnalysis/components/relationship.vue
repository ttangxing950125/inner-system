<template>
  <!-- 勾稽关系 -->
  <div class="container">
    <div v-if="!detalisShow">
      <icon-1-title>勾稽关系检验_一般企业</icon-1-title>
      <!-- 概述 -->
      <div class="margin-top14">
        <line-title class="margin-b10">概述</line-title>
        <div
          class="font2-400"
          style="margin-bottom: 7px"
          v-for="(item, index) in summary"
          :key="index + 'e'"
        >
          {{ index + 1 + "." + item }}
        </div>
      </div>

      <!-- 条建查询 -->
      <line-title class="margin-b10 margin-top30">检测范围</line-title>
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
              @keyup.native.enter="handldQuery"
              @change="handldQuery"
            ></el-input>
          </el-form-item>
          <el-form-item label="年份">
            <year-select
              :options="years"
              @change="changeYear"
              style="width: 130px"
            ></year-select>
          </el-form-item>

          <!-- <el-form-item style="margin-left: 12px" label-width="0px">
            <span class="data-btn">勾稽关系检验</span>
          </el-form-item> -->
        </el-form>
      </div>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        stripe
        style="width: 100%"
        :header-cell-style="headerStyles"
        :cell-style="cellStyles"
        v-loading="loading"
      >
        <el-table-column type="index" label="序号" align="center" />
        <el-table-column prop="code" label="校验规则" align="checkDescribe" />
        <el-table-column prop="reportDate" label="数据时间" align="left" />
        <el-table-column prop="migrationRate" label="通过比例" align="center" />
        <el-table-column prop="reportDate" label="数据时间" align="center" />
        <el-table-column
          prop="accuracy"
          label="是否通过系统质检"
          align="center"
        />
        <el-table-column label="数据校验详情" align="center">
          <template slot-scope="{ row }">
            <el-button type="text" @click="hanleSee(row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="isArtificialRecording"
          label="是否人工补录核查"
          align="center"
        >
          <template slot-scope="{ row }">
            {{ boolMenu[row.isArtificialRecording] }}
          </template>
        </el-table-column>

        <el-table-column
          prop="isArtificialInspection"
          label="是否通过人工质检"
          align="center"
        >
          <template slot-scope="{ row }">
            <el-button type="text" @click="hanleAdopt(row)">
              {{ boolMenu[row.isArtificialInspection] }}</el-button
            >
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
    <!-- 通过提示 -->
    <!--  :before-close="handleClose" -->
    <el-dialog
      class="dialog"
      title="提示"
      center
      :visible.sync="agrVisible"
      width="320px"
      style="margin-top: 30vh"
    >
      <span class="dialog-text"
        >是否确认该字段通过质检（通过则该数据可用于后续使用，无需后续人工补录）？</span
      >
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleDiaBtn(0)" size="mini">否</el-button>
        <el-button size="mini" type="primary" @click="handleDiaBtn(1)"
          >是</el-button
        >
      </span>
    </el-dialog>
    <!-- 查看 详情页 -->
    <relationship-details
      v-if="detalisShow"
      @back="detalisShow = false"
    ></relationship-details>
  </div>
</template>

<script>
import relationshipDetails from "./relationshipDetails.vue";
import { baseQuality } from "@/api/statisticalAnalysis/index.js";
import { boolMenu } from "@/menu/index.js";
export default {
  components: {
    relationshipDetails,
  },
  data() {
    return {
      boolMenu: boolMenu, //0否 1是
      agrVisible: false, //通过弹窗
      detalisShow: false, //详情页
      loading: true,
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWord: "", //关键字
        years: [],
      },
      //年份
      years: [],
      //上面基础信息
      summary: [
        " 一般企业、金融机构、地方政府",
        "基础层字段表，“其他”，指与主表无法构成勾稽关系的经营类字段类型；",
        "基础层字段表，“其他”，同类数据(如企业层级)不按产品线区分，通过产品标签的字段进行管理。",
      ],

      tableData: [
        {
          date: "主表",
          name: "王小虎",
          address: "上海",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上 弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
        {
          date: "主表",
          name: "王小虎",
          address: "上海弄",
          accuracy: 0.1,
        },
      ],
    };
  },
  mounted() {
    this.getList();
  },
  methods: {
    //条件查询
    handldQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    //获取表格
    getList() {
      try {
        this.loading = true;
        baseQuality(this.queryParams).then((res) => {
          if (res.code == 200) {
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
        });
      } finally {
        //表格渲染慢
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      }
    },
    //年份
    changeYear(val) {
      this.queryParams.years = val;
      this.handldQuery();
    },
    //表格查看
    hanleSee(row) {
      console.log(row, "表格查看");
    },
    //表格通过
    hanleAdopt(row) {
      this.agrVisible = true;
      console.log(row, "表格通过");
    },
    //点击dia 是否
    handleDiaBtn(type) {
      console.log(type, "是否");
      this.agrVisible = false;
    },
    //表头背景色
    headerStyles({ columnIndex }) {
      if (columnIndex > 6) {
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

<style lang="scss" scoped>
@import "@/assets/styles/dialog.scss";
.container {
  width: 100%;
  height: 100%;
  padding: 0 20px 0 20px;
}
.query {
  margin: 10px 0 10px 0;
}
.query-input {
  width: 282px;
  font-size: 12px;
}
.dialog-text {
  font-size: 12px;
  color: #35343a;
  letter-spacing: 0.5px;
  font-weight: 400;
}
::v-deep .el-dialog__footer {
  .el-button--primary {
    width: 60px;
    background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
    border-radius: 4px;
  }
  .el-button--default {
    width: 60px;
    background: #fff;
    border-radius: 4px;
  }
  .el-button--default:hover {
    width: 60px;
    background: #fff;
    border-radius: 4px;
  }
}
</style>