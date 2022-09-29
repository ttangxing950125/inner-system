<template>
  <div class="app-container home">
    <h3 class="g-title">副表管理</h3>
    <div class="g-desc flex1">
      <a :class="tab === 1 ? 'g-select' : ''" @click="changeTab(1)"
        >主体曾用名表</a
      >
      <a :class="tab === 2 ? 'g-select' : ''" @click="changeTab(2)"
        >债务信息表</a
      >
      <a :class="tab === 3 ? 'g-select' : ''" @click="changeTab(3)"
        >主体敞口部分</a
      >
    </div>
    <el-row>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <subject v-if="tab === 1" ref="subject"></subject>
          <debt v-if="tab === 2" ref="debt"></debt>
          <subject v-if="tab === 3" ref="subject"></subject>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import subject from "./components/subject.vue";
import debt from "./components/debt.vue";
export default {
  name: "government",
  components: {
    subject,
    debt,
  },
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
      tab: 1,
    };
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    add() {
      this.$router.psuh({ path: "subjectManagement/enterprise" });
    },
    select() {},
    changeTab(tab) {
      this.tab = tab;
    },
  },
};
</script>

<style scoped lang="scss">
.selet-box {
  justify-content: space-between;
}
.font {
  font-size: 13px;
  span {
    color: greenyellow;
  }
}
.page-t {
  margin-top: 10px;
  margin-left: 23%;
}
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
.g-desc {
  margin-top: 3%;
  margin-left: 4%;
  margin-bottom: 1%;
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
  margin-top: 10px;
  width: 24%;
}
.g-select {
  color: greenyellow;
}
</style>
