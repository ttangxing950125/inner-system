<template>
  <div class="pd20">
    <div class="content">
      <div class="top mt20 flex">
        <div @click="delCondition">
          <span class="seting ml20 mr10"
            ><i class="el-icon-error"></i> 重制选择范围</span
          >
        </div>
        <div @click="delOne">
          <span class="seting ml20 mr10"
            ><i class="el-icon-delete"></i> 删除条件</span
          >
        </div>
        <div @click="calculationFun">
          <span class="seting ml20 mr10"
            ><i class="el-icon-set-up"></i> 计算</span
          >
        </div>
      </div>
      <div class="bottm flex pd20">
        <div class="left">
          <div class="pd20">
            <div class="title">公式配置</div>
            <div v-if="calculation.length === 0">
              <div class="left-content">
                请选择页面左方待选数据和下方运算符号进行公式配置！
              </div>
              <div class="left-content1 flex">
                如：
                <div class="box mr20">待选数据1</div>
                <div class="box mr20">运算符号</div>
                <div class="box mr20">待选数据2</div>
                <div>...</div>
              </div>
            </div>
            <div class="flex mt20" v-else>
              <div
                v-for="(item, index) in calculation"
                :key="index"
                class="Symbol-x mr10"
              >
                <nobr>{{ item.name }}</nobr>
              </div>
            </div>
            <div class="calculation">运算符号：</div>
            <div class="flex mt10">
              <div
                v-for="(item, index) in symbol"
                :key="index"
                class="Symbol mr10"
                @click="getData(item)"
              >
                {{ item.name }}
              </div>
            </div>
          </div>
        </div>
        <div class="right">
          <!-- {{ form }} -->
          <el-form
            ref="form"
            class="pl20"
            :model="form"
            label-width="135px"
            label-position="left"
          >
            <el-form-item label="中间层evidence名称：">
              <el-input class="query-input" v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="evidence code：">
              <el-input class="query-input" v-model="form.code"></el-input>
            </el-form-item>
            <el-divider class="divider mt20"></el-divider>
            <el-form-item
              label-width="75px"
              class="mt20 desc1"
              label="文字公式："
            >
              <span class="form-font">自动显示</span>
            </el-form-item>
            <div class="flex">
              <el-form-item label-width="75px" label="单位：">
                <el-select
                  class="query-select"
                  v-model="form.unit"
                  placeholder="请选择单位"
                >
                  <el-option
                    v-for="(item, index) in unitArr"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item class="pl20" label-width="50px" label="精度：">
                <el-select
                  class="query-select"
                  v-model="form.accuracy"
                  placeholder="请选择精度"
                >
                  <el-option
                    v-for="(item, index) in accuracyArr"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="flex">
              <el-form-item label-width="75px" label="使用场景：">
                <el-select
                  class="query-select"
                  v-model="form.businessScene"
                  placeholder="请选择使用场景"
                >
                  <el-option
                    v-for="(item, index) in overviewOption"
                    :label="item.label"
                    :value="item.value"
                    :key="index"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item class="pl20" label-width="50px" label="年份：">
                <el-select
                  class="query-select"
                  v-model="form.year"
                  placeholder="请选择年份"
                >
                  <el-option
                    v-for="(item, index) in yearArr"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </div>
            <el-form-item label-width="75px" class="desc2" label="异常处理：">
              <span
                class="form-font"
                style="display: block; width: 200%; text-align: left"
                >{{ form.formulaDescribe }}</span
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog
      class="dialog"
      title="提示"
      center
      :visible.sync="notice"
      width="320px"
      top="40vh"
    >
      <span class="dialog-text"
        >配置保存成功，待数据运算完成，新的数据将自动更新在数据资产池。</span
      >
    </el-dialog>
  </div>
</template>
    
  <script>
import { getOverview } from "@/api/statisticalAnalysis/index.js";
import { getYears3 } from "@/api/statisticalAnalysis/index.js";
export default {
  props: {
    type: {
      type: Number || String,
    },
  },
  data() {
    return {
      form: {
        name: "人均GDP",
        code: "per_capita_GDP",
        unit: "1",
      },
      symbol: [
        {
          label: "➕",
          name: "+",
          code: "+",
        },
        {
          label: "➖",
          name: "-",
          code: "-",
        },
        {
          label: "✖",
          name: "*",
          code: "*",
        },
        {
          label: "➗",
          name: "/",
          code: "/",
        },
        {
          label: "(",
          name: "(",
          code: "(",
        },
        {
          label: ")",
          name: ")",
          code: ")",
        },
        {
          label: "log",
          name: "log",
          code: "log",
        },
        {
          label: "lag",
          name: "lag",
          code: "lag",
        },
        {
          label: "sag",
          name: "asg",
          code: "sag",
        },
        {
          label: "ssag",
          name: "ssag",
          code: "ssag",
        },
      ],
      calculation: [],
      overviewOption: [], //使用场景
      notice: false,
      unitArr: [
        {
          name: "元",
          value: "1",
        },
        {
          name: "万元",
          value: "2",
        },
        {
          name: "亿元",
          value: "3",
        },
        {
          name: "数值",
          value: "4",
        },
        {
          name: "文本",
          value: "5",
        },
        {
          name: "百分比",
          value: "7",
        },
      ],
      accuracyArr: [
        {
          name: "一位小数点",
          value: "1",
        },
        {
          name: "两位小数点",
          value: "2",
        },
        {
          name: "三位小数点",
          value: "3",
        },
        {
          name: "四位小数点",
          value: "4",
        },
        {
          name: "五位小数点",
          value: "5",
        },
      ],
      yearArr: [],
    };
  },
  created() {
    let that = this;
    this.$eventBus.$off("getData"); // 防止内存泄漏
    this.$eventBus.$on("getData", function (data) {
      that.getData(data);
    });
  },
  mounted() {
    this.getOverview();
    this.getYears3();
  },
  methods: {
    //获取年份
    getYears3() {
      getYears3({ hierarchy: this.type }).then((res) => {
        if (res.code == 200) {
          this.yearArr = [];
          res.data.forEach((item) => {
            this.yearArr.push({
              label: item,
              value: item,
            });
          });
        }
      });
    },
    //获取使用场景 type==2 中间层 3指标层
    getOverview() {
      getOverview({ hierarchy: this.type }).then((res) => {
        let { code, data } = res;
        if (code == 200 && data != null) {
          this.overviewOption = [];
          data.forEach((item) => {
            this.overviewOption.push({
              label: item.name,
              value: item.code,
            });
          });
        }
      });
    },
    // 获取点击数据与符号 数据从pageMenu这个组件获取
    getData(row) {
      this.calculation.push(row);
    },
    // 删除所有条件
    delCondition() {
      this.calculation = [];
    },
    // 提交计算
    calculationFun() {
      if (
        !this.form.businessScene ||
        !this.form.year ||
        !this.form.accuracy ||
        !this.form.unit
      ) {
        this.$message({
          message: "请选择右侧单位，精度，年份，使用场景等条件",
          type: "warning",
        });
        return;
      }
      this.notice = true;
    },
    delOne() {
      this.calculation.pop();
    },
    getSeting(row) {
      this.form = row;
      const formulaArr = row.formula.split(" ");
      const formulaDescribeArr = row.formulaDescribe.split(" ");
      formulaDescribeArr.forEach((e, index) => {
        this.calculation.push({
          name: e,
          value: formulaArr[index],
        });
      });
      this.calculation.pop();
      // this.form.unit = this.unitArr[row.unit]
    },
  },
};
</script>
  
  <style scoped lang='scss'>
.content {
  height: 360px;
  background-image: linear-gradient(180deg, #707c94 0%, #556171 100%);
  text-align: center;
  overflow: hidden;
  .top {
    width: 96.5%;
    background: #444e5a;
    margin: 0 auto;
    height: 26px;
    align-items: center;
    .seting {
      font-size: 12px !important;
      color: #ffffff;
      cursor: pointer;
      font-weight: 400;
    }
    .seting:hover {
      color: #ffb400;
    }
  }
  .left {
    width: 55%;
    height: 265px;
    background: #444e5a;
    border: 1px solid #444e5a;
    border-radius: 15px;
    .title {
      width: 64px;
      height: 21px;
      font-family: MicrosoftYaHei;
      font-size: 16px;
      color: #f2fbff;
      letter-spacing: 0;
      font-weight: 400;
    }
    .left-content {
      margin-top: 30px !important;
      margin: 0 auto;
      width: 300px;
      height: 16px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      color: #959ca8;
      letter-spacing: 0;
      font-weight: 400;
    }
    .left-content1 {
      height: 16px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      color: #959ca8;
      letter-spacing: 0;
      font-weight: 400;
      line-height: 6.5;
      margin-left: 13%;
    }
    .box {
      height: 36px;
      width: 96px;
      border: 1px dashed #d7dae2;
      border-radius: 5px;
      margin-top: 20px;
      line-height: 3;
    }
    .calculation {
      margin-top: 12%;
      width: 60px;
      height: 16px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      color: #dae0ee;
      letter-spacing: 0;
      font-weight: 400;
    }
    .Symbol {
      width: 26px;
      height: 26px;
      background-image: linear-gradient(168deg, #ffffff 0%, #b2c1d2 100%);
      border-radius: 2px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      color: #6d798f;
      letter-spacing: 0;
      font-weight: 400;
      line-height: 23px;
      cursor: pointer;
    }
    .Symbol-x {
      height: 26px;
      background-image: linear-gradient(168deg, #ffffff 0%, #b2c1d2 100%);
      border-radius: 2px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      color: #6d798f;
      letter-spacing: 0;
      font-weight: 400;
      line-height: 23px;
      padding: 0 6px;
      cursor: pointer;
    }
  }
  .right {
    width: 45%;
    ::v-deep .el-form-item__label {
      color: #dae0ee;
      font-size: 12px;
      font-weight: 300;
    }
    ::v-deep .el-form-item {
      margin-bottom: 3px;
    }
    .divider {
      display: block;
      height: 1px;
      width: 100%;
      margin: 24px 0;
      margin: 0 auto;
      /* padding-left: 19px; */
    }
    .form-font {
      color: #e5e5e5;
      font-size: 12px;
    }
    .desc1 {
      width: 129px;
    }
    .desc2 {
      width: 245px;
    }
    .query-input {
      height: 24px;
      width: 100%;
      ::v-deep .el-input__inner {
        height: 24px;
        &::placeholder {
          color: #ffffff;
          font-size: 12px;
        }
        color: #ffb400;
        font-size: 12px;
        font-weight: 700;
        background-color: #4d5763 !important;
        border: none;
        border-bottom: none;
      }
    }
    .query-select {
      height: 24px;
      width: 175px;
      ::v-deep .el-input__inner {
        height: 24px;
        &::placeholder {
          color: #959ca8;
          font-size: 12px;
        }
        color: #ffffff;
        font-size: 12px;
        background-color: #4d5763 !important;
        border: none;
        border-bottom: none;
      }
    }
  }
}
</style>