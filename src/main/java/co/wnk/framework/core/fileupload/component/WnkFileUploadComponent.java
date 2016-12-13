package co.wnk.framework.core.fileupload.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import co.wnk.framework.core.common.util.config.ApplicationProperty;
import co.wnk.framework.core.common.util.file.FileUploadutil;
import co.wnk.framework.core.fileupload.component.ThumnailerComponent;
import co.wnk.framework.core.fileupload.dao.WnkFileUploadServiceDao;
import co.wnk.framework.core.fileupload.component.FileIO;

@Service
public class WnkFileUploadComponent {
	
	private WnkFileUploadServiceDao dao;
	private FileIO fileIO;
	private ThumnailerComponent thumnailer;
	
	public List<MultipartFile> getMultiFileList(String fileName, HttpServletRequest request){
		List<MultipartFile> fileList = null;
		
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			fileList = multiRequest.getFiles(fileName);
		}
		
		return fileList;
	}
	
	
	public Map<String, Object> uploadFile(Map<String, Object> paramMap, String fileName, HttpServletRequest request) throws Throwable{
		Map<String, Object> result = null;
		List<MultipartFile> fileList = this.getMultiFileList(fileName, request);
		if(fileList == null) return null;
		
		for(MultipartFile f: fileList){
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
			
			String fileType = FileUploadutil.getFileType(fileNameExt);
			
			if(fileType.equals("IMAGE"))
				thumnailer.createThumbnail(ATTAFILE_PATH+ATTAFILE_NM, ATTAFILE_PATH+"thumb_"+ATTAFILE_NM, 500, 500);
			
			Map<String, Object> attaMap = new HashMap<String, Object>();
			attaMap.put("SOURCE_UNIQUE_SEQ", paramMap.get("SOURCE_UNIQUE_SEQ"));
			attaMap.put("ATTAFILE_TP", ATTAFILE_TP);
			attaMap.put("ORGNFILE_NM", ORGNFILE_NM);
			attaMap.put("ATTAFILE_NM", ATTAFILE_NM);
			attaMap.put("THUMBNAIL_NM", "thumb_"+ATTAFILE_NM);
			attaMap.put("ATTAFILE_PATH", ATTAFILE_PATH);
			attaMap.put("DEL_CD", paramMap.get("DEL_CD") != null && !paramMap.get("DEL_CD").equals("") ? paramMap.get("DEL_CD") : "N" );
			attaMap.put("ATTAFILE_SIZE", ATTAFILE_SIZE);
			dao.saveUloadFileDao(attaMap);
			result = attaMap;
		}
		
		return result;
	}
	
	public void setDao(WnkFileUploadServiceDao dao) {
		this.dao = dao;
	}
	
	public void setFileIO(FileIO fileIO) {
		this.fileIO = fileIO;
	}

	public void setThumnailer(ThumnailerComponent thumnailer) {
		this.thumnailer = thumnailer;
	}
}
