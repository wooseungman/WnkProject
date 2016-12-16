<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_save").on("click",function(){
		var url = '<c:url value="/sample/js/sampleAjax.mvc" />';
		common.ajaxCallbackSubmit(url, $("#frm1").serializeArray());
	});
	
	$("#btn_save2").on("click",function(){
		var url = '<c:url value="/sample/js/sampleAjax.mvc" />';
		common.ajaxCallbackSubmit(url, $("#frm2").serializeArray(), testCallbackFn);
	});
	
	var testCallbackFn = function(data){
		if(typeof data != 'object'){
			data = JSON.parse(json);
		}
		
		if(StringUtil.isEmpty(data[common.MSG_FOR_ALERT]) == false){
			alert(data[common.MSG_FOR_ALERT]);
		}
	}
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > 게시판 리스트 > 게시글 작성 </td>
</tr>
<tr>
	<td align="center">
		<form id="frm1" name="frm1" method="post">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_content al pl5">
				Ajax 통신1 callback이 없는 경우 
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="param1" name="param1" value="" class="inputbox" />
				<input id="btn_save" name="btn_save" type="button" value="저장" class="btn"/>
			</td>
		</tr>
		</table>
		</form>
		<br/>
		<form id="frm2" name="frm2" method="post">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_content al pl5">
				Ajax 통신2 callback이 있는 경우 
			</td>
		</tr>
		<tr>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="param1" name="param1" value="" class="inputbox" />
				<input id="btn_save2" name="btn_save2" type="button" value="저장" class="btn"/>
			</td>
		</tr>
		</table>
		</form>
		<br/>
	</td>
</tr>
</table><br/>