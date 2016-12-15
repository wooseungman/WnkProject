package wnk.com.biz.sample.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.wnk.framework.core.common.AsyncResponseMap;
import co.wnk.framework.core.common.Constants;
import co.wnk.framework.core.common.util.WnkStringUtil;
import co.wnk.framework.core.common.util.message.WnkMessageProperty;
import wnk.com.biz.common.service.FileUploadService;
import wnk.com.biz.sample.service.WNKSampleService;
import co.wnk.framework.core.security.vo.User;

@Controller
public class WNKSampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleController.class);
	
	@Autowired
	private WNKSampleService service;
	
	@Autowired
	private AsyncResponseMap responseMap; 
	
	@RequestMapping(value = "/sample/sampleMain.mvc")
	public void sampleMain(Map<String,Object> paramMap, ModelMap model) {
	
	}
	
	@RequestMapping(value = "/sample/sampleBoard/boardList.mvc")
	public void sambleBoardList(Map<String,Object> paramMap, ModelMap model) {
		model.put("list", service.getPagedList(paramMap));
	}
	
	@RequestMapping(value = "/sample/sampleBoard/boardDetail.mvc")
	public void sambleBoardDetail(Map<String,Object> paramMap, ModelMap model) {
		model.put("detail", service.getSelectBoardDetail(paramMap));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sample/sampleBoard/boardDelete.mvc")
	public @ResponseBody Map<String, Object> boardDelete(Map<String,Object> paramMap, ModelMap model) {
		
		int deleteCnt = service.removeBoardArticle(paramMap);
		if(deleteCnt > 0){
			String returnUrl = "/sample/sampleBoard/boardList.mvc?page="+WnkStringUtil.trim(paramMap.get("page"))
				+"&SEARCH_GUBUN="+WnkStringUtil.trim(paramMap.get("SEARCH_GUBUN"))+"&SEARCH_VALUE="
				+WnkStringUtil.trim(paramMap.get("SEARCH_VALUE"));
			
			return responseMap.setMessage(WnkMessageProperty.getMessage(Constants.deleteOk)).setUrl(returnUrl);
		}else{
			return responseMap.setMessage(WnkMessageProperty.getMessage(Constants.error));
		}
		
	}
	
	@RequestMapping(value = "/sample/sampleBoard/boardRegist.mvc")
	public void boardRegist(Map<String,Object> paramMap, ModelMap model) {
		if(paramMap.get("SEQ") != null && !paramMap.get("SEQ").equals(""))
			model.put("detail", service.getSelectBoardDetail(paramMap));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sample/sampleBoard/boardSave.mvc")
	public @ResponseBody Map<String, Object> boardSave(Map<String,Object> paramMap, ModelMap model) {
		
		int saveCnt = paramMap.get("SEQ") != null && !paramMap.get("SEQ").equals("") ? 
				saveCnt = service.updateBoardArticle(paramMap) : service.insertBoardArticle(paramMap);
		
		if(saveCnt > 0){
			String returnUrl = "/sample/sampleBoard/boardList.mvc?page="
					+WnkStringUtil.trim(paramMap.get("page"))+"&SEARCH_GUBUN="+WnkStringUtil.trim(paramMap.get("SEARCH_GUBUN"))+"&SEARCH_VALUE="
					+WnkStringUtil.trim(paramMap.get("SEARCH_VALUE"));
			
			return responseMap.setMessage(WnkMessageProperty.getMessage(Constants.saveOk)).setUrl(returnUrl);
		}else{
			return responseMap.setMessage(WnkMessageProperty.getMessage(Constants.error));
		}
		
	}
	
	@RequestMapping(value = "/sample/sampleBoard/boardListExcelDown.mvc")
	public ModelAndView boardListExcelDown(Map<String,Object> paramMap, ModelMap modelMap) {
		ModelAndView mav = new ModelAndView();
		List<Map<String, Object>> excelList = (List<Map<String,Object>>) service.getList(paramMap);
		mav.addObject("fileName", "SampleBaordExcel");
		mav.addObject("excelColumnLabel", "SEQ|ID|NAME|DESCRIPTION|USE_YN|REG_USER|REG_DATE");
		mav.addObject("excelColumns", "{SEQ}|{ID}|{NAME}|{DESCRIPTION}|{USE_YN}|{REG_USER}|{REG_DATE}");
		mav.addObject("excelList", excelList);
		mav.setViewName("excelView");
		
		return mav;
	}
	
	@RequestMapping(value = "/sample/message/sampleMessage.mvc")
	public void sampleMessage(Map<String,Object> paramMap, ModelMap model) {
		model.put("getMessage", WnkMessageProperty.getMessage("hello"));
	}
	
}
