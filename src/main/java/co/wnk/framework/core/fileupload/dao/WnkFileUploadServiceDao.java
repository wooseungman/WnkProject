package co.wnk.framework.core.fileupload.dao;

import java.util.Map;

public interface WnkFileUploadServiceDao {
	
	public void saveUploadFile(Map<String,Object> paramMap);
	
	public String selectUploadFileSeq(Map<String,Object> paramMap);
	
	public Map<String,Object> selectUploadFileByFileSeq(Map<String,Object> paramMap);
	
	public int deleteUploadFileByFileSeq(Map<String,Object> paramMap);
}
