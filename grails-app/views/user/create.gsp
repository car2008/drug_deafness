<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
<head>
<meta name="layout" content="main" />
<title><g:message code="default.create.label" args="${[message(code: 'user.label')]}" /></title>
</head>
<body>
	<div class="row-fluid">
		<div class="span12 bpms-header">
			<g:message code="default.create.label" args="${[message(code: 'user.label')]}" />
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
				<g:form action="save" class="form-horizontal">
					<g:render template="form" model="${[userInstance: userInstance]}"/>
					<div class="modal-footer" style="width:100%;float:left;margin: 15px -20px -20px -20px;">
						<g:submitButton id="create" name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label')}" />
					</div>
				</g:form>
			</div>
		</div>
	</div>
</body>
</html>
