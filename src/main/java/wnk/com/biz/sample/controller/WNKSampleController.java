package wnk.com.biz.sample.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.wnk.framework.core.common.AsyncResponseMap;
import co.wnk.framework.core.common.Constants;
import co.wnk.framework.core.common.util.WnkExcelUtil;
import co.wnk.framework.core.common.util.WnkStringUtil;
import co.wnk.framework.core.common.util.message.WnkMessageProperty;
import wnk.com.biz.common.service.FileUploadService;
import wnk.com.biz.sample.service.WNKSampleService;

@Controller
public class WNKSampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(WNKSampleController.class);
	
	@Autowired private WNKSampleService service;
	@Autowired private AsyncResponseMap responseMap;
	@Autowired private FileUploadService fileService;
	
	/**
	 * 샘플 페이지 메인
	 * @param paramMap
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping(value = "/sample/sampleMain.mvc")
	public void sampleMain(Map<String,Object> paramMap, ModelMap model,Locale locale) throws Exception {
	
	}
	
	/**
	 * 게시판 게시글 리스트
	 * @param paramMap
	 * @param model
	 */
	@RequestMapping(value = "/sample/sampleBoard/boardList.mvc")
	public void sambleBoardList(Map<String,Object> paramMap, ModelMap model) {
		model.put(Constants.KEY_RESULTS, service.getPagedList(paramMap));
	}
	
	/**
	 * 게시판 게시글 상세
	 * @param paramMap
	 * @param model
	 */
	@RequestMapping(value = "/sample/sampleBoard/boardDetail.mvc")
	public void sambleBoardDetail(Map<String,Object> paramMap, ModelMap model) {
		model.put(Constants.KEY_RESULTS, service.getSelectBoardDetail(paramMap));
	}
	
	/**
	 * 게시판 게시글 삭제
	 * @param paramMap
	 * @param model
	 * @return
	 */
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
	
	/**
	 * 게시판 글쓰기 페이지
	 * @param paramMap
	 * @param model
	 */
	@RequestMapping(value = "/sample/sampleBoard/boardRegist.mvc")
	public void boardRegist(Map<String,Object> paramMap, ModelMap model) {
		if(paramMap.get("SEQ") != null && !paramMap.get("SEQ").equals(""))
			model.put(Constants.KEY_RESULTS, service.getSelectBoardDetail(paramMap));
	}
	
	/**
	 * 게시판 게시글 저장
	 * @param paramMap
	 * @param model
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sample/sampleBoard/boardSave.mvc")
	public @ResponseBody Map<String, Object> boardSave(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		int saveCnt = service.saveBoardArticle(paramMap);
		
		if(saveCnt > 0){
			String returnUrl = "/sample/sampleBoard/boardList.mvc?page="
					+WnkStringUtil.trim(paramMap.get("page"))+"&SEARCH_GUBUN="+WnkStringUtil.trim(paramMap.get("SEARCH_GUBUN"))+"&SEARCH_VALUE="
					+WnkStringUtil.trim(paramMap.get("SEARCH_VALUE"));
			
			return responseMap.setMessage(WnkMessageProperty.getMessage(Constants.saveOk)).setUrl(returnUrl);
		}else{
			return responseMap.setMessage(WnkMessageProperty.getMessage(Constants.error));
		}
		
	}
	
	/**
	 * 엑셀 다운로드 샘플
	 * @param paramMap
	 * @param modelMap
	 * @return
	 */
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
	
	/**
	 * 메세지 view 샘플
	 * @param paramMap
	 * @param model
	 */
	@RequestMapping(value = "/sample/message/sampleMessage.mvc")
	public void sampleMessage(Map<String,Object> paramMap, ModelMap model) {
		model.put("getMessage", WnkMessageProperty.getMessage("hello"));
	}
	
	/**
	 * 파일 다운로드 샘플
	 * @param paramMap
	 * @param model
	 * @return String
	 * @throws Throwable
	 */
	@RequestMapping(value = "/common/fileDownload.mvc")
	public String fileDownload(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		String fileSeq = MapUtils.getString(paramMap, "ATTAFILE_SEQ");
		if(fileSeq == null || fileSeq.equals("")) return "";
		model.put("FILE_MODEL", fileService.downloadAttachFile(paramMap));
		return "fileDownloadView";
	}
	
	/**
	 * javascirpt validation form
	 * @param paramMap
	 * @param model
	 * @throws Throwable
	 */
	@RequestMapping(value = "/sample/js/sampleJsValidation.mvc")
	public void sampleJsValidation(Map<String,Object> paramMap, ModelMap model) throws Throwable {
	
	}
	
	/**
	 * 엑셀 업로도 샘플 페이지
	 * @param paramMap
	 * @param model
	 * @throws Throwable
	 */
	@RequestMapping(value = "/sample/excel/sampleExcelUploadForm.mvc")
	public void sampleExcelUploadForm(Map<String,Object> paramMap, ModelMap model) throws Throwable {
	
	}
	
	@RequestMapping(value = "/sample/excel/sampleExcelUpload.mvc")
	public @ResponseBody List<Map<String, Object>> sampleExcelUpload(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		return WnkExcelUtil.excelUploadTempProcess(fileService.getAttachFileList("file"));
	}
	
	@RequestMapping(value = "/sample/js/sampleAjaxForm.mvc")
	public void sampleAjaxForm(Map<String,Object> paramMap, ModelMap model) throws Throwable {
	
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sample/js/sampleAjax.mvc")
	public @ResponseBody Map<String,Object> sampleAjax(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		return responseMap.setMessage("메세지만 전송합니다. 전송하신 파라미터 값은 [" + paramMap.get("param1") + "] 입니다.");
	}
	
	@RequestMapping(value = "/sample/js/sampleScriptForm.mvc")
	public void sampleScriptForm(Map<String,Object> paramMap, ModelMap model) throws Throwable {
	
	}
	
	@RequestMapping(value = "/sample/login/login.mvc")
	public void login(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		
	}
	
	@RequestMapping(value = "/sample/login/loginDetail.mvc")
	public void loginDetail(Map<String,Object> paramMap, ModelMap model) throws Throwable {
		
	}
}
