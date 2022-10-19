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
              prop="code"
              label="德勤主体代码"
              
            >
            </el-table-column>
            <el-table-column prop="stockShortName" label="证券简称">
            </el-table-column>
            <el-table-column prop="fieldName" label="变动字段"> </el-table-column>
            <el-table-column prop="originalValue" label="已存值"> </el-table-column>
            <el-table-column prop="created" label="已存值录入日期">
            </el-table-column>
            <el-table-column prop="value" label="修改值"> </el-table-column>
            <el-table-column prop="updated" label="修改日期"> </el-table-column>
            <el-table-column prop="userName" label="修改人"> </el-table-column>
            <!-- <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >撤销</el-button
                >
              </template>
            </el-table-column> -->
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
import { getInfoUpdate } from '@/api/subject'
import pagination from "../../components/Pagination";
export default {
  name: "historyEnterprise",
  components: {
      pagination
  },
  data() {
    return {
      currentTime: "",
      list: [],
      tab: this.$route.query.name,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0
    };
  },
  created() {
    this.init()
  },
  methods: {
      init() {
        try {
            this.$modal.loading("loading...");
            const parmas = {
                pageNum: 1,
                pageSize: this.queryParams.pageSize,
                tableType: 1
            }
            getInfoUpdate(parmas).then(res => {
                const { data } = res
                this.list = data.records
                this.queryParams.pageNum = data.current
                this.total = data.total
            })
        } catch (error) {
            console.log(error);
        } finally {
            this.$modal.closeLoading();
        }
      },
    handleClick() {
      console.log(1);
    },
    back() {
      this.$router.back();
    },
    getList() {
         try {
            this.$modal.loading("loading...");
            const parmas = {
                pageNum: this.queryParams.pageNum,
                pageSize: this.queryParams.pageSize,
                tableType: 1
            }
            getInfoUpdate(parmas).then(res => {
                const { data } = res
                this.list = data.records
                this.queryParams.pageNum = data.current
                this.total = data.total
            })
        } catch (error) {
            console.log(error);
        } finally {
            this.$modal.closeLoading();
        }
    }
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
