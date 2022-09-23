<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()"
        >返回企业主体首页</el-button
      >
      <h3 class="title">{{ tab }}企业-更多指标</h3>
    </div>
    <div class="flex1" style="justify-content: space-between">
      <div class="title-2">
        当前已添加字段 <span>x</span> 个， 其中必选字段 <span>x</span> 个
      </div>
      <el-button class="export" type="text" @click="back()">导出数据</el-button>
    </div>
    <el-row>
      <el-col
        :sm="24"
        :lg="7"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <div class="left-box">
          <div class="head">
            <span>指标清单</span>
            <el-button type="text">清空重置</el-button>
          </div>
          <div>
            <el-input
              class="filter"
              placeholder="输入关键字进行过滤"
              v-model="filterText"
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="data"
              show-checkbox
              :filter-node-method="filterNode"
              ref="tree"
            >
            </el-tree>
          </div>
        </div>
        <div class="left-box">
          <div class="head">
            <span>主体范围</span>
            <el-button type="text">清空重置</el-button>
          </div>
          <div>
            <el-input
              class="filter"
              placeholder="输入关键字进行过滤"
              v-model="filterText"
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="data"
              show-checkbox
              :filter-node-method="filterNode"
              ref="tree"
            >
            </el-tree>
          </div>
        </div>
      </el-col>
      <el-col
        :sm="24"
        :lg="16"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-table
          class="table-content"
          :data="list"
          border
          style="width: 100%; margin-top: 15px"
        >
          <el-table-column type="index" fixed sortable label="序号">
          </el-table-column>
          <el-table-column prop="date" fixed label="德勤主体代码">
          </el-table-column>
          <el-table-column prop="name" fixed label="主体名称">
          </el-table-column>
          <el-table-column prop="province" fixed label="生效状态">
          </el-table-column>
          <el-table-column prop="city" label="证券代码"> </el-table-column>
          <el-table-column prop="address" label="创建日期"> </el-table-column>
          <el-table-column prop="zip" label="创建人"> </el-table-column>
          <el-table-column prop="zip" label="创建类型"> </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage1"
          :page-size="100"
          layout="total, prev, pager, next"
          :total="1000"
          class="page"
        >
        </el-pagination>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "addGovernment",
  data() {
    return {
      currentPage1: "",
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
      filterText: "",
      data: [
        {
          id: 1,
          label: "一级 1",
          children: [
            {
              id: 4,
              label: "二级 1-1",
              children: [
                {
                  id: 9,
                  label: "三级 1-1-1",
                },
                {
                  id: 10,
                  label: "三级 1-1-2",
                },
              ],
            },
          ],
        },
        {
          id: 2,
          label: "一级 2",
          children: [
            {
              id: 5,
              label: "二级 2-1",
            },
            {
              id: 6,
              label: "二级 2-2",
            },
          ],
        },
        {
          id: 3,
          label: "一级 3",
          children: [
            {
              id: 7,
              label: "二级 3-1",
            },
            {
              id: 8,
              label: "二级 3-2",
            },
          ],
        },
      ],
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
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
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
  },
};
</script>

<style scoped lang="scss">
.left-box {
  border: solid 1px #e8e8e8;
  margin-top: 15px;
  .filter {
    padding: 10px 15px;
  }
  .head {
    background: #ddd9d9;
    display: flex;
    justify-content: space-between;
    padding: 0px 10px;
  }
  .filter-tree {
    margin-bottom: 10px;
  }
}
.content {
  div {
    margin-top: 10px;
  }
}

.back {
  margin-left: 19px;
}
.title {
  margin-left: 31%;
  font-weight: 600;
}
.title-2 {
  margin-left: 37%;
  font-size: 14px;
  span {
    color: greenyellow;
  }
}
.export {
  margin-top: -10px;
}
.page {
  margin-left: 47%;
}
</style>
