<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()"
        >返回企业主体首页</el-button
      >
      <h3 class="title">{{ tab }}企业-新增主体</h3>
    </div>
    <el-row>
      <el-col :sm="24" :lg="23" class="form-card">
        <el-card>
          <div class="mt10">今日新{{ tab }}</div>
          <div v-if="tab === '上市'" class="mt10">
            今日新上市主体 3 个，最近七个交易日平均每日新上市主体 2.8
            个，最近一月平均每日新上市主体 1.1 个
          </div>
          <div v-if="tab === '发债'" class="mt10">
            今日新发债主体 3 个，涉及 x 个发债主体。其中 x 个为未收录主体， x
            给为已收录主体新发债
          </div>
          <div class="mt10">
            最近七个交易日平均每日新上市主体 2.8 个，最近一月平均每日新上市主体
            1.1 个
          </div>
          <div class="mt20" id="xchart" style="height: 240px; width: 100%" />
        </el-card>
      </el-col>
      <el-col
        :sm="24"
        :lg="23"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <div class="flex1">
            <h3 class="g-t-title">近期更新记录</h3>
            <el-button type="text" style="margin-left: 15px" @click="editData()"
              >手动编辑</el-button
            >
          </div>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column type="index" sortable label="序号" width="50">
            </el-table-column>
            <el-table-column prop="date" label="时间" sortable>
            </el-table-column>
            <el-table-column prop="name" label="操作人"> </el-table-column>
            <el-table-column prop="province" label="主体名称">
            </el-table-column>
            <el-table-column prop="city" label="主体代码"> </el-table-column>
            <el-table-column prop="address" label="详细信息"> </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >撤销停用</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      title="手动新增企业主体"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="新增日期">
          <span>{{ currentTime }}</span>
        </el-form-item>
        <el-form-item label="新增操作人">
          <span>admin</span>
        </el-form-item>
        <el-form-item label="企业证券代码" prop="region">
          <div class="flex1">
            <el-input
              class="t-input"
              v-model="ruleForm.name"
              placeholder="请输入企业完整证券嗲吗"
            ></el-input>
            <el-button
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.name)"
              >查重</el-button
            >
          </div>
        </el-form-item>
        <el-form-item label="企业证券简称" prop="region">
          <div class="flex1">
            <el-input
              class="t-input"
              v-model="ruleForm.name"
              placeholder="请输入企业证券简称"
            ></el-input>
            <el-button
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.name)"
              >查重</el-button
            >
          </div>
        </el-form-item>
        <el-form-item label="企业全称" prop="region">
          <div class="flex1">
            <el-input
              class="t-input"
              v-model="ruleForm.name"
              placeholder="请输入企业完整名称"
            ></el-input>
            <el-button
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.name)"
              >查重</el-button
            >
          </div>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <div class="flex1">
            <el-input
              class="t-input"
              v-model="ruleForm.name"
              placeholder="请输入企业统一社会信用代码"
            ></el-input>
            <el-button
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.name)"
              >查重</el-button
            >
            <el-radio
              style="margin-left: 5px; margin-top: 9px"
              v-model="noUse2"
              label="1"
              >不适用</el-radio
            >
          </div>
        </el-form-item>
        <el-form-item label="上市日期" prop="region">
          <el-date-picker
            v-model="ruleForm.date1"
            type="date"
            placeholder="yyyy-mm-dd"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="退市日期" prop="region">
          <el-date-picker
            type="date"
            placeholder="yyyy-mm-dd"
            v-model="ruleForm.date1"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="上市板块与交易所" required>
          <el-col :span="11">
            <el-form-item prop="date1">
              <el-date-picker
                type="date"
                placeholder="选择股票上市板块"
                v-model="ruleForm.date1"
                style="width: 100%"
              ></el-date-picker>
              <el-radio v-model="noUse1" label="1">不适用</el-radio>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="11">
            <el-form-item prop="date2">
              <el-time-picker
                placeholder="选择股票交易所"
                v-model="ruleForm.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="是否为金融机构" prop="delivery">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年报列示类型" prop="delivery">
          <el-checkbox-group v-model="ruleForm.type">
            <el-checkbox label="一般企业" name="type"></el-checkbox>
            <el-checkbox label="金融机构" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="金融机构子行业" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择新增类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          class="position-add"
          label="企业曾用名或别称"
          prop="delivery"
        >
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false"
          >确认新增</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "addGovernment",
  data() {
    return {
      noUse2: "",
      noUse1: "",
      list: [
        {
          date: "2016-05-02",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1517 弄",
          zip: 200333,
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1519 弄",
          zip: 200333,
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1516 弄",
          zip: 200333,
        },
      ],
      dialogVisible: false,
      ruleForm: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
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
      currentTime: "",
      tab: this.$route.query.name,
    };
  },
  mounted() {
    this.xEcharts();
    this.getCurrentTime();
  },
  methods: {
    getCurrentTime() {
      //获取当前时间并打印
      let yy = new Date().getFullYear();
      let mm = new Date().getMonth() + 1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf =
        new Date().getMinutes() < 10
          ? "0" + new Date().getMinutes()
          : new Date().getMinutes();
      let ss =
        new Date().getSeconds() < 10
          ? "0" + new Date().getSeconds()
          : new Date().getSeconds();
      // eslint-disable-next-line no-unused-vars
      this.currentTime =
        yy + "-" + mm + "-" + dd + " " + hh + ":" + mf + ":" + ss;
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    handleClick() {
      console.log(1);
    },
    back() {
      this.$router.back();
    },
    editData() {
      this.dialogVisible = true;
      console.log(1);
    },
    xEcharts() {
      const myChart = echarts.init(document.getElementById("xchart"));
      const option = {
        title: {
          text: this.tab === "上市" ? "新上市企业个数" : "日新发债券数量",
          left: "left",
        },
        legend: {
          left: "center",
          bottom: "2%",
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "10%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: [
            "22-08-15",
            "22-08-15",
            "22-08-15",
            "22-08-15",
            "22-08-15",
            "22-08-15",
            "22-08-15",
          ],
        },
        yAxis: {
          type: "value",
          axisLine: {
            show: true, // 不显示坐标轴轴线
          },
          axisTick: {
            show: false, // 不显示坐标轴刻度
          },
          splitLine: {
            show: false,
          },
        },
        series: [
          {
            data: [
              120,
              {
                value: 200,
                itemStyle: {
                  color: "red",
                },
              },
              150,
              80,
              70,
              110,
              130,
            ],
            type: this.tab === "上市" ? "bar" : "line",
          },
        ],
      };
      myChart.setOption(option);
    },
  },
};
</script>

<style scoped lang="scss">
.tips {
  margin-top: 5px;
}
.tips-2 {
  margin-top: 10px;
  color: red;
}
.t-input {
  width: 220px;
}

.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-left: 3%;
  margin-top: 3%;
}
.add-btn {
  position: relative;
  left: 63%;
  top: -27%;
}
.back {
  margin-left: 19px;
}
.title {
  margin-left: 31%;
  font-weight: 600;
}
.box-card {
  ::v-deep .el-card__body {
    display: flex;
    justify-content: space-between;
  }
  .top-left {
    padding: 20px 20px;
  }
  .title {
    font-size: 20px;
  }
  .body {
    width: 330px;
    margin-top: 12%;
    span {
      color: greenyellow;
    }
  }
  .top-right {
    display: flex;
    padding: 20px 20px;
    width: 40%;
  }
  .right-number {
    position: relative;
    left: 167%;
    top: 30%;
    color: greenyellow;
  }
}
.g-desc {
  margin-top: 15px;
  span {
    color: greenyellow;
  }
  a {
    font-size: 14px;
    color: #9b9b9b;
    text-decoration: revert;
    margin-right: 10px;
  }
}
.select-x {
  width: 24%;
  position: relative;
  left: 50%;
}
.g-tab {
  margin-left: 1.5%;
  a {
    font-size: 14px;
    color: black;
    text-decoration: underline;
    margin-right: 10px;
  }
  .g-select {
    color: greenyellow;
  }
}
</style>
