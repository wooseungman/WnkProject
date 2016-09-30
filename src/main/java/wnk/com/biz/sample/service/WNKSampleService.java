package wnk.com.biz.sample.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wnk.com.biz.sample.dao.WNKSampleDao;
import wnk.com.common.interceptor.WnkRowBounds;

@Service
public class WNKSampleService {
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleService.class);
	
	@Autowired WNKSampleDao dao;
	
	public List<?> getPagedList(Map<String,Object> params){
		return dao.selectList("sample.selectSampleList", params, new WnkRowBounds().getRowBounds());
	}
}
