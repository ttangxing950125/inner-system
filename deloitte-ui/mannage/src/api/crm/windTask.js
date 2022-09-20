import request from '@/utils/request'

// 查询角色1的每日任务，导入wind文件的任务列表
export function listWindTask(query) {
  return request({
    url: '/crm/windTask/list',
    method: 'get',
    params: query
  })
}

// 查询角色1的每日任务，导入wind文件的任务详细
export function getWindTask(id) {
  return request({
    url: '/crm/windTask/' + id,
    method: 'get'
  })
}

// 新增角色1的每日任务，导入wind文件的任务
export function addWindTask(data) {
  return request({
    url: '/crm/windTask',
    method: 'post',
    data: data
  })
}

// 修改角色1的每日任务，导入wind文件的任务
export function updateWindTask(data) {
  return request({
    url: '/crm/windTask',
    method: 'put',
    data: data
  })
}

// 删除角色1的每日任务，导入wind文件的任务
export function delWindTask(id) {
  return request({
    url: '/crm/windTask/' + id,
    method: 'delete'
  })
}
