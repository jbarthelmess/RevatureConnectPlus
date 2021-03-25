package dev.group1.aspects;

import dev.group1.dtos.UserDTO;
import dev.group1.entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

@Component
@Aspect
public class LoggingAspect {
    static Logger logger = Logger.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(dev.group1.aspects.LogErrors)")
    private void logErrorCheck() {}

    @AfterThrowing(value = "logErrorCheck()", throwing = "err")
    public void logThrownError(JoinPoint jp, Throwable err) throws Throwable {
        Object[] args = jp.getArgs();
        String userClause = "";
        if(args[0] instanceof UserDTO && !jp.getSignature().getName().equals("registerUser") && !jp.getSignature().getName().equals("login")) {
            UserDTO user = (UserDTO) args[0];
            userClause = "Error thrown in request made by "+user.getUsername()+". ";
        }
        logger.error(userClause +"Method "+jp.getSignature().getName() +" exited with error: "+ err.getMessage()+".");
        throw err;
    }

    @Pointcut("@annotation(dev.group1.aspects.LogEvent)")
    private void logEventCheck() {}

    @AfterReturning(value = "logEventCheck()", returning = "returnValue")
    public Object logUserEnterEvent(JoinPoint jp, Object returnValue) {
        if(jp.getSignature().getName().equals("login")) {
            User user = (User) returnValue;
            logger.info("Login for User "+user.getUsername());
        }
        if(jp.getSignature().getName().equals("registerUser")) {
            User user = (User) returnValue;
            logger.info("New Registered User "+user.getUsername());
        }
        return returnValue;
    }
}
