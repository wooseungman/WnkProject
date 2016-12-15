package co.wnk.framework.core.common.aspect;

import java.util.Enumeration;
import java.util.Map;

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
		
		System.out.println("************************* AOP START ******************************");
		
		
		Object[] args = joinPoint.getArgs();
		if(args.length == 0) return joinPoint.proceed();
		
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
        
		Enumeration<?> enumeration = request.getParameterNames();
		
		for(Object obj : args){
			if(obj instanceof Map){
				@SuppressWarnings("unchecked")
				Map<String,Object> m = (Map<String,Object>) obj;
				if(enumeration.hasMoreElements()){
        			while(enumeration.hasMoreElements()){
        				String key = (String) enumeration.nextElement();
        				String[] values = request.getParameterValues(key);
        				
        				if(!request.getParameter(key).isEmpty())
        					if(values!=null) m.put(key, (values.length > 1) ? values:values[0] );
        			}
        		}
			}
		}
		
		return joinPoint.proceed();
	}
}
