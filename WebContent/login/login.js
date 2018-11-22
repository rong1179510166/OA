(function(window,document,$){
	"use strict";
	
	var $captchaImgEL,
		$submitBtn,
		$loginForm
	;
	
	$(function(){
		$captchaImgEL=$("#captchaImgEL");
		$captchaImgEL.click(handleCaptchaImgClick).click();
		
		$submitBtn=$("#submitBtn");
		$submitBtn.click(handleSubmitBtnClick);
		
		$loginForm=$("#loginForm");
	});

	
	var handleSubmitBtnClick=function(evt){
		var url="/public-api/user/login";
		
		$.post(url,$loginForm.serialize(),function(resp){
			if(resp.code==="ok"){
				var uc=resp.data;
				toast("欢迎你,"+uc.name);
				sessionStorage.setItem("currentUser",JSON.stringify(uc));
				setTimeout(function(){
					location.href="../index/view.html";
				},1000);
			}else{
				toast(resp.message);
				$captchaImgEL.click();
			}
		});
		
	}
	var handleCaptchaImgClick=function(evt){
		var url=serverURL+"/public-api/captcha?"+Math.random();
		$captchaImgEL.attr("src",url);
		
	}
	
	
	
})(window,document,$);
