package co.wnk.framework.core.common.util.message;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class WnkMessageProperty {
	
private static MessageSourceAccessor message = null;
	
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		WnkMessageProperty.message = messageSourceAccessor;
    }
	
	public static String getMessage(String key){
		return message.getMessage(key, Locale.getDefault());
	}
	
	public static String getMessage(String key, Object[] args) {
        return message.getMessage(key, args, Locale.getDefault());
    }
}
