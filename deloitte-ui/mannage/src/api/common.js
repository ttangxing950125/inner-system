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
  const govAttrDto = JSON.stringify(params);
  return request({
    url: "/crm/govInfo/getListEntityByPage",
    headers: {
      isToken: true,
    },
    method: "post",
    data: govAttrDto,
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
