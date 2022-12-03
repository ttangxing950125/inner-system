import * as echarts from "echarts";
let changeName = (e) => {
  var newStr = " ";
  var start, end;
  var name_len = e.name.length; //每个内容名称的长度
  var max_name = 4; //每行最多显示的字数
  var new_row = Math.ceil(name_len / max_name); // 最多能显示几行，向上取整比如2.1就是3行
  if (name_len > max_name) {
    //如果长度大于每行最多显示的字数
    for (var i = 0; i < new_row; i++) {
      //循环次数就是行数
      var old = ""; //每次截取的字符
      start = i * max_name; //截取的起点
      end = start + max_name; //截取的终点
      if (i == new_row - 1) {
        //最后一行就不换行了
        old = e.name.substring(start);
      } else {
        old = e.name.substring(start, end) + "\n";
      }
      newStr += old; //拼接字符串
    }
  } else {
    //如果小于每行最多显示的字数就返回原来的字符串
    newStr = e.name;
  }
  return newStr;
};
//渐变色
let color1 = new echarts.graphic.LinearGradient(
  0,
  1,
  0,
  0,
  [
    { offset: 0, color: "#FBDC88" },
    { offset: 1, color: "#FCB048" },
  ],
  false
);
let color2 = new echarts.graphic.LinearGradient(
  0,
  1,
  0,
  0,
  [
    { offset: 0, color: "#9EBBD5" },
    { offset: 1, color: "#5763A7" },
  ],
  false
);
let color3 = new echarts.graphic.LinearGradient(
  0,
  1,
  0,
  0,
  [
    { offset: 0, color: "#CADC88" },
    { offset: 1, color: "#81BC40" },
  ],
  false
);
let color4 = new echarts.graphic.LinearGradient(
  0,
  1,
  0,
  0,
  [
    { offset: 0, color: "#94C2FD" },
    { offset: 1, color: "#5786E5" },
  ],
  false
);
let style = [color1, color2];
let style2 = [color1, color2, color3, color4];
let chartStyle = {
  data() {
    return {
      //business.vue charts样式配置 chart xx 对应 页面 chart 1 2 3
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

      // 第二个
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

      //第三个
      chart3Style: {
        tooltip: {
          trigger: "item",
          formatter: (val) => {
            return val.name + val.value + "%";
          },
        },
        color: style,
        radius: "76%",
        label: {
          normal: {
            position: "inner",
            formatter: (e) => {
              let a = `${changeName(e)}
  ${e.percent + "%"}`;
              return a;
            },
            textStyle: {
              lineHeight: 16,
              color: "#fff",
              fontWeight: "400",
              fontSize: 12,
              align: "center",
              baseline: "middle",
            },
          },
        },
      },

      //第四个
      chart4Style: {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        color: style,
        color2: style2,
        labelLine: {
          normal: {
            show: true,
            length: 10,
            length2: 50,
          },
        },
      },
    };
  },
  methods: {},
};

export default chartStyle;
