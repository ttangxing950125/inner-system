import request from "@/utils/request";

// 基础层数据结果
export function baseDataList(params) {
  return request({
    url: "/dataExtraction/baseData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
//导出基础层数据结果
export function exportBaseData(params) {
  return request({
    url: "/dataExtraction/baseData/export",
    headers: {
      isToken: false,
    },
    method: "post",
    params: params,
  });
}

//基础层数据详情
export function baseDataDetail(params) {
  return request({
    url: "/dataExtraction/baseDataDetail/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//导出基础层数据详情
export function exportBaseDetail(params) {
  return request({
    url: "/dataExtraction/baseDataDetail/export",
    headers: {
      isToken: false,
    },
    method: "post",
    params: params,
  });
}

//中间层数据结果
export function middleDataList(params) {
  return request({
    url: "/dataExtraction/middleData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//中间层数据详情
export function middleDataDetail(params) {
  return request({
    url: "/dataExtraction/middleDataDetail/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//指标层数据结果
export function applyData(params) {
  return request({
    url: "/dataExtraction/applyData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//指标层数据详情
export function applyDataDetail(params) {
  return request({
    url: "/dataExtraction/applyDataDetail/page",
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

//页头搜索
export function getForTop(params) {
  return request({
    url: "/dataMenu/top",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
