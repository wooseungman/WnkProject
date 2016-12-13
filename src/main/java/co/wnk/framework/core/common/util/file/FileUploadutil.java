package co.wnk.framework.core.common.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.wnk.framework.core.common.util.config.ApplicationProperty;

public class FileUploadutil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadutil.class);
	
	/**
	 * 확장자를 파라미터로 받아서 파일의 타입을 리턴한다.
	 * @param ext
	 * @return
	 */
	public static String getFileType(String ext){
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
	public static String getFileTypeByFileName(String fileName){
		return FileUploadutil.getFileType(fileName.substring(fileName.indexOf(".")+1,fileName.length()));
	}
}
