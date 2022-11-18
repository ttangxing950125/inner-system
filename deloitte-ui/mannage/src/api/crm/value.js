import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listValue(query) {
  return request({
    url: '/crm/value/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getValue(id) {
  return request({
    url: '/crm/value/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addValue(data) {
  return request({
    url: '/crm/value',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateValue(data) {
  return request({
    url: '/crm/value',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delValue(id) {
  return request({
    url: '/crm/value/' + id,
    method: 'delete'
  })
}
