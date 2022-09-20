<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名" prop="entityName">
        <el-input
          v-model="queryParams.entityName"
          placeholder="请输入企业名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="IB+自000001开始排序，每个企业唯一" prop="entityCode">
        <el-input
          v-model="queryParams.entityCode"
          placeholder="请输入IB+自000001开始排序，每个企业唯一"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="creditCode">
        <el-input
          v-model="queryParams.creditCode"
          placeholder="请输入统一社会信用代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否上市 0-未上市 1-已上市" prop="list">
        <el-input
          v-model="queryParams.list"
          placeholder="请输入是否上市 0-未上市 1-已上市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否发债 0-未发债 1-已发债" prop="issueBonds">
        <el-input
          v-model="queryParams.issueBonds"
          placeholder="请输入是否发债 0-未发债 1-已发债"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码是否异常 0-正常 1-异常" prop="creditError">
        <el-input
          v-model="queryParams.creditError"
          placeholder="请输入统一社会信用代码是否异常 0-正常 1-异常"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建这条数据的用户名" prop="creater">
        <el-input
          v-model="queryParams.creater"
          placeholder="请输入创建这条数据的用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最后一次更新这条数据的用户" prop="updater">
        <el-input
          v-model="queryParams.updater"
          placeholder="请输入最后一次更新这条数据的用户"
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
          v-hasPermi="['crm:entityInfo:add']"
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
          v-hasPermi="['crm:entityInfo:edit']"
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
          v-hasPermi="['crm:entityInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:entityInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="entityInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="企业名" align="center" prop="entityName" />
      <el-table-column label="IB+自000001开始排序，每个企业唯一" align="center" prop="entityCode" />
      <el-table-column label="统一社会信用代码" align="center" prop="creditCode" />
      <el-table-column label="是否上市 0-未上市 1-已上市" align="center" prop="list" />
      <el-table-column label="是否发债 0-未发债 1-已发债" align="center" prop="issueBonds" />
      <el-table-column label="统一社会信用代码是否异常 0-正常 1-异常" align="center" prop="creditError" />
      <el-table-column label="若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：
1、吊销
2、注销
3、非大陆注册机构
4、其他未知原因
5、正常" align="center" prop="creditErrorType" />
      <el-table-column label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

每次有操作曾用名表的时候，重新更新这个字段" align="center" prop="entityNameHis" />
      <el-table-column label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注" align="center" prop="entityNameHisRemarks" />
      <el-table-column label="创建这条数据的用户名" align="center" prop="creater" />
      <el-table-column label="最后一次更新这条数据的用户" align="center" prop="updater" />
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
            v-hasPermi="['crm:entityInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:entityInfo:remove']"
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
        <el-form-item label="企业名" prop="entityName">
          <el-input v-model="form.entityName" placeholder="请输入企业名" />
        </el-form-item>
        <el-form-item label="IB+自000001开始排序，每个企业唯一" prop="entityCode">
          <el-input v-model="form.entityCode" placeholder="请输入IB+自000001开始排序，每个企业唯一" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="creditCode">
          <el-input v-model="form.creditCode" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="是否上市 0-未上市 1-已上市" prop="list">
          <el-input v-model="form.list" placeholder="请输入是否上市 0-未上市 1-已上市" />
        </el-form-item>
        <el-form-item label="是否发债 0-未发债 1-已发债" prop="issueBonds">
          <el-input v-model="form.issueBonds" placeholder="请输入是否发债 0-未发债 1-已发债" />
        </el-form-item>
        <el-form-item label="统一社会信用代码是否异常 0-正常 1-异常" prop="creditError">
          <el-input v-model="form.creditError" placeholder="请输入统一社会信用代码是否异常 0-正常 1-异常" />
        </el-form-item>
        <el-form-item label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

每次有操作曾用名表的时候，重新更新这个字段" prop="entityNameHis">
          <el-input v-model="form.entityNameHis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注" prop="entityNameHisRemarks">
          <el-input v-model="form.entityNameHisRemarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创建这条数据的用户名" prop="creater">
          <el-input v-model="form.creater" placeholder="请输入创建这条数据的用户名" />
        </el-form-item>
        <el-form-item label="最后一次更新这条数据的用户" prop="updater">
          <el-input v-model="form.updater" placeholder="请输入最后一次更新这条数据的用户" />
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
import { listEntityInfo, getEntityInfo, delEntityInfo, addEntityInfo, updateEntityInfo } from "@/api/crm/entityInfo";

export default {
  name: "EntityInfo",
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
      entityInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        entityName: null,
        entityCode: null,
        creditCode: null,
        list: null,
        issueBonds: null,
        creditError: null,
        creditErrorType: null,
        entityNameHis: null,
        entityNameHisRemarks: null,
        creater: null,
        updater: null,
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
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listEntityInfo(this.queryParams).then(response => {
        this.entityInfoList = response.rows;
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
        entityName: null,
        entityCode: null,
        creditCode: null,
        list: null,
        issueBonds: null,
        creditError: null,
        creditErrorType: null,
        entityNameHis: null,
        entityNameHisRemarks: null,
        creater: null,
        updater: null,
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
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEntityInfo(id).then(response => {
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
            updateEntityInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEntityInfo(this.form).then(response => {
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
        return delEntityInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/entityInfo/export', {
        ...this.queryParams
      }, `entityInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
