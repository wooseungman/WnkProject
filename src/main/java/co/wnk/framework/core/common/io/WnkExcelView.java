package co.wnk.framework.core.common.io;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class WnkExcelView extends AbstractExcelView {
	private static final Logger logger = LoggerFactory.getLogger(WnkExcelView.class);
	
	private String excelColumns;
	private String excelColumnLabel;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		
		String fileName = (String)model.get("fileName") +"-"+ format.format(now) +".xls";		
		if(userAgent.indexOf("MSIE") > -1) {
			fileName = URLEncoder.encode(fileName, "utf-8");
		}else if(userAgent.indexOf("Mozilla") > -1) {
			fileName = URLEncoder.encode(fileName, "utf-8");
		}else {
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		
		//response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "ATTachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		@SuppressWarnings("unchecked")
		List<Map<String,Object>> excelList = (List<Map<String,Object>>) model.get("excelList");

		excelColumns = (String) model.get("excelColumns");
		excelColumnLabel = (String) model.get("excelColumnLabel");
		
		HSSFSheet sheet = createFirstSheet(workbook);
		createColumnLabel(workbook, sheet);
		createPageRow(workbook, sheet, excelList);
	}
	
	
	private void createColumnLabel(HSSFWorkbook workbook, HSSFSheet sheet) {
		HSSFRow firstRow = sheet.createRow(1);
		HSSFCell cell = null;
		
		HSSFCellStyle columnLabelStyle = workbook.createCellStyle();
		columnLabelStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		columnLabelStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		columnLabelStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		columnLabelStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		columnLabelStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		columnLabelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		columnLabelStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		StringTokenizer st = new StringTokenizer(excelColumnLabel, "|");
		
		int i = 0;
		while(st.hasMoreElements()) {
			String tmpLabel	= st.nextToken();
			
			cell = firstRow.createCell(i);
			cell.setCellValue(tmpLabel);
			cell.setCellStyle(columnLabelStyle);
			i++;
		}
	}
	
	
	private void createPageRow(HSSFWorkbook workbook, HSSFSheet sheet, List<Map<String,Object>> excelList) {
		HSSFRow row = null;
		HSSFCell cell = null;

		HSSFCellStyle columnStyle = workbook.createCellStyle();
		columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);

		for(int i=0; i < excelList.size(); i++) {
			row	= sheet.createRow(i+2);
			Map<String,Object> map = excelList.get(i);
			
			int j = 0;
			StringTokenizer st = new StringTokenizer(excelColumns, "|");
			while(st.hasMoreElements()) {
				String tmpStr = st.nextToken();
				//logger.debug("tmpStr : "+ tmpStr);
				String[] strArray = StringUtils.substringsBetween(tmpStr, "{", "}");
				String replaceToStr = "";
				String replaceFromStr = "";

				for(int k=0; k<strArray.length; k++) {
					try{
						replaceToStr = (String)map.get(strArray[k]);
					}
					catch(ClassCastException ce) {
						replaceToStr = map.get(strArray[k]).toString();
					}
					finally {
						replaceFromStr = "\\{"+strArray[k]+"\\}";
						tmpStr = tmpStr.replaceAll(replaceFromStr, replaceToStr == null ? "" : replaceToStr);
					}
				}
				
				cell = row.createCell(j);
				cell.setCellValue(tmpStr);
				cell.setCellStyle(columnStyle);
				
				j++;
			}
		}
	}

	
	private HSSFSheet createFirstSheet(HSSFWorkbook workbook) {
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Research List");
		return sheet;
	}
}
