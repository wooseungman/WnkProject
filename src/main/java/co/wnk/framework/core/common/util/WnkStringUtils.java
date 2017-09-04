/****************************************************************
 * WnkStringUtils Class
 * String 관련 Utility
 * @author skycow79
 * @version 1.0
 * @date 2016.12.22
 ****************************************************************/
package co.wnk.framework.core.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WnkStringUtils {
	
	/*******************************************************************
	 * 입력받은 문자열을 token을 기준으로 배열로 변환하여 리턴한다.
	 * @param str
	 * @param token
	 * @return
	 *******************************************************************/
	public static String[] split(String str, String token){
		return StringUtils.isNotEmpty(str) ? str.split("\\" + token) : null;
	}
	
	/*******************************************************************
	 * 입력받은 문자열을 token을 기준으로 배열로 변환하여 리턴한다.
	 * @param str
	 * @param token
	 * @return String[]
	 *******************************************************************/
	public static String[] stringTokenizer(String str, String token) {
		String[] StringArray = null;
		int i=0;
		if(StringUtils.isNotEmpty(str)){
			StringTokenizer stz = new java.util.StringTokenizer(str, token);
			StringArray = new String[stz.countTokens()];
			while (stz.hasMoreTokens()) StringArray[i++] = stz.nextToken();
		}
		
		return StringArray;
	}

	/*******************************************************************
	 * 입력받은 문자열을 대문자로 변환한다.
	 * @param str
	 * @return String
	 *******************************************************************/
	public static String upperCase(String str) {
		return StringUtils.upperCase(str);
	}
	
	/*******************************************************************
	 * 입력받은 문자열을 대문자로 변환한다.
	 * @param str
	 * @param locale
	 * @return
	 *******************************************************************/
	public static String upperCase(String str, Locale locale) {
		return StringUtils.upperCase(str, locale);
	}
	
	/*******************************************************************
	 * 입력받은 문자열을 소문자로 변환한다.
	 * @param str
	 * @return
	 *******************************************************************/
	public static String lowerCase(String str){
		return StringUtils.lowerCase(str);
	}
	
	/*******************************************************************
	 * 입력받은 문자열을 소문자로 변환한다.
	 * @param str
	 * @param locale
	 * @return
	 *******************************************************************/
	public static String lowerCase(String str, Locale locale){
		return StringUtils.lowerCase(str, locale);
	}
	
	/*******************************************************************
	 * 입력받은 문자열이 null값인지 체크한다.
	 * @param str
	 * @return boolean
	 *******************************************************************/
	public static boolean isEmpty(String str){
		return StringUtils.isEmpty(str);
	}
	
	/*******************************************************************
	 * 입력받은 문자열이 null값이 아닌지 체크한다.
	 * @param str
	 * @return boolean
	 *******************************************************************/
	public static boolean isNotEmpty(String str){
		return StringUtils.isNotEmpty(str);
	}
	
	/*******************************************************************
	 * 입력받은 문자열이 빈값(공백값)인지 체크한다.
	 * @param str
	 * @return boolean
	 *******************************************************************/
	public static boolean isBlank(String str){
		return StringUtils.isBlank(str);
	}
	
	/*******************************************************************
	 * 입력받은 문자열이 빈값(공백값)이 아닌지 체크한다.
	 * @param str
	 * @return boolean
	 *******************************************************************/
	public static boolean isNotBlank(String str){
		return StringUtils.isNotBlank(str);
	}
	
	/*******************************************************************
	 * 입력받은 문자열에서 특정 문자열을 찾아서 치환한다.
	 * @param text
	 * @param searchString
	 * @param replacement
	 * @return String
	 *******************************************************************/
	public static String replace(String text, String search, String replace) {
		return StringUtils.replace(text, search, replace);
	}
	
	/*******************************************************************
	 * 입력받은 문자열에서 문자배열에 들어 있는 값을 검색하여 
	 * 문자배열에 있는 값으로 치환한다.
	 * @param text
	 * @param search
	 * @param replacement
	 * @return
	 *******************************************************************/
	public static String replace(String text, String[] search, String[] replacement) {
		return StringUtils.replaceEach(text, search, replacement);
	}

	/*******************************************************************
	 * 입력받은 숫자를 1000단윈로 ,를 추가하여 리턴한다.
	 * @param number
	 * @return String
	 *******************************************************************/
	public static String displayComma(Double number){
		return new DecimalFormat("#,##0").format(number);
	}
	
	/*******************************************************************
	 * 입력받은 문자를 1000단윈로 ,를 추가하여 리턴한다.
	 * @param number
	 * @return String
	 *******************************************************************/
	public static String displayComma(String number){
		return StringUtils.isNotEmpty(number) ? 
				displayComma(Double.parseDouble(number)) : number;
	}
	
	/*******************************************************************
	 * 입력받은 Map Object에서 String을 꺼내 리턴한다.
	 * @param map
	 * @param obj
	 * @param defStr
	 * @return String
	 *******************************************************************/
	public static String getString(Map<String, Object> map, String obj){
		return map.containsKey(obj) ? String.valueOf(map.get(obj)) : null; 
	}
	
	/*******************************************************************
	 * 입력받은 Map Object에서 String을 꺼내 리턴한다.
	 * @param txtMap
	 * @param obj
	 * @param sDefault
	 * @return String
	 *******************************************************************/
	public static String getString(Map<String, Object> map, String obj, String defStr) {
		return map.containsKey(obj) ? String.valueOf(map.get(obj)) : defStr; 
	}
	
	/*******************************************************************
	 * 입력받은 Map Object에서 Int Value을 꺼내 리턴한다.
	 * @param txtMap
	 * @param obj
	 * @return int
	 *******************************************************************/
	public static int getInt(Map<String, Object> map, String obj) {
		return map.containsKey(obj) ? getConvertInteger(String.valueOf(map.get(obj))) : null;
	}
	
	/*******************************************************************
	 * 입력받은 Map Object에서 Int Value을 꺼내 리턴한다.
	 * @param txtMap
	 * @param obj
	 * @param defStr
	 * @return int
	 *******************************************************************/
	public static int getInt(Map<String, Object> map, String obj, int defStr) {
		return map.containsKey(obj) ? getConvertInteger(String.valueOf(map.get(obj))) : defStr;
	}

	/*******************************************************************
	 * 문자열을 확인하여 빈문자열인 경우 디폴트 문자열을 출력한다.
	 * @param param
	 * @param changeValue
	 * @return
	 *******************************************************************/
	public static String defaultStr(String str, String defStr) {
		return StringUtils.isNotEmpty(str) ? str.trim() : defStr;
	}
	
	/******************************************************************
	 * 입력받은 문자열을 숫자로 변경하여 반환한다. 
	 * @param str
	 * @return
	 ******************************************************************/
	public static int getConvertInteger(String str) {
		return StringUtils.isNotEmpty(str) ? Integer.parseInt(str) : null;
	}

	/******************************************************************
	 * 일정한 길이만큼 좌측으로 문자를 채운다
	 * @param value
	 * @param putChar
	 * @param count
	 * @return
	 ******************************************************************/
	public static String lpad(String str, char putChar, int count) {
		String valueStr = null;
		if(StringUtils.isNotEmpty(str) && str.length() < count){
			int srcCnt = str.length();
			int forCnt = count - srcCnt;
			String putString = "";
			for(int i = 0; i < forCnt; i++) putString += String.valueOf(putChar);
			valueStr = putString + str;
		}else{
			valueStr = str;
		}
		
		return valueStr;
	}
	
	/******************************************************************
	 * CLOB 데이터를 String으로 변환한다.
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 ******************************************************************/
	public static String clobToString(Clob clob) throws SQLException, IOException {
		if (clob == null) return null;
		String str = "";
		StringBuilder strOut = new StringBuilder();
		BufferedReader br = new BufferedReader(clob.getCharacterStream());
		while ((str = br.readLine()) != null) strOut.append(str); 
		return strOut.toString();
	}
	
	/******************************************************************
	 * UUID를 이용하여 Sequence를 생성한다.
	 * @return String
	 ******************************************************************/
	public static String getUUIDSequence() {
		return UUID.randomUUID().toString();
	}
	
	/******************************************************************
	 * 입력된 Object를 JsonString으로 변환한다.
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 ******************************************************************/
	public static String getJsonString(Object obj) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return obj != null ? mapper.writeValueAsString(obj) : null;  
	}
	
	/******************************************************************
	 * 입력된 Json String을 Map<String, String> 형식으로 변환한다.
	 * @param str
	 * @return Map<String, String>
	 * @throws IOException
	 ******************************************************************/
	@SuppressWarnings("unchecked")
	public static Map<String, String> getJsonMap(String jsonStr) throws IOException{
		return StringUtils.isNotEmpty(jsonStr) ? (Map<String, String>) new ObjectMapper().readValue(jsonStr, 
				new TypeReference<HashMap<String, String>>() {}) : null;  
	}
	
	/******************************************************************
	 * 입력된 Json String을 List<Map<String, String>> 형식으로 변환한다.
	 * @param str
	 * @return List<Map<String, String>>
	 * @throws IOException
	 ******************************************************************/
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getJsonList(String jsonStr) throws IOException{
		return StringUtils.isNotEmpty(jsonStr) ? (List<Map<String, String>>) new ObjectMapper().readValue(jsonStr, 
				new TypeReference<ArrayList<HashMap<String, String>>>() {}) : null;  
	}
	
	/******************************************************************
	 * 입력된 문자열의 좌우 공백을 제거하여 리턴한다.
	 * @param str
	 * @return String
	 ******************************************************************/
	public static String trim(String str){
		return StringUtils.isNotEmpty(str) ? str.trim() : null;
	}
	
	/******************************************************************
	 * 입력된 문자열의 좌우 공백을 제거하여 리턴한다.
	 * @param obj
	 * @return String
	 ******************************************************************/
	public static String trim(Object obj){
		return StringUtils.isNotEmpty(String.valueOf(obj)) ? 
				String.valueOf(obj).trim() : null;
	}
	
	/*****************************************************************
	 * 입력된 문자열에서 특정 자리의 내용을 잘라내어 리턴한다.
	 * @param str
	 * @param startIdx
	 * @param endIdx
	 * @return int
	 *****************************************************************/
	public static int substringInt(String str,int startIdx, int endIdx){
		return StringUtils.isNotEmpty(String.valueOf(str)) ? 
				Integer.parseInt(str.substring(startIdx, endIdx)) : -1; 
	}
	
	/*****************************************************************
	 * 입력된 문자열에서 특정 자리의 내용을 잘라내어 리턴한다.
	 * @param str
	 * @param startIdx
	 * @param endIdx
	 * @return String
	 *****************************************************************/
	public static String substring(String str,int startIdx, int endIdx){
		return StringUtils.isNotEmpty(String.valueOf(str)) ? 
				str.substring(startIdx, endIdx) : null; 
	}
}
