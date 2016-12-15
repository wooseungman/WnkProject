package wnk.com.biz.sample.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wnk.com.biz.sample.dao.WNKSampleDao;
import co.wnk.framework.core.dao.vo.WnkRowBounds;

@Service
public class WNKSampleService {
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleService.class);
	
	@Autowired WNKSampleDao dao;
	
	public List<?> getPagedList(Map<String,Object> paramMap){
		return dao.selectPagedList("sample.selectSampleList", paramMap);
	}
	
	public List<?> getList(Map<String,Object> paramMap){
		return dao.selectList("sample.selectSampleList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getSelectBoardDetail(Map<String,Object> paramMap){
		return (Map<String,Object>) dao.selectOne("sample.selectSampleList", paramMap);
	}
	
	public int removeBoardArticle(Map<String,Object> paramMap){
		return (Integer) dao.delete("sample.deleteSample", paramMap);
	}
	
	public int insertBoardArticle(Map<String,Object> paramMap){
		return (Integer) dao.insert("sample.insertSample", paramMap);
	}
	
	public int updateBoardArticle(Map<String,Object> paramMap){
		return (Integer) dao.insert("sample.updateSample", paramMap);
	}
}
