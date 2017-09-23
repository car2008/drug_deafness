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
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'index.css')}"/>
	    <script src="${resource(dir:'js/', file:'jquery.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap.min.js')}"></script>
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'sweetalert.css')}">   
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
	                    <li><a href="#" style="color:#563d7c;">欢迎${loggedInUser?.name}用户</a></li>
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
	            <h1>药物性耳聋数据库-信息列表</h1>
	        </div>
	    </div>
	
	    <div class="container-custom" >
	        <div class="row">
	            <div class="col-md-2">
                    <ul id="nav-page" class="nav">
                    	<li><a class="current" href="#">信息列表</a></li>
                        <li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'list')}">导出pdf报告</a></li>
                    </ul>
	            </div>
	            <div class="col-md-10">
	                <div class="clearfix">
	                    <ul class="nav nav-tabs">
	                        <li role="presentation"  class="active"><a>表格</a></li>
	                    </ul>
	                </div>

	            	<div class="specialForm">
                    	<table class="table" id="" >
                			<thead>
								<tr>
									<th>
										<input type="checkbox" name="selectedAll" id="selectedAll">
									</th>
									<th>样本编号</th>
									<th>姓名</th>
									<th>性别</th>
									<th>年龄</th>
									<th>医院/单位</th>
									<th>门诊号/住院号</th>
									<th>病房/床位</th>
									<th>送检科室</th>
									<th>送检医生</th>
									<th>送检样本</th>
									<th>送检时间</th>
									<th>联系电话</th>
									<th>备注</th>
			                	</tr>
		                	</thead>	
	                		<tbody>
                				<g:form name=""  method="post" enctype="multipart/form-data" action="" style="margin-bottom:0;">
									<g:each in="${informationInstanceList}" var="informationInstance">
										<tr>
											<td>
												<input type="checkbox" name="singleRow" id="singleRow">
											</td>
								    		<td>${informationInstance?.sampleNum}</td>
								    		<td>${informationInstance?.patientName}</td>
								    		<td>${informationInstance?.gender}</td>
								    		<td>${informationInstance?.age}</td>
								    		<td>${informationInstance?.hospital}</td>
								    		<td>${informationInstance?.patientNum}</td>
								    		<td>${informationInstance?.wardBed}</td>
								    		<td>${informationInstance?.inspectionDepartment}</td>
								    		<td>${informationInstance?.inspectionDoctor}</td>
								    		<td>${informationInstance?.inspectionSample}</td>
								    		<td>${informationInstance?.inspectionTime}</td>
								    		<td>${informationInstance?.phoneNum}</td>
								    		<td>${informationInstance?.remark}</td>
										</tr>
									</g:each>
								</g:form>
	                		</tbody>
	                	</table>
	                	<ul class="pagination">
	                		<cbt_health:paginate total="${allInformationInstanceTotal}" params="${params}" />
	                	</ul>
					</div>
	            </div>
	        </div>
	    </div>
	    
	    <script>
		    $("ul.nav-tabs").on("click","li",function () {
	            var index = $(this).index();
	            $(this).siblings("li").removeClass("active");
	            $(this).addClass("active");
	            var target = $("div.specialForm:eq("+index+")");
	            target.show();
	            target.siblings("div.specialForm").hide();
	        })
	        
	    </script>
	</body>
</html>
