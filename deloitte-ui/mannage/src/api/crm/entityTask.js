import request from '@/utils/request'

// 查询角色7，根据导入的数据新增主体的任务列表
export function listEntityTask(query) {
  return request({
    url: '/crm/entityTask/list',
    method: 'get',
    params: query
  })
}

// 查询角色7，根据导入的数据新增主体的任务详细
export function getEntityTask(id) {
  return request({
    url: '/crm/entityTask/' + id,
    method: 'get'
  })
}

// 新增角色7，根据导入的数据新增主体的任务
export function addEntityTask(data) {
  return request({
    url: '/crm/entityTask',
    method: 'post',
    data: data
  })
}

// 修改角色7，根据导入的数据新增主体的任务
export function updateEntityTask(data) {
  return request({
    url: '/crm/entityTask',
    method: 'put',
    data: data
  })
}

// 删除角色7，根据导入的数据新增主体的任务
export function delEntityTask(id) {
  return request({
    url: '/crm/entityTask/' + id,
    method: 'delete'
  })
}
