package co.wnk.framework.common.taglib;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

@SuppressWarnings("serial")
public class CommonPagingNavTag extends RequestContextAwareTag {
	
	int tot_count;				/*	총 카운트			*/
	int lastPpage;				/*	마지막 페이지 		*/
	int startPage;				/*	시작 페이지			*/
	int endPage;				/*	끝 페이지			*/
	int pageBlockSize;			/*	페이지 블럭 사이지	*/
	int currentPage;			/*	현재 페이지			*/
	
	@Override
	protected int doStartTagInternal() throws Exception {
		JspWriter out = pageContext.getOut();
		out.print(this.setPageInfomation());
		return SKIP_BODY;
	}
	
	/**
	 * 페이지 관련 정보를 셋팅한다.
	 * @param request
	 * @throws Exception
	 */
	private String setPageInfomation() throws Exception{
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		WebApplicationContext wac = getRequestContext().getWebApplicationContext();
		Properties props = wac.getBean("app", Properties.class);
		
		pageBlockSize = props.getProperty("page.size") != null && !props.getProperty("page.size").equals("") ? 
				Integer.parseInt(props.getProperty("page.size")) : 0;
		
		if(pageBlockSize == 0) throw new Exception();
				
		currentPage = request.getParameter("page") != null && !request.getParameter("page").equals("page") ? 
				Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;
		
		tot_count = Integer.parseInt(String.valueOf(request.getAttribute("LIST_TOT_COUNT")));
		lastPpage = tot_count/pageBlockSize;
		
		if(tot_count%pageBlockSize > 0) lastPpage++;
		startPage = ((currentPage/10))*10+1;
		endPage = ((currentPage/10)+1)*10;
		if(endPage > lastPpage) endPage = lastPpage;
		
		return this.printPagingNavigation(request);
	}
	
	/**
	 * 입력된 페이지 정보에 따른 페이지 네비게이션을 출력한다.
	 * @param request
	 * @return String
	 */
	private String printPagingNavigation(HttpServletRequest request){
		
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
			if(currentPage == i) result.append("<span style='font-weight:bold'>" + i + "</span>&nbsp;"); 
			else result.append("<a href='"+url.toString()+"?page="+i+ paremeter.toString() +"'>"+i+"</a>&nbsp;");
			if(i < endPage)result.append(" | ");
		}
		
		if(endPage < lastPpage) result.append("<a href='"+url.toString()+"?page="+(endPage+1)+ paremeter.toString() +"'>&nbsp;&nbsp;▷</a>");
		
		return result.toString();
	}
}
