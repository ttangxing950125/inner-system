<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button
        class="back"
        type="text"
        @click="back()"
      >返回企业主体首页</el-button>
      <h3 class="title">地方政府-新增主体</h3>
    </div>
    <el-row>
      <el-col :sm="24" :lg="23" class="form-card">
        <el-card class="box-card">
          <el-form
            ref="ruleForm"
            :model="ruleForm"
            :rules="rules"
            label-width="135px"
            label-position="left"
            class="demo-ruleForm"
          >
            <el-form-item label="新增日期">
              <span>{{ currentTime }}</span>
            </el-form-item>
            <el-form-item label="新增操作人">
              <span>admin</span>
            </el-form-item>
            <el-form-item label="新增主体名称" prop="govName">
              <div class="flex1">
                <el-input
                  v-model="ruleForm.govName"
                  class="t-input"
                  placeholder="输入新增主体名称"
                  @change="repalce1 = false"
                />
                <span v-if="repalce1 === 2" class="red">存在重复无法添加</span>
                <span v-if="repalce1 === 1" class="green">无重复，可新增</span>
                <el-button
                  v-if="!repalce1"
                  style="margin-left: 5px"
                  type="text"
                  @click="check(ruleForm.govName, 'GOV_NAME')"
                >查重</el-button>
              </div>
            </el-form-item>
            <el-form-item label="官方行政代码" prop="govCode">
              <div class="flex1">
                <el-input
                  v-model="ruleForm.govCode"
                  class="t-input"
                  placeholder="输入官方6位行政代码"
                  @change="repalce2 = false"
                />
                <span v-if="repalce2 === 2" class="red">存在重复无法添加</span>
                <span v-if="repalce2 === 1" class="green">无重复，可新增</span>
                <el-button
                  v-if="!repalce2"
                  style="margin-left: 5px"
                  type="text"
                  @click="check(ruleForm.govCode, 'GOV_CODE')"
                >查重</el-button>
              </div>
            </el-form-item>
            <el-form-item label="行政单位级别" class="max" prop="govLevelBig">
              <el-col :span="11">
                <el-select
                  v-model="ruleForm.govLevelBig"
                  class="level-select"
                  placeholder="选择行政单位级别"
                  @change="getSmall"
                >
                  <el-option
                    v-for="(item, index) in govOption1"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-col>
              <el-col class="line" :span="1">-</el-col>
              <el-col :span="11">
                <el-form-item prop="govLevelSmall">
                  <el-select
                    v-model="ruleForm.govLevelSmall"
                    class="level-select"
                    placeholder="选择行政单位细分级别"
                    @change="getSmall"
                  >
                    <el-option
                      v-for="(item, index) in govOption2"
                      :key="index"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-form-item>
            <el-form-item label="新增类型" prop="govType">
              <el-select v-model="ruleForm.govType" placeholder="选择新增类型">
                <el-option label="地方政府" value="1" />
                <el-option label="地方主管部门" value="2" />
                <el-option label="其他" value="3" />
              </el-select>
            </el-form-item>
            <el-form-item label="城市规模" prop="">
              <el-select
                v-model="ruleForm.govGrading"
                class="width146"
                placeholder="选择城市规模"
              >
                <el-option
                  v-for="(item, index) in range"
                  :key="index"
                  :label="item.value"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="曾用名或别称" prop="delivery">
              <el-input
                v-model="ruleForm.govNameHis"
                class="t-input"
                placeholder="输入曾用名或别称、顿号区分"
              />
            </el-form-item>
            <el-form-item label="上级行政单位名称" prop="preGovName">
              <el-select
                v-model="ruleForm.preGovName"
                class="width146"
                :disabled="ruleForm.preGovNameDis"
                placeholder="选择行政上级单位名称"
                @change="getGovCode"
              >
                <el-option
                  v-for="(item, index) in parentGovNameOptions"
                  :key="index"
                  :label="item.govName"
                  :value="item"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="城市分级" prop="delivery">
              <el-select
                v-model="ruleForm.govScale"
                class="width146"
                placeholder="选择城市分级"
              >
                <el-option
                  v-for="(item, index) in level"
                  :key="index"
                  :label="item.value"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item class="position-add" label="新增备注" prop="delivery">
              <el-input v-model="ruleForm.name" class="t-input" style="width: 300px" />
            </el-form-item>
            <el-form-item
              class="position-add"
              label="曾用名或别称备注"
              prop="delivery"
            >
              <el-input
                v-model="ruleForm.entityNameHisRemarks"
                class="t-input"
                style="width: 300px"
                placeholder="按需输入必要的曾用名或别称备注"
              />
            </el-form-item>
            <el-form-item
              class="position-add"
              label="上级行政单位代码"
              prop="preGovCode"
            >
              <el-input
                v-model="ruleForm.preGovCode"
                class="t-input"
                :disabled="true"
              />
            </el-form-item>
            <el-form-item
              class="position-add"
              label="是否为百强县"
              prop="delivery"
            >
              <el-select v-model="ruleForm.hundred" placeholder="选择是或否">
                <el-option label="是" value="1" />
                <el-option label="否" value="0" />
              </el-select>
            </el-form-item>
            <!-- <el-form-item class="position-add" label="" prop="delivery">
              <div></div>
            </el-form-item> -->
            <el-form-item class="add-btn">
              <el-button
                class="green-btn"
                type="primary"
                @click="submitForm('ruleForm')"
              >保存并添加</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col
        :sm="24"
        :lg="23"
        class="mt20 form-card"
        style="padding-left: 20px"
      >
        <el-card>
          <h3 class="g-t-title">近期更新记录</h3>
          <el-table
            class="table-content"
            :data="list"
            style="width: 98%; margin-top: 15px"
          >
            <el-table-column type="index" sortable label="序号" width="50" />
            <el-table-column prop="date" label="时间" sortable />
            <el-table-column prop="name" label="操作人" />
            <el-table-column prop="province" label="主体名称" />
            <el-table-column prop="city" label="主体代码" />
            <el-table-column prop="address" label="详细信息" />
            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="small"
                  :disabled="true"
                  @click="handleClick(scope.row)"
                >撤销停用</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="重复性提醒" :visible.sync="dialogVisible" width="35%">
      <div class="tips" style="margin-top: 5px">
        数据库中已有名称为 "<span style="font-weight: 600">xxx</span>" 的主体
        <span style="color: #86bc25"> 3 </span> 个
      </div>
      <div class="tips">GV410307 偃师区</div>
      <div class="tips">从属上级政府 GV410300 洛阳市</div>
      <div class="tips-2">
        可能与您输入的政府主体重复，或为同名主体，请注意！
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="dialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { addGovInfo, getGovInfoByLevel } from '@/api/subject'
import { getGovLevelBig, getGovLevelSmall } from '@/api/task'
import { checkData, getTypeByAttrId } from '@/api/common'
export default {
  name: 'AddGovernment',
  data() {
    return {
      currentTime: '',
      list: [],
      ruleForm: {},
      rules: {
        govName: [
          { required: true, message: '请输入新增主体名称', trigger: 'blur' }
        ],
        govCode: [
          { required: true, message: '请输入官方行政代码', trigger: 'change' }
        ],
        govLevelBig: [
          { required: true, message: '请选择行政单位级别', trigger: 'change' }
        ],
        govLevelSmall: [
          { required: true, message: '请选择行政单位细分级别', trigger: 'change' }
        ],
        govType: [
          { required: true, message: '请选择新增类型', trigger: 'change' }
        ],
        preGovName: [
          { required: true, message: '请选择上级行政单位名称', trigger: 'change' }
        ],
        preGovCode: [
          { required: true, message: '请选择上级行政单位名称', trigger: 'change' }
        ]
      },
      dialogVisible: false,
      repalce2: false,
      repalce1: false,
      govOption1: [],
      govOption2: [],
      parentGovNameOptions: [],
      range: [],
      level: [],
      options: []
    }
  },
  created() {
    this.getCurrentTime()
    this.init()
  },
  methods: {
    init() {
      getGovLevelBig({}).then((res) => {
        const { data } = res
        this.govOption1 = data
      })
      getTypeByAttrId({ attrId: 23 }).then((res) => {
        const { data } = res
        this.range = data
      })
      getTypeByAttrId({ attrId: 24 }).then((res) => {
        const { data } = res
        this.level = data
      })
    },
    goTarget(href) {
      window.open(href, '_blank')
    },
    handleClick() {
      console.log(1)
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
    back() {
      this.$router.back()
    },
    check(row, keyword) {
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
            case 'GOV_NAME':
              this.repalce1 = ret
              break
            case 'GOV_CODE':
              this.repalce2 = ret
              break
          }
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    submitForm() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          try {
            this.$modal.loading('Loading...')
            addGovInfo(this.ruleForm).then((res) => {
              if (res.code === 200) {
                this.governmentDig = false
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success'
                })
                this.init()
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
        } else {
          return false
        }
      })
    },
    getSmall(row) {
      if (row === 1) {
        this.ruleForm.preGovName = ''
        this.ruleForm.preGovCode = ''
        this.$set(this.ruleForm, 'preGovNameDis', true)
        this.$set(this.ruleForm, 'preGovCodeDis', true)
        this.$refs['ruleForm'].clearValidate()
      } else {
        this.$set(this.ruleForm, 'preGovNameDis', false)
        this.$set(this.ruleForm, 'preGovCodeDis', false)
      }
      getGovLevelSmall({ id: row }).then((res) => {
        const { data } = res
        this.govOption2 = data
      })
      getGovInfoByLevel({ bigLevel: this.ruleForm.govLevelBig, smallLevel: row }).then((res) => {
        const { data } = res
        this.parentGovNameOptions = data
      })
    },
    getGovCode(row) {
      try {
        this.$modal.loading('Loading...')
        this.ruleForm.preGovName = row.govName
        this.ruleForm.preGovCode = row.govCode
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .max {
  width: 700px;
}
.level-select {
  width: 222px;
}
.tips {
  margin-top: 5px;
}
.tips-2 {
  margin-top: 10px;
  color: red;
}

.green {
  margin-left: 5px;
  color: #86bc25;
}
.red {
  margin-left: 5px;
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
  margin-left: 3%;
  margin-top: 3%;
}
.add-btn {
  position: relative;
  left: 53%;
  top: -35%;
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
