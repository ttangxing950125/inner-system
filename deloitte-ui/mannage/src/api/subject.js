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
    url: "/crm/entityInfoLogs/list/" + parmas.type,
    headers: {
      isToken: true,
    },
    method: "get",
    data: parmas,
  });
}

// 撤销停用
export function logCancel(parmas) {
  return request({
    url: "/crm/entityInfoLogs/cancel/" + parmas.id,
    headers: {
      isToken: true,
    },
    method: "get",
    data: parmas,
  });
}

// 地方政府-更多指标-主体范围
export function getGovRange(parmas) {
  return request({
    url: "/crm/govInfo/getGovRange",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 企业主体分类查询
export function getListEntityByPage(parmas) {
  return request({
    url:
      "/crm/entityInfo/getListEntityByPage?pageNum=" +
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

// 查询政府曾用名列表
export function getGovHisNameList(parmas) {
  return request({
    url:
      "/crm/his/getGovHisNameList?pageNum=" +
      parmas.pageNum +
      "&pageSize=" +
      parmas.pageSize +
      "&param=" +
      parmas.param,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询企业曾用名列表
export function getEntityHisNameList(parmas) {
  return request({
    url:
      "/crm/his/getEntityHisNameList?pageNum=" +
      parmas.pageNum +
      "&pageSize=" +
      parmas.pageSize +
      "&param=" +
      parmas.param,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 修改企业主体的曾用名
export function updateOldName(parmas) {
  return request({
    url:
      "/crm/entityInfo/updateOldName?dqCode=" +
      parmas.dqCode +
      "&newOldName=" +
      parmas.newOldName +
      "&oldName=" +
      parmas.oldName +
      "&status=" +
      parmas.status +
      "&remarks=" +
      parmas.remarks,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 修改企业主体的曾用名
export function updateOldNameGov(parmas) {
  return request({
    url:
      "/crm/govInfo/updateOldName?dqCode=" +
      parmas.dqCode +
      "&newOldName=" +
      parmas.newOldName +
      "&oldName=" +
      parmas.oldName +
      "&status=" +
      parmas.status,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 企业主体清单-查询上市概览
export function getListView(parmas) {
  return request({
    url: "/crm/entityInfo/getListView?type="+parmas.type,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 政府主体清单-地方政府概览
export function getGovView(parmas) {
  return request({
    url: "/crm/govInfo/getGovView",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 上市企业-地方政府-更新记录 by正杰
export function getInfoUpdate(parmas) {
  return request({
    url: "/crm/updateRecords/getInfo?pageNum=" + parmas.pageNum + '&pageSize=' + parmas.pageSize + '&tableType='+parmas.tableType,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 根据政府名称查询政府主体
export function getGovByName(parmas) {
  return request({
    url: "/crm/govInfo/getGovByName?govName=" + parmas.govName ,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 根据 dqCode 查询政府主体
export function getInfoDetailGov(parmas) {
  return request({
    url: "/crm/govInfo/getInfoDetail?dqGovCode=" + parmas.dqGovCode ,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 政府主体批量修改
export function updateInfoList(parmas) {
  return request({
    url: "/crm/govInfo/updateInfoList",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 敞口划分根据主体名称查询信息
export function entityMaster(parmas) {
  return request({
    url: "/crm/entityInfo/entityMaster?name=" + parmas.name,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 根据主体code查询主体信息
export function entityByMaster(parmas) {
  return request({
    url: "/crm/entityInfo/entityByMaster?code=" + parmas.code,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// {查询修改记录}
export function getDataHis(parmas) {
  return request({
    url: "/crm/ProMaRelHis/getDataHis",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询产品年份
export function getDataYear(parmas) {
  return request({
    url: "/crm/ProMaRel/getDataYear",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询产品客户敞口信息
export function getProDucCom(parmas) {
  return request({
    url: "/crm/ProMaRel/getProDucCom?dataYear="+parmas.dataYear+'&entityCode='+parmas.entityCode+'&proId='+parmas.proId,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 修改产品信息
export function updateRel(parmas) {
  return request({
    url: "/crm/ProMaRel/updateRel",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询敞口信息
export function ProMaDir(parmas) {
  return request({
    url: "/crm/ProMaDir/ProMaDir?proCustId="+parmas.proCustId,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 下载查询产品
export function getProductsOne(parmas) {
  return request({
    url: "/crm/Products/getProductsOne?id="+parmas.id,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 下载查询产品 导出
export function getProductsExcel(parmas) {
  return request({
    url: "/crm/Products/getProductsExcel?id="+parmas.id,
    headers: {
      isToken: true,
      responseType:'blob'
    },
    responseType:'blob',
    method: "post",
    data: parmas,
  },{responseType:'blob'}, {responseType:'blob'});
}

// 政府级别
export function getGovLevel(parmas) {
  return request({
    url: "/crm/level/getGovLevel",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 总览政府导出
export function exportGov(parmas) {
  return request({
    url: "/crm/govInfo/exportGov",
    headers: {
      isToken: true,
      responseType:'blob'
    },
    responseType:'blob',
    method: "get",
    data: parmas,
  },{responseType:'blob'}, {responseType:'blob'});
}

// 总览企业导出
export function exportEntity(parmas) {
  return request({
    url: "/crm/entityInfo/exportEntity",
    headers: {
      isToken: true,
      responseType:'blob'
    },
    responseType:'blob',
    method: "get",
    data: parmas,
  },{responseType:'blob'}, {responseType:'blob'});
}

// 根据德勤code查询曾用名列表
export function getNameListByDqCoded(parmas) {
  return request({
    url: "/crm/his/getNameListByDqCoded?dqCode="+parmas.dqCode,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 新增曾用名
export function addOldName(parmas) {
  return request({
    url: "/crm/entityInfo/addOldName?entityCode="+parmas.entityCode+'&entityName='+parmas.entityName+'&entityNameHisRemarks='+parmas.entityNameHisRemarks+'&updated='+parmas.updated,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// searchCapture
export function searchCapture(parmas) {
  return request({
    url: `/crm/entityCaptureSpeed/search/${parmas}`,
    headers: {
      isToken: true,
    },
    method: "get",
    data: parmas,
  });
}

// {导出整体企业主体情况}
export function exportEntityIndex(parmas) {
  return request({
    url: `/crm/entityInfo/exportEntity`,
    headers: {
      isToken: true,
      responseType:'blob'
    },
    method: "post",
    responseType:'blob',
    data: parmas,
  }, {responseType:'blob'});
}

// 导出全部政府主体Excel表
export function govExport(parmas) {
  return request({
    url: `/crm/govInfo/govExport`,
    headers: {
      isToken: true,
      responseType:'blob'
    },
    method: "post",
    responseType:'blob',
    data: parmas,
  }, {responseType:'blob'});
}

// 根据政府主体，大类小类查询政府主体
export function getGovInfoByLevel(parmas) {
  return request({
    url: `/crm/govInfo/getGovInfoByLevel?bigLevel=`+parmas.bigLevel+'&smallLevel='+parmas.smallLevel,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}
