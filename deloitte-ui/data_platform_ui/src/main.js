import Vue from "vue";

import Cookies from "js-cookie";

import Element from "element-ui";
import "./assets/styles/element-variables.scss";
import echarts from "echarts";

import "@/assets/styles/index.scss"; // global css
import "@/assets/styles/deloitte.scss"; // deloitte css
import App from "./App";
import store from "./store";
import router from "./router";
import directive from "./directive"; // directive
import plugins from "./plugins"; // plugins
import { download } from "@/utils/request";

import "./assets/icons"; // icon
import "./permission"; // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree,
} from "@/utils/deloitte";
import tableStyle from "@/mixins/tableStyle.js";
// import options from "@/mixins/options.js";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar";
// 富文本组件
import Editor from "@/components/Editor";
// 文件上传组件
import FileUpload from "@/components/FileUpload";
// 图片上传组件
import ImageUpload from "@/components/ImageUpload";
// 图片预览组件
import ImagePreview from "@/components/ImagePreview";
// 字典标签组件
import DictTag from "@/components/DictTag";
// 头部标签组件
import VueMeta from "vue-meta";
// 字典数据组件
import DictData from "@/components/DictData";
//带icon得标题
import IconTitle from "@/components/iconTitle/iconTitle.vue";
import icon1Title from "@/components//iconTitle/icon1Title.vue";
import icon2Title from "@/components//iconTitle/icon2Title.vue";
//前面是竖线的title
import LineTitle from "@/components/lineTitle/lineTitle.vue";

//优化后的单选 和 多选
import choiceAlone from "@/components/selectAll/choiceAlone.vue";
import choiceAll from "@/components/selectAll/choiceAll.vue";
import yearSelect from "@/components/selectAll/yearSelect.vue"; //年份选项
import hierarchySelect from "@/components/selectAll/hierarchySelect.vue"; //数据层级
import sourcesSelect from "@/components/selectAll/sourcesSelect.vue"; //数据来源
import myMenu from "@/components/pageMenu/myMenu.vue";

//详情页返回
import goBack from "@/components/backHome/goBack.vue";

// 全局方法挂载
Vue.prototype.getDicts = getDicts;
Vue.prototype.getConfigKey = getConfigKey;
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;
Vue.prototype.selectDictLabels = selectDictLabels;
Vue.prototype.download = download;
Vue.prototype.handleTree = handleTree;
Vue.prototype.$echarts = echarts;

// 全局组件挂载
Vue.component("DictTag", DictTag);
Vue.component("Pagination", Pagination);
Vue.component("RightToolbar", RightToolbar);
Vue.component("Editor", Editor);
Vue.component("FileUpload", FileUpload);
Vue.component("ImageUpload", ImageUpload);
Vue.component("ImagePreview", ImagePreview);
Vue.component("IconTitle", IconTitle);
Vue.component("icon1Title", icon1Title);
Vue.component("LineTitle", LineTitle);
Vue.component("icon2Title", icon2Title);
Vue.component("choiceAlone", choiceAlone); //单选
Vue.component("choiceAll", choiceAll); //多选
Vue.component("yearSelect", yearSelect); //年份选项 多选 最少选择1个
Vue.component("hierarchySelect", hierarchySelect); //数据层级 默认基础层1 单选
Vue.component("sourcesSelect", sourcesSelect); //数据来源 多选 最少选择1个
Vue.component("myMenu", myMenu); //数据来源 多选 最少选择1个

Vue.component("goBack", goBack);

Vue.use(directive);
Vue.use(plugins);
Vue.use(VueMeta);
Vue.mixin(tableStyle);
DictData.install();

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get("size") || "medium", // set element-ui default size
});

Vue.config.productionTip = false;

// eventBus 子组件通信
Vue.prototype.$eventBus = new Vue();
new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
