package co.wnk.framework.core.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import co.wnk.framework.core.security.vo.User;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private String targetUrlParameter;
	private String defaultUrl;
	private boolean useReferer;
	
	public LoginSuccessHandler(){
		this.targetUrlParameter = "";
		this.defaultUrl = "/";
		this.useReferer = false;
	}
	
	public LoginSuccessHandler(String targetParameter, String defaultUrl, boolean useReferer){
		this.targetUrlParameter = targetParameter;
		this.defaultUrl = defaultUrl;
		this.useReferer = useReferer;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		
		clearAuthenticationAttributes(request);
		HttpSession session = request.getSession(false);
		session.setAttribute("memberInfo", authentication.getPrincipal());
		
		switch(decideRedirectStrategy(request, response)){
			case 1:
				useTargetUrl(request, response, authentication.getPrincipal());
				break;
			case 2:
				useSessionUrl(request, response, authentication.getPrincipal());
				break;
			case 3:
				useRefererUrl(request, response, authentication.getPrincipal());
				break;
			default:
				useDefaultUrl(request, response, authentication.getPrincipal());
		}
	}
	
	
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) return;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response, Object user) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null)
			requestCache.removeRequest(request, response);
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(request.getParameter(targetUrlParameter), user));
	}
	
	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response, Object user) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(savedRequest.getRedirectUrl(), user));
	}
	
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response, Object user) throws IOException{
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(request.getHeader("REFERER"), user));
	}
	
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response, Object user) throws IOException{
		redirectStrategy.sendRedirect(request, response, this.addParamterLanguageCode(defaultUrl, user));
	}
	
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response){
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
	
	private String addParamterLanguageCode(String targetUrl, Object obj){
		User userDetail =  (User) obj;
		if(userDetail.getLanguage_code() != null && !userDetail.getLanguage_code().equals("")){
			targetUrl = targetUrl.indexOf("?") == -1 ? 
					targetUrl + "?lang=" + userDetail.getLanguage_code() : targetUrl + "&lang=" + userDetail.getLanguage_code();
		}
		return targetUrl;
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
