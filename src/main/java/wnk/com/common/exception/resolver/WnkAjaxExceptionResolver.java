package wnk.com.common.exception.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import wnk.com.common.exception.WnkException;

public class WnkAjaxExceptionResolver implements HandlerExceptionResolver,Ordered {
	
	private View view;
	private int order = Ordered.LOWEST_PRECEDENCE;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {
		
		Map<String,Object> exMap = new HashMap<String,Object>();
		String contentType = request.getHeader("Accept");
		
		response.setStatus(500);
		exception.printStackTrace();
		
		if(contentType != null && !contentType.equals("")){
			if(!contentType.contains("json")) return null;
		}
		
		if(exception instanceof WnkException){
			WnkException ex = (WnkException) exception;
			exMap.put("MESSAGE",ex.getExceptionMessage());
		}else{
			exMap.put("MESSAGE","전송에 실패하였습니다.");
		}
		
		return new ModelAndView(view, exMap);
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}
}
