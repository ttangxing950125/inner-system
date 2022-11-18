import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listSupplyTask(query) {
  return request({
    url: '/crm/supplyTask/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getSupplyTask(id) {
  return request({
    url: '/crm/supplyTask/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addSupplyTask(data) {
  return request({
    url: '/crm/supplyTask',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateSupplyTask(data) {
  return request({
    url: '/crm/supplyTask',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delSupplyTask(id) {
  return request({
    url: '/crm/supplyTask/' + id,
    method: 'delete'
  })
}
