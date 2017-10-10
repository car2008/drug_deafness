<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
<head>
<meta name="layout" content="main" />
<title>
<g:if test="${self}">
<g:message code="edit.my.information.label" />
</g:if>
<g:else>
<g:message code="edit.information.label" />: ${userInstance?.username}
</g:else>
</title>
</head>
<body>
	<div class="row-fluid">
		<div class="span12 bpms-header">
			<g:if test="${self}">
				<g:message code="edit.my.information.label" />
			</g:if>
			<g:else>
				<g:message code="edit.information.label" />: ${userInstance?.username}
			</g:else>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span10">
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
				<g:form method="post" class="form-horizontal">
					<g:hiddenField name="id" value="${userInstance?.id}" />
					<g:hiddenField name="version" value="${userInstance?.version}" />
					<g:render template="form" model="${[userInstance: userInstance]}"/>
					<div class="modal-footer" style="width:100%;float:left;margin: 15px -20px -20px -20px;">
						<g:actionSubmit class="btn btn-primary" action="update"
							value="${message(code: 'default.button.update.label', default: 'Update')}" />
						<sec:ifAnyGranted roles="ROLE_ADMIN">
							<g:actionSubmit class="btn btn-danger" action="delete"
								href="#deleteModal" data-toggle="modal"
								value="${message(code: 'default.button.delete.label', default: 'Delete')}" />
						</sec:ifAnyGranted>
					</div>
				</g:form>
			</div>
		</div>
	</div>
	<sec:ifAnyGranted roles="ROLE_ADMIN">
		<!-- Modal -->
		<div id="deleteModal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="deleteModalLabel"><g:message code="warning.label" /></h3>
			</div>
			<div class="modal-body">
				<p><g:message code="user.deletion.warning"/></p>
			</div>
			<div class="modal-footer">
				<g:form action="delete" method="post" style="padding:0; margin:0">
					<g:hiddenField name="id" value="${userInstance?.id}" />
					<button class="btn" data-dismiss="modal" aria-hidden="true"><g:message code="cancel.label" /></button>
					<button class="btn btn-danger"><g:message code="confirm.deletion.label" /></button>
				</g:form>
			</div>
		</div>
	</sec:ifAnyGranted>
</body>
</html>
