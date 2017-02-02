package co.wnk.framework.core.dao.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import co.wnk.framework.core.common.util.config.ApplicationProperty;

public class WnkRowBounds extends RowBounds{
	
	public int ROW_OFF_SET = ApplicationProperty.get("page.size") != null ? Integer.parseInt(ApplicationProperty.get("page.size")) : 10; 
	public int ROW_LIMIT = 1;
	public int ROW_START = 0;
	public int ROW_END = 0;
	
	public WnkRowBounds(){
		 
	}
	
	public WnkRowBounds(int pageSize){
		this.ROW_OFF_SET = pageSize; 
	}
	
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
