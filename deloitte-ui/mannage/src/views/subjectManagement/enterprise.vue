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
                截止今日起，平台已收集主体
                <span>{{ overviewData.count }}</span>个，其中：
                <div>
                  -上市主体 <span>{{ overviewData.list }}</span> 个， 发债主体
                  <span>{{ overviewData.bonds }}</span> 个
                </div>
                <div>
                  -即上市又发债主体 <span>{{ overviewData.listBonds }}</span> 个
                </div>
                <div>
                  -非上市非发债企业
                  <span>{{ overviewData.unListBonds }}</span> 个
                </div>
                <div>
                  -金融机构 <span>{{ overviewData.finance }}</span> 个
                </div>
              </div>
            </div>
            <div class="top-right">
              <div
                class="box1"
                :style="{
                  background: '#d8d8d8',
                  width: unListBonds + '%',
                  'text-align': 'center',
                  opacity: 0.5,
                }"
              >
                <el-tooltip
                  v-if="unListBonds < 5"
                  class="item"
                  effect="dark"
                  :content="'非上市非发债' + unListBonds + '%'"
                  placement="top-start"
                >
                  <div class="max" />
                </el-tooltip>
                <div v-else>
                  <div class="desc-center">
                    {{ unListBonds ? "非上市非发债" : "" }}
                  </div>
                  <div>{{ unListBonds ? unListBonds + "%" : "" }}</div>
                </div>
              </div>
              <div
                class="box2"
                :style="{
                  background: '#86bc25',
                  width: bondsList + '%',
                  'text-align': 'center',
                  opacity: 0.3,
                }"
              >
                <el-tooltip
                  v-if="bondsList < 5"
                  class="item"
                  effect="dark"
                  :content="'单纯上市' + bondsList + '%'"
                  placement="top-start"
                >
                  <div class="max" />
                </el-tooltip>
                <div v-else>
                  <div class="desc-center">
                    {{ bondsList ? "单纯上市" : "" }}
                  </div>
                  <div>{{ bondsList ? bondsList + "%" : "" }}</div>
                </div>
              </div>

              <div
                class="box2"
                :style="{
                  background: '#86bc25',
                  width: listBonds + '%',
                  'text-align': 'center',
                  opacity: 0.5,
                }"
              >
                <el-tooltip
                  v-if="listBonds < 5"
                  class="item"
                  effect="dark"
                  :content="'即上市又发债' + listBonds + '%'"
                  placement="top-start"
                >
                  <div class="max" />
                </el-tooltip>
                <div v-else>
                  <div class="desc-center">
                    {{ listBonds ? "即上市又发债" : "" }}
                  </div>
                  <div>{{ listBonds ? listBonds + "%" : "" }}</div>
                </div>
              </div>
              <div
                class="box2"
                :style="{
                  background: '#86bc25',
                  width: bonds + '%',
                  'text-align': 'center',
                  opacity: 0.3,
                }"
              >
                <el-tooltip
                  v-if="bonds < 5"
                  class="item"
                  effect="dark"
                  :content="'即上市又发债' + listBonds + '%'"
                  placement="top-start"
                >
                  <div class="max" />
                </el-tooltip>
                <div v-else>
                  <div class="desc-center">
                    {{ bonds ? "单纯发债" : "" }}
                  </div>
                  <div>{{ bonds ? bonds + "%" : "" }}</div>
                </div>
              </div>
            </div>
          </div>
          <div>
            <h3 class="g-title" style="margin-top: 10px">
              快速查询企业上市或发债情况
            </h3>
            <div class="select-body mt20">
              <el-input
                v-model="nameInput"
                class="mr10 ml20"
                style="width: 400px"
                placeholder="搜索主体名称 / 代码 / 统一社会信用代码"
              />
              <el-button
                class="mr10 green-btn"
                type="primary"
                @click="select"
              >查询</el-button>
            </div>
            <el-card class="mt20">
              <div class="font">查询结果</div>
              <div class="font">
                共查询到 <span>{{ total2 }}</span> 个结果
              </div>
              <el-table
                class="table-content"
                :data="list"
                style="width: 98%; margin-top: 20px"
                align="center"
              >
                <el-table-column fixed type="index" width="50" align="center" label="序号" />
                <el-table-column prop="date" align="center" label="德勤主体代码">
                  <template slot-scope="scope">
                    <div>{{ scope.row.entityInfo.entityCode }}</div>
                  </template>
                </el-table-column>
                <el-table-column prop="name" align="center" label="统一社会信用代码">
                  <template slot-scope="scope">
                    <div>{{ scope.row.entityInfo.creditCode }}</div>
                  </template>
                </el-table-column>
                <el-table-column prop="province" align="center" label="企业名称">
                  <template slot-scope="scope">
                    <div v-html="replaceFun(scope.row.entityInfo.entityName)" />
                  </template>
                </el-table-column>
                <el-table-column prop="city" align="center" label="上市情况">
                  <template slot-scope="scope">
                    <div :class="scope.row.listDetail ? 'green' : ''">{{ scope.row.listDetail || '未曾上市' }}</div>
                  </template>
                </el-table-column>
                <el-table-column prop="city" align="center" label="发债情况">
                  <template slot-scope="scope">
                    <div :class="scope.row.bondDetail ? 'green' : ''">{{ scope.row.bondDetail || '未曾发债' }}</div>
                  </template>
                </el-table-column>
              </el-table>
              <pagination
                v-show="total2 > 0"
                :total="total2"
                :page.sync="queryParams2.pageNum"
                :limit.sync="queryParams2.pageSize"
                @pagination="getList2"
              />
            </el-card>
          </div>
        </el-card>
      </el-col>
      <h3 class="g-title" style="margin-top: 10px">企业主体清单</h3>
      <div class="g-tab flex1">
        <a
          :class="currentTab === '1' ? 'g-select' : ''"
          @click="changeTab('1')"
        >上市</a>
        <a
          :class="currentTab === '2' ? 'g-select' : ''"
          @click="changeTab('2')"
        >发债</a>
        <a
          :class="currentTab === '3' ? 'g-select' : ''"
          @click="changeTab('3')"
        >非上市非发债</a>
        <a
          :class="currentTab === '4' ? 'g-select' : ''"
          @click="changeTab('4')"
        >金融机构</a>
      </div>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <h3 class="g-t-title">{{ tabArr[currentTab] + "企业" }}</h3>
          <div class="g-desc">
            截止日期，合计收录 <span>{{ overview.totle || 0 }}</span> 个地方企业主体，其中
            <span>{{ overview.live || 0 }}</span> 个存续上市， <span>{{ overview.dead || 0 }}</span> 个已退市
          </div>
          <div class="g-desc flex1">
            <router-link
              :to="{
                name: '/subjectManagement/indexEnterprise',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">更多指标</a>
            </router-link>
            <router-link
              :to="{
                name: '/subjectManagement/addEnterprise',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">新增主体</a>
            </router-link>
            <router-link :to="{ name: '/subjectManagement/eidtEnterprise' }">
              <a href="">修改信息</a>
            </router-link>
            <router-link
              :to="{
                name: '/subjectManagement/historyEnterprise',
                query: { name: tabArr[currentTab] },
              }"
            >
              <a href="">更新记录</a>
            </router-link>
            <a style="text-decoration:underline;" @click="exportEntity">导出上市主体清单</a>
            <el-input
              v-model="codeInput"
              class="select-x"
              placeholder="搜索主体名称/代码/统一社会信用代码"
              @change="selectList"
            />
          </div>
          <el-table
            class="table-content"
            :data="list2"
            :height="500"
            align="center"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column prop="name" label="上市存续" width="80">
              <template slot-scope="scope">
                <span
                  :class="scope.row.liveState === 'Y' ? 'green' : 'red'"
                >{{
                  scope.row.liveState || "暂无"
                }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="entityCode" label="德勤主体代码" />
            <el-table-column prop="liveBondDetail" label="证券代码">
              <template slot-scope="scope">
                <span>{{ scope.row.stockCode && scope.row.stockCode.toString() }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="entityName" label="主体名称" width="200" />
            <el-table-column prop="nameUsedNum" label="曾用名或别称">
              <template slot-scope="scope">
                <a
                  :class="scope.row.nameUsedNum > 0 ? 'green' : ''"
                  style="text-decoration:underline"
                  @click="checkName(scope.row)"
                >{{
                  scope.row.nameUsedNum > 0 ? scope.row.nameUsedNum : "暂无"
                }}</a>
              </template>
            </el-table-column>
            <el-table-column prop="date" label="上市日期">
              <template slot-scope="scope">
                <span>{{ scope.row.listDate && scope.row.listDate.toString() }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="date" label="退市日期">
              <template slot-scope="scope">
                <span>{{ scope.row.downDate ? scope.row.downDate.toString() : '未曾退市' }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="zip" label="更新记录" />
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
    <el-dialog
      title="曾用名-或别称管理"
      :visible.sync="dialogVisible"
      width="45%"
    >
      <div>{{ selectName }}</div>
      <div>{{ selectCode }}</div>
      <el-table
        class="table-content"
        :data="usedData"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column fixed type="index" width="50" align="center" label="序号" />
        <el-table-column prop="oldName" align="center" label="曾用名或别称" width="200" />
        <el-table-column prop="remarks" align="center" label="添加详情" />
        <el-table-column prop="created" align="center" label="添加时间" />
        <el-table-column prop="created" align="center" label="操作">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="stopUsed"
            >停用</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button
        type="text"
        size="small"
        @click="addUsed"
      >添加别称</el-button>
    </el-dialog>
    <el-dialog
      title="添加别称"
      :visible.sync="addUsedDig"
      width="40%"
    >
      <div>{{ selectName }}</div>
      <div>{{ selectCode }}</div>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="曾用名或别称">
          <el-input v-model="form.entityName" />
        </el-form-item>
        <el-form-item label="新增备注">
          <el-input v-model="form.entityNameHisRemarks" type="textarea" />
        </el-form-item>
        <el-form-item label="更名日期">
          <el-date-picker v-model="form.updated" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item>
          <el-button class="green-btn" type="primary" @click="subAddUsed">立即创建</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  getOverviewByGroup,
  getInfoList,
  getQuickOfCoverage,
  getListView,
  getNameListByDqCoded,
  updateOldName,
  addOldName,
  exportEntityByType
} from '@/api/subject'
import { replaceStr, download } from '@/utils/index'
import pagination from '../../components/Pagination'
export default {
  name: 'Government',
  components: {
    pagination
  },
  data() {
    return {
      codeInput: '',
      nameInput: '',
      activeName: 'frist',
      tabArr: {
        1: '上市',
        2: '发债',
        3: '非上市非发债',
        4: '金融机构'
      },
      list2: [],
      list: [],
      loading: false,
      currentTab: '1',
      currentPage4: 4,
      overviewData: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      queryParams2: {
        pageNum: 1,
        pageSize: 10
      },
      total2: 0,
      overview: {},
      usedData: [],
      dialogVisible: false,
      addUsedDig: false,
      selectCode: '',
      selectName: '',
      form: {
        entityName: '',
        entityNameHisRemarks: '',
        updated: ''
      }
    }
  },
  computed: {
    sumCount() {
      return (
        this.overviewData.unListBonds +
        this.overviewData.bonds +
        this.overviewData.bonds +
        this.overviewData.list +
        this.overviewData.listBonds
      )
    },
    unListBonds() {
      const res = (
        (this.overviewData.unListBonds / this.overviewData.count) *
        100
      ).toFixed(2)
      return res
    },
    bonds() {
      const res = ((this.overviewData.onlyList / this.overviewData.count) * 100).toFixed(2)
      return res
    },
    bondsList() {
      const res = ((this.overviewData.onlyBonds / this.overviewData.count) * 100).toFixed(2)
      return res
    },
    listBonds() {
      const res = ((this.overviewData.listBonds / this.overviewData.count) * 100).toFixed(
        2
      )
      return res
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      try {
        this.$modal.loading('Loading...')
        getOverviewByGroup({}).then((res) => {
          const { data } = res
          this.overviewData = data
        })
        const parmas = {
          param: '',
          type: this.currentTab,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        }
        getInfoList(parmas).then((res) => {
          const { data } = res
          this.list2 = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
        })

        getListView({ type: this.currentTab }).then(res => {
          const { data } = res
          this.overview = data
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    changeTab(tab) {
      try {
        this.$modal.loading('Loading...')
        this.currentTab = tab
        this.queryParams.pageNum = 1
        const parmas = {
          param: this.codeInput,
          type: this.currentTab,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        }
        getInfoList(parmas).then((res) => {
          const { data } = res
          this.list2 = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
        })
        getListView({ type: this.currentTab }).then(res => {
          const { data } = res
          this.overview = data
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    add() {
      this.$router.psuh({ path: 'subjectManagement/enterprise' })
    },
    select() {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          param: this.nameInput,
          pageNum: this.queryParams2.pageNum,
          pageSize: this.queryParams2.pageSize
        }
        getQuickOfCoverage(parmas).then((res) => {
          const { data } = res
          this.list = data.records
          this.total2 = data.total
          this.queryParams2.pageNum = data.current
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    checkName(row) {
      console.log(row)
      try {
        this.dialogVisible = true
        this.$modal.loading('Loading...')
        const parmas = {
          dqCode: row.entityCode
        }
        this.selectCode = row.entityCode
        this.selectName = row.entityName
        getNameListByDqCoded(parmas).then((res) => {
          const { data } = res
          this.usedData = data
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    stopUsed(row) {
      try {
        this.dialogVisible = true
        this.$modal.loading('Loading...')
        const parmas = {
          dqCode: this.selectCode,
          status: 1
        }
        updateOldName(parmas).then((res) => {
          this.dialogVisible = false
          this.$message({
            message: '操作成功',
            type: 'success'
          })
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    addUsed() {
      try {
        this.addUsedDig = true
        this.$modal.loading('Loading...')
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    subAddUsed() {
      try {
        this.addUsedDig = true
        this.$modal.loading('Loading...')
        this.form.entityCode = this.selectCode
        addOldName(this.form).then(res => {
          this.addUsedDig = false
          this.$message({
            message: '操作成功',
            type: 'success'
          })
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },

    replaceFun(row) {
      return replaceStr(row, this.nameInput)
    },
    getList() {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          param: this.codeInput,
          type: this.currentTab,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        }
        getInfoList(parmas).then((res) => {
          const { data } = res
          this.list2 = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    getList2() {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          param: this.nameInput,
          type: this.currentTab,
          pageNum: this.queryParams2.pageNum,
          pageSize: this.queryParams2.pageSize
        }
        getQuickOfCoverage(parmas).then((res) => {
          const { data } = res
          this.list = data.records
          this.total2 = data.total
          this.queryParams2.pageNum = data.current
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    selectList() {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          param: this.codeInput,
          type: this.currentTab,
          pageNum: 1,
          pageSize: this.queryParams.pageSize
        }
        getInfoList(parmas).then((res) => {
          const { data } = res
          this.list2 = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    exportEntity() {
      try {
        this.$modal.loading('Loading...')
        exportEntityByType({ type: this.currentTab }).then((res) => {
          download(res, '企业主体清单.xlsx')
          this.$modal.closeLoading()
        })
      } catch (error) {
        console.log(error)
        this.$modal.closeLoading()
      }
    }
  }
}
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
</style>
