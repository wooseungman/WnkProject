package wnk.com.biz.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;
import java.util.Map;

public class StringUtil {
	
	/**
	 * Constructor
	 */
	private StringUtil() {}
	
	/**
	 * StringTokens
	 */
	public static String[] splitFullValue(String str, String token) {
		return str.split("\\" + token);
	}

	public static String[] StringTokens(String str, String token) {
		if (str != null && !str.equals("")) {
			java.util.StringTokenizer stz = new java.util.StringTokenizer(str, token);
			String[] data = new String[stz.countTokens()];
			int i = 0;
			while (stz.hasMoreTokens()) {
				data[i] = stz.nextToken();
				i++;
			}
			return data;
		}
		return null;
	}

	/**
	 */
	public static String upperEncorder(String token) {
		String enToken = "";
		int achar;

		for (int i = 0; i < token.length(); i++) {
			achar = token.charAt(i);
			if (achar >= 'a' && achar <= 'z')
				enToken += (char) ((achar - 'a') + 'A');
			else
				enToken += (char) achar;

		}
		return enToken;
	}

	/**
	 */
	public static String upperDecorder(String token) {
		String enToken = "";
		int achar;

		for (int i = 0; i < token.length(); i++) {
			achar = token.charAt(i);
			if (achar >= 'A' && achar <= 'Z')
				enToken += (char) ((achar - 'A') + 'a');
			else
				enToken += (char) achar;

		}
		return enToken;
	}

	/**
	 * isNotNull
	 */
	public static boolean isNotNull(String input[]) {
		boolean result = false;

		for (int i = 0; i < input.length; i++) {
			String temp = input[i];

			if (temp != null && !temp.equals("null") && !temp.equals(""))
				return true;
		}

		return result;
	}

	/**
	 */
	public static boolean isNull(String input[]) {
		return !isNotNull(input);
	}

	public static boolean isEmpty(Object obj) {
		boolean result = false;

		if (obj == null || "".equals(obj)) {
			return true;
		}

		return result;
	}

	public static String replace(String str, int sPos, int ePos, String replace) {
		StringBuffer result = new StringBuffer(str.substring(0, sPos));
		result.append(replace);
		result.append(str.substring(ePos));
		return result.toString();
	}

	public static String displayComma(String str) {
		if ("".equals(str)) {
			return str;
		} else if ("0".equals(str)) {
			return str;
		} else {
			NumberFormat nf = NumberFormat.getNumberInstance();
			return nf.format(Double.parseDouble(str));
		}
	}

	/**
	 * replaceInjectionAttack
	 */
	public static String replaceInjectionAttack(String str) {
		StringBuffer buffer = new StringBuffer();

		if (str != null) {
			char[] charArray = str.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == '<') {
					buffer.append("&#60;");
				} else if (charArray[i] == '>') {
					buffer.append("&#62;");
				} else if (charArray[i] == '"') {
					buffer.append("&#34;");
				} else if (charArray[i] == '\'') {
					buffer.append("&#39;");
				} else if (charArray[i] == '(') {
					buffer.append("&#40;");
				} else if (charArray[i] == ')') {
					buffer.append("&#41;");
				} else if (charArray[i] == '-') {
					buffer.append("&#45;");
				} else if (charArray[i] == '#') {
					buffer.append("&#35;");
				} else if (charArray[i] == '&') {
					buffer.append("&#38;");
				} else {
					buffer.append(charArray[i]);
				}
			}
		}
		return buffer.toString();
	}

	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static String getStr(String str, String defaultStr) {
		if (str == null || "".equals(str)) {
			return defaultStr;
		}
		return str.trim();
	}

	/**
	 * @param str
	 * @param len
	 * @param tail
	 * @return
	 */
	public static String getCutUTFString(String str, int len, String tail) {
		if (str.length() <= len) {
			return str;
		}
		StringCharacterIterator sci = new StringCharacterIterator(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append(sci.first());
		for (int i = 1; i < len; i++) {
			if (i < len - 1) {
				buffer.append(sci.next());
			} else {
				char c = sci.next();
				if (c != 32) {
					buffer.append(c);
				}
			}
		}
		buffer.append(tail);
		return buffer.toString();
	}
	
	/**
	 * Map에서 가져온 Object의 값에 따라 String[]로 변환하여 준다.
	 * @param o
	 * @return String[]
	 */
	public static String[] getParamStringArray(Object o){
		
		if(o != null){
			if(o instanceof String[]){
				return (String[])o;
			}else if(o instanceof String){
				String[] returnArray = {String.valueOf(o)};
				return returnArray;
			}
		}
		
		return new String[0];
		
	}
	
	public static String toHtml(String str) {
		if (str == null) {
			return null;
		} else {
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("\"", "&quot;");
			str = str.replaceAll("'", "&acute;");
			str = str.replaceAll("\\(", "&#40;");
			str = str.replaceAll("\\)", "&#41;");
			str = str.replaceAll("\r\n", "<br/>");
			str = str.replaceAll("\n", "<br/>");
			str = str.replaceAll(" ", "&nbsp;");
		    return str;
		}
	}
	
	public static String toSymbol(String str) {
		if (str == null) {
			return null;
		} else {
			str = str.replaceAll("&lt;", "<");
			str = str.replaceAll("&gt;", ">");
			str = str.replaceAll("&quot;", "\"");
			str = str.replaceAll("&acute;", "'");
			str = str.replaceAll("&#40;", "\\(");
			str = str.replaceAll("&#41;", "\\)");
			str = str.replaceAll("<br/>", "\r\n");
			str = str.replaceAll("&nbsp;", " ");
		    return str;
		}
	} 
	
	/**
	 * Map 에서 String으로 꺼내기
	 * @param txtMap
	 * @param obj
	 * @param sDefault
	 * @return
	 */
	public static String getMapString(Map<String, Object> txtMap, String obj, String sDefault) {
		String sRtn = "";
		
		if(txtMap.containsKey(obj)){
			sRtn = String.valueOf( txtMap.get(obj) );
		} else {
			sRtn = sDefault;
		}
		
		return sRtn;
	}
	
	/**
	 * Map 에서 Int로 꺼내기
	 * @param txtMap
	 * @param obj
	 * @param iDefault
	 * @return
	 */
	public static int getMapInt(Map<String, Object> txtMap, String obj, int iDefault) {
		int iRtn = 0;
		
		if(txtMap.containsKey(obj)){
			iRtn = StringToInt(String.valueOf( txtMap.get(obj) ));
		} else {
			iRtn = iDefault;
		}
		
		return iRtn;
	}

	/**
	 * 기본 String
	 * @param param
	 * @param changeValue
	 * @return
	 */
	public static String defaultStr(String param, String changeValue) {
		return (param == null || "".equals(param)) ? changeValue : param.trim();
	}
	
	/**
	 * String -> Int 
	 * @param str
	 * @return
	 */
	public static int StringToInt(String str) {
		int result = 0;
		if (!"".equals(defaultStr(str, ""))) {
			result = Integer.parseInt(str);
		}

		return result;
	}

	/**
	 * 일정한 길이만큼 좌측으로 문자를 채운다
	 * 
	 * @param value
	 *            String
	 * @param putChar
	 *            char
	 * @param count
	 *            int
	 * @return String
	 */
	public static String lpad(String value, char putChar, int count) {
		String valueStr = "";
		if (value != null && value.length() < count) {
			int srcCnt = value.length();
			int forCnt = count - srcCnt;
			String putString = "";

			for (int i = 0; i < forCnt; i++) {
				putString += String.valueOf(putChar);
			}
			valueStr = putString + value;
		} else {
			valueStr = value;
		}

		return valueStr;
	}
	
	/**
	 * clob -> String
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String clobToString(Clob clob) throws SQLException, IOException {

	  if (clob == null) {
	   return "";
	  }

	  StringBuffer strOut = new StringBuffer();

	  String str = "";

	  BufferedReader br = new BufferedReader(clob.getCharacterStream());

	  while ((str = br.readLine()) != null) {
	   strOut.append(str);
	  }
	  return strOut.toString();
	 }
}
