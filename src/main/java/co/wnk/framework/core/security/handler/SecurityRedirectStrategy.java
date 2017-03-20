package co.wnk.framework.core.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class SecurityRedirectStrategy {
	
	protected RequestCache requestCache = new HttpSessionRequestCache();
	protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	protected String targetUrlParameter;
	protected String defaultUrl;
	protected boolean useReferer;
	
	public SecurityRedirectStrategy(){
		targetUrlParameter = "";
		defaultUrl = "/";
		useReferer = false;
	}
	
	public SecurityRedirectStrategy(String targetUrlParameter, String defaultUrl, boolean useReferer){
		this.targetUrlParameter = targetUrlParameter;
		this.defaultUrl = defaultUrl;
		this.useReferer = useReferer;
	}
	
	public void sendRedirecUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
		switch(sendRedirectStrategy(request, response)){
		case 1:
			useTargetUrl(request, response, null);
			break;
		case 2:
			useSessionUrl(request, response, null);
			break;
		case 3:
			useRefererUrl(request, response, null);
			break;
		default:
			useDefaultUrl(request, response, null);
		}
	}
	
	protected int sendRedirectStrategy(HttpServletRequest request, HttpServletResponse response){
		int result = 0;
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String refererUrl = request.getHeader("REFERER");

		if(useReferer && StringUtils.hasText(refererUrl))
			result = 3;
		else
			result = 0;
		
		if(savedRequest != null)
			result = 2;
		
		if(!"".equals(targetUrlParameter)){
			String targetUrl = request.getParameter(targetUrlParameter);
			if(StringUtils.hasText(targetUrl))
				result = 1;
		}
		
		return result;
	}
	
	protected String addParamterStr(String targetUrl, String sendParamStr){
		if(sendParamStr != null && !sendParamStr.equals("")){
			targetUrl = targetUrl.indexOf("?") == -1 ? 
					targetUrl + "?" + sendParamStr : targetUrl + "&" + sendParamStr;
		}
		return targetUrl;
	}
	
	protected void useTargetUrl(HttpServletRequest request, HttpServletResponse response, String sendParamStr) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null)
			requestCache.removeRequest(request, response);
		
		sendRedirect(request, response, request.getParameter(targetUrlParameter), sendParamStr);
	}
	
	protected void useSessionUrl(HttpServletRequest request, HttpServletResponse response, String sendParamStr) throws IOException{
		sendRedirect(request, response, requestCache.getRequest(request, response).getRedirectUrl(), sendParamStr);
	}
	
	protected void useRefererUrl(HttpServletRequest request, HttpServletResponse response, String sendParamStr) throws IOException{
		sendRedirect(request, response, request.getHeader("REFERER"), sendParamStr);
	}
	
	protected void useDefaultUrl(HttpServletRequest request, HttpServletResponse response, String sendParamStr) throws IOException{
		sendRedirect(request, response, defaultUrl, sendParamStr);
	}
	
	protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url, String sendParamStr) throws IOException{
		redirectStrategy.sendRedirect(request, response, addParamterStr(url, sendParamStr));
	}
	
	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}

	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public boolean isUseReferer() {
		return useReferer;
	}

	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}
}