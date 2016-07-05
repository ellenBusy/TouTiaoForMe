package com.ellenBusy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by BusyEllen on 16/7/2.
 */
@Aspect
@Component
 public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.ellenBusy.controller.IndexController.*(..))")//在执行IndexController这个类之前
    // 都要执行beforMethod这个方法。(*)表示通配符。
    //*Controller表示以Controller结尾的类都要先执行这个方法
    //括号里是要监控的方法
    public void beforMethod(JoinPoint joinPoint) {

        StringBuilder sb = new StringBuilder();
        for(Object arg : joinPoint.getArgs()) {//记下调用这个方法的所有参数
            sb.append("args: " + arg.toString()+ "|");
        }
        logger.info("befor method: " + sb.toString());
    }

    @After("execution(* com.ellenBusy.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("after method: ");
    }
}