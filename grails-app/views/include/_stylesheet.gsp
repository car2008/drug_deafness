/* Locale: ${locale} */

.cbt-health-logo {
	background-image: url('${assetPath(src: locale == 'zh' ? 'logo_zh.png' : 'logo.png')}');
	background-repeat: no-repeat;
	display: inline-block;
	width: ${locale == 'zh' ? '118px' : '149px'};
	height: 24px;
}
.cbt-health-logo-login {
	background-image: url('${assetPath(src: locale == 'zh' ? 'logo_login_zh.png' : 'logo_login.png')}');
	background-repeat: no-repeat;
	display: inline-block;
	width: ${locale == 'zh' ? '107px' : '124px'};
	height: 84px;
}
@media all and (-webkit-min-device-pixel-ratio: 1.5) {
 	.cbt-health-logo {
  		background-image: url('${assetPath(src: locale == 'zh' ? 'logo_zh_2x.png' : 'logo_2x.png')}');
    	background-size: ${locale == 'zh' ? '107px 24px' : '124px 24px'};
 	}
 	.cbt-health-logo-login {
  		background-image: url('${assetPath(src: locale == 'zh' ? 'logo_login_zh_2x.png' : 'logo_login_2x.png')}');
    	background-size: ${locale == 'zh' ? '107px 84px' : '124px 84px'};
 	}
}