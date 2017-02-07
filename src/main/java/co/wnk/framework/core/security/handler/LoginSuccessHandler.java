package co.wnk.framework.core.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import co.wnk.framework.core.common.util.WnkStringUtil;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SecurityRedirectStrategy strategy;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		
		String accept = request.getHeader("accept");
		
		clearAuthenticationAttributes(request);
		HttpSession session = request.getSession(false);
		session.setAttribute("memberInfo", authentication.getPrincipal());
		
		/**
		 * 로그인을 페이지를 요청한 유형에 따라 다른 형태의 반환을 한다.
		 * HTML의 경우는 URL전략에 따라 Redirect 처리되며
		 * Json과 Xml의 경우는 Flag 값을 리턴한다.
		 */
		if(StringUtils.indexOf(accept, "html") > -1){
			strategy.sendRedirecUrl(request, response, authentication);
		}else if(StringUtils.indexOf(accept, "xml") > -1){
			response.setContentType("application/xml");
            response.setCharacterEncoding("utf-8");
			String data = StringUtils.join(new String[] {
	                 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
	                 "<response>",
	                 "<flag>true</flag>",
	                 "</response>"
	        });

			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
			
		}else if(StringUtils.indexOf(accept, "json") > -1){
			
			response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.setContentType( "application/json;charset=UTF-8" );
            
            Map<String, Object> returnMap = new HashMap<String,Object>();
            returnMap.put("loginFlag", "true");
            returnMap.put("loginInfo", authentication.getPrincipal());
            PrintWriter out = response.getWriter();
            out.print(WnkStringUtil.getObjectToJsonString(returnMap));
            out.flush();
            out.close();
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
