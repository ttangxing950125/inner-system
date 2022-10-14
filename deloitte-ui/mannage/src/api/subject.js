import request from "@/utils/request";

// 统计政府信息
export function govList(params) {
  return request({
    url: "/crm/govInfo/govList",
    headers: {
      isToken: true,
    },
    method: "post",
    data: { params },
  });
}

// 统计整体企业主体情况
export function entityInfoList(params) {
  return request({
    url: "/crm/entityInfo/entityInfoList",
    headers: {
      isToken: true,
    },
    method: "post",
    data: { params },
  });
}

// 企业主体分类概览
export function getOverviewByGroup(params) {
  return request({
    url: "/crm/entityInfo/getOverviewByGroup",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 企业主体分类查询
export function getInfoList(params) {
  return request({
    url:
      "/crm/entityInfo/getInfoList?type=" +
      params.type +
      "&param=" +
      params.param +
      "&pageNum=" +
      params.pageNum +
      "&pageSize=" +
      params.pageSize,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 政府主体分类查询
export function getInfoListGov(params) {
  return request({
    url:
      "/crm/govInfo/getInfoList?type=" +
      params.type +
      "&param=" +
      params.param +
      "&pageNum=" +
      params.pageNum +
      "&pageSize=" +
      params.pageSize,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 企业产品
export function getProduct(params) {
  return request({
    url: "/crm/Products/getProducts",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 查询覆盖情况
export function getCov(params) {
  return request({
    url:
      "/crm/ProductsCover/getCov?entityName=" +
      params.entityName +
      "&pageNum=" +
      params.pageNum +
      "&entityType=" +
      params.entityType +
      "&pageSize=" +
      params.pageSize,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 快速查询企业上市或发债情况
export function getQuickOfCoverage(parmas) {
  return request({
    url:
      "/crm/entityInfo/getQuickOfCoverage?param=" +
      parmas.param +
      "&pageNum=" +
      parmas.pageNum +
      "&pageSize=" +
      parmas.pageSize,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// addGovInfo
export function addGovInfo(parmas) {
  return request({
    url: "/crm/govInfo/addGovInfo",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 上市企业-修改信息-根据 entityCode 查询主体详细信息
export function getInfoDetail(parmas) {
  return request({
    url: "/crm/entityInfo/getInfoDetail?entityCode=" + parmas.entityCode,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 上市企业-修改信息
export function updateInfoDetail(parmas) {
  return request({
    url: "/crm/entityInfo/updateInfoDetail",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询 企业主体股票-历史记录
export function entityInfoLogsList(parmas) {
  return request({
    url: "/entityInfoLogs/list/" + parmas.type,
    headers: {
      isToken: true,
    },
    method: "get",
    data: parmas,
  });
}
