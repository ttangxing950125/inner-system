import * as echarts from "echarts";
let chartStyle = {
  data() {
    return {
      //第一个
      chart1Style: {
        tooltip: {
          trigger: "axis",
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
      //第二个
      chart2Style: {
        tooltip: {
          trigger: "axis",
        },
        legend: {
          itemHeight: 12,
          itemWidth: 12,
          itemGap: 30,
        },
        grid: {
          left: "3%",
          right: "4%",
          top: "17%",
          bottom: "0%",
          containLabel: true,
        },

        series: {
          itemStyle1: {
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

          itemStyle2: {
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
        },
      },
    };
  },
  // computed: {
  //   tableHeader() {
  //     if (this.hierarchyValue == 1) {
  //       return [
  //         {
  //           label: "主体",
  //           align: "center",
  //           props: "entityCode",
  //         },
  //         {
  //           label: "字段代码",
  //           align: "left",
  //           props: "code",
  //          
  //         },

  //         {
  //           label: "字段中文名称",
  //           align: "left",
  //           props: "name",
  //          
  //         },
  //         {
  //           label: "数据层级",
  //           align: "left",
  //           props: "hierarchy",
  //           width: "100px",
  //         },
  //         {
  //           label: "字段代码",
  //           align: "left",
  //           props: "code",
  //         },
  //       ];
  //     }
  //   },
  // },
  methods: {
    //表头背景色
    headerStyles({ columnIndex }) {
      if (this.hierarchyValue == 1) {
        // if (columnIndex == 8) {
        //   return {
        //     fontWeight: "700",
        //     color: "#35343A",
        //     background: "#F0F8ED",
        //     border: "none",
        //   };
        // }
        if (columnIndex > 6) {
          return {
            fontWeight: "700",
            color: "#35343A",
            background: "#E6F4F8",
            border: "none",
          };
        }
      } else {
        if (columnIndex > 7) {
          return {
            fontWeight: "700",
            color: "#35343A",
            background: "#E6F4F8",
            border: "none",
          };
        }
      }

      return {
        fontWeight: "700",
        color: "#35343A",
        background: "rgba(88,151,236,0.04)",
        border: "none",
      };
    },
  },
};

export default chartStyle;
