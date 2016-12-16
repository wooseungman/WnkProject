package wnk.com.biz.sample.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import co.wnk.framework.core.fileupload.dao.WnkFileUploadServiceDao;

public class WnkSampleFileUploadServiceDao implements WnkFileUploadServiceDao {
	
	@Autowired WNKSampleDao dao;
	
	@Override
	public void saveUploadFile(Map<String, Object> paramMap) {
		dao.insert("file.insertAttaFile", paramMap);
	}

	@Override
	public String selectUploadFileSeq(Map<String, Object> paramMap) {
		return (String) dao.selectOne("file.selectMaxAttaFileKey", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectUploadFileByFileSeq(Map<String, Object> paramMap) {
		return (Map<String, Object>) dao.selectOne("file.selectAttachFileList", paramMap);
	}
	
	@Override
	public int deleteUploadFileByFileSeq(Map<String, Object> paramMap) {
		return (int) dao.selectOne("file.deleteAttachFile", paramMap);
	}
	
	
	
}
