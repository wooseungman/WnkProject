<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > 게시판 리스트 > 게시글 상세 </td>
</tr>
<tr>
	<td align="center" style="padding-top:10px;padding-bottom:10px;">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_title">GetMessage</td>
			<td class="board_content al pl10">
				${getMessage}
			</td>
		</tr>
		<tr height="30">
			<td style="width:120px;" class="board_title">JSTL</td>
			<td class="board_content al pl5">
				<spring:message code='hello' var="testMessage1"/>
				${testMessage1}
			</td>
		</tr>
		<tr height="30">
			<td style="width:120px;" class="board_title">Tag</td>
			<td class="board_content al pl5">
				<app:message messageCode="hello" />
			</td>
		</tr>
		</table><br/>
	</td>
</tr>
</table>