import request from "@/utils/request";

// 模糊匹配 查询债券信息
export function findBondOrEntity(parmas) {
  return request({
    url:
      "/crm/bondInfoManager/findBondOrEntity?keyword=" +
      parmas.keyword +
      "&name=" +
      parmas.name,
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
