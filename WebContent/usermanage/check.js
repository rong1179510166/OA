(function(){
	"use strict";
	
	
	var id;
	
	var $modForm;
	var $modifyBtn;
	var $deleteBtn;
	
	$(function(){
		id=window.location.hash.substr(1);
		
		$modForm=$("#modForm");
		$modifyBtn=$("#modifyBtn").click(handleSubmitBtnClick);
		$deleteBtn=$("#deleteBtn").click(handleDeleteBtnClick);
		
		loadModel(id);
	});
	
	var loadModel=function(id){
		var url="/api/system/user/get?id="+id;
		$.get(url,function(resp){
			if(resp.code==="ok"){
				paintView(resp.data);
			}else{
				toast(resp.message);
			}
		})
		
	};
	
	var handleSubmitBtnClick=function(evt){
		var url="/api/system/user/mod";
		$.post(url,$modForm.serialize(),function(resp){
			if(resp.code==="ok"){
				toast("修改成功，请返回");
			}else{
				toast(resp.message);
			}
		});
	};
	var handleDeleteBtnClick=function(evt){
		var url="/api/system/user/del?id="+id;
		$.get(url,function(resp){
			if(resp.code==="ok"){
				toast("删除成功");
				setTimeout(function(){
					location.href="list.html";
				},1000);
			}else{
				toast(resp.message);
			}
		});
	};
	
	
	var paintView=function(m){
		var form=$modForm[0];
		
		form.id.value=m.id;
		
		$("#avatarEL").prop("src",m.avatar);
		
		form.account.value=m.account;
		form.name.value=m.name;
		form.remark.value=$$(m.remark);
		$("#accountStateEL").text(m.locked?"锁定":"正常");
		
		$("#phoneEL").val(m.phone);
		$("#securityEmailEL").val(m.securityEmail);
		$("#lastLoginTimeEL").val(m.lastLoginTime);
		$("#lastLoginIPEL").val(m.lastLoginIp);
		$("#lastModPasswordTimeEL").val(Date.format(m.lastModifyPasswordTime));
		$("#lastLoginIPEL").val(m.lastLoginIp);
		
		$("#createUserEL").val(m.createUser.name);
		$("#createTimeEL").val(Date.format(m.createTime));
	}
})();
