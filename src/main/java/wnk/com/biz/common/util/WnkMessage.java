package wnk.com.biz.common.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class WnkMessage {
	
	private static MessageSourceAccessor message = null;
	
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        WnkMessage.message = messageSourceAccessor;
    }
	
	public static String getMessage(String key){
		return message.getMessage(key, Locale.getDefault());
	}
	
	public static String getMessage(String key, Object[] args) {
        return message.getMessage(key, args, Locale.getDefault());
    }
}
