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
	            <h1>药物性耳聋数据库-用户管理</h1>
	        </div>
	    </div>
	    <div class="container-custom" >
			<div class="row">
	            <div class="col-md-2">
					<ul id="nav-page" class="nav">
						<li><a href="${createLink(controller: 'information', action: 'list')}">信息列表</a></li>
						<li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
						<li><a href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
						<li><a href="${createLink(controller: 'result', action: 'list')}">导出pdf报告</a></li>
						<li><a class="current" href="${createLink(controller: 'user', action: 'list')}">用户管理</a></li>
					</ul>
	            </div>
	            <div class="col-md-10">
	            	<div class="form-content">
	            		<div class="table-toolbar" style="margin-bottom:15px;">
	            			<g:link class="btn btn-success" controller="user" action="create">
	            				<i class="fa fa-fw fa-plus"></i>
	            				新增
	            			</g:link>
	            		</div>
	            		<table class="table">
							<thead>
								<tr>
									<th>${message(code: 'user.id.label')}</th>
									<th>${message(code: 'user.name.label')}</th>
									<th>${message(code: 'user.username.label')}</th>
									<th>${message(code: 'user.authority.label')}</th>
									<th>${message(code: 'user.location.label')}</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<g:each in="${userInstanceList}" var="userInstance">
									<tr>
										<td>
											${userInstance?.id}
										</td>
										<td>
											${userInstance?.name}
										</td>
										<td>
											${userInstance?.username}
										</td>
										<td>
											<g:each in="${userInstance?.getAuthorities()}" var="role" status="i">
												<g:if test="${i > 0}"><br /></g:if>
												<g:message code="role.authority.${role.authority}.label" />
											</g:each>
										</td>
										<td>
											${userInstance?.district?.title}
										</td>
										<td>
											<sec:ifAnyGranted roles="ROLE_ADMIN">
												<g:link controller="user" action="edit" id="${userInstance.id}">编辑信息</g:link>
											|
												<g:link controller="user" action="password" id="${userInstance.id}">修改密码</g:link>
											</sec:ifAnyGranted>
										</td>
									</tr>
								</g:each>
							</tbody>
						</table>
						<%--<div class="clearfix">
	                		<div style="margin-top:20px;float:left;" >
		                		显示第 1 到第${params.max}条记录，总共${allInformationInstanceTotal}条记录 每页
								<select id="pageCount" class="form-control" style="width:auto;padding:0;display:inline-block;">
									<g:each in="${[10, 20, 50, 100]}" var="option">
										<option value="${option}" ${params.max == option ? 'selected' : ''}>${option}</option>
									</g:each>
								</select>
								条记录
	                		</div>
							<ul class="pagination" style="float:right;">
		                		<cbt_health:paginate total="${allInformationInstanceTotal}" params="${params}" />
		                	</ul>
						</div> --%>
					</div>
	            </div>
		    </div>
	    </div>
	</body>
</html>
