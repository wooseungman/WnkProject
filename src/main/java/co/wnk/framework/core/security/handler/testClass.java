package co.wnk.framework.core.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class testClass extends LoginSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		super.setDefaultUrl("");
		super.setTargetUrlParameter("");
		super.setUseReferer(false);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
