package com.shaygorodskaia.familybudget.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private static final String SEPARATOR = "==========================================================================";

    @Pointcut("execution(* com.shaygorodskaia.familybudget.controller..*(..)))")
    private void loggingController() {
    }

    @AfterThrowing(pointcut = "loggingController()", throwing = "exception")
    public void loggingException(JoinPoint jp, Throwable exception) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        log.info(methodSignature + ":");
        log.error(exception.getMessage(), exception);
        log.info(SEPARATOR);
    }

    @Around("loggingController()")
    public Object logRequest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();

        log.info("Method {} was executed with parameters {}", methodSignature, Arrays.toString(args));
        log.info(SEPARATOR);

        Object result = proceedingJoinPoint.proceed();

        log.info("Exit with result {}", result.toString());
        log.info(SEPARATOR);

        return result;
    }
}
