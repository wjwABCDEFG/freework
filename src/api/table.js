import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/job/table/list',
    method: 'get',
    params
  })
}
