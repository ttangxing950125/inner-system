<template>
  <div class="app-container home">
    <h3 class="g-title">政府主体</h3>
    <el-row>
      <el-col :sm="24" :lg="24" style="padding-left: 20px; margin-bottom: 30px">
        <el-card class="box-card">
          <div class="top-left">
            <div class="title">整体情况</div>
            <div class="body">
              截止今日起，平台已手机主体
              <span>222222</span
              >个，其中，政府主体<span>222222</span>个，失效主题<span>222222</span>个
            </div>
          </div>
          <div class="top-right">
            <div
              class="box1"
              style="background: green; width: 15%; text-align: center"
            >
              <div style="margin-top: 50%">失效 15%</div>
            </div>
            <div
              class="box2"
              style="background: greenyellow; width: 94%; text-align: center"
            >
              <div style="margin-top: 11.5%">企业 94%</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <h3 class="g-title" style="margin-top: 10px">政府主体清单</h3>
      <div class="g-tab flex1">
        <a
          :class="currentTab === '地方政府' ? 'g-select' : ''"
          @click="changeTab('地方政府')"
          >地方政府</a
        >
        <a
          :class="currentTab === '地方主管部门' ? 'g-select' : ''"
          @click="changeTab('地方主管部门')"
          >地方主管部门</a
        >
        <a
          :class="currentTab === '其他' ? 'g-select' : ''"
          @click="changeTab('其他')"
          >其他</a
        >
      </div>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <h3 class="g-t-title">{{ currentTab }}</h3>
          <div class="g-desc">
            截止日期，会计收录<span>xxxx</span>个地方政府主体，其中<span>xxxx</span>个省级行政区，<span>xxxx</span>个地市级行政区，<span>xxxx</span>个县级行政区，<span>xxxx</span>个经开高新区，
          </div>
          <div class="g-desc flex1">
            <a href="">更多指标</a>
            <router-link :to="{ name: 'addGovernment' }">
              <a href="">新增主体</a>
            </router-link>
            <router-link :to="{ name: 'eidtGovernment' }">
              <a href="">修改信息</a>
            </router-link>
            <router-link :to="{ name: 'historyGovernment' }">
              <a href="">更新记录</a>
            </router-link>
            <el-input
              class="select-x"
              v-model="input"
              placeholder="搜索的亲主体代码/主体名称"
            ></el-input>
          </div>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column type="index" sortable label="生效状态">
            </el-table-column>
            <el-table-column prop="date" label="行政级别" sortable>
            </el-table-column>
            <el-table-column prop="name" label="德勤主体代码">
            </el-table-column>
            <el-table-column prop="province" label="主体名称">
            </el-table-column>
            <el-table-column prop="city" label="曾用名或别称">
            </el-table-column>
            <el-table-column prop="address" label="备注"> </el-table-column>
            <el-table-column prop="zip" label="更新记录"> </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "government",
  data() {
    return {
      input: "",
      activeName: "frist",
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
      currentTab: "地方政府",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    changeTab(tab) {
      this.currentTab = tab;
    },
    add() {
      console.log(1);
      this.$router.psuh({ path: "subjectManagement/enterprise" });
    },
  },
};
</script>

<style scoped lang="scss">
.tabs {
  width: 98%;
  margin-left: 1.5%;
}
.g-title {
  padding-left: 20px;
  font-weight: 600;
}
.g-t-title {
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
