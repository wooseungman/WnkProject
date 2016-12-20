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
		<form id="registFrm" name="detailFrm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="page" value="${param.page}" />
		<input type="hidden" name="SEARCH_GUBUN" value="${param.SEARCH_GUBUN}" />
		<input type="hidden" name="SEARCH_VALUE" value="${param.SEARCH_VALUE}" />
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr>
			<td style="width:120px;" class="board_title">ID</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="ID" name="ID" value="${list.ID}" class="inputbox" ${not empty list ? 'readonly':''} />
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">NAME</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="NAME" name="NAME" value="${list.NAME}" class="inputbox" />
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">DESCRIPTION</td>
			<td class="board_content al pl5" style="height:30px;padding-top:5px;padding-bottom:5px;">
				<textarea id="DESCRIPTION" name="DESCRIPTION" class="inputbox" style="width:400px;height:200px;">${list.DESCRIPTION}</textarea>
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">USE_YN</td>
			<td class="board_content al pl5">
				<select id="USE_YN" name="USE_YN" class="selectbox">
					<option value="Y" ${list.USE_YN eq 'Y' ? 'selected':''}>사용</option>
					<option value="N" ${list.USE_YN eq 'N' ? 'selected':''}>미사용</option>
				</select>
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">REG_USER</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="text" id="REG_USER" name="REG_USER" value="${list.REG_USER}" class="inputbox" />
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">FILE</td>
			<td class="board_content al pl5" style="height:30px;">
				<c:if test="${not empty list.ORGNFILE_NM}">
					${list.ORGNFILE_NM}
				</c:if>
				<c:if test="${empty list.ORGNFILE_NM}">
					<input type="file" name="file" value="" />
				</c:if>
			</td>
		</tr>
		</table>
		</form>
		<br/>
		<input id="btn_save" name="btn_save" type="button" value="저장" class="btn"/>
		<input id="btn_list" name="btn_list" type="button" value="목록" class="btn"/>
	</td>
</tr>
</table><br/>