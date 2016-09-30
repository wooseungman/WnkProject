package wnk.com.common.interceptor;

import java.util.Map;
import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SuppressWarnings("unchecked")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor  implements Interceptor {
	
    public Object intercept(Invocation invocation) throws Throwable {
    	
    	ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
    	
    	StatementHandler handler = (StatementHandler)invocation.getTarget();
    	Map<String,Object> paramMap = (Map<String, Object>) (handler.getParameterHandler().getParameterObject() != null ? handler.getParameterHandler().getParameterObject() : "");
    	System.out.println("paramMap : " + paramMap.toString());
    	StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler =  MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
		Configuration configuration = (Configuration)metaStatementHandler.getValue("delegate.configuration");
		
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}
		
		/*while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // The final separation of a proxy object target class
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }*/
        
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        
        String sql = boundSql.getSql();
		StringBuffer pagingSql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		
		int StartNumber = rowBounds.getOffset() * (rowBounds.getLimit() -1);
		int	EndNumber = rowBounds.getOffset() * (rowBounds.getLimit());
		
		System.out.println("*****************************************************");
		System.out.println("rowBounds.getLimit() : " + rowBounds.getLimit());
		System.out.println("rowBounds.getOffset() : " + rowBounds.getLimit());
		System.out.println("StartNumber : " + StartNumber);
		System.out.println("EndNumber : " + EndNumber);
		System.out.println("*****************************************************");
		
		
		if(configuration.getDatabaseId().equals("MySQL")){
			pagingSql.append("SELECT NONPAGINATEDLIST.* FROM (");
			pagingSql.append(sql);
			pagingSql.append(") NONPAGINATEDLIST").append(" LIMIT ").append(StartNumber).append(", ").append(rowBounds.getOffset());
		}else if(configuration.getDatabaseId().equals("ORACLE")){
			pagingSql.append("SELECT * FROM (");
			pagingSql.append("SELECT ROWNUM AS RNUM, NONPAGINATEDLIST.* FROM (");
			pagingSql.append(sql);
			pagingSql.append(") NONPAGINATEDLIST WHERE ROWNUM <= " + EndNumber + " ) WHERE RNUM > " + StartNumber);	
		}else if(configuration.getDatabaseId().equals("MSSQL")){
			
		}
		
		System.out.println(pagingSql);
		
		metaStatementHandler.setValue("delegate.boundSql.sql", pagingSql.toString());
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		
		try{
			countSql.append("SELECT COUNT(1) TOTAL_ROWS_COUNT FROM (").append(sql).append(") AUTO_COUNT");
			
			System.out.println("countSql : " + countSql);
			
			Connection connection = (Connection) invocation.getArgs()[0];
			countStmt = connection.prepareStatement(countSql.toString());
			rs = countStmt.executeQuery();
			if (rs.next()){
				System.out.println("rs.getInt(1) : "  + rs.getInt(1));
				paramMap.put("LIST_TOT_COUNT", rs.getInt(1));
				request.setAttribute("LIST_TOT_COUNT", rs.getInt(1));
				
				System.out.println("paramMap : " + paramMap.get("LIST_TOT_COUNT"));
				System.out.println("LIST_TOT_COUNT : " + request.getAttribute("LIST_TOT_COUNT"));
			}
		}catch(Exception e){
			throw new Exception();
		}finally{
			if(countStmt != null) countStmt.close();  
			if(rs != null) rs.close();
		}
		
		return invocation.proceed();
	}
	
	public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
	}

	public void setProperties(Properties arg0) {
		
	}
}
