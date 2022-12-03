<template>
  <!-- 中间层 新增 修改弹窗 -->
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="50%"
    style="margin-top: 12vh"
    center
    :before-close="close"
    @open="open"
  >
    <div class="content-box">
      <el-form
        label-position="left"
        label-width="130px"
        :model="form"
        size="small"
      >
        <el-form-item label="字段名称">
          <el-input v-model="form.name" clearable maxlength="32"></el-input>
        </el-form-item>
        <el-form-item label="字段代码">
          <el-input v-model="form.code" clearable maxlength="32"></el-input>
        </el-form-item>
        <el-form-item label="变动率上限">
          <el-input
            v-model="form.changeRateUpper"
            clearable
            maxlength="32"
          ></el-input>
        </el-form-item>
        <el-form-item label="值域">
          <el-input
            v-model="form.thresholdValue"
            clearable
            maxlength="32"
          ></el-input>
        </el-form-item>
        <el-form-item label="精度">
          <el-input v-model="form.accuracy" clearable maxlength="32"></el-input>
        </el-form-item>
        <el-form-item label="已配置公式">
          <el-input v-model="form.formulaDescribe" disabled></el-input>
        </el-form-item>
        <el-form-item
          label="异常值处理"
          v-show="form.abnormalValueHandleSelects.length"
        >
          <div
            class="flex-row-bw"
            style="margin-bottom: 20px"
            v-for="(item, index) in form.abnormalValueHandleList"
            :key="index"
          >
            <el-select
              style="width: 22%"
              v-model="item.name"
              placeholder="请选择"
              clearable
              @change="selectChange"
            >
              <!-- 提供的选项 -->
              <el-option
                v-for="(item2, index) in form.abnormalValueHandleSelects"
                :label="item2.name"
                :value="item2.name"
                :key="index + 'ce'"
              ></el-option>
            </el-select>
            <!-- 符号 -->
            <el-input
              v-model="item.symbol"
              style="width: 22%"
              clearable
            ></el-input>
            <!--  -->
            <el-input
              v-model="item.value"
              style="width: 22%"
              clearable
            ></el-input>
            <el-button style="width: 18%" size="mini" @click="handleRow(index)">
              {{ index ? "删除" : "添加" }}</el-button
            >
          </div>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button class="btn" size="small" @click="close">取 消</el-button>
      <el-button class="btn" size="small" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateMiddle } from "@/api/paramsSeting";

export default {
  props: {
    title: {
      type: String,
      default: "提示",
    },
    visible: {
      type: Boolean,
      default: () => {
        return true;
      },
    },
    info: {
      type: Object,
    },
  },
  data() {
    return {
      form: {
        name: "",
        code: "",
        changeRateUpper: "",
        thresholdValue: "",
        accuracy: "",
        formulaDescribe: "",
        abnormalValueHandleList: [
          {
            symbol: "",
            value: "",
            name: "",
            code: "",
          },
        ],
      },
    };
  },
  methods: {
    open() {
      this.form = this.deepClone(this.info);
      if (
        this.form.abnormalValueHandleList != null &&
        !this.form.abnormalValueHandleList.length
      ) {
        this.form.abnormalValueHandleList.push({
          symbol: "",
          value: "",
          name: "",
          code: "",
        });
      }
    },
    deepClone(source) {
      if (!source && typeof source !== "object") {
        throw new Error("error arguments", "deepClone");
      }
      const targetObj = source.constructor === Array ? [] : {};
      Object.keys(source).forEach((keys) => {
        if (source[keys] && typeof source[keys] === "object") {
          targetObj[keys] = this.deepClone(source[keys]);
        } else {
          targetObj[keys] = source[keys];
        }
      });
      return targetObj;
    },

    //添加 删除行
    handleRow(index) {
      //index 0 添加    其他 删除
      if (index) {
        this.form.abnormalValueHandleList.splice(index, 1);
      } else {
        this.form.abnormalValueHandleList.push({
          symbol: "",
          value: "",
          name: "",
          code: "",
        });
      }
    },
    selectChange() {
      this.form.abnormalValueHandleSelects.forEach((item1) => {
        this.form.abnormalValueHandleList.forEach((item2) => {
          if (item1.name == item2.name) {
            item2.code = item1.code;
          }
        });
      });
    },
    close() {
      this.$emit("close");
    },
    submit() {
      try {
        console.log(this.form, "this.formthis.formthis.form");
        this.$modal.loading("Loading...");
        addOrUpdateMiddle(this.form).then((res) => {
          if (res.code == 200) {
            this.$emit("close");
            this.$message({
              message: "操作成功",
              type: "success",
            });
          }
        });
      } catch (error) {
        this.$message.error(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
  },
};
</script>

<style lang='scss' scoped>
@import "@/assets/styles/dialog.scss";
::v-deep .el-form-item__label {
  font-size: 12px;
  color: #35343a;
  font-weight: 400;
}
.content-box {
  padding: 0 120px;
  max-height: 60vh;
  overflow-y: scroll;
}
.dialog-footer {
  .btn {
    width: 120px;
    &:last-child {
      background-image: linear-gradient(180deg, #6a788b 0%, #444e5a 100%);
      color: #fff;
    }
  }
}
::v-deep .el-button + .el-button {
  margin-left: 40px;
}
</style>