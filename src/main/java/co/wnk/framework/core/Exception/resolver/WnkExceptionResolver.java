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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class WnkExceptionResolver implements HandlerExceptionResolver,Ordered {
	
	private int order = Ordered.LOWEST_PRECEDENCE;
	private String viewName = null;
	private String errorCode = null;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {
		exception.printStackTrace();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("ErrorMessage", errorCode);
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

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
