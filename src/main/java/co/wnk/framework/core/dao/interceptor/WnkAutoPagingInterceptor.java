package co.wnk.framework.core.dao.interceptor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.lang.reflect.Field;
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
import org.apache.ibatis.mapping.ParameterMapping;
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
public class WnkAutoPagingInterceptor implements Interceptor {
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
    	
    	StatementHandler handler = (StatementHandler)invocation.getTarget();
    	StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler =  MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
		Configuration configuration = (Configuration)metaStatementHandler.getValue("delegate.configuration");
		
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) return invocation.proceed();
		
		Map<String,Object> paramMap = (Map<String, Object>) (handler.getParameterHandler().getParameterObject() != null ? handler.getParameterHandler().getParameterObject() : "");
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
		
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
		
		if(configuration.getDatabaseId().equals("MySQL")){
			//pagingSql.append("SELECT NONPAGINATEDLIST.* FROM (");
			pagingSql.append(sql).append(" LIMIT ").append(StartNumber).append(", ").append(rowBounds.getOffset());
			//pagingSql.append(") NONPAGINATEDLIST").append(" LIMIT ").append(StartNumber).append(", ").append(rowBounds.getOffset());
		}else if(configuration.getDatabaseId().equals("ORACLE")){
			pagingSql.append("SELECT * FROM (");
			pagingSql.append("SELECT ROWNUM AS RNUM, NONPAGINATEDLIST.* FROM (");
			pagingSql.append(sql);
			pagingSql.append(") NONPAGINATEDLIST WHERE ROWNUM <= " + EndNumber + " ) WHERE RNUM > " + StartNumber);	
		}else if(configuration.getDatabaseId().equals("MSSQL")){
			
		}
		
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		
		try{
			countSql.append("SELECT COUNT(1) TOTAL_ROWS_COUNT FROM (").append(paramBindSql(statementHandler)).append(") TABLE_COUNT");
			
			Connection connection = (Connection) invocation.getArgs()[0];
			countStmt = connection.prepareStatement(countSql.toString());
			rs = countStmt.executeQuery();
			if (rs.next()){
				paramMap.put("LIST_TOT_COUNT", rs.getInt(1));
				request.setAttribute("LIST_TOT_COUNT", rs.getInt(1));
			}
			
			if(request.getParameter("page") == null)
				request.setAttribute("page", "1");
			
		}catch(Exception e){
			throw new Exception();
		}finally{
			if(countStmt != null) countStmt.close();  
			if(rs != null) rs.close();
			metaStatementHandler.setValue("delegate.boundSql.sql", pagingSql.toString());
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		}
		
		return invocation.proceed();
	}
	
	private String paramBindSql(StatementHandler handler) throws NoSuchFieldException, IllegalAccessException {
		BoundSql boundSql = handler.getBoundSql();

		// 쿼리실행시 맵핑되는 파라미터를 구한다
		Object param = handler.getParameterHandler().getParameterObject();
		// 쿼리문을 가져온다(이 상태에서의 쿼리는 값이 들어갈 부분에 ?가 있다)
		String sql = boundSql.getSql();

		// 바인딩 파라미터가 없으면
		if (param == null) {
			sql = sql.replaceFirst("\\?", "''");
			return sql;
		}

		// 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
		if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
			sql = sql.replaceFirst("\\?", param.toString());
		}
		// 해당 파라미터의 클래스가 String인 경우
		else if (param instanceof String) {
			sql = sql.replaceFirst("\\?", "'" + param + "'");
		}
		// 해당 파라미터의 클래스가 Map인 경우
		else if (param instanceof Map) {
			List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
			for (ParameterMapping mapping : paramMapping) {
				String propValue = mapping.getProperty();
				Object value = ((Map) param).get(propValue);
				
				if (value == null) {
					value = "";
				}
				
				if (value instanceof String) {
					sql = sql.replaceFirst("\\?", "'" + value + "'");
				} else {
					sql = sql.replaceFirst("\\?", value.toString());
				}
			}
		}
		// 해당 파라미터의 클래스가 사용자 정의 클래스인 경우
		else {
			List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
			Class<? extends Object> paramClass = param.getClass();

			for (ParameterMapping mapping : paramMapping) {
				String propValue = mapping.getProperty();
				Field field = paramClass.getDeclaredField(propValue);
				field.setAccessible(true);
				Class<?> javaType = mapping.getJavaType();
				if (String.class == javaType) {
					sql = sql.replaceFirst("\\?", "'" + field.get(param) + "'");
				} else {
					sql = sql.replaceFirst("\\?", field.get(param).toString());
				}
			}
		}
		
		return sql;
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
