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
		
		<link rel="stylesheet" href="${resource(dir:'css/bootstrap/dist/css/', file:'bootstrap.css')}"/>
		<link rel="stylesheet" href="${resource(dir:'css/', file:'bootstrap-table.css')}"/>
	    <link rel="stylesheet" href="${resource(dir:'css/font-awesome/css/', file:'font-awesome.min.css')}"/>
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'sweetalert.css')}">  
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'index.css')}"/>
	    <script src="${resource(dir:'js/', file:'jquery.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap.min.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap-table.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap-table-zh-CN.js')}"></script>
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
	                    <sec:ifLoggedIn>
	                		<drug_deafness:setLoggedInUser var="loggedInUser" />
	                		<li class="dropdown">
	                			<a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color:#563d7c;">
	                				欢迎${loggedInUser?.name}用户
	                				<span class="caret"></span>
                				</a>
								<ul class="dropdown-menu">
									<li><g:link style="padding:10px 20px;" controller="user" action='edit'><g:message code="edit.my.information.label" /></g:link></li>
									<li><g:link style="padding:10px 20px;" controller="user" action='password'><g:message code="change.my.password.label" /></g:link></li>
									<li class="divider"></li>
									<li>
										<form method="post" action="${createLink(controller: 'logout', action: 'index')}">
				                           <button type="submit" class="btn btn-default" style="border:none;width:100%;text-align:left;padding:10px 20px;color:#F96A74;">
				                           		<g:message code="drug_deafness.user.logout.label" />
				                           </button>
										</form>
									</li>
								</ul>
					        </li>
	                	</sec:ifLoggedIn>
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
	                	<li><a href="${createLink(controller: 'information', action: 'list')}">信息列表</a></li>
                        <li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
                        <li><a class="current" href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'list')}">导出pdf报告</a></li>
                        <sec:ifAnyGranted roles="ROLE_ADMIN">
                        	<li><a href="${createLink(controller: 'user', action: 'list')}">用户管理</a></li>
                        	<li><a href="${createLink(controller: 'district', action: 'list')}">地区管理</a></li>
                        </sec:ifAnyGranted>
                    </ul>
	            </div>
	            <div class="col-md-10">
		                <div class="clearfix">
	                		<ul class="nav nav-tabs">
		                        <li role="presentation"  class="active"><a>批量上传</a></li>
		                        <li role="presentation"><a>单个录入</a></li>
		                        <li role="presentation"><a>上传记录</a></li>
		                    </ul>
	                    </div>
	                    <div class="specialForm">
		                    <g:form id="form-multiple" class="form-horizontal optForm" method="post" enctype="multipart/form-data" url="[action:'uploadBatch',controller:'result']" >
		                        <div class="form-group">
		                            <label class="col-md-2 control-label" for="InputFile">文件上传</label>
		                            <div class="col-md-4">
		                            	<input type="file" name="InputFile" id="InputFile" class="input-sm" multiple="multiple" >
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
	                                <label class="col-md-2 col-md-offset-1 control-label">样品编号</label>
	                                <div class="col-md-3">
	                                    <input id="sampleNum" name="sampleNum" type="text" class="form-control input-sm">
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
		                        <button id="clearBtn_single" class="btn btn-default">清空</button>
		                        <button id="submitBtn_single" class="btn btn-success" style="float: right;">提交</button>
	                        </div>
	                    </div>
	                    
	                	<div class="specialForm" style="display:none;">
                			<table class="table table-no-bordered" id="table-result"></table>
         		    	</div>
	                </div>
	            </div>
	        </div>
	    </div>
	   <script>
	   		var $table = $("#table-result");
		   	$(function(){

		   		var oTable = new TableInit();
	            oTable.Init();
	            
				//顶部导航
			    $("ul.nav-tabs").on("click","li",function () {
		            var index = $(this).index();
		            $(this).siblings("li").removeClass("active");
		            $(this).addClass("active");
		            var target = $("div.specialForm:eq("+index+")");
		            target.show();
		            target.siblings("div.specialForm").hide();
		        })
		        //清空按钮
			    $("#clearBtn_multiple").on("click",function(){
			    	$("#fileListTable tbody").empty();
	            	$("#fileList").hide();
			    	$("#InputFile").replaceWith('<input type="file" name="InputFile" id="InputFile" class="input-sm" multiple="multiple">');
	    		    $('#InputFile').on('change', function (e) {
	    		    	checkfile(this.id);
	    		    	selectFile(e);
	    		    });
				})	
			    //提交按钮 批量上传
			    $("#submitBtn_multiple").on("click",function(){
					var xhr = new XMLHttpRequest();
				    var data = new FormData(document.getElementById("form-multiple"));
				    var url="${createLink(controller: 'result', action:'uploadBatch')}";
				    xhr.open("POST",url,true);
				    xhr.onreadystatechange=function(){
				        if(xhr.readyState==4 && xhr.status==200){  //判断状态到4了并且返回状态码是200时才做操作
				        	
				        	$("div.specialForm:eq(2)").show();
					        $("div.specialForm:eq(2)").siblings("div.specialForm").hide();
					        $("ul.nav-tabs>li:eq(2)").addClass("active");
					        $("ul.nav-tabs>li:eq(2)").siblings("li").removeClass("active");
				        	$table.bootstrapTable('refresh');
				        	alert(xhr.responseText);
				        }
				    };
				    xhr.send(data);
				 	//清空信息
				    $("#fileListTable tbody").empty();
	            	$("#fileList").hide();
			    	$("#InputFile").replaceWith('<input type="file" name="InputFile" id="InputFile" class="input-sm" multiple="multiple" >');
	    		    $('#InputFile').on('change', function (e) {
	    		    	checkfile(this.id);
	    		    	selectFile(e);
	    		    });
			    });
			    //提交按钮 单个录入
			    $("#submitBtn_single").on("click",function(){
					var xhr = new XMLHttpRequest();
				    var data = new FormData(document.getElementById("form-single"));
				    var url="${createLink(controller: 'result', action:'uploadOne')}";
				    xhr.open("POST",url,true);
				    xhr.onreadystatechange=function(){
				        if(xhr.readyState==4 && xhr.status==200){  //判断状态到4了并且返回状态码是200时才做操作
				            $("div.specialForm:eq(2)").show();
					        $("div.specialForm:eq(2)").siblings("div.specialForm").hide();
					        $("ul.nav-tabs>li:eq(2)").addClass("active");
					        $("ul.nav-tabs>li:eq(2)").siblings("li").removeClass("active");
				        	$table.bootstrapTable('refresh');
				        }
				    };
				    xhr.send(data);
			    });
			    //清空按钮
			    $("#clearBtn_single").on("click",function(){
			    	
			    	
			    	
			    });
			    
		    	//选择文件
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
			                $("#InputFile").replaceWith('<input type="file" name="InputFile" id="InputFile" class="input-sm" multiple="multiple">');
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
			    }}
		    );
		    
		    var TableInit = function () {
	            var oTableInit = new Object();
	            //初始化Table
	            oTableInit.Init = function () {
	                $table.bootstrapTable({
	                    url: '<g:createLink controller="result" action="listRecord" params="[json: 'json']"/>',         //请求后台的URL（*）
	                    method: 'get',                      //请求方式（*）
	                    striped: false,                      //是否显示行间隔色
	                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	                    pagination: true,                   //是否显示分页（*）
	                    sortable: false,                    //是否启用排序
	                    sortOrder: "asc",                   //排序方式
	                    queryParams: oTableInit.queryParams,//传递参数（*）
	                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	                    pageNumber:1,                       //初始化加载第一页，默认第一页
	                    pageSize: 10,                       //每页的记录行数（*）
	                    pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
	                    search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	                    strictSearch: true,
	                    showColumns: false,                 //是否显示所有的列
	                    showRefresh: false,                  //是否显示刷新按钮
	                    minimumCountColumns: 2,             //最少允许的列数
	                    clickToSelect: true,                //是否启用点击选中行
	                    singleSelect:false,					//是否启用点击选中行
	                    height: 492,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	                    uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
	                    showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
	                    cardView: false,                    //是否显示详细视图
	                    detailView: true,                   //是否显示父子表
	                    detailFormatter:function(index, row) {
	                    	var succeedArr = ["上传成功编号:"];
	                    	var failedArr = ["上传失败编号:"];
					        if(row.successedSample !== null){
					        	var _arr = row['successedSample'].split(",")
					        	$.each(_arr,function(index,value){
					        		var _span = "<span class='label label-success'>"+value+"</span>"
					        		succeedArr.push(_span);
					        	})
				        	}
				        	if(row.failedSample !== null){
				        		var _arr = row['failedSample'].split(",")
			        			$.each(_arr,function(index,value){
					        		var _span = "<span class='label label-danger'>"+value+"</span>"
					        		failedArr.push(_span);
					        	})
			        		}
					        return succeedArr.join(" ")+"<br>"+failedArr.join(" ");
					    },
	                    columns: [{
	                        checkbox: true
	                    },{
	                        field: 'name',
	                        title: '上传人',
	                    }, {
	                        field: 'recordName',
	                        title: '上传文件名称',
	                    }, {
	                        field: 'successNum',
	                        title: '上传成功(条)'
	                    }, {
	                        field: 'failedNum',
	                        title: '上传失败(条)'
	                    }, {
	                        field: 'startTime',
	                        title: '上传日期'
	                    }]
	                });
	            };
	
	            //得到查询的参数
	            oTableInit.queryParams = function (params) {
	                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	                    max: params.limit,   //页面大小
	                    offset: params.offset,  //页码
	                };
	                return temp;
	            };
	
	            return oTableInit;
	        };
	    </script>
	</body>
</html>
