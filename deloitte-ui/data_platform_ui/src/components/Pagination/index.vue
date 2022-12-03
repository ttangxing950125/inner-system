<template>
  <div :class="{ hidden: hidden }" class="pagination">
    <el-pagination
      :background="background"
      :current-page.sync="currentPage"
      :page-size.sync="pageSize"
      :layout="layout"
      :page-sizes="pageSizes"
      :pager-count="pagerCount"
      :total="total"
      v-bind="$attrs"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <span class="all-page">共{{ Math.ceil(total / 10) }}页</span>
  </div>
</template>

<script>
import { scrollTo } from "@/utils/scroll-to";

export default {
  name: "Pagination",
  props: {
    total: {
      required: true,
      type: Number,
    },
    page: {
      type: Number,
      default: 1,
    },
    limit: {
      type: Number,
      default: 20,
    },
    pageSizes: {
      type: Array,
      default() {
        return [30, 50, 100, 200];
      },
    },
    // 移动端页码按钮的数量端默认值5
    pagerCount: {
      type: Number,
      default: document.body.clientWidth < 992 ? 5 : 7,
    },
    layout: {
      type: String,
      default: "total,  prev, pager, next",
    },
    background: {
      type: Boolean,
      default: true,
    },
    autoScroll: {
      type: Boolean,
      default: true,
    },
    hidden: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {};
  },
  computed: {
    currentPage: {
      get() {
        return this.page;
      },
      set(val) {
        this.$emit("update:page", val);
      },
    },
    pageSize: {
      get() {
        return this.limit;
      },
      set(val) {
        this.$emit("update:limit", val);
      },
    },
  },
  methods: {
    handleSizeChange(val) {
      if (this.currentPage * val > this.total) {
        this.currentPage = 1;
      }
      this.$emit("pagination", { page: this.currentPage, limit: val });
      if (this.autoScroll) {
        scrollTo(0, 800);
      }
    },
    handleCurrentChange(val) {
      this.$emit("pagination", { page: val, limit: this.pageSize });
      if (this.autoScroll) {
        scrollTo(0, 800);
      }
    },
  },
};
</script>

<style lang='scss'  scoped>
.pagination {
  background: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 20px 0;
}
::v-deep .el-pagination {
  display: flex;
  align-items: center;
  .el-pagination__total {
    font-size: 12px;
    color: #97999b;
    font-weight: 400;
  }
  button {
    height: 20px;
    line-height: 20px;
    background-color: #fff !important;
    border: 1px solid rgba(229, 229, 229, 1);
  }
  .more::before {
    line-height: 20px;
  }
  .el-pager {
    li {
      height: 20px;
      line-height: 20px;
      font-size: 12px;
      background-color: #fff !important;
      border: 1px solid rgba(229, 229, 229, 1);
      border-radius: 2px;
      color: #97999b;
      font-weight: 400;
    }
  }
  .active {
    background-image: linear-gradient(
      180deg,
      #6a788b 0%,
      #444e5a 100%
    ) !important;
  }
}
.all-page {
  font-size: 12px;
  padding-left: 10px;
  color: #97999b;
}
</style>
