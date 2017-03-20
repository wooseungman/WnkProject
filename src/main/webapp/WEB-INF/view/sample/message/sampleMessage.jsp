<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > Message > 다국어 메세지 가져오기 </td>
</tr>
<tr>
	<td align="center"><br/>
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_title">Controller</td>
			<td class="board_content al pl5">
				${getMessage}
			</td>
		</tr>
		<tr>
			<td style="width:120px;" class="board_title">JSTL</td>
			<td class="board_content al pl5">
				<spring:message code='hello'/> 
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">Custom Tag</td>
			<td class="board_content al pl5">
				<app:message messageCode="hello1" />
			</td>
		</tr>
		</table><br/>
		
		<a href="<c:url value="/sample/message/sampleMessage.mvc?lang=ko" />"><input type="button" name="btn_ko" value="한국어"></a>
		<a href="<c:url value="/sample/message/sampleMessage.mvc?lang=en" />"><input type="button" name="btn_en" value="영 어"></a>
		<a href="<c:url value="/sample/message/sampleMessage.mvc?lang=ja" />"><input type="button" name="btn_ja" value="일본어"></a>
		<br/>
	</td>
</tr>
</table><br/>