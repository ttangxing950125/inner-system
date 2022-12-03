import request from '@/utils/request'

// 数据配置列表
export function list(params) {
  return request({
    url: '/modelEvidence/list',
    headers: {
      isToken: false
    },
    method: 'get',
    params: params
  })
}
// 删除数据配置
export function deleteSeting(params) {
  return request({
    url: '/modelEvidence/delete',
    headers: {
      isToken: false
    },
    method: 'delete',
    params: params
  })
}
// 计算
export function calculation(params) {
  return request({
    url: '/modelEvidence/calculation',
    headers: {
      isToken: false
    },
    method: 'get',
    params: params
  })
}
// 修改新增数据
export function addOrUpdate(params) {
  return request({
    url: '/modelEvidence/addOrUpdate',
    headers: {
      isToken: false
    },
    method: 'put',
    params: params
  })
}