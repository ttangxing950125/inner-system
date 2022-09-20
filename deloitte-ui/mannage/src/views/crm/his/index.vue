<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="德勤code" prop="dqCode">
        <el-input
          v-model="queryParams.dqCode"
          placeholder="请输入德勤code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="改名日期" prop="happenDate">
        <el-date-picker clearable
          v-model="queryParams.happenDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择改名日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="曾用名" prop="oldName">
        <el-input
          v-model="queryParams.oldName"
          placeholder="请输入曾用名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="请输入备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人，为null的话就是系统创建" prop="creater">
        <el-input
          v-model="queryParams.creater"
          placeholder="请输入创建人，为null的话就是系统创建"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="${comment}" prop="created">
        <el-date-picker clearable
          v-model="queryParams.created"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="${comment}" prop="update">
        <el-date-picker clearable
          v-model="queryParams.update"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
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
          v-hasPermi="['crm:his:add']"
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
          v-hasPermi="['crm:his:edit']"
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
          v-hasPermi="['crm:his:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:his:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hisList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="1-企业主体 2-政府" align="center" prop="entityType" />
      <el-table-column label="生效状态 0-失效 1-生效" align="center" prop="status" />
      <el-table-column label="德勤code" align="center" prop="dqCode" />
      <el-table-column label="改名日期" align="center" prop="happenDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.happenDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="曾用名" align="center" prop="oldName" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作" align="center" prop="source" />
      <el-table-column label="创建人，为null的话就是系统创建" align="center" prop="creater" />
      <el-table-column label="${comment}" align="center" prop="created" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.created, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="${comment}" align="center" prop="update" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.update, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:his:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:his:remove']"
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

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="德勤code" prop="dqCode">
          <el-input v-model="form.dqCode" placeholder="请输入德勤code" />
        </el-form-item>
        <el-form-item label="改名日期" prop="happenDate">
          <el-date-picker clearable
            v-model="form.happenDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择改名日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="曾用名" prop="oldName">
          <el-input v-model="form.oldName" placeholder="请输入曾用名" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作" prop="source">
          <el-input v-model="form.source" placeholder="请输入记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作" />
        </el-form-item>
        <el-form-item label="创建人，为null的话就是系统创建" prop="creater">
          <el-input v-model="form.creater" placeholder="请输入创建人，为null的话就是系统创建" />
        </el-form-item>
        <el-form-item label="${comment}" prop="created">
          <el-date-picker clearable
            v-model="form.created"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择${comment}">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="${comment}" prop="update">
          <el-date-picker clearable
            v-model="form.update"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择${comment}">
          </el-date-picker>
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
import { listHis, getHis, delHis, addHis, updateHis } from "@/api/crm/his";

export default {
  name: "His",
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
      // 【请填写功能名称】表格数据
      hisList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        entityType: null,
        status: null,
        dqCode: null,
        happenDate: null,
        oldName: null,
        remarks: null,
        source: null,
        creater: null,
        created: null,
        update: null
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
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listHis(this.queryParams).then(response => {
        this.hisList = response.rows;
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
        entityType: null,
        status: 0,
        dqCode: null,
        happenDate: null,
        oldName: null,
        remarks: null,
        source: null,
        creater: null,
        created: null,
        update: null
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
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHis(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHis(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHis(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项？').then(function() {
        return delHis(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/his/export', {
        ...this.queryParams
      }, `his_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
