;+function(window){
  window.loadModuleTree=function(target,opts){
    $(target).fancytree($.extend({
        source : {
            url : "/api/system/module/list",
            cache : true
        },
        postProcess : function(evt, data) {
            var resp = data.response;
            if (resp.code === "ok") {
                data.result = covertFlatList2Tree(resp.data);
            } else {
                alert(resp.message);
            }
        },
        icon:false,
        checkbox : true,
        selectMode : 3,
        minExpandLevel:1,
        strings : {
            loading : "加载中...请稍后",
            loadError : "加载数据异常，请联系管理员",
            moreData : "More...",
            noData : "无功能数据"
        },
        init :function(evt, ctx, flag) {
            var tree = ctx.tree;
        },
        activate : function(evt, ctx) {
            var node = ctx.node;
            node.setSelected(true);
        },
        deactivate : function(evt, ctx) {
            var node = ctx.node;
            node.setSelected(false);
        },
        select : function(evt, ctx) {
            var node = ctx.node;
        }
    },opts||{}));
  };
    
    var covertFlatList2Tree = function(list) {
        //保存所有node的map
        var nodeMap = {};
        //保存所有根节点
        var roots = [];
        //保存
        list.filter(function(item) {
            item.key = item.id;
            item.title = item.name;
            nodeMap[item.id] = item;
            if (item.parentId) {
                return true;
            } else {
                roots.push(item);
                return false;
            }
        }).forEach(function(item) {
            var parent = nodeMap[item.parentId];
            
            if (parent.children) {
                parent.children.push(item);
            } else {
                parent.children = [ item ];
            }
        });
        return roots;
    };
}(window,document);