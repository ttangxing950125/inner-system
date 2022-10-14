import request from "@/utils/request";

// 根据指定TaskDate查询任务完成度
export function getTaskByDate(taskDate) {
  return request({
    url:
      "/crm/windTask/getTaskByDate?taskDate=" +
      taskDate.taskDate +
      "&pageNum=" +
      taskDate.pageNum +
      "&pageSize=" +
      taskDate.pageSize,
    headers: {
      isToken: true,
    },
    method: "post",
    data: taskDate,
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
export function getTaskInfo(params) {
  return request({
    url:
      "/crm//roleTwo/getTaskInfo?date=" +
      params.date +
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
    url:
      "/crm/roleSeven/getDayTaskInfo?date=" +
      date.date +
      "&pageNum=" +
      date.pageNum +
      "&pageSize=" +
      date.pageSize,
    headers: {
      isToken: true,
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

// 根据 entityCode 补充录入副表信息
export function addEntityAttrValuesNew(params) {
  return request({
    url: "/crm/value/addEntityAttrValuesNew",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 查询当日任务 角色2
export function getDayTaskInfoTwo(params) {
  return request({
    url: "/crm/roleTwo/getDayTaskInfo?date=" + params.date,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 敞口划分，选中单行开始工作 传入id后返回窗口 角色2
export function getTable(params) {
  return request({
    url: "/crm/roleTwo/getTable?id=" + params.id,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// getFinances 角色2
export function getFinances(params) {
  return request({
    url: "/crm/roleTwo/getFinances",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 获取上级地方政府行政编码 角色2
export function getPreGovName(params) {
  return request({
    url: "/crm/roleTwo/getPreGovName?govCode=" + params.govCode,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 获取敞口信息 角色2
export function getModelMaster(params) {
  return request({
    url: "/crm/roleTwo/getModelMaster",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 提交表单 角色2
export function insertMas(params) {
  return request({
    url: "/crm/roleTwo/insertMas",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 获取顶级 角色2
export function getGovLevelBig(params) {
  return request({
    url: "/crm/roleTwo/getGovLevelBig",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 获取子集 角色2
export function getGovLevelSmall(params) {
  return request({
    url: "/crm/roleTwo/getGovLevelSmall?id=" + params.id,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 新增政府 角色2
export function insertGov(params) {
  return request({
    url: "/crm/roleTwo/insertGov",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 新增 角色7
export function addSeven(params) {
  return request({
    url: "/crm/roleSeven/add",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 忽略任务 角色7
export function ignoreTask(params) {
  return request({
    url: "/crm/roleSeven/ignoreTask?id=" + params.id,
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 获取当日任务
export function getTaskCount(params) {
  return request({
    url: "/crm/windTask/getTaskCount?TaskDate=" + params.TaskDate,
    headers: {
      isToken: true,
    },
    method: "get",
    data: params,
  });
}

// 金融机构根据entityCode补充录入副表信息
export function addFinEntitySubtableMsg(params) {
  return request({
    url: "/crm/entityFinancial/addFinEntitySubtableMsg",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 城投机构根据entityCode补充录入副表信息
export function addGovEntitySubtableMsg(params) {
  return request({
    url: "/crm/entityGovRel/addGovEntitySubtableMsg",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}

// 财报收数根据entityCode补充录入信息--主表
export function addEntityeMsg(params) {
  return request({
    url: "/crm/entityInfo/addEntityeMsg",
    headers: {
      isToken: true,
    },
    method: "post",
    data: params,
  });
}
