# Spring Mybatis 动态数据源

## Annotation 和 Enum
我们首先需要一个注解和枚举类来标识和定义数据源
```
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataSource {
    DataSourceEnum value() default DataSourceEnum.MASTER;
}
```
```
public enum DataSourceEnum {
    MASTER, SLAVE
}
```

## DynamicDataSourceAspect
然后监听 service 层接口，获取数据源标识，切点前执行 `before()` 方法，切点后执行 `after()` 方法。（xml配置省略）
```
public class DynamicDataSourceAspect {

    public void before(JoinPoint point) {
        Method targetMethod = ((MethodSignature) point.getSignature()).getMethod();
        DataSource dataSource = targetMethod.getAnnotation(DataSource.class);
        if (dataSource != null) {
            DynamicDataSourceHolder.putDataSource(dataSource.value());
        } else {
            DynamicDataSourceHolder.putDataSource(DataSourceEnum.MASTER);
        }
    }

    public void after(JoinPoint point) {
        DynamicDataSourceHolder.clearDataSource();
    }
}
```


## DynamicDataSourceHolder
我们需要一个 `ThreadLocal` 来保存当前取到的数据源，这里我们用了一个 `Stack`，因为可能 A 方法调用了 B 方法，执行完 B 方法后上下文要还原到 A 的数据源来继续执行 A 方法后续语句。因此需要一个先进先出的数据结构来存放数据源。

```
public class DynamicDataSourceHolder {

    private static final ThreadLocal<Stack<DataSourceEnum>> holder = new ThreadLocal<>();

    public static void putDataSource(DataSourceEnum dataSource) {
        Stack<DataSourceEnum> dataSourceEnums = holder.get();
        if (null == dataSourceEnums) {
            dataSourceEnums = new Stack<>();
            holder.set(dataSourceEnums);
        }

        dataSourceEnums.push(dataSource);
    }

    public static DataSourceEnum getDataSource() {
        Stack<DataSourceEnum> dataSourceEnums = holder.get();
        if (null == dataSourceEnums || dataSourceEnums.isEmpty()) {
            return null;
        }
        return dataSourceEnums.peek();
    }

    public static void clearDataSource() {
        Stack<DataSourceEnum> dataSourceEnums = holder.get();
        if (null == dataSourceEnums || dataSourceEnums.isEmpty()) {
            return;
        }
        dataSourceEnums.pop();
    }
}
```

## DynamicDataSource
Spring 提供 `AbstractDataSource` 这个抽象类，我们继承这个类实现自己的动态 DataSource，我们我们先看看这个类的主要代码
```
//有 set 和 get 方法
private Map<Object, Object> targetDataSources;
//有 set 和 get 方法
private Object defaultTargetDataSource;
//在 afterPropertiesSet() 方法中复制自 targetDataSources
private Map<Object, DataSource> resolvedDataSources;
//在 afterPropertiesSet() 方法中复制自 defaultTargetDataSource
private DataSource resolvedDefaultDataSource;

@Override
public void afterPropertiesSet() {
    //复制 targetDataSources -> resolvedDataSources
    //复制 defaultTargetDataSource -> resolvedDefaultDataSource
}

//获取 DataSource 连接
@Override
public Connection getConnection() throws SQLException {
    return determineTargetDataSource().getConnection();
}

//根据 Key 获取指定 DataSource
protected DataSource determineTargetDataSource() {
    Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
    Object lookupKey = determineCurrentLookupKey();
    DataSource dataSource = this.resolvedDataSources.get(lookupKey);
    if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
        dataSource = this.resolvedDefaultDataSource;
    }
    if (dataSource == null) {
        throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
    }
    return dataSource;
}

//重写此方法，动态选择 Key
protected abstract Object determineCurrentLookupKey();
```
上面方法最后三个方法是重点，调用顺序 `getConnection() -> determineTargetDataSource() -> determineCurrentLookupKey()`，从一个 `Map` 中根据 key 来获取对应的 DataSource，最后这个抽象方法是需要我们重写的。
```
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Setter
    private Object writeDataSource; //写数据源
    @Setter
    private Object readDataSources; //读数据源

    @Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.MASTER.name(), writeDataSource);
        targetDataSources.put(DataSourceEnum.SLAVE.name(), readDataSources);
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceEnum dataSourceEnum = DynamicDataSourceHolder.getDataSource();
        if (dataSourceEnum == null) {
            return DataSourceEnum.MASTER.name();
        } else {
            return dataSourceEnum.name();
        }
    }
}
```
DynamicDataSource 的配置文件如下
```
<bean id="masterDataSource" class="org.apache.commons.dbcp.BasicDataSource">...</bean>
<bean id="slaveDataSource" class="org.apache.commons.dbcp.BasicDataSource">...</bean>
<bean id="dataSource" class="me.snowhere.datasource.DynamicDataSource">
    <property name="writeDataSource" ref="masterDataSource"/>
    <property name="readDataSources" ref="slaveDataSource"/>
    <property name="defaultTargetDataSource" ref="masterDataSource"/>
</bean>
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
</bean>
```

## 其他
上面只是实现了一个最简单的，可以工作的动态数据源，并不适合放到工程中，因为还有很多地方需要扩展：
1. 切面和注解。除了注解方法，我们还可以设置注解到类上以及接口上，并在 `DynamicDataSourceAspect` 中按照优先级依次寻找方法、类、接口上的注解，确定数据源标识。
2. 动态数据源。在 `DynamicDataSource` 中，我们只有一个主数据源和从数据源，我们可以把从数据源改为 `List`，在 `targetDataSources.put()` 时的 key 为 `DataSourceEnum.SLAVE.name() + index`，并设置一个属性来标识选择从库的策略，比如随机或轮询，来确定 index 的值选择具体从库。实现一主多从的架构。

另外还有一些其他需要注意的问题：
1. 直接用 this 调用当前对象的方法不会被切面拦截，配置的数据源标识也就无效
2. 事务不能跨数据源
