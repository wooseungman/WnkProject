package wnk.com.common.interceptor;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.CLOB;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MybatisObjectHandler extends BaseTypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		System.out.println("setParameter START!!!!!!");
		String value = (String) parameter;
		if (value != null) {
			 CLOB clob = CLOB.createTemporary(ps.getConnection(), true, CLOB.DURATION_SESSION);
			 clob.putChars(1, value.toCharArray());
			 ps.setClob(i, clob);
		}else{
			ps.setString(i, null);
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter);
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Object object = rs.getObject(columnName);
		
		if(object instanceof Clob) {
			Clob clob = (Clob) object;
			if (clob != null) return clob.getSubString(1, (int) clob.length());
		}
		
		return object;
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Object object = rs.getObject(columnIndex);
		
		if(object instanceof Clob) {
			Clob clob = (Clob) object;
			if (clob != null) return clob.getSubString(1, (int) clob.length());
		}
		
		return object;
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getObject(columnIndex);
	}
	
}
