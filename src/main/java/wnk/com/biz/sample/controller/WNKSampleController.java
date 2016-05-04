package wnk.com.biz.sample.controller;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wnk.com.biz.sample.service.WNKSampleService;

@Controller
public class WNKSampleController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleController.class);
	
	@Autowired WNKSampleService service;
	
	@RequestMapping(value = "/sample/sampleMain.mvc")
	public void sampleMain(@RequestParam Map<String,Object> paramMap, ModelMap model) {
	
	}
	
	@RequestMapping(value = "/sample/sampleBoard/boardList.mvc")
	public void sampleBoardList(Map<String,Object> paramMap, ModelMap model) {
		
		Iterator<String> itr = paramMap.keySet().iterator();
		while(itr.hasNext()){
			String key = itr.next();
			System.out.println("key : " + key + " || " + paramMap.get(key));
		}
		
		model.put("list", service.getPagedList(paramMap));
		model.put("PAGE", paramMap.get("PAGE"));
		model.put("LIST_TOT_COUNT", paramMap.get("LIST_TOT_COUNT"));
	}
	
}
