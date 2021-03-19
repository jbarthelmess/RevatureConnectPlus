package dev.group1.aspects;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.group1.entities.User;
import dev.group1.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class SecurityAspect {
    @Pointcut("@annotation(dev.group1.aspects.Authorize)")
    private void authorizationCheck(){}

    @Around("authorizationCheck()")
    public Object checkJWT(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        if(jwt == null){
            // add logging later
            System.out.println("Responding with an error for no JWT");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No JWT provided. Please log in");
        }
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        if(decodedJWT == null) {
            // add logging later
            System.out.println("Responding with an error for Invalid JWT");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT provided. Please log in.");
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
    }
}
