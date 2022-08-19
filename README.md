# 概述：

## 框架集成示例

    主要用于框架集成，不太涉及太多的业务，主要为了自己练习各种技术，集成各种框架。以及一个框架应该有的功能

## 框架结构（framework-structure）

# Project Name ： framework-integration

## Model Category :

- business-center : 业务中心
- system-management-center： 系统管理中心
    - sys-monitor: 系统监控
    - sys-file: 文件统一管理
    - sys-base: 系统基础服务：
        - controller
            - 认证登录 (logController)
        - entity:
            - 用户(account)：
            - 部门(dept)：
            - 菜单(menu)：
            - 角色(role)：
            - 资源(resource) 也可以叫做接口：
            - 审批（approve 直接嵌入）：
            - 字典（dict）： 主要用于前端展示， 或者一些客户自定义的字典
                - 系统字典： 对于系统枚举的映射，别名，主要用于一些下拉菜单，form填写，删除可能会影响系统正常运行
                - 客户自定义字典。 客户无论如何删除自定义字典，都不影响系统的正常运行
- task-center: 任务管理中心 集成xxl-job
- auth-center： 认证中心 考虑将
    - 功能单一简单，只负责认证相关的东西
    - 登录
    - 登出
- gateway: 网关
- common： 公共资源， 包含一些工具类，通用常量，枚举，断言等
- framework-common: 主要存放一些starter之类的

## 工具类：

- JWT 工具类

## 代集成的一些功能

- 集成swagger ui
    - 通过swagger ui与apiFox 结合使用方便代码调试 
    
