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
	<td align="right" height="30" style="padding-right:30px;">* sample > 게시판 리스트 > 게시글 작성 </td>
</tr>
<tr>
	<td align="center">
		<form name="frm" action="<c:url value="/sample/upload/sampleFileUploadSave.mvc" />" method="post" enctype="multipart/form-data"> 
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_title">ID</td>
			<td class="board_content" style="height:30px;">
				<input type="text" id="ID" name="ID" value="${detail.ID}" class="inputbox" ${not empty detail ? 'readonly':''} />
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">FILE</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="file" name="file" value="" />
				<input id="btn_save" name="btn_save" type="button" value="저장" class="btn"/>
			</td>
		</tr>
		</table>
		</form>
		<br/>
	</td>
</tr>
</table><br/>