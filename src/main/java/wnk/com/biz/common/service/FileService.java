package wnk.com.biz.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import wnk.com.biz.framework.config.ApplicationProperty;
import wnk.com.biz.framework.file.ThumnailerComponent;
import wnk.com.biz.framework.file.io.FileIO;
import wnk.com.biz.sample.dao.CommonDao;

public class FileService {
	
	@Autowired FileIO fileIO;
	@Autowired ThumnailerComponent thumnailerComponent;
	@Autowired CommonService commonService;
	@Autowired CommonDao dao;
	
	
	/**
	 * FILE UPLOAD
	 * @param paramMap
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public Map<String, Object> uploadFile(Map<String, Object> paramMap, MultipartFile[] file, HttpServletRequest request) throws Throwable{
		Map<String, Object> result = null;
		if(file == null) return null;
		
		for(MultipartFile f: file){
			if(f.isEmpty()) continue;
			
			String fileNameOri = f.getOriginalFilename();
			String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
			
			System.out.println("fileNameOri : " + fileNameOri);
			System.out.println("fileNameExt : " + fileNameExt);
			
			Map<String, Object> fileMap = fileIO.saveFile(f);
			
			String ATTAFILE_TP = (String)fileMap.get(FileIO.FILE_EXT);
			String ORGNFILE_NM = (String)fileMap.get(FileIO.ORIGINAL_FILE_NAME);
			String ATTAFILE_NM = (String)fileMap.get(FileIO.SAVED_FILE_NAME);
			String ATTAFILE_SIZE = (String)fileMap.get(FileIO.SAVED_FILE_SIZE);
			String ATTAFILE_PATH = ApplicationProperty.get("upload.path");
			
			System.out.println("ATTAFILE_TP : " + ATTAFILE_TP);
			System.out.println("ORGNFILE_NM : " + ORGNFILE_NM);
			System.out.println("ATTAFILE_NM : " + ATTAFILE_NM);
			System.out.println("ATTAFILE_SIZE : " + ATTAFILE_SIZE);
			System.out.println("ATTAFILE_PATH : " + ATTAFILE_PATH);
			
			String fileType = commonService.getFileType(fileNameExt);
			if(fileType.equals("IMAGE")){
				//createThumbnail(String sourceFullPathFileName, String targetFullPathFileName, int width, int height)
				thumnailerComponent.createThumbnail(ATTAFILE_PATH+ATTAFILE_NM, ATTAFILE_PATH+"thumb_"+ATTAFILE_NM, 500, 500);
			}
			
			Map<String, Object> attaMap = new HashMap<String, Object>();
			attaMap.put("SOURCE_UNIQUE_SEQ", paramMap.get("FILE_SOURCE_UNIQUE_SEQ"));
			attaMap.put("ATTAFILE_SEQ", commonService.getSequence());
			attaMap.put("ATTAFILE_TP", ATTAFILE_TP);
			attaMap.put("ORGNFILE_NM", ORGNFILE_NM);
			attaMap.put("ATTAFILE_NM", ATTAFILE_NM);
			attaMap.put("THUMBNAIL_NM", "thumb_"+ATTAFILE_NM);
			attaMap.put("ATTAFILE_PATH", ATTAFILE_PATH);
			attaMap.put("DEL_CD", paramMap.get("DEL_CD") != null && !paramMap.get("DEL_CD").equals("") ? paramMap.get("DEL_CD") : "N" );
			attaMap.put("ATTAFILE_SIZE", ATTAFILE_SIZE);
			attaMap.putAll(commonService.concatRegAndModSeq(attaMap, "TEMP_SEQ", request));
			
			dao.insert("file.insertAttaFile", attaMap);
			result = attaMap;
		}
		
		return result;
	}
}
