<template>
  <div class="app-wrapper">
    <div class="fixed-header">
      <navbar />
      <sidebar />
    </div>

    <app-main />
  </div>
</template>

<script>
import { AppMain, Navbar, Sidebar } from "./components";
import ResizeMixin from "./mixin/ResizeHandler";
import { mapState } from "vuex";

export default {
  name: "Layout",
  components: {
    AppMain,
    Navbar,
    Sidebar,
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapState({
      theme: (state) => state.settings.theme,
      sideTheme: (state) => state.settings.sideTheme,
      sidebar: (state) => state.app.sidebar,
    }),
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/mixin.scss";
@import "~@/assets/styles/variables.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: 100%;
  transition: width 0.28s;
  background-image: linear-gradient(176deg, #707c94 0%, #566272 68%);
}
</style>
