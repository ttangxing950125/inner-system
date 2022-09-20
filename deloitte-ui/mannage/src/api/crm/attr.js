import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listAttr(query) {
  return request({
    url: '/crm/attr/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getAttr(id) {
  return request({
    url: '/crm/attr/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addAttr(data) {
  return request({
    url: '/crm/attr',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateAttr(data) {
  return request({
    url: '/crm/attr',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delAttr(id) {
  return request({
    url: '/crm/attr/' + id,
    method: 'delete'
  })
}
