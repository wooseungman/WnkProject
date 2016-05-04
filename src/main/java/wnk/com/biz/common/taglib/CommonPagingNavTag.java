package wnk.com.biz.common.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

@SuppressWarnings("serial")
public class CommonPagingNavTag extends RequestContextAwareTag {

	@Override
	protected int doStartTagInternal() throws Exception {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		JspWriter out = pageContext.getOut();
		
		return SKIP_BODY;
	}
	
}
