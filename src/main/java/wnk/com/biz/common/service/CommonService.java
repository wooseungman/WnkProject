package wnk.com.biz.common.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;

import wnk.com.biz.common.util.StringUtil;
import wnk.com.biz.framework.config.ApplicationProperty;
import wnk.com.biz.framework.file.io.FileIO;
import wnk.com.biz.sample.dao.CommonDao;

public class CommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	@Autowired	CommonDao dao;
	@Autowired	HttpServletRequest request;
	@Autowired FileIO fileIO;
	
	/**
	 * 
	 * @param request
	 * @param objNm
	 * @return
	 */
	public Object getSessionUserInfo(HttpServletRequest request, String objNm){
		//Map<String, Object> userInfo = (Map<String, Object>)request.getSession().getAttribute(Constants.loginSessionName);
		Map<String, Object> userInfo = (Map<String, Object>)request.getSession().getAttribute("memberSessionInfo");
		
		if(null == userInfo){
			return null;
		}
		return userInfo.get(objNm);
	}
	
	public Map<String, Object> concatRegAndModSeq(Map<String, Object> paramMap, String seqName, HttpServletRequest request){
		String seq = getSequence();
		String regMembersSeq = StringUtil.getStr((String)getSessionUserInfo(request, "MEMBERS_SEQ"), "101023123595901");	//101023123595901 는 임시데이터
		String modMembersSeq = regMembersSeq;
		
		paramMap.put(seqName, seq);
		paramMap.put("REG_MEMBER_SEQ", regMembersSeq);
		paramMap.put("MODIFY_MEMBER_SEQ", modMembersSeq);
		
		return paramMap;
	}
	
	public String getSequence() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 확장자를 파라미터로 받아서 파일의 타입을 리턴한다.
	 * @param ext
	 * @return
	 */
	public String getFileType(String ext){
		System.out.println("*********************************************");
		System.out.println("ext : " + ext);
		System.out.println("*********************************************");
		
		
		String imageExt = ApplicationProperty.get("image.extension");
		String documentExt = ApplicationProperty.get("document.extension");
		String videoExt = ApplicationProperty.get("video.extension");
		
		String[] imgArray = imageExt.split(",");
		String[] docArray = documentExt.split(",");
		String[] vodArray = videoExt.split(",");
		
		if(null != imgArray && imgArray.length > 0){
			for(String img : imgArray){
				if(ext.equalsIgnoreCase(img.trim())){
					return "IMAGE";
				}
			}
		}else if(null != docArray && docArray.length > 0){
			for(String doc : docArray){
				if(ext.equalsIgnoreCase(doc.trim())){
					return "DOC";
				}
			}
		}else if(null != vodArray && vodArray.length > 0){
			for(String vod : vodArray){
				if(ext.equalsIgnoreCase(vod.trim())){
					return "VIDEO";
				}
			}
		}
		
		return "NONE";
	}
	
	/**
	 * 파일명을 입력받아 파일 타입을 리턴한다.
	 * @param fileName
	 * @return String
	 */
	public String getFileTypeByFileName(String fileName){
		System.out.println("*****************************************************");
		System.out.println("fileName : " + fileName);
		System.out.println("*****************************************************");
		
		return this.getFileType(fileName.substring(fileName.indexOf(".")+1,fileName.length()));
	}
}
