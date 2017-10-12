<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
<head>
	<title><g:message code="user.label" />: ${userInstance?.username}</title>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, district-scalable=no" />
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
			<div class="col-md-10 col-md-offset-1">
				<p class="bg-info" style="padding:15px;border-radius:5px;">
					用户信息
				</p>
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
				<hr>
				<dl class="dl-horizontal">
					<dt><g:message code="user.username.label" /></dt>
					<dd>${userInstance?.username}</dd>
					<dt><g:message code="user.name.label" /></dt>
					<dd>${userInstance?.name}</dd>
					<dt><g:message code="user.enabled.label" /></dt>
					<dd>${userInstance?.enabled==true?'在职':'离职'}</dd>
					<dt><g:message code="district.label" /></dt>
					<dd>${userInstance?.district.title}</dd>
					<dt><g:message code="user.roles.label" /></dt>
					<dd>
						<g:each in="${userInstance?.getAuthorities()}" var="role" status="i">
							<g:if test="${i > 0}"><br /></g:if>
							<g:message code="role.authority.${role.authority}.label" />
						</g:each>
					</dd>
				</dl>
				<div class="modal-footer">
					<sec:ifAnyGranted roles="ROLE_ADMIN">
						<g:link class="btn btn-primary" action="edit" id="${userInstance?.id}"><g:message code="edit.information.label" /></g:link>
						<g:link class="btn btn-primary" action="password" id="${userInstance?.id}"><g:message code="change.password.label" /></g:link>
					</sec:ifAnyGranted>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
