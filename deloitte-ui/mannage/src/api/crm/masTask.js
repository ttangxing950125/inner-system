import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listMasTask(query) {
  return request({
    url: '/crm/masTask/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getMasTask(id) {
  return request({
    url: '/crm/masTask/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addMasTask(data) {
  return request({
    url: '/crm/masTask',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateMasTask(data) {
  return request({
    url: '/crm/masTask',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delMasTask(id) {
  return request({
    url: '/crm/masTask/' + id,
    method: 'delete'
  })
}
