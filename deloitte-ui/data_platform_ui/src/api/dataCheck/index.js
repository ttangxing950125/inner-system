import request from "@/utils/request";

// 数据质检列表
export function list(params) {
  return request({
    url: "/dataInspection/entityInfo/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 质检结果
export function detaileList(params) {
  return request({
    url: "/dataInspection/baseFinData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 更新信息
export function updateInfo(params) {
  return request({
    url: "/dataInspection/updateInfo",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 更新信息
export function entityList(params) {
  return request({
    url: "dataInspection/codeEntity/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

export function isArtificialInspection(year, dataId) {
  return request({
    url: "/dataInspection/isArtificialInspection/" + year + "/" + dataId,
    headers: {
      isToken: false,
    },
    method: "put",
  });
}
