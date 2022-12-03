// 缺失率统计
<template>
  <div id="defactChart" style="width: 400px" v-loading="loading"></div>
</template>
<script>
import * as echarts from "echarts";
export default {
  props: {
    data1: {
      type: Array,
      default: () => {
        return [];
      },
    },
    data2: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      loading: true,
      charts2: null,
      chart2Style: {
        title: {
          text: "缺失率统计：",
          left: "24%",
          textStyle: {
            fontSize: 12,
            color: "#35343A",
            fontWeight: 700,
          },
        },
        tooltip: {
          trigger: "axis",
          formatter: (val) => {
            let res = "";
            val.forEach((item) => {
              res += item.marker + item.seriesName + item.value + "%" + "<br/>";
            });
            return res;
          },
        },
        legend: {
          itemHeight: 12,
          itemWidth: 12,
          itemGap: 30,
          left: "46%",
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "0%",
          top: "14%",
          containLabel: true,
        },
        xAxis: {
          type: "value",
          min: 0,
          max: 100,
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
          type: "category",
          data: [
            "缺失率0%",
            "缺0%-30%",
            "缺30%-60%",
            "缺60%-90%",
            "缺90%-100%",
            "缺100%",
          ],
          axisTick: {
            show: false, // 不显示坐标轴刻度线
          },
        },
        series: {
          itemStyle1: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "#FBDC88",
                },
                {
                  offset: 1,
                  color: "#FCB048",
                },
              ]),
            },
          },
          itemStyle2: {
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
  watch: {
    data1(val) {
      if (val.length) {
        this.$nextTick(() => {
          this.drawChart2();
        });
      }
    },
  },
  mounted() {
    window.addEventListener("resize", () => {
      this.charts2 && this.charts2.resize();
    });
  },
  methods: {
    //却失统计率
    drawChart2() {
      this.loading = true;
      let chartDom = document.getElementById("defactChart");
      this.chart2 = this.$echarts.init(chartDom);
      let option = {
        title: this.chart2Style.title,
        tooltip: this.chart2Style.tooltip,
        legend: this.chart2Style.legend,
        grid: this.chart2Style.grid,
        xAxis: this.chart2Style.xAxis,
        yAxis: this.chart2Style.yAxis,
        series: [
          {
            name: "缺失",
            type: "bar",
            stack: "total",
            barWidth: 12,
            data: this.data1,
            itemStyle: this.chart2Style.series.itemStyle1,
          },
          {
            name: "已填充",
            type: "bar",
            stack: "total",
            barWidth: 12,
            itemStyle: this.chart2Style.series.itemStyle2,
            data: this.data2,
          },
        ],
      };
      option && this.chart2.setOption(option);
      this.loading = false;
    },
  },
};
</script>

<style>
</style>