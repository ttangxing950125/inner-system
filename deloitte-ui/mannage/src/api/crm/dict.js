import request from '@/utils/request'

// 查询导入的wind文件分类列表
export function listDict(query) {
  return request({
    url: '/crm/dict/list',
    method: 'get',
    params: query
  })
}

// 查询导入的wind文件分类详细
export function getDict(id) {
  return request({
    url: '/crm/dict/' + id,
    method: 'get'
  })
}

// 新增导入的wind文件分类
export function addDict(data) {
  return request({
    url: '/crm/dict',
    method: 'post',
    data: data
  })
}

// 修改导入的wind文件分类
export function updateDict(data) {
  return request({
    url: '/crm/dict',
    method: 'put',
    data: data
  })
}

// 删除导入的wind文件分类
export function delDict(id) {
  return request({
    url: '/crm/dict/' + id,
    method: 'delete'
  })
}
