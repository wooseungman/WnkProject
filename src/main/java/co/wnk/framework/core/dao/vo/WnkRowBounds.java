package co.wnk.framework.core.dao.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WnkRowBounds extends RowBounds{
	
	public int ROW_OFF_SET = 30; 
	public int ROW_LIMIT = 1;
	public int ROW_START = 0;
	public int ROW_END = 0;
	
	public RowBounds getRowBounds(){
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
        
        if(request.getParameter("page") != null && !request.getParameter("page").equals("")) 
        	ROW_LIMIT = Integer.parseInt(request.getParameter("page"));
        else 
        	ROW_LIMIT = 1;
		
        return new RowBounds(ROW_OFF_SET, ROW_LIMIT);
	}
	
	@Override
	public int getOffset() {
		return super.getOffset();
	}
	
	@Override
	public int getLimit() {
		return super.getLimit();
	}
}
