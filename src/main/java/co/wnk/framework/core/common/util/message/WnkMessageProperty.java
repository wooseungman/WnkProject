package co.wnk.framework.core.common.util.message;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class WnkMessageProperty {
	
private static MessageSourceAccessor message = null;
	private static Locale defaultLocale = null;
	
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		WnkMessageProperty.message = messageSourceAccessor;
    }
	
	public static String getMessage(String key){
		try{
			return message.getMessage(key, defaultLocale);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getMessage(String key, Object[] args) {
		try{
			return message.getMessage(key, args, getLocale().getLanguage());
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
    }
	
	public static Locale getLocale(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		defaultLocale =  request.getSession().getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE") == null ?
				request.getLocale() : (Locale) request.getSession().getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
		return defaultLocale;
	}
}
