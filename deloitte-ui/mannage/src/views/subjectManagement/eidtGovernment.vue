<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()"
        >返回企业主体首页</el-button
      >
      <h3 class="title">地方政府-修改信息</h3>
    </div>
    <div class="g-tab flex1">
      <a
        :class="currentTab === '逐一修改' ? 'g-select' : ''"
        @click="changeTab('逐一修改')"
        >逐一修改</a
      >
      <a
        :class="currentTab === '批量修改' ? 'g-select' : ''"
        @click="changeTab('批量修改')"
        >地方主管部门</a
      >
    </div>
    <el-row>
      <el-col :sm="24" :lg="24" class="form-card">
        <el-card class="box-card">
          <div class="select-body mt20">
            <el-input
              class="mr10"
              v-model="input"
              style="width: 400px"
              placeholder="请输入内容"
            ></el-input>
            <el-button class="mr10" type="primary" @click="select"
              >查询</el-button
            >
          </div>
          <h3 class="g-title">查询结果</h3>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 20px"
          >
            <el-table-column type="index" sortable label="序号" width="50">
            </el-table-column>
            <el-table-column prop="date" label="德勤主体代码" sortable>
            </el-table-column>
            <el-table-column prop="name" label="政府名称"> </el-table-column>
            <el-table-column prop="province" label="行政区划代码">
            </el-table-column>
            <el-table-column prop="city" label="上级政府名称">
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >进入修改</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <div class="clearf">
        <div class="bottom-title">
          <span class="g-title">目标政府信息</span>
          <span class="s-2">修改中</span>
        </div>
        <el-button type="text">提交变更并退出修改模式</el-button>
      </div>
      <el-col
        :sm="24"
        :lg="24"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <span style="color: red"
            >注意：带星号字段需同故宫附表维护更新，请进入附表管理模块进行修改</span
          >
          <el-collapse accordion class="collpase">
            <el-collapse-item>
              <template slot="title">
                <span style="font-size: 16px">基本信息</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">德勤主体代码</div>
                  <div style="color: a7a7a7">GV304892</div>
                  <el-button class="edit-btn" type="text" size="mini"
                    >修改</el-button
                  >
                </div>
                <div class="flex1">
                  <div class="first">德勤主体代码</div>
                  <div style="color: a7a7a7">GV304892</div>
                  <el-button class="edit-btn" type="text" size="mini"
                    >修改</el-button
                  >
                </div>
                <div class="flex1">
                  <div class="first">德勤主体代码</div>
                  <div style="color: a7a7a7">GV304892</div>
                  <el-button class="edit-btn" type="text" size="mini"
                    >修改</el-button
                  >
                </div>
                <div class="flex1">
                  <div class="first">德勤主体代码</div>
                  <div style="color: a7a7a7">GV304892</div>
                  <el-button class="edit-btn" type="text" size="mini"
                    >修改</el-button
                  >
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">曾用名或别称备注</div>
                  <div>（暂无记录）</div>
                </div>
                <div class="flex1">
                  <div class="first">曾用名或别称备注</div>
                  <div>（暂无记录）</div>
                </div>
                <div class="flex1">
                  <div class="first">曾用名或别称备注</div>
                  <div>（暂无记录）</div>
                </div>
                <div class="flex1">
                  <div class="first">曾用名或别称备注</div>
                  <div>（暂无记录）</div>
                </div>
              </el-col>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "eidtGovernment",
  data() {
    return {
      currentTime: "",
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
      currentTab: "",
    };
  },
  created() {
    this.getCurrentTime();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    handleClick() {
      console.log(1);
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
      this.currentTime =
        yy + "-" + mm + "-" + dd + " " + hh + ":" + mf + ":" + ss;
    },
    check(name) {
      this.dialogVisible = true;
    },
    changeTab(tab) {
      this.currentTab = tab;
    },
    back() {
      this.$router.back();
    },
  },
};
</script>

<style scoped lang="scss">
.edit-btn {
  margin-top: -3px;
  margin-left: 5px;
}
.flex1 {
  .first {
    width: 200px;
  }
}
.collpase {
  margin-top: 20px;
  ::v-deep .el-collapse {
    border-top: none;
  }
}
.clearf {
  clear: both;
  display: flex;
  justify-content: space-between;
}
.bottom-title {
  padding-left: 20px;
  .s-2 {
    color: greenyellow;
    font-size: 13px;
    margin-left: 22px;
  }
}
.right-btn {
  position: absolute;
  top: 78%;
  right: 1%;
}
.g-title {
  font-weight: 600;
}
.select-body {
  margin: 0 auto;
  width: 45%;
  margin-top: 20px;
}
.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-top: 1%;
}
.title {
  margin-left: 31%;
  font-weight: 600;
}
.box-card {
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
