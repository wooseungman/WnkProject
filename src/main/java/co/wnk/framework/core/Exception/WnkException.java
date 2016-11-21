
package co.wnk.framework.core.Exception;

public class WnkException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String exceptionMessage = null; 
	
	public WnkException(){
		super();
	}
	
	public WnkException(String message) {
		this.setExceptionMessage(message);
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
