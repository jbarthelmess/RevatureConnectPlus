package dev.group1.aspects;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.group1.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class SecurityAspect {
    //@Pointcut("@annotation(Authorize)")
    //private void authorizationCheck(){}

    @Around("@annotation(Authorize)")
    public Object checkJWT(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        if(jwt == null) throw new IllegalAccessException("No JWT Provided");
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        if(decodedJWT == null) {
            throw new IllegalAccessException("Invalid JWT Provided");
        } else {
            // will want to pass a user object to most function calls for info
            return pjp.proceed();
        }
        //HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
