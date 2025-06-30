package com.Aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {

    @Pointcut(value="execution(* com.*.*.*(..))")
    public void PointCut(){
    }


    @Around("PointCut()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper mapper=new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        String methodName= proceedingJoinPoint.getSignature().getName();
        String className=proceedingJoinPoint.getClass().toString();
        Object[] arguments=proceedingJoinPoint.getArgs();

        // Safe logging with try-catch
        try {
            log.info("Method Invoked from Class: "+className+": "+methodName+"() with arguments: "+ mapper.writeValueAsString(arguments));
        } catch (Exception e) {
            log.info("Method Invoked from Class: "+className+": "+methodName+"() with arguments: [Cannot serialize arguments]");
        }

        Object object=proceedingJoinPoint.proceed();

        try {
            log.info(className+": "+methodName+"(). Response was: "+ mapper.writeValueAsString(object));
        } catch (Exception e) {
            log.info(className+": "+methodName+"(). Response was: [Cannot serialize response]");
        }
        return object;
    }
}
