import request from '@/utils/request'

// 查询立案调查事项表列表
export function listInvestigation(query) {
  return request({
    url: '/deloitte-business/investigation/list',
    method: 'get',
    params: query
  })
}

// 查询立案调查事项表详细
export function getInvestigation(factor01) {
  return request({
    url: '/deloitte-business/investigation/' + factor01,
    method: 'get'
  })
}

// 新增立案调查事项表
export function addInvestigation(data) {
  return request({
    url: '/deloitte-business/investigation',
    method: 'post',
    data: data
  })
}

// 修改立案调查事项表
export function updateInvestigation(data) {
  return request({
    url: '/deloitte-business/investigation',
    method: 'put',
    data: data
  })
}

// 删除立案调查事项表
export function delInvestigation(factor01) {
  return request({
    url: '/deloitte-business/investigation/' + factor01,
    method: 'delete'
  })
}
