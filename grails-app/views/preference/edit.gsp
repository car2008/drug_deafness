<%@ page import="com.capitalbiotech.health.Preference" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>
			<g:message code="preference.label" /> - <g:message code="management.label" />
		</title>
	</head>
	<body>
		<g:render template="/management/header"/>
		
		<div class="cloud-header-follower"></div>
		
		<g:form action="update" role="form" class="form-horizontal form-groups validate">
			<g:each in="${categories}" var="category">
				<div class="form-group">
					<div class="col-sm-6 col-sm-offset-3">
						<h2 class="text-highlight">
							<g:message code="preference.category.${category}.label" />
						</h2>
					</div>
				</div>
				<g:each in="${categorizedPreferences[category]}" var="preference">
					<div class="form-group">
						<label for="" class="col-sm-3 control-label">
							<g:message code="preference.key.${preference.key}.label" />
							<g:if test="${preference.type == Preference.TYPE_USER}">
								<i data-toggle="tooltip" data-placement="top" data-original-title="${message(code: 'preference.type.USER.tip.label')}" class="fa fa-fw fa-user tooltip-primary"></i>
							</g:if>
						</label>
						<div class="col-sm-6">
							<g:if test="${preference.valueType == Preference.VALUE_TYPE_SINGLE_SELECTION}">
								<select class="form-control selectpicker" name="${preference.key}" ${preference.options?.size() > 10 ? 'data-live-search="true"' : ''}>
									<g:each in="${preference.options}" var="option">
										<option value="${option}" ${preference.value == option ? 'selected' : ''}>
											<g:message code="preference.key.${preference.key}.option.${option}.label" default="${option}" />
										</option>
									</g:each>
								</select>
							</g:if>
							<g:elseif test="${preference.valueType == Preference.VALUE_TYPE_MULTIPLE_SELECTION}">
								<select multiple class="form-control selectpicker" name="${preference.key}" ${preference.options?.size() > 10 ? 'data-live-search="true"' : ''}>
									<g:each in="${preference.options}" var="option">
										<option value="${option}" ${preference.values?.contains(option) ? 'selected' : ''}>
											<g:message code="preference.key.${preference.key}.option.${option}.label" default="${option}" />
										</option>
									</g:each>
								</select>
							</g:elseif>
							<g:elseif test="${preference.valueType == Preference.VALUE_TYPE_STRING}">
								<textArea class="form-control" name="${preference.key}">${preference.value}</textArea>
							</g:elseif>
							<g:else>
								<input type="text" class="form-control" name="${preference.key}" value="${preference.value}">
							</g:else>
							<span class="help-block">
								<g:message code="preference.key.${preference.key}.help" default="" />
							</span>
						</div>
					</div>
				</g:each>
			</g:each>
			<div class="form-group">
				<div class="col-sm-3 control-label">
					
				</div>
				<div class="col-sm-6">
					<button class="btn btn-primary" type="submit">
						<i class="fa fa-fw fa-check"></i>
						<g:message code="default.update.label" />
					</button>
				</div>
			</div>
		</g:form>
	</body>
</html>
