<template>
  <!-- 数据层级  单选-->
  <choice-alone
    :options="option"
    :defaultValue="defaultValue"
    @change="change"
  ></choice-alone>
</template>
<script>
import choiceAlone from "./choiceAlone.vue";
export default {
  components: { choiceAlone },
  data() {
    return {
      defaultValue: 1,
      option: [
        {
          label: "基础层",
          value: 1,
        },
        {
          label: "中间层",
          value: 2,
        },
        {
          label: "指标层",
          value: 3,
        },
      ],
    };
  },
  methods: {
    change(val) {
      this.$emit("change", val);
    },
  },
};
</script>
