package wnk.com.biz.sample.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wnk.com.biz.common.util.WnkMessage;
import wnk.com.biz.sample.service.WNKSampleService;
import wnk.com.common.security.Vo.User;

@Controller
public class WNKSampleController {
	
	@Value("#{app['page.size']}")
	private int test;
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleController.class);
	
	@Autowired private WNKSampleService service;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	/**
	 * 샘플 메인페이지
	 * @param paramMap
	 * @param model
	 */
	@RequestMapping(value = "/sample/sampleMain.mvc")
	public void sampleMain(@RequestParam Map<String,Object> paramMap, ModelMap model) { }
	
	/**
	 * 샘플 게시판 리스트
	 * @param paramMap
	 * @param model
	 */
	@RequestMapping(value = "/sample/sampleBoard/boardList.mvc")
	public void sampleBoardList(Map<String,Object> paramMap, ModelMap model) {
		model.put("list", service.getPagedList(paramMap));
	}
	
	@RequestMapping(value = "/sample/message/sampleMessage.mvc")
	public void sampleMessageTest(Map<String,Object> paramMap, ModelMap model) {
		model.addAttribute("getMessage", WnkMessage.getMessage("hello"));
	}
	
	@RequestMapping(value = "/sample/login/login.mvc")
    public void signin(Map<String,Object> paramMap, ModelMap model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if(principal != null && principal instanceof User){
			User user = (User) auth.getPrincipal();
			System.out.println("로그인 되어 있슴!!!!!!!");
		}
		
		//System.out.println("user : " + user.toString());
		//System.out.println("principal : " + principal.toString());
		
		System.out.println("************************************");
		
		
         model.addAttribute("error", paramMap.get("error"));

         // Sha 암호값을 보기 위한 테스트용.
         String guest_password = passwordEncoder.encodePassword("guest", null);
         String admin_password = passwordEncoder.encodePassword("admin", null);

         logger.info(guest_password + "//" + admin_password);
    }
	
}
