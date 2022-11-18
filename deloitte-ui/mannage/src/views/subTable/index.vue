<template>
  <div class="app-container home">
    <div class="home">
      <div class="mt20">
        <div class="flex1 selet-box">
          <h3 class="g-t-title">政府主体</h3>
          <el-input
            class="select-x"
            v-model="govInput"
            placeholder="搜索主体名称/代码/统一社会信用代码"
            @change="getGovList('gov')"
          ></el-input>
        </div>
        <el-table class="table-content" :data="list" style="margin-top: 15px">
          <el-table-column type="index" label="生效状态" width="100">
            <template slot-scope="scope">
              <div>{{ scope.row.status ? "Y" : "N" }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="dqCode" label="政府主体代码">
          </el-table-column>
          <el-table-column prop="govName" label="政府主体名称">
          </el-table-column>
          <el-table-column prop="oldName" label="曾用名或别称">
          </el-table-column>
          <el-table-column prop="updated" label="变更日期">
            <template slot-scope="scope">
              <div>{{ retFormatDate(scope.row.updated) }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="remarks" label="备注"> </el-table-column>
          <el-table-column prop="zip" label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleClick(scope.row, 'gov', 'edit')"
                type="text"
                size="small"
                >修改</el-button
              >
              <el-button
                @click="handleClick(scope.row, 'gov', 'stop')"
                type="text"
                size="small"
                >停用</el-button
              >
            </template></el-table-column
          >
        </el-table>
        <el-button @click="handleClick(scope.row)" type="text"
          >增加记录</el-button
        >
      </div>
      <div class="mt20">
        <div class="flex1 selet-box">
          <h3 class="g-t-title">企业主体</h3>
          <el-input
            class="select-x"
            v-model="entityInput"
            placeholder="搜索主体名称/代码/统一社会信用代码"
            @change="getGovList"
          ></el-input>
        </div>
        <el-table class="table-content" :data="list2" style="margin-top: 15px">
          <el-table-column type="index" label="生效状态" width="100">
            <template slot-scope="scope">
              <div>{{ scope.row.status ? "Y" : "N" }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="entityCode" label="企业主体代码">
          </el-table-column>
          <el-table-column prop="entityName" label="企业主体名称">
          </el-table-column>
          <el-table-column prop="creditCode" label="统一社会信用代码">
          </el-table-column>
          <el-table-column prop="oldName" label="曾用名或别称">
          </el-table-column>
          <el-table-column prop="city" label="变更日期">
            <template slot-scope="scope">
              <div>{{ retFormatDate(scope.row.updated) }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="remarks" label="备注"> </el-table-column>
          <el-table-column prop="zip" label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleClick(scope.row, 'entity', 'edit')"
                type="text"
                size="small"
                >修改</el-button
              >
              <el-button
                @click="handleClick(scope.row, 'entity', 'stop')"
                type="text"
                size="small"
                >停用</el-button
              >
            </template></el-table-column
          >
        </el-table>
        <el-button @click="handleClick(scope.row)" type="text"
          >增加记录</el-button
        >
      </div>
    </div>
    <el-dialog title="修改" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="德勤统一识别码">
          <el-input v-model="form.dqCode"></el-input>
        </el-form-item>
        <el-form-item label="修改后的曾用名">
          <el-input v-model="form.newOldName"></el-input>
        </el-form-item>
        <el-form-item label="原本的曾用名">
          <el-input v-model="form.oldName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getGovHisNameList,
  getEntityHisNameList,
  updateOldName,
  updateOldNameGov,
} from "@/api/subject";
import { formatDate } from "@/utils/index";
export default {
  name: "government",
  data() {
    return {
      entityInput: "",
      govInput: "",
      list: [],
      list2: [],
      loading: false,
      dialogVisible: false,
      form: {},
      editType: "",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    handleClick(row, type, action) {
      try {
        this.$modal.loading("Loading...");
        this.editType = type;
        const dqCode = type === "gov" ? row.dqCode : row.entityCode;
        if (action === "edit") {
          this.$set(this.form, "oldName", row.oldName || "");
          this.$set(this.form, "dqCode", dqCode || "");
          this.dialogVisible = true;
        } else {
          const parmas = {
            dqCode: dqCode,
            state: 1,
            oldName: row.oldName,
          };
          if (type === "gov") {
            updateOldNameGov(parmas).then((res) => {
              this.$message({
                showClose: true,
                message: "操作成功",
                type: "success",
              });
            });
          } else {
            updateOldName(parmas).then((res) => {
              this.$message({
                showClose: true,
                message: "操作成功",
                type: "success",
              });
            });
          }
        }
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    getGovList(row) {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          param: row === "gov" ? this.govInput : this.entityInput,
        };
        if (row === "gov") {
          getGovHisNameList(parmas).then((res) => {
            const { data } = res;
            this.list = data;
          });
        } else {
          getEntityHisNameList(parmas).then((res) => {
            const { data } = res;
            this.list2 = data;
          });
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    retFormatDate(row) {
      return formatDate(row);
    },
    submit() {
      try {
        this.$modal.loading("Loading...");
        this.form.status = "";
        if (this.editType === "entity") {
          updateOldName(this.form).then((res) => {
            if (res.code === 200) {
              this.dialogVisible = false;
              this.$message({
                showClose: true,
                message: "操作成功",
                type: "success",
              });
            }
          });
        } else {
          updateOldNameGov(this.form).then((res) => {
            if (res.code === 200) {
              this.dialogVisible = false;
              this.$message({
                showClose: true,
                message: "操作成功",
                type: "success",
              });
            }
          });
        }
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
  },
};
</script>

<style scoped lang="scss">
.selet-box {
  justify-content: space-between;
}

.g-t-title {
  font-weight: 600;
}
.select-x {
  margin-top: 10px;
  width: 24%;
}
</style>
