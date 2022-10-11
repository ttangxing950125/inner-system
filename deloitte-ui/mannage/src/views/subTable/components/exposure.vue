<template>
  <div class="home">
    <div class="mt20">
      <div class="flex1 between">
        <h3 class="g-t-title">债券交易信息表</h3>
        <div class="mt20">
          <el-button @click="addNew" type="text" size="small"
            >手动添加债券</el-button
          >
        </div>
      </div>
      <div>
        <div class="select-body">
          <div class="g-desc flex1">
            <a :class="tab === 1 ? 'g-select' : ''" @click="changeTab(1)"
              >搜主体</a
            >
            <a :class="tab === 2 ? 'g-select' : ''" @click="changeTab(2)"
              >搜债券</a
            >
          </div>
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
      <el-table class="table-content" :data="list" style="margin-top: 15px">
        <el-table-column type="index" label="序号"> </el-table-column>
        <el-table-column prop="date" label="德勤主体代码" sortable>
        </el-table-column>
        <el-table-column prop="name" label="企业名称"> </el-table-column>
        <el-table-column prop="province" label="统一社会信用代码">
        </el-table-column>
        <el-table-column prop="city" label="债券交易代码"> </el-table-column>
        <el-table-column prop="zip" label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >设为目标</el-button
            >
          </template></el-table-column
        >
      </el-table>
    </div>
    <div class="mt20">
      <div class="flex1">
        <h3 class="g-t-title">目标主体发行债券</h3>
        <div class="mt20">
          【 <span class="font-color">请从上表选定目标主体</span> 】
        </div>
      </div>
      <el-table class="table-content" :data="list" style="margin-top: 15px">
        <el-table-column type="index" label="序号"> </el-table-column>
        <el-table-column prop="date" label="债券交易代码"> </el-table-column>
        <el-table-column prop="name" label="债券全称"> </el-table-column>
        <el-table-column prop="province" label="债券简称"> </el-table-column>
        <el-table-column prop="city" label="存续状态"> </el-table-column>
        <el-table-column prop="city" label="债券类型"> </el-table-column>
        <el-table-column prop="address" label="公私募类型"> </el-table-column>
        <el-table-column prop="address" label="是否违约"> </el-table-column>
        <el-table-column prop="zip" label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >设为目标</el-button
            >
          </template></el-table-column
        >
      </el-table>
      <div class="flex1 between">
        <h3 class="g-t-title">债券交易信息表</h3>
        <div class="mt20">
          <el-button @click="handleClick(scope.row)" type="text" size="small"
            >提交变更并退出修改模式</el-button
          >
        </div>
      </div>
      <el-card>
        <el-col :sm="24" :lg="12" class="form-card">
          <div class="flex1 mt10">
            <div class="first">德勤主体代码</div>
            <div class="content">GV304892</div>
            <el-button class="edit-btn" type="text" size="mini">修改</el-button>
          </div>
          <div class="flex1 mt10">
            <div class="first">德勤主体代码</div>
            <div class="content">GV304892</div>
            <el-button class="edit-btn" type="text" size="mini">修改</el-button>
          </div>
          <div class="flex1 mt10">
            <div class="first">德勤主体代码</div>
            <div class="content">GV304892</div>
            <el-button class="edit-btn" type="text" size="mini">修改</el-button>
          </div>
          <div class="flex1 mt10">
            <div class="first">德勤主体代码</div>
            <div class="content">GV304892</div>
            <el-button class="edit-btn" type="text" size="mini">修改</el-button>
          </div>
        </el-col>
        <el-col :sm="24" :lg="12" class="form-card">
          <div class="flex1 mt10">
            <div class="first">曾用名或别称备注</div>
            <div class="content">-</div>
          </div>
          <div class="flex1 mt10">
            <div class="first">曾用名或别称备注</div>
            <div class="content">-</div>
          </div>
          <div class="flex1 mt10">
            <div class="first">曾用名或别称备注</div>
            <div class="content">-</div>
          </div>
          <div class="flex1 mt10">
            <div class="first">曾用名或别称备注</div>
            <div class="content">-</div>
          </div>
        </el-col>
      </el-card>
    </div>
    <el-dialog
      title="手动添加债券信息"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="195px"
        label-position="left"
      >
        <el-form-item label="债券全称" prop="region">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="债券简称" prop="region">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="债券交易代码" prop="region">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="wind债券类型" prop="region">
          <el-col :span="11">
            <el-form-item prop="date1">
              <el-date-picker
                type="date"
                placeholder="下拉选择债券类型-I"
                v-model="ruleForm.date1"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="date2">
              <el-time-picker
                placeholder="下拉选择债券类型-II"
                v-model="ruleForm.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="公私募类型" prop="region">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="公募"></el-radio>
            <el-radio label="私募"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="债券状态" prop="region">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="存续"></el-radio>
            <el-radio label="违约"></el-radio>
            <el-radio label="已兑付"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="起息日" prop="region">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="到期兑付日" prop="region">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="债务主体统一社会信用代码" prop="region">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="债务主体名称" prop="region">
          <span class="color-gary">根据统一社会信用代码自动匹配</span>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false"
          >确认新增</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "government",
  data() {
    return {
      input: "",
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
      dialogVisible: false,
      ruleForm: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      rules: {
        name: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        region: [
          { required: true, message: "请选择活动区域", trigger: "change" },
        ],
        date1: [
          {
            type: "date",
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
        date2: [
          {
            type: "date",
            required: true,
            message: "请选择时间",
            trigger: "change",
          },
        ],
        type: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个活动性质",
            trigger: "change",
          },
        ],
        resource: [
          { required: true, message: "请选择活动资源", trigger: "change" },
        ],
        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }],
      },
      tab: "",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    select() {
      console.log(1);
    },
    addNew() {
      this.dialogVisible = true;
    },
    changeTab(tab) {
      this.tab = tab;
    },
    handleClick(row) {},
  },
};
</script>

<style scoped lang="scss">
.between {
  justify-content: space-between;
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
    color: greenyellow;
  }
  a {
    font-size: 14px;
    color: #9b9b9b;
    text-decoration: revert;
    margin-right: 10px;
  }
  .g-select {
    color: greenyellow;
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
</style>
