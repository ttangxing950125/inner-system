   <template>
  <!-- <el-select v-model="value" :placeholder="placeholder" size="mini" clearable>
    <el-option v-for="item in list" :key="item.value" :label="item.label">
     <span style="border: 1px solid red; width: 100px; display: box"> 
 <el-checkbox v-model="item.value" :label="item.label"></el-checkbox 
></span
      > 
  </el-option> 
 </el-select>  -->
  <el-select
    @change="changeSelect"
    v-model="value"
    :placeholder="placeholder"
    size="mini"
    clearable
    filterable
  >
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
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
      require: false,
    },
    placeholder: {
      type: String,
      default: "全部",
    },
  },
  data() {
    return {
      value: this.defaultValue || "",
    };
  },

  methods: {
    changeSelect() {
      // this.list.forEach((i) => {
      //   i.isCheck = i.value == this.value ? true : false;
      // });
      this.$emit("selectChange", this.value);
    },
    // clear() {
    //   this.value = "";
    //   this.list.forEach((i) => {
    //     i.isCheck = false;
    //   });
    //   this.$emit("selectChange", this.value);
    // },
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