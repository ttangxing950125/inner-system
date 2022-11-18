import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listEntityInfo(query) {
  return request({
    url: '/crm/entityInfo/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getEntityInfo(id) {
  return request({
    url: '/crm/entityInfo/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addEntityInfo(data) {
  return request({
    url: '/crm/entityInfo',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateEntityInfo(data) {
  return request({
    url: '/crm/entityInfo',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delEntityInfo(id) {
  return request({
    url: '/crm/entityInfo/' + id,
    method: 'delete'
  })
}
