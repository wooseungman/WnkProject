<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/sample.css'/>" />
<title>Basic Sample</title>
</head>
<body>
<div>
	시스템 에러가 발생하였습니다.<br/>
	에러 내용은 아래와 같습니다.
</div>
<table cellspacing="0" border="1">
<tr>
	<td>에러 메세지</td>
	<td>${ErrorMessage}</td>
</tr>
<tr>
	<td>에러 원인</td>
	<td>${ExceptionCause}</td>
</tr>
</table>
<div>
	<a href="${requestUrl}">이전으로 돌아가기</a>
</div>
</body>
</html>