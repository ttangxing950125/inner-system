<template>
  <div>
    <el-select
      ref="select"
      size="mini"
      @change="chang"
      v-model="value"
      multiple
      collapse-tags
      placeholder="全部"
    >
      <el-option label="全部" value="全部" v-if="options.length > 2">
        <span class="flex-row-bw">
          <el-checkbox
            label="全部"
            :value="value.includes('全部')"
            onclick="return false"
          ></el-checkbox>
          <span> </span>
        </span>
      </el-option>
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      >
        <span class="flex-row-bw">
          <el-checkbox
            :value="value.includes(item.value)"
            :label="item.label"
            onclick="return false"
          ></el-checkbox>
          <!-- 去掉小勾 -->
          <span> </span>
        </span>
      </el-option>
    </el-select>
  </div>
</template>

<script>
export default {
  props: {
    //选项数组
    options: {
      type: Array,
      default: () => {
        return [];
      },
    },
    //默认参数
    defaultValue: {
      type: Array,
      default: () => {
        return [];
      },
    },
    //最少选中1个
    isMust: {
      type: Boolean,
      default: () => {
        return false;
      },
    },
  },
  data() {
    return {
      value: this.defaultValue,
      isAll: false,
    };
  },

  methods: {
    chang(val) {
      if (this.isAll) {
        this.isAll = false;
        if (val.indexOf("全部") > -1) {
          this.value = val.filter((p) => p !== "全部");
        } else {
          this.value = [];
        }
      } else {
        if (val.indexOf("全部") > -1) {
          this.value = ["全部"];
          this.options.forEach((i) => {
            this.value.push(i.value);
          });
          this.isAll = true;
        }
        if (val.length == this.options.length && val.indexOf("全部") == -1) {
          this.value = ["全部"];
          this.options.forEach((i) => {
            this.value.push(i.value);
          });
          this.isAll = true;
        }
      }
      //isMust 参数确定最少选中1条数据
      if (this.isMust && !this.value.length) {
        this.value = [this.options[0].value];
        this.$message("最少选择一个");
      }
      this.$emit(
        "change",
        this.value.filter((p) => p !== "全部")
      );
    },
  },
};
</script>
<style scoped lang='scss'>
::v-deep .el-checkbox__input.is-checked .el-checkbox__inner {
  background: #ffffff;
  border: 1px solid rgba(210, 210, 210, 1);
}
::v-deep .el-checkbox__inner::after {
  border-color: #6d798f;
}
::v-deep .el-checkbox__input.is-checked + .el-checkbox__label {
  color: #6d798f;
}
</style>




// .selectCheckBox .el-tag:first-child {
//   max-width: calc(100% - 72px);
//   .el-select__tags-text {
//     display: inline-block;
//     width: calc(100% - 18px);
//     overflow: hidden;
//     text-overflow: ellipsis;
//     white-space: nowrap;
//   }
//   .el-icon-close {
//     top: -7px;
//   }
// }

// .pop_select_down.is-multiple .el-select-dropdown__item {
//   padding: 0 18px;
// }
// .pop_select_down.is-multiple .el-select-dropdown__list {
//   padding-top: 0;
// }
// .pop_select_down.is-multiple .selectbox {
//   display: block;
//   height: 34px;
//   line-height: 34px;
// }
// .pop_select_down.is-multiple .el-select-dropdown__item.selected::after {
//   top: 0.05rem;
//   left: 1.17rem;
//   z-index: 1;
//   font-weight: normal;
//   color: #fff;
// }
// .pop_select_down.is-multiple .el-select-dropdown__item .check {
//   position: relative;
//   box-sizing: border-box;
//   display: inline-block;
//   width: 0.875rem;
//   height: 0.875rem;
//   margin-right: 10px;
//   vertical-align: middle;
//   border: 1px solid #656c7e;
//   border-radius: 2px;
//   transition: border-color 0.25s cubic-bezier(0.71, -0.49, 0.26, 1.46),
//     background-color 0.25s cubic-bezier(0.71, -0.49, 0.26, 1.46);
// }
// .pop_select_down.is-multiple .el-select-dropdown__item.selected .check {
//   background-color: #656c7e;
// }
