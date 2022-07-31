package com.example.day19assignment.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    //Controller Pointcut
    @Pointcut("within(com.example.day19assignment.controller.*)")
    public void inControllerLayer(){}

    //Login Pointcut
    @Pointcut("execution(* com.example.day19assignment.controller.AuthenticationController.loginRequest(*))")
    public void loginAttempt(){}
}
