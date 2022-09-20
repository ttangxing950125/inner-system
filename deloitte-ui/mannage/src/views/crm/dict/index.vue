<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="wind文件分类名" prop="cateName">
        <el-input
          v-model="queryParams.cateName"
          placeholder="请输入wind文件分类名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="wind文件具体名称" prop="windFileName">
        <el-input
          v-model="queryParams.windFileName"
          placeholder="请输入wind文件具体名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件数据存放在哪个数据表中" prop="fileTable">
        <el-input
          v-model="queryParams.fileTable"
          placeholder="请输入文件数据存放在哪个数据表中"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="每天的权利文件数据放在哪个数据表中" prop="fileTableHis">
        <el-input
          v-model="queryParams.fileTableHis"
          placeholder="请输入每天的权利文件数据放在哪个数据表中"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="wind文件任务描述" prop="taskDesc">
        <el-input
          v-model="queryParams.taskDesc"
          placeholder="请输入wind文件任务描述"
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
      <el-form-item label="${comment}" prop="updated">
        <el-date-picker clearable
          v-model="queryParams.updated"
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
          v-hasPermi="['crm:dict:add']"
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
          v-hasPermi="['crm:dict:edit']"
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
          v-hasPermi="['crm:dict:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:dict:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dictList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="wind文件分类名" align="center" prop="cateName" />
      <el-table-column label="wind文件具体名称" align="center" prop="windFileName" />
      <el-table-column label="文件数据存放在哪个数据表中" align="center" prop="fileTable" />
      <el-table-column label="每天的权利文件数据放在哪个数据表中" align="center" prop="fileTableHis" />
      <el-table-column label="wind文件任务描述" align="center" prop="taskDesc" />
      <el-table-column label="任务文件状态 0-禁用 1-启用" align="center" prop="status" />
      <el-table-column label="${comment}" align="center" prop="created" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.created, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="${comment}" align="center" prop="updated" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updated, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:dict:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:dict:remove']"
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

    <!-- 添加或修改导入的wind文件分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="wind文件分类名" prop="cateName">
          <el-input v-model="form.cateName" placeholder="请输入wind文件分类名" />
        </el-form-item>
        <el-form-item label="wind文件具体名称" prop="windFileName">
          <el-input v-model="form.windFileName" placeholder="请输入wind文件具体名称" />
        </el-form-item>
        <el-form-item label="文件数据存放在哪个数据表中" prop="fileTable">
          <el-input v-model="form.fileTable" placeholder="请输入文件数据存放在哪个数据表中" />
        </el-form-item>
        <el-form-item label="每天的权利文件数据放在哪个数据表中" prop="fileTableHis">
          <el-input v-model="form.fileTableHis" placeholder="请输入每天的权利文件数据放在哪个数据表中" />
        </el-form-item>
        <el-form-item label="wind文件任务描述" prop="taskDesc">
          <el-input v-model="form.taskDesc" placeholder="请输入wind文件任务描述" />
        </el-form-item>
        <el-form-item label="${comment}" prop="created">
          <el-date-picker clearable
            v-model="form.created"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择${comment}">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="${comment}" prop="updated">
          <el-date-picker clearable
            v-model="form.updated"
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
import { listDict, getDict, delDict, addDict, updateDict } from "@/api/crm/dict";

export default {
  name: "Dict",
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
      // 导入的wind文件分类表格数据
      dictList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cateName: null,
        windFileName: null,
        fileTable: null,
        fileTableHis: null,
        taskDesc: null,
        status: null,
        created: null,
        updated: null
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
    /** 查询导入的wind文件分类列表 */
    getList() {
      this.loading = true;
      listDict(this.queryParams).then(response => {
        this.dictList = response.rows;
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
        cateName: null,
        windFileName: null,
        fileTable: null,
        fileTableHis: null,
        taskDesc: null,
        status: 0,
        created: null,
        updated: null
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
      this.title = "添加导入的wind文件分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDict(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改导入的wind文件分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDict(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDict(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除导入的wind文件分类编号为"' + ids + '"的数据项？').then(function() {
        return delDict(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/dict/export', {
        ...this.queryParams
      }, `dict_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
