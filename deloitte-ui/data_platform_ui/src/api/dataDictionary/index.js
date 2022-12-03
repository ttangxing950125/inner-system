import request from "@/utils/request";

// 数据字典 基础层列表
export function baseData(params) {
  return request({
    url: "/dataDict/baseData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 数据字典 指标层数据字典分页
export function applyData(params) {
  return request({
    url: "/dataDict/applyData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}
// 数据字典 中间层数据字典分页
export function middleData(params) {
  return request({
    url: "/dataDict/middleData/page",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//数据字典 更新信息
export function updateInfo(params) {
  return request({
    url: "/dataDict/updateInfo",
    headers: {
      isToken: false,
    },
    method: "get",
    params: params,
  });
}

//菜单
export function menuList() {
  return request({
    url: "/dataMenu/all",
    headers: {
      isToken: false,
    },
    method: "get",
  });
}
//添加我的库表  （收藏）
export function addMenu(data) {
  return request({
    url: "/dataMenu/myLibraryTable",
    headers: {
      isToken: false,
    },
    method: "post",
    data: data,
  });
}

///我的库表
export function myMenu() {
  return request({
    url: "/dataMenu/myLibraryTable/",
    //开发调试 假数据
    headers: {
      Authorization: 1,
    },
    method: "get",
  });
}

//移除我的库表{id}
export function delMyMenu(id) {
  return request({
    url: "/dataMenu/myLibraryTable/" + id,
    //开发调试 假数据
    headers: {
      Authorization: 1,
    },
    method: "delete",
  });
}
