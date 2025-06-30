package Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(Controller..*) || within(Service..*)")
    public void applicationPackagePointcut() {}

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering: {}.{}()", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting: {}.{}() with result = {}", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result != null ? result.toString() : "null");
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}() with cause = {}", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            e.getCause() != null ? e.getCause() : "NULL");
    }
}