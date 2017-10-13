<%@ page import="com.capitalbiotech.drugdeafness.District" %>
	<g:set var="today" value="${formatDate(format:'yyyy-MM-dd', date:new Date())}" />
	<sec:ifAnyGranted roles="ROLE_ADMIN">
		<g:set var="flag" value="flag" />
	</sec:ifAnyGranted>
	<div class="modal-body">
		<div class="form-group ${hasErrors(bean: districtInstance, field: 'code', 'error')}">
			<label class="control-label col-sm-1" for="code"><g:message code="district.code.label" /><span class="help-inline">*</span></label>
			<div class="col-sm-3">
				<g:textField class="form-control input-sm" name="code" value="${districtInstance?.code}"  />
			</div>
		</div>
		<div class="form-group ${hasErrors(bean: districtInstance, field: 'title', 'error')}">
			<label class="control-label col-sm-1" for="title"><g:message code="district.title.label" /><span class="help-inline">*</span></label>
			<div class="col-sm-3">
				<g:textField  class="form-control input-sm" name="title" value="${districtInstance?.title}" />
			</div>
		</div>
		<div class="form-group ${hasErrors(bean: districtInstance, field: 'description', 'error')}">
			<label class="control-label col-sm-1" for="description"><g:message code="district.description.label" /><span class="help-inline">*</span></label>
			<div class="col-sm-3">
				<g:textField  class="form-control input-sm" name="description" value="${districtInstance?.description}" />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#district").chosen({});
		});
	</script>
