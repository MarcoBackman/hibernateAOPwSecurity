package com.example.day19assignment.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before(value = "com.example.day19assignment.aop.PointCuts.loginAttempt()")
    public void logAttempt(JoinPoint joinPoint){
        logger.info("Attempt to login: "  + joinPoint.getSignature());
    }

    @AfterThrowing(value = "com.example.day19assignment.aop.PointCuts.loginAttempt()", throwing = "ex")
    public void logControllerException(JoinPoint joinPoint, Throwable ex){
        logger.error("From LoggingAspect.logThrownException in controller: " + ex.getMessage() + ": " + joinPoint.getSignature());
    }

    @After("com.example.day19assignment.aop.PointCuts.inControllerLayer()")
    public void logController(JoinPoint joinPoint) {
        logger.info("From LoggingAspect.logThrownException in controller: " + System.currentTimeMillis() + " : " + joinPoint.getSignature());
    }

    @AfterThrowing(value = "com.example.day19assignment.aop.PointCuts.inControllerLayer()", throwing = "ex")
    public void logThrownException(JoinPoint joinPoint, Throwable ex) {
        logger.error("From LoggingAspect.logThrownException in controller: " + ex.getMessage() + ": " + joinPoint.getSignature());
    }
}