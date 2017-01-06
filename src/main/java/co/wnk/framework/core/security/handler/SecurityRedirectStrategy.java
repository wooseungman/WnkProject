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

import co.wnk.framework.core.security.vo.User;

public class SecurityRedirectStrategy {
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private String targetUrlParameter;
	private String defaultUrl;
	private boolean useReferer;
	
	public SecurityRedirectStrategy(){
		targetUrlParameter = "";
		defaultUrl = "/";
		useReferer = false;
	}
	
	public void sendRedirecUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
		switch(decideRedirectStrategy(request, response ,authentication)){
		case 1:
			useTargetUrl(request, response, authentication);
			break;
		case 2:
			useSessionUrl(request, response, authentication);
			break;
		case 3:
			useRefererUrl(request, response, authentication);
			break;
		default:
			useDefaultUrl(request, response, authentication);
		}
	}
	
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
		int result = 0;
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(!"".equals(targetUrlParameter)){
			String targetUrl = request.getParameter(targetUrlParameter);
			if(StringUtils.hasText(targetUrl)){
				result = 1;
			}else{
				if(savedRequest != null){
					result = 2;
				}else{
					String refererUrl = request.getHeader("REFERER");
					if(useReferer && StringUtils.hasText(refererUrl)){
						result = 3;
					}else{
						result = 0;
					}
				}
			}
		}
		
		if(savedRequest != null){
			result = 2;
			return result;
		}
		
		String refererUrl = request.getHeader("REFERER");
		if(useReferer && StringUtils.hasText(refererUrl)){
			result = 3;
		}else{
			result = 0;
		}
		
		return result;
	}
	
	private String addParamterLanguageCode(String targetUrl, Authentication authentication){
		User user = (User) authentication.getPrincipal();
		
		if(user.getLanguage_code() != null && !user.getLanguage_code().equals("")){
			targetUrl = targetUrl.indexOf("?") == -1 ? 
					targetUrl + "?lang=" + user.getLanguage_code() : targetUrl + "&lang=" + user.getLanguage_code();
		}
		
		return targetUrl;
	}
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null)
			requestCache.removeRequest(request, response);
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(request.getParameter(targetUrlParameter), authentication));
	}
	
	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(savedRequest.getRedirectUrl(), authentication));
	}
	
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(request.getHeader("REFERER"), authentication));
	}
	
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(defaultUrl, authentication));
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
