package wnk.com.biz.sample.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WNKSampleDao {
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleDao.class);
	
	@Autowired private SqlSessionTemplate sqlSession;
	
	public String getSqlString(String queryId, Object params){
		return sqlSession.getConfiguration().getMappedStatement(queryId).getSqlSource().getBoundSql(params).getSql();
	}
	
	public Object insert(String queryId, Object params){
		return sqlSession.insert(queryId, params);
    }
     
    public Object update(String queryId, Object params){
        return sqlSession.update(queryId, params);
    }
     
    public Object delete(String queryId, Object params){
        return sqlSession.delete(queryId, params);
    }
     
    public Object selectOne(String queryId){
        return sqlSession.selectOne(queryId);
    }
     
    public Object selectOne(String queryId, Object params){
		this.getSqlString(queryId, params);
    	return sqlSession.selectOne(queryId, params);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId){
        return sqlSession.selectList(queryId);
    }
     
    public List<?> selectList(String queryId, Object params, RowBounds rowBounds){
    	return sqlSession.selectList(queryId,params, rowBounds);
    }
    
    public List<?> selectPagzedList(String queryId, Object params){
    	String excuteQuery = this.getSqlString(queryId, params);
    	System.out.println("******************************* queryId : " + queryId + " Start ************************************");
    	System.out.println(excuteQuery);
    	System.out.println("******************************* queryId : " + queryId + " End ************************************");
    	return sqlSession.selectList(queryId,params,new RowBounds(10,10));
    }
	
}
