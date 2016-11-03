package wnk.com.biz.sample.controller;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import wnk.com.biz.common.util.WnkMessage;
import wnk.com.biz.sample.service.WNKSampleService;

@Controller
public class WNKSampleController {
	
	@Value("#{app['page.size']}")
	private int test;
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleController.class);
	
	@Autowired private WNKSampleService service;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/sample/sampleMain.mvc")
	public void sampleMain(@RequestParam Map<String,Object> paramMap, ModelMap model) {
		System.out.println("www : " + WnkMessage.getMessage("hello"));
	}
	
	@RequestMapping(value = "/sample/sampleBoard/boardList.mvc")
	public void sampleBoardList(Map<String,Object> paramMap, ModelMap model) {
		
		model.put("list", service.getPagedList(paramMap));
		Iterator<String> itr = paramMap.keySet().iterator();
		while(itr.hasNext()){
			String key = itr.next();
			System.out.println("key : " + key + " || " + paramMap.get(key));
		}
	}
	
	@RequestMapping(value = "/signin.mvc")
    public void signin(Map<String,Object> paramMap, ModelMap model) {
		
		System.out.println("************************************");
		System.out.println("START!!!!!!!!");
		System.out.println("************************************");
		
		
         model.addAttribute("error", paramMap.get("error"));

         // Sha 암호값을 보기 위한 테스트용.
         String guest_password = passwordEncoder.encodePassword("guest", null);
         String admin_password = passwordEncoder.encodePassword("admin", null);

         logger.info(guest_password + "//" + admin_password);
    }
	
}
