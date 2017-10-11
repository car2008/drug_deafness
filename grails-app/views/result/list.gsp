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
                        <li><a href="${createLink(controller: 'user', action: 'list')}">用户管理</a></li>
                    </ul>
	            </div>
	            <div class="col-md-10">
	                <div class="form-content clearfix" >
	                	<div class="panel panel-default"> 
					       <div class="panel-body"> 
					            <form id="formSearch" class="form-horizontal" action="queryResult">
					               <div class="form-group" style="margin-bottom:0px;">
						                <label class="control-label col-sm-1">编号</label> 
						                <div class="col-sm-2"> 
						                    <input type="text" class="form-control input-sm" id="search_sampleNum" name="search_sampleNum"> 
						                </div> 
						                <label class="control-label col-sm-1">姓名</label> 
						                <div class="col-sm-2"> 
						                    <input type="text" class="form-control input-sm" id="search_name" name="search_name"> 
						                </div>
									    <div class="col-sm-3">
											<label class="radio-inline"><input name="testResult" value="positive" type="radio">阳性</label>
										    <label class="radio-inline"><input name="testResult" value="negative" type="radio">阴性</label>
										    <label class="radio-inline"><input name="testResult" value="abnormal" type="radio">检测异常</label>
									    </div>
						                <div class="col-sm-1" style="text-align:left;"> 
						                    <button type="button" id="btn_query" class="btn btn-primary btn-sm">查询</button> 
						                </div> 
						            </div> 
					            </form>
					       </div> 
					   	</div>
					   	
					   	<table class="table table-no-bordered" id="table-pdf"></table>
					   	
					   	<div>
	                        <a href="#" id="createReport" class="btn btn-success" role="button" style="float: right;">生成报告</a>
	                    </div>
					   	
	                </div>
	            </div>
	        </div>
	    </div>
	    
	     <div id="editModal" class="modal fade" aria-labelledby="editModalLabel" data-backdrop="static">
	        <div class="modal-dialog"  style="width:60%;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                    <h4 class="modal-title" id="editModal">Modal title</h4>
	                </div>
	                <div class="modal-body">
	                    <form id="editForm" class="form-horizontal" method="post">
	                        <div class="modal-body">
	                            <input name="id" id="id" type="hidden" class="form-control input-sm">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">样本编号</label>
	                                <div class="col-sm-4">
	                                	<input name="sampleNum" id="sampleNum" class="form-control input-sm"></input>
	                                </div>
	                                <label class="col-sm-2 control-label">姓名</label>
	                                <div class=" col-sm-4">
	                                	<input name="patientName" id="patientName" class="form-control input-sm"></input>
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">性别</label>
	                                <div class="col-sm-4">
	                                    <input name="gender" id="gender" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-sm-2 control-label" style="font-size: 13px;">年龄</label>
	                                <div class="col-sm-4">
	                                    <input name="age" id="age" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">FAM_Ct</label>
	                                <div class="col-sm-4">
	                                    <input name="famCt" id="famCt" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-sm-2 control-label">VIC_Ct</label>
	                                <div class="col-sm-4">
	                                    <input name="vicCt" id="vicCt" type="text" class="form-control input-sm">
	                                </div>
	                           </div>
	                           <div class="form-group">
	                                <label class="col-sm-2 control-label">NED Ct</label>
	                                <div class="col-sm-4">
	                                    <input name="nedCt" id="nedCt" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-sm-2 control-label">检测结果</label>
	                                <div class="col-sm-4">
	                                    <input name="detectedResult" id="detectedResult" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">报告台头</label>
	                                <div class="col-sm-4">
	                                    <input name="resulttitle" id="resulttitle" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-sm-2 control-label">检验员</label>
	                                <div class="col-sm-4">
	                                    <input name="checker" id="detectedResult" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
                               <div class="form-group">
                               		<label class="col-sm-2 control-label">审核员</label>
	                                <div class="col-sm-4">
	                                    <input name="detectedResult" id="detectedResult" type="text" class="form-control input-sm">
	                                </div>
	                                <label class="col-sm-2 control-label">备注</label>
	                                <div class="col-sm-4">
	                                    <input name="pdfcomment" id="pdfcomment" type="text" class="form-control input-sm">
	                                </div>
	                            </div>
	                        </div>
                        </form>
	                </div>
	                <div class="modal-footer">
	                    <button id="btn_update" type="button" class="btn btn-success">
	                        <span class="glyphicon glyphicon-ok">保存修改</span>
	                    </button>
	                    <button id="btn_cancel" type="button" class="btn btn-danger" data-dismiss="modal">
	                        <span class="glyphicon glyphicon-remove">取消</span>
	                    </button>
	                </div>
	            </div>
	        </div>
	    </div>
	    
    	<div id="optModal" class="modal fade" aria-labelledby="optModalLabel" data-backdrop="static">
	        <div class="modal-dialog" >
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    	<h4>请补充报告信息</h4>
	                </div>
	                <div class="modal-body">
		                <form id="appendForm"  class="form-horizontal" >
							<div class="form-group">
							    <label class="col-md-offset-1 col-md-2 control-label">报告台头</label>
							    <div class="col-md-8">
							    	<input name="hospital" id="hospital" class="form-control input-sm"></input>
							    </div>
						    </div>
						    <div class="form-group">
						    	<label class="col-md-offset-1 col-md-2 control-label">审核员</label>
							    <div class="col-md-8">
							    	<input name="assessor" id="assessor" class="form-control input-sm"></input>
							    </div>
							</div>
							<div class="form-group">
								<label class="col-md-offset-1 col-md-2 control-label">检验员</label>
							    <div class="col-md-8">
							    	<input name="checker" id="checker" class="form-control input-sm"></input>
							    </div>
							</div>
							<div class="form-group">
							    <label class="col-md-offset-1 col-md-2 control-label">备注</label>
							    <div class="col-md-8">
							    	<textarea name="pdfcomment" id="pdfcomment" class="form-control" rows="4" style="resize:none;margin:0 auto;"></textarea>
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
	  		var $table = $("#table-pdf");
	  		var pageInfo = { 
  	           query:{ 
  					max:"", 
  					offset:"", 
  					num:$("#search_sampleNum").val(),
	                name:$("#search_name").val(),
	                testResult:$("input[name='testResult']:checked").val(),
  	           } 
  	        };
  	        
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
	        
	        function setPageInfo(){ 
				var tableOptions = $table.bootstrapTable('getOptions'); 
	            pageInfo['query']['max'] = tableOptions['pageSize']; 
				pageInfo['query']['offset'] = (tableOptions['pageNumber'] - 1) * tableOptions['pageSize']; 
				pageInfo['query']['num'] = $("#search_sampleNum").val(); 
				pageInfo['query']['name'] = $("#search_name").val(); 
				pageInfo['query']['testResult'] = $("input[name='testResult']:checked").val();
			}
			
	        var TableInit = function () {
	            var oTableInit = new Object();
	            //初始化Table
	            oTableInit.Init = function () {
	                $table.bootstrapTable({
	                    url: '<g:createLink controller="result" action="list" params="[json: 'json']"/>',         //请求后台的URL（*）
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
	                        formatter:function(value,row,index){
	                            var _a = '<a href="#" class="td_edit">'+value+'</a>';
	                            return _a;
	                        }
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
	                        field: 'famCt',
	                        title: 'FAM Ct'
	                    }, {
	                        field: 'vicCt',
	                        title: 'VIC Ct'
	                    }, {
	                        field: 'nedCt',
	                        title: 'NED Ct'
	                    },{
	                        field: 'detectedResult',
	                        title: '检测结果',
	                    },{
	                        field: 'resulttitle',
	                        title: '医院名称',
	                    },{
	                        field: 'checker',
	                        title: '检验员',
	                    },{
	                        field: 'assessor',
	                        title: '审核员',
	                    },{
	                        field: 'pdfcomment',
	                        title: '备注',
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
	                    testResult:$("input[name='testResult']:checked").val(),
	                };
	                return temp;
	            };
	
	            return oTableInit;
	        };
	        //表格行编辑按钮
	        $(document).on("click",".td_edit",function(){
				var arrselections = $table.bootstrapTable('getSelections');
            	var selectedRows = arrselections[0];
            	
                $("#editModalLabel").text("编辑");
                
                $("#id").val(selectedRows.id);
                $("#sampleNum").val(selectedRows.sampleNum);
                $("#patientName").val(selectedRows.patientName);
                $("#gender").val(selectedRows.gender);
                $("#age").val(selectedRows.age);
                $("#famCt").val(selectedRows.famCt);
                $("#vicCt").val(selectedRows.vicCt);
                $("#nedCt").val(selectedRows.nedCt);
                $("#detectedResult").val(selectedRows.detectedResult);
                $("#resulttitle").val(selectedRows.resulttitle);
                $("#checker").val(selectedRows.checker);
                $("#assessor").val(selectedRows.assessor);
         		$("#pdfcomment").val(selectedRows.pdfcomment);
         		
				$table.bootstrapTable('uncheckAll');
				$('#editModal').modal('show');

				//获取并重新设置查询参数
				setPageInfo();   
			});
			
			//表格行编辑更新按钮
            $("#btn_update").click(function(){
                $('#editModal').modal('hide');
                var form = new FormData(document.getElementById("editForm"));
                
                $.ajax({
					url:'<g:createLink controller="result" action="update" params="[json: 'json']"/>',
					type:"post",
					data:form,
					processData:false,
					contentType:false,
					success:function(data){
					    $table.bootstrapTable('refresh',pageInfo);  
					},
					error:function(e){
					     
					}
	         	});
            });
            //生成报告按钮
            $("#createReport").on("click", function () {
		        var arrselections = $table.bootstrapTable('getSelections');
		        if (arrselections.length <= 0) {
                    swal({
                        title: "请先选择需要修改的行",
                        type: "warning",
                    });
                    return;
                }
		        $("#optModal").modal('show');
		    })
		    //生成报告下载按钮
		    $("#btn_submit").on("click",function(){
		    	var arrselections = $table.bootstrapTable('getSelections');
            	
            	$.each(arrselections,function (index, singleRow) {
		            var _input = $("<input name='id'>");
		            _input.val(singleRow.id);
		            _input.css("display", "none");
		            $("#appendForm").append(_input);
		        })
		       
            	$("#appendForm")[0].action = '<g:createLink controller="result" action="generatePdf"/>';
        		$("#appendForm")[0].method = "POST";
		        $("#appendForm")[0].submit();
		        
            	$("#optModal").modal('hide');
		    })
            //查询按钮
            $("#btn_query").on("click",function(){
            	$table.bootstrapTable('refresh');
            });
	    </script>
	</body>
</html>
