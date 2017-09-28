<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="renderer" content="webkit" />
		<meta name="author" content="CBT Bioinformatics, CapitalBio Technology" />
		
		<link rel="stylesheet" href="${resource(dir:'css/bootstrap/dist/css/', file:'bootstrap.css')}"/>
	    <link rel="stylesheet" href="${resource(dir:'css/font-awesome/css/', file:'font-awesome.min.css')}"/>
	    	    <link rel="stylesheet" href="${resource(dir:'css/', file:'sweetalert.css')}">  
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'index.css')}"/>
	    <script src="${resource(dir:'js/', file:'jquery.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap.min.js')}"></script>
	    <script src="${resource(dir:'js/', file:'jquery.form.js')}"></script>
	    <script src="${resource(dir:'js/', file:'sweetalert.min.js')}"></script>
		<title>
			<g:message code="drug_deafness.name" />
		</title>
	</head>
	
	<body>
	 	<nav class="navbar navbar-default nav-custom">
	        <div class="container-custom">
	            <div class="navbar-header">
	                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
	                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	                    <span class="sr-only"></span>
	                    <span class="icon-bar"></span>
	                    <span class="icon-bar"></span>
	                    <span class="icon-bar"></span>
	                </button>
	                <a class="navbar-brand" href="#" style="color:#563d7c;">药物性耳聋数据库</a>
	            </div>
	            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	                <ul class="nav navbar-nav navbar-right">
	                    <li><a href="#" style="color:#563d7c;">欢迎XXX用户</a></li>
	                    <li>
	                         <form method="post" action="${createLink(controller: 'logout', action: 'index')}">
		                           <button type="submit" class="btn btn-default" style="border:none;width:100%;text-align:left;padding:15px;color:#F96A74;">
		                           		<g:message code="drug_deafness.user.logout.label" />
		                           </button>
							</form>
                        </li>
	                </ul>
	            </div>
	        </div>
	    </nav>
	
	    <div style="background-color: #684D90;padding:30px 0;margin-bottom:20px;">
	        <div class="container-custom" style="color:#fff;">
	            <h1>药物性耳聋数据库-pdf</h1>
	        </div>
	    </div>
	
	    <div class="container-custom">
	        <div class="row">
	            <div class="col-md-2">
	                <ul id="nav-page" class="nav">
	                	<li><a href="${createLink(controller: 'information', action: 'list')}">信息列表</a></li>
                        <li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
                        <li><a class="current" href="${createLink(controller: 'result', action: 'list')}">导出pdf报告</a></li>
                    </ul>
	            </div>
	            <div class="col-md-10">
	                <div class="form-content clearfix" >
	                	<div class="panel panel-default"> 
					       <div class="panel-body"> 
					            <form id="formSearch" class="form-horizontal" >
					               <div class="form-group" style="margin-bottom:0px;">
						                <label class="control-label col-sm-1">编号</label> 
						                <div class="col-sm-2"> 
						                    <input type="text" class="form-control input-sm" id="search_name"> 
						                </div> 
						                <div class="col-sm-1" style="text-align:left;"> 
						                    <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary btn-sm">查询</button> 
						                </div> 
						            </div> 
					            </form>
					       </div> 
					   	</div>
	                	<table class="table" id="table-pdf" >
                			<thead>
								<tr>
									<th>
										<input type="checkbox" name="selectedAll" id="selectedAll">
									</th>
									<th>样本编号</th>
									<th>姓名</th>
									<th>性别</th>
									<th>年龄</th>
									<th>FAM Ct</th>
									<th>VIC Ct</th>
									<th>NED Ct</th>
									<th>检测结果</th>
									<th>备注</th>
			                	</tr>
		                	</thead>	
	                		<tbody>
                				<g:form name="generatePdfForm" id="generatePdfForm"  method="post" enctype="multipart/form-data" url="[action:'generatePdf',controller:'result']" style="margin-bottom:0;">
									<g:each in="${resultInstanceList}" var="resultInstance">
										<tr>
											<td>
												<input type="checkbox" name="singleRow" id="singleRow" value="${resultInstance.id}">
												<%-- <input type="hidden" name="id" >--%>
											</td>
								    		<td>${resultInstance.information?.sampleNum}</td>
								    		<td>${resultInstance.information?.patientName}</td>
								    		<td>${resultInstance.information?.gender}</td>
								    		<td>${resultInstance.information?.age}</td>
								    		<td>${resultInstance.famCt}</td>
								    		<td>${resultInstance.vicCt}</td>
								    		<td>${resultInstance.nedCt}</td>
								    		<td>${resultInstance.detectedResult}</td>
								    		<td>${resultInstance.information?.remark}</td>
										</tr>
									</g:each>
								
								</g:form>
	                		</tbody>
	                	</table>
	                	<%--<div>
	            			<cbt_health:paginate total="${allRecordInstanceTotal}" params="${params}" />
	            		</div> --%>
	                    <div style="margin-top:20px;">
	                        <a href="#" id="createReport" class="btn btn-success" role="button" style="float: right;">生成报告</a>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    
    	<div id="optModal" class="modal fade" aria-labelledby="optModalLabel" data-backdrop="static">
	        <div class="modal-dialog" style="width:60%;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    	请输入字段信息
	                </div>
	                <div class="modal-body">
		                <form id="appendForm"  class="form-horizontal">
							<div class="form-group">
							    <label class="col-md-2 control-label">医院名称</label>
							    <div class="col-md-4">
							    	<input name="hospital" id="hospital" class="form-control input-sm"></input>
							    </div>
							    <label class="col-md-2 control-label">检验员</label>
							    <div class="col-md-4">
							    	<input name=checker id="checker" class="form-control input-sm"></input>
							    </div>
							</div>
							<div class="form-group">
							    <label class="col-md-2 control-label">审核员</label>
							    <div class="col-md-4">
							    	<input name="assessor" id="assessor" class="form-control input-sm"></input>
							    </div>
							    <label class="col-md-2 control-label">备注</label>
							    <div class=" col-md-4">
							    	<input name="pdfcomment" id="pdfcomment" class="form-control input-sm"></input>
							    </div>
							</div>
						</form>
	                </div>
	                <div class="modal-footer">
	                	<button id="btn_submit" type="button" class="btn btn-success">
                       		提交
	                    </button>
	                 	<button id="btn_cancel" type="button" class="btn btn-danger" data-dismiss="modal">
                        	取消
	                    </button>
	                </div>
	            </div>
	        </div>
	    </div>
	    <script>
		    $("#createReport").on("click",function(){
		    	var selectedRows = $(".table>tbody td").find("input[type='checkbox']:checked");
				if(selectedRows.length === 0){
					showMessage();
					return;
				}
				$("#optModal").modal('show');
		    })
	    
			
			function showMessage(){
				swal({
                    title: "请先选择需要导出的数据",
                    type: "warning",
                });
			}

			$("#selectedAll").on("click",function(){
				$("#table-pdf input[type='checkbox']").prop("checked", $(this).prop("checked"));
			});

			$("#table-pdf input").click(function(){
				$("#selectedAll").prop("checked",$("#table-pdf input").length === $("#table-pdf input:checked").length);
			});

			$("#btn_submit").on("click",function(){
			
				$("#appendForm input").each(function(index,value){
					var _input = $(this).clone();
					_input.val($(this).val());
					_input.css("display","none");
					$("#generatePdfForm").append(_input);
				})
				$("#generatePdfForm").submit();
			})
			
			
	    </script>
	</body>
</html>
