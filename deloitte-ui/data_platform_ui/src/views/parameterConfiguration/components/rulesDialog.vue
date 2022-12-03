<<template>
  <!-- 中间层 新增 修改弹窗 -->
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="46%"
    style="margin-top: 24vh"
    center
     :before-close="close"
     @open="open"
  >
    <div class="content-box">
      <el-form
        label-position="left"
        label-width="70px"
        :model="form"
        size="small"
      >
        <el-form-item label="校验规则">
       <div class="flex-row-bw">
        <el-input style='width:90%' v-model="form.checkFormula" clearable  placeholder="请输入" maxlength="255" ></el-input>
         <el-button class="btn" size="small" @click="handleCheck" :disabled="!form.checkFormula" :loading='btnloading'>校 验</el-button>
       </div>
       <span class='tips' >请输入校验规则，例如：( BS_NCA_TotalAssets + lag ( BS_NCA_TotalAssets ) ) / 2</span><br/>
        

        <!-- 校验成功 -->
         <span class='sucess' v-show="res"><i class="el-icon-success"></i><span class="ml10">受限资金≥受限资产总额≥受限资金≥受限资产总额</span></span>
        <!-- 校验失败 -->
          <span class='error' v-show='res === false'><i class="el-icon-error"></i><span class="ml10">校验失败，请检查检验规则是否输入正确</span></span>
        </el-form-item>
      
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button class="btn" size="small" @click="close">取 消</el-button>
      <el-button class="btn" size="small" :disabled='res!=true'  @click="submit">提 交</el-button>
      <!-- :disabled=' res!=true ' -->
    </span>
  </el-dialog>
</template>

<script>
import { updateOrAdd, checkRules } from "@/api/paramsSeting";
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
    data: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      res: "", //校验成功/失败
      btnloading: false, //
      form: {
        checkFormula: "",
      },
    };
  },
  methods: {
    open() {
      this.res = "";
      this.data.id && (this.form = Object.assign({}, this.data));
    },
    //较验
    handleCheck() {
      try {
        this.$modal.loading("Loading...");
        checkRules(this.form).then((res) => {
          this.res = res.code == 200 ? true : false;
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    close() {
      this.form.checkFormula = "";
      this.$emit("close");
    },
    submit() {
      try {
        this.$modal.loading("Loading...");
        updateOrAdd(this.form).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: "操作成功",
              type: "success",
            });
            this.close();
          }
        });
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
  padding: 0 30px;
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
.tips {
  font-size: 12px;
  color: #6d798f;
  font-weight: 400;
}
.sucess {
  font-size: 12px;
  color: #118e13;
  font-weight: 400;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.error {
  font-size: 12px;
  color: #d1740a;
  font-weight: 400;
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>