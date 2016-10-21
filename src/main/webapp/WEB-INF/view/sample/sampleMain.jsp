<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center><h1>Sample Page</h1></center><br/>
<table cellpadding="0" cellspacing="0" border="0">
<tr>
	<td><a href="./sampleBoard/boardList.mvc">1. 게시판 샘플(CRUD 포함)</a> 
	<spring:message code='hello' var="testMessage1"/>
	${testMessage1}
	</td>
</tr>
</table>
</body>
</html>