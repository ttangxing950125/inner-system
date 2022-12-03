<template>
  <div class="flex-row" style="padding: 0px 0 12px 0; line-height: 14px">
    <span class="home" v-on="$listeners">{{ home }}</span>
    <span class="title" style="padding: 0 4px">/</span>
    <span class="title">{{ title }}</span>
  </div>
</template>

<script>
export default {
  props: {
    home: {
      type: String,
      default: "HOME",
    },
    title: {
      type: String,
      default: "child",
    },
  },
};
</script>
<style scoped lang='scss'>
.home {
  font-size: 12px;
  color: #35343a;
  font-weight: 400;
  cursor: pointer;
}
.title {
  font-size: 12px;
  color: #97999b;
  font-weight: 400;
}
</style>

