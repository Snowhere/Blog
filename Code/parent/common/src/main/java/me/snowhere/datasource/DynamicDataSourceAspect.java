package me.snowhere.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Pointcut("execution(* me.snowhere.service.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint point) {
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
            } else {
                DynamicDataSourceHolder.putDataSource(DataSourceEnum.WRITE);
            }
        } catch (Exception e) {
            log.error(String.format("Choose DataSource error, method:%s, msg:%s", methodName, e.getMessage()));
        }
    }

    @After("pointCut()")
    public void after(JoinPoint point) {
        DynamicDataSourceHolder.clearDataSource();
    }
}