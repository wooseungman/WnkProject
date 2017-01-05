package co.wnk.framework.core.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SecurityRedirectStrategy strategy;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		
		clearAuthenticationAttributes(request);
		HttpSession session = request.getSession(false);
		session.setAttribute("memberInfo", authentication.getPrincipal());
		
		switch(strategy.decideRedirectStrategy(request, response)){
			case 1:
				strategy.useTargetUrl(request, response, authentication.getPrincipal());
				break;
			case 2:
				strategy.useSessionUrl(request, response, authentication.getPrincipal());
				break;
			case 3:
				strategy.useRefererUrl(request, response, authentication.getPrincipal());
				break;
			default:
				strategy.useDefaultUrl(request, response, authentication.getPrincipal());
		}
	}
	
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) return;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public SecurityRedirectStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(SecurityRedirectStrategy strategy) {
		this.strategy = strategy;
	}
}
