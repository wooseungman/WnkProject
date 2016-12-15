package co.wnk.framework.core.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import co.wnk.framework.core.common.util.WnkSpringBeanUtil;
import co.wnk.framework.core.common.util.message.WnkMessageProperty;

@Component
@SuppressWarnings({ "serial", "rawtypes" })
public class AsyncResponseMap  extends HashMap {
	
	public AsyncResponseMap(){
		super();
	}
	
	@SuppressWarnings("unchecked")
	public AsyncResponseMap setUrl(String urlForRedirect){
		ServletContext ctx = WnkSpringBeanUtil.getServletContext();
		Assert.notNull(urlForRedirect);
		try{
			super.put(Constants.URL_FOR_REDIRECT, ctx.getContextPath() +"/" + urlForRedirect.replaceAll("^/", ""));
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AsyncResponseMap setUrlIsParam(String urlForRedirect, @RequestParam Map<String, Object> paramMap){
		ServletContext ctx = WnkSpringBeanUtil.getServletContext();
		Assert.notNull(urlForRedirect);
		try{
			super.put(Constants.URL_FOR_REDIRECT, ctx.getContextPath() +"/" + urlForRedirect.replaceAll("^/", ""));
			
			// Controller에서 Map형식의 파라미터를 가지고 와서 HashMap에 넣어준다.
			Iterator<String> iterator = paramMap.keySet().iterator();	
		    while (iterator.hasNext()) {
		        String key = (String) iterator.next();
		        super.put(key,paramMap.get(key));
		    }	
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AsyncResponseMap setSuccessFlag(boolean success){
		super.put(Constants.SUCCESS_FLAG, success);
		return this;
	};
	
	@SuppressWarnings("unchecked")
	public AsyncResponseMap setMessage(String messageForAlert){
		Assert.notNull(messageForAlert);
		super.put(Constants.MSG_FOR_ALERT, messageForAlert);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AsyncResponseMap setSaveOkMessage(){
		super.put(Constants.MSG_FOR_ALERT,WnkMessageProperty.getMessage(Constants.saveOk) );
		return this;
	}

	@SuppressWarnings("unchecked")
	public AsyncResponseMap setModifyOkMessage(){
		super.put(Constants.MSG_FOR_ALERT,WnkMessageProperty.getMessage(Constants.modifyOk) );
		return this;
	}

	@SuppressWarnings("unchecked")
	public AsyncResponseMap setDeleteOkMessage(){
		super.put(Constants.MSG_FOR_ALERT,WnkMessageProperty.getMessage(Constants.deleteOk) );
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AsyncResponseMap setErrorMessage(){
		super.put(Constants.MSG_FOR_ALERT,WnkMessageProperty.getMessage(Constants.error) );
		return this;
	}
}
