import request from '@/utils/request'
import settings from '@/settings'
import qs from 'querystring'

export function login(data) {
  return request({
    url: settings.apiUrl + 'users/login',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getInfo(token) {
  return request({
    url: settings.apiUrl + 'users/info',
    method: 'get',
    params: { token }
  })
}

export function logout(token) {
  let form = {
    token: token
  }
  return request({
    url: settings.apiUrl + '/users/logout',
    method: 'post',
    data: qs.stringify(form)
  })
}
