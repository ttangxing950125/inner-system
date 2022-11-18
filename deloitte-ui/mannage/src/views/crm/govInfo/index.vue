<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="政府主体官方名称" prop="govName">
        <el-input
          v-model="queryParams.govName"
          placeholder="请输入政府主体官方名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="官方行政代码，六位数字，各地方唯一" prop="govCode">
        <el-input
          v-model="queryParams.govCode"
          placeholder="请输入官方行政代码，六位数字，各地方唯一"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对于地方政府主体：
省级、地级、县级政府为“GV+官方行政代码”
经开区为“GVA”+000001开始排序
高新区为“GVB”+000001开始排序
新区为“GVC”+000001开始排序
其他类型区域暂以“GVZ”+000001开始排序" prop="dqGovCode">
        <el-input
          v-model="queryParams.dqGovCode"
          placeholder="请输入对于地方政府主体：
省级、地级、县级政府为“GV+官方行政代码”
经开区为“GVA”+000001开始排序
高新区为“GVB”+000001开始排序
新区为“GVC”+000001开始排序
其他类型区域暂以“GVZ”+000001开始排序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="sys_dict_data gov_level_big" prop="govLevelBig">
        <el-input
          v-model="queryParams.govLevelBig"
          placeholder="请输入sys_dict_data gov_level_big"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="sys_dict_data gov_level_small" prop="govLevelSmall">
        <el-input
          v-model="queryParams.govLevelSmall"
          placeholder="请输入sys_dict_data gov_level_small"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失效 0-有效 1-失效
一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府" prop="invalid">
        <el-input
          v-model="queryParams.invalid"
          placeholder="请输入失效 0-有效 1-失效
一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失效后的新政府名，如果没有填写就是撤销" prop="newGovName">
        <el-input
          v-model="queryParams.newGovName"
          placeholder="请输入失效后的新政府名，如果没有填写就是撤销"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失效后的新政府德勤code" prop="newDqCode">
        <el-input
          v-model="queryParams.newDqCode"
          placeholder="请输入失效后的新政府德勤code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失效后的新政府code" prop="newGovCode">
        <el-input
          v-model="queryParams.newGovCode"
          placeholder="请输入失效后的新政府code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建数据的用户名" prop="creater">
        <el-input
          v-model="queryParams.creater"
          placeholder="请输入创建数据的用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新数据的用户名" prop="updater">
        <el-input
          v-model="queryParams.updater"
          placeholder="请输入更新数据的用户名"
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
          v-hasPermi="['crm:govInfo:add']"
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
          v-hasPermi="['crm:govInfo:edit']"
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
          v-hasPermi="['crm:govInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:govInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="govInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="政府主体官方名称" align="center" prop="govName" />
      <el-table-column label="官方行政代码，六位数字，各地方唯一" align="center" prop="govCode" />
      <el-table-column label="对于地方政府主体：
省级、地级、县级政府为“GV+官方行政代码”
经开区为“GVA”+000001开始排序
高新区为“GVB”+000001开始排序
新区为“GVC”+000001开始排序
其他类型区域暂以“GVZ”+000001开始排序" align="center" prop="dqGovCode" />
      <el-table-column label="sys_dict_data  gov_type
1、地方政府
2、地方主管部门
3、其他" align="center" prop="govType" />
      <el-table-column label="sys_dict_data gov_level_big" align="center" prop="govLevelBig" />
      <el-table-column label="sys_dict_data gov_level_small" align="center" prop="govLevelSmall" />
      <el-table-column label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

每次有操作曾用名表的时候，重新更新这个字段" align="center" prop="govNameHis" />
      <el-table-column label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注" align="center" prop="entityNameHisRemarks" />
      <el-table-column label="失效 0-有效 1-失效
一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府" align="center" prop="invalid" />
      <el-table-column label="失效后的新政府名，如果没有填写就是撤销" align="center" prop="newGovName" />
      <el-table-column label="失效后的新政府德勤code" align="center" prop="newDqCode" />
      <el-table-column label="失效后的新政府code" align="center" prop="newGovCode" />
      <el-table-column label="创建数据的用户名" align="center" prop="creater" />
      <el-table-column label="更新数据的用户名" align="center" prop="updater" />
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
            v-hasPermi="['crm:govInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:govInfo:remove']"
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
        <el-form-item label="政府主体官方名称" prop="govName">
          <el-input v-model="form.govName" placeholder="请输入政府主体官方名称" />
        </el-form-item>
        <el-form-item label="官方行政代码，六位数字，各地方唯一" prop="govCode">
          <el-input v-model="form.govCode" placeholder="请输入官方行政代码，六位数字，各地方唯一" />
        </el-form-item>
        <el-form-item label="对于地方政府主体：
省级、地级、县级政府为“GV+官方行政代码”
经开区为“GVA”+000001开始排序
高新区为“GVB”+000001开始排序
新区为“GVC”+000001开始排序
其他类型区域暂以“GVZ”+000001开始排序" prop="dqGovCode">
          <el-input v-model="form.dqGovCode" placeholder="请输入对于地方政府主体：
省级、地级、县级政府为“GV+官方行政代码”
经开区为“GVA”+000001开始排序
高新区为“GVB”+000001开始排序
新区为“GVC”+000001开始排序
其他类型区域暂以“GVZ”+000001开始排序" />
        </el-form-item>
        <el-form-item label="sys_dict_data gov_level_big" prop="govLevelBig">
          <el-input v-model="form.govLevelBig" placeholder="请输入sys_dict_data gov_level_big" />
        </el-form-item>
        <el-form-item label="sys_dict_data gov_level_small" prop="govLevelSmall">
          <el-input v-model="form.govLevelSmall" placeholder="请输入sys_dict_data gov_level_small" />
        </el-form-item>
        <el-form-item label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

每次有操作曾用名表的时候，重新更新这个字段" prop="govNameHis">
          <el-input v-model="form.govNameHis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注" prop="entityNameHisRemarks">
          <el-input v-model="form.entityNameHisRemarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="失效 0-有效 1-失效
一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府" prop="invalid">
          <el-input v-model="form.invalid" placeholder="请输入失效 0-有效 1-失效
一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府" />
        </el-form-item>
        <el-form-item label="失效后的新政府名，如果没有填写就是撤销" prop="newGovName">
          <el-input v-model="form.newGovName" placeholder="请输入失效后的新政府名，如果没有填写就是撤销" />
        </el-form-item>
        <el-form-item label="失效后的新政府德勤code" prop="newDqCode">
          <el-input v-model="form.newDqCode" placeholder="请输入失效后的新政府德勤code" />
        </el-form-item>
        <el-form-item label="失效后的新政府code" prop="newGovCode">
          <el-input v-model="form.newGovCode" placeholder="请输入失效后的新政府code" />
        </el-form-item>
        <el-form-item label="创建数据的用户名" prop="creater">
          <el-input v-model="form.creater" placeholder="请输入创建数据的用户名" />
        </el-form-item>
        <el-form-item label="更新数据的用户名" prop="updater">
          <el-input v-model="form.updater" placeholder="请输入更新数据的用户名" />
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
import { listGovInfo, getGovInfo, delGovInfo, addGovInfo, updateGovInfo } from "@/api/crm/govInfo";

export default {
  name: "GovInfo",
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
      govInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        govName: null,
        govCode: null,
        dqGovCode: null,
        govType: null,
        govLevelBig: null,
        govLevelSmall: null,
        govNameHis: null,
        entityNameHisRemarks: null,
        invalid: null,
        newGovName: null,
        newDqCode: null,
        newGovCode: null,
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
      listGovInfo(this.queryParams).then(response => {
        this.govInfoList = response.rows;
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
        govName: null,
        govCode: null,
        dqGovCode: null,
        govType: null,
        govLevelBig: null,
        govLevelSmall: null,
        govNameHis: null,
        entityNameHisRemarks: null,
        invalid: null,
        newGovName: null,
        newDqCode: null,
        newGovCode: null,
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
      getGovInfo(id).then(response => {
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
            updateGovInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGovInfo(this.form).then(response => {
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
        return delGovInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/govInfo/export', {
        ...this.queryParams
      }, `govInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
