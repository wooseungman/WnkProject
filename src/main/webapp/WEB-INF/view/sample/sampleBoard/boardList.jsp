<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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