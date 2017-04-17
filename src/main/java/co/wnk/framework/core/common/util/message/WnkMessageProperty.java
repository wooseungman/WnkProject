/***************************************************************
 * 설정된 Locale 정보에 따른 프로퍼티 정보를 읽어온다.
 * @author skycow79
 * <pre>
 * <b>History</b>
 *     작성자, 1.0, 2017.04.06 최초작성
 * </pre>
 * @version 1.0
 * @see None
 ***************************************************************/
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
	
	/**********************************************************************************
	 * 생성자
	 * @param messageSourceAccessor
	 **********************************************************************************/
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		WnkMessageProperty.message = messageSourceAccessor;
    }
	
	/**********************************************************************************
	 * 입력된 Key에 따른 프로퍼티를 읽어온다.
	 * 가져온는 프로퍼티는 Locale 설정에 따름
	 * @param key
	 * @return String
	 **********************************************************************************/
	public static String getMessage(String key){
		try{
			return message.getMessage(key, defaultLocale);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************************************************
	 * 입력된 Key에 따른 프로퍼티를 읽어온다. 
	 * 가져온는 프로퍼티는 Locale 설정에 따름
	 * {0} 에 대응되는 Args를 넘길 수 있다.
	 * @param key
	 * @param args
	 * @return String
	 **********************************************************************************/
	public static String getMessage(String key, Object[] args) {
		try{
			return message.getMessage(key, args, getLocale().getLanguage());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
    }
	
	/**********************************************************************************
	 * 현재 설정되어 있는 Locale 설정을 가져온다.
	 * 설정되어 있는 Session Locale 정보가 없을시에는 Default Locale 정보를 Return 한다.
	 * @return String
	 **********************************************************************************/
	public static Locale getLocale(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		defaultLocale =  request.getSession().getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE") == null ?
				request.getLocale() : (Locale) request.getSession().getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
		return defaultLocale;
	}
}
