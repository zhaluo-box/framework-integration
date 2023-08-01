/*
 * @FileDescription: 
 * @Author: ccy
 * @Date: 2023-03-05 22:19:40
 * @LastEditors: ccy
 * @LastEditTime: 2023-03-17 14:03:41
 * @FilePath: \zr_szhpt_web\src\store\modules\user.ts
 * @Description: 
 */
import { login, logout, getInfo, refreshToken } from '@/api/login.ts'
import { getToken, setToken, setExpiresIn, removeToken } from '@/utils/auth.ts'
import { defineStore } from "pinia";
import { requirePath } from "@/utils/requireImg.ts";
const useUserStore = defineStore(
  'user',
  {
    state: () => ({
      token: getToken(),
      name: '',
      avatar: '',
      roles: [],
      permissions: [],
      sysName: '',
      sysLogin: '',
      expires_in:''
    }),
    actions: {
      // 登录
      login(userInfo) {
        const username = userInfo.username.trim()
        const password = userInfo.password
        const code = userInfo.code
        const uuid = userInfo.uuid
        return new Promise((resolve, reject) => {
          login(username, password, code, uuid).then(res => {
            let data = res.data
            setToken(data.access_token)
            this.token = data.access_token;
            setExpiresIn(data.expires_in)
            this.expires_in = data.expires_in
            resolve(data)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 获取用户信息
      getInfo() {
        return new Promise((resolve, reject) => {
          getInfo().then(res => {
            const user = res.user
            const avatar = user.avatar == "" ? requirePath("@/assets/images/profile.jpg") : user.avatar;
            const sysName = res.systemName || '粉末事业部MES系统'
            const sysLogin = res.logo || requirePath("@/assets/logo/logo.png")
            if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
              this.roles = res.roles
              this.permissions = res.permissions
            } else {
              this.roles = ['ROLE_DEFAULT']
            }
            this.name = user.userName
            this.avatar = avatar;
            this.sysName = sysName;
            this.sysLogin = sysLogin;
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 刷新token
    refreshToken() {
      return new Promise((resolve, reject) => {
        refreshToken(this.token).then(res => {
          setExpiresIn(res.data)
          this.expires_in = res.data
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出系统
    logOut() {
      return new Promise((resolve, reject) => {
        logout(this.token).then(() => {
          setToken("")
          this.token = "";
          this.roles = [];
          this.permissions = [];
          removeToken()
          resolve("")
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 前端 登出
    FedLogOut() {
      return new Promise(resolve => {
        setToken("")
        this.token = "";
        removeToken()
        resolve("")
      })
    }
    }
  })

export default useUserStore
