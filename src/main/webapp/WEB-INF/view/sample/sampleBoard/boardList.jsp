<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Board List</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					총 게시물 : ${LIST_TOT_COUNT} / 페이지 : ${page}
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div style="padding-bottom:10px;vertical-align:top">
						<form id="frm" name="searchForm" method="get" action="">
							<select id="SEARCH_GUBUN" name="SEARCH_GUBUN" style="width:120px;height:30px;">
								<option value="">전체</option>
								<option value="ID" <c:if test="${param.SEARCH_GUBUN eq 'ID'}">selected</c:if>>ID</option>
							</select>
							<input type="text" name="SEARCH_VALUE" placeholder="Enter text" value="${param.SEARCH_VALUE}" style="width:120px;height:30px;">
							<button type="submit" value="Search" class="btn btn-default">Search</button>
						</form>
					</div>
					<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
					<thead>
					<tr>
						<th>ID</th>
						<th>NAME</th>
						<th>DESCRIPTION</th>
						<th>USE_YN</th>
						<th>REG_USER</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="list" varStatus="row">
							<tr class="gradeA">
								<td>${list.ID}</td>
								<td>${list.NAME}</td>
								<td>${list.DESCRIPTION}</td>
								<td class="center">${list.USE_YN}</td>
								<td class="center">${list.REG_USER}</td>
							</tr>
						</c:forEach>
					</tbody>
					</table>
					<!-- /.table-responsive -->
					<app:paging />
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
</div>
<!-- /#page-wrapper -->