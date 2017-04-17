package wnk.com.biz.sample.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.wnk.framework.core.dao.vo.WnkRowBounds;

@Repository
public class CommonDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/*********************************************************************
	 * Query ID에 해당하는 Query를 가져온다.
	 * @param queryId
	 * @param params
	 * @return String
	 *********************************************************************/
	public String getSqlString(String queryId, Object params){
		return sqlSession.getConfiguration().getMappedStatement(queryId).getSqlSource().getBoundSql(params).getSql();
	}
	
	/*********************************************************************
	 * Query ID에 해당하는 Query를 실행하여 Insert 한다.
	 * @param queryId
	 * @param params
	 * @return int
	 *********************************************************************/
	public int insert(String queryId, Object params){
		return sqlSession.insert(queryId, params);
    }
    
	/*********************************************************************
	 * Query ID에 해당하는 Query를 실행하여 Update 한다.
	 * @param queryId
	 * @param params
	 * @return
	 *********************************************************************/
    public int update(String queryId, Object params){
    	return sqlSession.update(queryId, params);
    }
    
    /*********************************************************************
     * Query ID에 해당하는 Query를 실행하여 Delete 한다.
     * @param queryId
     * @param params
     * @return int
     *********************************************************************/
    public int delete(String queryId, Object params){
    	return sqlSession.delete(queryId, params);
    }
    
    /*********************************************************************
     * Query ID에 해당하는 Query를 실행하여 단건의 ResultSet을 가져온다.
     * @param queryId
     * @return Object
     *********************************************************************/
    public Object selectOne(String queryId){
    	return sqlSession.selectOne(queryId);
    }
    
    /*********************************************************************
     * Query ID에 해당하는 Query를 실행하여 단건의 ResultSet을 가져온다.
     * @param queryId
     * @param params
     * @return Object
     *********************************************************************/
    public Object selectOne(String queryId, Object params){
    	return sqlSession.selectOne(queryId, params);
    }
    
    /*********************************************************************
     * Query ID에 해당하는 Query를 실행하여 복수의 ResultSet을 List<Object>
     * 형식으로 가져온다.
     * @param queryId
     * @return List<?>
     *********************************************************************/
    public List<?> selectList(String queryId){
        return sqlSession.selectList(queryId);
    }
    
    /*********************************************************************
     * Query ID에 해당하는 Query를 실행하여 복수의 ResultSet을 List<Object>
     * 형식으로 가져온다.
     * @param queryId
     * @param params
     * @return List<?>
     *********************************************************************/
    public List<?> selectList(String queryId, Object params){
    	return sqlSession.selectList(queryId, params);
    }
    
    /*********************************************************************
     * Query ID에 해당하는 Query를 실행하여 복수의 ResultSet을 Pageing 처리하여
     * List<Object> 형식으로 가져온다.
     * @param queryId
     * @param params
     * @return List<?>
     *********************************************************************/
    public List<?> selectPagedList(String queryId, Object params){
    	return sqlSession.selectList(queryId, params, new WnkRowBounds().getRowBounds());
    }
}
