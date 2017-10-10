<%@ page import="com.capitalbiotech.drugdeafness.User"%>
<html>
<head>
<meta name="layout" content="main" />
<title>
<g:if test="${self}">
<g:message code="change.my.password.label" />
</g:if>
<g:else>
<g:message code="change.password.label" />: ${userInstance?.username}
</g:else>
</title>
<style>
	.error{ color:red; }
</style>
</head>

<body>
	<div class="row-fluid">
		<div class="span12 bpms-header">
			<g:if test="${self}">
				<g:message code="change.my.password.label" />
			</g:if>
			<g:else>
				<g:message code="change.password.label" />: ${userInstance?.username}
			</g:else>
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
				<g:hasErrors bean="${userInstance}">
					<div class="alert alert-error">
						<g:renderErrors bean="${userInstance}" as="list" />
					</div>
				</g:hasErrors>
				<g:form name="editPwdForm" method="post" class="form-horizontal">
					<g:hiddenField name="version" value="${userInstance?.version}" />
					<sec:ifAnyGranted roles="ROLE_ADMIN">
						<g:hiddenField name="id" value="${userInstance?.id}" />
					</sec:ifAnyGranted>
					<div class="control-group">
						<label class="control-label" for="oldPassword">
								<g:if test="${self}"><g:message code="original.password.label" /></g:if>
								<g:else><g:message code="admin.password.label" /></g:else>
						</label>
						<div class="controls">
							<input type="password" name="oldPassword" id="oldPassword" >
							<%--<g:passwordField name="oldPassword" />--%>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="newPassword"><g:message code="new.password.label" /></label>
						<div class="controls">
							<input type="password" name="newPassword" id="newPassword" >
							<%--<g:passwordField name="newPassword" />--%>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="confirmPassword"><g:message code="confirm.new.password.label" /></label>
						<div class="controls">
							<input type="password" name="confirmPassword" id="confirmPassword" >
							<%--<g:passwordField name="confirmPassword" />--%>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<g:actionSubmit class="btn btn-primary" action="updatePassword" value="${message(code: 'default.button.submit.label')}" />
						</div>
					</div>
				</g:form>
			</div>
			<!-- /span -->
			<div class="span3">
				<g:render template="sidebar" />
			</div>
			<!--/span-->
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
