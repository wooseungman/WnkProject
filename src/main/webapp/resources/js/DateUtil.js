var dateObj = new Object();

/**
 * 오늘날짜 구하기 (yyyyMMdd)
 */
function getNowDate() {
	var date = new Date();
	var yyyy = date.getFullYear();
	var MM = date.getMonth()+1 < 10 ? "0" + (date.getMonth()+1) : date.getMonth()+1;
	var dd = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

	return yyyy + "-" + MM + "-" + dd;
}

/**
 * 현재 시간 구하기 (hh)
 */
function getCurrentHour(term) {
	var date = new Date();
	date.setTime(date.getTime() + term*60*1000);
	var hh = date.getHours();

	return hh;
}

/**
 * 현재 분 구하기 (mm)
 */
function getCurrentMinute(term) {
	var date = new Date();
	date.setTime(date.getTime() + term*60*1000);
	var mm = date.getMinutes();

	return mm;
}
/**
 * 오늘날짜에 현재시간까지 구하기 (yyyy.mm.dd hh:mm:ss)
 */
function getNowFullDate() {
	var date = new Date();
	var yyyy = date.getFullYear();
	var MM = date.getMonth()+1 < 10 ? "0" + (date.getMonth()+1) : date.getMonth()+1;
	var dd = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	var hh = date.getHours();
	var mm = date.getMinutes();
	var ss = date.getSeconds();

	return yyyy + "." + MM + "." + dd + " " + hh + ":" + mm + ":" + ss;
}

/**
 * 날짜차이 구하기
 * YYYYMMDD 형식의 두 날짜를 입력받아 뒤의 날짜에서 앞의 날짜를 뺸 일 수 차이를 돌려준다.
 * @param dateString1 : 날짜1
 * @param dateString2 : 날짜2
 * @returns
 */
function getDateDiff(dateString1, dateString2) {
    var date1 = new Date();
    var date2 = new Date();

    date1.setFullYear(dateString1.substring(0,4));
    date1.setMonth(dateString1.substring(4, 6)-1);
    date1.setDate(dateString1.substring(6, 8));

    date2.setFullYear(dateString2.substring(0,4));
    date2.setMonth(dateString2.substring(4,6)-1);
    date2.setDate(dateString2.substring(6,8));

    var day   = 1000 * 3600 * 24;		//24시간

    return Number((date2 - date1) / day, 10);
}

/**
 * YYYYMMDD 형식의 날짜에 달 더한 날짜 구하기
 * @param ymd : 날짜 문자열
 * @param addMonth : 더할 달(숫자)
 * @returns {String}
 */
function getDateAddMonth(ymd, addMonth){
	var yyyy = ymd.substring(0, 4);
	var mm = eval(ymd.substring(4, 6) + "-1") ;
	var dd = ymd.substring(6, 8);

	var dt = new Date(yyyy, eval(mm + '+' + addMonth), dd);

	yyyy = dt.getFullYear();
	mm = (dt.getMonth() + 1) < 10 ? "0" + (dt.getMonth() + 1) : (dt.getMonth() + 1) ;
	dd = dt.getDate() < 10 ? "0" + dt.getDate() : dt.getDate();

	return  "" + yyyy + mm + dd ;
}

/**
 * YYYYMMDD 형식의 날짜에 날짜 더하기
 * @param ymd : 날짜
 * @param addDay : 더할 일자(숫자)
 * @returns {String}
 */
function getDateAddDay(ymd, addDay) {
	var yyyy = ymd.substring(0, 4);
	var mm = eval(ymd.substring(4, 6) + "-1") ;
	var dd = ymd.substring(6, 8);

	var dt = new Date(yyyy, mm, eval(dd + '+' + addDay));

	yyyy = dt.getFullYear();
	mm = (dt.getMonth() + 1) < 10 ? "0" + (dt.getMonth() + 1) : (dt.getMonth() + 1) ;
	dd = dt.getDate() < 10 ? "0" + dt.getDate() : dt.getDate();

	return  "" + yyyy + mm + dd ;
}

/**
 * YYYYMMDD 형식의 날짜에 구분자 추가
 * @param ymd : 날짜 (8자리)
 * @param separator : 날짜포맷구분자
 * @returns {String}
 */
function formatDate(date, separator) {
	return date.substring(0, 4) + separator + date.substring(4, 6) + separator + date.substring(6, 8);
}


/**
 * 오늘, 1주일, 1개월, 3개월, 6개월, 1년 조회기간 설정
 * @param period
 * @param endElement
 * @param staratElement
 */
function setSearchDate(period, endElement, staratElement){
	var now = new Date();
	var year= now.getFullYear();
	var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
	var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
	var newDate = year + '-' + mon + '-' + day;
	var changeDate = null;
	
	if(period == 1){
		changeDate = newDate;
	}else if(period == 2){
		changeDate = calcChangeDate(now, -7)
	}else if(period == 3){
		changeDate = calcChangeDate(now, -30)
	}else if(period == 4){
		changeDate = calcChangeDate(now, -91)
	}else if(period == 5){
		changeDate = calcChangeDate(now, -182)
	}else if(period == 6){
		changeDate = calcChangeDate(now, -365)
	}
	
	$("#"+endElement).val(newDate);
	$("#"+staratElement).val(changeDate);
}
function calcChangeDate(now, count){
	var result = null;
	now.setDate(now.getDate() + count);
	year= now.getFullYear();
	mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
	day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
	return result = year + '-' + mon + '-' + day;
}