package me.snowhere.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Slf4j
public class DynamicDataSourceAspect {

    public void before(JoinPoint point) {
        log.info("before ========= {}",point.getSignature().getName());
        Class<?> clazz = point.getTarget().getClass();
        Method targetMethod = ((MethodSignature) point.getSignature()).getMethod();
        String methodName = targetMethod.getName();
        Class<?>[] parameterTypes =targetMethod.getParameterTypes();

        try {
            // method > class > interface method
            DataSource dataSource = targetMethod.getAnnotation(DataSource.class);
            if (dataSource == null) {
                dataSource = clazz.getAnnotation(DataSource.class);
            }
            if (dataSource == null) {
                for (Class<?> interfaceClazz : clazz.getInterfaces()) {
                    Method interfaceMethod = interfaceClazz.getMethod(methodName, parameterTypes);
                    if (interfaceMethod != null) {
                        dataSource = interfaceMethod.getAnnotation(DataSource.class);
                        if (dataSource != null) {
                            break;
                        }
                    }
                }
            }
            if (dataSource != null) {
                DynamicDataSourceHolder.putDataSource(dataSource.value());
                log.info("{} choice database :{}",methodName,dataSource.value().name());
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