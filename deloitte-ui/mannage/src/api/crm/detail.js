import request from '@/utils/request'

// 查询证券发行-股票发行-首次发行明细列表
export function listDetail(query) {
  return request({
    url: '/crm/detail/list',
    method: 'get',
    params: query
  })
}

// 查询证券发行-股票发行-首次发行明细详细
export function getDetail(id) {
  return request({
    url: '/crm/detail/' + id,
    method: 'get'
  })
}

// 新增证券发行-股票发行-首次发行明细
export function addDetail(data) {
  return request({
    url: '/crm/detail',
    method: 'post',
    data: data
  })
}

// 修改证券发行-股票发行-首次发行明细
export function updateDetail(data) {
  return request({
    url: '/crm/detail',
    method: 'put',
    data: data
  })
}

// 删除证券发行-股票发行-首次发行明细
export function delDetail(id) {
  return request({
    url: '/crm/detail/' + id,
    method: 'delete'
  })
}
