       
  <template>
  <el-select
    v-model="value"
    :placeholder="placeholder"
    size="mini"
    :clearable="clearable"
    @change="changeSelect"
    @clear="clear"
  >
    <el-option
      v-for="item in list"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
      <el-checkbox
        v-model="item.isCheck"
        :label="item.label"
        onClick="return false"
      ></el-checkbox>
    </el-option>
  </el-select>
</template>


<script>
export default {
  props: {
    options: {
      type: Array,
      default: () => {
        return [];
      },
    },
    defaultValue: {
      default: () => {
        return null;
      },
    },
    clearable: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: "全部",
    },
  },
  computed: {
    list() {
      this.options.forEach((i) => {
        i.isCheck = false;
      });
      let arr = this.options;
      return arr;
    },
  },
  data() {
    return {
      value: "",
    };
  },
  mounted() {
    //有默认值的时候 需要加上
    this.$nextTick(() => {
      if (this.defaultValue != null) {
        this.value = this.defaultValue;
        this.list.forEach((i) => {
          i.isCheck = i.value == this.defaultValue ? true : false;
        });
      }
    });
  },
  methods: {
    clear() {
      this.list.forEach((i) => {
        i.isCheck = false;
      });
      // this.$emit("change", this.value);
    },
    changeSelect() {
      this.list.forEach((i) => {
        i.isCheck = i.value == this.value ? true : false;
      });
      this.$emit("change", this.value);
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