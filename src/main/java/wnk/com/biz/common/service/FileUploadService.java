package wnk.com.biz.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import co.wnk.framework.core.common.FileModel;
import co.wnk.framework.core.common.util.WnkSpringBeanUtil;
import co.wnk.framework.core.fileupload.component.WnkFileUploadComponent;

@Service
public class FileUploadService {
	
	 public String upLoad(Map<String,Object> paramMap, String fileName) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WnkFileUploadComponent fileUploadService = (WnkFileUploadComponent)WnkSpringBeanUtil.getStringBean("fileUploadComponent");
		return fileUploadService.uploadFile(paramMap, fileName, request);
	}
	
	public FileModel download(Map<String,Object> paramMap) throws Throwable{
		WnkFileUploadComponent fileUploadService = (WnkFileUploadComponent)WnkSpringBeanUtil.getStringBean("fileUploadComponent");
		return fileUploadService.getAttachFileByAttaFileSeq(paramMap);
	}
	
	public List<MultipartFile> getMultiFileList(String fileName) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WnkFileUploadComponent fileUploadService = (WnkFileUploadComponent)WnkSpringBeanUtil.getStringBean("fileUploadComponent");
		return fileUploadService.getMultiFileList(fileName, request);
	}
}
