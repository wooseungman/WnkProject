package wnk.com.biz.sample.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import co.wnk.framework.core.fileupload.dao.WnkFileUploadServiceDao;

public class WnkSampleFileUploadServiceDao implements WnkFileUploadServiceDao {
	
	@Autowired WNKSampleDao dao;
	
	@Override
	public void saveUloadFileDao(Map<String, Object> paramMap) {
		dao.insert("file.insertAttaFile", paramMap);
	}

}
