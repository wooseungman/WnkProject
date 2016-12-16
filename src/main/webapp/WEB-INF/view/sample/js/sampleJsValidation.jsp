<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_save").on("click",function(){
		common.submit("registFrm");
	});
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > js validation </td>
</tr>
<tr>
	<td align="center">
		<form id="registFrm" name="detailFrm" method="post">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_title">* ID</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="ID" name="ID" value="" class="inputbox" data-fixed="true" title="ID" />
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">NAME</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="NAME" name="NAME" value="" class="inputbox" />
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">* DESCRIPTION</td>
			<td class="board_content al pl5" style="height:30px;padding-top:5px;padding-bottom:5px;">
				<textarea id="DESCRIPTION" name="DESCRIPTION" class="inputbox" style="width:400px;height:200px;" data-fixed="true" title="DESCRIPTION" ></textarea>
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">* USE_YN</td>
			<td class="board_content al pl5">
				<select id="USE_YN" name="USE_YN" class="selectbox" data-fixed="true" title="USE_YN">
					<option value="">선택</option>
					<option value="Y">사용</option>
					<option value="N">미사용</option>
				</select>
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">* CHECK_BOX</td>
			<td class="board_content al pl5">
				<input type="checkbox" id="check_1" name="checkbox" value="Y" data-fixed="true" title="checkbox"> Y 
				<input type="checkbox" id="check_2" name="checkbox" value="N" data-fixed="true" title="checkbox"> N
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">* RADIO BUTTON</td>
			<td class="board_content al pl5">
				<input type="radio" id="radio_1" name="radio" value="Y" data-fixed="true" title="radio"> Y 
				<input type="radio" id="radio_2" name="radio" value="N" data-fixed="true" title="radio"> N
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">REG_USER</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="REG_USER" name="REG_USER" value="" class="inputbox" />
			</td>
		</tr>
		</table>
		</form>
		<br/>
		<input id="btn_save" name="btn_save" type="button" value="저장" class="btn"/>
	</td>
</tr>
</table><br/>