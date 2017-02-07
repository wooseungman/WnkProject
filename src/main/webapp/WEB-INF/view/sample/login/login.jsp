<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko-kr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>WnkFrameWork Login Page</title>
    <link href="<c:url value="/resources/sample/css/reset.css"/>" rel="stylesheet" type="text/css">
	<script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script>
	<script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/common.js"/>"></script>
	<script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/validation.js"/>"></script>
	<script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/StringUtil.js"/>"></script>
	<script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/jquery.form.js"/>"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_save").on("click",function(){
		//$("#registFrm").submit();
		
		common.submit("registFrm",callbackLoginPopup);
	});
	
	var callbackLoginPopup = function(xhr, status, data){
		alert(xhr);
		alert(status);
		alert(data);
	}
});
</script>

<body style="background-color:#FFFFFF;">
	<table style="width:100%;background-color:#FFFFFF;">
	<tr>
		<td align="center">
			<table style="width:1000px;border:1px solid #EEEEEE;">
			<tr>
				<td colspan="2" style="vertical-align:top;border:1px solid #EEEEEE;height:100px;">
				</td>
			</tr>
			<tr>
				<td style="vertical-align:top;padding-top:35px;width:20%;">
					
				</td>
				<td style="vertical-align:top;width:80%;"><br/><br/>
					<form id="registFrm" name="loginFrm" method="post" action="/sample/login/login_ok">
					<table>
					<tr height="30">
						<td class="board_title" style="width:120px">ID</td>
						<td class="board_content al pl5" style="width:300px;height:30px;">
							<input type="text" placeholder="user_id" name="user_id" class="inputbox" style="height:25px;" data-fixed="true" title="아이디" />
						</td>
						<td rowspan="2" style="padding-left:10px;">
							<input id="btn_save" name="btn_save" type="button" value="Login" class="btn" style="width:100px;height:100px;" data-fixed="true" title="비밀번호" />
						</td>
					</tr>
					<tr height="30">
						<td class="board_title">PASSWORD</td>
						<td class="board_content al pl5" style="height:30px;">
							<input placeholder="password" id="user_passwd" name="user_passwd" type="password" class="inputbox" style="height:25px;"/>
						</td>
					</tr>
					</table>
					</form>
					<br/>
					<br/><br/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="height:100px;border:1px solid #EEEEEE;">
					
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</body>
</html>