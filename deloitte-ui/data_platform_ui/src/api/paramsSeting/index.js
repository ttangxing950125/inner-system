import request from "@/utils/request";

// 参数配置表
export function list(params) {
  return request({
    url: "/parameterConfig/list",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 质检规则列表
export function modelDataCheckList(params) {
  return request({
    url: "/parameterConfig/modelDataCheckList",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 质检规则修改/新增
export function updateOrAdd(params) {
  return request({
    url: "/parameterConfig/updateOrAdd",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 新增/修改参数配置(中间层)
export function addOrUpdateMiddle(data) {
  return request({
    url: "/parameterConfig/addOrUpdateMiddle",
    headers: {
      isToken: false,
    },
    method: "post",
    data: data,
  });
}
// 新增/修改参数配置(基础层)
export function addOrUpdateBase(params) {
  return request({
    url: "/parameterConfig/addOrUpdateBase",
    headers: {
      isToken: false,
    },
    method: "post",
    params: params,
  });
}
// 新增/修改参数配置(指标层)
export function addOrUpdateApply(data) {
  return request({
    url: "/parameterConfig/addOrUpdateApply",
    headers: {
      isToken: false,
    },
    method: "post",
    data: data,
  });
}

//获取基础层主体类型
export function getBaseMenu(params) {
  return request({
    url: "/parameterConfig/getBaseConfigDict",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//质检规则校验
export function checkRules(params) {
  return request({
    url: "/parameterConfig/checkData",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
