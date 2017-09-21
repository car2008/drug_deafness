<%@ page import="com.capitalbiotech.drugdeafness.Result" %>
<%@ page import="com.capitalbiotech.drugdeafness.Record" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="renderer" content="webkit" />
		<meta name="author" content="CBT Bioinformatics, CapitalBio Technology" />
		<link rel="icon" type="image/png" href="/static/images/favicons/favicon-32x32.png" sizes="32x32" />
		<link rel="icon" type="image/png" href="/static/images/favicons/android-chrome-192x192.png" sizes="192x192" />
		<link rel="icon" type="image/png" href="/static/images/favicons/favicon-16x16.png" sizes="16x16" />
		<link rel="shortcut icon" href="/static/images/favicons/favicon.ico" />
		
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
	                    <li><a href="#" style="color:#563d7c;">欢迎XXX用户</a></li>
	                    <li><a href="#" style="color:#563d7c;">[退出]</a></li>
	                </ul>
	            </div>
	        </div>
	    </nav>
	
	    <div style="background-color: #684D90;padding:30px 0;margin-bottom:20px;">
	        <div class="container-custom" style="color:#fff;">
	            <h1>药物性耳聋数据库-结果录入</h1>
	        </div>
	    </div>
	
	    <div class="container-custom">
	        <div class="row">
	            <div class="col-md-2">
	                <ul id="nav-page" class="nav">
                        <li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
                        <li><a class="current" href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'showpdf')}">导出pdf报告</a></li>
                    </ul>
	            </div>
	            <div class="col-md-10">
	                <div class="clearfix">
	                    <ul class="nav nav-tabs">
	                    	<li role="presentation" class="active"><a>上传记录</a></li>
	                        <li role="presentation" ><a>批量上传</a></li>
	                        <li role="presentation"><a>单个录入</a></li>
	                    </ul>
	                    <div class="specialForm" >
	                    	<table class="table" id="" >
	                			<g:if test="${flash.message}">
									<div class="alert alert-info">
										${flash.message}
									</div>
								</g:if>
								<g:if test="${flash.error}">
									<div class="alert alert-error">
										${flash.error}
									</div>
								</g:if>
	                			<thead>
									<tr>
										<th>
											<g:checkBox name="selectedAll" id="selectedAll" />
										</th>
										<th>上传人</th>
										<th>上传文件名称</th>
										<th>上传成功(条)</th>
										<th>上传失败(条)</th>
										<th>上传日期</th>
				                	</tr>
			                	</thead>	
		                		<tbody>
	                				<g:form name="recordForm"  method="post" enctype="multipart/form-data" action="" style="margin-bottom:0;">
										<g:each in="${recordInstanceList}" var="recordInstance">
											<tr>
												<th>
													<g:checkBox name="" />
												</th>
									    		<td>${recordInstance?.uploadUser.name}</td>
									    		<td>${recordInstance?.recordName}</td>
									    		<td>${recordInstance?.successNum}</td>
									    		<td>${recordInstance?.failedNum}</td>
									    		<td><g:formatDate format="yyyy-MM-dd" date="${recordInstance?.startTime}" /></td>
											</tr>
										</g:each>
									</g:form>
		                		</tbody>
		                	</table>
	                   	</div>
	                    <div class="specialForm" style="display:none;">
		                    <g:form id="form-multiple" class="form-horizontal optForm" method="post" enctype="multipart/form-data" url="[action:'uploadBatch',controller:'result']" >
		                        <div class="form-group">
		                            <label class="col-md-2 control-label" for="InputFile">文件上传</label>
		                            <div class="col-md-4">
		                            	<input type="file" id="InputFile" class="input-sm" multiple="multiple" >
		                            </div>
		                        </div>
		                    </g:form>
		                    <div id="fileList" class="file-info" style="width: 50%;display:none;">
		                    	<table id="fileListTable" class="table table-bordered table-condensed">
		                    		<thead><tr><td colspan="2"><label>已选择文件</label></td></tr></thead>
		                    		<tbody></tbody>
		                    	</table>
		                    </div>
		                    <div style="margin-top:30px;">
		                        <button id="clearBtn_multiple" class="btn btn-default">清空</button>
		                        <button id="submitBtn_multiple" class="btn btn-success" style="float: right;" >提交</button>
		                    </div>
		                 </div>
	                     <div class="specialForm" style="display:none;">
	                        <g:form id="form-single" class="form-horizontal optForm" url="[action:'uploadOne',controller:'result']" method="post" enctype="multipart/form-data">
	                            <div class="form-group">
	                                <label class="col-md-2 col-md-offset-1 control-label">位置</label>
	                                <div class="col-md-3">
	                                    <input id="location" name="location" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-md-2 control-label">样品编号</label>
	                                <div class="col-md-3">
	                                    <input id="sampleNum" name="sampleNum" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-md-2 col-md-offset-1 control-label">样品类型</label>
	                                <div class="col-md-3">
	                                    <input id="sampleBelong" name="sampleBelong" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-md-2 control-label">FAM Ct</label>
	                                <div class="col-md-3">
	                                    <input id="famCt" name="famCt" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-md-2 col-md-offset-1 control-label">VIC Ct</label>
	                                <div class="col-md-3">
	                                    <input id="vicCt" name="vicCt" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-md-2  control-label">NED Ct</label>
	                                <div class="col-md-3">
	                                    <input id="nedCt" name="nedCt" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-md-2 col-md-offset-1 control-label">检测结果</label>
	                                <div class="col-md-3">
	                                    <input id="detectedResult" name="detectedResult" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-md-2  control-label">备注</label>
	                                <div class="col-md-3">
	                                    <input id="comment" name="comment" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                        </g:form>
	                        <div style="margin-top:30px;">
		                        <button class="btn btn-default">清空</button>
		                        <button id="submitBtn_single" class="btn btn-success" style="float: right;">提交</button>
	                        </div>
	                    </div>
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
	        
		    $("#clearBtn_multiple").on("click",function(){
		    	$("#fileListTable tbody").empty();
            	$("#fileList").hide();
		    	$("#InputFile").replaceWith('<input type="file" id="InputFile" class="input-sm" multiple="multiple" >');
    		    $('#InputFile').on('change', function (e) {
    		    	checkfile(this.id);
    		    	selectFile(e);
    		    });
			})	
		    
		    $("#submitBtn_multiple").on("click",function(){
				$("#form-multiple").submit();
		    });
		    
		    $("#submitBtn_single").on("click",function(){
				$("#form-single").submit();
		    });
		    
		    $("#form-multiple").on("change","#InputFile",function(e){
		    	checkfile(this.id);
		    	selectFile(e);
		    });

				    
		    function selectFile(e){
		    	$("#fileListTable tbody").empty();
		    	$("#fileList").hide();
		    	var files = e.target.files;  //FileList Objects
		        for (var i = 0, f; f = files[i]; i++) {
		        	var ldot = f.name.lastIndexOf(".");
		            var type = f.name.substring(ldot + 1);
		            if ("csv" == type || "xls" == type || "xlsx" == type || "txt" == type || "rar" == type || "gz" == type || "zip" == type) {
		            	$("#fileListTable tbody").append('<tr><td><label>文件名</label></td><td><label>' + f.name + '</label></td></tr>');
		            	$("#fileList").show();
		            } else {
		            	$("#fileListTable tbody").empty();
		            	$("#fileList").hide();
		                alert("请选择正确的格式上传：csv excel或者压缩文件");
		                //为避免type=file控件的change()只能执行一次，更换控件，重新绑定事件
		                $("#InputFile").replaceWith('<input type="file" id="InputFile" class="input-sm" multiple="multiple" >');
		                $("#form-multiple").on("change","#InputFile",function(e){
		    		    	checkfile(this.id);
		    		    	selectFile(e);
		    		    });
		    		    return;
		            }	
		        }
		    }
		    var maxsize = 2*1024*1024;//2M
		    var errMsg = "上传的附件文件不能超过2M！！！";
		    var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器。";
		    var  browserCfg = {};
		    var ua = window.navigator.userAgent;
		    if (ua.indexOf("MSIE")>=1){
		        browserCfg.ie = true;
		    }else if(ua.indexOf("Firefox")>=1){
		        browserCfg.firefox = true;
		    }else if(ua.indexOf("Chrome")>=1){
		        browserCfg.chrome = true;
		    }
		    function checkfile(id){
		        try{
		            var obj_file = document.getElementById(id);
		            if(obj_file.value==""){
		                alert("请先选择上传文件");
		                return;
		            }
		            var filesize = 0;
		            if(browserCfg.firefox || browserCfg.chrome ){
		                filesize = obj_file.files[0].size;
		            }else if(browserCfg.ie){
		                var obj_img = document.createElement("img");
		                obj_img.dynsrc=obj_file.value;
		                filesize = obj_img.fileSize;
		            }else{
		                alert(tipMsg);
		                return;
		            }
		            if(filesize==-1){
		                alert(tipMsg);
		                return;
		            }else if(filesize>maxsize){
		                alert(errMsg);
		                return;
		            }else{
		                return;
		            }
		        }catch(e){
		            alert(e);
		        }
		    }
	    </script>
	</body>
</html>
