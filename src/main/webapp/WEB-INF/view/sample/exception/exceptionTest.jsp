<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
<!--
function occurException(){
	common.submit("exceptionFrm");
}
//-->
</script>
<form id="exceptionFrm" name="exceptionFrm" method="post" action="<c:url value="/sample/exception/exceptionError.mvc" />">
</form>
<table style="width:95%">
<tr>
	<td align="center"><br/>
		<table style="width:90%">
		<tr>
			<td><a href="<c:url value="/sample/exception/exceptionError.mvc" />">1. Exception</a></td>
		</tr>
		<tr>
			<td><a href="#none" onClick="javascript:occurException(); return false;">2. AjaxException</a></td>
		</tr>
		</table>
	</td>
</tr>
</table>
