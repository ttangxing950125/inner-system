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
                v-model="input"
                style="width: 400px"
                placeholder="请输入内容"
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
                <el-table-column prop="name" label="证券代码（A）">
                </el-table-column>
                <el-table-column prop="province" label="主体名称">
                </el-table-column>
                <el-table-column prop="city" label="曾用名或别称">
                </el-table-column>
                <el-table-column prop="city" label="上市日期（A）">
                </el-table-column>
                <el-table-column prop="city" label="退市日期（A）">
                </el-table-column>
                <el-table-column prop="city" label="更新记录">
                  <template slot-scope="scope">
                    <span style="color: gainsboro">{{ scope.row.city }}</span>
                  </template>
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
        <a
          :class="currentTab === '上市' ? 'g-select' : ''"
          @click="changeTab('上市')"
          >上市</a
        >
        <a
          :class="currentTab === '发债' ? 'g-select' : ''"
          @click="changeTab('发债')"
          >发债</a
        >
        <a
          :class="currentTab === '非上市非发债' ? 'g-select' : ''"
          @click="changeTab('非上市非发债')"
          >非上市非发债</a
        >
        <a
          :class="currentTab === '金融机构' ? 'g-select' : ''"
          @click="changeTab('金融机构')"
          >金融机构</a
        >
      </div>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <h3 class="g-t-title">{{ currentTab + "企业" }}</h3>
          <div class="g-desc">
            截止日期，会计收录<span>xxxx</span>个地方政府主体，其中
            <span>xxxx</span> 个存续上市， <span>xxxx</span> 个已退市
          </div>
          <div class="g-desc flex1">
            <router-link
              :to="{ name: 'indexEnterprise', query: { name: currentTab } }"
            >
              <a href="">更多指标</a>
            </router-link>
            <router-link
              :to="{ name: 'addEnterprise', query: { name: currentTab } }"
            >
              <a href="">新增主体</a>
            </router-link>
            <router-link :to="{ name: 'eidtEnterprise' }">
              <a href="">修改信息</a>
            </router-link>
            <router-link
              :to="{ name: 'historyEnterprise', query: { name: currentTab } }"
            >
              <a href="">更新记录</a>
            </router-link>
            <a href="">导出上市主体清单</a>
            <el-input
              class="select-x"
              v-model="input"
              placeholder="搜索主体名称/代码/统一社会信用代码"
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
      currentTab: "上市",
      currentPage4: 4,
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
    changeTab(tab) {
      this.currentTab = tab;
    },
    add() {
      console.log(1);
      this.$router.psuh({ path: "subjectManagement/enterprise" });
    },
    select() {
      console.log(1);
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
