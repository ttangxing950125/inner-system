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
              v-model="filterTextFirst"
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="data1"
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
              @check-change="handleCheckChange2"
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
import { getAllByGroup } from "@/api/common";
import { getListEntityByPage } from "@/api/subject";
import pagination from "../../components/Pagination";
export default {
  name: "addGovernment",
  components: {
    pagination,
  },
  data() {
    return {
      currentPage1: "",
      currentTime: "",
      list: [],
      tab: this.$route.query.name,
      filterText: "",
      header: [],
      data1: [],
      data2: [
        {
          name: "全部",
          value: [
            {
              name: "内地股票",
              key: 'stockCn',
              value: [],
            },
            {
              name: "香港股票",
              key: 'stockThk',
              value: [],
            },
            // {
            //   name: "接触地划分",
            //   value: [],
            // },
            {
              name: "集合债",
              key: 'coll',
              value: [],
            },
            {
              name: "ABS",
              key: 'abs',
              value: [],
            },
            {
              name: "公募债",
              key: 'publicType',
              value: [],
            },
            {
              name: "私募债",
              key: 'privateType',
              value: [],
            },
          ],
        },
      ],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      selected: {},
      mapList: [],
      filterTextFirst: "",
      filterTextScend: "",
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
      try {
        this.$modal.loading("Loading...");
        getAllByGroup({ type: 1 }).then((res) => {
          const { data } = res;
          this.data1 = data;
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
    handleCheckChange2(data, checked, indeterminate) {
      //获取所有选中的节点 start
      let res = this.$refs.tree2.getCheckedNodes();
      this.selected = {};
      res.forEach((e) => {
        this.selected[e.key] = 1
      });
      
      try {
          this.$modal.loading("loading...");
          this.selected.pageNum = 1
          this.selected.pageSize = 1
          this.selected.mapList = this.mapList
          
        getListEntityByPage(this.selected).then((res) => {
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
            if (more) { 
                more.forEach((i) => {
                e[i.key] = i.value;
                });
            }
          });
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    getList() {
      this.selected.pageNum = this.queryParams.pageNum
      this.selected.pageSize = this.queryParams.pageSize
      this.selected.mapList = this.mapList
      getListEntityByPage(this.selected).then((res) => {
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
