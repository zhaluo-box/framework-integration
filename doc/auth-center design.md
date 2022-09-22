# 认证中心设计

    登录，登出，注册都需要过滤器放行白名单

## 登录

    登录基于JWT token 无状态实现。
    token 续约的方式
        单token， token本身无时间限制， 基于redis实现过期
        单token， token本身有时间限制， 基于JWT expireTime 实现， 每次访问验证时间，然后根据续约时间 校验时间，刷新token
                将新的token 设置到response header， 前端配置拦截请求 拦截header 刷新token
        双token : access_token  refresh_token
            token过期返回约定的状态码， 前端根据状态码携带refresh_token 再次请求新token, 并带着新token再次请求刚刚的失败的请求，存在一个问题，前端现在很多都是异步的，
            会存在从新发起请求入参传递的问题
            refresh_token 失效则真正意义上认为token失效
            网上还有一种解决方案：
                access_token： 过期，但是小于refresh_token 过期时间，则放行，并在response header 标记，前端拦截
                refresh_token： 前端拦截到 response header 带着refresh_token 刷新

采取单token，token有时间限制，结合http response header 来拦截刷新

## 登出

    方式一： 黑名单 登出的token 全部设置到黑名单列表（redis）， 在请求时拦截token是否被登出加入黑名单, 自动过期
    方式二： 基于中间件（redis）或者数据库记录
        缺点： 如果中间件、数据库宕机则影响整个系统
    方式二： 前端移除token记录

采取黑名单的方式，请求拦截token

## 注册

