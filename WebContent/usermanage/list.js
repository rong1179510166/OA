(function(){
	"use strict";
	var $qryForm;
	var $resultContainer;
	var $totalRowsEL,$pageNoEL,$totalPagesEL,$pageSizeEL;
	var $paginationBoxEL;
	var $firstPageLink,$lastPageLink,$previousPageLink,$nextPageLink;
	$(function(){
		$qryForm=$("#qryForm");
		$resultContainer=$("#resultContainer");
		$totalRowsEL=$("#totalRowsEL");
		$pageNoEL=$("#pageNoEL");
		$totalPagesEL=$("#totalPagesEL");
		$pageSizeEL=$("#pageSizeEL");
		$paginationBoxEL=$("#paginationBoxEL");
		
		$firstPageLink=$("#firstPageLink");
		$lastPageLink=$("#lastPageLink");
		$previousPageLink=$("#previousPageLink");
		$nextPageLink=$("#nextPageLink");
		
		
		$qryForm.submit(handleFormSubmit).submit();
	});
	
	
	var handleFormSubmit=function(evt){
		var url="/api/system/user/list";
		$.get(url,$qryForm.serialize(),function(resp){
			if(resp.code==="ok"){
				var data=resp.data;
				if(data.totalRows>0){
					paintQueryResult(data);
				}else{
					paintNoResult();
				}
			}else{
				toast(resp.message);
			}
		});
		
		return false;
	};
	
	var paintQueryResult=function(data){
		var rows=data.rows;
		var html="";
		for(var i=0;i<rows.length;i++){
			html+=paintOneRow(rows[i],i+1);
		}
		$resultContainer.html(html);
		
		$firstPageLink[0].onclick=function(){
			goPage(1);
		};
		$previousPageLink[0].onclick=function(){
			goPage(data.pageNo-1);
		};
		$lastPageLink[0].onclick=function(){
			goPage(data.totalPages);
		};
		
		$nextPageLink[0].onclick=function(){
			goPage(data.pageNo+1);
		};
		if(data.pageNo===1){
			$firstPageLink[0].onclick=null;
			$previousPageLink[0].onclick=null;
		}else if(data.pageNo===data.totalPages){
			$lastPageLink[0].onclick=null;
			$nextPageLink[0].onclick=null;
		}
		
		
		$totalRowsEL.text(data.totalRows);
		$pageNoEL.text(data.pageNo);
		$totalPagesEL.text(data.totalPages);
		$pageSizeEL.text(data.pageSize);
		
		$paginationBoxEL.show();
	}
	
	var paintOneRow=function(row,count){
		var html="<tr>";
		html+='<td>'+count+'</td>';
		html+='<td>';
		html+="<a href='check.html?#"+row.id+"'>查看</a>";
		html+='</td>';
		html+='<td>'+$$(row.account)+'</td>';
		html+='<td>'+$$(row.name)+'</td>';
		html+='<td>'+$$(row.phone)+'</td>';
		html+='<td>'+paintUserState(row.flag)+'</td>';
		html+='<td>'+$$(row.remark)+'</td>';
		html+="</tr>";
		return html;
	}
	var paintUserState=function(flag){
		switch(flag){
		case 0:return "正常";
		case 1:return "锁定";
		
		}
	};
	var paintNoResult=function(){
		var html="<tr>";
		
		html+='<td colspan="999">';
		html+="很抱歉，未找到相关数据";
		html+="</td>"
		html+="</tr>"
		$resultContainer.html(html);
		$paginationBoxEL.hide();
	};
	
	
	var goPage=function(no){
		var form=$qryForm[0];
		form.pageNo.value=String(no);
		$qryForm.submit();
	}
	
	
})();