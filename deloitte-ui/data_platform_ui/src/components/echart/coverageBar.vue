<template>
  <div style="width: 400px">
    <div class="chart1-title">
      <span>数据来源覆盖度：</span>
      <span style="padding-left: 12px">
        <el-radio-group v-model="coverage" @input="changeRadio">
          <el-radio size="mini" label="1">全部数据</el-radio>
          <el-radio size="mini" label="2">推荐数据</el-radio>
        </el-radio-group>
      </span>
    </div>
    <div
      v-loading="chartLoading"
      id="coverChart"
      style="width: 100%; height: 180px"
    ></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  props: {
    xdata: {
      type: Array,
      default: () => {
        return [];
      },
    },
    ydata: {
      type: Array,
      default: () => {
        return [];
      },
    },
    type: {
      require: true,
    },
  },
  data() {
    return {
      coverage: "1",
      chartLoading: true,
      charts1: null,
      chart1Style: {
        tooltip: {
          trigger: "axis",
          formatter: (val) => {
            return val[0].axisValueLabel + val[0].data + "%";
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "0%",
          top: "6%",
          containLabel: true,
        },
        xAxis: {
          type: "value",
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false, // 不显示坐标轴刻度线
          },
          axisLabel: {
            fontSize: 12,
            color: "#35343A",
            formatter: (val) => {
              return val + "%";
            },
          },
        },
        yAxis: {
          axisTick: {
            show: false, // 不显示坐标轴刻度线
          },
        },
        series: {
          itemStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "#9EBBD5",
                },
                {
                  offset: 1,
                  color: "#5763A7",
                },
              ]),
            },
          },
        },
      },
    };
  },
  mounted() {
    window.addEventListener("resize", () => {
      this.charts1 && this.charts1.resize();
    });
  },
  watch: {
    xdata(val) {
      if (val.length) {
        this.$nextTick(() => {
          this.drawChart1();
        });
      }
    },
  },
  methods: {
    //全部数据 推荐数据
    changeRadio() {
      this.chartLoading = true;
      this.$emit("change", this.coverage);
    },
    //绘制 数据来源覆盖
    drawChart1() {
      let chartDom = document.getElementById("coverChart");
      this.chart1 = this.$echarts.init(chartDom);
      let option = {
        tooltip: this.chart1Style.tooltip,
        grid: this.chart1Style.grid,
        xAxis: this.chart1Style.xAxis,
        yAxis: {
          type: "category",
          data: this.xdata,
          axisTick: this.chart1Style.yAxis.axisTick,
        },
        series: [
          {
            type: "bar",
            itemStyle: this.chart1Style.series.itemStyle,
            barWidth: 15,
            data: this.ydata,
          },
        ],
      };
      option && this.chart1.setOption(option);
      this.chartLoading = false;
    },
  },
};
</script>

<style lang='scss' scoped>
.chart1-title {
  width: 100%;
  text-align: left;
  padding-left: 20px;
  font-size: 12px;
  color: #35343a;
  font-weight: 700;
}
</style>