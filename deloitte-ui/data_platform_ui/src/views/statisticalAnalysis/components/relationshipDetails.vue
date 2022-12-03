<template>
  <div>
    <go-back home="勾稽关系" :title="'xxxxx'" @click="$emit('back')"></go-back>
    <icon-title>单个校验规则详情：有息债务总额总负债</icon-title>
    <!-- 条件查询 -->
    <div class="query">
      <el-form ref="form" :model="query" inline>
        <el-form-item label-width="0px">
          <el-input
            size="mini"
            clearable
            v-model="query.crux"
            placeholder="输入关键字进行搜索"
            prefix-icon="el-icon-search"
            style="width: 282px; margin-right: 20px"
            @keyup.enter.native="handleQuery"
            @change="handleQuery"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <!-- 表格 -->
    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      :header-cell-style="headerStyle"
      :cell-style="cellStyles"
    >
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column prop="date" label="校验规则" align="left" />
      <el-table-column prop="name" label="主体名称" align="left" />
      <el-table-column prop="address" label="主体代码" align="left" />
      <el-table-column prop="accuracy" label="通过情况" align="center" />
      <el-table-column prop="name" label="数据时间" align="center" />
      <el-table-column prop="name" label="有息负载总额" align="center" />
      <el-table-column prop="name" label="总负债" align="center" />
    </el-table>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="pageNum"
      :limit.sync="pageSize"
    />
  </div>
</template>

<script>
import goBack from "../../../components/backHome/goBack.vue";
export default {
  components: { goBack },
  data() {
    return {
      total: 1000,
      pageNum: 1,
      pageSize: 30,
      //条件查询
      query: {
        crux: null,
      },
      tableData: [],
    };
  },
  methods: {},
};
</script>

 
<style lang="scss" scoped>
.query {
  margin: 20px 0 20px 0;
}
</style>