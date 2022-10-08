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

// 查询指定日期或当月任务情况,返回 List
export function getTaskInfo(taskDate) {
  return request({
    url: "/crm//roleTwo/getTaskInfo",
    headers: {
      isToken: true,
    },
    method: "post",
    data: taskDate,
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

// 查询当日任务
export function getDayTaskInfo(date) {
  return request({
    url: "/crm/roleSeven/getDayTaskInfo?date=" + date.date,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: date,
  });
}

// 查询当月任务
export function getMouthTaskInfo(date) {
  return request({
    url: "/crm/roleSeven/getMouthTaskInfo?date=" + date.date,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: date,
  });
}

// 校验统一社会信用代码是否存在
export function checkCreditCode(date) {
  return request({
    url: "/crm/roleSeven/checkCreditCode?creditCode=" + date.creditCode,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: date,
  });
}

// 修改主体信息中的主体名称
export function editEntityNameHis(params) {
  return request({
    url:
      "/crm/roleSeven/editEntityNameHis?creditCode=" +
      params.creditCode +
      "&entityNewName=" +
      params.entityNewName,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: params,
  });
}

// 确认该任务的主体是新增或是忽略
export function changeState(params) {
  return request({
    url:
      "/crm/roleSeven/changeState?id=" + params.id + "&state=" + params.state,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: params,
  });
}

// 登录用户角色的信息补充任务 角色6
export function getRoleSupplyTask(params) {
  return request({
    url: "/crm/roletff/getRoleSupplyTask?taskDate=" + params.taskDate,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: params,
  });
}

// 根据任务id查询补充信息 角色6
export function getTaskByEntityCode(params) {
  return request({
    url:
      "/crm/attr/getTaskByEntityCode?entityCode=" +
      params.entityCode +
      "&roleId=" +
      params.roleId,
    headers: {
      isToken: true,
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: params,
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
