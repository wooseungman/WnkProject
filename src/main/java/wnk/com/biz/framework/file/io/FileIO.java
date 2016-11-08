package wnk.com.biz.framework.file.io;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import wnk.com.biz.common.util.DateUtil;
import wnk.com.biz.common.util.WnkMessage;
import wnk.com.biz.framework.config.ApplicationProperty;


public class FileIO {
	
	@Autowired WnkMessage messageProperty;
	
	public static final String ORIGINAL_FILE_NAME = "fileNameOri";
	public static final String FILE_EXT = "fileNameExt";
	public static final String SAVED_FILE_NAME = "fileNameToSave";
	public static final String SAVED_FILE_SIZE = "fileSizeToSave";
	
	public Map<String,Object> saveFile(MultipartFile file) throws Exception{
		String fileNameOri =file.getOriginalFilename();
		String fileSize = file.getSize()+"";
		String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
		String fileNameToSave = DateUtil.getToday() + System.currentTimeMillis() +"."+ fileNameExt;
		
		File uploadFile = new File(ApplicationProperty.get("upload.path"),fileNameToSave);		
		if(uploadFile.mkdirs() == false){
			throw new AuthenticationException(WnkMessage.getMessage("F004"));
		};
		
		file.transferTo(uploadFile);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(ORIGINAL_FILE_NAME, fileNameOri);
		resultMap.put(FILE_EXT, fileNameExt);
		resultMap.put(SAVED_FILE_NAME, fileNameToSave);
		resultMap.put(SAVED_FILE_SIZE, fileSize);
		
		return resultMap;
	}
}
