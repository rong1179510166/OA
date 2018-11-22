(function(){
	"use strict";
	
	
	var $treeContainer;
	
	
	$(function(){
		$treeContainer=$("#functionalityTableTree");
		
		paintView();
		
	});
	
	var paintView=function(){
		var url="/api/system/module/list"
		$.get(url,function(resp){
			if(resp.code==="ok"){
				var data=resp.data;
				data=list2tree(data);
				
				paintTree(data);
			}
		});
	};
	
	var paintTree=function(data){
		$treeContainer.fancytree({
			icon : false,
            checkbox : false,
            selectMode : 1,
            minExpandLevel : 1,
            extensions : [ "table" ],
            table : {
                nodeColumnIdx : 1,
                indentation : 16
            },
			source:data,
			
			renderColumns:function(evt,evtData){
				//node对象
				var node=evtData.node;
				//我们传入的node模型
				var nodeData=node.data;
				//tr标签
				var tr=node.tr;
				var tds=tr.cells;

				//序号
 				tds[0].textContent = node.getIndexHier();
 				//name
 				//cells[1]
				//第3列-操作
				tds[2].innerHTML="<a href='check.html#"+nodeData.id+"'>查看</a>";
				//url
				var url=$$(nodeData.url);
				tds[3].textContent=url.substr(0,30)+"...";
				tds[3].title=url;
				//remrak
				tds[4].textContent=$$(nodeData.remark);
			}
		});
	}
	
	
	
	var list2tree=function (list) {
				//保存所有根节点
				var roots=[];
				//保存所有节点
				var nodeMap={};
				list.filter(function(node){
					node.key=node.id;
					node.title=node.name;
					nodeMap[node.id]=node;
					//如果父节点id未定义或未null,则表示未跟节点
					if(node.parentId===null||typeof node.parentId==="undefined"){
						roots.push(node);
						return false;
					}
					return true;
				}).forEach(function(node){
					//找到父节点
					var parent=nodeMap[node.parentId];
					if(parent){
						(parent.children=parent.children||[]).push(node);
					}else{
						//未找到父节点
						console.error("节点"+node+"未找到父节点");
					}
				});
				return roots;
			};
	
})();
