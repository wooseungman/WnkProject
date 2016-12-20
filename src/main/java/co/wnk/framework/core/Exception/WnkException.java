
package co.wnk.framework.core.Exception;

import co.wnk.framework.core.common.util.message.WnkMessageProperty;

public class WnkException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String exceptionMessage = null; 
	
	public WnkException(){
		super();
	}
	
	public WnkException(String message) {
		this.setExceptionMessage(WnkMessageProperty.getMessage(message));
	}
	
	public WnkException(String message, Object[] args) {
		this.setExceptionMessage(WnkMessageProperty.getMessage(message, args));
	}
	
	@Override
	public String getMessage() {
		return exceptionMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
