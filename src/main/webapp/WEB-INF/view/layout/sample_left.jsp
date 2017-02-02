<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<table cellpadding="0" cellspacing="0" border="0" width="100%" style="maring-top:100px;">
<tr>
	<td align="center">
		<table width="95%" style="width:95%;border:1px solid #BBBBBB;">
		<tr height="30">
			<td class="board_title"><b>메뉴</b></td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/sampleBoard/boardList.mvc" />'>1. board sample</a>
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/message/sampleMessage.mvc" />'>2. message getting</a>
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/js/sampleJsValidation.mvc" />'>3. js validation</a>
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/excel/sampleExcelUploadForm.mvc" />'>4. Excel 업로드</a>
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/js/sampleAjaxForm.mvc" />'>5. Ajax 관련 기능</a>
			</td>
		</tr>
		<c:if test="${empty sessionScope.memberInfo}">
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/login/login.mvc" />'>6. 로그인</a>
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/login/loginDetail.mvc" />'>6. 사용자 정보</a>
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/js/sampleScriptForm.mvc" />'>7. 그 밖에 스크립트</a>
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5">
				<a href='<c:url value="/sample/exception/exceptionTest.mvc" />'>8. Exception 처리</a>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>