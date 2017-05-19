/****************************************************************
 * WnkParameterColleterResolver
 * Parameter를 Binding 하여 WnkParamMap 형태로 반환한다. 
 * @author skycow79
 * @version 1.0
 * @date 2016.12.22
 ****************************************************************/
package co.wnk.framework.core.common.resolver;

import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import co.wnk.framework.core.common.WnkParamMap;

public class WnkParameterColleterResolver implements HandlerMethodArgumentResolver {
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return WnkParamMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		WnkParamMap paramMap = new WnkParamMap();
		Iterator<String> paramIterator = webRequest.getParameterNames();
		
		while(paramIterator.hasNext()){
			String key = paramIterator.next();
			String[] values = webRequest.getParameterValues(key);
			if(values!=null) paramMap.put(key, (values.length > 1) ? values : values[0]);
		}
		
		return paramMap;
	}
}
