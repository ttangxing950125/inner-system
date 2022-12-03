<template>
  <!-- 基础层 新增 修改弹窗 -->
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="50%"
    style="margin-top: 18vh"
    center
    :before-close="close"
    @open="open"
  >
    <div style="padding: 0 120px">
      <el-form
        label-position="left"
        label-width="130px"
        :model="form"
        size="small"
      >
        <el-form-item label="字段名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="字段代码">
          <el-input v-model="form.code"></el-input>
        </el-form-item>
        <el-form-item label="wind优先级推荐">
          <el-input v-model="form.windSeq"></el-input>
        </el-form-item>
        <el-form-item label="同花顺优先级推荐">
          <el-input v-model="form.flushSeq"></el-input>
        </el-form-item>
        <el-form-item label="自动化优先级">
          <el-input v-model="form.ocrSeq"></el-input>
        </el-form-item>
        <el-form-item label="人工补录优先级推荐">
          <el-input v-model="form.artificialRecordingSeq"></el-input>
        </el-form-item>
        <el-form-item label="变动率上限">
          <el-input v-model="form.changeRateUpper"></el-input>
        </el-form-item>
        <el-form-item label="值域">
          <el-input v-model="form.thresholdValue"></el-input>
        </el-form-item>
        <el-form-item label="精度">
          <el-input v-model="form.accuracy"></el-input>
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
import { addOrUpdateBase } from "@/api/paramsSeting";

export default {
  props: {
    title: {
      type: String,
      default: "提示",
    },
    info: {
      type: Object,
    },
    visible: {
      type: Boolean,
      default: () => {
        return true;
      },
    },
  },
  data() {
    return {
      form: {
        name: "",
        code: "",
        windSeq: "",
        flushSeq: "",
        ocrSeq: "",
        artificialRecordingSeq: "",
        changeRateUpper: "",
        region: "",
        accuracy: "",
      },
    };
  },

  methods: {
    open() {
      this.title === "修改基础层字段" && (this.form = this.info);
    },
    close() {
      this.form = {
        name: "",
        code: "",
        flushSeq: "",
        ocrSeq: "",
        artificialRecordingSeq: "",
        changeRateUpper: "",
        thresholdValue: "",
        accuracy: "",
      };
      this.$emit("close");
    },
    submit() {
      try {
        this.$modal.loading("Loading...");
        addOrUpdateBase(this.form).then((res) => {
          this.$message({
            message: "操作成功",
            type: "success",
          });
          this.close();
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