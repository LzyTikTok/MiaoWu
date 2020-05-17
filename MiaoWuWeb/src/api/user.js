import request from '@/utils/request'
import settings from '@/settings'
import qs from 'querystring'

export function login(data) {
  return request({
    url: settings.apiUrl + 'user/login',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getInfo(token) {
  return request({
    url: settings.apiUrl + 'user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
