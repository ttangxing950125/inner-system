<template>
  <div class="app-container home">
    <h3 class="g-title">今日运维任务</h3>
    <div class="todo-desc">
      <div>HELLO!</div>
      <div>今天是 {{ currentTime }}, {{ week }}。</div>
      <div>
        今日完成任务合计 {{ "5" }} 条, 待完成{{ "3" }}条，已完成{{
          "2"
        }}条，请尽快完成！
      </div>
      <span type="text">切换日期：</span>
      <el-date-picker v-model="monthDate" type="month" placeholder="选择月">
      </el-date-picker>
    </div>
    <div class="table-box">
      <table border="1" id="table"></table>
    </div>
    <h3 class="g-title">每日运维任务</h3>
    <div class="list-box">
      <el-table
        class="table-content"
        :data="list"
        border
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" sortable label="序号"> </el-table-column>
        <el-table-column prop="date" label="任务说明"> </el-table-column>
        <el-table-column prop="name" label="任务状态">
          <template slot-scope="scope">
            <span style="color: red">{{ "待完成( " + index + " )" }}</span>
            <span style="color: #52ff00bd">{{
              "已完成( " + index + " )"
            }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >开始工作</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "government",
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
      loading: false,
      currentTime: "",
      week: "",
      month: "",
      monthDate: "",
      currentDay: "",
    };
  },
  mounted() {
    this.getCurrentTime();
    this.getDaysInfo();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
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
      this.currentTime = yy + "年" + mm + "月" + dd + "日";
      this.monthDate = yy + "-" + mm;
      this.currentDay = dd;
      let myDate = new Date();
      let wk = myDate.getDay();
      let weeks = [
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六",
      ];

      this.week = weeks[wk];
      this.month = yy + "年" + mm + "月";
    },
    getDaysInfo() {
      var _this = this;
      this.sureDate(_this);
    },
    is_leap(year) {
      //判断是不是闰年
      return year % 100 == 0
        ? year % 400 == 0
          ? 1
          : 0
        : year % 4 == 0
        ? 1
        : 0;
    },
    //下面的是画表格的方法，这个方法会根据数据画出我们需要的表格
    drawTable(jsonHtml) {
      var _this = this;
      var str = `
  <tr class="xiqi">
   <td class="isRed">日</td>
   <td>一</td>
   <td>二</td>
   <td>三</td>
   <td>四</td>
   <td>五</td>
   <td class="isRed">六</td>
  </tr>`;
      let idx = "",
        date_str = "",
        isRed = "",
        hasMsg = "";
      for (var i = 0; i < _this.tr_str; i++) {
        str += "<tr>";
        for (var k = 0; k < 7; k++) {
          idx = i * 7 + k;
          isRed = k === 0 || k === 6 ? "isRed" : "";
          date_str = idx - _this.firstnow + 1;
          date_str <= 0 || date_str > this.m_days[this.mnow]
            ? (date_str = " ")
            : (date_str = idx - _this.firstnow + 1);
          date_str == _this.dnow
            ? (hasMsg =
                '<td class="thisDay" date="' +
                date_str +
                '"><div class="' +
                isRed +
                'content-day">' +
                "<div class='shadow'>" +
                date_str +
                "</div>" +
                "</div></td>")
            : (hasMsg =
                '<td date="' +
                date_str +
                '"><div class="' +
                isRed +
                'content-day">' +
                "<div class=''>" +
                date_str +
                "</div>" +
                "</div></td>");

          for (var l = 0, len = jsonHtml.length; l < len; l++) {
            if (date_str == jsonHtml[l].date) {
              date_str == _this.dnow
                ? (hasMsg =
                    '<td class="thisDay" date="' +
                    date_str +
                    '"><div class="' +
                    isRed +
                    'content-day">' +
                    "<div class='shadow-green'>" +
                    date_str +
                    "</div>" +
                    "</div>" +
                    "</td>")
                : (hasMsg =
                    '<td date="' +
                    date_str +
                    '"><div class="' +
                    isRed +
                    'content-day">' +
                    "<div class='shadow-green'>" +
                    date_str +
                    "</div>" +
                    "</div>" +
                    "</td>");
            }
          }
          str += hasMsg;
        }
        str += "</tr>";
      }
      var table = document.getElementById("table");
      table.innerHTML = str;
    },
    //两个参数代表的含义分别是this对象以及判断当前的操作是不是在进行月份的修改
    sureDate(_this, other) {
      this.newDate = new Date();
      this.ynow = this.newDate.getFullYear();
      if (!other) {
        this.mnow = this.newDate.getMonth(); //月份
      }
      this.dnow = this.newDate.getDate(); //今日日期
      this.firstDay = new Date(this.ynow, this.mnow, 1);
      this.firstnow = this.firstDay.getDay();
      this.m_days = [
        31,
        28 + this.is_leap(this.ynow),
        31,
        30,
        31,
        30,
        31,
        31,
        30,
        31,
        30,
        31,
      ];
      this.tr_str = Math.ceil((_this.m_days[this.mnow] + this.firstnow) / 7);
      this.showMsg();
    },
    preMon() {
      var _this = this;
      this.mnow--;
      this.sureDate(_this, "up");
    },
    nextMon() {
      var _this = this;
      this.mnow++;
      this.sureDate(_this, "next");
    },
    //通过接口返回的是我们当前的月份对应在日历中需要处理的事项
    showMsg() {
      var jsonHtml = [
        {
          date: 2,
          msg: "吃饭",
        },
        {
          date: 3,
          msg: "睡觉",
        },
        {
          date: 4,
          msg: "打豆豆",
        },
        {
          date: 6,
          msg: "豆豆不在家",
        },
        {
          date: 12,
          msg: "~~~~~",
        },
        {
          date: 15,
          msg: "怎么办~~~~",
        },
        {
          date: 20,
          msg: "找豆豆",
        },
      ];
      this.drawTable(jsonHtml);
    },
    handleClick(row) {
      this.$router.push({ path: "" });
    },
  },
};
</script>

<style scoped lang="scss">
#table {
  border-collapse: collapse;
  border: solid 1px #cccc;
  margin: 0 auto;
}

.g-title {
  padding-left: 20px;
  font-weight: 600;
}
.todo-desc {
  padding-left: 20px;
  div {
    margin-top: 10px;
  }
}
.table-box {
  margin-top: 20px;
}
.list-box {
  padding-left: 15px;
}
::v-deep {
  .content-day {
    width: 80px;
    height: 50px;
    text-align: center;
    line-height: 52px;
  }
  .isRedcontent-day {
    width: 80px;
    height: 50px;
    text-align: center;
    line-height: 52px;
  }
  .shadow {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    color: #ccc;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #777;
    border-radius: 15px;
  }
  .shadow-green {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: rgb(107, 244, 112);
    border-radius: 15px;
  }
  .xiqi {
    border-bottom: 1px solid #52ff00bd;
    text-align: center;
    height: 40px;
  }
  .thisDay {
    background: #52ff00bd;
    .shadow {
      background-color: #fff;
    }
  }
}
</style>
