<template>
  <div class="app-container home">
    <div class="home">
      <div class="mt20">
        <div class="flex1">
          <h3 class="g-t-title">债券交易信息表</h3>
          <div class="mt20">
            <el-button @click="notice = !notice" type="text" class="notice" size="small"
              >{{ notice ? '收起近期敞口划分变更公告' : '展开近期敞口划分变更公告' }}</el-button
            >
          </div>
        </div>
        <div class="mb20" v-if="notice">
          <el-table class="table-content" :data="list2" style="margin-top: 15px" max-height="300">
          <el-table-column type="index" label="序号"> </el-table-column>
          <el-table-column prop="addHis" label="变更操作日期">
          </el-table-column>
          <el-table-column prop="addHis" label="变更年份">
            <template slot-scope="scope">
              <div>{{ scope.row.addHis.substr(0, 4) }}</div>
            </template>  
          </el-table-column>
          <el-table-column prop="entityNameHis" label="变更企业">
          </el-table-column>
          <el-table-column prop="city" label="变更版本">
             <template slot-scope="scope">
              <div>{{ scope.row.proCustName ? scope.row.proCustName + ' - ' + scope.row.masterName : '-' }}</div>
            </template>  
          </el-table-column>
          <el-table-column prop="masterName" label="原敞口"> </el-table-column> 
          <el-table-column prop="masterName" label=""> 
              <template slot-scope="scope">
              <div>➡</div>
            </template>
        </el-table-column> 
          <el-table-column prop="masterNameNew" label="变更后敞口"> </el-table-column>
        </el-table>
        </div>
        <div>
          <div class="select-body">
            <div class="">
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
          </div>
        </div>
        <el-table class="table-content" :data="list" style="margin-top: 15px" max-height="300">
          <el-table-column type="index" label="序号"> </el-table-column>
          <el-table-column prop="entityCode" label="德勤主体代码">
          </el-table-column>
          <el-table-column prop="entityName" label="企业名称">
            <template slot-scope="scope">
              <div v-html="replaceFun(scope.row.entityName)"></div>
            </template>
          </el-table-column>
          <el-table-column prop="creditCode" label="统一社会信用代码">
          </el-table-column>
          <el-table-column prop="city" label="是否上市">
            <template slot-scope="scope">
              <div>{{ scope.row.list === 1 ? '已上市' : '未上市' }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="city" label="是否发债">
            <template slot-scope="scope">
              <div>{{ scope.row.issueBonds === 1 ? '已发债' : '未发债' }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="zip" label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleClick(scope.row)"
                type="text"
                size="small"
                >设为目标</el-button
              >
            </template></el-table-column
          >
        </el-table>
        <div class=btn-str>(列表中默认展示当日新增主体，如果有其他查询需要请于搜索框中输入相关信息)</div>
      </div>
      <div class="mt20">
        <div class="flex1">
          <h3 class="g-t-title">选中主体敞口划分信息</h3>
          <div class="g-tab flex1 mt20">
            <a v-for="(item, index) in options" :key="index" :class="currentTab === item.id ? 'g-select' : ''" @click="changeTab(item.id, item.proName)"
            >{{item.proName}}</a>
        </div>
          <!-- <div class="mt20">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >提交变更并退出修改模式</el-button
            >
          </div> -->
        </div>
        <el-card v-if="JSON.stringify(info) !== '{}'">
          <div class="content-title">CRM基本信息</div>
          <el-col :sm="24" :lg="12" class="form-card">
            <div class="flex1 mt10">
              <div class="first">主体德勤代码</div>
              <div class="content">{{ info.entityCode }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">企业名称</div>
              <div class="content">{{ info.entityName }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">统一社会信用代码</div>
              <div class="content">{{ info.creditCode }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">是否上市</div>
              <div class="content">{{ info.list === 1 ? '已上市' : '未上市' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">是否发债</div>
              <div class="content">{{ info.issueBonds === 1 ? '已发债' : '未发债' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">是否为金融机构</div>
              <div class="content">{{ info.finance === 1 ? '是' : '否' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">金融细分行业</div>
              <div class="content">{{ info.mince || '-' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">是否为城投机构</div>
              <div class="content">{{ info.isGov || '-' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">所属城投机构</div>
              <div class="content">{{ info.govName || '-' }}</div>
            </div>
          </el-col>
          <el-col :sm="24" :lg="12" class="form-card">
            <div class="flex1 mt10">
              <div class="first">YY-是否为城投</div>
              <div class="content">{{ info.yyUrban && info.yyUrban === '1' ? '是' : '否' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">中诚信-是否为城投</div>
              <div class="content">{{ info.zhongxinUrban && info.zhongxinUrban === '1' ? '是' : '否' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">wind行业划分</div>
              <div class="content">{{ info.windMaster && info.windMaster === '1' ? '是' : '否' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">申万行业划分</div>
              <div class="content">{{ info.shenWanMaster && info.shenWanMaster === '1' ? '是' : '否' }}</div>
            </div>
            <div class="flex1 mt10">
              <div class="first">IB行业划分结果</div>
              <div class="content">{{ info.yyUrban && info.yyUrban === '1' ? '是' : '否' }}</div>
            </div>
          </el-col>
          <div v-if="currentTab" class="flex1 max">
            <div class="content-title">{{ currentName }}</div>
            <el-divider class="mt10"></el-divider>
            <el-button class="check-p" type="text" @click="checkProject">查看产品覆盖与敞口映射规则</el-button>
          </div>
          <div v-if="currentTab" class="g-tab flex1 mt20">
            <a v-for="(item, index) in years" :key="index" :class="currentYear === item ? 'g-select' : ''" @click="changeYear(item)"
            >{{ item }}</a>
          </div>
          <el-col v-if="currentTab && currentYear" :sm="24" :lg="12" class="form-card">
            <div v-for="(item, index) in btnContent" :key="index" class="flex1 mt10">
              <div class="first">{{ item.proName + ' - ' + item.customer }}</div>
              <div class="content">{{ item.masterName }}</div>
              <el-dropdown @command="(row)=>clickTab(row, index)" @visible-change="(row)=>getMenu(row, item)" class="ml20" style="margin-top:-3px" trigger="click">
                <el-button  type="text">修改</el-button>
                <el-dropdown-menu  slot="dropdown">
                    <el-dropdown-item v-for="(e,index) in menus" :key="index" :command="e">{{ e.masterName }}</el-dropdown-item>
                </el-dropdown-menu>
                </el-dropdown>
            </div>
          </el-col>
        </el-card>
      </div>
    </div>
    <el-dialog :title="currentName" :visible.sync="dialogVisible" width="30%">
      <div class="p-top">
        <span class="project-font">产品覆盖规则</span>
      </div>
      <div class="p-top mt20">
        <span class="version">版本：v </span><span class="green">{{ role.proVersion }}</span>
      </div>

      <div class="p-red mt20">
        <div>{{ role.proRemark }}</div>
      </div>
        <div class="p-top mt20">
        <span class="project-font">敞口映射规则</span>
      </div>
      <div class="p-top mt20">
        <span class="version">版本：v </span><span class="green">{{ role.masterVer }}</span>
      </div>
      <span class="version">最新版本： </span>
      <el-button type="text" class="p-top mt20" @click="downFile">{{ role.fileName }}</el-button>
      <div slot="footer" class="dialog-footer center">
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { replaceStr, download } from "@/utils/index";
import { entityMaster, entityByMaster, getDataHis, getDataYear, getProduct, getProDucCom, updateRel, ProMaDir, getProductsOne, getProductsExcel } from "@/api/subject";
export default {
  name: "government",
  data() {
    return {
        currentTab: '',
        notice: false,
      input: "",
      list: [],
      loading: false,
      dialogVisible: false,
      info: {},
      list2: [],
      options: [],
      currentName: '',
      years: [],
      currentYear: '',
      code: '',
      btnContent: {},
      entityName:'',
      menus: [],
      selected: [],
      entityCode: '',
      proCumId: '',
      role: {},
    };
  },
  created() {
      this.init()
  },
  methods: {
    init() {
       try {
          this.$modal.loading("Loading...");
          getDataHis({}).then(res => {
              this.list2 = res.data
          })
          getDataYear({}).then(res => {
              this.years = res.data
          })
          getProduct({}).then((res) => {
            const { data } = res;
            this.options = data;
        });
        
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    changeTab(tab, name) {
        this.currentTab = tab
        this.currentName = name
        try {
          this.$modal.loading("Loading...");
          if(!this.currentTab && !this.currentYear) {
              return
          }
          const params = {
              dataYear: this.currentYear,
              entityCode: this.code,
              proId: this.currentTab,
          }
          getProDucCom(params).then(res => {
              this.btnContent = res.data
          })
        
        } catch (error) {
            this.$message({
            showClose: true,
            message: error,
            type: "error",
            });
        } finally {
            this.$modal.closeLoading();
        }
    },
    changeYear(tab) {
        this.currentYear = tab   
        try {
          this.$modal.loading("Loading...");
          const params = {
              dataYear: this.currentYear,
              entityCode: this.code,
              proId: this.currentTab,
          }
          getProDucCom(params).then(res => {
              this.btnContent = res.data
          })
        
        } catch (error) {
            this.$message({
            showClose: true,
            message: error,
            type: "error",
            });
        } finally {
            this.$modal.closeLoading();
        }
    },
    select() {
      try {
          this.$modal.loading("Loading...");
          entityMaster({name: this.input}).then(res => {
              const { data } = res
              this.list = data
          })
        
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    handleClick(row) {
      try {
          this.code = row.entityCode
          this.entityName = row.entityName
          this.entityCode = row.entityCode
          this.$modal.loading("Loading...");
          entityByMaster({code: row.entityCode}).then(res => {
              this.info = res.data
          })
        
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    replaceFun(row) {
      return replaceStr(row, this.input);
    },
    downFile() {
        try {
            this.$modal.loading("Loading...")
            getProductsExcel({id: this.currentTab}).then(res => {
                download(res, this.role.fileName)
            })
        
        } catch (error) {
            this.$message({
            showClose: true,
            message: error,
            type: "error",
            });
        } finally {
            this.$modal.closeLoading();
        } 
    },
    submit(index, row) {
        try {
            const parmas = {
                entityName: this.entityName,
                proCustId: this.selected.proCumId,
                dataYear: this.currentYear,
                customer: this.selected.customer,
                masterNameOld: this.selected.masterName,
                dictIdOld: this.selected.dictId,
                masterNameNew: this.masterNameNew,
                dictIdNew: this.dictIdNew,
                entityCode: this.entityCode,
            }
            this.$modal.loading("Loading...");
            updateRel(parmas).then(res => {
                if(res.data) {
                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.btnContent[index].masterName = row.masterName
                }else {
                    this.$message({
                        showClose: true,
                        message: '操作失败！',
                        type: "error",
                    }); 
                }
            })
        
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      } 
    },
    clickTab(row, index) {
        this.masterNameNew = row.masterName
        this.dictIdNew = row.id
        this.submit(index, row)
    },
    getMenu(row, id) {
        try {
            if(!row) {
                return
            }
            this.selected = id
            this.$modal.loading("Loading...");
            ProMaDir({proCustId: id.proCumId}).then(res => {
                this.menus = res.data
            })
        
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: "error",
        });
      } finally {
        this.$modal.closeLoading();
      } 
    },
    checkProject() {
        try {
            this.dialogVisible = true
            this.$modal.loading("Loading...");
            getProductsOne({id: this.currentTab}).then(res => {
                this.role = res.data
                let fileNameArr = this.role.filePath.split('/')
                this.role.fileName = fileNameArr[1]
            })
        } catch (error) {
            this.$message({
            showClose: true,
            message: error,
            type: "error",
            });
        } finally {
            this.$modal.closeLoading();
        } 
    }
  },
};
</script>

<style scoped lang="scss">
.between {
  justify-content: space-between;
}
.green {
    color: #86bc25;
    font-weight: 600;
}
.g-t-title {
  font-weight: 600;
}
.select-x {
  margin-top: 10px;
  width: 24%;
}
.g-desc {
  margin-left: 20px;
  margin-bottom: 1%;
  span {
    color: #86bc25;
  }
  a {
    font-size: 14px;
    color: #9b9b9b;
    text-decoration: revert;
    margin-right: 10px;
  }
  .g-select {
    color: #86bc25;
  }
}
.select-body {
  margin-left: 29%;
}
.font-color {
  color: red;
}
.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-top: 1%;
}
.edit-btn {
  margin-top: -3px;
  margin-left: 5px;
}
.flex1 {
  .first {
    width: 200px;
  }
  .content {
    color: #a7a7a7;
    margin-top: 3px;
  }
}
.color-gary {
  color: #a7a7a7;
}
.t-input {
  width: 50%;
}
.notice {
    margin-top: -5px;
    margin-left: 28px;
}
.btn-str{ 
    font-size: 13px;
    color: #cccc;
    margin-top: 5px;
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
.content-title {
    font-weight: 600;
    color: #86bc25;
    width: 105px;
}
.check-p {
        margin-top: -15px;
    margin-left: 20px;
}
.max {
    width: 100%
}
.project-font {
    font-size: 14px;
    font-weight: 600;
}
.version {
    font-size: 12px;
    color: #ccc;
}
</style>
