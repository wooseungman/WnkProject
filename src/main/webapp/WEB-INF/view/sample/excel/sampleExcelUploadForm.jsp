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
			common.submit("registFrm", callbackFn);
		}
	});
	
	var callbackFn = function(data){
		var html = "";
		console.log(data);
		for(var i=0;i<data.length;i++){
			if(i>0){
				html += "<tr>";	
				html += "<td class='board_content al pl5'>"+data[i].cell_0+"</td>";
				html += "<td class='board_content al pl5'>"+data[i].cell_1+"</td>";
				html += "<td class='board_content al pl5'>"+data[i].cell_2+"</td>";
				html += "<td class='board_content al pl5'>"+data[i].cell_3+"</td>";
				html += "<td class='board_content al pl5'>"+data[i].cell_4+"</td>";
				html += "<td class='board_content al pl5'>"+data[i].cell_5+"</td>";
				html += "<td class='board_content al pl5'>"+data[i].cell_6+"</td>";
				html += "</tr>";	
			}
		}
		
		$("#excel_table").append(html);
	}
});
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td align="right" height="30" style="padding-right:30px;">* sample > Excel Upload </td>
</tr>
<tr>
	<td align="center">
		<form id="registFrm" name="detailFrm" method="post" action="<c:url value="/sample/excel/sampleExcelUpload.mvc" />" enctype="multipart/form-data">
		<table style="border:1px solid #EEEEEE;width:95%;">
		<tr height="30">
			<td class="board_title">FILE</td>
			<td class="board_content al pl5" style="height:30px;">
				<input type="file" name="file" value="" />
				<input id="btn_save" name="btn_save" type="button" value="저장" class="btn"/>
			</td>
		</tr>
		</table>
		</form>
		<br/>
		<table id="excel_table" style="border:1px solid #EEEEEE;width:95%;">
		<tr height="30">
			<td class="board_title">CELL1</td>
			<td class="board_title">CELL2</td>
			<td class="board_title">CELL3</td>
			<td class="board_title">CELL4</td>
			<td class="board_title">CELL5</td>
			<td class="board_title">CELL6</td>
			<td class="board_title">CELL7</td>
		</tr>
		</table><br/>
	</td>
</tr>
</table><br/>