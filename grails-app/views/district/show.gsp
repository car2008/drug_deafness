<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
<head>
<meta name="layout" content="main" />
<title><g:message code="district.label" />: ${districtInstance?.title}</title>
</head>
<body>
	<div class="row-fluid">
		<div class="span12 drut_deafness-header">
			<g:message code="district.label" />: ${districtInstance?.title}
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
				<dl class="dl-horizontal">
					<dt><g:message code="district.id.label" /></dt>
					<dd>${districtInstance?.id}</dd>
					<dt><g:message code="district.code.label" /></dt>
					<dd>${districtInstance?.code}</dd>
					<dt><g:message code="district.title.label" /></dt>
					<dd>${districtInstance?.title}</dd>
					<dt><g:message code="district.description.label" /></dt>
					<dd>${districtInstance?.description}</dd>
				</dl>
				<sec:ifAnyGranted roles="ROLE_ADMIN">
					<g:link class="btn btn-primary" action="edit" id="${districtInstance?.id}"><g:message code="edit.information.label" /></g:link>
					<g:link class="btn" action="password" id="${districtInstance?.id}"><g:message code="change.password.label" /></g:link>
				</sec:ifAnyGranted>
			</div>
		</div>
	</div>
</body>
</html>
