import request from '@/utils/request'

// 查询新债发行-新发行债券-20220801-20220914列表
export function listIss(query) {
  return request({
    url: '/crm/iss/list',
    method: 'get',
    params: query
  })
}

// 查询新债发行-新发行债券-20220801-20220914详细
export function getIss(id) {
  return request({
    url: '/crm/iss/' + id,
    method: 'get'
  })
}

// 新增新债发行-新发行债券-20220801-20220914
export function addIss(data) {
  return request({
    url: '/crm/iss',
    method: 'post',
    data: data
  })
}

// 修改新债发行-新发行债券-20220801-20220914
export function updateIss(data) {
  return request({
    url: '/crm/iss',
    method: 'put',
    data: data
  })
}

// 删除新债发行-新发行债券-20220801-20220914
export function delIss(id) {
  return request({
    url: '/crm/iss/' + id,
    method: 'delete'
  })
}
