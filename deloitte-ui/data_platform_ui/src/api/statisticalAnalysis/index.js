import request from "@/utils/request";
//数据统计分析

//总览
//列表
export function overviewList(params) {
  return request({
    url: "/statisticalDataAnalysis/overview/list",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
//数据来源覆盖度 图表
export function sourceCoverage(params) {
  return request({
    url: "/statisticalDataAnalysis/overview/sourceCoverage",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//缺失率统计
export function missRate(params) {
  return request({
    url: "/statisticalDataAnalysis/businessScenario/missRate",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//客户列表
export function customerPage(params) {
  return request({
    url: "/statisticalDataAnalysis/businessScenario/customerPage",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

// 基础质量
// export function list(params) {
//   return request({
//     url: "/modelEvidence/list",
//     headers: {
//       isToken: false,
//     },
//     method: "get",
//     params: params,
//   });
// }

//勾基关系
//列表
export function baseQuality(params) {
  return request({
    url: "/dataStatistical/hookArticulation/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//质检结果
// 基础层质检结果
export function qualityBase(params) {
  return request({
    url: "/dataStatistical/baseQuality/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
//中间层数据结果
export function qualityMiddle(params) {
  return request({
    url: "/dataStatistical/middleQuality/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//指标层数据结果
export function qualityApply(params) {
  return request({
    url: "/dataStatistical/applyQuality/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//获取年份选项
export function getYears(params) {
  return request({
    url: "/dataInspection/years",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
//查询有数据的最近三条年份
export function getYears3(params) {
  return request({
    url: "/statisticalDataAnalysis/overview/years",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

// 人工补录字段情况-条形图表
export function artificialRecordingBar(params) {
  return request({
    url: "/statisticalDataAnalysis/businessScenario/artificialRecordingBar",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//人工补录字段情况-圆形图表
export function recordingAll(params) {
  return request({
    url: "/statisticalDataAnalysis/businessScenario/recordingAll",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//数据缺失率总占比统计
export function missRateAll(params) {
  return request({
    url: "/statisticalDataAnalysis/businessScenario/missRateAll",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//使用场景列表
export function getOverview(params) {
  return request({
    url: "/statisticalDataAnalysis/overview/getDataMenu",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 人工补录字段情况-圆形图表
export function artificiaCircular(params) {
  return request({
    url: "/statisticalDataAnalysis/businessScenario/artificialRecordingCircular",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
