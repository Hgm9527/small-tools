import request from '@/utils/request'
import { AxiosPromise } from 'axios'
import { UserInfo } from '@/types/api/system/user'

const BASE_API = '/system/web/api/user'

export function getUserPerm(): AxiosPromise<UserInfo> {
  return request({
    url: BASE_API + '/getUserPerm',
    method: 'get',
  })
}
