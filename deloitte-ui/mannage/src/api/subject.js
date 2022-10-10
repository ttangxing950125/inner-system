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
      params.param,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}
