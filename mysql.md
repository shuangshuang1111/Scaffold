spring data JPA 和spring data JDBC区别？
JDBC 是连接数据库操作的原生接口,是对数据库操作的统一接口,定义的是对数据库操作的规范,由具体的数据库厂家实现,如:Mysql,Oracle 等;
JPA 是持久化规范，是orm框架(对象关系映射框架)的标准,JPA的具体实现是由ORM框架实现,如:Hibernate等,
ORM框架底层都是通过封装JDBC来实现CRUD功能的;
简单数据库交互和快速原型开发：如果您需要快速原型开发或与数据库进行简单交互，且对性能有严格要求，那么Spring Data JDBC可能是更好的选择。
大型企业级应用程序和复杂查询需求：对于需要高级功能、对象关系映射和复杂查询的大型企业级应用程序，Spring Data JPA是一个更好的选择
CrudRepository JpaRepository PagingAndSortingRepository之间的区别？
CrudRepository — 针对特定类型的存储库的通用CRUD（创建、读取、更新和删除）操作的接口。
PagingAndSortingRepository — 扩展 CrudRepository 以提供额外的方法来使用分页和排序抽象检索实体，默认不支持带查询条件的分页。
JpaRepository提供JPA相关的方法，如刷新持久化数据、批量删除。JpaRepository包含了CrudRepository和PagingAndSortingRepository所有的API。
当我们不需要JpaRepository和PagingAndSortingRepository提供的功能时，可以简单使用CrudRepository。
简单地说，Spring Data中的每个repository都继承自Repository接口，但是，除此之外，它们每个又有不同的功能。
Spring 提供了两种重要的方法：Spring JDBC 和 Spring Data JPA。虽然两者服务于同一目标——数据访问——但它们却采用不同的理念和功能。
Spring JDBC 是 Spring 框架提供的处理数据库操作的直接方法。它利用 JDBC API，但消除了与之相关的样板代码。
Spring JDBC 核心特点   JdbcTemplate用于简化数据库交互
异常翻译为 Spring 的 DataAccessException
针对不同数据库的一致 API
Spring JDBC 优点   直接控制 SQL 查询：Spring JDBC 允许您显式编写 SQL 查询，从而完全控制数据库交互。
高性能：使用 Spring JDBC，应用程序和数据库之间的开销最小，从而可能获得更好的性能。
小型项目的简单性：对于简单的应用程序，Spring JDBC 可以直接使用，而无需 ORM 的复杂性
Spring JDBC 缺点   更多样板代码：即使进行了简化JdbcTemplate，您仍然需要手动编写和管理 SQL 查询并映射结果。
SQL 注入的风险较高：如果输入处理不小心，SQL 注入可能会带来风险。
更少的抽象：随着领域模型复杂性的增加，处理关系和复杂的查询可能会变得很麻烦。
Spring Data JPA 是位于 Java Persistence API 之上的抽象层。它简化了 Spring 应用程序生态系统内的数据访问。
Spring Data JPA 核心特点  简单的 CRUD 操作CrudRepository
根据方法名称自动生成查询
高级分页和动态查询执行
Spring Data JPA 优点   减少样板代码：Spring Data JPA 中的存储库可以显着减少所需的样板代码量。
强大的抽象层：Spring Data JPA 处理将对象映射到数据库表的复杂性，从而更轻松地处理复杂的数据模型。
更易于维护：Spring Data JPA 提供的抽象使维护和更新应用程序的数据访问层变得更容易。
Spring Data JPA 缺点   对 SQL 查询的控制较少：抽象有时可能太多，隐藏了优化所需的细节。
潜在的性能开销：抽象层可能会带来性能开销，特别是如果不小心使用的话。
更陡峭的学习曲线：了解 JPA 和 Hibernate 的复杂性对于新手来说可能具有挑战性。
方法和设计理念
Spring JDBC 是一种较低级别的方法，它在标准 JDBC 上提供了一个薄层，这意味着您可以与 SQL 和手动对象映射密切合作。相比之下，
Spring Data JPA 是一种更高级别的抽象，它与对象及其元数据一起自动处理 SQL 创建和结果映射。
Spring JDBC 和 Spring Data JPA 之间的选择通常归结为项目的特定需求和开发团队的偏好。对于那些喜欢完全控制 SQL 并拥有管理 SQL 专业知识的人来说，Spring JDBC 是一个绝佳的选择。
另一方面，Spring Data JPA 提供了更高级别的抽象，可以大大加快复杂应用程序的开发时间，但可能会牺牲一些性能和控制
如何写分页的复杂查询？
JpaRepositoryImplementation<T, ID> 继承 JpaSpecificationExecutor<T>
JpaSpecificationExecutor<T> 里面有 Page<T> findAll(Specification<T> spec, Pageable pageable);方法 可以写复杂的查询条件
JpaRepository<T, ID> extends ListCrudRepository<T, ID>, ListPagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>
QueryByExampleExecutor<T> 里面有<S extends T> Page<S> findAll(Example<S> example, Pageable pageable); 可以写简单点的查询方法