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
	
	public int insertSample(Map<String,Object> paramMap){
		return (int) dao.insert("sample.insertSample", paramMap);
	}
	
	public int insertTest(Map<String,Object> paramMap){
		return (int) dao.insert("sample.insertMybatis", paramMap);
	}
	
	public void saveTest(Map<String,Object> paramMap){
		paramMap.put("title", "1");
		this.insertTest(paramMap);
		
		paramMap.put("title", "1111111111111111111111111111111");
		this.insertTest(paramMap);
	}
}
