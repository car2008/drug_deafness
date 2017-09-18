<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="auth">
	<title>
		${message(code: 'drug_deafness.springSecurity.login.title')}
	</title>
</head>

<body>
	<!-- HOME -->
    <section>
        <div class="container">
            <div class="row">
                <div class="col-sm-12">

                    <div class="wrapper-page">

                        <div class="account-pages">
                            <div class="account-box">
                                <div class="account-logo-box">
                                    <h2 class="text-uppercase text-center">
                                         <span class="cbt-health-logo-login"></span>
                                    </h2>
                                    <h5 class="text-uppercase font-bold m-b-5 m-t-50">
                                    	<g:message code="drug_deafness.springSecurity.login.title" />
                                    </h5>
                                    <p class="m-b-0">
                                    	<g:message code="drug_deafness.springSecurity.login.header" />
                                    </p>
                                    <g:if test='${flash.message}'>
                                    	<p class="m-b-0 text-danger">
	                                    	${flash.message}
	                                    </p>
                                    </g:if>
                                </div>
                                <div class="account-content">
                                    <form method="post" action='${postUrl}' class="form-horizontal">

                                        <div class="form-group m-b-20">
                                            <div class="col-xs-12">
                                                <input class="form-control" type="email" id="username" name="j_username" required="required" placeholder="${message(code: 'drug_deafness.springSecurity.login.username.label')}">
                                            </div>
                                        </div>

                                        <div class="form-group m-b-20">
                                            <div class="col-xs-12">
                                                <input class="form-control" type="password" required="required" id="password" name="j_password" placeholder="${message(code: 'drug_deafness.springSecurity.login.password.label')}">
                                            </div>
                                        </div>

                                        <div class="form-group m-b-20">
                                            <div class="col-xs-12">

                                                <div class="checkbox checkbox-success">
                                                    <input id="remember_me" name="${rememberMeParameter}" type="checkbox" checked="">
                                                    <label for="remember_me">
                                                        <g:message code="drug_deafness.springSecurity.login.remember.me.label" />
                                                    </label>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="form-group text-center m-t-10">
                                            <div class="col-xs-12">
                                                <button class="btn btn-md btn-block btn-primary waves-effect waves-light" type="submit">
                                                	<g:message code="drug_deafness.springSecurity.login.button" />
                                                </button>
                                            </div>
                                        </div>

                                    </form>

                                    <div class="row m-t-50">
                                        <div class="col-sm-12 text-center">
                                            <p class="text-muted">
                                            	<a href="#" class="text-dark m-l-5">
                                            		<g:message code="user.register.label" />
                                            	</a>
                                            	<a href="#" class="text-dark m-l-5">
                                            		<g:message code="user.password.reset.label" />
                                            	</a>
                                            </p>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <!-- end card-box-->


                    </div>
                    <!-- end wrapper -->

                </div>
            </div>
        </div>
      </section>
      <!-- END HOME -->
</body>
</html>
