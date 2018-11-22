(function(){
	"use strict";
	
	
	
	var $userAvatarEL;
	
	$(function(){
		$userAvatarEL=$("#userAvatarEL");
		paintAvatar();
	});
	
	var paintAvatar=function(id){
		var currentUser=JSON.parse(window.sessionStorage.getItem("currentUser"));
		var avatar=currentUser.avatar;
		$userAvatarEL.attr("src",serverURL+avatar);
	};
	
	
})();
