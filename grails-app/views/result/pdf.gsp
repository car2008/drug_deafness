<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="renderer" content="webkit" />
		<meta name="author" content="CBT Bioinformatics, CapitalBio Technology" />
		<link rel="icon" type="image/png" href="/static/images/favicons/favicon-32x32.png" sizes="32x32" />
		<link rel="icon" type="image/png" href="/static/images/favicons/android-chrome-192x192.png" sizes="192x192" />
		<link rel="icon" type="image/png" href="/static/images/favicons/favicon-16x16.png" sizes="16x16" />
		<link rel="shortcut icon" href="/static/images/favicons/favicon.ico" />
		
		<link rel="stylesheet" href="${resource(dir:'css/bootstrap/dist/css/', file:'bootstrap.css')}"/>
	    <link rel="stylesheet" href="${resource(dir:'css/font-awesome/css/', file:'font-awesome.min.css')}"/>
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'index.css')}"/>
	    <script src="${resource(dir:'js/', file:'jquery.js')}"></script>
	    <script src="${resource(dir:'js/', file:'bootstrap.min.js')}"></script>
	    <link rel="stylesheet" href="${resource(dir:'css/', file:'sweetalert.css')}">   
	    <script src="${resource(dir:'js/', file:'sweetalert.min.js')}"></script>
		<title>
			<g:message code="drug_deafness.name" />
		</title>
	</head>
	
	<body>
		
	 	<nav class="navbar navbar-default nav-custom">
	        <div class="container-custom">
	            <div class="navbar-header">
	                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
	                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	                    <span class="sr-only"></span>
	                    <span class="icon-bar"></span>
	                    <span class="icon-bar"></span>
	                    <span class="icon-bar"></span>
	                </button>
	                <a class="navbar-brand" href="#" style="color:#563d7c;">药物性耳聋数据库</a>
	            </div>
	            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	                <ul class="nav navbar-nav navbar-right">
	                    <li><a href="#" style="color:#563d7c;">欢迎XXX用户</a></li>
	                    <li><a href="#" style="color:#563d7c;">[退出]</a></li>
	                </ul>
	            </div>
	        </div>
	    </nav>
	
	    <div style="background-color: #684D90;padding:30px 0;margin-bottom:20px;">
	        <div class="container-custom" style="color:#fff;">
	            <h1>药物性耳聋数据库-pdf</h1>
	        </div>
	    </div>
	
	    <div class="container-custom">
	        <div class="row">
	            <div class="col-md-2">
	                <ul id="nav-page" class="nav">
                        <li><a href="${createLink(controller: 'information', action: 'index')}">信息录入</a></li>
                        <li><a href="${createLink(controller: 'result', action: 'index')}">结果录入</a></li>
                        <li><a class="current" href="${createLink(controller: 'result', action: 'showpdf')}">导出pdf报告</a></li>
                    </ul>
	            </div>
	            <div class="col-md-10">
	                <div class="form-content clearfix" >
	                	<div class="panel panel-default"> 
					       <div class="panel-body"> 
					            <form id="formSearch" class="form-horizontal" >
					               <div class="form-group" style="margin-bottom:0px;">
						                <label class="control-label col-sm-1">编号</label> 
						                <div class="col-sm-2"> 
						                    <input type="text" class="form-control input-sm" id="search_name"> 
						                </div> 
						                <div class="col-sm-1" style="text-align:left;"> 
						                    <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary btn-sm">查询</button> 
						                </div> 
						            </div> 
					            </form>
					       </div> 
					   	</div>
	                	<table class="table" id="" >
                			<thead>
								<tr>
									<th>
										<g:checkBox name="selectedAll" id="selectedAll" />
									</th>
									<th>pdf报告编号</th>
									<th>样本编号</th>
									<th>姓名</th>
									<th>上传记录</th>
			                	</tr>
		                	</thead>	
	                		<tbody>
	                			
	                		
	                		</tbody>
	                	</table>
	                	
	                    <div style="text-align: center">
	                        <h2>xxxxxxxxxxx医院</h2>
	                        <h2>药物性耳聋核酸检测报告</h2>
	                    </div>
	                    <hr>
	                    <table class="table table-bordered">
	                        <h4><strong>病人基本信息</strong></h4>
	                        <tbody>
	                        <tr>
	                            <th>姓名</th>
	                            <td></td>
	                            <th>性别</th>
	                            <td></td>
	                            <th>年龄</th>
	                            <td></td>
	                        </tr>
	                        <tr>
	                            <th>样本编号</th>
	                            <td></td>
	                            <th>门诊号/住院号</th>
	                            <td></td>
	                            <th>病房/床位</th>
	                            <td></td>
	                        </tr>
	                        <tr>
	                            <th>送检科室</th>
	                            <td></td>
	                            <th>送检医生</th>
	                            <td></td>
	                            <th>送检样本</th>
	                            <th></th>
	                        </tr>
	                        <tr>
	                            <th>送检时间</th>
	                            <td></td>
	                            <th>备注</th>
	                            <td colspan="3"></td>
	                        </tr>
	                        </tbody>
	                    </table>
	                    <table class="table">
	                        <h4><strong>检测结果</strong></h4>
	                        <thead>
	                            <tr>
	                                <th>序号</th>
	                                <th>检测项目</th>
	                                <th>检测值</th>
	                                <th>阴阳性判断</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            <tr>
	                                <th>1</th>
	                                <td>FAM（1494 C > T）</td>
	                                <td>18.35</td>
	                                <td>阳性（+）</td>
	                            </tr>
	                            <tr>
	                                <th>2</th>
	                                <td>VIC（1555 A > G）</td>
	                                <td>--</td>
	                                <td>阴性（-）</td>
	                            </tr>
	                            <tr>
	                                <th>3</th>
	                                <td>NED（质控）</td>
	                                <td>18.89</td>
	                                <td>阳性（+）</td>
	                            </tr>
	                            <tr>
	                                <th colspan="4">备注</th>
	                            </tr>
	                        </tbody>
	                    </table>
	                    <div class="result_notice">
	                        <h4><strong>检测结果</strong></h4>
	                        <p>本次送检样本为1494 C > T突变型。</p>
	                        <p>药物性耳聋敏感个体。</p>
	                        <p>提示服用耳毒性药物会导致耳聋,应终生禁用耳毒性药物。</p>
	                        <p>建议被检者亲属进行基因检测，以确认其是否为遗传性耳聋或遗传性耳聋基因突变携带者。</p>
	                    </div>
	                    <div class="examination_info clearfix">
	                        <ul>
	                            <li>
	                                <span>检验员</span>
	                                <span style="text-decoration:underline">XXX</span></li>
	                            <li>
	                                <span>审核员</span>
	                                <span style="text-decoration:underline">XXX</span></li>
	                            <li>
	                                <span>检验日期</span>
	                                <span style="text-decoration:underline">XXX</span></li>
	                        </ul>
	                    </div>
	                    <div class="warning" style="color: red;">
	                        *&nbsp;&nbsp;<strong>本报告仅对本次送检样品负责，如有疑问，请与您的医生联系。</strong>
	                    </div>
	                    <div style="margin-top:20px;">
	                        <a href="./result/single.html" class="btn btn-default" role="button">上一步</a>
	                        <a href="../pdf.html" class="btn btn-success" role="button" style="float: right;">生成报告</a>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</body>
</html>
