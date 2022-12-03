<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view v-if="!$route.meta.link" :key="key" />
    </transition>
    <iframe-toggle />
  </section>
</template>

<script>
import iframeToggle from "./IframeToggle/index";

export default {
  name: "AppMain",
  components: { iframeToggle },
  computed: {
    key() {
      return this.$route.path;
    },
  },
};
</script>

<style lang="scss" scoped lang='scss'>
.app-main {
  position: relative;
  top: 120px;
  width: 100%;
  height: calc(100vh - 120px);
  overflow-y: scroll;
  // background: green;
  // margin-top: 120px;
}
</style>

