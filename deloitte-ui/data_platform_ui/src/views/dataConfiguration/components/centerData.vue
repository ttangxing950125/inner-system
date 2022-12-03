<template>
  <div class="padding20">
    <div class="pd20" v-if="settingPage" @click="setting(false)">
      <icon-title class="back mr10">返回列表</icon-title>
    </div>
    <div class="pd20" v-if="!settingPage">
      <el-input
        v-model="query"
        size="mini"
        class="query-input"
        placeholder="输入中间层字典表关键字"
        prefix-icon="el-icon-search"
        clearable
        :maxlength="64"
        @change="init(1)"
        @keyup.native.enter="init(1)"
      ></el-input>
      <el-table
        class="mt20"
        :data="tableData"
        stripe
        :header-cell-style="headerStyle"
        :cell-style="cellStyles"
      >
        <el-table-column
          width="100px"
          type="index"
          label="中间层evidence名称"
          align="center"
        />

        <el-table-column
          prop="code"
          label="evidence code"
          align="center"
          show-overflow-tooltip
        >
          <template slot-scope="{ row }">
            <span>{{ row.code || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="reportDate" label="配置时间" align="left">
          <template slot-scope="{ row }">
            <span>{{ row.reportDate || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="formulaDescribe"
          label="文字公式"
          align="left"
          show-overflow-tooltip
        >
          <template slot-scope="{ row }">
            <span>{{ row.formulaDescribe || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="formula"
          label="配置公式"
          align="center"
          show-overflow-tooltip
        >
          <template slot-scope="{ row }">
            <span>{{ row.formula || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="unit" label="单位" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.unit || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="accuracy" label="精度" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.accuracy || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="businessScene" label="使用场景" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.businessScene || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="accuracy"
          label="操作"
          align="center"
          width="180px"
        >
          <template slot-scope="{ row }">
            <el-button type="text" class="gray-btn" @click="setting(true, row)"
              >配置</el-button
            >
            <el-button type="text" class="gray-btn" @click="calculation(row)"
              >计算</el-button
            >
            <el-button type="text" class="gray-btn" @click="del(row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="pageNum"
        :limit.sync="pageSize"
        :autoScroll="false"
        @pagination="init()"
      />
    </div>
    <setting ref="seting" v-show="settingPage" :type="2"></setting>
    <div class="bootm pd20">
      <div class="flex">
        <div class="font ml10">计算结果</div>
        <div @click="saveFun">
          <span class="seting mr10">
            <i class="el-icon-folder-checked"></i> <span>保存配置</span></span
          >
        </div>
      </div>
      <el-table
        :data="secondData"
        stripe
        :header-cell-style="headerStyle"
        :cell-style="cellStyles"
      >
        <!--- 空数据状态的插槽 -->
        <template slot="empty">
          <span class="font none-font">暂无计算结果</span>
        </template>
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column
          width="100px"
          prop="formulaDescribe"
          label="字段中文名称"
          align="center"
        />

        <el-table-column prop="code" label="主体代码" align="center" />
        <el-table-column prop="reportDate" label="数据时间" align="left">
          <template slot-scope="{ row }">
            <span>{{ row.reportDate || "-" }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="name" :label="tableName.a0" align="left">
          <template slot-scope="{ row }">
            <span>{{ row.fields0 || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          v-if="tableName.a1"
          prop="name"
          :label="tableName.a1"
          align="left"
        >
          <template slot-scope="{ row }">
            <span>{{ row.fields1 || "-" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          v-if="tableName.a1"
          prop="name"
          :label="tableName.a2"
          align="left"
        >
          <template slot-scope="{ row }">
            <span>{{ row.fields2 || "-" }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total2 > 0"
        :total="total2"
        :page.sync="pageNum2"
        :limit.sync="pageSize2"
        :autoScroll="false"
        @pagination="calculation(paramsData)"
      />
    </div>
  </div>
</template>
  
<script>
import setting from "./setting.vue";
import { list, deleteSeting, addOrUpdate, calculation } from "@/api/dataSeting";
export default {
  components: {
    setting,
  },
  props: {
    index: {
      type: Number,
      default: 2,
    },
  },
  data() {
    return {
      query: "",
      tableData: [],
      secondData: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      total2: 0,
      pageNum2: 1,
      pageSize2: 20,
      settingPage: false,
      form: {},
      tableName: {
        a0: "",
        a1: "",
        a2: "",
      },
      paramsData: {},
    };
  },
  created() {
    this.init();
  },
  methods: {
    init(page) {
      try {
        this.$modal.loading("Loading...");
        // console.log(this.id)
        const parmas = {
          hierarchy: 2,
          searchName: this.query,
          pageNum: page || this.pageNum,
          pageSize: this.pageSize,
        };
        list(parmas).then((res) => {
          const { data } = res;
          this.tableData = data.records;
          this.total1 = data.total;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },
    setting(show, row) {
      this.settingPage = show;
      this.secondData = [];
      if (row) {
        this.$refs.seting.getSeting(row);
      }
      this.$eventBus.$emit("hideBtn", show);
    },
    calculation(row) {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          hierarchy: 2,
          id: row.id,
          formula: row.formula,
          formulaName: row.formulaDescribe,
          pageNum: this.pageNum2,
          pageSize: this.pageSize2,
        };
        this.paramsData = parmas;
        calculation(parmas).then((res) => {
          const { data } = res;
          this.secondData = data.records;
          this.total2 = data.total;

          // 获取列表内的 结算结果自定义表头
          this.secondData.forEach((e, eIndex) => {
            if (e.fields) {
              e.fields.forEach((f, index) => {
                e["fields" + index] = f.fieldValue;
                if (eIndex === 0) {
                  this.$set(this.tableName, "a" + index, f.fieldName);
                }
              });
            }
          });
        });
      } catch (error) {
        this.$message.error(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    del(row) {
      try {
        this.$modal.loading("Loading...");
        // console.log(this.id)
        const parmas = {
          hierarchy: 2,
          id: row.id,
        };
        deleteSeting(parmas).then((res) => {
          this.$message({
            message: "操作成功",
            type: "success",
          });
          this.init();
        });
      } catch (error) {
        this.$message.error(error);
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },
    saveFun() {
      try {
        this.$modal.loading("Loading...");
        const calculation = this.$refs.seting.calculation;
        let code = "";
        let name = "";
        calculation.forEach((e) => {
          code += e.code + " ";
          name += e.name + " ";
        });
        const form = this.$refs.seting.form;
        form.formula = code;
        form.formulaDescribe = name;
        form.hierarchy = 2;
        console.log(form);
        addOrUpdate(form).then((res) => {
          this.$message({
            message: "操作成功",
            type: "success",
          });
          this.form = {};
        });
      } catch (error) {
        this.$message.error(error);
      } finally {
        this.loading = false;
        this.$modal.closeLoading();
      }
    },
    getList() {
      this.init();
    },
  },
};
</script>

<style scoped lang='scss'>
.query-input {
  width: 282px;
  font-size: 12px;
}
.flex {
  height: 26px;
  justify-content: space-between;
  background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
  padding-bottom: 0px;
  align-items: center;
  .font {
    color: #ffffff;
    align-self: center;
  }
  .seting {
    font-size: 12px !important;
    // align-self: center;
    color: #ffffff;
    cursor: pointer;
  }
  .seting:hover {
    color: #ffb400;
  }
}
.back {
  font-size: 12px !important;
  // align-self: center;
  cursor: pointer;
  font-weight: 400;
}
</style>