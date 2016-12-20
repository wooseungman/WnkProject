<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
$(document).ready(function(){
	$("#btn_logout").on("click",function(){
		location.href='<c:url value="/sample/login/logout" />';
	});
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > 로그인 정보 </td>
</tr>
<tr>
	<td align="center" style="padding-top:10px;padding-bottom:10px;">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr height="30">
			<td style="width:200px;" class="board_title">ID</td>
			<td class="board_content al pl10">
				${memberInfo.id}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">NAME</td>
			<td class="board_content al pl10">
				${memberInfo.name}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">MEMBER_TYPE_CODE</td>
			<td class="board_content al pl10">
				${memberInfo.member_type_code}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">FIRST_CLASS_CODE</td>
			<td class="board_content al pl10">
				${memberInfo.first_class_code}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">BIRTH_DAY</td>
			<td class="board_content al pl10">
				${memberInfo.birth_day}
			</td>
		</tr>
		</table>
		<br/>
		<input type="button" id="btn_logout" name="btn_logout" value="로그아웃">
	</td>
</tr>
</table>