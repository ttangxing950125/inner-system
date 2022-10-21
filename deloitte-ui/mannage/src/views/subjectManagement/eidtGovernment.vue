<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()"
        >返回企业主体首页</el-button
      >
      <h3 class="title">地方政府-修改信息</h3>
    </div>
    <div class="g-tab flex1">
      <a
        :class="currentTab === '逐一修改' ? 'g-select' : ''"
        @click="changeTab('逐一修改')"
        >逐一修改</a
      >
      <a
        :class="currentTab === '批量修改' ? 'g-select' : ''"
        @click="changeTab('批量修改')"
        >地方主管部门</a
      >
    </div>
    <el-row>
      <el-col :sm="24" :lg="24" class="form-card">
        <el-card class="box-card">
          <div class="select-body mt20">
            <el-input
              class="mr10"
              v-model="input"
              style="width: 80%;"
              placeholder="请输入内容"
            ></el-input>
            <el-button class="mr10" type="primary" @click="select"
              >查询</el-button
            >
          </div>
          <h3 class="g-title">查询结果</h3>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 20px"
          >
            <el-table-column type="index" label="序号" width="50">
            </el-table-column>
            <el-table-column prop="dqGovCode" label="德勤主体代码">
            </el-table-column>
            <el-table-column prop="govName" label="政府名称">
                <template slot-scope="scope">
                <div v-html="replaceFun(scope.row.govName)"></div>
              </template>
            </el-table-column>
            <el-table-column prop="govCode" label="行政区划代码">
            </el-table-column>
            <el-table-column prop="preGovName" label="上级政府名称">
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  v-if="!scope.row.check"
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >进入修改</el-button
                >
                <span v-else class="green">已选中</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <div class="clearf">
        <div class="bottom-title">
          <span class="g-title">目标政府信息</span>
          <span v-if="eight.length > 0" class="s-2">修改中</span>
        </div>
        <el-button v-if="eight.length > 0" type="text" @click="submit">提交变更</el-button>
      </div>
      <el-col
        :sm="24"
        :lg="24"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <span style="color: red"
            >注意：带星号字段需同故宫附表维护更新，请进入附表管理模块进行修改</span
          >
          <el-collapse accordion class="collpase">
            <el-collapse-item v-if="info.govInfo">
              <template slot="title">
                <span style="font-size: 16px">基本信息</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">德勤主体代码（企业）</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.govInfo.dqGovCode
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">政府主体名称</div>
                  <el-input
                    class="t-input"
                    v-model="info.govInfo && info.govInfo.govName"
                    @change="item.edit = true"
                  ></el-input>
                </div>
                <div class="flex1">
                  <div class="first">政府主体类型</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.govInfo && levelStr[info.govInfo.govType] 
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">生效状态</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{ info.govInfo && info.govInfo.status === 1 ? 'Y' : 'N' }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">曾用名或别称</div>
                  <el-input
                    class="t-input"
                    v-model="info.govInfo && info.govInfo.govNameHis"
                    @change="item.edit = true"
                  ></el-input>
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">曾用名或别称备注</div>
                  <el-input
                    class="t-input"
                    v-model="info.govInfo && info.govInfo.entityNameHisRemarks"
                    @change="item.edit = true"
                  ></el-input>
                </div>
                <div class="flex1">
                  <div class="first">曾用名或别称更新人</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{ info.govInfo && info.govInfo.entityNameHis }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">曾用名或别称更名日期</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo && info.govInfo.updated }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.govInfo">
              <template slot="title">
                <span style="font-size: 16px">行政区划</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">政府主体官方行政代码</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.govInfo.govCode
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">政府主体行政单位级别-大类</div>
                   <el-select v-model="info.govInfo.govLevelBig" placeholder="请选择">
                        <el-option
                        v-for="item in bigLevel"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </div>
                <div class="flex1">
                  <div class="first">政府主体行政单位级别-小类</div>
                    <el-select v-model="info.govInfo.govLevelSmall" placeholder="请选择">
                        <el-option
                        v-for="item in smallLevel"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </div>
                <div class="flex1">
                  <div class="first">是否为省会城市</div>
                  <el-select v-model="info.govInfo.provincial" placeholder="请选择">
                        <el-option
                        label="是"
                        :value="1">
                        </el-option>
                        <el-option
                        label="否"
                        :value="0">
                        </el-option>
                    </el-select>
                </div>
                <div class="flex1">
                  <div class="first">地理分区归属</div>
                  <el-select v-model="info.govInfo.gegphZone" placeholder="请选择">
                        <el-option
                        v-for="item in eight"
                        :key="item.send"
                        :label="item.name"
                        :value="item.send">
                        </el-option>
                    </el-select>
                </div>
                <div class="flex1">
                  <div class="first">八大经济区归属</div>
                    <el-select v-model="info.govInfo.economyRegion" placeholder="请选择">
                        <el-option
                        v-for="item in eight"
                        :key="item.send"
                        :label="item.name"
                        :value="item.send">
                        </el-option>
                    </el-select>
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">19个城市群归属</div>
                   <el-select v-model="info.govInfo.cityGroup" placeholder="请选择">
                        <el-option
                        v-for="item in eight"
                        :key="item.send"
                        :label="item.name"
                        :value="item.send">
                        </el-option>
                    </el-select>
                </div>
                <div class="flex1">
                  <div class="first">是否为国家中心城市</div>
                    <el-select v-model="info.govInfo.countryCenter" placeholder="请选择">
                        <el-option
                        label="是"
                        :value="1">
                        </el-option>
                        <el-option
                        label="否"
                        :value="0">
                        </el-option>
                    </el-select>
                </div>
                <div class="flex1">
                  <div class="first">城市规模</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo.govScale }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">城市分级</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo.govGrading }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">是否为百强县</div>
                  <el-select v-model="info.govInfo.hundred" placeholder="请选择">
                        <el-option
                        label="是"
                        :value="1">
                        </el-option>
                        <el-option
                        label="否"
                        :value="0">
                        </el-option>
                    </el-select>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.govInfo">
              <template slot="title">
                <span style="font-size: 16px">关联政府</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">上级行政单位名称</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.govInfo.preGovName
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">上级行政单位官方行政代码</div>
                  <el-input
                    class="t-input"
                    v-model="info.govInfo.preCode"
                  ></el-input>
                </div>
                <div class="flex1">
                  <div class="first">上级行政单位德勤代码</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo.preGovCode }}
                  </div>
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">失效后关联政府主体名称</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo.newGovName }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">失效后关联政府主体官方行政代码</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo.newGovCode }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">失效后关联政府主体德勤代码</div>
                  <div class="scond" style="color: #updated">
                    {{ info.govInfo.newDqCode }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.relationEntity">
              <template slot="title">
                <span style="font-size: 16px">关联企业</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">关联城投企业数量</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.relationEntity
                    }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getGovByName,
  getInfoDetailGov,
  updateInfoList,
  getGovLevel,
  getGovRange
} from "@/api/subject";
import { replaceStr } from "@/utils/index";
export default {
  name: "eidtGovernment",
  data() {
    return {
      currentTime: "",
      list: [],
      currentTab: "",
      input: '',
      info: {},
      levelStr: {
        1: "省级行政区",
        2: "地级市行政区",
        3: "县级行政区",
        4: "经开，高新区，新区等",
      },
      bigLevel: [],
      smallLevel: [],
      eight: []
    };
  },
  created() {
  },
  methods: {
      replaceFun(row) {
      return replaceStr(row, this.input);
    },
     handleClick(row) {
      try {
        this.$modal.loading("Loading...");
        const code = row.dqGovCode
        getInfoDetailGov({ dqGovCode: code }).then((res) => {
          const { data } = res;
          this.info = data;
          row.check = true
        });
        getGovLevel({}).then((res) => {
          const { data } = res;
          data.forEach(e => {
             if (e.level === 1) {
                 this.bigLevel.push(e)
             }else {
                 this.smallLevel.push(e)
             }
          });
        });
        getGovRange({}).then((res) => {
          const { data } = res;
          this.eight = data[4].value
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
    changeTab(tab) {
      this.currentTab = tab;
    },
    back() {
      this.$router.back();
    },
    select() {
      try {
        this.$modal.loading("Loading...");
        const parmas = {
          govName: this.input
        };
        getGovByName(parmas).then((res) => {
          const { data } = res;
          this.list = data
        //   this.list = data.records;
        //   this.total = data.total;
        //   this.queryParams.pageNum = data.current;
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.$modal.closeLoading();
      }
    },
     submit() {
      try {
        this.$modal.loading("Loading...");
        updateInfoList(this.info.govInfo).then((res) => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              message: "操作成功",
              type: "success",
            });
          }
        });
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: 'error'
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
  },
};
</script>

<style scoped lang="scss">
.t-input {
  width: 200px;
}
.edit-btn {
  margin-top: -3px;
  margin-left: 5px;
}
.flex1 {
  .first {
    width: 250px;
    font-size: 14px;
    line-height: 3;
  }
  .scond {
    font-size: 14px;
    line-height: 3;
    width: 200px;
  }
}
.collpase {
  margin-top: 20px;
  ::v-deep .el-collapse {
    border-top: none;
  }
}
.clearf {
  clear: both;
  display: flex;
  justify-content: space-between;
}
.bottom-title {
  padding-left: 20px;
  .s-2 {
    color: #86bc25;
    font-size: 13px;
    margin-left: 22px;
  }
}
.right-btn {
  position: absolute;
  top: 78%;
  right: 1%;
}
.g-title {
  font-weight: 600;
}
.select-body {
  margin: 0 auto;
  width: 45%;
  margin-top: 20px;
}
.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-top: 1%;
}
.title {
  margin-left: 31%;
  font-weight: 600;
}
.box-card {
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
    color: #86bc25;
  }
}
.green {
    color: #86bc25;
}
</style>
