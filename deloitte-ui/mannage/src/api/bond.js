import request from "@/utils/request";

// 模糊匹配 查询债券信息
export function findBondOrEntity(parmas) {
  return request({
    url:
      "/crm/bondInfoManager/findBondOrEntity?keyword=" +
      parmas.keyword +
      "&name=" +
      parmas.name +
      "&pageNum=" +
      parmas.pageNum +
      "&pageSize=" +
      parmas.pageSize,
    headers: {
      isToken: true,
    },
    method: "post",
    data: { parmas },
  });
}

// 查询债券或是主体下相关的主体或是债券信息
export function findRelationEntityOrBond(parmas) {
  return request({
    url:
      "/crm/bondInfoManager/findRelationEntityOrBond?keyword=" +
      parmas.keyword +
      "&id=" +
      parmas.id,
    headers: {
      isToken: true,
    },
    method: "post",
    data: { parmas },
  });
}

// 新增股票主体
export function createST(parmas) {
  return request({
    url: "/crm/value/createST",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 新增债券主体
export function createBE(parmas) {
  return request({
    url: "/crm/value/createBE",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询选择的债券 查询债券的具体信息
export function findAllDetail(parmas) {
  return request({
    url:
      "/crm/bondInfoManager/findAllDetail?bondCode=" +
      parmas.bondCode +
      "&entityCode=" +
      parmas.entityCode,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 修改具体信息
export function editAllDetail(parmas) {
  return request({
    url: "/crm/bondInfoManager/editAllDetail",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 手动添加债券信息
export function insertBondInfoManual(parmas) {
  return request({
    url: "/crm/bondInfoManager/insertBondInfoManual",
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// wind下拉框
export function getWindBondType(parmas) {
  return request({
    url: "/crm/bondInfoManager/getWindBondType?id=" + parmas.id,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}

// 查询主体信息
export function getEntityInfo(parmas) {
  return request({
    url: "/crm/bondInfoManager/getEntityInfo?creditCode=" + parmas.creditCode,
    headers: {
      isToken: true,
    },
    method: "post",
    data: parmas,
  });
}
