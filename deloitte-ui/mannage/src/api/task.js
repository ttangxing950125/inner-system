import request from "@/utils/request";

// 根据指定TaskDate查询任务完成度
export function getTaskByDate(taskDate) {
  return request({
    url: "/crm/windTask/getTaskByDate?TaskDate=" + taskDate.taskDate,
    headers: {
      isToken: true,
    },
    method: "get",
    data: { taskDate },
  });
}

// 根据指定TaskDate,获取当月的任务信息
export function queryList(taskDate) {
  return request({
    url: "/crm/windTask/queryList?TaskDate=" + taskDate.taskDate,
    headers: {
      isToken: true,
    },
    method: "get",
    data: { taskDate },
  });
}

// 查询某一天角色1某个分类的wind任务
export function findTaskDetails(taskDate) {
  return request({
    url:
      "/crm/windTask/findTaskDetails?taskCateId=" +
      taskDate.taskCateId +
      "&taskDate=" +
      taskDate.taskDate,
    headers: {
      isToken: true,
    },
    method: "get",
    data: { taskDate },
  });
}

// 进行wind任务
export function doTask(params) {
  return request({
    url: "/windTask/doTask",
    headers: {
      isToken: true,
    },
    method: "post",
    data: { params },
  });
}

// 注册方法
export function register(data) {
  return request({
    url: "/auth/register",
    headers: {
      isToken: false,
    },
    method: "post",
    data: data,
  });
}

// 刷新方法
export function refreshToken() {
  return request({
    url: "/auth/refresh",
    method: "post",
  });
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: "/system/user/getInfo",
    method: "get",
  });
}

// 退出方法
export function logout() {
  return request({
    url: "/auth/logout",
    method: "delete",
  });
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: "/code",
    headers: {
      isToken: false,
    },
    method: "get",
    timeout: 20000,
  });
}
