import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listEntityBondRel(query) {
  return request({
    url: '/crm/entityBondRel/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getEntityBondRel(id) {
  return request({
    url: '/crm/entityBondRel/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addEntityBondRel(data) {
  return request({
    url: '/crm/entityBondRel',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateEntityBondRel(data) {
  return request({
    url: '/crm/entityBondRel',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delEntityBondRel(id) {
  return request({
    url: '/crm/entityBondRel/' + id,
    method: 'delete'
  })
}
