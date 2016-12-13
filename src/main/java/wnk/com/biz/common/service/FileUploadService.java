package wnk.com.biz.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import co.wnk.framework.core.common.util.WnkSpringBeanUtil;
import co.wnk.framework.core.fileupload.component.WnkFileUploadComponent;

@Service
public class FileUploadService {
	
	public void upLoad(Map<String,Object> paramMap, String fileName) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WnkFileUploadComponent fileUploadService = (WnkFileUploadComponent)WnkSpringBeanUtil.getStringBean("fileUploadComponent");
		fileUploadService.uploadFile(paramMap, fileName, request);
	}
}
