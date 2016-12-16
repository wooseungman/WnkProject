<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_list").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardList.mvc" />';
		$("#registFrm").attr('action',url);
		$("#registFrm").submit();
	});
	
	$("#btn_save").on("click",function(){
		if(confirm("저장하시겠습니까?")){
			var url = '<c:url value="/sample/sampleBoard/boardSave.mvc" />';
			$("#registFrm").attr('action',url);
			common.submit("registFrm");
		}
	});
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > 기타 Script </td>
</tr>
<tr>
	<td align="center">
		<form id="registFrm" name="detailFrm" method="post">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr height="30">
			<td class="board_title al pl5" style="height:30px;">
				숫자만 입력가능
			</td>
		</tr>
		<tr height="30">
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="ID" name="ID" value="" class="inputbox" onKeyUp="onlyNum(this);" />
			</td>
		</tr>
		</table><br/>
		</form>
		<br/>
	</td>
</tr>
</table><br/>