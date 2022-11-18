<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button
        class="back"
        type="text"
        @click="back()"
      >返回企业主体首页</el-button>
      <h3 class="title">{{ tab }}企业-新增主体</h3>
    </div>
    <el-row>
      <!-- <el-col :sm="24" :lg="23" class="form-card">
        <el-card>
          <div class="mt10">今日新{{ tab }}</div>
          <div v-if="tab === '上市'" class="mt10">
            今日新上市主体
            <span>{{ infomation.addToday || 0 }}</span>
            个，最近七个交易日平均每日新上市主体
            <span>{{ infomation.sevenTradingDayAverages || 0 }}</span>
            个，最近一月平均每日新上市主体
            <span>{{ infomation.averageDailyLatestMonth || 0 }}</span> 个
          </div>
          <div v-if="tab === '发债'" class="mt10">
            今日新发债主体 <span>{{ infomation.addToday || 0 }}</span> 个
          </div>
          <div class="mt20" id="xchart" style="height: 240px; width: 100%" />
          <div v-show="none" class="mt20 none">
              暂无数据
          </div>
        </el-card>
      </el-col> -->
      <el-col
        :sm="24"
        :lg="23"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <div class="flex1">
            <h3 class="g-t-title">近期更新记录</h3>
            <el-button
              type="text"
              style="margin-left: 15px; margin-top: 3px"
              @click="editData()"
            >手动新增</el-button>
          </div>
          <el-table
            class="table-content"
            :data="infomation.entityInfoLogs"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column prop="createTime" align="center" label="时间">
              <template slot-scope="scope">
                {{ scope.row.createTime || '-' }}
              </template>
            </el-table-column>
            <el-table-column prop="operName" label="操作人" align="center" />
            <el-table-column prop="code" label="证券代码" align="center" />
            <el-table-column prop="name" label="证券简称" align="center">
              <template slot-scope="scope">
                {{ scope.row.name || '-' }}
              </template>
            </el-table-column>
            <el-table-column prop="entityCode" label="德勤主体代码" align="center" />
            <el-table-column prop="remarks" label="详细信息" align="center">
              <template slot-scope="scope">
                {{ scope.row.remarks || '-' }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100" align="center">
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.operType === 1"
                  type="text"
                  size="small"
                  @click="handleClick(scope.row)"
                >撤销停用</el-button>
                <span v-else>-</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :title="digName"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="新增日期">
          <span>{{ currentTime }}</span>
        </el-form-item>
        <el-form-item label="新增操作人">
          <span>{{ currentName }}</span>
        </el-form-item>
        <el-form-item label="IB敞口划分" prop="masterCode">
          <el-select v-model="ruleForm.masterCode" placeholder="选择新增类型">
            <el-option v-for="(item, index) in options" :key="index" :label="item.masterName" :value="item.masterCode" />
          </el-select>
        </el-form-item>
        <el-form-item label="企业股票类型" prop="stockType">
          <el-select v-model="ruleForm.stockType" placeholder="请选择股票类型">
            <el-option label="A股" value="A" />
            <el-option label="港股" value="G" />
          </el-select>
        </el-form-item>
        <el-form-item label="企业股票代码" prop="stockCode">
          <div class="flex1">
            <el-input
              v-model="ruleForm.stockCode"
              class="t-input"
              placeholder="请输入企业完整证券代码"
              @change="repalce1 = false"
            />
            <span v-if="repalce1 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce1 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce1"
              style="margin-left: 5px"
              type="text"
              @click="
                check(
                  ruleForm.stockCode,
                  ruleForm.stockType === 'A' ? 'STOCK_CN_CODE' : 'STOCK_HK_CODE'
                )
              "
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="企业股票简称" prop="stockShortName">
          <div class="flex1">
            <el-input
              v-model="ruleForm.stockShortName"
              class="t-input"
              placeholder="请输入企业证券简称"
              @change="repalce2 = false"
            />
            <span v-if="repalce2 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce2 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce2"
              style="margin-left: 5px"
              type="text"
              @click="
                check(
                  ruleForm.stockShortName,
                  ruleForm.stockType === 'A' ? 'STOCK_A_NAME' : 'STOCK_HK_NAME'
                )
              "
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="企业全称" prop="entityName">
          <div class="flex1">
            <el-input
              v-model="ruleForm.entityName"
              class="t-input"
              placeholder="请输入企业完整名称"
              @change="repalce4 = false"
            />
            <span v-if="repalce4 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce4 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce4"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.entityName, 'ENTITY_NAME')"
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="creditCode">
          <div class="flex1">
            <el-input
              v-model="ruleForm.creditCode"
              class="t-input"
              :disabled="ruleForm.notUse"
              placeholder="请输入企业统一社会信用代码"
              @change="repalce5 = false"
            />
            <span v-if="repalce5 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce5 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce5"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.creditCode, 'CREDIT_CODE')"
            >查重</el-button>
          </div>
        </el-form-item>
        <div class="notUse">
          <el-checkbox
            v-model="ruleForm.notUse"
            class="mr60"
            label="1"
          >不适用</el-checkbox>
          <span class="mr10">不适用原因</span>
          <el-select
            v-model="ruleForm.creditErrorRemark"
            :disabled="!ruleForm.notUse"
            placeholder="请选择"
          >
            <el-option
              v-for="item in notUseoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </div>

        <el-form-item v-if="ruleForm.stockType" label="上市板块与交易所" prop="stockType">
          <el-select
            v-if="ruleForm.stockType === 'A'"
            v-model="ruleForm.exchange"
            placeholder="选择股票交易所"
          >
            <el-option label="深交所" value="深交所" />
            <el-option label="沪交所" value="沪交所" />
            <el-option label="北交所" value="北交所" />
          </el-select>
          <el-select
            v-if="ruleForm.stockType === 'G'"
            v-model="ruleForm.exchange"
            placeholder="选择股票交易所"
          >
            <el-option label="港交所" value="港交所" />
          </el-select>
        </el-form-item>
        <el-form-item label="上市日期" prop="startXiDate">
          <el-date-picker
            v-model="ruleForm.startXiDate"
            type="date"
            class="t-input"
            placeholder="yyyy-mm-dd"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item label="退市日期" :prop="ruleForm.notUse1 ? '' : 'endDate'">
          <el-date-picker
            v-model="ruleForm.endDate"
            type="date"
            class="t-input"
            placeholder="yyyy-mm-dd"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            :disabled="ruleForm.notUse1"
          />
          <el-checkbox
            v-model="ruleForm.notUse1"
            class="ml10"
            label="1"
          >不适用</el-checkbox>
        </el-form-item>
        <el-form-item label="是否为金融机构" prop="finance">
          <el-radio-group v-model="ruleForm.finance">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年报列示类型" prop="anRportType">
          <el-radio-group v-model="ruleForm.anRportType">
            <el-radio label="1">一般企业</el-radio>
            <el-radio label="0">金融机构</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="position-add" label="企业曾用名或别称" prop="entityNameHis">
          <el-input
            v-model="ruleForm.entityNameHis"
            class="t-input"
            style="width: 320px"
            placeholder="请输入企业曾用名或别称，以顿号区分"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="green-btn" @click="subForm">确认新增</el-button>
      </span>
    </el-dialog>
    <!-- 债券新增主体 -->
    <el-dialog
      title="手动新增企业主体"
      :visible.sync="dialogVisible2"
      width="50%"
      :before-close="handleClose"
    >
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="新增日期">
          <span>{{ currentTime }}</span>
        </el-form-item>
        <el-form-item label="新增操作人">
          <span>{{ currentName }}</span>
        </el-form-item>
        <el-form-item label="企业债券全称" prop="bondName">
          <div class="flex1">
            <el-input
              v-model="ruleForm.bondName"
              class="t-input"
              placeholder="请输入企业债券全称"
              @change="repalce1 = false"
            />
            <span v-if="repalce1 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce1 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce1"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.bondName, 'BOND_FULL_NAME')"
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="企业债券代码" prop="stockCode">
          <div class="flex1">
            <el-input
              v-model="ruleForm.stockCode"
              class="t-input"
              placeholder="请输入企业债券代码"
              @change="repalce2 = false"
            />
            <span v-if="repalce2 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce2 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce2"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.stockCode, 'BOND_CODE')"
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="企业债券简称" prop="bondShortName">
          <div class="flex1">
            <el-input
              v-model="ruleForm.bondShortName"
              class="t-input"
              placeholder="请输入企业债券简称"
              @change="repalce3 = false"
            />
            <span v-if="repalce3 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce3 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce3"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.bondShortName, 'BOND_SHORT_NAME')"
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="企业全称" prop="entityName">
          <div class="flex1">
            <el-input
              v-model="ruleForm.entityName"
              class="t-input"
              placeholder="请输入企业完整名称"
              @change="repalce4 = false"
            />
            <span v-if="repalce4 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce4 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce4"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.entityName, 'ENTITY_NAME')"
            >查重</el-button>
          </div>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="creditCode">
          <div class="flex1">
            <el-input
              v-model="ruleForm.creditCode"
              class="t-input"
              :disabled="!disabeld"
              placeholder="请输入企业统一社会信用代码"
              @change="repalce5 = false"
            />
            <span v-if="repalce5 === 2" class="red">存在重复无法添加</span>
            <span v-if="repalce5 === 1" class="green">无重复，可新增</span>
            <el-button
              v-if="!repalce5"
              style="margin-left: 5px"
              type="text"
              @click="check(ruleForm.creditCode, 'CREDIT_CODE')"
            >查重</el-button>
          </div>
        </el-form-item>
        <div class="notUse">
          <el-checkbox
            v-model="ruleForm.notUse"
            class="mr60"
            label="1"
          >不适用</el-checkbox>
          <span class="mr10">不适用原因</span>
          <el-select
            v-model="ruleForm.creditErrorRemark"
            :disabled="disabeld"
            placeholder="请选择"
          >
            <el-option
              v-for="item in notUseoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </div>
        <el-form-item label="起息日" prop="startXiDate">
          <el-date-picker
            v-model="ruleForm.startXiDate"
            type="date"
            class="t-input"
            placeholder="yyyy-mm-dd"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item label="到期日" prop="endDate">
          <el-date-picker
            v-model="ruleForm.endDate"
            type="date"
            class="t-input"
            placeholder="yyyy-mm-dd"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item label="债券类型" prop="bondType">
          <el-col :span="11">
            <el-form-item prop="">
              <el-select v-model="ruleForm.bondType" placeholder="选择债券类型">
                <el-option label="公募" value="0" />
                <el-option label="私募" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="是否为金融机构" prop="finance">
          <el-radio-group v-model="ruleForm.finance">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年报列示类型" prop="anRportType">
          <el-radio-group v-model="ruleForm.anRportType">
            <el-radio label="1">一般企业</el-radio>
            <el-radio label="0">金融机构</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="position-add" label="企业曾用名或别称" prop="entityNameHis">
          <el-input
            v-model="ruleForm.entityNameHis"
            class="t-input"
            style="width: 320px"
            placeholder="请输入企业曾用名或别称，以顿号区分"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="green-btn" @click="subForm(2)">确认新增</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { createST, createBE } from '@/api/bond'
import { entityInfoLogsList, logCancel } from '@/api/subject'
import { getModelMaster } from '@/api/task'
import { checkData, getFinanceSubIndu } from '@/api/common'
export default {
  name: 'AddGovernment',
  data() {
    return {
      dialogVisible2: false,
      noUse2: '',
      noUse1: '',
      list: [],
      dialogVisible: false,
      ruleForm: {
        creditCode: '',
        endDate: ''
      },
      rules: {
        masterCode: [
          { required: true, message: '请输入IB敞口划分', trigger: 'blur' }
        ],
        stockType: [
          { required: true, message: '请选择企业股票类型', trigger: 'blur' }
        ],
        stockCode: [
          { required: true, message: '请输入企业股票代码', trigger: 'blur' }
        ],
        stockShortName: [
          { required: true, message: '请输入企业股票简称', trigger: 'blur' }
        ],
        entityName: [
          { required: true, message: '请输入企业名称', trigger: 'blur' }
        ],
        creditCode: [
          { required: true, message: '请输入企业统一社会信用代码', trigger: 'blur' }
        ],
        stockType: [
          { required: true, message: '请选择上市板块与交易所', trigger: 'blur' }
        ],
        startXiDate: [
          { required: true, message: '请选择日期', trigger: 'blur' }
        ],
        endDate: [
          { required: true, message: '请选择日期', trigger: 'blur' }
        ],
        finance: [
          { required: true, message: '请选择是否为金融机构', trigger: 'blur' }
        ],
        anRportType: [
          { required: true, message: '请选择年报列示类型', trigger: 'blur' }
        ],
        financeSubIndu: [
          { required: true, message: '请选择金融机构子行业', trigger: 'blur' }
        ],
        entityNameHis: [
          { required: true, message: '请输入企业曾用名或别称', trigger: 'blur' }
        ],
        bondName: [
          { required: true, message: '请输入企业债券全称', trigger: 'blur' }
        ],
        bondShortName: [
          { required: true, message: '请输入企业债券简称', trigger: 'blur' }
        ],
        bondType: [
          { required: true, message: '请选择债券类型', trigger: 'blur' }
        ]
      },
      currentTime: '',
      tab: this.$route.query.name,
      currentName: this.$store.state.user.name,
      notUseoptions: [
        {
          value: 1,
          label: '吊销'
        },
        {
          value: 2,
          label: '注销'
        },
        {
          value: 3,
          label: '非大陆注册机构'
        },
        {
          value: 4,
          label: '其他未知原因'
        }
      ],
      repalce1: false,
      repalce2: false,
      repalce3: false,
      repalce4: false,
      repalce5: false,
      disabeld: true,
      financeSubIndu: [],
      infomation: {},
      options: [],
      digName: '',
      none: false
    }
  },
  watch: {
    'ruleForm.notUse': {
      handler(newName, oldName) {
        if (newName) {
          this.ruleForm.creditCode = ''
          this.$refs['ruleForm'].clearValidate()
        } else {
          this.ruleForm.creditErrorRemark = ''
        }
      },
      deep: true // 为true，表示深度监听，这时候就能监测到a值变化
    },
    'ruleForm.notUse1': {
      handler(newName, oldName) {
        if (newName) {
          this.ruleForm.endDate = ''
          this.$refs['ruleForm'].clearValidate()
        }
      },
      deep: true // 为true，表示深度监听，这时候就能监测到a值变化
    }
  },
  mounted() {
    this.getCurrentTime()
    this.init()
  },
  methods: {
    init() {
      try {
        this.$modal.loading('Loading...')
        const type = this.tab === '发债' ? 'TYPE_BOND' : 'TYPE_STOCK'
        this.digName = this.tab === '发债' ? '手动新增发债主体' : '手动新增企业主体'
        entityInfoLogsList({ type: type }).then((res) => {
          this.infomation = res.data
          const keys = Object.keys(this.infomation.cylinderDatas)
          const values = Object.values(this.infomation.cylinderDatas)
          this.none = keys.length > 0
          this.xEcharts(keys, values)
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
    goTarget(href) {
      window.open(href, '_blank')
    },
    handleClick(row) {
      try {
        this.$modal.loading('Loading...')
        logCancel({ id: row.id }).then((res) => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success'
            })
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
    },
    back() {
      this.$router.back()
    },
    editData() {
      if (this.tab === '发债') {
        this.dialogVisible2 = true
      } else {
        this.dialogVisible = true
      }
      getModelMaster({ attrId: 656 }).then((res) => {
        const { data } = res
        this.options = data
      })
      getFinanceSubIndu({ attrId: 656 }).then((res) => {
        const { data } = res
        this.financeSubIndu = data
      })
    },
    xEcharts(keys, values) {
      const myChart = echarts.init(document.getElementById('xchart'))
      const option = {
        title: {
          text: this.tab === '上市' ? '新上市企业个数' : '日新发债券数量',
          left: 'left'
        },
        legend: {
          left: 'center',
          bottom: '2%'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: keys
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: true // 不显示坐标轴轴线
          },
          axisTick: {
            show: false // 不显示坐标轴刻度
          },
          splitLine: {
            show: false
          }
        },
        series: [
          {
            barWidth: 30,
            data: values,
            type: this.tab === '上市' ? 'bar' : 'line',
            itemStyle: {
              color: '#cccccc'
            }
          }
        ]
      }
      myChart.setOption(option)
    },
    subForm(row) {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          try {
            this.$modal.loading('Loading...')
            this.ruleForm.creditError = this.ruleForm.notUse ? 1 : 0
            if (row) {
              createST(this.ruleForm).then((res) => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success'
                })
                this.dialogVisible = false
              })
            } else {
              createBE(this.ruleForm).then((res) => {
                const { data } = res
                if (data === '新增成功') {
                  this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success'
                  })
                  this.dialogVisible2 = false
                }
              })
            }
          } catch (error) {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            })
          } finally {
            this.$modal.closeLoading()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    check(row, keyword) {
      if (!row) {
        this.$message({
          showClose: true,
          message: '请输入查重信息',
          type: 'error'
        })
        return
      }
      try {
        this.$modal.loading('Loading...')
        const parmas = {
          target: row,
          keyword: keyword
        }
        checkData(parmas).then((res) => {
          const { data } = res
          let ret = false
          if (!data.data) {
            ret = 1
          } else {
            ret = 2
          }
          switch (keyword) {
            case 'STOCK_CN_CODE':
              this.repalce1 = ret
              break
            case 'STOCK_HK_CODE':
              this.repalce1 = ret
              break
            case 'STOCK_A_NAME':
              this.repalce2 = ret
              break
            case 'STOCK_HK_NAME':
              this.repalce2 = ret
              break
            case 'BOND_FULL_NAME':
              this.repalce1 = ret
              break
            case 'BOND_CODE':
              this.repalce2 = ret
              break
            case 'BOND_SHORT_NAME':
              this.repalce3 = ret
              break
            case 'ENTITY_NAME':
              this.repalce4 = ret
              break
            case 'CREDIT_CODE':
              this.repalce5 = ret
              break
            default:
              break
          }
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    handleClose() {
      this.dialogVisible2 = false
      this.dialogVisible = false
      this.repalce1 = false
      this.repalce2 = false
      this.repalce3 = false
      this.repalce4 = false
      this.repalce5 = false
      this.ruleForm = {}
      this.$refs['ruleForm'].clearValidate()
    }
  }
}
</script>

<style scoped lang="scss">
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

.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-left: 3%;
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
.notUse {
  margin-left: 82px;
  margin-bottom: 20px;
  .mr60 {
    margin-right: 60px;
  }
}
.red {
  margin-left: 5px;
  color: red;
}
.green {
  margin-left: 5px;
  color: greenyellow;
}
.none {
         margin: 0 auto;
    width: 100px;
    margin-top: 20px;
}
</style>
