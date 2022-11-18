import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listLevel(query) {
  return request({
    url: '/crm/level/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getLevel(id) {
  return request({
    url: '/crm/level/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addLevel(data) {
  return request({
    url: '/crm/level',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateLevel(data) {
  return request({
    url: '/crm/level',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delLevel(id) {
  return request({
    url: '/crm/level/' + id,
    method: 'delete'
  })
}
