<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_list").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardList.mvc" />';
		$("#detailFrm").attr('action',url);
		$("#detailFrm").submit();
	});
	
	$("#btn_modify").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardRegist.mvc" />';
		$("#detailFrm").attr('action',url);
		$("#detailFrm").submit();
	});
	
	$("#btn_remove").on("click",function(){
		if(confirm("삭제하시겠습니까?")){
			var url = '<c:url value="/sample/sampleBoard/boardDelete.mvc" />';
			$("#detailFrm").attr('action',url);
			common.submit("detailFrm");
		}
	});
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
			<td class="board_content">
				${getMessage}
			</td>
		</tr>
		<tr>
			<td style="width:120px;" class="board_title">JSTL</td>
			<td class="board_content">
				<spring:message code='hello' var="testMessage1"/>
				${testMessage1}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">Custom Tag</td>
			<td class="board_content">
				<app:message messageCode="hello" />
			</td>
		</tr>
		</table>
		<br/>
	</td>
</tr>
</table><br/>