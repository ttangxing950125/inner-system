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
      "/crm/bondInfoManager/bondInfoManager/findRelationEntityOrBond?keyword=" +
      parmas.keyword +
      "&code=" +
      parmas.code,
    headers: {
      isToken: true,
    },
    method: "post",
    data: { parmas },
  });
}
