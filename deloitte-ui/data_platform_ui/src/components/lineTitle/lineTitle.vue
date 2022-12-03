<template>
  <div class="line-title"><slot></slot></div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.line-title {
  border-left: 2px solid #444e5a;
  height: 12px;
  font-size: 12px;
  font-weight: 700;
  color: #35343a;
  line-height: 12px;
  padding-left: 10px;
}
</style>