// export const sourcesList = 

<template>
  <!-- 数据来源 默认选中全部 -->
  <choice-all
    :options="option"
    :defaultValue="defaultValue"
    @change="change"
  ></choice-all>
</template>

<script>
import choiceAll from "./choiceAll.vue";
export default {
  components: { choiceAll },
  data() {
    return {
      defaultValue: [],
      option: [
        {
          label: "wind数据",
          value: 1,
        },
        {
          label: "wind客户端",
          value: 3,
        },
        {
          label: "同花顺",
          value: 4,
        },
        {
          label: "OCR",
          value: 5,
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

