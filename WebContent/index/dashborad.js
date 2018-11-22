(function(){
	"use strict";
	
	var $userNameEL,
		$lastLoginTimeEL,
		$lastLoginIpEL,
		$weatherEL
	;
	
	
	$(function(){
		$userNameEL=$("#userNameEL");
		$lastLoginIpEL=$("#lastLoginIpEL");
		$lastLoginTimeEL=$("#lastLoginTimeEL");
		
		
		paintLoginInfo();
		
	});
	
	var paintLoginInfo=function(){
		var uc=JSON.parse(sessionStorage.getItem("currentUser"));
		$userNameEL.text(uc.name);
		//是否是第一次登录
		if(uc.lastLoginTime){
			$lastLoginIpEL.text(uc.lastLoginIp);
			$lastLoginTimeEL.text(Date.format(uc.lastLoginTime));
		}else{
			//第一次登录
			$("#tipBox").remove();
		}
		
	};
	
	
	
})();
