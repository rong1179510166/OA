<%@page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>

<link rel="shortcut icon" href="../favicon.ico">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<base href="/oa/login/">	
<title>NEUSOFT | OA</title>
<link rel="stylesheet" href="../common.css">
<link rel="stylesheet"
	href="../thirdparty-lib/fontawesome/font-awesome.min.css">
<script src="../thirdparty-lib/jquery/jquery.min.js"></script>
<script src="../common.js"></script>
<link rel="stylesheet" href="login.css">
</head>
<body class="layout-vbox">
	<header>
		<h1>东软·OA</h1>
	</header>
	<main class="grow layout-center-middle">
	<article>
		<div class="eagel-box">
			<i class="sprt-eagle hand-left"></i> <i class="sprt-eagle head"></i>
			<i class="sprt-eagle hand-right"></i>
		</div>
		<section class="form-box">
			<form id="loginForm" method="post">
				<p>
					<input class="grow" type="text" name="account" placeholder="账号">
				</p>

				<p>
					<input class="grow" name="password" type="password"
						placeholder="密码">
				</p>
				<p>
					<span class="input-group"> <input class="grow" type="text"
						name="captcha" value="1" placeholder="请输入验证码" /> <img
						id="captchaImgEL" width="150" height="40" title="看不清，点击图片切换">
					</span>
				</p>
			</form>
			<div class="layout-justify form-action">
				<a href="../forget-password/view.html">忘记密码？</a>
				<button id="submitBtn">登陆</button>
			</div>
		</section>
	</article>
	</main>
	<footer>
		&copy;东软睿道 2000 - <span id="yearNowEL"> <script>document.getElementById("yearNowEL").innerHTML =new Date().getFullYear();</script>
		</span> All Rights Reservered.
	</footer>
	<script src="eagle.js"></script>
	<script src="login.js"></script>
</body>
</html>