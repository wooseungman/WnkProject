<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<!DOCTYPE html>
<html lang="ko-kr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>WnkFrameWork Sample Page</title>
</head>
<body style="background-color:#FFFFFF;">
	<table style="width:100%;background-color:#FFFFFF;">
	<tr>
		<td align="center">
			<table style="width:1000px;border:1px solid #EEEEEE;">
			<tr>
				<td colspan="2" style="vertical-align:top;border:1px solid #EEEEEE;height:100px;">
					<page:apply-decorator name="sample_top" encoding="utf-8"/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:top;padding-top:35px;width:20%;">
					<page:apply-decorator name="sample_left" encoding="utf-8"/>
				</td>
				<td style="vertical-align:top;width:80%;">
					<decorator:body/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="height:100px;border:1px solid #EEEEEE;">
					<page:apply-decorator name="sample_bottom" encoding="utf-8"/>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</body>
</html>