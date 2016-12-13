package wnk.com.biz.sample.controller;

import java.util.Map;
import java.util.UUID;

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

import co.wnk.framework.core.common.util.message.WnkMessageProperty;
import wnk.com.biz.common.service.FileUploadService;
import wnk.com.biz.sample.service.WNKSampleService;
import co.wnk.framework.core.security.vo.User;

@Controller
public class WNKSampleController {
	
	@Value("#{app['page.size']}")
	private int test;
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleController.class);
	
	@Autowired private WNKSampleService service;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Autowired private FileUploadService fileService;
	
	//@Autowired WnkMessageProperty message;
	
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
		model.addAttribute("getMessage", WnkMessageProperty.getMessage("hello"));
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
	
	@RequestMapping(value = "/sample/sampleFileUpload.mvc")
	public void sampleFileUpload(Map<String,Object> paramMap, ModelMap model) { }
	
	@RequestMapping(value = "/sample/sampleFileUploadSave.mvc")
	public void sampleFileUploadSave(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		paramMap.put("SOURCE_UNIQUE_SEQ", UUID.randomUUID());
		fileService.upLoad(paramMap, "file");
	}
	
	@RequestMapping(value = "/sample/sampleTransactionCheck.mvc")
	public void sampleTransactionCheck(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		service.saveTest(paramMap);
	}
	
	@RequestMapping(value = "/sample/sampleMessageChangeTest.mvc")
	public void sampleMessageChangeTest(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		System.out.println("hello : " + WnkMessageProperty.getMessage("hello"));
	}
}
