<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上市日期 yyyy-mm-dd" prop="ipoDate">
        <el-date-picker clearable
          v-model="queryParams.ipoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上市日期 yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上市版" prop="ipoBoard">
        <el-input
          v-model="queryParams.ipoBoard"
          placeholder="请输入上市版"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行价格(港元)" prop="issPrice">
        <el-input
          v-model="queryParams.issPrice"
          placeholder="请输入发行价格(港元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际发行总数(百万股)" prop="issCountReal">
        <el-input
          v-model="queryParams.issCountReal"
          placeholder="请输入实际发行总数(百万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划发行总数(百万股)" prop="issCountPlan">
        <el-input
          v-model="queryParams.issCountPlan"
          placeholder="请输入计划发行总数(百万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="超额配售数量(百万股)" prop="issPlacingExceed">
        <el-input
          v-model="queryParams.issPlacingExceed"
          placeholder="请输入超额配售数量(百万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首发/预计募资总额(百万港元)" prop="issFundCount">
        <el-input
          v-model="queryParams.issFundCount"
          placeholder="请输入首发/预计募资总额(百万港元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发售募资净额(百万港元)" prop="issFundNet">
        <el-input
          v-model="queryParams.issFundNet"
          placeholder="请输入发售募资净额(百万港元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="超额配售募资净额(百万港元)" prop="issFundExceed">
        <el-input
          v-model="queryParams.issFundExceed"
          placeholder="请输入超额配售募资净额(百万港元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主承销商" prop="mainUnw">
        <el-input
          v-model="queryParams.mainUnw"
          placeholder="请输入主承销商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保荐人" prop="sponsor">
        <el-input
          v-model="queryParams.sponsor"
          placeholder="请输入保荐人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发售面值" prop="firstDenom">
        <el-input
          v-model="queryParams.firstDenom"
          placeholder="请输入发售面值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="面值货币" prop="firstCurrency">
        <el-input
          v-model="queryParams.firstCurrency"
          placeholder="请输入面值货币"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首发交易单位(股)" prop="firstUnit">
        <el-input
          v-model="queryParams.firstUnit"
          placeholder="请输入首发交易单位(股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招股公告日 yyyy-mm-dd" prop="prospDate">
        <el-date-picker clearable
          v-model="queryParams.prospDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择招股公告日 yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="招股价区间上限(港元)" prop="prospPriceMax">
        <el-input
          v-model="queryParams.prospPriceMax"
          placeholder="请输入招股价区间上限(港元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招股价区间下限(港元)" prop="prospPriceMin">
        <el-input
          v-model="queryParams.prospPriceMin"
          placeholder="请输入招股价区间下限(港元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属Wind行业" prop="belWind">
        <el-input
          v-model="queryParams.belWind"
          placeholder="请输入所属Wind行业"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属行业(HS)" prop="belModle">
        <el-input
          v-model="queryParams.belModle"
          placeholder="请输入所属行业(HS)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="H股/红筹股" prop="stockBelong">
        <el-input
          v-model="queryParams.stockBelong"
          placeholder="请输入H股/红筹股"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司介绍" prop="entityDes">
        <el-input
          v-model="queryParams.entityDes"
          placeholder="请输入公司介绍"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['crm:detail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['crm:detail:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['crm:detail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:detail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="上市日期 yyyy-mm-dd" align="center" prop="ipoDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ipoDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上市版" align="center" prop="ipoBoard" />
      <el-table-column label="发行方式" align="center" prop="issType" />
      <el-table-column label="发行价格(港元)" align="center" prop="issPrice" />
      <el-table-column label="实际发行总数(百万股)" align="center" prop="issCountReal" />
      <el-table-column label="计划发行总数(百万股)" align="center" prop="issCountPlan" />
      <el-table-column label="超额配售数量(百万股)" align="center" prop="issPlacingExceed" />
      <el-table-column label="首发/预计募资总额(百万港元)" align="center" prop="issFundCount" />
      <el-table-column label="发售募资净额(百万港元)" align="center" prop="issFundNet" />
      <el-table-column label="超额配售募资净额(百万港元)" align="center" prop="issFundExceed" />
      <el-table-column label="主承销商" align="center" prop="mainUnw" />
      <el-table-column label="保荐人" align="center" prop="sponsor" />
      <el-table-column label="发售面值" align="center" prop="firstDenom" />
      <el-table-column label="面值货币" align="center" prop="firstCurrency" />
      <el-table-column label="首发交易单位(股)" align="center" prop="firstUnit" />
      <el-table-column label="招股公告日 yyyy-mm-dd" align="center" prop="prospDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.prospDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="招股价区间上限(港元)" align="center" prop="prospPriceMax" />
      <el-table-column label="招股价区间下限(港元)" align="center" prop="prospPriceMin" />
      <el-table-column label="所属Wind行业" align="center" prop="belWind" />
      <el-table-column label="所属行业(HS)" align="center" prop="belModle" />
      <el-table-column label="H股/红筹股" align="center" prop="stockBelong" />
      <el-table-column label="公司介绍" align="center" prop="entityDes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:detail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:detail:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改证券发行-股票发行-首次发行明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="上市日期 yyyy-mm-dd" prop="ipoDate">
          <el-date-picker clearable
            v-model="form.ipoDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上市日期 yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上市版" prop="ipoBoard">
          <el-input v-model="form.ipoBoard" placeholder="请输入上市版" />
        </el-form-item>
        <el-form-item label="发行价格(港元)" prop="issPrice">
          <el-input v-model="form.issPrice" placeholder="请输入发行价格(港元)" />
        </el-form-item>
        <el-form-item label="实际发行总数(百万股)" prop="issCountReal">
          <el-input v-model="form.issCountReal" placeholder="请输入实际发行总数(百万股)" />
        </el-form-item>
        <el-form-item label="计划发行总数(百万股)" prop="issCountPlan">
          <el-input v-model="form.issCountPlan" placeholder="请输入计划发行总数(百万股)" />
        </el-form-item>
        <el-form-item label="超额配售数量(百万股)" prop="issPlacingExceed">
          <el-input v-model="form.issPlacingExceed" placeholder="请输入超额配售数量(百万股)" />
        </el-form-item>
        <el-form-item label="首发/预计募资总额(百万港元)" prop="issFundCount">
          <el-input v-model="form.issFundCount" placeholder="请输入首发/预计募资总额(百万港元)" />
        </el-form-item>
        <el-form-item label="发售募资净额(百万港元)" prop="issFundNet">
          <el-input v-model="form.issFundNet" placeholder="请输入发售募资净额(百万港元)" />
        </el-form-item>
        <el-form-item label="超额配售募资净额(百万港元)" prop="issFundExceed">
          <el-input v-model="form.issFundExceed" placeholder="请输入超额配售募资净额(百万港元)" />
        </el-form-item>
        <el-form-item label="主承销商" prop="mainUnw">
          <el-input v-model="form.mainUnw" placeholder="请输入主承销商" />
        </el-form-item>
        <el-form-item label="保荐人" prop="sponsor">
          <el-input v-model="form.sponsor" placeholder="请输入保荐人" />
        </el-form-item>
        <el-form-item label="发售面值" prop="firstDenom">
          <el-input v-model="form.firstDenom" placeholder="请输入发售面值" />
        </el-form-item>
        <el-form-item label="面值货币" prop="firstCurrency">
          <el-input v-model="form.firstCurrency" placeholder="请输入面值货币" />
        </el-form-item>
        <el-form-item label="首发交易单位(股)" prop="firstUnit">
          <el-input v-model="form.firstUnit" placeholder="请输入首发交易单位(股)" />
        </el-form-item>
        <el-form-item label="招股公告日 yyyy-mm-dd" prop="prospDate">
          <el-date-picker clearable
            v-model="form.prospDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择招股公告日 yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="招股价区间上限(港元)" prop="prospPriceMax">
          <el-input v-model="form.prospPriceMax" placeholder="请输入招股价区间上限(港元)" />
        </el-form-item>
        <el-form-item label="招股价区间下限(港元)" prop="prospPriceMin">
          <el-input v-model="form.prospPriceMin" placeholder="请输入招股价区间下限(港元)" />
        </el-form-item>
        <el-form-item label="所属Wind行业" prop="belWind">
          <el-input v-model="form.belWind" placeholder="请输入所属Wind行业" />
        </el-form-item>
        <el-form-item label="所属行业(HS)" prop="belModle">
          <el-input v-model="form.belModle" placeholder="请输入所属行业(HS)" />
        </el-form-item>
        <el-form-item label="H股/红筹股" prop="stockBelong">
          <el-input v-model="form.stockBelong" placeholder="请输入H股/红筹股" />
        </el-form-item>
        <el-form-item label="公司介绍" prop="entityDes">
          <el-input v-model="form.entityDes" placeholder="请输入公司介绍" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDetail, getDetail, delDetail, addDetail, updateDetail } from "@/api/crm/detail";

export default {
  name: "Detail",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 证券发行-股票发行-首次发行明细表格数据
      detailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        ipoDate: null,
        ipoBoard: null,
        issType: null,
        issPrice: null,
        issCountReal: null,
        issCountPlan: null,
        issPlacingExceed: null,
        issFundCount: null,
        issFundNet: null,
        issFundExceed: null,
        mainUnw: null,
        sponsor: null,
        firstDenom: null,
        firstCurrency: null,
        firstUnit: null,
        prospDate: null,
        prospPriceMax: null,
        prospPriceMin: null,
        belWind: null,
        belModle: null,
        stockBelong: null,
        entityDes: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询证券发行-股票发行-首次发行明细列表 */
    getList() {
      this.loading = true;
      listDetail(this.queryParams).then(response => {
        this.detailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        ipoDate: null,
        ipoBoard: null,
        issType: null,
        issPrice: null,
        issCountReal: null,
        issCountPlan: null,
        issPlacingExceed: null,
        issFundCount: null,
        issFundNet: null,
        issFundExceed: null,
        mainUnw: null,
        sponsor: null,
        firstDenom: null,
        firstCurrency: null,
        firstUnit: null,
        prospDate: null,
        prospPriceMax: null,
        prospPriceMin: null,
        belWind: null,
        belModle: null,
        stockBelong: null,
        entityDes: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加证券发行-股票发行-首次发行明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDetail(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改证券发行-股票发行-首次发行明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDetail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetail(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除证券发行-股票发行-首次发行明细编号为"' + ids + '"的数据项？').then(function() {
        return delDetail(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/detail/export', {
        ...this.queryParams
      }, `detail_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
