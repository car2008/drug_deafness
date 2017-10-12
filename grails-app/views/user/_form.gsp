<%@ page import="com.capitalbiotech.drugdeafness.User" %>
	<g:set var="today" value="${formatDate(format:'yyyy-MM-dd', date:new Date())}" />
	<sec:ifAnyGranted roles="ROLE_ADMIN">
		<g:set var="flag" value="flag" />
	</sec:ifAnyGranted>
	<style>
		.control-group{
			margin:0 10%;
			margin-bottom:10px;
		}
	</style>
		<div class="modal-body">
			<div class="form-group ${hasErrors(bean: userInstance, field: 'username', 'error')}">
				<label class="control-label col-sm-1" for="username"><g:message code="user.username.label" /><span class="help-inline">*</span></label>
				<div class="col-sm-3">
					<g:textField class="form-control input-sm" name="username" value="${userInstance?.username}"  />
				</div>
			</div>
			<div class="form-group ${hasErrors(bean: userInstance, field: 'name', 'error')}">
				<label class="control-label col-sm-1" for="avatar"><g:message code="user.name.label" /><span class="help-inline">*</span></label>
				<div class="col-sm-3">
					<g:textField  class="form-control input-sm" name="name" value="${userInstance?.name}" />
				</div>
			</div>
			<div class="form-group ${hasErrors(bean: userInstance, field: 'district', 'error')}">
				<label class="control-label col-sm-1"  for="district"><g:message code="district.label" /><span class="help-inline">*</span></label>
				<div class="col-sm-3">
					<select class="form-control input-sm" id="district" name="district">
						<g:each in="${districtInstanceList}" var="districtInstance">
							<option value="${districtInstance?.id}" ${userInstance?.district?.collect{it.id}?.contains(districtInstance.id) ? 'selected' : ''}  >${districtInstance?.title}</option>
						</g:each>
					</select>
				</div>
			</div>
			<g:if test="${params.action=='create'||params.action=='save' }">
				<div class="form-group ${hasErrors(bean: userInstance, field: 'password', 'error')}">
					<label class="control-label col-sm-1" for="password"><g:message code="user.password.label" /><span class="help-inline">*</span></label>
					<div class="col-sm-3">
						<g:textField class="form-control input-sm" name="password" value="" />
					</div>
				</div>
			</g:if>
			<div class="form-group ${hasErrors(bean: userInstance, field: 'enabled', 'error')}">
				<label class="control-label col-sm-1" for="enabled"><g:message code="user.enabled.label" /><span class="help-inline">*</span></label>
				<div class="col-sm-3">
					<input class="radio-inline" type="radio" name="enabled" id="enabled" value="true"  ${userInstance?.enabled == true ? 'checked' : ''}/>在职
					<input class="radio-inline" type="radio" name="enabled" id="enabled" value="false" ${userInstance?.enabled == false ? 'checked' : ''}/>离职
				</div>
			</div>
			<div class="form-group ${hasErrors(bean: userInstance, field: 'roles', 'error')}">
				<label class="control-label col-sm-1" for="roles"><g:message code="user.roles.label" default="Roles" /><span class="help-inline">*</span></label>
				<div class="col-sm-3">
					<label class="checkbox-inline"> 
						<input type="checkbox" name="authority" value="ROLE_ADMIN" ${authorities?.contains('ROLE_ADMIN') ? 'checked' : ''}>
						管理员
					</label>
					<br />
					<label class="checkbox-inline"> 
						<input type="checkbox" name="authority" value="ROLE_DEV" ${authorities?.contains('ROLE_DEV') ? 'checked' : ''}>
						开发人员
					</label>
					<br />
					<label class="checkbox-inline"> 
						<input type="checkbox" name="authority" value="ROLE_STAFF" ${authorities?.contains('ROLE_STAFF') ? 'checked' : ''}>
						工作人员
					</label>
					<br/>
					<label class="checkbox-inline"> 
						<input type="checkbox" name="authority" value="ROLE_USER" checked disabled>
						用户
					</label>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		$(function(){
			$("#district").chosen({});
		});
	</script>
