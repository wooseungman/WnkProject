<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/jstl/app.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="center">
		총 게시물 : ${LIST_TOT_COUNT} / 페이지 : ${page}
		<form id="frm" name="searchForm" method="get" action="">
		<select id="SEARCH_GUBUN" name="SEARCH_GUBUN">
			<option value="">전체</option>
			<option value="ID" <c:if test="${param.SEARCH_GUBUN eq 'ID'}">selected</c:if>>ID</option>
		</select>
		<input type="text" name="SEARCH_VALUE" value="${param.SEARCH_VALUE}">
		<button value="submit">검색</button>
		</form>
		<table cellpadding="1" cellspacing="1" border="0" width="95%" bgcolor="#CCCCCC">
		<tr>
			<td bgcolor="#EEEEEE" align="center">ID</td>
			<td bgcolor="#EEEEEE" align="center">NAME</td>
			<td bgcolor="#EEEEEE" align="center">DESCRIPTION</td>
			<td bgcolor="#EEEEEE" align="center">USE_YN</td>
			<td bgcolor="#EEEEEE" align="center">REG_USER</td>
		</tr>
		<c:forEach items="${list}" var="list" varStatus="row">
		<tr>
			<td align="center" bgcolor="#FFFFFF">${list.ID}</td>
			<td align="center" bgcolor="#FFFFFF">${list.NAME}</td>
			<td align="center" bgcolor="#FFFFFF">${list.DESCRIPTION}</td>
			<td align="center" bgcolor="#FFFFFF">${list.USE_YN}</td>
			<td align="center" bgcolor="#FFFFFF">${list.REG_USER}</td>
		</tr>
		</c:forEach>
		</table>
		<app:paging />
	</td>
</tr>
<tr>
	<td align="center">
		<br/>
		<table cellpadding="0" cellspacing="0" border="0" width="95%">
		<tr>
			<td align="right">
				<input type="button" value="뒤로가지" />
				<input type="button" value="글쓰기" />
			</td>
		</tr>
		</table>
		
	</td>
</tr>
</table>
</body>
</html>