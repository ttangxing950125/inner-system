import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listGovInfo(query) {
  return request({
    url: '/crm/govInfo/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getGovInfo(id) {
  return request({
    url: '/crm/govInfo/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addGovInfo(data) {
  return request({
    url: '/crm/govInfo',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateGovInfo(data) {
  return request({
    url: '/crm/govInfo',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delGovInfo(id) {
  return request({
    url: '/crm/govInfo/' + id,
    method: 'delete'
  })
}
