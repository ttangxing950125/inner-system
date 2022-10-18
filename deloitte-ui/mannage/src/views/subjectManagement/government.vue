<template>
  <div class="app-container home">
    <h3 class="g-title">政府主体</h3>
    <el-row>
      <el-col :sm="24" :lg="24" style="padding-left: 20px; margin-bottom: 30px">
        <el-card class="box-card">
          <div class="top-left">
            <div class="title">整体情况</div>
            <div class="body">
              截止今日起，平台已收录主体
              <span>{{ count }}</span
              >个，其中，生效主体<span>{{ invalid }}</span
              >个，未生效主题<span>{{ unInvalid }}</span
              >个
            </div>
          </div>
          <div class="top-right">
            <div
              class="box1"
              :style="{
                background: '#CCCCCC',
                width: unInvalidPercent + '%',
                'text-align': 'center',
              }"
            >
              <div style="margin-top: 45px">
                {{ unInvalidPercent ? "未生效" + unInvalidPercent + "%" : "" }}
              </div>
            </div>
            <div
              class="box2"
              :style="{
                background: '#86bc25',
                width: invalidPercent + '%',
                'text-align': 'center',
              }"
            >
              <div style="margin-top: 45px">
                {{ invalidPercent ? "生效" + invalidPercent + "%" : "" }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <h3 class="g-title" style="margin-top: 10px">政府主体清单</h3>
      <div class="g-tab flex1">
        <a :class="currentTab === '1' ? 'g-select' : ''" @click="changeTab('1')"
          >地方政府</a
        >
      </div>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <h3 class="g-t-title">{{ tabArr[currentTab] }}</h3>
          <div class="g-desc">
            截止日期，会计收录
            <span>{{ overview.govTotle }}</span> 个地方政府主体，其中
            <span>{{ overview.province }}</span> 个省级行政区，<span>{{
              overview.city
            }}</span>
            个地市级行政区，<span>{{ overview.area }}</span> 个县级行政区，<span
              >{{ overview.gx }}</span
            >
            个经开高新区
          </div>
          <div class="g-desc flex1">
            <router-link
              :to="{
                name: 'indexGovernment',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">更多指标</a>
            </router-link>
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
              placeholder="搜索德勤主体代码/主体名称"
              @change="selectTable"
            ></el-input>
          </div>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column sortable label="生效状态" width="100px">
              <template slot-scope="scope">
                <span :class="scope.row.status === '0' ? 'green' : 'red'">{{
                  scope.row.status === "0" ? "N" : "Y"
                }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="govLevel" label="行政级别" sortable>
              <template slot-scope="scope">
                <span>{{ getLevel(scope.row.govLevelBig) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="dqGovCode" label="德勤主体代码">
            </el-table-column>
            <el-table-column prop="govName" label="主体名称"> </el-table-column>
            <el-table-column prop="nameUsedNum" label="曾用名或别称">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  style="color: rgb(181 182 184)"
                  @click="usedName"
                  >{{ 1 }}</el-button
                >
              </template>
            </el-table-column>
            <el-table-column prop="entityNameHisRemarks" label="备注">
            </el-table-column>
            <el-table-column prop="updated" label="更新记录">
              <template slot-scope="scope">
                <span>{{ getLocalTime(scope.row.updated) }}</span>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="曾用名或别称管理" :visible.sync="usedDig" width="30%">
      <el-table
        class="table-content"
        :data="list"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" sortable label="序号" width="100px">
          <template slot-scope="scope">
            <span>{{ scope.row.invalid === "0" ? "N" : "Y" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="govLevel" label="行政级别" sortable>
        </el-table-column>
        <el-table-column prop="dqGovCode" label="德勤主体代码">
        </el-table-column>
        <el-table-column prop="govName" label="主体名称"> </el-table-column>
        <el-table-column prop="nameUsedNum" label="曾用名或别称">
          <template slot-scope="scope">
            <a
              :class="scope.row.nameUsedNum > 0 ? 'green' : ''"
              @click="usedName(scope.row)"
              >{{
                scope.row.nameUsedNum > 0 ? scope.row.nameUsedNum : "暂无"
              }}</a
            >
          </template>
        </el-table-column>
        <el-table-column prop="entityNameHisRemarks" label="备注">
        </el-table-column>
        <el-table-column prop="updated" label="更新记录">
          <template slot-scope="scope">
            <span>{{ getLocalTime(scope.row.updated) }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { getInfoList, getOverview } from "@/api/common";
import pagination from "../../components/Pagination";
import { getInfoListGov, getGovView } from "@/api/subject";
import { formatDate } from "@/utils/index";
export default {
  name: "government",
  data() {
    return {
      usedDig: false,
      input: "",
      activeName: "frist",
      list: [],
      loading: false,
      currentTab: "1",
      tabArr: {
        1: "地方政府",
        2: "地方主管部门",
        3: "其他",
      },
      count: 0,
      invalid: 0,
      unInvalid: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      overview: {},
    };
  },
  created() {
    this.init();
  },
  computed: {
    invalidPercent() {
      const res = (this.invalid / this.count).toFixed(4);
      return res * 100;
    },
    unInvalidPercent() {
      const res = (this.unInvalid / this.count).toFixed(4);
      return res * 100;
    },
  },
  methods: {
    init() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          param: this.input,
          pageSize: this.queryParams.pageSize,
          pageNum: this.queryParams.pageNum,
          type: this.currentTab,
        };
        getInfoListGov(parmas).then((res) => {
          const { data } = res;
          this.list = data.records;
          this.total = data.total;
          this.queryParams.pageNum = data.current;
        });
        getOverview({}).then((res) => {
          const { data } = res;
          this.count = data.count;
          this.invalid = data.invalid;
          this.unInvalid = data.unInvalid;
        });
        getGovView({}).then((res) => {
          const { data } = res;
          this.overview = data;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    changeTab(tab) {
      this.currentTab = tab;
      try {
        this.$modal.loading("Loading...");
        this.currentTab = tab;
        this.queryParams.pageNum = 1;
        const parmas = {
          param: this.codeInput,
          type: this.currentTab,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        getInfoListGov(parmas).then((res) => {
          const { data } = res;
          this.list = data.records;
          this.total = data.total;
          this.queryParams.pageNum = data.current;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    selectTable() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          param: this.input,
          pageSize: this.queryParams.pageSize,
          pageNum: 1,
          type: this.currentTab,
        };
        getInfoListGov(parmas).then((res) => {
          const { data } = res;
          this.list = data.records;
          this.total = data.total;
          this.queryParams.pageNum = data.current;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    getLocalTime(nS) {
      return formatDate(1664363715);
      // return new Date(parseInt(1664363715) * 1000)
      //   .toLocaleString()
      //   .replace(/:\d{1,2}$/, " ");
    },
    usedName(row) {},
    getList() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          param: this.input,
          type: this.currentTab,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        getInfoListGov(parmas).then((res) => {
          const { data } = res;
          this.list = data.records;
          this.total = data.total;
          this.queryParams.pageNum = data.current;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    getLevel(row) {
      let ret = "";
      switch (row) {
        case "1":
          ret = "1-省级行政区";
          break;
        case "2":
          ret = "2-地级行政区";
          break;
        case "3":
          ret = "3-县级行政区";
          break;
        case "4":
          ret = "4-经开高新区";
          break;
      }
      return ret;
    },
  },
};
</script>

<style scoped lang="scss">
.green {
  color: #86bc25;
}
.red {
  color: red;
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
      color: #86bc25;
    }
  }
  .top-right {
    display: flex;
    padding: 20px 20px;
    width: 40%;
    height: 150px;
  }
  .right-number {
    position: relative;
    left: 167%;
    top: 30%;
    color: #86bc25;
  }
}
.g-desc {
  margin-top: 15px;
  span {
    color: #86bc25;
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
  left: 49%;
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
    color: #86bc25;
  }
}
</style>
