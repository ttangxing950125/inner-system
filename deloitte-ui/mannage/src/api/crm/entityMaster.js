import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listEntityMaster(query) {
  return request({
    url: '/crm/entityMaster/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getEntityMaster(id) {
  return request({
    url: '/crm/entityMaster/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addEntityMaster(data) {
  return request({
    url: '/crm/entityMaster',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateEntityMaster(data) {
  return request({
    url: '/crm/entityMaster',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delEntityMaster(id) {
  return request({
    url: '/crm/entityMaster/' + id,
    method: 'delete'
  })
}
