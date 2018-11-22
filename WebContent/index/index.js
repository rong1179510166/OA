
var $menuBox;

$(function(){
	$menuBox=$("#menuBox");
	
	
	paintAvatar();
	
	loadMenu();
	
});

var loadMenu=function(){
	var url="/api/user/loadMenu";
	$.get(url,function(resp){
		if(resp.code==="ok"){
			var menus=resp.data;
			menus=list2tree(menus);
			paintMenus(menus);
			addModuleLinkClickListener();
		}else{
			toast(resp.message);
		}
	});
};

var paintMenus=function(menus){
	var html="";
	for(var i=0;i<menus.length;i++){
		html+=paintMenu(menus[i]);
	}
	
	$menuBox.html($menuBox.html()+html);
};
var paintMenu=function(menu){
	var html='<li>';
	html+='<a id="'+menu.id+'"';
	html+='class="fa fa-folder group-row ">';
	html+='<span class="grow">&nbsp;'+menu.name+'</span>';
	html+='<i class="fa fa-angle-left"></i>';
	html+='</a>';
	var children=menu.children;
	if(children){
		html+='<ul class="sidebar-submenu">';
		for(var i=0;i<children.length;i++){
			html+=paintSubMenu(children[i]);
		}
		html+='</ul>';
	}
	html+='</li>';
	return html;
};

var paintSubMenu=function(m){
	var html="<li>";
	html+='<a data-folder-id="#'+m.parentId+'"';
	var url=m.url;
	url="/oa"+url;
	html+=' class="fa fa-link" href="'+url+'"';
	html+='	target="mainFrame">&nbsp;'+m.name+'</a>';
	html+="</li>";
	return html;
}



var list2tree=function (list) {
	//保存所有根节点
	var roots=[];
	//保存所有节点
	var nodeMap={};
	list.filter(function(node){
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

window.paintAvatar=function(){
	var currentUser=JSON.parse(window.sessionStorage.getItem("currentUser"));
	var avatar=currentUser.avatar;
	$("#userSmallAvatarEL").attr("src",serverURL+avatar+"?"+Math.random());
	$("#userBigAvatarEL").attr("src",serverURL+avatar+"?"+Math.random());
};
var addModuleLinkClickListener = function(evt) {
    $(".fa-link").click(function(evt) {
        //expand the folder if exists
        var folderId=this.getAttribute("data-folder-id");
        if(folderId){
            var folder=$(folderId);
            if(!folder.hasClass("expand")){
                folder.click();
            }
        }
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(".fa-folder").click(function() {
        //the arrow
        $(this).children().last()
        .toggleClass("fa-angle-left")
        .toggleClass("fa-angle-down");
        $(".expand").removeClass("expand").next().slideToggle();
        $(this).addClass("expand").next().slideToggle(1000);
    })
};
/**
 * 用于打开指定菜单,如openMenu("修改密码")
 *
 */
window.top.openMenu = function(menu,params) {
    if (menu === "首页") {
        document.getElementById("dashboardLinkEL").click();
        return;
    }
    menu=menu.trim();
    $(".fa-link",window.top.document).each(function(link) {
        if (this.textContent.trim() === menu) {
            this.setAttribute("params",params);
            this.click();
            return false;
        }
    });
}

window.top.dashboard = function() {
    window.top.openMenu('首页');
}


var exitSystem = function() {
    if (window.confirm("确认退出?")) {
       window.logout();
    }
}
