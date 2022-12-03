<template>
  <div class="title">
    <span>
      <i
        class="el-icon-office-building"
        style="font-size: 18px; font-weight: 600; margin-right: 8px"
      ></i></span
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