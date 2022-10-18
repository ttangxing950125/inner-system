<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()"
        >返回企业主体首页</el-button
      >
      <h3 class="title">{{ tab }}-更多指标</h3>
    </div>
    <div class="flex1" style="justify-content: space-between">
      <div class="title-2">
        当前已添加字段 <span>{{ selected }}</span> 个， 其中必选字段
        <span>5</span> 个
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
            <el-button type="text" @click="reset">清空重置</el-button>
          </div>
          <div>
            <el-input
              class="filter"
              placeholder="输入关键字进行过滤"
              v-model="filterTextFirst"
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="data"
              :props="{ label: 'name', children: 'value' }"
              show-checkbox
              :filter-node-method="filterNode"
              ref="tree1"
              @check-change="handleCheckChange"
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
              v-model="filterTextScend"
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="data2"
              :props="{ label: 'name', children: 'value' }"
              show-checkbox
              :filter-node-method="filterNode"
              ref="tree2"
              @check-change="handleCheckChange"
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
          v-loading="tableLoading"
          class="table-content"
          :data="list"
          border
          style="width: 100%; margin-top: 15px"
        >
          <el-table-column fixed type="index" fixed sortable label="序号">
          </el-table-column>
          <el-table-column fixed prop="dqGovCode" fixed label="德勤主体代码">
          </el-table-column>
          <el-table-column fixed prop="govName" fixed label="主体名称">
          </el-table-column>
          <el-table-column fixed prop="invalid" fixed label="生效状态">
            <template slot-scope="scope">
              <span>{{ scope.row.invalid ? "Y" : "N" }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="created" label="创建日期">
            <template slot-scope="scope">
              <span>{{
                scope.row.created && scope.row.created.substr(0, 10)
              }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="creater" label="创建人"> </el-table-column>
          <el-table-column
            v-for="(item, index) in header"
            :key="index"
            :prop="item"
            :label="item"
          >
          </el-table-column>
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getAllByGroup, getListEntityByPage } from "@/api/common";
import { getGovRange } from "@/api/subject";
import pagination from "../../components/Pagination";
export default {
  name: "addGovernment",
  components: {
    pagination,
  },
  data() {
    return {
      currentPage1: 1,
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
      filterTextFirst: "",
      filterTextScend: "",
      data: [],
      data2: [
        {
          name: "东北综合经济区",
          value: {},
        },
        {
          name: "北部沿海综合经济区",
          value: {},
        },
        {
          name: "东部沿海综合经济区",
          value: {},
        },
      ],
      tableLoading: false,
      mapList: [],
      moreData: [],
      header: [],
      selected: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
    };
  },
  watch: {
    filterTextFirst(val) {
      this.$refs.tree1.filter(val);
    },
    filterTextScend(val) {
      this.$refs.tree2.filter(val);
    },
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.$modal.loading("loading...");
      try {
        getAllByGroup({ type: 2 }).then((res) => {
          const { data } = res;
          this.data = data;
        });
        const params = {
          pageNum: 1,
          pageSize: 10,
        };
        getListEntityByPage(params).then((res) => {
          const { data } = res;
          this.total = data.total;
          this.list = [];
          data.records.forEach((e) => {
            this.list.push(e.govInfo);
          });
        });
        getGovRange({}).then((res) => {
          const { data } = res;
          this.data2 = data.eightER;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    back() {
      this.$router.back();
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCheckChange(data, checked, indeterminate) {
      //获取所有选中的节点 start
      let res = this.$refs.tree1.getCheckedNodes();
      let arrDeptId = [];
      res.forEach((e) => {
        const item = {
          id: e.id,
          name: e.name,
        };
        arrDeptId.push(item);
      });
      this.selected = arrDeptId.length;
      this.mapList = arrDeptId;
      try {
        this.$modal.loading("loading...");
        const params = {
          pageNum: 1,
          pageSize: 10,
          mapList: this.mapList,
        };
        getListEntityByPage(params).then((res) => {
          const { data } = res;
          this.total = data.total;
          this.list = [];
          let more = [];
          data.records.forEach((e) => {
            this.list.push(e.govInfo);
            more = e.more;
            this.header = e.header;
          });
          this.list.forEach((e) => {
            more.forEach((i) => {
              e[i.key] = i.value;
            });
          });
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    reset() {
      getAllByGroup({}).then((res) => {
        const { data } = res;
        this.data = data;
      });
    },
    getList() {
      const params = {
        pageNum: this.queryParams.pageNum,
        pageSize: 10,
      };
      getListEntityByPage(params).then((res) => {
        const { data } = res;
        this.total = data.total;
        this.list = [];
        this.queryParams.pageNum = data.current;
        data.records.forEach((e) => {
          this.list.push(e.govInfo);
        });
      });
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
    background: #f8f8f9;
    display: flex;
    justify-content: space-between;
    padding: 0px 10px;
    span {
      margin-top: 7px;
    }
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
