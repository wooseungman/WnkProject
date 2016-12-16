package co.wnk.framework.core.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import co.wnk.framework.core.common.FileModel;

public class WnkFileDownloadView extends AbstractView {

private static final Logger logger = LoggerFactory.getLogger(WnkFileDownloadView.class);
	
	private static final String CHARSET = "utf-8";
	
	public WnkFileDownloadView() {		
	
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		FileModel fileModel = (FileModel)model.get("FILE_MODEL");
		File file = fileModel.getFile();
		FileInputStream is = fileModel.getInputStream();
		String orgFileName = fileModel.getOrgFileName();
		
		try{
			MagicMatch mm = Magic.getMagicMatch(orgFileName.getBytes(), false);
			response.setContentType(mm.getMimeType()+"; charset="+CHARSET);
		}catch(MagicMatchNotFoundException me){
			response.setContentType("application/octet-stream; charset="+CHARSET);
		}
		
		
		//response.setCharacterEncoding(CHARSET);		
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		String userAgent = request.getHeader("User-Agent");
		
		if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5
			response.setHeader("Content-Disposition", "filename="+URLEncoder.encode(orgFileName, "UTF-8") + ";");
		} else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(orgFileName, "UTF-8") + ";");
		} else if (userAgent != null && userAgent.indexOf("Trident") > -1) { // MS IE
	    	response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(orgFileName, "UTF-8").replaceAll("\\+", "%20")  + ";");
		} else { // etc browser
	    	response.setHeader("Content-Disposition", "attachment; filename="+new String(orgFileName.getBytes(CHARSET), "latin1") + ";");
		}
		
		response.setHeader("Cache-Control","no-cache,no-store");
		
		
		OutputStream out = response.getOutputStream();
        FileInputStream fis = is;
        
        try
        {
        	if(fis == null){
        		fis = new FileInputStream(file);
        	}
            FileCopyUtils.copy(fis,out);            
        }
        catch(java.io.IOException ioe)
        {
        	logger.warn("file not found" + fileModel);
            /*ioe.printStackTrace();*/
        }
        finally
        {
            if(fis != null) fis.close();
        }
	}

}
