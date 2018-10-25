package me.snowhere.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Slf4j
public class DynamicDataSourceAspect {

    public void before(JoinPoint point) {
        log.info("before ========= {}",point.getSignature().getName());
        Object target = point.getTarget();
        String methodName = point.getSignature().getName();
        Class<?> serviceImplClazz = target.getClass();
        Class<?>[] serviceClazz = serviceImplClazz.getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = serviceImplClazz.getMethod(methodName, parameterTypes);
            if (!method.isAnnotationPresent(DataSource.class) && serviceClazz.length > 0) {
                method = serviceClazz[0].getMethod(methodName, parameterTypes);
            }
            if (method != null && method.isAnnotationPresent(DataSource.class)) {
                DataSource data = method.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(data.value());
                log.info("{} choice database :{}",methodName,data.value().name());
            } else {
                DynamicDataSourceHolder.putDataSource(DataSourceEnum.MASTER);
            }
        } catch (Exception e) {
            log.error(String.format("Choose DataSource error, method:%s, msg:%s", methodName, e.getMessage()));
        }
    }

    public void after(JoinPoint point) {
        DynamicDataSourceHolder.clearDataSource();
    }
}