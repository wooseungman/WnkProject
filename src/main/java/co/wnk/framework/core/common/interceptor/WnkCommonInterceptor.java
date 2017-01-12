package co.wnk.framework.core.common.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class WnkCommonInterceptor extends HandlerInterceptorAdapter {
	
	private static Log log = LogFactory.getLog(WnkCommonInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request instanceof MultipartHttpServletRequest) 
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Enumeration<?> e = request.getParameterNames();
		if (e.hasMoreElements()) {
			System.out.println("**************************************** REQUEST PARAMETER LOG START **********************************");
			System.out.println("-------------------------------------------------------------------------------------------------------");
			System.out.println("|                  key                  	  |                      value                        |");
			System.out.println("-------------------------------------------------------------------------------------------------------");
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = request.getParameter(key);
				String emptyKey = "";
				String emptyKeyValue = "";
				for (int i = 0 ; i < 47 - key.length() ; i++) {
					emptyKey += " ";
				}
				for (int i = 0 ; i < 50 - value.length() ; i++) {
					emptyKeyValue += " ";
				}
				System.out.println("| " + key + emptyKey + " | " + value + emptyKeyValue + "|");
			}
			System.out.println("-------------------------------------------------------------------------------------------------------");
			System.out.println("****************************************  REQUEST PARAMETER LOG END ***********************************\n");
		}
		
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, 	HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//super.postHandle(request, response, handler, modelAndView);
		String viewName = null;
		
		if (modelAndView !=  null && modelAndView.getViewName() != null) {
			viewName = modelAndView.getViewName();
		}
		
		String param = "";
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = request.getParameter(key);
			if (param.equals(""))
				param += "?" + key + "=" + value;
			else
				param += "&" + key + "=" + value;
		}
		
		System.out.println("--------------------------------------- EXECUTE INFORMATION -------------------------------------------");
		System.out.println("|         PARAM INFORMATION 		: 	" + param);
	    System.out.println("|         URL INFORMATION 			: 	" + request.getRequestURL());
	    System.out.println("|         CONTROLLER INFORMATION 	: 	" + handler.toString());
	    if (viewName != null)
	    	System.out.println("|         JSP INFORMATION 	: 	" + viewName + ".jsp");
	    System.out.println("|          CLIENT BROWSER 	: 	" + request.getHeader("User-Agent"));
	    System.out.println("-------------------------------------------------------------------------------------------------------");
	    System.out.println("-----------------------------------------  CONTROLLER PROCESS INFO ------------------------------------");
	    request.removeAttribute("processStartTime");
	    Runtime rt = Runtime.getRuntime();
		System.out.println("| TOTAL MEMORY 	: 	" + rt.totalMemory() / 1024 + " KB");
		System.out.println("| FREE SIZE    	: 	" + rt.freeMemory() / 1024 + " KB");
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		long heap = total - free;
		long sum = (heap * 100) / total;
		System.out.println("| HEAP SIZE    	: 	" + heap / 1024 + " KB" + " (" + sum + "%)" );
		System.out.println("-------------------------------------------------------------------------------------------------------\n");
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
