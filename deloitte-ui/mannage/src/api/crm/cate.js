import request from '@/utils/request'

// 查询企业属性分类列表
export function listCate(query) {
  return request({
    url: '/crm/cate/list',
    method: 'get',
    params: query
  })
}

// 查询企业属性分类详细
export function getCate(id) {
  return request({
    url: '/crm/cate/' + id,
    method: 'get'
  })
}

// 新增企业属性分类
export function addCate(data) {
  return request({
    url: '/crm/cate',
    method: 'post',
    data: data
  })
}

// 修改企业属性分类
export function updateCate(data) {
  return request({
    url: '/crm/cate',
    method: 'put',
    data: data
  })
}

// 删除企业属性分类
export function delCate(id) {
  return request({
    url: '/crm/cate/' + id,
    method: 'delete'
  })
}
