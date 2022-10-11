<template>
  <div class="app-container home">
    <h3 class="g-title">企业主体</h3>
    <el-row>
      <el-col :sm="24" :lg="24" style="padding-left: 20px; margin-bottom: 30px">
        <el-card>
          <div class="box-card">
            <div class="top-left">
              <div class="title">整体情况</div>
              <div class="body">
                截止今日起，平台已手机主体
                <span>222222</span>个，其中：
                <div>
                  -上市主体 <span>xxx</span> 个， 发债主体 <span>xxx</span> 个
                </div>
                <div>-即上市又发债主体 <span>xxx</span> 个</div>
                <div>-非上市非发债企业 <span>xxx</span> 个</div>
                <div>-金融机构 <span>xxx</span> 个</div>
              </div>
            </div>
            <div class="top-right">
              <div
                class="box1"
                style="background: gainsboro; width: 15%; text-align: center"
              >
                <div style="margin-top: 50%">非上市非发债15%</div>
              </div>
              <div
                class="box2"
                style="background: greenyellow; width: 20%; text-align: center"
              >
                <div style="margin-top: 11.5%">单纯上市 20%</div>
              </div>
              <div
                class="box2"
                style="background: green; width: 30%; text-align: center"
              >
                <div style="margin-top: 11.5%">即上市又发债 30%</div>
              </div>
              <div
                class="box2"
                style="background: greenyellow; width: 35%; text-align: center"
              >
                <div style="margin-top: 11.5%">单纯发债 35%</div>
              </div>
            </div>
          </div>
          <div>
            <h3 class="g-title" style="margin-top: 10px">
              快速查询企业上市或发债情况
            </h3>
            <div class="select-body mt20">
              <el-input
                class="mr10 ml20"
                v-model="nameInput"
                style="width: 400px"
                placeholder="搜索主体名称 / 代码 / 统一社会信用代码"
              ></el-input>
              <el-button class="mr10" type="primary" @click="select"
                >查询</el-button
              >
            </div>
            <el-card class="mt20">
              <div class="font">查询结果</div>
              <div class="font">共查询到 <span>35</span> 个结果</div>
              <el-table
                class="table-content"
                :data="list"
                style="width: 98%; margin-top: 20px"
              >
                <el-table-column
                  type="index"
                  sortable
                  label="存续状态"
                  width="50"
                >
                </el-table-column>
                <el-table-column prop="date" label="德勤主体代码" sortable>
                </el-table-column>
                <el-table-column prop="name" label="统一社会信用代码">
                </el-table-column>
                <el-table-column prop="province" label="企业名称">
                </el-table-column>
                <el-table-column prop="city" label="上市情况">
                </el-table-column>
                <el-table-column prop="city" label="发债情况">
                </el-table-column>
              </el-table>
              <el-pagination
                class="page-t"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage4"
                :page-sizes="[100, 200, 300, 400]"
                :page-size="100"
                layout="total, sizes, prev, pager, next, jumper"
                :total="400"
              >
              </el-pagination>
            </el-card>
          </div>
        </el-card>
      </el-col>
      <h3 class="g-title" style="margin-top: 10px">企业主体清单</h3>
      <div class="g-tab flex1">
        <a :class="currentTab === '1' ? 'g-select' : ''" @click="changeTab('1')"
          >上市</a
        >
        <a :class="currentTab === '2' ? 'g-select' : ''" @click="changeTab('2')"
          >发债</a
        >
        <a :class="currentTab === '3' ? 'g-select' : ''" @click="changeTab('3')"
          >非上市非发债</a
        >
        <a :class="currentTab === '4' ? 'g-select' : ''" @click="changeTab('4')"
          >金融机构</a
        >
      </div>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <h3 class="g-t-title">{{ tabArr[currentTab] + "企业" }}</h3>
          <div class="g-desc">
            截止日期，会计收录<span>xxxx</span>个地方政府主体，其中
            <span>xxxx</span> 个存续上市， <span>xxxx</span> 个已退市
          </div>
          <div class="g-desc flex1">
            <router-link
              :to="{
                name: 'indexEnterprise',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">更多指标</a>
            </router-link>
            <router-link
              :to="{
                name: 'addEnterprise',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">新增主体</a>
            </router-link>
            <router-link :to="{ name: 'eidtEnterprise' }">
              <a href="">修改信息</a>
            </router-link>
            <router-link
              :to="{
                name: 'historyEnterprise',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">更新记录</a>
            </router-link>
            <a href="">导出上市主体清单</a>
            <el-input
              class="select-x"
              v-model="codeInput"
              placeholder="搜索主体名称/代码/统一社会信用代码"
            ></el-input>
          </div>
          <el-table
            class="table-content"
            :data="list2"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column prop="name" label="存续状态" width="80">
              <template slot-scope="scope">
                <a
                  :class="scope.row.nameUsedNum > 0 ? 'green' : 'red'"
                  @click="checkName(scope.row)"
                  >{{
                    scope.row.nameUsedNum > 0 ? scope.row.nameUsedNum : "暂无"
                  }}</a
                >
              </template>
            </el-table-column>
            <el-table-column prop="entityCode" label="德勤主体代码">
            </el-table-column>
            <el-table-column prop="date" label="证券代码（A）">
            </el-table-column>
            <el-table-column prop="entityName" label="主体名称" width="200">
            </el-table-column>
            <el-table-column prop="nameUsedNum" label="曾用名或别称">
              <template slot-scope="scope">
                <a
                  :class="scope.row.nameUsedNum > 0 ? 'green' : ''"
                  @click="checkName(scope.row)"
                  >{{
                    scope.row.nameUsedNum > 0 ? scope.row.nameUsedNum : "暂无"
                  }}</a
                >
              </template>
            </el-table-column>
            <el-table-column prop="date" label="上市日期（A）">
            </el-table-column>
            <el-table-column prop="date" label="退市日期（B）">
              <template slot-scope="scope">
                <span>{{ scope.row.date || "当前未退市" }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="zip" label="更新记录"> </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getOverviewByGroup, getInfoList } from "@/api/subject";
export default {
  name: "government",
  data() {
    return {
      codeInput: "",
      nameInput: "",
      activeName: "frist",
      tabArr: {
        1: "上市",
        2: "发债",
        3: "非上市非发债",
        4: "金融机构",
      },
      list2: [],
      list: [],
      loading: false,
      currentTab: "1",
      currentPage4: 4,
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      try {
        this.$modal.loading("Loading...");
        getOverviewByGroup({}).then((res) => {
          console.log(res);
        });
        const parmas = {
          param: "",
          type: this.currentTab,
        };
        getInfoList(parmas).then((res) => {
          const { data } = res;
          this.list2 = data;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    changeTab(tab) {
      try {
        this.$modal.loading("Loading...");
        this.currentTab = tab;
        const parmas = {
          param: this.codeInput,
          type: this.currentTab,
        };
        getInfoList(parmas).then((res) => {
          const { data } = res;
          this.list2 = data;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    add() {
      console.log(1);
      this.$router.psuh({ path: "subjectManagement/enterprise" });
    },
    select() {
      console.log(1);
    },
    checkName(row) {
      console.log(row);
    },
  },
};
</script>

<style scoped lang="scss">
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
.green {
  color: greenyellow;
}
.red {
  color: red;
}
.box-card {
  display: flex;
  justify-content: space-between;
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
    div {
      margin-top: 5px;
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
  left: 39%;
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
