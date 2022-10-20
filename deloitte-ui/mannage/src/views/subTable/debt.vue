<template>
  <div class="app-container home">
    <div class="home">
      <div class="mt20">
        <div class="flex1 between">
          <h3 class="g-t-title">债券交易信息表</h3>
          <div class="mt20">
            <el-button @click="addNew" type="text" size="small"
              >手动添加债券</el-button
            >
          </div>
        </div>
        <div>
          <div class="select-body">
            <div class="g-desc flex1">
              <a :class="tab === 1 ? 'g-select' : ''" @click="changeTab(1)"
                >搜主体</a
              >
              <a :class="tab === 2 ? 'g-select' : ''" @click="changeTab(2)"
                >搜债券</a
              >
            </div>
            <div class="">
              <el-input
                class="mr10 ml20"
                v-model="input"
                style="width: 400px"
                placeholder="请输入内容"
              ></el-input>
              <el-button class="mr10" type="primary" @click="select"
                >查询</el-button
              >
            </div>
          </div>
        </div>
        <el-table class="table-content" :data="list" style="margin-top: 15px">
          <el-table-column type="index" sortable label="序号">
          </el-table-column>
          <el-table-column prop="entityCode" label="德勤主体代码" sortable>
          </el-table-column>
          <el-table-column prop="entityName" label="企业名称">
            <template slot-scope="scope">
              <div v-html="replaceFun(scope.row.entityName)"></div>
            </template>
          </el-table-column>
          <el-table-column prop="creditCode" label="统一社会信用代码">
          </el-table-column>
          <el-table-column prop="bondCode" label="债券交易代码">
          </el-table-column>
          <el-table-column prop="zip" label="操作">
            <template slot-scope="scope">
              <el-button @click="setTarget(scope.row)" type="text" size="small"
                >设为目标</el-button
              >
            </template></el-table-column
          >
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <div class="mt20">
        <div class="flex1">
          <h3 class="g-t-title">目标主体发行债券</h3>
          <div class="mt20">
            【 <span class="font-color">请从上表选定目标主体</span> 】
          </div>
        </div>
        <el-table class="table-content" :data="list2" style="margin-top: 15px">
          <el-table-column type="index" label="序号"> </el-table-column>
          <el-table-column prop="transactionCode" label="债券交易代码">
          </el-table-column>
          <el-table-column prop="fullName" label="债券全称"> </el-table-column>
          <el-table-column prop="shortName" label="债券简称"> </el-table-column>
          <!-- <el-table-column prop="bondState" label="存续状态">
          <template slot-scope="scope">
            <span>{{ scope.row.bondState === 0 ? "Y" : "N" }}</span>
          </template>
        </el-table-column> -->
          <el-table-column
            prop="transactionCode"
            label="债募类型"
          ></el-table-column>
          <el-table-column label="公私募类型">
            <template slot-scope="scope">
              <span>{{ scope.row.raiseType === 0 ? "公募" : "私募" }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="是否违约">
          <template slot-scope="scope">
            <span>{{ scope.row.bondState === 1 ? "Y" : "N" }}</span>
          </template>
        </el-table-column> -->
          <el-table-column prop="zip" label="操作">
            <template slot-scope="scope">
              <el-button @click="setTarget2(scope.row)" type="text" size="small"
                >设为目标</el-button
              >
            </template></el-table-column
          >
        </el-table>
        <div class="flex1 between">
          <h3 class="g-t-title">债券交易信息表</h3>
          <div class="mt20">
            <el-button @click="submit()" type="text" size="small"
              >提交变更</el-button
            >
          </div>
        </div>
        <el-card>
          <el-col :sm="24" :lg="12" class="form-card">
            <div
              v-for="(item, index) in xContent[0]"
              :key="index"
              class="flex1 mt10"
            >
              <div class="first">{{ item.name }}</div>
              <el-input
                v-if="item.enableEdite"
                class="t-input"
                v-model="item.value"
                @change="item.edit = true"
              ></el-input>
              <div v-else class="content">{{ item.value || "-" }}</div>
            </div>
          </el-col>
          <el-col :sm="24" :lg="12" class="form-card">
            <div
              v-for="(item, index) in xContent[1]"
              :key="index"
              class="flex1 mt10"
            >
              <div class="first">{{ item.name }}</div>
              <el-input
                v-if="item.enableEdite"
                class="t-input"
                v-model="item.value"
                @change="item.edit = true"
              ></el-input>
              <div v-else class="content">{{ item.value || "-" }}</div>
            </div>
          </el-col>
          <span v-if="xContent.length === 0">暂无数据</span>
        </el-card>
      </div>
      <el-dialog
        title="手动添加债券信息"
        :visible.sync="dialogVisible"
        width="50%"
      >
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="195px"
          label-position="left"
        >
          <el-form-item label="债券全称" prop="">
            <el-input
              class="t-input"
              v-model="ruleForm.bondFullName"
            ></el-input>
          </el-form-item>
          <el-form-item label="债券简称" prop="">
            <el-input
              class="t-input"
              v-model="ruleForm.bondShortName"
            ></el-input>
          </el-form-item>
          <el-form-item label="债券交易代码" prop="">
            <el-input class="t-input" v-model="ruleForm.oriCode"></el-input>
          </el-form-item>
          <el-form-item label="wind债券类型" prop="">
            <el-col :span="11">
              <el-form-item prop="date1">
                <el-select
                  class="mr10 selects"
                  v-model="ruleForm.windTypeOne"
                  placeholder="下拉选择债券类型-I"
                  @change="selectChange"
                >
                  <el-option
                    v-for="item in wind1"
                    :key="item.id"
                    :label="item.value"
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
              <el-form-item prop="date2">
                <el-select
                  class="mr10 selects"
                  v-model="ruleForm.windTypeTwo"
                  placeholder="下拉选择债券类型-II"
                >
                  <el-option
                    v-for="item in wind2"
                    :key="item.id"
                    :label="item.value"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="公私募类型" prop="">
            <el-radio-group v-model="ruleForm.raiseType">
              <el-radio label="0">公募</el-radio>
              <el-radio label="1">私募</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="债券状态" prop="">
            <el-radio-group v-model="ruleForm.bondState">
              <el-radio label="0">存续</el-radio>
              <el-radio label="1">违约</el-radio>
              <el-radio label="2">已兑付</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="起息日" prop="">
            <el-input class="t-input" v-model="ruleForm.valueDate"></el-input>
          </el-form-item>
          <el-form-item label="到期兑付日" prop="">
            <el-input
              class="t-input"
              v-model="ruleForm.dueCashingDate"
            ></el-input>
          </el-form-item>
          <el-form-item label="债务主体统一社会信用代码" prop="">
            <el-input
              class="t-input"
              v-model="ruleForm.creditCode"
              @change="getCode"
            ></el-input>
          </el-form-item>
          <el-form-item label="债务主体名称" prop="">
            <span v-if="ruleForm.entityCode" class="balck">{{
              ruleForm.entityName
            }}</span>
            <span v-else class="color-gary">根据统一社会信用代码自动匹配</span>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitAdd">确认新增</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  findBondOrEntity,
  findRelationEntityOrBond,
  findAllDetail,
  editAllDetail,
  insertBondInfoManual,
  getWindBondType,
  getEntityInfo,
} from "@/api/bond";
import pagination from "../../components/Pagination";
import { replaceStr, sliceIntoChunks } from "@/utils/index";
export default {
  name: "government",
  components: {
    pagination,
  },
  data() {
    return {
      input: "",
      list: [],
      list2: [],
      loading: false,
      dialogVisible: false,
      ruleForm: {},
      rules: {
        name: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        region: [
          { required: true, message: "请选择活动区域", trigger: "change" },
        ],
        date1: [
          {
            type: "date",
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
        date2: [
          {
            type: "date",
            required: true,
            message: "请选择时间",
            trigger: "change",
          },
        ],
        type: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个活动性质",
            trigger: "change",
          },
        ],
        resource: [
          { required: true, message: "请选择活动资源", trigger: "change" },
        ],
        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }],
      },
      tab: 1,
      entityCode: "",
      xContent: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      wind1: [],
      wind2: [],
    };
  },
  created() {},
  methods: {
    getBondOrEntity() {
      this.$modal.loading("loading...");
      try {
        const parmas = {
          keyword: this.tab === 1 ? "ENTITY" : "BOND",
          name: this.input,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        findBondOrEntity(parmas).then((res) => {
          const { data } = res;
          this.list = [];
          this.total = data.total;
          this.queryParams.pageNum = data.pages;
          data.records.forEach((e) => {
            this.list.push(e.entityVo);
          });
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    select() {
      this.getBondOrEntity();
    },
    addNew() {
      this.dialogVisible = true;
      try {
        this.$modal.loading("loading...");
        getWindBondType({ id: "" }).then((res) => {
          const { data } = res;
          this.wind1 = data;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    selectChange(row) {
      getWindBondType({ id: row.id }).then((res) => {
        const { data } = res;
        this.wind2 = data;
      });
    },
    changeTab(tab) {
      this.tab = tab;
    },
    setTarget(row) {
      this.$modal.loading("loading...");
      try {
        const parmas = {
          keyword: this.tab === 1 ? "ENTITY" : "BOND",
          id: row.id,
        };
        this.entityCode = row.entityCode;
        findRelationEntityOrBond(parmas).then((res) => {
          const { data } = res;
          this.list2 = [];
          data.forEach((e) => {
            this.list2.push(e.bondVo);
          });
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    setTarget2(row) {
      this.$modal.loading("loading...");
      try {
        const parmas = {
          bondCode: row.bondCode,
          entityCode: this.entityCode,
        };
        findAllDetail(parmas).then((res) => {
          const { data } = res;
          this.xContent = data;
          this.xContent = sliceIntoChunks(
            this.xContent,
            this.xContent.length / 2
          );
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    getList() {
      this.getBondOrEntity();
    },
    replaceFun(row) {
      return replaceStr(row, this.input);
    },
    submit() {
      try {
        this.$modal.loading("loading...");
        const parmas = this.xContent[0].concat(this.xContent[1]);
        const ret = [];
        parmas.forEach((e) => {
          if (e.edit) {
            ret.push(e);
          }
        });
        editAllDetail(ret).then((res) => {
          const { data } = res;
          this.$message({
            showClose: true,
            message: "操作成功",
            type: "success",
          });
        });
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
    submitAdd() {
      try {
        this.$modal.loading("loading...");
        this.ruleForm.windTypeOne = this.ruleForm.windTypeOne.value;
        insertBondInfoManual(this.ruleForm).then((res) => {
          this.$message({
            showClose: true,
            message: "操作成功",
            type: "success",
          });
        });
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
    getCode() {
      try {
        this.$modal.loading("loading...");
        getEntityInfo({ creditCode: this.ruleForm.creditCode }).then((res) => {
          const { data } = res;
          this.$set(this.ruleForm, "entityName", data.entityName);
          this.$set(this.ruleForm, "entityCode", data.entityCode);
        });
      } catch (error) {
      } finally {
        this.$modal.closeLoading();
      }
    },
  },
};
</script>

<style scoped lang="scss">
.between {
  justify-content: space-between;
}

.g-t-title {
  font-weight: 600;
}
.select-x {
  margin-top: 10px;
  width: 24%;
}
.g-desc {
  margin-left: 20px;
  margin-bottom: 1%;
  span {
    color: #86bc25;
  }
  a {
    font-size: 14px;
    color: #9b9b9b;
    text-decoration: revert;
    margin-right: 10px;
  }
  .g-select {
    color: rgb(121, 131, 105);
  }
}
.select-body {
  margin-left: 29%;
}
.font-color {
  color: red;
}
.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-top: 1%;
}
.edit-btn {
  margin-top: -3px;
  margin-left: 5px;
}
.flex1 {
  .first {
    width: 200px;
  }
  .content {
    color: #a7a7a7;
    margin-top: 3px;
    width: 300px;
    margin-left: 10px;
  }
}
.color-gary {
  color: #a7a7a7;
}
.black {
  color: black;
}
.t-input {
  width: 50%;
  width: 300px;
  margin-left: 10px;
}
</style>
