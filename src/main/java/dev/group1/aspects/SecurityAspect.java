package dev.group1.aspects;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.group1.entities.User;
import dev.group1.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class SecurityAspect {
    @Pointcut("@annotation(dev.group1.aspects.Authorize)")
    private void authorizationCheck(){}

    @Around("authorizationCheck()")
    public Object checkJWT(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String jwt = request.getHeader("Authorization");
        if(jwt == null){
            response.sendError(401, "No JWT Provided. Please Log in");
        }
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        if(decodedJWT == null) {
            response.sendError(401, "Illegal JWT Provided. Please Log in");
        } else {
            // will want to pass a user object to most function calls for info
            User user = new User();
            user.setUserId(decodedJWT.getClaim("userId").asInt());
            user.setUsername(decodedJWT.getClaim("username").asString());
            user.setDisplayName(decodedJWT.getClaim("displayName").asString());
            Object[] args = pjp.getArgs();
            args[0] = user;
            return pjp.proceed(args);
        }
        return null;
    }
}
