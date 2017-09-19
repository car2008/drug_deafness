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
			<g:layoutTitle /> - <g:message code="drug_deafness.name" />
		</title>
		
		<g:layoutHead />
		<r:layoutResources />
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
	            <h1>药物性耳聋数据库-信息录入</h1>
	        </div>
	    </div>
	
	    <div class="container-custom" >
	        <div class="row">
	            <div class="col-md-2">
	                <nav class="">
	                    <ul class="nav">
	                        <li><a href="#">信息录入</a></li>
	                        <li><a href="./result/multiple.html">结果录入</a></li>
	                        <li><a href="./pdf.html">导出pdf报告</a></li>
	                    </ul>
	                </nav>
	            </div>
	            <div class="col-md-10">
	                <div class="clearfix">
	                    <ul class="nav nav-tabs">
	                        <li role="presentation" class="active" ><a>批量上传</a></li>
	                        <li role="presentation"><a>单个录入</a></li>
	                    </ul>
	                </div>
	                <div class="specialForm">
	                    <form id="form-multiple" class="form-horizontal optForm" >
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label" for="InputFile">文件上传</label>
	                            <div class="col-sm-4">
	                                <input type="file" id="InputFile" class="input-sm" multiple="multiple">
	                            </div>
	                        </div>
	                        <div style="margin-top:30px;">
	                            <button class="btn btn-default">清空</button>
	                            <button class="btn btn-success" style="float: right;">提交</button>
	                        </div>
	                    </form>
	                </div>

	                <div class="specialForm" style="display: none;">
	                    <form id="form-single" class="form-horizontal optForm">
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label">样本编号</label>
	                            <div class="col-md-3">
	                                <input id="sampleNum" name="sampleNum" type="text"  class="form-control input-sm">
	                            </div>
	                            <label class="col-md-2 control-label">姓名</label>
	                            <div class="col-md-3">
	                                <input id="patientName" name="patientName" type="text" class="form-control input-sm">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label">性别</label>
	                            <div class="col-md-3">
	                                <input id="gender" name="gender" type="text" class="form-control input-sm">
	                            </div>
	                            <label class="col-md-2 control-label">年龄</label>
	                            <div class="col-md-3">
	                                <input id="age" name="age" type="text" class="form-control input-sm">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label">门诊号/住院号</label>
	                            <div class="col-md-3">
	                                <input id="patientNum" name="patientNum" type="text" class="form-control input-sm">
	                            </div>
	                            <label class="col-md-2 control-label">病房/床位</label>
	                            <div class="col-md-3">
	                                <input id="ward-bed" name="ward-bed" type="text" class="form-control input-sm">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label">送检科室</label>
	                            <div class="col-md-3">
	                                <input id="inspection-department" name="inspection-department" type="text" class="form-control input-sm">
	                            </div>
	                            <label class="col-md-2 control-label">送检医生</label>
	                            <div class="col-md-3">
	                                <input id="inspection-doctor" name="inspection-doctor" type="text" class="form-control input-sm">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label">送检样本</label>
	                            <div class="col-md-3">
	                                <input id="inspection-sample" name="inspection-sample" type="text" class="form-control input-sm">
	                            </div>
	                            <label class="col-md-2 control-label">送检时间</label>
	                            <div class="col-md-3">
	                                <input  id="inspection-time" name="inspection-time" type="text" class="form-control input-sm">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-md-2 col-md-offset-1 control-label">联系电话</label>
	                            <div class="col-md-3">
	                                <input id="phoneNum" name="phoneNum" type="text" class="form-control input-sm">
	                            </div>
	                            <label class="col-md-2 control-label">备注</label>
	                            <div class="col-md-3">
	                                <input id="remark" name="remark" type="text" class="form-control input-sm">
	                            </div>
	                        </div>
	                    </form>
	                    <div style="margin-top:30px;">
	                        <button class="btn btn-default">清空</button>
	                        <button class="btn btn-success" style="float: right;">提交</button>
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
	    </script>
	</body>
</html>
