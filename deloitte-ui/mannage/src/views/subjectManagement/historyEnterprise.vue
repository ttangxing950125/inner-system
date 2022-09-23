<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()"
        >返回企业主体首页</el-button
      >
      <h3 class="title">{{ tab }}企业-更新记录</h3>
    </div>
    <el-row>
      <el-col
        :sm="24"
        :lg="24"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <h3 class="g-t-title">内部更新记录</h3>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column
              type="index"
              sortable
              label="德勤主体代码"
              width="50"
            >
            </el-table-column>
            <el-table-column prop="date" label="证券简称" sortable>
            </el-table-column>
            <el-table-column prop="name" label="变动字段"> </el-table-column>
            <el-table-column prop="province" label="已存值"> </el-table-column>
            <el-table-column prop="city" label="已存值录入日期">
            </el-table-column>
            <el-table-column prop="address" label="修改值"> </el-table-column>
            <el-table-column prop="address" label="修改日期"> </el-table-column>
            <el-table-column prop="address" label="修改人"> </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >撤销</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col
        :sm="24"
        :lg="24"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <h3 class="g-t-title">外部更新记录</h3>
          <div class="content">
            <div style="font-weight: 600">存量修改</div>
            <div>最近一次接受更新时间：2022-09-23 11：11：11</div>
            <div>设计变动条数 328 条，其中已确认 10 条， 待确认 318 条</div>
          </div>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column
              type="index"
              sortable
              label="德勤主体代码"
              width="50"
            >
            </el-table-column>
            <el-table-column prop="date" label="证券简称" sortable>
            </el-table-column>
            <el-table-column prop="name" label="变动字段"> </el-table-column>
            <el-table-column prop="province" label="已存值"> </el-table-column>
            <el-table-column prop="city" label="已存值录入日期">
            </el-table-column>
            <el-table-column prop="address" label="修改值"> </el-table-column>
            <el-table-column prop="address" label="修改值推送日期">
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >撤销</el-button
                >
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >忽略</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "addGovernment",
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
      tab: this.$route.query.name,
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
    back() {
      this.$router.back();
    },
  },
};
</script>

<style scoped lang="scss">
.content {
  div {
    margin-top: 10px;
  }
}
.g-t-title {
  font-weight: 600;
}
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
.demo-ruleForm {
  height: 640px;
}
.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
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
.position-add {
  position: relative;
  top: -36%;
  left: 105%;
  width: 75%;
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
