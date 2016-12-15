package co.wnk.framework.common.taglib;


import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;
import co.wnk.framework.core.common.util.message.WnkMessageProperty;

@SuppressWarnings("serial")
public class CommonMessageTag  extends RequestContextAwareTag {
	
	private String messageCode;
	private Object[] args;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		String returnMessage = null;
		JspWriter out = pageContext.getOut();
		if(args != null && args.length > 0 && messageCode != null && !messageCode.equals("")){
			returnMessage =WnkMessageProperty.getMessage(messageCode, args);
		}else if(messageCode != null && !messageCode.equals("")){
			returnMessage =WnkMessageProperty.getMessage(messageCode);
		}
		
		out.print(returnMessage);
		return SKIP_BODY;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
