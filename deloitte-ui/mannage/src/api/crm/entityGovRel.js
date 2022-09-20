import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listEntityGovRel(query) {
  return request({
    url: '/crm/entityGovRel/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getEntityGovRel(id) {
  return request({
    url: '/crm/entityGovRel/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addEntityGovRel(data) {
  return request({
    url: '/crm/entityGovRel',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateEntityGovRel(data) {
  return request({
    url: '/crm/entityGovRel',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delEntityGovRel(id) {
  return request({
    url: '/crm/entityGovRel/' + id,
    method: 'delete'
  })
}
