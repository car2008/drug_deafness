<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
	<head>
		<title><g:message code="default.list.label" args="${[message(code: 'user.label')]}" /></title>
			<meta charset="utf-8" />
			<meta http-equiv="X-UA-Compatible" content="IE=edge" />
			<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
			<meta name="renderer" content="webkit" />
			<meta name="author" content="CBT Bioinformatics, CapitalBio Technology" />
			<link rel="stylesheet" href="${resource(dir:'css/bootstrap/dist/css/', file:'bootstrap.css')}"/>
			<link rel="stylesheet" href="${resource(dir:'css/', file:'bootstrap-table.css')}"/>
		    <link rel="stylesheet" href="${resource(dir:'css/font-awesome/css/', file:'font-awesome.min.css')}"/>
		    <link rel="stylesheet" href="${resource(dir:'css/', file:'sweetalert.css')}">  
		    <link rel="stylesheet" href="${resource(dir:'css/', file:'chosen.css')}">  
		    <link rel="stylesheet" href="${resource(dir:'css/', file:'index.css')}"/>
		    <script src="${resource(dir:'js/', file:'jquery.js')}"></script>
		    <script src="${resource(dir:'js/', file:'bootstrap.min.js')}"></script>
		    <script src="${resource(dir:'js/', file:'bootstrap-table.js')}"></script>
		    <script src="${resource(dir:'js/', file:'bootstrap-table-zh-CN.js')}"></script>
		    <script src="${resource(dir:'js/', file:'chosen.jquery.js')}"></script>
		    <script src="${resource(dir:'js/', file:'sweetalert.min.js')}"></script>
		<style>
			.error{ color:red; }
		</style>
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
	            <h1>药物性耳聋数据库-用户管理</h1>
	        </div>
	    </div>
		<div class="container-custom" >
			<div class="row">
				<div class="col-md-10">
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
					<g:hasErrors bean="${userInstance}">
						<div class="alert alert-error">
							<g:renderErrors bean="${userInstance}" as="list" />
						</div>
					</g:hasErrors>
					<div class="modal-body">
						<g:form name="editPwdForm" method="post" class="form-horizontal">
							<g:hiddenField name="version" value="${userInstance?.version}" />
							<sec:ifAnyGranted roles="ROLE_ADMIN">
								<g:hiddenField name="id" value="${userInstance?.id}" />
							</sec:ifAnyGranted>
							<div class="form-group">
								<label class="control-label col-sm-2" for="oldPassword">
									原始密码
								</label>
								<div class="col-sm-3">
									<input class="form-control input-sm" type="password" name="oldPassword" id="oldPassword" >
									<%--<g:passwordField name="oldPassword" />--%>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="newPassword"><g:message code="new.password.label" /></label>
								<div class="col-sm-3">
									<input class="form-control input-sm" type="password" name="newPassword" id="newPassword" >
									<%--<g:passwordField name="newPassword" />--%>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="confirmPassword"><g:message code="confirm.new.password.label" /></label>
								<div class="col-sm-3">
									<input class="form-control input-sm" type="password" name="confirmPassword" id="confirmPassword" >
									<%--<g:passwordField name="confirmPassword" />--%>
								</div>
							</div>
						</g:form>
					</div>
					<div class="modal-footer" style="width:100%;">
						<g:actionSubmit class="btn btn-primary" action="updatePassword" value="${message(code: 'default.button.submit.label')}" />
					</div>
				</div>
			</div>
		</div>
		<script><%--
			$(function (){
				$("#editPwdForm").validate({
					errorPlacement: function(error, element) {
						// Append error within linked label
						$( element ).parent().append( error );
					},
					errorElement: "span",
				    rules: {
				      oldPassword: {
				        required: true,
				      },
				      newPassword:{
				    	required: true,
				    	minlength: 6,
				      },
				      confirmPassword: {
				        required: true,
				        minlength: 6,
				        equalTo: "#newPassword"
				      },
				    },
				    messages: {
				      newPassword: {
				        required: "请输入密码",
				        minlength: "密码长度不能小于 6 个字母"
				      },
				      confirmPassword: {
				        required: "请输入密码",
				        minlength: "密码长度不能小于 6 个字母",
				        equalTo: "两次密码输入不一致"
				      },
				    }
				});
			});
		 	
		--%></script>
	</body>
</html>
