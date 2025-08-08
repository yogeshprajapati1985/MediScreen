package com.mediscreen.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.mediscreen..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} | Args: {} | RequestId: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                MDC.get("requestId"));
    }

    @AfterReturning(pointcut = "execution(* com.mediscreen..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned: {} | RequestId: {}",
                joinPoint.getSignature().toShortString(),
                result,
                MDC.get("requestId"));
    }

    @AfterThrowing(pointcut = "execution(* com.mediscreen..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Method {} threw exception: {} | RequestId: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage(),
                MDC.get("requestId"));
    }
}


