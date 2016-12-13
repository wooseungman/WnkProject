package co.wnk.framework.core.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WnkDateUtil {
	
	/**
	 * Constructor
	 */
	private WnkDateUtil() {}

	/**
	 * @return String
	 */
	public static String getYear() {
		return getDateByPattern("yyyy");
	}

	/**
	 * @return String
	 */
	public static String getMonth() {
		return getDateByPattern("MM");
	}

	/**
	 * @return String
	 */
	public static String getDay() {
		return getDateByPattern("dd");
	}

	/**
	 * @return String
	 */
	public static String getHour() {
		return getDateByPattern("HH");
	}

	/**
	 * @return String
	 */
	public static String getMinute() {
		return getDateByPattern("mm");
	}

	/**
	 * @return String
	 */
	public static String getSecond() {
		return getDateByPattern("ss");
	}

	/**
	 * 'yyyyMMdd'
	 * @return String
	 */
	public static String getShortDate() {
		return getDateByPattern("yyyyMMdd");
	}

	/**
	 * 'yyyyMMddHHmmss'
	 * @return String
	 */
	public static String getToday() {
		return getDateByPattern("yyyyMMddHHmmss");
	}

	/**
	 * 'yyyy-MM-dd'
	 * @return String
	 */
	public static String getDate() {
		return getDateByPattern("yyyy-MM-dd");
	}

	/**
	 * 'HHmmss'
	 * @return String
	 */
	public static String getShortTime() {
		return getDateByPattern("HHmmss");
	}

	/**
	 * 'HH:mm:ss'
	 * @return String
	 */
	public static String getTime() {
		return getDateByPattern("HH:mm:ss");
	}

	/**
	 * @return String
	 */
	public static String getWeekOfYear() {
		return getDateByPattern("w");
	}

	/**
	 * @return String
	 */
	public static String getWeekOfMonth() {
		return getDateByPattern("W");
	}

	/**
	 * 'yyyy-MM-dd HH:mm:ss'
	 * @return String
	 */
	public static String getFullDate() {
		return getDateByPattern("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @return String
	 */
	public static String getDateByPattern(String pattern) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern, java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return String
	 */
	public static String getDateByPattern(String pattern, Locale locale) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern, locale);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return int
	 */
	public static String getLastDate(String date) {
		Calendar calendar = Calendar.getInstance();

		int yyyy = Integer.parseInt(date.substring(0, 4));
		int MM = Integer.parseInt(date.substring(4, 6));
		int dd = Integer.parseInt(date.substring(6, 8));

		calendar.set(yyyy, MM - 1, dd);

		return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @param date yyyyMMdd
	 * @return result
	 */
	public static String getDayOfWeek(String date) {
		if (date == null) return null;

		int yyyy = 0, MM = 1, dd = 1, day_of_week; // default
		String days[] = {"월", "화", "수", "목", "금", "토", "일"};

		try {
			yyyy = Integer.parseInt(date.substring(0, 4));
			MM = Integer.parseInt(date.substring(4, 6));
			dd = Integer.parseInt(date.substring(6, 8));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(yyyy, MM - 1, dd);
		day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

		return days[day_of_week - 1];
	}

	/**
	 * getDayOfWeek("20000225") -> 6
	 * @param date yyyyMMdd
	 * @return int
	 */
	public static int getWeekday(String date) {
		if (date == null) return -1;

		int yyyy = 0, MM = 1, dd = 1, day_of_week; // default

		try {
			yyyy = Integer.parseInt(date.substring(0, 4));
			MM = Integer.parseInt(date.substring(4, 6));
			dd = Integer.parseInt(date.substring(6, 8));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(yyyy, MM - 1, dd);
		day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

		return day_of_week;
	}

	/**
	 * @param date : yyyyMMdd
	 * @return
	 */
	public static int getWeekOfMonth(String date) {
		Calendar calendar = Calendar.getInstance();

		int yyyy = Integer.parseInt(date.substring(0, 4));
		int MM = Integer.parseInt(date.substring(4, 6));
		int dd = Integer.parseInt(date.substring(6, 8));

		calendar.set(yyyy, MM - 1, dd);

		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * @param date : yyyyMMdd
	 * @return
	 */
	public static int getTotalWeekOfMonth(String date) {
		Calendar calendar = Calendar.getInstance();

		int yyyy = Integer.parseInt(date.substring(0, 4));
		int MM = Integer.parseInt(date.substring(4, 6));
		int dd = Integer.parseInt(date.substring(6, 8));

		calendar.set(yyyy, MM - 1, dd);

		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * @param date : yyyyMMdd
	 * @param type : START or END
	 */
	public static String getDateOfWeek(String date, String type) {
		String dateOfWeek = "";

		int START_OF_WEEK = 1;
		int END_OF_WEEK = 7;

		Calendar sCalendar = Calendar.getInstance();

		int yyyy = Integer.parseInt(date.substring(0, 4));
		int MM = Integer.parseInt(date.substring(4, 6));
		int dd = Integer.parseInt(date.substring(6, 8));

		sCalendar.set(yyyy, MM - 1, dd);

		Calendar eCalendar = (Calendar)sCalendar.clone();

		int dayOfWeek = sCalendar.get(Calendar.DAY_OF_WEEK);
		sCalendar.add(Calendar.DATE, START_OF_WEEK - dayOfWeek); // 占쎌뮇�됵옙遺우뵬占쏙옙占쎌눘�귨옙�곗쨮 筌욑옙��
		eCalendar.add(Calendar.DATE, END_OF_WEEK - dayOfWeek); // 筌띾뜆占쏙쭕�깆뒄占쎌눘��占쎌쥙�귨옙�곗쨮 筌욑옙��

		if ("START".equalsIgnoreCase(type)) {
			dateOfWeek = ""
				+ sCalendar.get(Calendar.YEAR)
				+ ((sCalendar.get(Calendar.MONTH)+1) < 10 ? "0" + (sCalendar.get(Calendar.MONTH)+1) : (sCalendar.get(Calendar.MONTH)+1))
				+ (sCalendar.get(Calendar.DATE) < 10 ? "0" + sCalendar.get(Calendar.DATE) : sCalendar.get(Calendar.DATE));

		} else if ("END".equalsIgnoreCase(type)) {
			dateOfWeek = ""
				+ eCalendar.get(Calendar.YEAR)
				+ ((eCalendar.get(Calendar.MONTH)+1) < 10 ? "0" + (eCalendar.get(Calendar.MONTH)+1) : (eCalendar.get(Calendar.MONTH)+1))
				+ (eCalendar.get(Calendar.DATE) < 10 ? "0" + eCalendar.get(Calendar.DATE) : eCalendar.get(Calendar.DATE));
		}

		return dateOfWeek;
	}

	/**
	 * @return
	 */
	public static String getDayOfWeek() {
		return getDayOfWeek(getDate());
	}

	/**
	 * AddMonth
	 * @param date yyyyMMdd
	 * @param month param
	 * @return String value
	 */
	public static String getAddMonth(String date, int month) {
		if (date == null) return null;
		if (date.length() < 8) return "";

		return getAddDate(date, "yyyyMMdd", "month", month);
	}

	/**
	 * AddMonth
	 * @param date yyyyMMdd
	 * @param month param
	 * @param type return
	 * @return String value
	 */
	public static String getAddMonth(String date, int month, String type) {
		if (date == null) return null;
		if (date.length() < 8) return "";

		return getAddDate(date, type, "month", month);
	}

	/**
	 * AddDay
	 * @param date yyyyMMdd
	 * @param rday param
	 * @return String value
	 */
	public static String getAddDay(String date, int day) {
		if (date == null) return null;
		if (date.length() < 8) return "";

		return getAddDate(date, "yyyyMMdd", "date", day);
	}

	/**
	 * AddDay
	 * @param date yyyyMMdd
	 * @param rday param
	 * @param type return
	 * @return String value
	 */
	public static String getAddDay(String date, int day, String type) {
		if (date == null) return null;
		if (date.length() < 8) return "";

		return getAddDate(date, type, "date", day);
	}

	/**
	 * AddHour
	 * @param date yyyyMMddHHmmss
	 * @param rday param
	 * @param type return
	 * @return String value
	 */
	public static String getAddHour(String date, int hour) {
		if (date == null) return null;
		if (date.length() < 14) return "";

		return getAddDate(date, "yyyyMMddHHmmss", "hour", hour);
	}

	/**
	 * AddHour
	 * @param date yyyyMMddHHmmss
	 * @param rday param
	 * @param type return
	 * @return String value
	 */
	public static String getAddHour(String date, int hour, String type) {
		if (date == null) return null;
		if (date.length() < 14) return "";

		return getAddDate(date, type, "hour", hour);
	}

	/**
	 * AddMinute
	 * @param date yyyyMMddHHmmss
	 * @param rday param
	 * @param type return
	 * @return String value
	 */
	public static String getAddMinute(String date, int minute) {
		return getAddDate(date, "yyyyMMddHHmmss", "minute", minute);
	}

	/**
	 * AddMinute
	 * @param date yyyyMMddHHmmss
	 * @param rday param
	 * @param type return
	 * @return String value
	 */
	public static String getAddMinute(String date, int minute, String type) {
		return getAddDate(date, type, "minute", minute);
	}

	/**
	 *
	 * @param date
	 * @param type
	 * @param gubn
	 * @param rec
	 * @return
	 */
	public static String getAddDate(String date, String type, String gubn, int rec) {
		String result = "";
		int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

		if (date == null) return null;
		if (type == null) return null;

		try {
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(4, 6)) - 1;
			day = Integer.parseInt(date.substring(6, 8));

			if (date.length() == 14) {
				hour = Integer.parseInt(date.substring(8, 10));
				min = Integer.parseInt(date.substring(10, 12));
				sec = Integer.parseInt(date.substring(12, 14));
			}

			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hour, min, sec);

			if (gubn == "month") {
				calendar.add(Calendar.MONTH, rec);

			} else if (gubn == "date") {
				calendar.add(Calendar.DATE, rec);

			} else if (gubn == "hour") {
				calendar.add(Calendar.HOUR_OF_DAY, rec);

			} else if (gubn == "minute") {
				calendar.add(Calendar.MINUTE, rec);
			}

			result = (new SimpleDateFormat(type)).format(calendar.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @param firstdate yyyyMMdd
	 * @param lastdate yyyyMMdd
	 * @return
	 */
	public static int getIntervalMonth(String firstdate, String lastdate) {
		return datediff("month", firstdate, lastdate);
	}

	/**
	 * @param firstdate yyyyMMdd
	 * @param lastdate yyyyMMdd
	 * @return
	 */
	public static int getIntervalDay(String firstdate, String lastdate) {
		return datediff("date", firstdate, lastdate);
	}

	/**
	 * @param gubn.("month", "date")
	 * @param firstdate yyyyMMdd
	 * @param lastdate yyyyMMdd
	 * @return
	 */
	public static int datediff(String gubn, String firstdate, String lastdate) {
		int returnValue = 0;
		long temp = 0;
		int year = 0, month = 0, day = 0, year1 = 0, month1 = 0, day1 = 0;
		int year2 = 0, month2 = 0;

		if (firstdate == null || firstdate.equals("")) return returnValue;
		if (lastdate == null || lastdate.equals("")) return returnValue;

		try {
			year = Integer.parseInt(firstdate.substring(0, 4));
			month = Integer.parseInt(firstdate.substring(4, 6));
			day = Integer.parseInt(firstdate.substring(6, 8));

			year1 = Integer.parseInt(lastdate.substring(0, 4));
			month1 = Integer.parseInt(lastdate.substring(4, 6));
			day1 = Integer.parseInt(lastdate.substring(6, 8));

			if (gubn.equals("date")) {
				TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
				Calendar calendar = Calendar.getInstance(tz);

				calendar.set((year - 1900), (month - 1), day);

				Calendar cal2 = Calendar.getInstance(tz);
				cal2.set((year1 - 1900), (month1 - 1), day1);

				java.util.Date temp1 = calendar.getTime();
				java.util.Date temp2 = cal2.getTime();

				temp = temp2.getTime() - temp1.getTime();

				if ((temp % 10) < 5)
					temp = temp - (temp % 10);
				else
					temp = temp + (10 - (temp % 10));

				returnValue = (int) (temp / (1000 * 60 * 60 * 24));

				if (returnValue == 0) {
					returnValue = 1;
				}

			} else {
				year2 = (year - year1) * 12;
				month2 = month - month1;
				returnValue = year2 + month2;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	/**
	 * @param second
	 * @return
	 */
	public static String secondToHHmmss(float second) {
		int sec = (int)second;
		int d=0, h=0, m=0, s=0;
		if (sec > 86400) {
			d = sec / 86400;
			sec %= 86400;
		}
		if (sec > 3600) {
			h = sec / 3600;
			sec %= 3600;
		}
		if (sec > 60) {
			m = sec / 60;
			sec %= 60;
		}
		if (sec < 60) {
			s = sec;
		}
		String result = "";
		if (d > 0) result = d + "";
		result += ((h < 10)? "0" + h : h) + ":" + ((m < 10)? "0" + m : m) + ":" + ((s < 10)? "0" + s : s);
		return result;
	}


	/**
	 * @Purpose : 날짜 비교
	 *
	 * @Method Name 	: compareDate
	 * @Author      	: 신희영
	 * @Date         	: 2012. 10. 22.
	 * @Return        	: String
	 * @Description
	 * 
	 * result 
	 * date2 기준
	 * 0 : error
	 * 1 : befor
	 * 2 : equal(same)
	 * 3 : after
	 * 
	 */
	public static String compareDate(Date date1, Date date2) {
		String result = "0";

		if (date1.before(date2)) {
			result = "1";
		} else if (date1.equals(date2)) {
			result = "2";
		} else if (date1.after(date2)) {
			result = "3";
		}

		return result;
	}

	/**
	 * @Purpose : 날짜 비교 (비교 대상이 string 이나 date, string 일 경우 사용) 
	 *
	 * @Method Name 	: compareDate
	 * @Author      	: 신희영
	 * @Date         	: 2012. 10. 22.
	 * @Return        	: String
	 * @Description
	 */
	public static String compareDate(Object date1, Object date2, String format) {
		Date dateConvert1 = new Date();
		Date dateConvert2 = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		try {
			if ("String".equals(date1.getClass().getSimpleName())) {
				dateConvert1 = (Date) formatter.parse(String.valueOf(date1));
			} else {
				dateConvert1 = (Date) date1;
			}

			if ("String".equals(date2.getClass().getSimpleName())) {
				dateConvert2 = (Date) formatter.parse(String.valueOf(date2));
			} else {
				dateConvert2 = (Date) date2;
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		return compareDate(dateConvert1, dateConvert2);
	}
	

	public static boolean betweenDate(Object chkDate, Object date1, Object date2, String format) {
		boolean bRtn = false;

		Date dateConvertTarget = new Date();
		Date dateConvertStart = new Date();
		Date dateConvertEnd = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		try {
			if ("String".equals(chkDate.getClass().getSimpleName())) {
				dateConvertTarget = (Date) formatter.parse(String.valueOf(chkDate));
			} else {
				dateConvertTarget = (Date) chkDate;
			}
			
			if ("String".equals(date1.getClass().getSimpleName())) {
				dateConvertStart = (Date) formatter.parse(String.valueOf(date1));
			} else {
				dateConvertStart = (Date) date1;
			}

			if ("String".equals(date2.getClass().getSimpleName())) {
				dateConvertEnd = (Date) formatter.parse(String.valueOf(date2));
			} else {
				dateConvertEnd = (Date) date2;
			}
			bRtn = dateConvertStart.compareTo(dateConvertTarget) * dateConvertTarget.compareTo(dateConvertEnd) > 0; 
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		
		return bRtn;
	}
}
