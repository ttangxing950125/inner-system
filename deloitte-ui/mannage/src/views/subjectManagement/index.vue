<template>
  <div class="app-container home">
    <el-row>
      <el-col :sm="24" :lg="24" style="padding-left: 20px">
        <el-card class="box-card">
          <div class="top-left">
            <div class="title">整体情况</div>
            <div class="body">
              截止今日起，平台已收录主体
              <span>{{ goveSum + entitySum }}</span> 个，其中，政府主体
              <span>{{ goveSum }}</span> 个，企业主体
              <span> {{ entitySum }} </span> 个
            </div>
          </div>
          <div v-loading="loadingData" class="top-right">
            <div
              class="box1"
              :style="{
                background: '#86BC25',
                width: govePercent + '%',
                'text-align': 'center',
              }"
            >
              <div style="margin-top: 35px">
                {{ govePercent ? "政府" : "" }}
              </div>
              <div>{{ govePercent ? govePercent + "%" : "" }}</div>
            </div>
            <div
              class="box2"
              :style="{
                background: '#D7ECB8',
                width: entityPercent + '%',
                'text-align': 'center',
              }"
            >
              <!-- <div style="margin-top: 45px">
                {{ entityPercent ? "企业" + entityPercent + "%" : "" }}
              </div> -->
              <div style="margin-top: 35px">
                {{ entityPercent ? "企业" : "" }}
              </div>
              <div>{{ entityPercent ? entityPercent + "%" : "" }}</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :sm="24" :lg="12" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="box-card1 box-card">
            <div class="flex1 top-left">
              <div class="title mr20">政府主体</div>
              <el-button type="text ">导出全部</el-button>
              <div class="right-number">{{ goveSum }}</div>
            </div>
          </div>
          <div class="title1">
            <div class="flex1">
              <div>
                <div class="text item">
                  {{ "地方政府 " }}
                </div>
                <div class="text item">
                  {{ "省级 " }}
                </div>
                <div class="text item">
                  {{ "市级 " }}
                </div>
                <div class="text item">
                  {{ "县级 " }}
                </div>
                <div class="text item">
                  {{ "经开高新区 " }}
                </div>
              </div>
              <div style="width: 70%; margin-right: 10px">
                <div
                  class="text item number-font"
                  :style="{
                    background: '#86BC25',
                    width: govSumPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ govSumPercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#86BC25',
                    width: provincePercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ provincePercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#86BC25',
                    width: cityPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ cityPercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#86BC25',
                    width: countyPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ countyPercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#86BC25',
                    width: openPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ openPercent + "%" }}
                </div>
              </div>
              <div>
                <div class="text item number-font" style="width: 60px">
                  <div>{{ goverment.govSum || "" }}</div>
                  <div>{{ goverment.province || "" }}</div>
                  <div>{{ goverment.city || "" }}</div>
                  <div>{{ goverment.county || "" }}</div>
                  <div>{{ goverment.open || "" }}</div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :sm="24" :lg="12" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="box-card1 box-card">
            <div class="flex1 top-left">
              <div class="title mr20">企业主体</div>
              <el-button type="text ">导出全部</el-button>
              <div class="right-number">{{ entitySum }}</div>
            </div>
          </div>
          <div class="title1">
            <div class="flex1">
              <div>
                <div class="text item">
                  {{ "上市企业 " }}
                </div>
                <div class="text item">
                  {{ "发债企业 " }}
                </div>
                <div class="text item">
                  {{ "即上市又发债 " }}
                </div>
                <div class="text item">
                  {{ "金融机构 " }}
                </div>
                <div class="text item">
                  {{ "非上市非发债 " }}
                </div>
              </div>
              <div style="width: 70%; margin-right: 10px">
                <div
                  class="text item number-font"
                  :style="{
                    background: '#D7ECB8',
                    width: listPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ listPercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#D7ECB8',
                    width: issueBondsPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ issueBondsPercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#D7ECB8',
                    width: bondsAndListPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ bondsAndListPercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#D7ECB8',
                    width: financePercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ financePercent + "%" }}
                </div>
                <div
                  class="text item number-font"
                  :style="{
                    background: '#D7ECB8',
                    width: notBondsAndListPercent + '%',
                    'text-align': 'center',
                  }"
                >
                  {{ notBondsAndListPercent + "%" }}
                </div>
              </div>
              <div>
                <div class="text item number-font" style="width: 60px">
                  <div>{{ entity.list || "0" }}</div>
                  <div>{{ entity.issueBonds || "0" }}</div>
                  <div>{{ entity.bondsAndList || "0" }}</div>
                  <div>{{ entity.finance || "0" }}</div>
                  <div>{{ entity.notBondsAndList || "0" }}</div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :sm="24" :lg="24" class="mt20" style="padding-left: 20px">
        <el-card>
          <div class="box-card1 box-card">
            <div class="flex1 top-left">
              <div class="title mr20">覆盖情况快速查询</div>
              <el-button type="text" @click="selectAll">批量查询</el-button>
            </div>
          </div>
          <div class="select-body">
            <div class="flex1">
              <el-select
                class="mr5 select-only"
                v-model="value"
                placeholder="选择主体类型"
                @change="changeType"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <el-input
                class="mr10"
                v-model="input"
                placeholder="请输入主体名称/行政区划/企业统一社会信用代码"
              ></el-input>
              <el-select
                class="mr10 selects"
                v-model="value1"
                :disabled="value === 'GV'"
                multiple
                placeholder="选择查询产品范围（可多选）"
                @change="selectChange"
              >
                <el-option
                  v-for="item in options2"
                  :key="item.id"
                  :label="item.proName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
              <el-button class="mr10" type="primary" @click="select"
                >查询</el-button
              >
            </div>
          </div>
          <div class="select-table">
            <div class="table-title flex1" v-if="list.length === 0">
              <div class="table-desc">查询结果</div>
              <el-divider
                direction="vertical"
                style="height：60px"
              ></el-divider>
              <div class="table-desc">
                {{
                  loading ? "查询中..." : list.length === 0 ? "查无此结果" : ""
                }}
              </div>
            </div>
            <div>
              <div class="table-desc">
                <div>查询结果</div>
                <div class="table-result">
                  共查询到<span> {{ total }} </span>个结果:
                </div>
              </div>
              <el-table class="table-content" :data="list" style="width: 98%">
                <el-table-column fixed type="index" width="50" label="序号">
                </el-table-column>
                <el-table-column
                  fixed
                  :prop="value === 'GV' ? 'govCode' :'creditCode'"
                  label="德勤主体代码"
                  width="200"
                >
                </el-table-column>
                <el-table-column
                  fixed
                  :prop="value === 'GV' ? 'govName' :'entityName'"
                  :label="value === 'GV' ? '政府名称' : '企业名称'"
                  width="300"
                  class="xxxxxxx"
                >
                </el-table-column>
                <el-table-column
                  v-for="(item, index) in rettHeaer"
                  :key="index"
                  :prop="item.proName"
                  :label="item.proName"
                  :class="item.color === 1 ? 'red' : 'green'"
                >
                  <template slot-scope="scope">
                    <div>
                      {{ scope.row[item.proName] }}
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="批量查询" :visible.sync="dialogVisible" :before-close="handleClose" width="30%">
      <div class="p-top">
        <span>请按照模板准备输入文件， 并通过以下界面导入文件进行匹配。</span>
      </div>
      <el-button type="text" class="p-top" @click="downFile"
        >批量查询匹配模板.xlsx</el-button
      >
      <div class="p-red">
        <div>特别注意：</div>
        <div>请不要修改列名！</div>
        <div>请不要修改列顺序！</div>
        <div>请不要在行间出现空行！</div>
      </div>
      <div :class="uploadStatus ? 'upload-success' : 'upload-background'">
        <div class="upload-font">请选择文件</div>
        <fileUpload
          v-if="!uploadStatus && !uploadLoading"
          :uploadUrl="'#'"
          :uploadStr="'+ 点击上传文件'"
          ref="fileUpload"
          @loading="loadingFun"
          @uploadFail="uploadFail"
          @uploadPass="uploadPass"
          :httpFun="uploadFun"
        /> 
        <div class="upload-complete" v-else>
            <span class="upload-c-s">{{ uploadLoading ? '上传中' : '已完成' }}</span>
        </div>
      </div>
      <div>
        <div class="flex1">
          <div class="upload-font">匹配进度</div>
          <div class="upload-font2"> {{ !uploadStatus ? '[暂未开始匹配]' : '匹配完成' }}</div>
        </div>
        <el-progress
          class="percentage"
          :percentage="percentage"
          color="#86BC25"
          :text-inside="true"
          :stroke-width="15"
        ></el-progress>
      </div>
      <div slot="footer" class="dialog-footer center">
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { govList, entityInfoList, getProduct, getCov } from "@/api/subject";
import { importExcelByEntity, getChecking  } from "@/api/common";
import { download } from '@/utils/index'
import fileUpload from "../../components/FileUpload/httpUpload.vue";
import pagination from "../../components/Pagination";
export default {
  name: "Index",
  components: {
    fileUpload,
    pagination,
  },
  data() {
    return {
      list: [],
      input: "",
      options: [
        {
          value: "GV",
          label: "政府主体",
        },
        {
          value: "Q",
          label: "企业主体",
        },
      ],
      value: "Q",
      options2: [],
      value1: [],
      loading: false,
      goveSum: 0,
      entitySum: 0,
      govSumPercent: 0,
      provincePercent: 0,
      cityPercent: 0,
      countyPercent: 0,
      openPercent: 0,
      goverment: {},
      entity: {},
      entitySumPercent: 0,
      listPercent: 0,
      issueBondsPercent: 0,
      financePercent: 0,
      bondsAndListPercent: 0,
      notBondsAndListPercent: 0,
      loadingData: false,
      dialogVisible: false,
      percentage: 0,
      selectHeaer: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
      },
      total2: 0,
      rettHeaer: [
          {
              proName: ''
          },
          {
              proName: ''
          },
          {
              proName: ''
          }
      ],
      uuid: '',
      timer: null,
      uploadStatus: false,
      fileName: '批量检查结果.xlsx',
      uploadLoading: false
    };
  },
  mounted() {
    this.init();
  },
  methods: {
      getuuid() {
          const timeStr = Date.now()
        const rondStr = Math.round(Math.random()*10)
        const parmasStr = timeStr+rondStr
        localStorage.setItem('uuid', parmasStr)
        this.uuid = parmasStr
      },
    init() {
      try {
        this.$modal.loading("loading...");
        this.loadingData = true;
        govList({}).then((res) => {
          const { data } = res;
          this.goverment = data;
          this.goveSum = eval(Object.values(data).join("+"));
          this.govSumPercent = this.getPercent(data.govSum, this.goveSum);
          this.provincePercent = this.getPercent(data.province, this.goveSum);
          this.cityPercent = this.getPercent(data.city, this.goveSum);
          this.countyPercent = this.getPercent(data.county, this.goveSum);
          this.openPercent = this.getPercent(data.open, this.goveSum);
        });
        entityInfoList({}).then((res) => {
          const { data } = res;
          this.entity = data;
          this.entitySum = eval(Object.values(data).join("+"));
          this.entitySumPercent = this.getPercent(
            data.entitySum,
            this.entitySum
          );
          this.listPercent = this.getPercent(data.list, this.entitySum);
          this.issueBondsPercent = this.getPercent(
            data.issueBonds,
            this.entitySum
          );
          this.financePercent = this.getPercent(data.finance, this.entitySum);
          this.bondsAndListPercent = this.getPercent(
            data.bondsAndList,
            this.entitySum
          );
          this.notBondsAndListPercent = this.getPercent(
            data.notBondsAndList,
            this.entitySum
          );
        });
        getProduct({}).then((res) => {
          const { data } = res;
          this.options2 = data;
          this.options2.push({
            id: 9999,
            proName: "全部产品",
          });
        });
        let proId = [];
        if (this.value1.length === 1 && this.value1[0] === 9999) {
          this.options2.forEach((k) => {
            if (k.id !== 9999) {
              proId.push(k.id);
            }
          });
        } else {
          proId = this.value1;
        }
        const parmas = {
          entityName: this.input,
          entityType: this.value,
          proId: proId,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        };
        getCov(parmas).then((res) => {
          const { data } = res;
          data.records.forEach((e) => {
            if (e.result) {
              e.result.forEach((r) => {
                e[r.key] = r.value;
              });
            }
          });
          this.list = data.records;
          this.total = data.total;
          this.queryParams.pageNum = data.current;
        });
        this.getuuid()
      } catch (error) {
        console.log(error);
      } finally {
        this.loadingData = false;
        this.$modal.closeLoading();
      }
    },
    getList() {
      let proId = [];
      if (this.value1.length === 1 && this.value1[0] === 9999) {
        this.options2.forEach((k) => {
          if (k.id !== 9999) {
            proId.push(k.id);
          }
        });
      } else {
        proId = this.value1;
      }
      const parmas = {
        entityName: this.input,
        entityType: this.value,
        proId: proId,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
      };
      getCov(parmas).then((res) => {
        const { data } = res;
        (data.records).forEach((e) => {
          if (e.result.length > 0) {
            e.result.forEach((r) => {
              e[r.key] = r.value;
              e.color = r.color;
            });
          }
        });
        this.list = data.records;
        
        this.total = data.total;
        this.queryParams.pageNum = data.current;
      });
    },
    getPercent(newObj, sum) {
      const res = ((newObj / sum) * 100).toFixed(2);
      return res;
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    select() {
      try {
        this.loading = true;
        this.getList();
        this.rettHeaer = this.selectHeaer
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    selectAll() {
      this.dialogVisible = true;
    },
    downFile() {
      var a = document.createElement("a"); // 创建一个<a></a>标签
      a.href = "/批量查询匹配模板.xlsx";
      a.download = "批量查询匹配模板.xlsx"; // 设置下载文件文件名
      a.style.display = "none"; // 隐藏a标签
      document.body.appendChild(a); // 将a标签追加到文档对象中
      a.click(); // 模拟点击了a标签，会触发a标签的href的读取，浏览器就会自动下载了
      a.remove(); // 一次性的，用完就删除a标签
    },
    loadingFun(index) {
      this.$modal.loading('Loading...')
      this.uploadLoading = true
    },
    uploadPass(data, index) {
      download(data, '适配文件.xlsx')
      this.$modal.closeLoading()
    },
    uploadFail(index) {
      this.uploadStatus = false
      this.uploadLoading = false
    },
    selectChange(row) {
      this.selectHeaer = [];
      if (row.length === 1 && row[0] === 9999) {
        this.options2.forEach((k) => {
            if (k.id !== 9999) {
              this.selectHeaer.push(k);
            }
        });
      } else {
        row.forEach((e) => {
          this.options2.forEach((k) => {
            if (e === k.id && e !== 9999) {
              this.selectHeaer.push(k);
            }
          });
        });
      }
    },
    uploadFun(params) {
    // 获取文件 通过FormData将文件转化为二进制流的形式传给后端
        let form = new FormData()
        form.append('file', params.file)
        form.uuid = this.uuid
        form.proIds = this.value1
        importExcelByEntity(form).then((res) => {
            clearTimeout(this.timer);
            this.uploadStatus = true
            this.uploadLoading = false
            this.percentage = 0
            download(res, this.fileName)
        })
        this.timer = setInterval(this.getProgress, 1000)
        this.$modal.closeLoading()
    },
    getProgress() {
        let uuid = localStorage.getItem('uuid')
        getChecking({uuid: uuid}).then(res => {
            const { data } = res
            if (data) { 
                const ret = parseFloat(data)
                const percent = data && (ret.toFixed(2))*100
                this.percentage = percent
            }
        })
    },
    changeType(row) {
        this.value1 = this.value === 'GV' ? [] : this.value1
        if (row === 'GV') {
            this.options2.forEach(e => {
                if (e.id !== 9999) {
                    this.selectHeaer.push(e);
                }
            })
        }
    },
    handleClose() {
        this.uploadStatus = false
        this.percentage = 0
        this.dialogVisible = false
    }
  },
  computed: {
    govePercent() {
      const res = (this.goveSum / (this.goveSum + this.entitySum)).toFixed(4);
      return res * 100;
    },
    entityPercent() {
      const res = (this.entitySum / (this.goveSum + this.entitySum)).toFixed(4);
      return res * 100;
    },
  },
};
</script>

<style scoped lang="scss">
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
    height: 150px;
  }
  .right-number {
    width: 50px;
    position: relative;
    left: 156%;
    top: 30%;
    color: #86bc25;
  }
}
.box-card1 {
  display: flex;
  justify-content: space-between;
}
.title1 {
  padding-left: 30px;
  .item {
    margin-top: 13px;
    margin-right: 15px;
    width: 105px;
  }
  .number-font {
    font-size: 18px;
    div {
      margin-top: 13px;
    }
  }
}
.select-body {
  padding-left: 15px;
}
.selects {
  width: 60%;
}
.select-table {
  margin-top: 25px;
  min-height: 80px;
  background: #f6f6f6;
  padding: 10px;
  width: 98%;
  margin-left: 14px;
}
.select-only {
  width: 30%;
}
.table-desc {
  margin-top: 1.5%;
  width: 13%;
  padding-left: 12px;
}
.table-result {
  margin-top: 10px;
  width: 200px;
  span {
    color: #86bc25;
  }
}
.page {
  margin-left: 31%;
  margin-top: 20px;
}
.table-content {
  margin-top: 10px;
}
.p-top {
  margin-bottom: 15px;
  font-size: 12px;
  font-weight: 600;
  span {
    color: #75787a;
    font-size: 12px;
    font-weight: 600;
  }
}
.p-red {
  margin-bottom: 35px;
  div {
    color: red;
    font-size: 12px;
    font-weight: 600;
  }
}
.upload-success {
  ::v-deep .upload-file {
    text-align: center;
    background: #86bc25;
  }
}
.upload-background {
  margin-bottom: 5px;
  font-weight: 800;
  font-size: 12px;
  ::v-deep .upload-file {
    background: #e7f0d9;
    text-align: center;
  }
  ::v-deep .el-button--text {
    color: #86bc25;
  }
}
.upload-font {
  font-size: 12px;
  font-weight: 600;
  margin-bottom: 10px;
}
.flex1 {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  .upload-font2 {
    font-size: 12px;
  }
}
.percentage {
  ::v-deep .el-progress-bar__outer {
    border-radius: 0px !important;
  }
  ::v-deep .el-progress-bar__inner {
    border-radius: 0px !important;
  }
}
.center {
  text-align: center !important;
  ::v-deep .el-button--primary {
    background: #86bc25;
    border-color: #86bc25;
  }
}
.red {
  background-color: #ffe7e7;
}
.green {
  background-color: #86bc25;
}
.upload-complete {
    height: 46px;
    width: 100%;
    text-align: center;
    background: #86bc25;
    color: white;
    line-height: 3;
}
</style>
