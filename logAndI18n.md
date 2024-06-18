国际化代码步骤
Steps:
--Understand Locale(know language codes)
--Configure Locale resolver and interceptor in spring
-- Create languge files(messages_XX.properties)  messages.properties:default
-- Update Thymeleaf views for i18n  Print text:[[#{key}]]  Attribute:th:value="#{key}"
-- Switch between languages(Englich,Chinese..)   URL paramter:?lang=xx
优雅停机
所有四个嵌入式Web服务器（Jetty、Reactor Netty、Tomcat和Undertow）以及基于响应式和Servlet的Web应用都支持优雅关闭。 它作为关闭应用程序上下文的一部分发生，
并在停止 SmartLifecycle bean的最早阶段执行。 这种停止处理使用一个超时，提供一个宽限期，在此期间，现有的请求将被允许完成，但不允许有新的请求。 
不允许新请求的确切方式取决于正在使用的网络服务器。 Jetty、Reactor Netty和Tomcat将在网络层停止接受请求。 Undertow将接受请求，但立即响应服务不可用（503）的回应
使用Tomcat的优雅关机需要Tomcat 9.0.33或更高版本
要启用优雅关机，配置 server.shutdown 属性
要配置超时时间，请配置 spring.lifecycle.timeout-per-shutdown-phase 属性
spring官网介绍了3种日志框架
Spring Boot使用Commons Logging进行所有内部日志记录但使底层日志实现保持开放状态。为Java Util Logging、Log4j2和Logback提供了默认配置。

默认情况下，如果您使用“starter”，则使用Logback进行日志记录。还包括适当的Logback路由，以确保使用Java Util Logging、Commons Logging、Log4J或SLF4J的依赖库都能正确工作。

目前最受欢迎且被广泛采用的日志框架是Log4j 2和Logback
选用springboot默认的Logback进行日志输出。

日志默认输出到控制台，并不会输出到文件（默认情况下，Spring Boot只记录到控制台，不写日志文件）

因为标准的logback.xml配置文件加载得太早，所以不能在其中使用扩展。需要使用logback-spring.xml或定义一个日志记录。配置属性。本次选用logback-spring.xml进行日志配置。


先在百度上搜索springboot3如何集成swagger3.找到了springdoc-openapi-starter-webmvc-ui
再去chat问这个包什么作用，在官网哪里可以找到这个包
Springdoc OpenAPI项目并没有一个官方网站，但您可以在其GitHub页面获取相关信息
GitHub页面链接： Springdoc OpenAPI GitHub（https://github.com/springdoc/springdoc-openapi）
在这个网址找到了集成的办法

