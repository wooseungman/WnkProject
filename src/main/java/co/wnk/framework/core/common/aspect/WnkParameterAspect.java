package co.wnk.framework.core.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class WnkParameterAspect {
	
	@Around("execution(* wnk.com.biz..*.*Controller.*(..))")
    public Object setMapParamter(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object[] args = joinPoint.getArgs();
		if(args.length == 0) return joinPoint.proceed();
		
		//ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        //HttpServletRequest request = requestAttributes.getRequest();
        
		return joinPoint.proceed();
	}
}
