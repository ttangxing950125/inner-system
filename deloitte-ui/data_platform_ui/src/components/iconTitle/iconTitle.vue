<template>
  <div class="title">
    <span>
      <img
        src="../../assets/images/详情.svg"
        style="margin-right: 12px"
      /> </span
    ><slot class="title"></slot>
  </div>
</template>

<script>
export default {
  props: {
    icon: {
      type: String,
      default: "",
    },
  },
};
</script>

<style lang='scss' scoped>
.title {
  font-size: 14px;
  color: #35343a;
  font-weight: 700;
  display: flex;
  align-items: center;
}
</style>