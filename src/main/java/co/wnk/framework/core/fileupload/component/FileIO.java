package co.wnk.framework.core.fileupload.component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import co.wnk.framework.core.common.util.config.ApplicationProperty;
import co.wnk.framework.core.common.util.message.WnkMessageProperty;
import co.wnk.framework.core.common.util.WnkDateUtil;

@Component
public class FileIO {
	
	public static final String ORIGINAL_FILE_NAME = "fileNameOri";
	public static final String FILE_EXT = "fileNameExt";
	public static final String SAVED_FILE_NAME = "fileNameToSave";
	public static final String SAVED_FILE_SIZE = "fileSizeToSave";
	
	public Map<String,Object> saveFile(MultipartFile file) throws Exception{
		String fileNameOri =file.getOriginalFilename();
		String fileSize = file.getSize()+"";
		String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
		String fileNameToSave = WnkDateUtil.getToday() + System.currentTimeMillis() +"."+ fileNameExt;
		File uploadFile = new File(ApplicationProperty.get("upload.path"),fileNameToSave);		
		
		if(uploadFile.mkdirs() == false)
			throw new AuthenticationException(WnkMessageProperty.getMessage("F004"));
		
		file.transferTo(uploadFile);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(ORIGINAL_FILE_NAME, fileNameOri);
		resultMap.put(FILE_EXT, fileNameExt);
		resultMap.put(SAVED_FILE_NAME, fileNameToSave);
		resultMap.put(SAVED_FILE_SIZE, fileSize);
		
		return resultMap;
	}
}
