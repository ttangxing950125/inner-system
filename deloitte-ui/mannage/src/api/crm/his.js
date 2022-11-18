import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listHis(query) {
  return request({
    url: '/crm/his/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getHis(id) {
  return request({
    url: '/crm/his/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addHis(data) {
  return request({
    url: '/crm/his',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateHis(data) {
  return request({
    url: '/crm/his',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delHis(id) {
  return request({
    url: '/crm/his/' + id,
    method: 'delete'
  })
}
