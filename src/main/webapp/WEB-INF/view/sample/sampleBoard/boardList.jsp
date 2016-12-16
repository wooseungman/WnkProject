<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(document).ready(function(){
	$("#btn_search").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardList.mvc" />';
		$("#searchFrm").attr("action",url);
		$("#searchFrm").submit();
	})
	
	$(".btn_detail").on("click",function(){
		var seq = $(this).attr("seq");
		var searchGubun = $("#SEARCH_GUBUN").val();
		var searchValue = $("#SEARCH_VALUE").val();
		var page = "${page}";
		var url = '<c:url value="/sample/sampleBoard/boardDetail.mvc?SEQ=" />'+seq+"&SEARCH_GUBUN="+searchGubun+"&SEARCH_VALUE="+searchValue+"&page="+page;
		common.goLocationUri(url);
	});
	
	$("#btn_register").on("click",function(){
		var searchGubun = $("#SEARCH_GUBUN").val();
		var searchValue = $("#SEARCH_VALUE").val();
		var page = "${page}";
		var url = '<c:url value="/sample/sampleBoard/boardRegist.mvc" />'+"?SEARCH_GUBUN="+searchGubun+"&SEARCH_VALUE="+searchValue+"&page="+page;
		common.goLocationUri(url);
	});
	
	$("#btn_excel").on("click",function(){
		var url = '<c:url value="/sample/sampleBoard/boardListExcelDown.mvc" />';
		$("#searchFrm").attr("action",url);
		$("#searchFrm").submit();
	});
	
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > 게시판 리스트 </td>
</tr>
<tr>
	<td align="center"><br/>
		<table style="width:95%;height:25px;">
		<tr>
			<td>total : ${LIST_TOT_COUNT}개</td>
			<td align="right">
				<table>
				<tr>
					<td>
						<form id="searchFrm" name="searchFrm" action="" method="get">
						<input type="hidden" name="page" value="${page}" />
						<select id="SEARCH_GUBUN" name="SEARCH_GUBUN" class="selectbox">
							<option value="">전체</option>
							<option value="ID" <c:if test="${param.SEARCH_GUBUN eq 'ID'}">selected</c:if>>ID</option>
						</select>
						<input type="text" id="SEARCH_VALUE" name="SEARCH_VALUE" placeholder="Enter text" value="${param.SEARCH_VALUE}" class="inputbox">
						<input type="button" id="btn_search" name="btn_search" value="검 색" style="width:100px;height:25px;"/>
						<input type="button" id="btn_excel" name="btn_search" value="엑셀다운" style="width:100px;height:25px;"/>
						</form>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table><br/>
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr height="30">
			<td class="board_title">SEQ</td>
			<td class="board_title">ID</td>
			<td class="board_title">NAME</td>
			<td class="board_title">DESCRIPTION</td>
			<td class="board_title">USE_YN</td>
			<td class="board_title">REG_USER</td>
			<td class="board_title">REG_DATE</td>
		</tr>
		<c:forEach items="${list}" var="list" varStatus="row">
		<tr>
			<td class="board_content al pl5" style="width:50px;"><a class="btn_detail" href='#none' seq="${list.SEQ}">${list.SEQ}</a></td>
			<td class="board_content al pl5">${list.ID}</td>
			<td class="board_content al pl5">${list.NAME}</td>
			<td class="board_content al pl5">${list.DESCRIPTION}</td>
			<td class="board_content ac">${list.USE_YN}</td>
			<td class="board_content al pl5">${list.REG_USER}</td>
			<td class="board_content ac">${list.REG_DATE}</td>
		</tr>
		</c:forEach>
		</table><br/>
		<table style="width:95%;height:25px;">
		<tr>
			<td align="right">
				<input id="btn_register" name="btn_register" type="button" value="등록">
			</td>
		</tr>
		</table><br/>
		<div>
			<app:paging />
		</div>
		
	</td>
</tr>
</table><br/><br/>