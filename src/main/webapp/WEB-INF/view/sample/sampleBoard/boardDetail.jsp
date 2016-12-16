<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_list").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardList.mvc" />';
		$("#detailFrm").attr('action',url);
		$("#detailFrm").submit();
	});
	
	$("#btn_modify").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardRegist.mvc" />';
		$("#detailFrm").attr('action',url);
		$("#detailFrm").submit();
	});
	
	$("#btn_remove").on("click",function(){
		if(confirm("삭제하시겠습니까?")){
			var url = '<c:url value="/sample/sampleBoard/boardDelete.mvc" />';
			$("#detailFrm").attr('action',url);
			common.submit("detailFrm");
		}
	});
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > 게시판 리스트 > 게시글 상세 </td>
</tr>
<tr>
	<td align="center">
		<form id="detailFrm" name="detailFrm" method="get">
		<input type="hidden" name="page" value="${param.page}" />
		<input type="hidden" name="SEARCH_GUBUN" value="${param.SEARCH_GUBUN}" />
		<input type="hidden" name="SEARCH_VALUE" value="${param.SEARCH_VALUE}" />
		<input type="hidden" name="SEQ" value="${list.SEQ}" />
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr height="30">
			<td style="width:120px;" class="board_title">SEQ</td>
			<td class="board_content al pl5">
				${list.SEQ}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">ID</td>
			<td class="board_content al pl5">
				${list.ID}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">NAME</td>
			<td class="board_content al pl5">
				${list.NAME}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">DESCRIPTION</td>
			<td class="board_content al pl5">
				${list.DESCRIPTION}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">USE_YN</td>
			<td class="board_content al pl5">
				${list.USE_YN}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">REG_USER</td>
			<td class="board_content al pl5">
				${list.REG_USER}
			</td>
		</tr>
		<tr height="30">
			<td class="board_title">UPLOAD FILE</td>
			<td class="board_content al pl5">
				<a href="<c:url value="/common/fileDownload.mvc?ATTAFILE_SEQ=${list.FILE_SEQ}" />">${list.ORGNFILE_NM}</a>
			</td>
		</tr>
		</table>
		</form>
		<br/>
		<input id="btn_list" name="btn_list" type="button" value="목록" class="btn"/>
		<input id="btn_modify" name="btn_modify" type="button" value="수정" class="btn"/>
		<input id="btn_remove" name="btn_remove" type="button" value="삭제" class="btn"/>
	</td>
</tr>
</table><br/>