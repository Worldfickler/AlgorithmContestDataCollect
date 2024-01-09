# Algorithm Contest Data Collect

Algorithm Contest Data Collect (算法竞赛数据收集，以下简称ACDC), 是一套集算法竞赛数据收集，存储，展示于一体的解决方案。

## 组成成分

ACDC由七个子模块组成，分别是:

ManageFrontend : 管理前端，使用Vue3 + element plus 开发

ManageBackend : 管理后端，使用SpringBoot开发

DisplayFrontend : 展示前端，使用Vue3 + element plus 开发

DisplayBackend : 管理后端，使用SpringBoot开发

CrawlerEndPoint : 爬取终端，第一版基于webmagic 开发(已弃用) ，第二版基于Springboot开发

CrawlerDispatcher : 爬虫调度端，使用Springboot开发

CrawlerReceiver : 数据收集端，使用Springboot开发

其中各个模块可独立启动，但如果需要完成最基本的功能需要几个模块相互配合:

数据获取功能 : CrawlerDispatcher + CrawlerEndPoint + Redis

数据获取并持久化 : 数据获取功能 + CrawlerReceiver 

管理用户数据 : ManageBackend  + ManageFrontend 

提供数据展示 : DisplayFrontend + DisplayBackend

## 启动方法

### 数据库配置

项目所需数据库放置于本项目的database.sql文件中，可使用该sql文件导入。导入时可能会出错，这是由于数据库需要以一种拓扑排序的顺序导入，而该database.sql 中的sql语句是无序的，请自行处理该问题。

### 数据获取功能

#### CrawlerDispatcher 

##### 环境配置

``${<环境变量名>:<默认值>}`` 这种格式表示这个配置首先会到环境变量中寻找是否存在该环境变量，然后如果不存在则会使用默认值，所以，针对

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.url=${DB_URL}
spring.redis.host=${REDIS_HOST}
spring.redis.port=${REDIS_PORT}
spring.redis.password=${REDIS_PASSWORD}
```

以上项目，应自行配置默认值或在环境变量中配置默认值，由于环境变量通常是使用docker环境部署时使用，并不建议在本机上进行配置，故建议自行设置默认值，针对每一项的解释如下

```properties
# 日志格式及保存设置
logging.config=classpath:log4j2.xml
# 数据源驱动类名，使用mysql可保持不变
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据源用户名
spring.datasource.username=${DB_USERNAME}
# 数据源密码
spring.datasource.password=${DB_PASSWORD}
# 数据源链接(格式如下 jdbc:mysql://<地址>:<端口>/<数据库名>)
spring.datasource.url=${DB_URL}
# redis地址
spring.redis.host=${REDIS_HOST}
# redis端口
spring.redis.port=${REDIS_PORT}
# redis密码
spring.redis.password=${REDIS_PASSWORD}
# 任务队列名
crawlerTask.stream=${CRAWLERTASK_STREAM:tCrawlerTasks}
# 任务队列组
crawlerTask.group=${CRAWLERTASK_GROUP:consumer}
# 结果队列名
resultQueue.stream=${RESULTQUEUE_STREAM:tResultQueue}
# 结果队列组
resultQueue.group=${RESULTQUEUE_GROUP:consumer}
# 错误队列名
errorQueue.stream=${ERRORQUEUE_STREAM:tErrorQueue}
# 错误队列组
errorQueue.group=${ERRORQUEUE_GROUP:consumer}
# 应用名
spring.application.name=${APP_NAME:CrawlerDispatchServer}
# 服务端口
server.port=${PORT:18080}
# 是否获取所有用户的信息
dispatcher.setting.use-all-user=false
```

##### 命令行流程

首先运行``mvn dependency:get`` 获取依赖

然后尝试``mvn spring-boot:run`` 看看能不能启动

##### idea流程

导入后点击

![image-20230118114533316](https://image-1255315175.cos.ap-shanghai.myqcloud.com/core/image-20230118114533316.png)

获取依赖

![image-20230118114626595](https://image-1255315175.cos.ap-shanghai.myqcloud.com/core/image-20230118114626595.png)

点击运行键运行

如果没有则进入主项目运行

![image-20230118121036871](https://image-1255315175.cos.ap-shanghai.myqcloud.com/core/image-20230118121036871.png)



##### CrawlerEndPoint2

由于第一版已经启用，这里仅讨论第二版的配置

```properties
# 日志配置文件
logging.config=classpath:log4j2.xml
# 是否启动web应用(由于该模块并不向外提供访问，故不启动web应用)
spring.main.web-application-type=none
# redis地址
spring.redis.host=${REDIS_HOST}
# redis端口
spring.redis.port=${REDIS_PORT}
# redis密码
spring.redis.password=${REDIS_PASSWORD}
# 应用名
spring.application.name=${APP_NAME:crawler}
crawlerTask.stream=${CRAWLERTASK_STREAM:tCrawlerTasks}
crawlerTask.group=${CRAWLERTASK_GROUP:consumer}
resultQueue.stream=${RESULTQUEUE_STREAM:tResultQueue}
resultQueue.group=${RESULTQUEUE_GROUP:consumer}
errorQueue.stream=${ERRORQUEUE_STREAM:tErrorQueue}
errorQueue.group=${ERRORQUEUE_GROUP:consumer}
# 启用redis池
spring.redis.jedis.pool.enabled=true
```

idea 启动如CrawlerDispatcher ， 但请务必先启动Dispatcher创建redis消息队列

#### CrawlerReceiver

没有新增的配置项，idea 启动如CrawlerDispatcher ， 但请务必先启动Dispatcher创建redis消息队列



#### ManageBackend

由于没有进行环境变量配置，故直接填写相关参数

```properties
logging.config=classpath:log4j2.xml
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据源
spring.datasource.username=
spring.datasource.password=
spring.datasource.url=

# redis
spring.redis.host=
spring.redis.password=
spring.redis.port=

#启用微信通知
allow.WxBot=true
# 根用户名，每次启动会寻找该用户名，如果没有则进行创建
security.rootUsername=
# 根用户密码
security.rootPassword=
# jwt密码，随便的字符串都可以
jwt.secret=
# jwt过期时间
jwt.expire=3600000
# jwt签名
jwt.issuer=ACDC/MANAGE
# 对外服务端口
server.port=8085
```

idea 启动如CrawlerDispatcher，配置完成即可启动



#### ManageFrontend

注意后端配置，在vite.config.js中

![image-20230118122603471](https://image-1255315175.cos.ap-shanghai.myqcloud.com/core/image-20230118122603471.png)

target注意配置为后端的ip:port



##### 安装依赖及启动

```bash
npm install
npm run dev
```



#### DisplayBackend

没有新增的配置项，idea 启动如CrawlerDispatcher 



#### DisplayFrontend

没有新增的配置项,注意事项及启动流程如ManageFrontend











