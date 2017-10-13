<%@ page import="com.capitalbiotech.drugdeafness.District"%>
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
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'chosen.css')}">  
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'index.css')}"/>
	    <script src="${resource(dir:'js/', file:'jquery.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap.min.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap-table.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap-table-zh-CN.js')}"></script>
	    <script src="${resource(dir:'js/', file:'chosen.jquery.js')}"></script>
	    <script src="${resource(dir:'js/', file:'sweetalert.min.js')}"></script>
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
                <a class="navbar-brand" href="${createLink(controller: 'information', action: 'index')}" style="color:#563d7c;">药物性耳聋数据库</a>
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
            <h1>药物性耳聋数据库-地区管理</h1>
        </div>
    </div>
	<div class="container-custom" >
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				 
				<g:if test="${self}">
					<p class="bg-info" style="padding:15px;border-radius:5px;">
						<g:message code="edit.my.information.label" />
					</p>
				</g:if>
				<g:else>
					<p class="bg-info" style="padding:15px;border-radius:5px;">
						<g:message code="edit.information.label" />: ${districtInstance?.title}
					</p>
				</g:else>
				 
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
				<g:hasErrors bean="${districtInstance}">
					<div class="alert alert-error">
						<g:renderErrors bean="${districtInstance}" as="list" />
					</div>
				</g:hasErrors>
				<hr>
				<form id="editForm" class="form-horizontal" method="post" action="${createLink(controller: 'district', action: 'update')}">
					<g:hiddenField name="id" value="${districtInstance?.id}" />
					<g:hiddenField name="version" value="${districtInstance?.version}" />
					<g:render template="form" model="${[districtInstance: districtInstance]}"/>
				<form>
				<div class="modal-footer" style="width:100%;">
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
					<sec:ifAnyGranted roles="ROLE_ADMIN">
						<g:actionSubmit class="btn btn-danger" action="delete" href="#deleteModal" data-toggle="modal" value="删除" />
					</sec:ifAnyGranted>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="deleteModal" aria-labelledby="deleteModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="deleteModalLabel">删除</h4>
	      </div>
	      <div class="modal-body">
				确定要删除该条记录吗？
	      </div>
	      <div class="modal-footer">
	      	<g:form action="delete" method="post" style="padding:0; margin:0">
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
				<button class="btn btn-danger">确定</button>
			</g:form>
	      </div>
	    </div>
	  </div>
	</div>
	<script>
		$(function(){
			$("#updateBtn").on("click",function(){
				$("#editForm").submit();
			});
		})
	</script>
</body>
</html>
