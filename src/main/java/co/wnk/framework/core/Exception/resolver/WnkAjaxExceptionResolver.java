/*********************************************************************
 * Controller 혹은 Service에서 throw 된 Exception에 대한 메세지 처리
 * Ajax 통신에서의 Exception을 처리한다.
 * @author skycow79
 * <pre>
 * <b>History</b>
 *     작성자, 1.0, 2017.04.06 최초작성
 * </pre>
 * @version 1.0
 * @see None
 *********************************************************************/
package co.wnk.framework.core.Exception.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import co.wnk.framework.core.Exception.WnkException;
import co.wnk.framework.core.common.Constants;
import co.wnk.framework.core.common.util.message.WnkMessageProperty;

public class WnkAjaxExceptionResolver implements HandlerExceptionResolver,Ordered {
	
	private View view;
	private int order = Ordered.LOWEST_PRECEDENCE;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response
			, Object obj, Exception exception) {
		
		Map<String,Object> exceptionMessage = new HashMap<String,Object>();
		String contentType = request.getHeader("Accept");
		
		response.setStatus(500);
		exception.printStackTrace();
		
		if(contentType != null && !contentType.equals("")){
			if(!contentType.contains("json")) return null;
		}
		
		if(exception instanceof WnkException){
			WnkException ex = (WnkException) exception;
			exceptionMessage.put("MESSAGE",ex.getExceptionMessage());
		}else{
			exceptionMessage.put("MESSAGE",WnkMessageProperty.getMessage(Constants.KEY_ERROR_MESSAGE));
		}
		
		return new ModelAndView(view, exceptionMessage);
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
