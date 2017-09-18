<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="renderer" content="webkit" />
		<meta name="author" content="CBT Bioinformatics, CapitalBio Technology" />
		
		<link rel="apple-touch-icon" sizes="180x180" href="/static/images/favicons/apple-touch-icon.png" />
		<link rel="icon" type="image/png" href="/static/images/favicons/favicon-32x32.png" sizes="32x32" />
		<link rel="icon" type="image/png" href="/static/images/favicons/android-chrome-192x192.png" sizes="192x192" />
		<link rel="icon" type="image/png" href="/static/images/favicons/favicon-16x16.png" sizes="16x16" />
		<link rel="manifest" href="/static/images/favicons/manifest.json" />
		<link rel="mask-icon" href="/static/images/favicons/safari-pinned-tab.svg" color="#5bbad5" />
		<link rel="shortcut icon" href="/static/images/favicons/favicon.ico" />
		<meta name="apple-mobile-web-app-title" content="${message(code: 'drug_deafness.name')}" />
		<meta name="application-name" content="${message(code: 'drug_deafness.name')}" />
		<meta name="msapplication-TileColor" content="#d9534f" />
		<meta name="msapplication-TileImage" content="/static/images/favicons/mstile-144x144.png" />
		<meta name="msapplication-config" content="/static/images/favicons/browserconfig.xml" />
		<meta name="theme-color" content="#ffffff" />
		
		<title>
			<g:layoutTitle /> - <g:message code="drug_deafness.name" />
		</title>
		
		<asset:stylesheet href="application.css" />
		<link href="${createLink(controller: 'include', action: 'stylesheet')}" rel="stylesheet" type="text/css" />
		
		<asset:javascript src="application.js" />
		<script src="${createLink(controller: 'include', action: 'javascript')}"></script>
		
		<g:layoutHead />
		<r:layoutResources />
	</head>
	
	<body class="bg-accpunt-pages">
		<g:layoutBody />
	</body>
</html>
