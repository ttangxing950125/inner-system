import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listModelMaster(query) {
  return request({
    url: '/crm/modelMaster/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getModelMaster(id) {
  return request({
    url: '/crm/modelMaster/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addModelMaster(data) {
  return request({
    url: '/crm/modelMaster',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateModelMaster(data) {
  return request({
    url: '/crm/modelMaster',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delModelMaster(id) {
  return request({
    url: '/crm/modelMaster/' + id,
    method: 'delete'
  })
}
