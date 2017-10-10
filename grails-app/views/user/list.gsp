<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
<head>
<meta name="layout" content="main" />
<title><g:message code="default.list.label" args="${[message(code: 'user.label')]}" /></title>
</head>
<body>
	<div class="row-fluid">
		<div class="span12 bpms-header">
			<g:message code="default.list.label" args="${[message(code: 'user.label')]}" />
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span9">
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
				<table class="table nowrap">
					<thead>
						<tr>
							<th>${message(code: 'user.id.label')}</th>
							<th>${message(code: 'user.name.label')}</th>
							<th>${message(code: 'user.username.label')}</th>
							<th>${message(code: 'user.authority.label')}</th>
							<th>${message(code: 'user.location.label')}</th>
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
									${userInstance?.username}
								</td>
								<td>
									<sec:ifAnyGranted roles="ROLE_ADMIN">
										<g:link controller="user" action="edit" id="${userInstance.id}"><g:message code="edit.information.label" /></g:link>
									|
										<g:link controller="user" action="password" id="${userInstance.id}"><g:message code="change.password.label" /></g:link>
									</sec:ifAnyGranted>
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<drug_deafness:paginate total="${userInstanceTotal}" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>
