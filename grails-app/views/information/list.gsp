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
	                		<li><a href="#" style="color:#563d7c;">欢迎${loggedInUser?.name}用户</a></li>
	                	</sec:ifLoggedIn>
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
                    	<li><a class="current" href="${createLink(controller: 'information', action: 'list')}">信息列表</a></li>
                        <li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'list')}">导出pdf报告</a></li>
                        <li><a href="${createLink(controller: 'user', action: 'list')}">用户管理</a></li>
                        <li><a href="${createLink(controller: 'district', action: 'list')}">地区管理</a></li>
                    </ul>
	            </div>
	            <div class="col-md-10">
	            	<div class="table-container">
	            		<div class="clearfix">
		                    <ul class="nav nav-tabs">
		                        <li role="presentation"  class="active"><a>信息列表</a></li>
		                    </ul>
		                    <div class="specialForm">
			            		<div class="panel panel-default"> 
							        <div class="panel-body"> 
										<form id="formSearch" class="form-horizontal" >
							                <div class="form-group" style="margin-bottom:0px;">
								                <label class="control-label" style="float:left;margin-left:15px;">编号</label> 
								                <div class="col-sm-2"> 
								                    <input type="text" class="form-control input-sm" id="search_sampleNum"> 
								                </div> 
								                <label class="control-label" style="float:left;">姓名</label> 
								                <div class="col-sm-2"> 
								                    <input type="text" class="form-control input-sm" id="search_name"> 
								                </div>
								                <div style="float:left;">
													<label class="radio-inline"><input value="true" name="hasResult" type="radio">有</label>
													<label class="radio-inline"><input value="false" name="hasResult" type="radio">无</label>
											    </div>
											    <sec:ifAnyGranted roles="ROLE_ADMIN">
												    <label class="control-label col-sm-1">地区</label> 
												    <div class="col-sm-2">
								                       	<select class="form-control input-sm" id="area-select">
											    			<option value="" selected></option>
											    			<g:each in="${districtInstanceList}" var="districtInstance">
																<option value="${districtInstance?.code}">${districtInstance?.title}</option>
															</g:each>
											    		</select>
												    </div>
											    </sec:ifAnyGranted> 
								                <div class="col-sm-1" style="text-align:left;"> 
								                    <button type="button" id="btn_query" class="btn btn-primary btn-sm">查询</button> 
								                </div> 
								            </div> 
							            </form>
							       </div> 
							   	</div>
			            		<table class="table table-no-bordered" id="table-infoList"></table>
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
	        
	        var $table = $("#table-infoList");
	  		
			function getIdSelections() {
		        return $.map($table.bootstrapTable('getSelections'), function (row) {
		            return row.id
		        });
		    }
		    
	        $(function () {
	            //1.初始化Table
	            var oTable = new TableInit();
	            oTable.Init();
	        });
	        
	        var TableInit = function () {
	            var oTableInit = new Object();
	            //初始化Table
	            oTableInit.Init = function () {
	                $table.bootstrapTable({
	                    url: '<g:createLink controller="information" action="list" params="[json: 'json']"/>',         //请求后台的URL（*）
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
	                    detailView: false,                   //是否显示父子表
	                    columns: [{
	                        checkbox: true
	                    },{
	                        field: 'sampleNum',
	                        title: '样本编号',
	                    }, {
	                        field: 'patientName',
	                        title: '姓名',
	                    }, {
	                        field: 'gender',
	                        title: '性别'
	                    }, {
	                        field: 'age',
	                        title: '年龄'
	                    }, {
	                        field: 'hospital',
	                        title: '医院/单位'
	                    }, {
	                        field: 'patientNum',
	                        title: '门诊号/住院号'
	                    }, {
	                        field: 'wardBed',
	                        title: '病房/床位'
	                    },{
	                        field: 'inspectionDepartment',
	                        title: '送检科室',
	                    },{
	                        field: 'inspectionDoctor',
	                        title: '送检医生',
	                    },{
	                        field: 'inspectionSample',
	                        title: '送检样本',
	                    },{
	                        field: 'inspectionTime',
	                        title: '送检时间',
	                    },{
	                        field: 'phoneNum',
	                        title: '联系电话',
	                    },{
	                        field: 'remark',
	                        title: '备注',
	                    },{
	                        field: 'hasResult',
	                        title: '有无结果',
	                        formatter:function(value,row,index){ 
		                        if( value === "有"){
		                        	 return '<span class="label label-success">'+value+'</span>';
		                        }else{
									return '<span class="label label-danger">'+value+'</span>';
		                        }
	                        }
	                    },{
	                        field: 'district',
	                        title: '地区',
	                    }]
	                });
	            };
	
	            //得到查询的参数
	            oTableInit.queryParams = function (params) {
	                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	                    max: params.limit,   //页面大小
	                    offset: params.offset,  //页码
	                    num:$("#search_sampleNum").val(),
		                name:$("#search_name").val(),
		               	hasResult:$("input[name='hasResult']:checked").val()
	                };
	                return temp;
	            };
	
	            return oTableInit;
	        };

	        $("#btn_query").on("click",function(){
            	$table.bootstrapTable('refresh');
            });


            
	    </script>
	</body>
</html>
