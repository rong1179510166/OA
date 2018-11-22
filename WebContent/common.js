window.ctx="/oa";
	
var serverURL="http://10.25.208.100:8888"+window.ctx;

(function(window,$){
	$.ajaxPrefilter(function(settings,originalOptions,xhr){
		settings.url=window.serverURL+settings.url;
	});
})(window,$);

;+function ($) {
    $.fn.extend({
        checked: function() {
            if(this.length===0)return this;

            if(arguments.length===0){//getter
                return this[0].checked;
            }
            var yesno=arguments[0];
            return this.each(function() { this.checked = yesno; });
        },
        readonly:function () {
            if(this.length===0)return this;

            if(arguments.length===0){//getter
                return this[0].readonly;
            }
            var yesno=arguments[0];
            return this.each(function() { this.readonly = yesno; });
        },
        disabled:function () {
            if(this.length===0)return this;

            if(arguments.length===0){//getter
                return this[0].disabled;
            }
            var yesno=arguments[0];
            return this.each(function () {
                this.disabled=yesno;
            });
        }
    });
}(jQuery);
;+function ($) {
    //全局ajax设置
    $.ajaxSetup({
        dataType:"json",
        traditional:true,
        xhrFields:{
        	withCredentials:true
        },
        crossDomain:true
    });
    

    /**
     * 注册全局的ajax错误处理
     */
    $(document).ajaxError(function (event, jqXHR, ajaxSettings, thrownError) {
        console.log("ajax请求失败，详细原因是：");
        console.log(thrownError);
        window.top.toast("请求失败，请联系管理员");
    });
    /**
     * 统一处理业务异常
     */
    var handleAjaxFail=function (resp) {
        var m=resp.message;
        if(m){
            if(window.toast) {
                window.toast(m);
            }else{
                alert(m);
            }
        }
    };

    /***
     * 封装GET=load，POST=submit方法
     */
    $.load=function (url,data,done) {
        if(typeof data==="function"){
            done=data;
            data=null;
        }
        return $.getJSON(url,data,function (resp) {
            if(resp.code === "ok"||resp.ok){
                if(done){
                    done(resp.data);
                }
            }else{
                handleAjaxFail(resp);
            }
        });
    };
    
    $.put=function (url,data,done) {
        if(typeof data==="function"){
            done=data;
            data=null;
        }
        return $.post(url,data,function (resp) {
            if(resp.code === "ok"||resp.ok){
                if(done){
                    done(resp.data);
                }
            }else{
                handleAjaxFail(resp);
            }
        });
    };
   
}($);
;+function(window,document){
    
window.$$=(function(){
    var div=document.createElement("div");
    var $$=function(){
        if(arguments.length===0)return "";
        if(arguments.length===1){
            var v=arguments[0];
            if(typeof v==="undefined"||v===null||v===""){
                return "";
            }
            if(typeof v==="number"||typeof v==="boolean"){
                return String(v);
            }
            div.textContent=String(v);
            return div.innerHTML;
        }
        if(arguments.length===2){
            var current=arguments[1];
            if(!current){
                return $(arguments[0]);
            }
            var exps=arguments[0].split("\.");
            for(var i=0;i<exps.length;i++){
                current=current[exps[i]];
                if(typeof current==="undefined"||current===null){
                    current="";
                    break;
                }
            }
            return $$(current);
        }
    }
    return $$; 
})();

$.isBlank=function(s){
    if(s===null||typeof s==="undefined"){
        return true;
    }
    if(typeof s==="string"){
        return s.trim().length==0;  
    }
    return false;
};

Date.prototype.format = function (fmt) {
    fmt = fmt || "yyyy-MM-dd HH:mm:ss";
    var date = this;
    var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "H+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "S": date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    var k;
    for (k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

Date.format = function (d, fmt) {
    fmt=fmt||'yyyy-MM-dd HH:mm:ss';
    if (typeof d === "number") {
        return new Date(d).format(fmt);
    } else if (d instanceof Date) {
        return d.format(fmt);
    } else if(typeof d==="undefined"||d===null){
        return "";
    }else{
        return String(d);
    }
};

window.location.params=function(name){
    var result = window.location.search.match(new RegExp("(?:\\?|&)" + name + "=([^&]*)(?:[$#&]?)"));
    if (result)return decodeURI(result[1]);
    return null;
};

}(window,document);
;+function (window) {
    "use strict";
    //找到页面中所有Form表单
    window.addEventListener("load",function () {
       var forms=document.forms;
        for(var i=forms.length,form;i-->0;){
            form=forms[i];
            extendFormLabel(form);
            extendMethod2Form(form);
        }
    });

    /**
     * 查找网页中所有label，使其关联到后面的对应的input，textarea,select
     */
    var extendFormLabel=function (form) {
       //查找表单下的label
        var labels=form.getElementsByTagName("label");
        for(var i=labels.length,label;i-->0;){
            label=labels[i];
            //已关联的跳过
            if(!label.getAttribute("for")) {
                //如果label后面是input，textarea,select才处理
                var nextSiblingElement = label.nextElementSibling;
                if (nextSiblingElement) {
                    switch (nextSiblingElement.tagName) {
                        case "INPUT":
                        case "SELECT":
                        case "TEXTAREA":
                            //如果输入框已有id，则直接使用，否则使用其name属性
                            var targetId=nextSiblingElement.id;
                            if (!targetId) {
                                targetId= nextSiblingElement.name;
                                //如果name属性也不存在,自动生成一个
                                if(!targetId){
                                    targetId="el"+String(Math.random()).substr(2);
                                }
                            }
                            nextSiblingElement.id=targetId;
                            label.setAttribute("for", targetId);
                    }
                }
            }
        }
    };





    var extendMethod = {
        "checkRadio": function (radioName, checkValue) {
            var radios = this[radioName];
            if (radios) {
                checkValue = String(checkValue);
                
                for (var i = radios.length, r; i-- > 0;) {
                    if ((r = radios[i]).value === checkValue) {
                        if (!r.checked) {
                            r.checked = true;
                        }
                        return;
                    }
                }
            }
        }
    };
    var extendMethod2Form=function (form) {
        //extends
        var names = Object.getOwnPropertyNames(extendMethod);
        for(var name in extendMethod){
            if(extendMethod.hasOwnProperty(name)){
                form[name]=extendMethod[name];
            }
        }
    }

}(window);



;+function (window, document, undefined) {
    "use strict";
    var defaultSetting = {
        dom: null,
        activeListener: function () {

        }
    };

    var TabLayout = function (setting) {
        this.setting = $.extend({}, defaultSetting, setting);
        //the dom
        this.dom;
        //the tabs map
        this.tabsMap;

        this._activeTab = null;

        privateMethod.init.call(this);

    };
    var privateMethod = {
        init: function () {
            var setting = this.setting;
            var dom = setting.dom;
            if (typeof dom === "string") {
                dom = document.querySelector(dom);
            }

            var tabContainerEL = dom.querySelector(".tabs");
            //add click listener
            tabContainerEL.addEventListener("click", privateMethod.handleTabClick);

            var tabELs = tabContainerEL.getElementsByTagName("a");
            var tabsMap = {};
            var activeTab;
            for (var i = tabELs.length, tab; i-- > 0;) {
                tab = privateMethod.createTab.call(this, tabELs[i]);
                tabsMap[tab.id] = tab;
                if (tabELs[i].classList.contains("active")) {
                    activeTab = tab;
                }
            }
            this.tabsMap = tabsMap;
            this.dom = dom;
            //active Tab
            this.activeTab(activeTab);
        },
        createTab: function (tabEL) {
            var tab = new Tab(this, tabEL);
            var href = tabEL.href;
            tab.id = href.substr(href.lastIndexOf("#") + 1);
            tab.name = tabEL.innerHTML;
            var tabViewEL = document.getElementById(tab.id);
            if (tabViewEL) {
                this.tabView = tab.createCardView(tabViewEL);
            } else {
                console.error("can not find the tab view of id " + tab.id);
                return;
            }
            return tab;
        },
        handleTabClick: function (evt) {
            evt.preventDefault();

            var target = evt.target;
            if (target.tagName === "A") {
                var a = target;
                var tab = a.model;
                var tabLayout = tab.tabLayout;
                tabLayout.activeTab(tab);
            }
        }
    };
    //publicMethod
    TabLayout.prototype = {
        active: function (tabId) {
            var tab = this.tabsMap[tabId];
            if (tab) {
                this.activeTab(tab);
            } else {
                console.log("can't find the tab " + tabId);
            }
        },
        activeTab: function (tab) {
            if (arguments.length === 0)return this._activeTab;
            else {
                if (!tab)return;
                if (this._activeTab) {
                    this._activeTab.active(false);

                }
                tab.active(true);
                this._activeTab = tab;
                this.setting.activeListener.call(this, tab.dom, tab.cardView.dom);
            }
        }
    };

    var Tab = function (tabLayout, dom) {
        this.tabLayout = tabLayout;
        this.dom = dom;
        this.dom.model = this;
        this.id = null;
        this.name = null;
        this.cardView = null;
        this._active = false;
        //init

    };

    Tab.prototype = {
        active: function () {
            if (arguments.length === 0)return this._active;
            if (arguments[0] === true) {
                this.dom.classList.add("active");
                this.cardView.active(true);
                this._active = true;
            } else {
                this.dom.classList.remove("active");
                this.cardView.active(false);
                this._active = false;
            }
        },
        createCardView: function (dom) {
            var view = new CardView(this, dom);
            this.cardView = view;
            return view;
        }
    };
    var CardView = function (tab, dom) {
        this.tab = tab;
        this.dom = dom;
        this.tabLayout = tab.tabLayout;
    };
    CardView.prototype.active = function () {
        if (arguments[0] === true) {
            this.dom.classList.add("active");
        } else {
            this.dom.classList.remove("active");
        }
    };


    //export

    window.TabLayout = TabLayout;

}(window, document);
;+function (window, document) {
    window.addEventListener("load", function () {
        var tables = document.getElementsByClassName("layout-table");
        if (tables) {
            for (var i = tables.length, table; i-- > 0;) {
                table = tables[i];
                var theadColgroup = table.querySelector("header>table>colgroup");
                if(theadColgroup){
                    var tbodyColgroup = theadColgroup.cloneNode(true);
                    var tbodyTable = table.querySelector("main>table");
                    tbodyTable.appendChild(tbodyColgroup);
                }
            }
        }
    });


}(window, document);
;
+function(window, document) {
	window.addEventListener("load", function() {
		document.querySelectorAll("input[autosize]").forEach(function(inputEL) {
			inputEL.addEventListener("keydown", function(evt) {

				if (calculateStringPx(this) + 15 >= inputEL.offsetWidth) {
					// resize
					inputEL.style.width = (inputEL.offsetWidth * 2) + "px";
				}

			}, false);

		});
	});
	var calculateStringPx = function(input) {
		var style = window.getComputedStyle(input);
		var fontSize = style.fontSize;
		fontSize=parseInt(fontSize,10);
		var inputValue = input.value;
		return fontSize * inputValue.length;
	}

}(window, document);
//顶层窗口作为中转站
if (window === window.top) {
    window.children = [];
    window.registerBroadcast = function (w) {
        if(window.children.indexOf(w)===-1) {
            window.children.push(w);
        }
    }
} else {
    window.top.registerBroadcast(window);
}
//所有窗口监听广播
window.addEventListener("message", function (evt) {
    var origin = evt.origin || evt.originalEvent.origin;
    //是同源页面才处理
    if (origin !== window.location.origin) {
        return;
    }
    var data = evt.data;
    var from = evt.source;
    var messageType = data.type;
    var args = data.args;
    if (window.top === window) {
        //顶层窗口广播给其他子window
        window.children.forEach(function (w) {
            w.postMessage(data, "/");
        });
    }

    //广播回调
    var handlersMap=window.onbroadcast.handlersMap;
    if(handlersMap){
        var handlers=handlersMap[messageType];
        if(handlers){
            for(var i=0,z=handlers.length,handler;i<z;i++){
                try {
                    handlers[i].apply(window, args);
                }catch(e){
                    console.log(e);
                }
            }
        }
    }

}, false);
/**
 * 所有窗口广播注册
 */
window.onbroadcast=function (messageType,handler) {
    var handlersMap=window.onbroadcast.handlersMap;
    if(!handlersMap){
        handlersMap=window.onbroadcast.handlersMap={};
    }
    var handlers=handlersMap[messageType];
    if(handlers){
        handlers.push(handler);
    }else{
        handlers=handlersMap[messageType]=[handler];
    }
}
/**
 * 发送广播
 * @param type
 * @param args
 */
window.broadcast = function (type, args) {
    window.top.postMessage({
        type: type,
        args: Array.prototype.slice.call(arguments, 1)
    }, "/");
};
(function(window,document){
    window.addEventListener("load",function(){
        document.querySelectorAll(".dismissible-box").forEach(function(box){
              box.querySelectorAll(".dismiss-btn").forEach(function(btn){
                    btn.addEventListener("click",function(evt){
                        evt.preventDefault();
                        var targetBox=this.parentNode;
                        while(targetBox!==null&&!targetBox.classList.contains("dismissible-box")){
                            targetBox=targetBox.parentNode;
                        }
                        if(targetBox){
                            targetBox.parentNode.removeChild(targetBox);
                        }
                    })
               });
        });
    },false);

})(window,document);
;+function (window, document, undefined) {
    "use strict";
    window.addEventListener("load", function (evt) {
        var css = "";
        css += ".framewindow {" +
            "	position: fixed;" +
            "	left: 0;" +
            "	top: 0;" +
            "	width: 100vw;" +
            "	height: 100vh;" +
            "	background: #000;" +
            "	background-color:rgba(0,0,0,0.5);" +
            "	display: none;" +
            "	justify-content: center;" +
            "	align-items: center;" +
            "}";
        css += ".framewindow>div {" +
            "	background: #fff;" +
            "	border-radius: 5px;" +
            "	padding:5px;" +
            "}";

        css += "	.framewindow>div>iframe {" +
            "	border: 0;" +
            "	width: 100%;" +
            "	height: 100%;" +
            "}";

        css += "	.framewindow>div>a {" +
            "	position: absolute;" +
            "	top:5px;" +
            "	right:5px;" +
            "	font-size:50px;" +
            "	color: #fff;" +
            "	width:50px;" +
            "	text-align:center;" +
            "	cursor: pointer;" +
            "	text-decoration: none;" +
            "}";

        var style = document.createElement("style");
        style.setAttribute("type", "text/css");
        if (style.styleSheet) {// IE
            style.styleSheet.cssText = css;
        } else {// w3c
            style.appendChild(document.createTextNode(css));
        }
        var heads = document.getElementsByTagName("head");
        if (heads.length) {
            heads[0].appendChild(style);
        } else {
            document.documentElement.appendChild(style);
        }
    });
    var $ = function () {

    };
    $.extend = function () {
        var a = arguments, t = a[0], k, x, i = 1, z = a.length;
        while (i < z)if (x = a[i++])for (k in x)t[k] = x[k];
        return t;
    };

    var defaultSetting = {
        width: "80vw",
        height: "80vh",
        url: null,
        onclose: null,
        onload: null
    };

    //you can set min zIndex
    FrameWindow.currentZIndex = 999;
    /**
     * 构造器
     * @param setting
     */
    function FrameWindow(setting) {
        this.setting = $.extend({}, defaultSetting, setting);

        this.windowEL = null;
        this.frameEL = null;
        this.closeBtnEL = null;


        this.showing = false;

        //call method
        privateMethod.init.call(this);
    }

    var privateMethod = {
        init: function () {
            privateMethod.createView.call(this);
            privateMethod.addEventListener.call(this);
        },
        createView: function () {
            var setting = this.setting;

            //crate window box
            var windowbox = document.createElement('div');
            windowbox.style.width = setting.width;
            windowbox.style.height = setting.height;

            var closeBtnEL = document.createElement("a");
            closeBtnEL.innerHTML = "×";
            closeBtnEL.className = "closeBtn";
            windowbox.appendChild(closeBtnEL);


            var contentFrameEL = document.createElement("iframe");
            contentFrameEL.frameBorder = "0";
            windowbox.appendChild(contentFrameEL);

            var artical = document.createElement("article");
            artical.className = "framewindow";
            artical.style.zIndex = FrameWindow.currentZIndex++;
            artical.appendChild(windowbox);

            document.body.appendChild(artical);

            this.windowEL = artical;
            closeBtnEL.frameWindow = this;
            this.closeBtnEL = closeBtnEL;

            contentFrameEL.frameWindow = this;
            this.frameEL = contentFrameEL;
        },

        addEventListener: function () {
            //close btn
            this.closeBtnEL.addEventListener("click", function (evt) {
                var fw = this.frameWindow;
                fw.close();
            });

            this.frameEL.addEventListener("load", function (evt) {
                var fw = this.frameWindow;
                var win = this.contentWindow;
                var setting = fw.setting;
                //回调加载完成
                var onloadCallback = setting.onload;
                if (onloadCallback) {
                    onloadCallback.apply(win);
                }
                //添加窗口关闭监听
                win.close = function () {
                    fw.close();
                };
            });

        }


    };
    //public method
    FrameWindow.prototype = {
        open: function (url) {
            if (!this.showing) {
                this.windowEL.style.display = "flex";
                this.showing = true;
            }
            this.frameEL.src = url;
        },
        close: function () {
            if (this.showing) {
                this.windowEL.style.display = "none";
                this.showing = false;
                this.destroy();
            }
        },
        destroy: function () {
            this.frameEL = null;
            this.closeBtnEL = null;
            this.windowEL.parentNode.removeChild(this.windowEL);
        }
    };


    //expose
    window.FrameWindow = FrameWindow;

    window.addEventListener("load", function (evt) {
        var as = document.querySelectorAll("a[target='frameWindow']");
        for (var i = as.length; i-- > 0;) {
            as[i].addEventListener("click", function (evt) {
                evt.preventDefault();
                var fw = new FrameWindow();
                fw.open(this.href);
            });
        }
    });

}(window, window.document);
(function(window){
    
    window.list2tree = function (list,covertor) {
        covertor=covertor||function(n){return n.parentId;};
        //保存所有node的map
        var nodeMap = {};
        //保存所有根节点
        var roots = [];

        list.filter(function (item) {
            nodeMap[item.id] = item;
            if (item.__parentId=covertor(item)) {
                return true;
            } else {
                roots.push(item);
                return false;
            }
        }).forEach(function (item) {
            var parent = nodeMap[item.__parentId];
            if (parent.children) {
                parent.children.push(item);
            } else {
                parent.children = [item];
            }
        });
        return roots;
    };
})(window);
(function(window){
    window.logout=function(){
            var url = "/api/user/logout";
            return $.post(url, function(resp) {
                
            }).always(function(){
                sessionStorage.clear();
                window.top.location.href = "../login/view.jsp";    
            });
    }
})(window);
(function(window,document){
    
    window.shakeIt = function(e) {
        e=typeof e==="string"?document.querySelector(e):e;
        var offsets = [ 0, 2, -2, 4, -4, 8, -8, 16, -16 ];    
        offsets = offsets.concat(offsets, offsets);
        var run = function() {
            e.style.transform = "translate(" + offsets.pop() + "px,0px)";
            if (offsets.length) {
                setTimeout(run, 1000 / 30);
            }
        }
        run();
    };
    
})(window,document);
;
+function(window, document) {
    window.addEventListener("load", function() {
        var tables = document.getElementsByClassName("table");
        if (tables) {
            for (var i = tables.length, table; i-- > 0;) {
                table = tables[i];
                var theadColgroup = table.querySelector("header>table>colgroup");
                if (theadColgroup) {
                    var tbodyColgroup = theadColgroup.cloneNode(true);

                    var tbodyTable = table.querySelector("main>table");
                    tbodyTable.appendChild(tbodyColgroup);
                }
            }
        }
    });

}(window, document);
;+function () {
    "use strict";

    /**
     *
     *
     * @param {String} nameAndArgs 方法声明为xxx(a,b)
     * @param {String} templateHtml 字符串模板
     */
    var TemplateFunction =window.TemplateFunction= function (nameAndArgs, templateHtml) {
        //方法声明格式为xxx或xxx(a,b)
        this.nameAndArgs = nameAndArgs;
        //模板字符串
        this.templateSource = templateHtml;
        //模板方法名称
        this.functionName=null;
        //模板方法参数
        this.functionArgumentNames=null;
        //模板方法体
        this.body = [];
        //生成方法引用
        this.functionReference=null;

        privateMethod.init.apply(this);
    };
    //私有方法
    var privateMethod = {
        init: function () {
            //解析
            var result = staticMethod.parseFunctionNameAndArgumentNames(this.nameAndArgs);
            this.functionName = result[0];
            this.functionArgumentNames = result[1];
        }
    };
    //方法中的
    TemplateFunction.helper={
        print:function(s,escape/*=true*/){
            if(s===null||s===undefined)return '';
            escape=escape||true;
            if(escape) {
                return String(s).replace(/[<>&]/g, function (c) {
                    return {'<': '&lt;', '>': '&gt;', '&': '&amp;'}[c]
                })
            }
            return String(s);

        },
        checked:function (b) {
            return b?"checked":"";
        },
        selected:function (b) {
            return b?"selected":"";
        }
    };
    /**
     * 公开方法
     */
    TemplateFunction.prototype = {
        //构建模板方法
        build: function () {

            var out = this.body;
            //添加工具方法
            out.push("var helper=window.TemplateFunction.helper;");
            out.push("var $=helper.print;");
            out.push("var $checked=helper.checked;");
            out.push("var $selected=helper.selected;");
            out.push("var _='';\n");
            //添加try-finally保证模板方法返回值
            out.push("try{\n");
            out.push("\n");
            //构建方法体
            staticMethod.parse(this.templateSource, out);
            //添加finally保证模板方法返回值
            out.push("\n}catch(e){console.log(e)}finally{return _;}");

            var funBody = out.join("");
            var args = this.functionArgumentNames;
            try {
                //创建方法
                var f = args ? new Function(args, funBody) : new Function(funBody);
                this.functionReference=f;
                return f;
            } catch (e) {
                console.log(e);
                console.log(funBody);
            }
        },
        export: function (ctx) {
            ctx = ctx || window;
            if (!this.functionReference) {
                this.build();
            }
            ctx[this.functionName] = this.functionReference;
        }
    };

    var staticMethod = {
        parseFunctionNameAndArgumentNames: function (funName) {
            var begin = funName.indexOf("(");
            if (begin === -1) {
                return [funName, null];
            } else {
                return [funName.substring(0, begin), funName.substring(begin + 1, funName.lastIndexOf(")"))];
            }
        },
        parse: function (template, out) {
            /*
             × 不使用正则，原因如下：
             * 1)正则效率相对较低
             * ２）正则很难支持表达式嵌套，如$($(....));
             */
            var handleJs = staticMethod.parseJs;
            var handleHtml = staticMethod.parseHtml;
            var handleHtmlExp = staticMethod.parseHtmlExp;
            var handleJsExp=staticMethod.parseJsExp;
            var jsBegin = -1;
            var expBegin = -1;
            var htmlBegin = 0;
            var jsExpBegin=-1;
            var nestingExp = 0;
            for (var i = 0, z = template.length, c; i < z; i++) {
                switch (c = template.charAt(i)) {
                    case '<':
                        //may be js block
                        if (template.charAt(i + 1) === '-') {//yes we got a js block
                            //handle the previous content
                            if (htmlBegin !== i) {
                                handleHtml(template.substring(htmlBegin, i), out);
                            }
                            jsBegin = i + 2;
                            //jump the '-'
                            i++;
                        }
                        break;
                    case '-':
                        //may be js block end
                        if (template.charAt(i + 1) === '>') {
                            if (jsBegin > 0) {
                                handleJs(template.substring(jsBegin, i), out);
                                jsBegin = -1;
                                htmlBegin = i + 2;
                                //jump the '>'
                                i++;
                            }
                        }
                        break;
                    case '$':
                        //may be exp
                        if (template.charAt(i + 1) === '(') {
                            //handle the previous content
                            if (htmlBegin !== i) {
                                //in the js block
                                if(jsBegin>0){
                                    jsExpBegin=i+2;
                                    handleJs(template.substring(jsBegin,i),out);
                                    jsBegin=-1;
                                }else {
                                    expBegin = i + 2;
                                    handleHtml(template.substring(htmlBegin, i), out);
                                }
                            }

                            //jump the '('
                            i++;
                        }
                        break;
                    case '(':
                        //nesting exp
                        if (expBegin > 0||jsExpBegin>0) {
                            nestingExp++;
                        }
                        break;
                    case ')':
                        if (expBegin > 0) {
                            if (nestingExp > 0) {
                                nestingExp--;
                            } else {
                                //exp end
                                handleHtmlExp(template.substring(expBegin, i), out);
                                expBegin = -1;
                                htmlBegin = i + 1;
                            }
                        }else if(jsExpBegin>0){
                            if (nestingExp > 0) {
                                nestingExp--;
                            } else {
                                handleJsExp(template.substring(jsExpBegin, i), out);
                                jsExpBegin=-1;
                                jsBegin=i+1;
                            }
                        }
                        break;

                }
            }
            //如果最后还有html代码
            if (htmlBegin<template.length) {
                handleHtml(template.substr(htmlBegin), out);
            }
        },
        parseJs: function (code, out) {
            out.push(code);
        },
        parseHtmlExp: function (exp,out) {
            out.push("_+=$(" + exp + ");");
        },
        parseJsExp:function (exp,out) {
            out.push("_+=$(" + exp + ")");
        },
        parseHtml: function (html, out) {
            //不处理空白字符
            if ((html = html.trim()).length === 0) {
                return;
            }

            //双引号转义
            html = html.replace(/"/g, '\\"');
            //换行符分割
            var lines = html.split(/\r?\n/);
            if (lines.length > 0) {
                var i = 0, z = lines.length - 1;
                while (i < z) {
                    //一行一行输出,每行多输出一个换行
                    out.push('_+="' + lines[i++] + '";\n');
                }
                out.push('_+="' + lines[z] + '";');
            }
        }

    };
    window.addEventListener("load",function(){
      //通过<script type="text/tempate">标签构建模板方法
        var templates = document.querySelectorAll("script[type='text/template']");
        Array.prototype.forEach.call(templates, function (t) {
            var templateHTML = t.innerHTML;
            var name = t.getAttribute("name");
            if (!name) {
                name = t.id;
            }
           
            var tf = new TemplateFunction(name, templateHTML);
            //直接把模板id暴露为window下方法
            tf.export(window);
        });
    },false);
    
}(window, document);
(function (window, document) {
    "use strict";
    /**
     * how to :
     *
     * toast("message");//默认2000
     * toast("message",300);
     *
     */

    var defautSetting = {
        timeout: 2000,
        fadeIn: 500,
        fadeOut: 1000,
    };
	/**
	 * 创建toastview
	 */
    var toastEL = (function (setting) {

        var divCss = {
            position: "fixed",
            zIndex: 2147483647,
            bottom: "150px",
            background: "#000",
            color: "#fff",
            border: "1px solid #000",
            fontSize: "14px",
            padding: "5px 1em",
            borderRadius: "5px",
            boxShadow: "inset 0 1px 1px rgba(0, 0, 0, .075)",
            display:"none"
        };
        var toastEL = document.createElement("div");
        var k;
        for (k in divCss) {
            toastEL.style[k] = divCss[k];
        }
        window.addEventListener("load", function () {
            window.document.body.appendChild(toastEL);
        });
        return toastEL;
    })(defautSetting);


    //save all messages
    var messages = [];


    //显示消息
    var toastMessages = function () {
        if (messages.length === 0) {
        	toastEL.show=false;
        	toastEL.style.display="none";
            return;
        }
    	if(!toastEL.show){
    		toastEL.style.display="block";
    		toastEL.show=true;
		}

        var m = messages.shift();
        toastEL.textContent = m.message;
        //计算宽度
        var left=(document.body.offsetWidth-toastEL.offsetWidth)/2;
        if(left>0){
			toastEL.style.left=left+"px";
        }
        fadeIn(toastEL, defautSetting.fadeIn, function () {
            setTimeout(function () {
                fadeOut(toastEL, defautSetting.fadeOut, function () {
                    if (m.done) {
                       try{
                        	m.done.apply(window,m.message);
                       }catch(e){
                       	 console.log(e);
                       }
                    }
                    toastMessages();
                });
            },m.timeout);
        });
    };
    /**
     * 淡入
     * @param {Object} e 目标
     * @param {Object} t 时间
     * @param {Object} f 完成回调
     */
    var fadeIn = function (e, t, f) {
        e.style.opacity = 0;
        e.opacity = 0;
        var timeout = 30;//1000/30;
        //每次递增
        var increase = (100 * timeout / t) ^ 0;
        function run() {
            var next = e.opacity + increase;
            if (next >= 100) {
                e.style.opacity = 1;
                delete e.opacity;
                f();
                return;
            }
            e.opacity = next;
            e.style.opacity = next / 100;
            setTimeout(run, timeout);
        }

        run(timeout);
    };
    /**
     * 淡入
     * @param {Object} e 目标
     * @param {Object} t 时间
     * @param {Object} f 完成回调
     */
    var fadeOut = function (e, t, f) {
        e.style.opacity = 1;
        e.opacity = 100;
        var timeout = 30;//1000/30;
        //每次递增
        var increase = (100 * timeout / t) ^ 0;
        function run() {
            var next = e.opacity - increase;
            if (next <= 0) {
                e.style.opacity = 0;
                delete e.opacity;
                f();
                return;
            }
            e.opacity = next;
            e.style.opacity = next / 100;
            setTimeout(run, timeout);
        }

        run(timeout);
    };

    //expode
    window.toast = function () {
        var message, timeout=defautSetting.timeout, whenDone;
        for (var i = arguments.length, arg; i-- > 0;) {
            arg = arguments[i];
            if (typeof arg === "function") {
                whenDone = arg;
            } else if (typeof arg === "number") {
                timeout = arg;
            }else{
                message = arg;
            }
        }
        if(Array.isArray(message)){
            message=message.join("<br>");
        }
        if(message){
	        messages.push({message: message, timeout: timeout, done: whenDone});
	        toastMessages();
        }
    };

})(window, document);







;+function(window,document){
	var BLANK="　",DESCENDANT="│",LASTCHILD="└",CHILD="├";
	
	
	
	
	var OptionNode=function(pid,id,content,other){
		this.view=null;
		this.pid=pid;
		this.id=id;
		this.content=content;
		this.other=other;
		
		this.prefix=null;
		this.parent=null;
		this.children;
		this.isLastChild=false;
	};
	
	OptionNode.prototype={
		paint:function(selectEL){
			var optionEL=this.view;
			if(!this.view){
				optionEL=this.view=document.createElement("option");
			}
			optionEL.textContent=this.createPrefix()+this.content;
			for(var key in this.other){
				optionEL[key]=this.other[key];
			}
			selectEL.appendChild(optionEL);
			//paint Children
			var children=this.children;
			if(children){
				for(var i=0;i<children.length;i++){
					children[i].paint(selectEL);
				}
			}
			
		},
		createPrefix:function(){
			var prefix="";
			var parent=this.parent;
			if(parent){
				var parentPrefix=parent.prefix;
				if(parentPrefix){
					prefix+=parentPrefix.slice(0,-1);
				}
				//如果父节点是最后节点
				if(parent.isLastChild){
					prefix+=BLANK;
				}else{
					prefix+=DESCENDANT;
				}
			}
			
			//判断自己是否是最后节点
			if(this.isLastChild){
				prefix+=LASTCHILD;
			}else{
				prefix+=CHILD;
			}
			this.prefix=prefix;
			
			return prefix;
		},
		addChild:function(child){
			if(!this.children){
				this.children=[];
			}else{
				this.children[this.children.length-1].isLastChild=false;	
			}
			child.parent=this;
			child.isLastChild=true;
			this.children.push(child);
		}
	}
	
	
	var TreeSelect=function(selectEL){
		this.selectEL=selectEL;
		this.roots=[];
		this.nodeMap={};
		this.orphansMap={};
		
		TreeSelectInit.apply(this,arguments);
		
	};
	var TreeSelectInit=function(selectEL){
		if(typeof selectEL==="string"){
			this.selectEL=document.querySelector(selectEL);
		}else if(selectEL instanceof window["jQuery"]){
			this.selectEL=selectEL[0];
		}else{
			this.selectEL=selectEL;
		}
	}
	
	TreeSelect.prototype={
		insert:function(pid,id,value,content){
			var node=new OptionNode(pid,id,value,content);
			if(pid===null||typeof pid==="undefined"||pid===""){
				var rootsAmount=this.roots.length;
				if(rootsAmount>0){
					this.roots[rootsAmount-1].isLastChild=false;
				}
				node.isLastChild=true;
				this.roots.push(node);
			}else{
				var parent=this.nodeMap[pid];
				if(parent){
					parent.addChild(node);
				}else{
					//尝试在orphans中找
					this.orphansMap[id]=node;
				}
			}
			this.nodeMap[id]=node;
			return node;
		},
		done:function(){
			var roots=this.roots;
			for(var i=0;i<roots.length;i++){
				roots[i].paint(this.selectEL);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	window.TreeSelect=TreeSelect;
}(window,document);

(function(window,document){
	"use strict";
	/**
	 * how to user:
	 *
	 * url:/xx?unmout=admin,mod&...
	 * urk:/xx?mout=admin,mod&...
	 *
	 * <button class="admin">add</button>
	 * <button class="unmout mod">add</button>
	 *
	 */
	var UNMOUNT_VIEW_CLASS_PATTERN=/[?&]unmount=([^&]+).*$/;
	var MOUNT_VIEW_CLASS_PATTERN=/[?&]mount=([^&]+).*$/;

	var getUnmountViewClassArray=function(){
		var result=UNMOUNT_VIEW_CLASS_PATTERN.exec(window.location.href);
		if(result){
			return result[1].split(",");
		}
		return [];
	};
	var getMountViewClassArray=function(){
        var result=MOUNT_VIEW_CLASS_PATTERN.exec(window.location.href);
        if(result){
            return result[1].split(",");
        }
        return [];
    };
	var unmountViews=function(){
		var unmountViewClassArray=getUnmountViewClassArray();
		
		var unmountViews;
		for(var i=unmountViewClassArray.length;i-->0;){
			unmountViews=document.getElementsByClassName(unmountViewClassArray[i]);
			
			for(var j=unmountViews.length,v;j-->0;){
				//just delete it
				(v=unmountViews[j]).parentNode.removeChild(v);
			}
		}
	}
	var mountViews=function(){
        var mountViewClassArray=getMountViewClassArray();
        var mountViews;
        for(var i=mountViewClassArray.length;i-->0;){
            mountViews=document.getElementsByClassName(mountViewClassArray[i]);
            for(var j=mountViews.length,v;j-->0;){
                //just remove mount class
                (v=mountViews[j]).classList.remove("unmount");
            }
        }
    };
    var addUnmountClass=function(){
        var css=".unmount{display:none !important;}"
        var style = document.createElement("style");
        style.setAttribute("type", "text/css");
        if (style.styleSheet) {// IE
            style.styleSheet.cssText = css;
        } else {// w3c
            style.appendChild(document.createTextNode(css));
        }
        var heads = document.getElementsByTagName("head");
        if (heads.length) {
            heads[0].appendChild(style);
        } else {
            document.documentElement.appendChild(style);
        }
    }
	
	window.addEventListener("load",function(evt){
	    addUnmountClass();
	    mountViews();
		unmountViews();
	},false);






})(window,document);

;+function(window,document){
	
	var validateIDCard=function(idcard){
		if(idcard.length!==18)return false;
		//1-17位系数
		var MODULUS17=[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
		//求1-17位每一位和系数相乘之和
		var sum=0;
		for(var i=idcard.length-1;i-->0;){
			sum+=(parseInt(idcard.charAt(i),10))*MODULUS17[i];
		}
		//除11求余验证
		return "10X98765432".charAt(sum%11)==idcard.charAt(17);
	};
	
	
	window.Validate={
			isIDCard:validateIDCard
	};
	
}(window,document);
