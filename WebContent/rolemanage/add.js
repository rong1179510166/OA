(function(){
	"use strict";
	
	var $addForm;
	var $submitBtn;
	
	$(function(){
		$addForm=$("#addForm");
		$submitBtn=$("#submitBtn");
		
		$submitBtn.click(handleSubmitBtnClick);
	});
	
	var handleSubmitBtnClick=function(evt){
		$submitBtn.disabled(true);
		var url="/api/system/role/add";		
		$.post(url,$addForm.serialize(),function(resp){
			if(resp.code==="ok"){
				toast("新增成功");
				if(!window.confirm("是否继续添加?")){
					location.href="list.html";
				}
			}else{
				toast(resp.message);
			}
		}).always(function(){
			$submitBtn.disabled(false);
		})
	};
	
	
})();
