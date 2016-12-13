<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">File Upload Test</h1>
				<div class="panel panel-default">
	                <div class="panel-heading">
	                    File Upload Test
	                </div>
	                <!-- .panel-heading -->
	                <div class="panel-body">
	                    
	                    <form name="frm" action="<c:url value="/sample/sampleFileUploadSave.mvc" />" method="post" enctype="multipart/form-data"> 
	                    <table cellpadding="0" cellspacing="0" border="1" width="90%">
	                    <tr>
	                    	<td align="center">
	                    		파일 이름
	                    	</td>
	                    	<td>
	                    		<input type="file" name="file" value="" />
	                    	</td>
	                    	<td><input type="submit" name="submit" value="upload" /></td>
	                    </tr>
	                    </table>
	                    </form>
	                </div>
	                <!-- .panel-body -->
	            </div>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->