package com.example.elandweb.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

public class ExceptionAspect {
    private static final Logger logger = Logger.getLogger(ControllerAspect.class);

    @Before("execution(* com.example.elandweb.config.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        logger.info("api Method " + className + "." + methodName + " .");
    }
}
