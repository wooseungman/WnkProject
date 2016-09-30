<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<!DOCTYPE html>
<html>
<head>
	<title>WnkProject</title>
</head>
<body>
	<div>
		<div id="header">
			<page:apply-decorator name="top"/>
		</div>
		<div>
			<decorator:body/>
		</div>
	</div>
</body>
</html>