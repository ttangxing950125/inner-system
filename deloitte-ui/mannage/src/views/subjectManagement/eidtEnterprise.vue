<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button
        class="back"
        type="text"
        @click="back()"
      >返回企业主体首页</el-button>
      <h3 class="title">上市企业-修改信息</h3>
    </div>
    <div class="g-tab flex1">
      <a
        :class="currentTab === '逐一修改' ? 'g-select' : ''"
        @click="changeTab('逐一修改')"
      >逐一修改</a>
      <a
        style="cursor: not-allowed"
      >批量修改</a>
    </div>
    <el-row>
      <el-col :sm="24" :lg="24" class="form-card">
        <el-card class="box-card">
          <div class="select-body mt20">
            <el-input
              v-model="input"
              class="mr10"
              style="width: 80%;"
              placeholder="请输入内容"
            />
            <el-button
              class="mr10 green-btn"
              type="primary"
              @click="select"
            >查询</el-button>
          </div>
          <h3 class="g-title">查询结果</h3>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 20px"
          >
            <el-table-column type="index" label="序号" width="50" />
            <el-table-column prop="date" label="德勤主体代码">
              <template slot-scope="scope">
                <span>{{
                  scope.row.entityInfo && scope.row.entityInfo.entityCode
                }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="企业名称">
              <template slot-scope="scope">
                <div v-html="replaceFun(scope.row.entityInfo.entityName)" />
              </template>
            </el-table-column>
            <el-table-column prop="province" label="统一社会信用代码">
              <template slot-scope="scope">
                <span>{{ scope.row.entityInfo.creditCode || "-" }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="city" label="证券代码">
              <template slot-scope="scope">
                <span>{{
                  scope.row.entityInfo.stockCode
                    ? scope.row.entityInfo.stockCode.toString()
                    : "-"
                }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  v-if="!scope.row.edit"
                  type="text"
                  size="small"
                  @click="handleClick(scope.row)"
                >进入修改</el-button>
                <span v-else class="green">修改中</span>
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
      <div class="clearf">
        <div class="bottom-title">
          <span class="g-title">目标企业信息</span>
          <span v-if="clickEdit" class="s-2">修改中</span>
        </div>
        <el-button type="text" @click="submit">提交变更</el-button>
      </div>
      <el-col
        :sm="24"
        :lg="24"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <span
            style="color: red"
          >注意：带星号字段需同故宫附表维护更新，请进入附表管理模块进行修改</span>
          <el-collapse v-model="activeNames" accordion class="collpase">
            <el-collapse-item v-if="info.entityInfo" name="info">
              <template slot="title">
                <span style="font-size: 16px">基本信息</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">德勤主体代码（企业）</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityInfo.entityCode
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first" :class="edit.creditCode ? 'green' : ''">统一社会信用代码</div>
                  <el-input
                    v-model="info.entityInfo.creditCode"
                    class="t-input"
                    @change="edit.creditCode = true"
                  />
                </div>
                <div class="flex1">
                  <div class="first">统一社会信用代码是否异常</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityInfo.creditError === 0
                        ? "正常"
                        : "异常"
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">统一社会信用代码异常备注</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{ info.entityInfo.creditErrorRemark }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first" :class="edit.listType ? 'green' : ''">年报列示类型</div>
                  <el-radio-group v-model="info.entityInfo.listType" class="mt10" @change="edit.listType = true">
                    <el-radio label="1">一般企业</el-radio>
                    <el-radio label="0">金融机构</el-radio>
                  </el-radio-group>
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first" :class="edit.entityName ? 'green' : ''">企业名称</div>
                  <el-input
                    v-model="info.entityInfo.entityName"
                    class="t-input"
                    @change="edit.entityName = true"
                  />
                </div>
                <div class="flex1">
                  <div class="first">企业曾用名</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{ info.entityInfo.entityNameHis }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">企业更名日期</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{ info.entityInfo.updated }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.stockCnInfo" name="stockCnInfo">
              <template slot="title">
                <span style="font-size: 16px">上市情况</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">是否上市</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">上市类型汇总</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.listType || '-'
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">A股上市状态</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.listTypeA || '-'
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">A股上市交易所</div>
                  <el-input
                    v-model="info.stockCnInfo.exchange"
                    class="t-input"
                    :class="edit.exchange ? 'green' : ''"
                    @change="edit.exchange = true"
                  />
                </div>
                <div class="flex1">
                  <div class="first">A股股票代码</div>
                  <el-input
                    v-model="info.stockCnInfo.stockCode"
                    class="t-input"
                    :class="edit.stockCode ? 'green' : ''"
                    @change="edit.stockCode = true"
                  />
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">A股上市日期</div>
                  <el-input
                    v-model="info.stockCnInfo.listDate"
                    class="t-input"
                    :class="edit.listDate ? 'green' : ''"
                    @change="edit.listDate = true"
                  />
                </div>
                <div class="flex1">
                  <div class="first">A股退市日期</div>
                  <el-input
                    v-model="info.stockCnInfo.delistingDate"
                    class="t-input"
                    :class="edit.delistingDate ? 'green' : ''"
                    @change="edit.delistingDate = true"
                  />
                </div>
                <div class="flex1">
                  <div class="first">A股证券简称</div>
                  <div class="scond" style="color: #updated">
                    {{ info.stockCnInfo.stockShortName }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">A股曾用证券简称</div>
                  <div class="scond" style="color: #updated">
                    {{ info.stockCnInfo.shortNameHisA && info.stockCnInfo.shortNameHisA.join() }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">A股证券简称变更日期</div>
                  <div class="scond" style="color: #updated">
                    {{ info.stockCnInfo.updated }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.bondInfoDetail" name="bondInfoDetail">
              <template slot="title">
                <span style="font-size: 16px">发债情况</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">是否发债（含历史）</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.bondInfoDetail && (info.bondInfoDetail.isBond ? 'Y' : 'N')
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">是否可以收数</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.bondInfoDetail && info.bondInfoDetail.isCollection === '1' ? 'Y' : 'N'
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">首次发债时间</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.firstBond }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">是否发行集合债</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail && info.bondInfoDetail.isCollBond ? 'Y' : 'N' }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行集合债明细</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.collBonds && info.bondInfoDetail.collBonds.join() }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">存续集合债数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.collBondsLiveNum }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">是否发行ABS</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.isAbsBond ? 'Y' : 'N' }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行ABS 明细</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.absBonds && info.bondInfoDetail.absBonds.join() }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行ABS 数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.absBondsNum }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">存续ABS 数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.absBondsLiveNum }}
                  </div>
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">是否发行公募债</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.isPublicBond ? 'Y' : 'N' }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行公募债明细</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.publicBonds && info.bondInfoDetail.publicBonds.join() }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行公募债数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.publicBondsNum }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">存续公募债数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.publicBondsLiveNum }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">是否发行私募债</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.isPrivateBond ? 'Y' : 'N' }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行私募债明细</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.privateBonds && info.bondInfoDetail.privateBonds.join() }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">发行私募债数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.privateBondsNum }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">存续私募债数量</div>
                  <div class="scond" style="color: #updated">
                    {{ info.bondInfoDetail.privateBondsLiveNum }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.entityInfo" name="entityInfo">
              <template slot="title">
                <span style="font-size: 16px">金融机构</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">是否为金融机构</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityInfo.finance === 1 ? 'Y' : 'N'
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">所处细分行业</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityFinancial && info.entityFinancial.mince
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">对口监管机构</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityFinancial && info.entityFinancial.regulators
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">是否发行同业存单（银行）</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityFinancial && info.entityFinancial.isIssPeerDep === 1 ? 'Y' : 'N'
                    }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item v-if="info.entityInfo" name="ck">
              <template slot="title">
                <span style="font-size: 16px">敞口划分</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">申万（2021）行业划分明细</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityInfo.shenWanMaster
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">wind行业划分明细</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityInfo.windMaster
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">旧辖口行业划分</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
              </el-col>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">客户敞口行业划分汇集</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.masterNames && info.masterNames.join()
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">产业链CICS行业划分明细</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityInfo.cicsIndustryDetails
                    }}
                  </div>
                </div>
              </el-col>
            </el-collapse-item>
            <el-collapse-item>
              <template slot="title">
                <span style="font-size: 16px">产品覆盖情况</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div v-for="(item, index) in info.coverageDetail" :key="index">
                  <div class="flex1">
                    <div class="first">{{ item.isCover.name }}</div>
                    <div class="scond" style="color: #a7a7a7">
                      {{
                        item.isCover.value === 1 ? '是' : '否'
                      }}
                    </div>
                  </div>
                  <div class="flex1">
                    <div class="first">{{ item.coverReason.name }}</div>
                    <div class="scond" style="color: #a7a7a7">
                      {{
                        item.coverReason.value === 1 ? '是' : '否'
                      }}
                    </div>
                  </div>
                </div>
              </el-col>
              <!-- <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">客户敞口行业划分汇集</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">产业链CICS行业划分明细</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">产业链是否覆盖</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">产业链未覆盖原因</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">ESC是否覆盖</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">ESC未覆盖原因</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.stockCnInfo && levelStr[info.stockCnInfo.govType]
                    }}
                  </div>
                </div>
              </el-col> -->
            </el-collapse-item>
            <el-collapse-item v-if="info.entityBaseBusiInfo" name="entityBaseBusiInfo">
              <template slot="title">
                <span style="font-size: 16px">其他一般工商信息</span>
              </template>
              <el-col :sm="24" :lg="12" class="form-card">
                <div class="flex1">
                  <div class="first">注册地址</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.regAddr
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">注册地所在省</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.regProvince
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">法人名称</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.ceoName
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">法人类型</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.ceoType
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">公司类型</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.comType
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">成立日期</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.establishDate
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">营业期限开始日期</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.busStartDate
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">营业期限截止日期</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.busEndDate
                    }}
                  </div>
                </div>
                <div class="flex1">
                  <div class="first">营业范围</div>
                  <div class="scond" style="color: #a7a7a7">
                    {{
                      info.entityBaseBusiInfo.busRange
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
  getQuickOfCoverage,
  getInfoDetail,
  updateInfoDetail
} from '@/api/subject'
import pagination from '../../components/Pagination'
import { replaceStr } from '@/utils/index'
export default {
  name: 'EidtGovernment',
  components: {
    pagination
  },
  data() {
    return {
      input: '',
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      currentTime: '',
      list: [],
      currentTab: '逐一修改',
      info: {
        stockCnInfo: {}
      },
      levelStr: {

      },
      activeNames: 'info',
      clickEdit: false,
      edit: {
        creditCode: false,
        listType: false,
        entityName: false,
        exchange: false,
        stockCode: false,
        listDate: false,
        delistingDate: false
      }
    }
  },
  created() {
    this.getCurrentTime()
  },
  methods: {
    replaceFun(row) {
      return replaceStr(row, this.input)
    },
    handleClick(row) {
      try {
        this.$modal.loading('Loading...')
        this.list.forEach(e => {
          e.edit = false
        })
        row.edit = true
        this.clickEdit = true
        const code = row.entityInfo.entityCode
        getInfoDetail({ entityCode: code }).then((res) => {
          const { data } = res
          this.info = data
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    getCurrentTime() {
      // 获取当前时间并打印
      const yy = new Date().getFullYear()
      const mm = new Date().getMonth() + 1
      const dd = new Date().getDate()
      const hh = new Date().getHours()
      const mf =
        new Date().getMinutes() < 10
          ? '0' + new Date().getMinutes()
          : new Date().getMinutes()
      const ss =
        new Date().getSeconds() < 10
          ? '0' + new Date().getSeconds()
          : new Date().getSeconds()
      // eslint-disable-next-line no-unused-vars
      this.currentTime =
        yy + '-' + mm + '-' + dd + ' ' + hh + ':' + mf + ':' + ss
    },
    check(name) {
      this.dialogVisible = true
    },
    changeTab(tab) {
      this.currentTab = tab
    },
    back() {
      this.$router.back()
    },
    select() {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          param: this.input,
          pageNum: 1,
          pageSize: this.queryParams.pageSize
        }
        getQuickOfCoverage(parmas).then((res) => {
          const { data } = res
          this.list = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    getList() {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          param: this.input,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        }
        getQuickOfCoverage(parmas).then((res) => {
          const { data } = res
          this.list = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    submit() {
      try {
        this.$modal.loading('Loading...')
        // const entityInfoDetails = {
        //   entityInfo: this.info.entityInfo,
        //   stockCnInfo: {
        //     stockDqCode: this.info.stockDqCode,
        //   },
        //   stockThkInfo: {
        //     stockDqCode: this.info.stockDqCode,
        //   },
        // };
        updateInfoDetail(this.info).then((res) => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success'
            })
            this.getList()
            this.info = {}
          }
        })
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: 'error'
        })
      } finally {
        this.$modal.closeLoading()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.t-input {
  width: 200px;
  margin-top: 5px;
  ::v-deep .el-input__inner{
      height: 30px;
  }
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
.green {
    color: #86bc25;
    font-size: 12px;
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
</style>
