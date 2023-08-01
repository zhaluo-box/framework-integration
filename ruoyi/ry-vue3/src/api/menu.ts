/*
 * @FileDescription: 
 * @Author: ccy
 * @Date: 2023-03-05 22:19:40
 * @LastEditors: ccy
 * @LastEditTime: 2023-03-15 14:51:07
 * @FilePath: \RuoYi-Vue3-master\src\api\menu.js
 * @Description: 
 */
import request from '@/utils/request'

// 获取路由
// 获取路由
export const getRouters = () => {
  return request({
    url: '/system/menu/getRouters?belongTag=WEB',
    method: 'get'
  })
}