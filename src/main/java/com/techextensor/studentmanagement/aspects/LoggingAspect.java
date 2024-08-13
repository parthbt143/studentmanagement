package com.techextensor.studentmanagement.aspects;

import com.techextensor.studentmanagement.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.techextensor.studentmanagement.controllers..*(..)) || " +
            "execution(* com.techextensor.studentmanagement.services..*(..)) || " +
            "execution(* com.techextensor.studentmanagement.repositories..*(..))")
    public void applicationLayer() {
    }

    @Before("applicationLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log("Entering : ", joinPoint);
    }

    @After("applicationLayer()")
    public void logAfter(JoinPoint joinPoint) {
        log("Exiting : ", joinPoint);
    }

    public void log(String message, JoinPoint joinPoint) {
        logger.info("User : {} {} : {} in {}()",
                SecurityUtils.getCurrentUserName(),
                message,
                joinPoint.getSignature().getDeclaringTypeName()
                        .replace("com.techextensor.studentmanagement.", "")
                        .replace("org.springframework.data.", ""),
                joinPoint.getSignature().getName());
    }
}
