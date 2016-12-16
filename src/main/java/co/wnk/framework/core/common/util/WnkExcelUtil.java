package co.wnk.framework.core.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.wnk.framework.core.common.util.config.ApplicationProperty;

public class WnkExcelUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WnkExcelUtil.class);
	
	public static List<Map<String,Object>> excelUploadTempProcess(List<MultipartFile> file) throws Throwable {
		List<Map<String,Object>> result = null;
		String fileNameOri = null;
		String fileNameExt = null;
		String tmpSavePath = null;
		File tmpFile = null;
		
		for(MultipartFile f: file){
			if(f.isEmpty()) continue;
			fileNameOri = f.getOriginalFilename();
			
			fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? 
					fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
					
			tmpSavePath = ApplicationProperty.get("excel.path");
			tmpFile = new File(tmpSavePath, fileNameOri);
			f.transferTo(tmpFile);
			FileInputStream fis = new FileInputStream(tmpFile);
			if(fileNameExt.equals("xlsx"))																											//excel 2007 이상인 경우
				result = ExcelXlsxToList(fis);
			else if(fileNameExt.equals("xls"))																										//excel 97 ~ 2003 버전인 경우
				result = ExcelXlsToList(fis);
			else result = null;
			if(tmpFile.exists()) tmpFile.delete();																										//임시파일 삭제
		}
		return result;
	}
	
	/**
	 * excel 2007 이상인 경우 excel을 Parse하여 List형식으로 반환한다.
	 * @param fis
	 * @return List<Map<String,Object>>
	 * @throws IOException
	 */
	public static List<Map<String,Object>> ExcelXlsxToList(FileInputStream fis) throws IOException{
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		String imsi = null;
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = null;
	    XSSFRow row = null;
	    XSSFCell cell = null;
		int sheetNum = workbook.getNumberOfSheets();
		
		for(int k=0; k<sheetNum; k++){
	    	sheet = workbook.getSheetAt(k);
	    	int rows = sheet.getPhysicalNumberOfRows();
	    	
	    	for(int r=1; r<rows; r++){																				// 첫줄 네임명 생략
	    		Map<String,Object> resultMap = new HashMap<String,Object>();
	    		row = sheet.getRow(r);
	    		int cells = row.getLastCellNum();
	    		
	    		for(short c=0; c<cells; c++){
	    			cell = row.getCell(c);
	    			if(cell == null){
	    				resultMap.put("cell_"+c, "");
	    				continue;
	    			}
	    			
	    			switch(cell.getCellType()){
	    				case XSSFCell.CELL_TYPE_BOOLEAN:
	    					boolean bdata = cell.getBooleanCellValue();
	    					imsi = String.valueOf(bdata);
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				case XSSFCell.CELL_TYPE_NUMERIC:
	    					if (HSSFDateUtil.isCellDateFormatted(cell)){
	    						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    						imsi = formatter.format(cell.getDateCellValue());
	    					} else{
	    						int ddata = (int) cell.getNumericCellValue();
	    						imsi = String.valueOf(ddata);
	    					}
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				case XSSFCell.CELL_TYPE_STRING:
	    					imsi = cell.toString();
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				case XSSFCell.CELL_TYPE_BLANK:
	    				case XSSFCell.CELL_TYPE_ERROR:
	    				case HSSFCell.CELL_TYPE_FORMULA:
	    					imsi = cell.toString();
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				default:
	    					continue;
	    			}
	    		}
	    		resultList.add(resultMap);
	    	}
	    } 
		return resultList;
	}
	
	/**
	 * excel 97 ~ 2003 버전인 경우 excel을 Parse하여 List형식으로 반환한다.
	 * @param fis
	 * @return List<Map<String,Object>>
	 * @throws IOException
	 */
	public static List<Map<String,Object>> ExcelXlsToList(FileInputStream fis)  throws IOException{
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		String imsi = null;
		HSSFWorkbook workbook = new HSSFWorkbook(fis);

		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		
		int sheetNum = workbook.getNumberOfSheets();
		for(int k=0; k<sheetNum; k++){
	    	sheet = workbook.getSheetAt(k);
	    	int rows = sheet.getPhysicalNumberOfRows();
	    	for(int r=1; r<rows; r++){																				// 첫줄 네임명 생략
	    		Map<String,Object> resultMap = new HashMap<String,Object>();
	    		row = sheet.getRow(r);
	    		int cells = row.getLastCellNum();																	// 빈값이 포함되어 있는 Cell 때문에 마지막 Cell 체크
	    		for(short c=0; c<cells; c++){
	    			cell = row.getCell(c);
	    			if(cell == null){
	    				resultMap.put("cell_"+c, "");
	    				continue;
	    			}
	    			switch(cell.getCellType()){
	    				case HSSFCell.CELL_TYPE_BOOLEAN:
	    					boolean bdata = cell.getBooleanCellValue();
	    					imsi = String.valueOf(bdata);
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				case HSSFCell.CELL_TYPE_NUMERIC:
	    					if (HSSFDateUtil.isCellDateFormatted(cell)){
	    						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    						imsi = formatter.format(cell.getDateCellValue());
	    						resultMap.put("cell_"+c, imsi);
	    					} else{
	    						int ddata = (int) cell.getNumericCellValue();
	    						imsi = String.valueOf(ddata);
	    						resultMap.put("cell_"+c, imsi);
	    					}
	    					break;
	    				case HSSFCell.CELL_TYPE_STRING:
	    					imsi = cell.toString();
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				case HSSFCell.CELL_TYPE_BLANK:
	    				case HSSFCell.CELL_TYPE_ERROR:
	    				case HSSFCell.CELL_TYPE_FORMULA:
	    					imsi = cell.toString();
	    					resultMap.put("cell_"+c, imsi);
	    					break;
	    				default:
	    					continue;
	    			}
	    		}
	    		resultList.add(resultMap);
	    	}
	    }
		return resultList;
	}
	
}
