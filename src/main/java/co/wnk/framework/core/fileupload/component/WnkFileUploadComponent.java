package co.wnk.framework.core.fileupload.component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.collections.MapUtils;

import co.wnk.framework.core.common.FileModel;
import co.wnk.framework.core.common.util.file.FileUploadutil;
import co.wnk.framework.core.fileupload.component.ThumnailerComponent;
import co.wnk.framework.core.fileupload.dao.WnkFileUploadServiceDao;
import co.wnk.framework.core.fileupload.component.FileIO;

@Service
public class WnkFileUploadComponent {
	
	private WnkFileUploadServiceDao dao;
	private FileIO fileIO;
	private ThumnailerComponent thumnailer;
	private String defaultUploadPath;
	
	public List<MultipartFile> getMultiFileList(String fileName, HttpServletRequest request){
		List<MultipartFile> fileList = null;
		
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			fileList = multiRequest.getFiles(fileName);
		}
		
		return fileList;
	}
	
	public String uploadFile(Map<String, Object> paramMap, String fileName, HttpServletRequest request) throws Throwable{
		if(defaultUploadPath != null && !defaultUploadPath.equals(""))
			return this.upload(paramMap, fileName, request, defaultUploadPath);
		else return "";
	}
	
	public String uploadFile(Map<String, Object> paramMap, String fileName, HttpServletRequest request, String uploadPath) throws Throwable{
		if(uploadPath != null && !uploadPath.equals("")) 
			return this.upload(paramMap, fileName, request, uploadPath);
		else return "";
			
	}
	
	private String upload(Map<String, Object> paramMap, String fileName, HttpServletRequest request, String uploadPath) throws Exception{
		String fileSeq = null;
		String fileGroupSeq = null;
		String fileType = null;
		String result = null;
		
		List<MultipartFile> fileList = this.getMultiFileList(fileName, request);
		if(fileList == null) return null;
		
		for(MultipartFile f: fileList){
			if(f.isEmpty()) continue;
			
			String fileNameOri = f.getOriginalFilename();
			String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
			
			System.out.println("fileNameOri : " + fileNameOri);
			System.out.println("fileNameExt : " + fileNameExt);
			
			Map<String, Object> fileMap = fileIO.saveFile(f, uploadPath);
			
			String ATTAFILE_TP = (String)fileMap.get(FileIO.FILE_EXT);
			String ORGNFILE_NM = (String)fileMap.get(FileIO.ORIGINAL_FILE_NAME);
			String ATTAFILE_NM = (String)fileMap.get(FileIO.SAVED_FILE_NAME);
			String ATTAFILE_SIZE = (String)fileMap.get(FileIO.SAVED_FILE_SIZE);
			String ATTAFILE_PATH = uploadPath;
			
			fileType = FileUploadutil.getFileType(fileNameExt);
			
			if(fileType.equals("IMAGE"))
				thumnailer.createThumbnail(ATTAFILE_PATH+ATTAFILE_NM, ATTAFILE_PATH+"thumb_"+ATTAFILE_NM, 500, 500);
			
			fileSeq = dao.selectUploadFileSeq(paramMap);
			if(fileGroupSeq == null)
				fileGroupSeq = fileSeq;
			
			Map<String, Object> attaMap = new HashMap<String, Object>();
			attaMap.put("ATTAFILE_SEQ", fileSeq);
			attaMap.put("ATTAFILE_GROUP_SEQ", fileGroupSeq);
			attaMap.put("ATTAFILE_TP", ATTAFILE_TP);
			attaMap.put("ORGNFILE_NM", ORGNFILE_NM);
			attaMap.put("ATTAFILE_NM", ATTAFILE_NM);
			attaMap.put("THUMBNAIL_NM", "thumb_"+ATTAFILE_NM);
			attaMap.put("ATTAFILE_PATH", ATTAFILE_PATH);
			attaMap.put("DEL_CD", paramMap.get("DEL_CD") != null && !paramMap.get("DEL_CD").equals("") ? paramMap.get("DEL_CD") : "N" );
			attaMap.put("ATTAFILE_SIZE", ATTAFILE_SIZE);
			dao.saveUploadFile(attaMap);
			result = fileGroupSeq;
		}
		return result;
	}
	
	public FileModel getAttachFile(Map<String, Object> paramMap){
		Map<String,Object> info = dao.selectUploadFileByFileSeq(paramMap);
		
		String ATTA_FILE_PATH = (String)info.get("ATTAFILE_PATH");
		String ORGN_FILE_NM = MapUtils.getString(info, "ORGNFILE_NM");
		String ATTA_FILE_NM = MapUtils.getString(info, "ATTAFILE_NM");
		
		System.out.println("ATTA_FILE_PATH : " + ATTA_FILE_PATH);
		System.out.println("ORGN_FILE_NM : " + ORGN_FILE_NM);
		System.out.println("ATTA_FILE_NM : " + ATTA_FILE_NM);
		
		return new FileModel(new File(ATTA_FILE_PATH + File.separator + ATTA_FILE_NM), ORGN_FILE_NM);
	}
	
	public int removeAttachFile(Map<String, Object> paramMap){
		return dao.deleteUploadFileByFileSeq(paramMap);
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

	public void setDefaultUploadPath(String defaultUploadPath) {
		this.defaultUploadPath = defaultUploadPath;
	}
}
