package wnk.com.biz.common.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

@SuppressWarnings("serial")
public class CommonPagingNavTag extends RequestContextAwareTag {

	@Override
	protected int doStartTagInternal() throws Exception {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		int currentPage = request.getParameter("page") != null && !request.getParameter("page").equals("page") ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;
		int tot_count = Integer.parseInt(String.valueOf(request.getAttribute("LIST_TOT_COUNT")));
		int pageBlockSize = 30;
		int pageBlock = tot_count/pageBlockSize;
		if(tot_count%pageBlockSize > 0) pageBlock++;
		
		System.out.println("**************************************************************");
		System.out.println("currentPage : " + currentPage);
		System.out.println("tot_count : " + tot_count);
		
		System.out.println("pageBlock : " + pageBlock);
		System.out.println("tot_count/pageBlockSize : " + (tot_count/pageBlockSize)*10);
		System.out.println("**************************************************************");
		request.setAttribute("currentPage", currentPage);
		
		JspWriter out = pageContext.getOut();
		StringBuffer result = new StringBuffer();
		if(currentPage>pageBlock) result.append("◁");
		for(int i=1;i<=pageBlock;i++){
			if(currentPage == i){
				result.append("<b>" + i + "</b> | ");
			}else{
				result.append(i + " | ");
			}
			
		}
		if(currentPage <= pageBlockSize) result.append("▷");
		
		out.print(result.toString());
		
		return SKIP_BODY;
	}
	
}
