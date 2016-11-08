package wnk.com.common.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class WnkExceptionResolver implements HandlerExceptionResolver,Ordered {
	
	private int order = Ordered.LOWEST_PRECEDENCE;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {
		exception.printStackTrace();
		String viewName = "error/error";
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("ErrorMessage", "시스템 에러발생");
		mav.addObject("ExceptionCause",exception);
		mav.addObject("requestUrl",request.getHeader("referer"));
		
		return mav;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}
}
