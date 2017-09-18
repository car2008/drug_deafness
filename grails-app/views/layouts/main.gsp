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
	
	<body>
		<!-- Navigation Bar-->
        <header id="topnav">
            <div class="topbar-main">
                <div class="container">

                    <!-- Logo container-->
                    <div class="logo">
                        <!-- Image Logo -->
                        <span class="cbt-health-logo" style="line-height: 24px; margin-top: 18px;"></span>

                    </div>
                    <!-- End Logo container-->

                    <div class="menu-extras">

                        <ul class="nav navbar-nav navbar-right pull-right">
                            <!-- <li class="navbar-c-items">
                                <form role="search" class="navbar-left app-search pull-left hidden-xs">
                                     <input type="text" placeholder="Search..." class="form-control">
                                     <a href=""><i class="fa fa-search"></i></a>
                                </form>
                            </li> -->

                            <li class="dropdown navbar-c-items">
                                <span class="badge badge-topbar badge-success">4</span>
                                 <a href="#" class="right-menu-item dropdown-toggle waves-effect" data-toggle="dropdown">
                                    <i class="mdi mdi-bell"></i>
                                </a>

                                <ul class="dropdown-menu dropdown-menu-right arrow-dropdown-menu arrow-menu-right dropdown-lg user-list notify-list">
                                    <li class="text-center">
                                        <h5>Notifications</h5>
                                    </li>
                                    <li>
                                        <a href="#" class="user-list-item">
                                            <div class="icon bg-info">
                                                <i class="mdi mdi-account"></i>
                                            </div>
                                            <div class="user-desc">
                                                <span class="name">New Signup</span>
                                                <span class="time">5 hours ago</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" class="user-list-item">
                                            <div class="icon bg-danger">
                                                <i class="mdi mdi-comment"></i>
                                            </div>
                                            <div class="user-desc">
                                                <span class="name">New Message received</span>
                                                <span class="time">1 day ago</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" class="user-list-item">
                                            <div class="icon bg-warning">
                                                <i class="mdi mdi-settings"></i>
                                            </div>
                                            <div class="user-desc">
                                                <span class="name">Settings</span>
                                                <span class="time">1 day ago</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="all-msgs text-center">
                                        <p class="m-0"><a href="#">See all Notification</a></p>
                                    </li>
                                </ul>
                            </li>

							<drug_deafness:ifLoggedIn var="loggedInUser">
	                            <li class="dropdown navbar-c-items">
	                                <a href="" class="dropdown-toggle waves-effect profile" data-toggle="dropdown" aria-expanded="true">
	                                	<i class="mdi mdi-account"></i>
	                                </a>
	                                <ul class="dropdown-menu dropdown-menu-right arrow-dropdown-menu arrow-menu-right user-list notify-list">
	                                    <li class="text-center">
	                                        <span class="logged-in-user">${loggedInUser?.name}</span>
	                                    </li>
	                                    <li>
	                                    	<a href="javascript:void(0)">
	                                    		<i class="dripicons-user m-r-10"></i>
	                                    		<g:message code="user.profile.label" />
	                                    	</a>
	                                    </li>
	                                    <li>
	                                    	<a href="javascript:void(0)">
	                                    		<i class="dripicons-gear m-r-10"></i>
	                                    		<g:message code="preferences.label" />
	                                    	</a>
	                                    </li>
	                                    <li>
	                                    	
	                                    	<form method="post" action="${createLink(controller: 'logout', action: 'index')}">
			                                    <button type="submit" class="btn btn-default" style="border:none;width:100%;text-align:left;padding: 7px 20px;color:#F96A74;">
			                                    	<i class="dripicons-power m-r-10"></i>
			                                    	<g:message code="drug_deafness.user.logout.label" />
			                                    </button>
		                                    </form>
	                                    </li>
	                                </ul>
	                            </li>
                            </drug_deafness:ifLoggedIn>
                        </ul>
                        <div class="menu-item">
                            <!-- Mobile menu toggle-->
                            <a class="navbar-toggle">
                                <div class="lines">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </div>
                            </a>
                            <!-- End mobile menu toggle-->
                        </div>
                    </div>
                    <!-- end menu-extras -->

                </div> <!-- end container -->
            </div>
            <!-- end topbar-main -->

            <div class="navbar-custom">
                <div class="container">
                    <div id="navigation">
                        <!-- Navigation Menu-->
                        <ul class="navigation-menu">

                            <li class="has-submenu">
                                <a href="#"><i class="fi-air-play"></i>Dashboard</a>
                                <ul class="submenu">
                                    <li><a href="index.html">Dashboard 1</a></li>
                                    <li><a href="dashboard-2.html">Dashboard 2</a></li>
                                </ul>
                            </li>

                            <li class="has-submenu">
                                <a href="#"><i class="fi-briefcase"></i>Admin UI</a>
                                <ul class="submenu megamenu">
                                    <li>
                                        <ul>
                                            <li>
                                                <span>UI Kit</span>
                                            </li>
                                            <li><a href="ui-typography.html">Typography</a></li>
                                            <li><a href="ui-panels.html">Panels</a></li>
                                            <li><a href="ui-buttons.html">Buttons</a></li>
                                            <li><a href="ui-modals.html">Modals</a></li>
                                            <li><a href="ui-checkbox-radio.html">Checkboxs-Radios</a></li>
                                            <li><a href="ui-spinners.html">Spinners</a></li>
                                            <li><a href="ui-ribbons.html">Ribbons</a></li>
                                            <li><a href="ui-portlets.html">Portlets</a></li>
                                            <li><a href="ui-tabs.html">Tabs</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <ul>
                                            <li>
                                                <span>UI Kit</span>
                                            </li>
                                            <li><a href="ui-progressbars.html">Progress Bars</a></li>
                                            <li><a href="ui-notifications.html">Notification</a></li>
                                            <li><a href="ui-carousel.html">Carousel</a>
                                            <li><a href="ui-video.html">Video</a>
                                            <li><a href="ui-tooltips-popovers.html">Tooltips & Popovers</a></li>
                                            <li><a href="ui-images.html">Images</a></li>
                                            <li><a href="ui-bootstrap.html">Bootstrap UI</a></li>
                                            <li><a href="admin-grid.html">Grid</a></li>
                                            <li><a href="admin-sweet-alert.html">Sweet Alert</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <ul>
                                            <li>
                                                <span>Admin UI</span>
                                            </li>
                                            <li><a href="admin-tiles.html">Tiles Box</a></li>
                                            <li><a href="admin-nestable.html">Nestable List</a></li>
                                            <li><a href="admin-rangeslider.html">Range Slider</a></li>
                                            <li><a href="admin-ratings.html">Ratings</a></li>
                                            <li><a href="admin-filemanager.html">File Manager</a></li>
                                            <li><a href="admin-lightbox.html">Lightbox</a></li>
                                            <li><a href="admin-scrollbar.html">Scroll bar</a></li>
                                            <li><a href="admin-slider.html">Slider</a></li>
                                            <li><a href="admin-treeview.html">Treeview</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>

                            <li class="has-submenu">
                                <a href="#"><i class="fi-box"></i>Components</a>
                                <ul class="submenu">
                                    <li class="has-submenu">
                                        <a href="#">Icons</a>
                                        <ul class="submenu">
                                            <li><a href="icons-colored.html">Colored Icons</a></li>
                                            <li><a href="icons-materialdesign.html">Material Design</a></li>
                                            <li><a href="icons-dripicons.html">Dripicons</a></li>
                                            <li><a href="icons-fontawesome.html">Font awesome</a></li>
                                            <li><a href="icons-feather.html">Feather Icons</a></li>
                                            <li><a href="icons-simple-line.html">Simple line Icons</a></li>
                                            <li><a href="icons-flags.html">Flag Icons</a></li>
                                            <li><a href="icons-file.html">File Icons</a></li>
                                            <li><a href="icons-pe7.html">PE7 Icons</a></li>
                                            <li><a href="icons-typicons.html">Typicons</a></li>
                                        </ul>
                                    </li>
                                    <li class="has-submenu">
                                        <a href="#">Graphs</a>
                                        <ul class="submenu">
                                            <li><a href="chart-flot.html">Flot Chart</a></li>
                                            <li><a href="chart-morris.html">Morris Chart</a></li>
                                            <li><a href="chart-google.html">Google Chart</a></li>
                                            <li><a href="chart-echart.html">Echarts</a></li>
                                            <li><a href="chart-chartist.html">Chartist Charts</a></li>
                                            <li><a href="chart-chartjs.html">Chartjs Chart</a></li>
                                            <li><a href="chart-c3.html">C3 Chart</a></li>
                                            <li><a href="chart-justgage.html">Justgage Charts</a></li>
                                            <li><a href="chart-sparkline.html">Sparkline Chart</a></li>
                                            <li><a href="chart-knob.html">Jquery Knob</a></li>
                                        </ul>
                                    </li>
                                    <li class="has-submenu">
                                        <a href="#">Email</a>
                                        <ul class="submenu">
                                            <li><a href="email-inbox.html">Inbox</a></li>
                                            <li><a href="email-read.html">Read Email</a></li>
                                            <li><a href="email-compose.html">Compose Email</a></li>
                                        </ul>
                                    </li>
                                    <li class="has-submenu">
                                        <a href="#">Tables</a>
                                        <ul class="submenu">
                                            <li><a href="tables-basic.html">Basic Tables</a></li>
                                            <li><a href="tables-layouts.html">Tables Layouts</a></li>
                                            <li><a href="tables-datatable.html">Data Tables</a></li>
                                            <li><a href="tables-foo-tables.html">Foo Tables</a></li>
                                            <li><a href="tables-responsive.html">Responsive Table</a></li>
                                            <li><a href="tables-tablesaw.html">Tablesaw Tables</a></li>
                                            <li><a href="tables-editable.html">Editable Tables</a></li>
                                        </ul>
                                    </li>
                                    <li class="has-submenu">
                                        <a href="#">Maps</a>
                                        <ul class="submenu">
                                            <li><a href="maps-google.html">Google Maps</a></li>
                                            <li><a href="maps-google-full.html">Full Google Map</a></li>
                                            <li><a href="maps-vector.html">Vector Maps</a></li>
                                            <li><a href="maps-mapael.html">Mapael Maps</a></li>
                                        </ul>
                                    </li>
                                    <li class="has-submenu">
                                        <a href="#">Apps</a>
                                        <ul class="submenu">
                                            <li><a href="apps-tickets.html">Tickets</a></li>
                                            <li><a href="apps-taskboard.html">Taskboard</a></li>
                                            <li><a href="apps-calendar.html">Calendar</a></li>
                                            <li><a href="apps-todo.html">Todo</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>

                            <li class="has-submenu">
                                <a href="#"><i class="fi-paper"></i>Forms</a>
                                <ul class="submenu megamenu">
                                    <li>
                                        <ul>
                                            <li><a href="form-elements.html">Form Elements</a></li>
                                            <li><a href="form-advanced.html">Form Advanced</a></li>
                                            <li><a href="form-layouts.html">Form Layouts</a></li>
                                            <li><a href="form-validation.html">Form Validation</a></li>
                                            <li><a href="form-pickers.html">Form Pickers</a></li>
                                            <li><a href="form-wizard.html">Form Wizard</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <ul>
                                            <li><a href="form-mask.html">Form Masks</a></li>
                                            <li><a href="form-summernote.html">Summernote</a></li>
                                            <li><a href="form-wysiwig.html">Wysiwig Editors</a></li>
                                            <li><a href="form-typeahead.html">Typeahead</a></li>
                                            <li><a href="form-x-editable.html">X Editable</a></li>
                                            <li><a href="form-uploads.html">Multiple File Upload</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>

                            <li class="has-submenu">
                                <a href="#"><i class="fi-paper-stack"></i>Pages</a>
                                <ul class="submenu megamenu">
                                    <li>
                                        <ul>
                                            <li><a href="page-starter.html">Starter Page</a></li>
                                            <li><a href="page-login.html">Login</a></li>
                                            <li><a href="page-register.html">Register</a></li>
                                            <li><a href="page-logout.html">Logout</a></li>
                                            <li><a href="page-recoverpw.html">Recover Password</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <ul>
                                            <li><a href="page-lock-screen.html">Lock Screen</a></li>
                                            <li><a href="page-confirm-mail.html">Confirm Mail</a></li>
                                            <li><a href="page-404.html">Error 404</a></li>
                                            <li><a href="page-404-alt.html">Error 404-alt</a></li>
                                            <li><a href="page-500.html">Error 500</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>

                            <li class="has-submenu">
                                <a href="#"><i class="fi-layers"></i>数据管理</a>
                                <ul class="submenu megamenu">
                                    <li>
                                        <ul>
                                            <li>
		                                    	<a href="${createLink(controller: 'topic', action: 'index')}">
		                                    		<g:message code="topic.label" />
		                                    	</a>
		                                    </li>
		                                    <li>
		                                    	<a href="${createLink(controller: 'snpEdia', action: 'index')}">
		                                    		<g:message code="SNPedia.label" />
		                                    	</a>
		                                    </li>
		                                    <li>
		                                    	<a href="${createLink(controller: 'snpTopic', action: 'index')}">
		                                    		<g:message code="snpTopic.label" />
		                                    	</a>
		                                    </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>

                        </ul>
                        <!-- End navigation menu -->
                    </div> <!-- end #navigation -->
                </div> <!-- end container -->
            </div> <!-- end navbar-custom -->
        </header>
        <!-- End Navigation Bar-->


        <div class="wrapper">
            <div class="container">

				<g:layoutBody />
				
                <!-- Footer -->
                <footer class="footer text-right">
                    <div class="container">
                        <div class="row">
                            <div class="col-xs-12 text-center">
                                &copy; <g:message code="drug_deafness.copyright"/>
                                <a href="${message(code: 'drug_deafness.company.url')}">
                                	<g:message code="drug_deafness.company.name"/>
                                </a>
                            </div>
                        </div>
                    </div>
                </footer>
                <!-- End Footer -->

            </div> <!-- end container -->
        </div>
        <!-- end wrapper -->
	</body>
</html>
