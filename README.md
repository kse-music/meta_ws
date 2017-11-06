### 一、背景
> 针对目前各项目代码结构混乱，导致维护难，他人拒绝接收的问题，本人制定出适合本公司现有的JavaWeb技术架构，整理出一个标准的架构。

### 二、目标
	让开发人员只需关注业务逻辑的实现

### 三、技术框架
1. 基于Jdk1.8
- [jdk8内存模型](http://www.cnblogs.com/paddix/p/5309550.html)
- [jdk8十大特性](http://www.jb51.net/article/48304.htm)
- [java bio nio aio](http://blog.csdn.net/wanglei_storage/article/details/50225779)

2. Web控制器Jersey2.25.1
- [资源常用注解](https://jersey.github.io/documentation/latest/jaxrs-resources.html)
- [启动方式](http://blog.csdn.net/u013628152/article/details/42126521)

3. Spring4.3.11管理javaBean，持久层mybatis3.4.2
- [spring原理剖析](http://bradyzhu.iteye.com/blog/2270692)
- [Spring 教程](https://www.w3cschool.cn/wkspring/)
- [mybatis中 #和$的区别](http://note.youdao.com/noteshare?id=4a97cad3801c22ef12adba9b18d13028&sub=AEDB30BC3CDE4F2F9E57E57772FAC7F9)

4. 已经配置好常用的相关db：MySQL5.7.19、Elasticsearch5.5.3、MongoDB3.4.6、Redis3.2.10
- [Mysql存储引擎](http://blog.csdn.net/xifeijian/article/details/20316775)
- [Elasticsearch基本概念](https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html)
- [MongoDB教程](https://www.w3cschool.cn/mongodb/)
- [Redis教程](https://www.w3cschool.cn/redis/)

5. 第三方工具：Guava23.0，Gson2.8.1
- [Guava使用指导](https://github.com/google/guava/wiki)
- [Gson解析教程](http://blog.csdn.net/axuanqq/article/details/51441590)

6. 基于Jersey Client封装的HttpUtils(调用http接口)，基于guava Cache封装的CacheUtils(缓存使用)，基于gson封装的JsonUtils(序列化json)
- [jerseyClient剖析](https://jersey.github.io/documentation/latest/client.html)
- [Guava Cache使用教程](https://github.com/google/guava/wiki/CachesExplained)

7. API生成框架Jeresey Swagger
- [jersey-swagger环境搭建](http://www.codeweblog.com/jersey-swagger%E6%90%AD%E5%BB%BA/)

### 四、主要体现
1. 异常的统一处理，让开发人员只需注重业务逻辑的实现，该抛异常的地方，不要上来就是超大的try-catch
 
2. 针对elasticsearch、redis、mongo这些稍微复杂的配置，已经全部放在java代码中配置（ConfigBeans.java），用起来简单易懂。另外还附赠一个针对本公司的一个elasticsearch搜索解析器（EsParser.java）

3. 直接Jersey-Swagger的集成使得restful接口文档很是方便的生成，以便前后端调试接口
 
4. 针对java后端调用http请求的，本人基于Jersey Client封装成了一个方便简单易用的HttpUtils
 
5. 针对非持久的缓存，本人基于guava cache封装了一个简单易用的CacheUtils
 
6. 其它还有事务、aop等配置，全是基于注解的方式，使得开发变得更加快速高效
 
### 五、使用步骤
1. 与CVS断开连接，修改项目名称

2. 修改meta_ws.properties及com.hiekn.meta.app.App.java中相关配置项
- 【**必须**】base.package=com.hiekn.meta：Jersey与Swagger的扫描注解的基础路径
- swagger.init=true：是否初始化Swagger
- swagger.version=1.0.0：API的版本号
- 【**必须**】swagger.title=metaProject API：API的名称
- 【**必须**】swagger.host=192.168.1.119:8080：Swagger访问接口的host
- 【**必须**】swagger.base.path=/meta_ws/api：Swagger访问接口的基础路径

```
说明：
    如果修改了servlet拦截基础路径即注解@ApplicationPath中的值，请同时修改swagger.base.path的值，
    例如：将其值改为"/*",则swagger.base.path=/meta_ws
```

3. 修改applicationContext.xml中相关配置
- context:property-placeholder：该标签时初始化数据库配置文件，spring文件中只能有一个该标签，文件名db.properties一般不用改，但是里面的配置项得结合项目作相应的更改。如何读取这些属性值，请参考com.hiekn.meta.config.ConfigBeans.java
- util:properties ：该标签是初始化项目相关的属性文件，该标签可以有多个。如何读取这些属性值，请参考com.hiekn.meta.config.CommonResource.java。**请接合项目修改文件名称**
- 【**必须**】context:component-scan ，请修改base-package的值，该路径下的注解会被spring识别。
- 【**必须**】sqlSessionFactory中mapperLocations中的值为mybatis的*mapper.xml中的位置，请结合项目作相应修改。

```
说明：
    如果该mapper.xml文件与mapper接口文件在同一个包下，该属性可以去掉
    如果有多个mapper.xml路径，可以配置为数组形式

```

- 【**必须**】org.mybatis.spring.mapper.MapperScannerConfigurer中basePackage中的值为*mapper.java接口的位置，请结合项目作相应修改。**同时得修改mapper文件中的namespace！**

- 【**必须**】txManager，tx:annotation-driven ，txAdvice为事务相关配置，不需要的可以全部去掉

- 【**必须**】aop:config ，aop:aspectj-autoproxye为aop相关配置，不需要的可以全部去掉

4. 修改pom.xml相关配置
- 【**必须**】结合项目修改groupId以及artifactId
- 【**必须**】如果不需要elasticsearch或者redis或者mongo，可以去掉ConfigBeans.java中相关配置同时去掉pom中引用的jar包

```
说明：
    如果全都不需要，可以删掉该文件亦可以去掉注解@Configuration
```

5. 修改日志相关配置
-  【**必须**】修改log4j.properties和log4j2.xml中日志文件名和路径

```
将两个日志文件中的将meta_ws改成项目相应的即可
```

-  保留log4j.properties是因为一些老的框架还在使用，而我们自己的代码，统一使用log4j2.xml
- 【**必须**】开发时禁用System.out.println(),请用log4j2代替，请参考com.hiekn.meta.service.impl.UserServcieImpl.java
- 【**必须**】若部署到生产环境，请将log4j2.xml中Console标签下的ThresholdFilter标签中的level属性值改为error
- 设置第三方框架日志的级别，请直接参考log4j2.xml

6. 单元测试
- 【**必须**】若没有使用事务请将com.hiekn.meta.test.TestBase.java文件中@Transactional去掉
- 【**必须**】TestBase是基础，主要是初始化springjunit的，以便可以使用spring相关注解，所以不要在这里写测试代码
- 【**必须**】具体业务代码测试请参考com.hiekn.meta.test.UserServiceTest.java

7. 其它
- 【推荐】可以将建表语句统一放在/src/main/resources/meta.sql(文件名结合项目作修改)，以便数据库的维护
- 【**必须**】异常定义请参考com.hiekn.meta.bean.result.ErrorCode.java，统一异常处理请参考com.hiekn.demo.exception.ExceptionHandler.java
- 【**必须**】service接口写法请参考com.hiekn.meta.service.UserService.java及实现，rest接口写法请参考com.hiekn.meta.rest.UserRestApi.java

### 六、注意事项
1. 【**必须**】使用meta_ws请务必按照结构来写，就拿SqlSession来说，官方的建议是开发人员直接使用Mapper接口映射dao，不用自己去实现dao。每个线程都应该有它自己的SqlSession实例。SqlSession的实例不能共享使用，它也是线程不安全的。因此最佳的范围是请求或方法范围。绝对不能将SqlSession实例的引用放在一个类的静态字段或实例字段中。 
2. 【**必须**】业务bean的注入请严格按照基于接口的形式，不要基于类的形式
3. 【**必须**】运行环境必须是jdk1.8+和tomcat8+即容器须支持web3.1

### 七、Question
1. 为什么要用断言做单元测试？
2. 开发时为什么要用log4j2替代System.out.println("")打印调式？
3. log4j里使用系统属性catalina.base的好处是什么？
4. 在com.hiekn.meta.config.ConfigBeans.java中使用注解@Lazy有什么好处？
5. 在com.hiekn.meta.test.TestBase.java中使用@Transactional作用是什么？
6. spring动态代理有几种？区别又是什么？
