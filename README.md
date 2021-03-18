## 目录

- [项目基本说明](#项目基本说明)
- [开发工具及所需环境](#开发工具及所需环境)
- [目录结构说明](#目录结构说明)
- [项目开发](#项目开发)
- [注意事项](#注意事项)
- [命名规范](#命名规范)

## 项目基本说明

- 名称

Bali-Framework

- 作用

用于快速搭建后端Api或Web项目，提供统一的基于OAuth和Jwt的认证中心
 
- git 地址

https://gitee.com/union_tech_company/bali-framework.git

## 开发工具及所需环境

- 开发工具

1. Idea （推荐）
2. Eclipse

- 所需环境

1. JDK 1.8或以上，支持11、15等版本
2. Maven 3.6.0或以上
3. Nginx 1.15.1或以上
4. Redis 3.4或以上
5. RabbitMQ 3.8或以上
5. MySQL 8.0或以上

## 目录结构说明

```
bali-framework
├─bali-auth-server
├─bali-core 公共支撑
├─bali-examples 示例项目
├─bali-extensions 第三方扩展支撑
```

## 端口说明
- 公认端口（Well Known Ports）：从0到1023，它们紧密绑定（binding）于一些服务。通常这些端口的通讯明确表明了某种服务的协议。例如：80端口实际上总是HTTP通讯。
- 注册端口（Registered Ports）：从1024到49151。它们松散地绑定于一些服务。也就是说有许多服务绑定于这些端口，这些端口同样用于许多其它目的。例如：许多系统处理动态端口从1024左右开始。
- 动态和/或私有端口（Dynamic and/or Private Ports）：从49152到65535。理论上，不应为服务分配这些端口。实际上，机器通常从1024起分配动态端口。但也有例外：SUN的RPC端口从32768开始。


### 中间件端口
6379 | redis | Redis端口

### 数据库端口
3306 | mysql | MYSQL连接端口

## 项目开发

## 注意事项
> 设置完成后需要重启IDE

框架依赖制品采用阿里云效统一管理，需要管理员添加至企业制品仓库，并进行maven配置，配置方式如下
```xml
<!--将云效提供的仓库凭证配置到本机用户目录下的maven settings.xml-->
<!--通常settings.xml在$HOME/.m2/文件目录下-->
<servers>
    <server>
        <!--注意ID需要修改成如下值-->
        <id>ali-maven-rdc-releases</id>
        <username>************************</username>
        <password>************</password>
    </server>
    <server>
        <!--注意ID需要修改成如下值-->
        <id>ali-maven-rdc-snapshots</id>
        <username>************************</username>
        <password>************</password>
    </server>
</servers>
```

## 命名规范
### Service接口命名
3. 持久层Service 则必须是 IXxxService 样式
### DTO命名
> 数据传输对象，这个概念来源于J2EE的设计模式，原来的目的是为了EJB的分布式应用提供粗粒度的数据实体，以减少分布式调用的次数，从而提高分布式调用的性能和降低网络负载，但在这里，我泛指用于服务层之间的数据传输对象。
用于服务内部调用过程中的传输对象
1. 对象命名均为 XxxDTO样式，且实现IDTO接口
### VO命名
> 视图对象，用于展示层，它的作用是把某个指定页面（或组件）的所有数据封装起来
1. 对象命名均为 XxxVO样式，且实现IVO接口

## 更新日志

序号  | 版本号 | 日期 | 修改人 | 概述
--- | --- | --- | --- | ---
1 | 0.1.0-SNAPSHOT | 2020-12-31 | Petty | 初版
2 | 0.2.0-SNAPSHOT | 2020-01-20 | Petty | 初版
3 | 0.3.0-SNAPSHOT | 2021-01-31 | Petty | 初版
4 | 0.4.0-SNAPSHOT | 2021-03-10 | Petty | 增加配置文件加密功能；修复微信登录user_id混乱的问题
5 | 0.5.0-SNAPSHOT | 2021-03-18 | Petty | 将依赖管理单独拆分出模块，同时新增starter便于子项目依赖


