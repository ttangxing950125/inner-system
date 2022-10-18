import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/redirect"),
      },
    ],
  },
  {
    path: "/login",
    component: () => import("@/views/login"),
    hidden: true,
  },
  {
    path: "/register",
    component: () => import("@/views/register"),
    hidden: true,
  },
  {
    path: "/404",
    component: () => import("@/views/error/404"),
    hidden: true,
  },
  {
    path: "/401",
    component: () => import("@/views/error/401"),
    hidden: true,
  },
  {
    path: "",
    component: Layout,
    redirect: "index",
    meta: { title: "每日运维", icon: "system" },
    children: [
      {
        path: "index",
        component: () => import("@/views/dashboard/index.vue"),
        name: "Index",
        meta: { title: "每日运维" },
      },
      {
        path: "work",
        component: () => import("@/views/dashboard/work.vue"),
        name: "work",
        meta: { title: "导入任务清单" },
        hidden: true,
      },
    ],
  },
  {
    path: "/subjectManagement",
    component: Layout,
    redirect: "subjectManagement",
    meta: { title: "主体管理" },
    children: [
      {
        path: "/subjectManagement/overview",
        component: () => import("@/views/subjectManagement/index"),
        name: "subjectManagement",
        meta: { title: "总览" },
      },
      {
        path: "/subjectManagement/enterprise",
        component: () => import("@/views/subjectManagement/enterprise.vue"),
        name: "enterprise",
        meta: { title: "企业主体管理" },
      },
      {
        path: "/subjectManagement/government",
        component: () => import("@/views/subjectManagement/government.vue"),
        name: "government",
        meta: { title: "政府主体管理" },
      },
      {
        path: "/subjectManagement/addGovernment",
        component: () => import("@/views/subjectManagement/addGovernment.vue"),
        name: "addGovernment",
        meta: { title: "新增政府主体" },
        hidden: true,
      },
      {
        path: "/subjectManagement/eidtGovernment",
        component: () => import("@/views/subjectManagement/eidtGovernment"),
        name: "eidtGovernment",
        meta: { title: "修改政府主体信息" },
        hidden: true,
      },
      {
        path: "/subjectManagement/historyGovernment",
        component: () =>
          import("@/views/subjectManagement/historyGovernment.vue"),
        name: "historyGovernment",
        meta: { title: "政府主体信息更新记录" },
        hidden: true,
      },
      {
        path: "/subjectManagement/indexGovernment",
        component: () => import("@/views/subjectManagement/indexGovernment"),
        name: "indexGovernment",
        meta: { title: "政府主体更多指标" },
        hidden: true,
      },
      {
        path: "/subjectManagement/addEnterprise",
        component: () => import("@/views/subjectManagement/addEnterprise.vue"),
        name: "addEnterprise",
        meta: { title: "新增企业主体" },
        hidden: true,
      },
      {
        path: "/subjectManagement/eidtEnterprise",
        component: () => import("@/views/subjectManagement/eidtEnterprise.vue"),
        name: "eidtEnterprise",
        meta: { title: "修改企业主体信息" },
        hidden: true,
      },
      {
        path: "/subjectManagement/historyEnterprise",
        component: () =>
          import("@/views/subjectManagement/historyEnterprise.vue"),
        name: "historyEnterprise",
        meta: { title: "企业主体更新记录" },
        hidden: true,
      },
      {
        path: "/subjectManagement/indexEnterprise",
        component: () =>
          import("@/views/subjectManagement/indexEnterprise.vue"),
        name: "indexEnterprise",
        meta: { title: "企业主体更多指标" },
        hidden: true,
      },
    ],
  },
  {
    path: "/subTable",
    component: Layout,
    redirect: "subTable",
    meta: { title: "副表管理" },
    children: [
      {
        path: "index",
        component: () => import("@/views/subTable/index.vue"),
        name: "subTable",
        meta: { title: "曾用名管理" },
      },
      {
        path: "debt",
        component: () => import("@/views/subTable/debt.vue"),
        name: "debt",
        meta: { title: "债券信息管理" },
      },
      {
        path: "exposure",
        component: () => import("@/views/subTable/exposure.vue"),
        name: "exposure",
        meta: { title: "主体敞口划分管理" },
      },
    ],
  },
  {
    path: "/user",
    component: Layout,
    hidden: true,
    redirect: "noredirect",
    children: [
      {
        path: "profile",
        component: () => import("@/views/system/user/profile/index"),
        name: "Profile",
        meta: { title: "个人中心", icon: "user" },
      },
    ],
  },
];

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: "/system/user-auth",
    component: Layout,
    hidden: false,
    permissions: ["system:user:edit"],
    children: [
      {
        path: "role/:userId(\\d+)",
        component: () => import("@/views/system/user/authRole"),
        name: "AuthRole",
        meta: { title: "分配角色", activeMenu: "/system/user" },
      },
    ],
  },
  {
    path: "/system/role-auth",
    component: Layout,
    hidden: false,
    permissions: ["system:role:edit"],
    children: [
      {
        path: "user/:roleId(\\d+)",
        component: () => import("@/views/system/role/authUser"),
        name: "AuthUser",
        meta: { title: "分配用户", activeMenu: "/system/role" },
      },
    ],
  },
  {
    path: "/system/dict-data",
    component: Layout,
    hidden: false,
    permissions: ["system:dict:list"],
    children: [
      {
        path: "index/:dictId(\\d+)",
        component: () => import("@/views/system/dict/data"),
        name: "Data",
        meta: { title: "字典数据", activeMenu: "/system/dict" },
      },
    ],
  },
  {
    path: "/tool/gen-edit",
    component: Layout,
    hidden: false,
    permissions: ["tool:gen:edit"],
    children: [
      {
        path: "index/:tableId(\\d+)",
        component: () => import("@/views/tool/gen/editTable"),
        name: "GenEdit",
        meta: { title: "修改生成配置", activeMenu: "/tool/gen" },
      },
    ],
  },
];

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch((err) => err);
};

export default new Router({
  mode: "history", // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes,
  base: "/crm-door/",
});
