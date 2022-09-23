<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()">返回首页</el-button>
      <h3 class="title">新上市-导入任务清单</h3>
    </div>
    <el-row>
      <el-col :sm="24" :lg="23" class="form-card">
        <div class="flex1">
          <h3 class="title1">wind内地股票-IPO-新股发行资料</h3>
          <el-button class="back" type="text">下载导入模板</el-button>
          <el-upload
            class="upload-demo back"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList"
          >
            <el-button type="text">导入今日更新文件</el-button>
          </el-upload>
        </div>
        <div>【 <span style="color: red">暂未导入今日更新文件</span> 】</div>
      </el-col>
      <el-col
        :sm="24"
        :lg="23"
        class="mt20 form-card"
        style="padding-left: 20px; margin-top: -28px"
      >
        <div class="flex1">
          <h3 class="title1">
            新增待确认记录( <span style="color: red">0</span> )
          </h3>
        </div>
        <el-table
          class="table-content"
          :data="list"
          style="width: 98%; margin-top: 15px"
          height="300"
          empty-text="暂无确认记录，请导入今日更新文件"
        >
          <el-table-column prop="date" label="导入日期"> </el-table-column>
          <el-table-column prop="name" label="交易代码"> </el-table-column>
          <el-table-column prop="province" label="起息日"> </el-table-column>
          <el-table-column prop="city" label="到期日"> </el-table-column>
          <el-table-column prop="address" label="债券全称"> </el-table-column>
          <el-table-column prop="address" label="发行人全称"> </el-table-column>
          <el-table-column prop="address" label="新增信息备注">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :sm="24" :lg="23" class="form-card">
        <div class="flex1">
          <h3 class="title1">wind港台股票-证券发行股票发行-首次发行明细</h3>
          <el-button class="back" type="text">下载导入模板</el-button>
          <el-upload
            class="upload-demo back"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList"
          >
            <el-button type="text">导入今日更新文件</el-button>
          </el-upload>
        </div>
        <div>【 <span style="color: red">暂未导入今日更新文件</span> 】</div>
      </el-col>
      <el-col
        :sm="24"
        :lg="23"
        class="mt20 form-card"
        style="padding-left: 20px; margin-top: -28px"
      >
        <div class="flex1">
          <h3 class="title1">
            新增待确认记录( <span style="color: red">0</span> )
          </h3>
          <div class="g-desc flex1">
            <a
              :class="currentTab === '确认全部修改信息' ? 'g-select' : ''"
              @click="changeTab('确认全部修改信息')"
              >确认全部修改信息</a
            >
            <a
              :class="currentTab === '确认全部新增记录' ? 'g-select' : ''"
              @click="changeTab('确认全部新增记录')"
              >确认全部新增记录</a
            >
          </div>
        </div>
        <el-table
          class="table-content"
          :data="list"
          style="width: 98%; margin-top: 15px"
          height="300"
          empty-text="暂无确认记录，请导入今日更新文件"
        >
          <el-table-column prop="date" label="导入日期"> </el-table-column>
          <el-table-column prop="name" label="交易代码"> </el-table-column>
          <el-table-column prop="province" label="起息日"> </el-table-column>
          <el-table-column prop="city" label="到期日"> </el-table-column>
          <el-table-column prop="address" label="债券全称"> </el-table-column>
          <el-table-column prop="address" label="发行人全称"> </el-table-column>
          <el-table-column prop="address" label="新增信息备注">
          </el-table-column>
          <el-table-column prop="province" label="任务操作">
            <template slot-scope="scope">
              <el-button
                @click="handleClick(scope.row)"
                type="text"
                size="small"
                >添加</el-button
              >
              <el-button
                @click="handleClick(scope.row)"
                type="text"
                size="small"
                >忽略</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "work",
  data() {
    return {
      list: [],
    };
  },
  mounted() {},
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    handleClick() {
      console.log(1);
    },
    back() {
      this.$router.back();
    },
  },
};
</script>

<style scoped lang="scss">
::v-deep {
  .el-upload-list {
    display: none;
  }
}
.upload-demo {
  margin-top: 11px;
}

.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-left: 3%;
  margin-top: 3%;
}
.back {
  margin-left: 19px;
}
.title {
  margin-left: 31%;
  font-weight: 600;
}
.title1 {
  font-weight: 600;
}
.g-desc {
  margin-top: 15px;
  margin-left: 25px;
  span {
    color: greenyellow;
  }
  a {
    font-size: 14px;
    color: #9b9b9b;
    text-decoration: revert;
    margin-right: 26px;
    height: 16px;
    margin-top: 5px;
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
