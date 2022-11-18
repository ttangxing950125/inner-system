import request from '@/utils/request'

// 查询IPO-新股发行资料-20210914-20221014列表
export function listIpoInfo(query) {
  return request({
    url: '/crm/ipoInfo/list',
    method: 'get',
    params: query
  })
}

// 查询IPO-新股发行资料-20210914-20221014详细
export function getIpoInfo(id) {
  return request({
    url: '/crm/ipoInfo/' + id,
    method: 'get'
  })
}

// 新增IPO-新股发行资料-20210914-20221014
export function addIpoInfo(data) {
  return request({
    url: '/crm/ipoInfo',
    method: 'post',
    data: data
  })
}

// 修改IPO-新股发行资料-20210914-20221014
export function updateIpoInfo(data) {
  return request({
    url: '/crm/ipoInfo',
    method: 'put',
    data: data
  })
}

// 删除IPO-新股发行资料-20210914-20221014
export function delIpoInfo(id) {
  return request({
    url: '/crm/ipoInfo/' + id,
    method: 'delete'
  })
}
