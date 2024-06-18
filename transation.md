事务超时，也就是指一个事务所允许执行的最长时间，如果在超时时间内还没有完成的话，就自动回滚。

假如事务的执行时间格外的长，由于事务涉及到对数据库的锁定，就会导致长时间运行的事务占用数据库资源。

有了 Spring Boot 之后，只需要在业务层添加事务注解（@Transactional）就可以快速开启事务。

判断事务是否开启：
assertTrue(TransactionSynchronizationManager.isActualTransactionActive());

Spring 将事务管理的核心抽象为一个事务管理器（TransactionManager）
该接口有两个子接口，分别是响应式事务接口 ReactiveTransactionManager 和声明式事务接口 PlatformTransactionManager
和ConfigurableTransactionManager。
通过 PlatformTransactionManager 这个接口，Spring 为各个平台如
JDBC(DataSourceTransactionManager)、Hibernate(HibernateTransactionManager)、JPA(JpaTransactionManager)
等都提供了对应的事务管理器，但是具体的实现就是各个平台自己的事情了。

本地事务：单个数据库或单个资源上进行的事务操作
分布式事务：分布式系统中同时涉及多个数据库或资源的事务操作

分布式事务使用场景：
跨库事务
分库分表
微服务化

org.springframework.transaction.annotation.Transactional
和javax.transaction.Transactional注释之间的差异
JTA事务注释适用于 CDI 管理的 bean 和由 Java EE 规范定义为托管 bean 的类，而 Spring 的事务注释仅适用于 Spring bean。
还值得注意的是，Spring Framework 4.0 中引入了对 JTA 1.2 的支持。这样，我们就可以在Spring应用程序中使用JTA
Transactional注解了。然而，反过来是不可能的，因为我们不能在 Spring 上下文之外使用 Spring 注释。

Java Transaction API，通常称为 JTA，是 Java 中用于管理事务的 API。它允许我们以与资源无关的方式启动、提交和回滚事务。
JTA 的真正威力在于它能够在单个事务中管理多个资源（即数据库、消息服务）。
JTA 定义了 事务管理器要实现的接口和语义。Narayana和Atomikos等库提供了实现。