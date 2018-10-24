# Spring Mybatis 动态数据源

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

上面方法最后三个方法是重点，调用顺序 `getConnection() -> determineTargetDataSource() -> determineCurrentLookupKey()`，最后这个抽象方法是需要我们重写的。