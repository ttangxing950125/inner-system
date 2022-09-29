<template>
  <div class="home">
    <div class="mt20">
      <div class="flex1 selet-box">
        <h3 class="g-t-title">政府主体</h3>
        <el-input
          class="select-x"
          v-model="input"
          placeholder="搜索主体名称/代码/统一社会信用代码"
        ></el-input>
      </div>
      <el-table class="table-content" :data="list" style="margin-top: 15px">
        <el-table-column type="index" sortable label="生效状态" width="100">
        </el-table-column>
        <el-table-column prop="date" label="政府主体代码" sortable>
        </el-table-column>
        <el-table-column prop="name" label="政府主体名称"> </el-table-column>
        <el-table-column prop="province" label="曾用名或别称">
        </el-table-column>
        <el-table-column prop="city" label="变更日期"> </el-table-column>
        <el-table-column prop="address" label="备注"> </el-table-column>
        <el-table-column prop="zip" label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >修改</el-button
            >
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >停用</el-button
            >
          </template></el-table-column
        >
      </el-table>
      <el-button @click="handleClick(scope.row)" type="text"
        >增加记录</el-button
      >
    </div>
    <div class="mt20">
      <div class="flex1 selet-box">
        <h3 class="g-t-title">政府主体</h3>
        <el-input
          class="select-x"
          v-model="input"
          placeholder="搜索主体名称/代码/统一社会信用代码"
        ></el-input>
      </div>
      <el-table class="table-content" :data="list" style="margin-top: 15px">
        <el-table-column type="index" sortable label="生效状态" width="100">
        </el-table-column>
        <el-table-column prop="date" label="企业主体代码" sortable>
        </el-table-column>
        <el-table-column prop="name" label="企业主体名称"> </el-table-column>
        <el-table-column prop="province" label="统一社会信用代码">
        </el-table-column>
        <el-table-column prop="city" label="曾用名或别称"> </el-table-column>
        <el-table-column prop="city" label="变更日期"> </el-table-column>
        <el-table-column prop="address" label="备注"> </el-table-column>
        <el-table-column prop="zip" label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >修改</el-button
            >
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >停用</el-button
            >
          </template></el-table-column
        >
      </el-table>
      <el-button @click="handleClick(scope.row)" type="text"
        >增加记录</el-button
      >
    </div>
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
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    select() {
      console.log(1);
    },
  },
};
</script>

<style scoped lang="scss">
.selet-box {
  justify-content: space-between;
}

.g-t-title {
  font-weight: 600;
}
.select-x {
  margin-top: 10px;
  width: 24%;
}
</style>
