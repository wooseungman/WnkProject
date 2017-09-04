package co.wnk.framework.core.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import co.wnk.framework.core.common.util.WnkStringUtils;

public class AccessFailureHandler implements AccessDeniedHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessFailureHandler.class);
	
	private String errorPage;

    public AccessFailureHandler() { }

    public AccessFailureHandler(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, 
    		AccessDeniedException exception) throws IOException, ServletException {
    	
    	String error = "true";
    	String message = exception.getMessage();
        String accept = request.getHeader("accept");

        if( StringUtils.indexOf(accept, "html") > -1 ) {
        	request.setAttribute("error", error);
        	request.setAttribute("message", exception);
        	request.getRequestDispatcher(errorPage).forward(request, response);
        } else if( StringUtils.indexOf(accept, "xml") > -1 ) {
        	response.setContentType("application/xml");
            response.setCharacterEncoding("utf-8");

            String data = StringUtils.join(new String[] {
                 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                 "<response>",
                 "<error>"+error+"</error>",
                 "<message>"+message+"</message>",
                 "</response>"
            });

            PrintWriter out = response.getWriter();
            out.print(data);
            out.flush();
            out.close();
        } else if( StringUtils.indexOf(accept, "json") > -1 ) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.setContentType( "application/json;charset=UTF-8" );
            
            Map<String, Object> returnMap = new HashMap<String,Object>();
            returnMap.put("error", error);
            returnMap.put("message", message);
            PrintWriter out = response.getWriter();
            out.print(WnkStringUtils.getJsonString(returnMap));
            out.flush();
            out.close();
        }
    }

}
