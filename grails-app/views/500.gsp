<!doctype html>
<html>
	<head>
		<title><g:message code="default.error.try.again.label" /></title>
		<meta name="layout" content="main">
	</head>
	<body>
		<g:if env="development">
			<g:renderException exception="${exception}" />
		</g:if>
		<g:else>
			<div class="page-error-404">
				<div class="error-symbol">
					<i class="fa fa-fw fa-exclamation-triangle"></i>
				</div>
				<div class="error-text">
					<h2>500</h2>
					<p><g:message code="default.error.try.again.label" /></p>
				</div>
			</div>
		</g:else>
	</body>
</html>