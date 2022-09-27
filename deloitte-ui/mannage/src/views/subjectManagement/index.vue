<template>
  <div class="app-container home">
    <el-row>
      <el-col :sm="24" :lg="24" style="padding-left: 20px">
        <el-card class="box-card">
          <div class="top-left">
            <div class="title">整体情况</div>
            <div class="body">
              截止今日起，平台已收录主体
              <span>{{ goveSum + entitySum }}</span> 个，其中，政府主体
              <span>{{ goveSum }}</span> 个，企业主体
              <span> {{ entitySum }} </span> 个
            </div>
          </div>
          <div class="top-right">
            <div
              class="box1"
              :style="{
                background: 'green',
                width: govePercent + '%',
                'text-align': 'center',
              }"
            >
              <div style="margin-top: 37%">政府 {{ govePercent }}</div>
            </div>
            <div
              class="box2"
              style="background: greenyellow; width: 70%; text-align: center"
            >
              <div style="margin-top: 16.5%">企业 {{ entityPercent }}</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :sm="24" :lg="12" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="box-card1 box-card">
            <div class="flex1 top-left">
              <div class="title mr20">政府主体</div>
              <el-button type="text ">导出全部</el-button>
              <div class="right-number">30303</div>
            </div>
          </div>
          <div class="title1">
            <div class="flex1">
              <div>
                <div v-for="o in 4" :key="o" class="text item">
                  {{ "政府 " + o }}
                </div>
              </div>
              <div style="width: 77%">
                <div
                  v-for="o in 4"
                  :key="o"
                  class="text item number-font"
                  :style="{
                    background: 'green',
                    width: 30 + o + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ 3 + o + "%" }}
                </div>
              </div>
              <div>
                <div v-for="o in 4" :key="o" class="text item number-font">
                  <div>1020</div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :sm="24" :lg="12" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="box-card1 box-card">
            <div class="flex1 top-left">
              <div class="title mr20">企业主体</div>
              <el-button type="text ">导出全部</el-button>
              <div class="right-number">30303</div>
            </div>
          </div>
          <div class="title1">
            <div class="flex1">
              <div>
                <div v-for="o in 4" :key="o" class="text item">
                  {{ "政府 " + o }}
                </div>
              </div>
              <div style="width: 77%">
                <div
                  v-for="o in 4"
                  :key="o"
                  class="text item number-font"
                  :style="{
                    background: 'green',
                    width: 30 + o + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ 3 + o + "%" }}
                </div>
              </div>
              <div>
                <div v-for="o in 4" :key="o" class="text item number-font">
                  <div>1020</div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="box-card1 box-card">
            <div class="flex1 top-left">
              <div class="title mr20">覆盖情况快速查询</div>
              <el-button type="text ">批量查询</el-button>
            </div>
          </div>
          <div class="select-body">
            <div class="flex1">
              <el-select
                class="mr5 select-only"
                v-model="value"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <el-input
                class="mr10"
                v-model="input"
                placeholder="请输入内容"
              ></el-input>
              <el-select
                class="mr10 selects"
                v-model="value1"
                multiple
                placeholder="请选择"
              >
                <el-option
                  v-for="item in options2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <el-button class="mr10" type="primary" @click="select"
                >查询</el-button
              >
            </div>
          </div>
          <div class="select-table">
            <div class="table-title flex1" v-if="list.length === 0">
              <div class="table-desc">查询结果</div>
              <el-divider
                direction="vertical"
                style="height：60px"
              ></el-divider>
              <div class="table-desc">
                {{
                  loading ? "查询中..." : list.length === 0 ? "查无此结果" : ""
                }}
              </div>
            </div>
            <div>
              <div class="table-desc">
                <div>查询结果</div>
                <div class="table-result">共查询到<span> 36 </span>个结果:</div>
              </div>
              <el-table class="table-content" :data="list" style="width: 98%%">
                <el-table-column fixed type="index" width="50" label="序号">
                </el-table-column>
                <el-table-column fixed prop="date" label="德勤主体代码">
                </el-table-column>
                <el-table-column fixed prop="name" label="企业名称" width="300">
                </el-table-column>
                <el-table-column prop="province" label="IB" width="120">
                </el-table-column>
                <el-table-column prop="city" label="城投" width="120">
                </el-table-column>
                <el-table-column prop="address" label="地产"> </el-table-column>
                <el-table-column prop="zip" label="财报"> </el-table-column>
                <el-table-column prop="zip" label="股票"> </el-table-column>
                <el-table-column prop="zip" label="产业链"> </el-table-column>
                <el-table-column prop="zip" label="ES"> </el-table-column>
              </el-table>
              <el-pagination
                class="page"
                background
                layout="prev, pager, next"
                :total="1000"
              >
              </el-pagination>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { govList, entityInfoList } from "@/api/subject";
export default {
  name: "Index",
  data() {
    return {
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
      input: "",
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      value: "企业主体",
      options2: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      value1: [],
      loading: false,
      goveSum: 0,
      entitySum: 0,
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      try {
        govList({}).then((res) => {
          const { data } = res;
          this.goveSum = data.govSum;
        });
        entityInfoList({}).then((res) => {
          const { data } = res;
          this.entitySum = data.entitySum;
        });
      } catch (error) {}
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    select() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 2000);
    },
  },
  computed: {
    govePercent() {
      const res = (this.goveSum / (this.goveSum + this.entitySum)).toFixed(4);
      return res * 100;
    },
    entityPercent() {
      const res = (this.entitySum / (this.goveSum + this.entitySum)).toFixed(4);
      return res * 100;
    },
  },
};
</script>

<style scoped lang="scss">
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
.box-card1 {
  display: flex;
  justify-content: space-between;
}
.title1 {
  padding-left: 30px;
  .item {
    margin-top: 13px;
    margin-right: 15px;
  }
  .number-font {
    font-size: 18px;
  }
}
.select-body {
  padding-left: 15px;
}
.selects {
  width: 60%;
}
.select-table {
  margin-top: 25px;
  min-height: 80px;
  background: gainsboro;
  padding: 10px;
  width: 98%;
  margin-left: 14px;
}
.table-title {
}
.select-only {
  width: 30%;
}
.table-desc {
  margin-top: 1.5%;
  width: 13%;
  padding-left: 12px;
}
.table-result {
  margin-top: 10px;
  width: 150px;
  span {
    color: greenyellow;
  }
}
.page {
  margin-left: 31%;
  margin-top: 20px;
}
.table-content {
  margin-top: 10px;
}
</style>
