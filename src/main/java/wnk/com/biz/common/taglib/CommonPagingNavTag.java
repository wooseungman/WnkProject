package wnk.com.biz.common.taglib;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

@SuppressWarnings("serial")
public class CommonPagingNavTag extends RequestContextAwareTag {
	
	
	
	
	
	@Override
	protected int doStartTagInternal() throws Exception {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		int currentPage = request.getParameter("page") != null && !request.getParameter("page").equals("page") ? 
				Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;
		
		
		int pageBlockSize = 30;
		System.out.println("pageBlockSize : " + pageBlockSize);
		
		int tot_count = Integer.parseInt(String.valueOf(request.getAttribute("LIST_TOT_COUNT")));
		int lastPpage = tot_count/pageBlockSize;
		if(tot_count%pageBlockSize > 0) lastPpage++;
		int startPage = ((currentPage/10))*10+1;
		int endPage = ((currentPage/10)+1)*10;
		if(endPage > lastPpage) endPage = lastPpage;
		
		
		
		JspWriter out = pageContext.getOut();
		StringBuffer result = new StringBuffer();
		StringBuffer url = new StringBuffer();
		StringBuffer paremeter = new StringBuffer();
		
		url.append(request.getAttribute("javax.servlet.forward.request_uri"));
		Enumeration<?> enumeration = request.getParameterNames();
		if(enumeration.hasMoreElements()){
			while(enumeration.hasMoreElements()){
				String key = (String) enumeration.nextElement();
				String value = request.getParameter(key);
				if(!request.getParameter(key).isEmpty() && !key.equals("page"))
					if(value!=null) paremeter.append("&"+key+"="+value);
			}
		}
		if(startPage > pageBlockSize) result.append("<a href='"+url.toString()+"?page="+(startPage-10)+ paremeter.toString() +"'>◁</a>&nbsp;&nbsp;");
		
		for(int i=startPage;i<=endPage;i++){
			if(currentPage == i) result.append("<b>" + i + "</b>&nbsp;"); 
			else result.append("<a href='"+url.toString()+"?page="+i+ paremeter.toString() +"'>"+i+"</a>&nbsp;");
			if(i < endPage)result.append(" | ");
		}
		
		if(endPage < lastPpage) result.append("<a href='"+url.toString()+"?page="+(endPage+1)+ paremeter.toString() +"'>&nbsp;&nbsp;▷</a>");
		out.print(result.toString());
		return SKIP_BODY;
	}
}
