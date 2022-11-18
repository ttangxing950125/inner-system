<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="crm_task_dict的id" prop="taskDictId">
        <el-input
          v-model="queryParams.taskDictId"
          placeholder="请输入crm_task_dict的id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务描述" prop="taskDesc">
        <el-input
          v-model="queryParams.taskDesc"
          placeholder="请输入任务描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务日期" prop="taskDate">
        <el-date-picker clearable
          v-model="queryParams.taskDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择任务日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="任务文件名" prop="taskFileName">
        <el-input
          v-model="queryParams.taskFileName"
          placeholder="请输入任务文件名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务分类" prop="taskCategory">
        <el-input
          v-model="queryParams.taskCategory"
          placeholder="请输入任务分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否导入过该任务的文件  0-否 1-是" prop="imported">
        <el-input
          v-model="queryParams.imported"
          placeholder="请输入是否导入过该任务的文件  0-否 1-是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否确认过该任务的新增记录  0-否 1-是" prop="confirmInsert">
        <el-input
          v-model="queryParams.confirmInsert"
          placeholder="请输入是否确认过该任务的新增记录  0-否 1-是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否确认过该任务的更新记录  0-否 1-是" prop="confirmUpdate">
        <el-input
          v-model="queryParams.confirmUpdate"
          placeholder="请输入是否确认过该任务的更新记录  0-否 1-是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="该条任务是否完成   0-否 1-是" prop="complete">
        <el-input
          v-model="queryParams.complete"
          placeholder="请输入该条任务是否完成   0-否 1-是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务完成人" prop="handleUser">
        <el-input
          v-model="queryParams.handleUser"
          placeholder="请输入任务完成人"
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
      <el-form-item label="备注信息" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="请输入备注信息"
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
          v-hasPermi="['crm:windTask:add']"
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
          v-hasPermi="['crm:windTask:edit']"
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
          v-hasPermi="['crm:windTask:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:windTask:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="windTaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="crm_task_dict的id" align="center" prop="taskDictId" />
      <el-table-column label="任务描述" align="center" prop="taskDesc" />
      <el-table-column label="任务日期" align="center" prop="taskDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.taskDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务文件名" align="center" prop="taskFileName" />
      <el-table-column label="任务分类" align="center" prop="taskCategory" />
      <el-table-column label="是否导入过该任务的文件  0-否 1-是" align="center" prop="imported" />
      <el-table-column label="是否确认过该任务的新增记录  0-否 1-是" align="center" prop="confirmInsert" />
      <el-table-column label="是否确认过该任务的更新记录  0-否 1-是" align="center" prop="confirmUpdate" />
      <el-table-column label="该条任务是否完成   0-否 1-是" align="center" prop="complete" />
      <el-table-column label="任务完成人" align="center" prop="handleUser" />
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
      <el-table-column label="备注信息" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:windTask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:windTask:remove']"
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

    <!-- 添加或修改角色1的每日任务，导入wind文件的任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="crm_task_dict的id" prop="taskDictId">
          <el-input v-model="form.taskDictId" placeholder="请输入crm_task_dict的id" />
        </el-form-item>
        <el-form-item label="任务描述" prop="taskDesc">
          <el-input v-model="form.taskDesc" placeholder="请输入任务描述" />
        </el-form-item>
        <el-form-item label="任务日期" prop="taskDate">
          <el-date-picker clearable
            v-model="form.taskDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择任务日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="任务文件名" prop="taskFileName">
          <el-input v-model="form.taskFileName" placeholder="请输入任务文件名" />
        </el-form-item>
        <el-form-item label="任务分类" prop="taskCategory">
          <el-input v-model="form.taskCategory" placeholder="请输入任务分类" />
        </el-form-item>
        <el-form-item label="是否导入过该任务的文件  0-否 1-是" prop="imported">
          <el-input v-model="form.imported" placeholder="请输入是否导入过该任务的文件  0-否 1-是" />
        </el-form-item>
        <el-form-item label="是否确认过该任务的新增记录  0-否 1-是" prop="confirmInsert">
          <el-input v-model="form.confirmInsert" placeholder="请输入是否确认过该任务的新增记录  0-否 1-是" />
        </el-form-item>
        <el-form-item label="是否确认过该任务的更新记录  0-否 1-是" prop="confirmUpdate">
          <el-input v-model="form.confirmUpdate" placeholder="请输入是否确认过该任务的更新记录  0-否 1-是" />
        </el-form-item>
        <el-form-item label="该条任务是否完成   0-否 1-是" prop="complete">
          <el-input v-model="form.complete" placeholder="请输入该条任务是否完成   0-否 1-是" />
        </el-form-item>
        <el-form-item label="任务完成人" prop="handleUser">
          <el-input v-model="form.handleUser" placeholder="请输入任务完成人" />
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
        <el-form-item label="备注信息" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注信息" />
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
import { listWindTask, getWindTask, delWindTask, addWindTask, updateWindTask } from "@/api/crm/windTask";

export default {
  name: "WindTask",
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
      // 角色1的每日任务，导入wind文件的任务表格数据
      windTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskDictId: null,
        taskDesc: null,
        taskDate: null,
        taskFileName: null,
        taskCategory: null,
        imported: null,
        confirmInsert: null,
        confirmUpdate: null,
        complete: null,
        handleUser: null,
        created: null,
        updated: null,
        remarks: null
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
    /** 查询角色1的每日任务，导入wind文件的任务列表 */
    getList() {
      this.loading = true;
      listWindTask(this.queryParams).then(response => {
        this.windTaskList = response.rows;
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
        taskDictId: null,
        taskDesc: null,
        taskDate: null,
        taskFileName: null,
        taskCategory: null,
        imported: null,
        confirmInsert: null,
        confirmUpdate: null,
        complete: null,
        handleUser: null,
        created: null,
        updated: null,
        remarks: null
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
      this.title = "添加角色1的每日任务，导入wind文件的任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWindTask(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改角色1的每日任务，导入wind文件的任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWindTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWindTask(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除角色1的每日任务，导入wind文件的任务编号为"' + ids + '"的数据项？').then(function() {
        return delWindTask(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/windTask/export', {
        ...this.queryParams
      }, `windTask_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
