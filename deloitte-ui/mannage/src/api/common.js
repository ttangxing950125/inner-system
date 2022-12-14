import request from "@/utils/request";

// 分组查询全部---构造父子级关系
export function getAllByGroup(params) {
  //   const govAttrDto = JSON.stringify(params);
  return request({
    url: "/crm/attr/getAllByGroup/" + params.type,
    headers: {
      isToken: true,
    },
    method: "get",
    data: params,
  });
}

// 分页查询全部政府主体
export function getListEntityByPage(params) {
  return request({
    url:
      "/crm/govInfo/getListEntityByPage?pageNum=" +
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

// 政府主体分页查询
export function getInfoList(params) {
  return request({
    url: "/crm/govInfo/getInfoList",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 政府主体分页查询
export function getOverview(params) {
  return request({
    url: "/crm/govInfo/getOverview",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 获取省市级数据
export function getGovLevel(params) {
  return request({
    url: "/crm/govInfo/getGovLevel?preGovCode=" + params.preGovCode,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 根据机构类型查询需要补充录入的信息
export function getAttrByOrganName(params) {
  return request({
    url: "/crm/attr/getAttrByOrganName?organName=" + params.organName,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 根据attrId查询选项
export function getTypeByAttrId(params) {
  return request({
    url: "/crm/intype/getTypeByAttrId?attrId=" + params.attrId,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 校验字段
export function checkData(params) {
  return request({
    url:
      "/crm/roleMain/checkData?keyword=" +
      params.keyword +
      "&target=" +
      params.target,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 校验字段
export function getFinanceSubIndu(params) {
  return request({
    url: "/crm/value/getFinanceSubIndu?attrId=" + params.attrId,
    headers: {
      isToken: true,
    },
    method: "get",
    data: params,
  });
}

// 批量查询并导出excel结果
export function importExcelByEntity(params) {
  return request({
    url: "/crm/entityInfo/importExcelByEntity?uuid=" + params.uuid+'&proIds=' + params.proIds,
    headers: {
      isToken: true,
      responseType:'blob'
    },
    responseType:'blob',
    method: "post",
    data: params,
  }, {responseType:'blob'}, {responseType:'blob'});
}

// 查询匹配进度
export function getChecking(params) {
  return request({
    url: "/crm/entityInfo/getChecking?uuid=" + params.uuid,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 根据政府名称或者政府code查询政府主体
export function getGovByParam(params) {
  return request({
    url: "/crm/govInfo/getGovByParam?param=" + params.param,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}
