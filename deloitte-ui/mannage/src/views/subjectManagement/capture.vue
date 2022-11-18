<template>
  <div class="app-container home">
    <h3 class="g-title">捕获进度</h3>
    <el-row>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="g-desc flex1">
            <el-input
              class="select-x"
              v-model="nameInput"
              placeholder="主体名/统一社会信用代码"
              @change="selectList"
            ></el-input>
          </div>
          <el-table
            class="table-content"
            :data="list"
            align="center"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column type="expand">
                <template slot-scope="scope">
                    <div class="flex1 space-around">
                        <div class="flex1">
                            <div :class="scope.row.capture === 1 ? 'rectangle' : 'rectangle-gary'">
                                <span class="desc">{{ scope.row.capture === 1 ? '已捕获 '+scope.row.captureTime : '未捕获'}}</span>
                            </div>
                            <div :class="scope.row.capture === 1 ? 'triangle-right' : 'triangle-right-gary'"></div>
                        </div>
                        <div class="flex1">
                            <div :class="scope.row.added === 1 ? 'rectangle' : 'rectangle-gary'">
                                <span class="desc">{{ scope.row.added === 1 ? '已确定新增' : '未确定'}}</span>
                            </div>
                            <div :class="scope.row.added === 1 ? 'triangle-right' : 'triangle-right-gary'"></div>
                        </div>
                        <div class="flex1">
                            <div :class="scope.row.divide === 1 ? 'rectangle' : 'rectangle-gary'">
                                <span class="desc">{{ scope.row.divide === 1 ? '已划分敞口' : '未划分'}}</span>
                            </div>
                            <div :class="scope.row.divide === 1 ? 'triangle-right' : 'triangle-right-gary'"></div>
                        </div>
                        <div class="flex1">
                            <div :class="scope.row.supplement === 1 ? 'rectangle' : 'rectangle-gary'">
                                <span class="desc">{{ scope.row.supplement === 1 ? '补充信息' : '补充信息'}}</span>
                            </div>
                            <div :class="scope.row.supplement === 1 ? 'triangle-right' : 'triangle-right-gary'"></div>
                        </div>
                        <div class="flex1">
                            <div :class="scope.row.pushMeta === 1 ? 'rectangle' : 'rectangle-gary'">
                                <span class="desc">{{ scope.row.pushMeta === 1 ? '已推补录平台' : '未推送'}}</span>
                            </div>
                            <div :class="scope.row.pushMeta === 1 ? 'triangle-right' : 'triangle-right-gary'"></div>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column fixed type="index" width="50"  align="center" label="序号">
            </el-table-column>
            <el-table-column prop="entityCode" align="center" label="主体Code">
            </el-table-column>
            <el-table-column prop="entityName" align="center" label="主体名">
              <template slot-scope="scope">
                <div v-html="replaceFun(scope.row.entityName)"></div>
              </template>
            </el-table-column>
            <el-table-column prop="creditCode" align="center" label="统一社会信用代码" width="200">
              <template slot-scope="scope">
                <div v-html="replaceFun(scope.row.creditCode)"></div>
              </template>
            </el-table-column>
            <el-table-column prop="source" align="center" label="捕获渠道">
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
  </div>
</template>
<script>
import {
  searchCapture
} from "@/api/subject";
import { replaceStr } from "@/utils/index";
import pagination from "../../components/Pagination";
export default {
  name: "government",
  components: {
    pagination,
  },
  data() {
    return {
      nameInput: "",
      list: [],
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
    };
  },
  created() {
  },
  methods: {
    
    replaceFun(row) {
      return replaceStr(row, this.nameInput);
    },
    getList() {
      try {
        this.$modal.loading("Loading...");
        searchCapture(this.nameInput).then((res) => {
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
    selectList() {
      try {
        this.$modal.loading("Loading...");
        searchscope.row.capture(this.nameInput).then((res) => {
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
  }
};
</script>

<style scoped lang="scss">
.font {
  font-size: 13px;
  span {
    color: #86bc25;
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
  color: #86bc25;
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
    width: 350px;
    margin-top: 12%;
    span {
      color: #86bc25;
    }
    div {
      margin-top: 5px;
    }
  }
  .top-right {
    display: flex;
    padding: 20px 20px;
    width: 600px;
    height: 150px;
    margin-top: 5%;
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
.desc-center {
  width: 50px;
  margin: 0 auto;
  margin-top: 30px;
  font-weight: 600;
}
.desc-center2 {
  width: 45px;
  margin: 0 auto;
  margin-top: 30px;
  font-weight: 600;
}
.max {
  height: 100%;
  width: 100%;
}
.triangle-right {
  /* 右三角 */
  width: 0;
  height: 0;
  border-top: 13px solid transparent;
  border-bottom: 13px solid transparent;
  border-left: 13px solid #86bc25;
}
.rectangle {
    /* 长方形 */
    width:170px;
    height: 26px;
    background: #86bc25;
    border-top: 13px solid transparent;
    border-bottom: 13px solid transparent;
    border-left: 13px solid #fff;
    text-align: center;
}
.triangle-right-gary {
  /* 右三角 */
  width: 0;
  height: 0;
  border-top: 13px solid transparent;
  border-bottom: 13px solid transparent;
  border-left: 13px solid #D8D8D8;
}
.rectangle-gary {
    /* 长方形 */
    width: 170px;
    height: 26px;
    background: #D8D8D8;
    border-top: 13px solid transparent;
    border-bottom: 13px solid transparent;
    border-left: 13px solid #fff;
    text-align: center;
}
.desc {    
    z-index: 100;
    margin-top: -11px;
    display: block;
    color: #fff;
}
.space-around{ 
    justify-content: space-around
}
</style>
