<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
</head>
<body style="background: #363636; font-family: Helvetica, Arial, sans-serif; margin: 0; padding: 0" bgcolor="#363636">
<style type="text/css">
body {
background-color: #363636; font-family: Helvetica, Arial, sans-serif; margin: 0; padding: 0;
}
</style>
<table style="-premailer-cellpadding: 0; -premailer-cellspacing: 0; background: #363636; border: 0; border-collapse: separate; width: 100%" bgcolor="#363636" cellpadding="0" cellspacing="0">
<tr>
<td>
<table style="-premailer-cellpadding: 0; -premailer-cellspacing: 0; border: 0; border-collapse: separate; margin: 0 auto; text-align: left; width: 600px" cellpadding="0" cellspacing="0">
<td valign="top" style="text-align: center" align="center">
<img src="${resource(dir: 'images', file: 'logo-white-bigger@2x.png', absolute: true)}" style="border: none; min-height: 57px; outline: none; width: 50px; margin: 20px 0;">
</td>
</table>
<table style="-premailer-cellpadding: 0; -premailer-cellspacing: 0; background: #fafafa; border: 1px solid #000; border-collapse: separate; border-radius: 4px; margin: 0 auto 20px auto; width: 600px" bgcolor="#fafafa" cellpadding="0" cellspacing="0">
<tr>
<td id="body-container" style="padding: 20px 40px">
<div class="center" style="text-align: center" align="center">
<div id="content">
<h2 style="color: #e14329; font-size: 30px; font-weight: 400; line-height: 34px; margin-top: 0">
${title}
</h2>
<p style="color: #444; font-size: 17px; line-height: 24px; margin-bottom: 0">
${description}
</p>
</div>
<div id="cta" style="border: 1px solid #e14329; border-radius: 3px; display: inline-block; margin: 20px 0; padding: 12px 24px">
<a href="${linkHref}" style="background: #fafafa; color: #e14329; display: inline-block; text-decoration: none">
${linkTitle}
</a>
</div>
</div>
</td>
</tr>
</table>
<table style="-premailer-cellpadding: 0; -premailer-cellspacing: 0; border-collapse: separate; margin: 0 auto 20px auto; width: 600px; color: #ddd; font-size: 13px;" cellpadding="0" cellspacing="0">
<tr>
<td id="body-container" style="padding: 20px 40px">
<div class="center" style="text-align: center" align="center">
<div id="content">
<p>${message(code: 'mail.privacy.description')}</p>
</div>
</div>
</td>
</tr>
</table>
</td>
</tr>
</table>
</body>
</html>