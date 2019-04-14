(function(window) {
	var xclk = new Object();
	var xres = new Object();
/**
 * http://xres.xiu.com:8088/xclk/ 
 * http://xres.xiu.com:8088/static/xclk-fcf.swf 
 * http://xres.xiu.com:8088/static/xclk-fcf 
 * http://xres.xiu.com
 * 
 */
	xclk.server = deCode("104 116 116 112 58 47 47 120 114 101 115 46 120 105 117 46 99 111 109 58 56 48 56 56 47 120 99 108 107 47");
	xclk.swfUrl= deCode("104 116 116 112 58 47 47 120 114 101 115 46 120 105 117 46 99 111 109 58 56 48 56 56 47 115 116 97 116 105 99 47 120 99 108 107 45 102 99 102 46 115 119 102");
	xclk.swfUrlHome = deCode("104 116 116 112 58 47 47 120 114 101 115 46 120 105 117 46 99 111 109 58 56 48 56 56 47 115 116 97 116 105 99 47 120 99 108 107 45 102 99 102");
	xres.server = deCode("104 116 116 112 58 47 47 120 114 101 115 46 120 105 117 46 99 111 109");
	
	xclk.VO = VO;
	xclk.FB = FB;
	xclk.FbDetail = FbDetail;
	xclk.PI = PI;
	xclk.OrderDetail = OrderDetail;
	xclk.SK = SK;
	xclk.EO = {};
	xclk.invoke = invoke;
	window.xclk = xclk;
	
	xres.RecommendVAV = RecommendVAV;
	xres.RecommendByViewUltiBought = RecommendByViewUltiBought;
	xres.RecommendByGuessYouLike = RecommendByGuessYouLike;
	xres.RecommendByOfficialList = RecommendByOfficialList;
	xres.RecommendByCatBrowsingList = RecommendByCatBrowsingList;
	xres.RecommendByBrandBrowsingList = RecommendByBrandBrowsingList;
	xres.invoke = invoke;
	window.xres = xres;
	
	var xresUID = "";
	var doneGet = false;
	var isfired = false;
	var fcInd = 0;
	//xclkCallback 
	window[deCode("120 99 108 107 67 97 108 108 98 97 99 107")] = function(data) {
	};
	
	function VO() {
		this.uri = "vo";
		this.user_id = null;
		this.obj_id = null;
		this.obj_type = null;
		this.x_price = null;
		this.category_id = null;
		this.brand_id = null;
		this.ref_page_id = null;
		this.scene_id = null;
		this.algorithm_ind = null;
	}
	
	function FB() {
		this.uri = "fb";
		this.user_id = null;
		this.fb_type = null;
		this.fb_detail = null;
	}
	
	function FbDetail() {
		this.item_id = null;
		this.x_price = null;
		this.category_id = null;
		this.brand_id = null;
	}
	
	function PI() {
		this.uri = "pi";
		this.user_id = null;
		this.order_id = null;
		this.order_detail = null;
	}
	
	function OrderDetail() {
		this.item_id = null;
		this.category_id = null;
		this.brand_id = null;
		this.sku_id = null;
		this.color = null;
		this.size = null;
		this.quantity = null;
		this.x_price = null;
		this.disc_price = null;
	}
	
	function SK() {
		this.uri = "sk";
		this.user_id = null;
		this.key_words = null;
		this.result_ind = null;
	}
	
	function getInvokeURL(action){
		var url = xclk.server + action.uri + "?" + "data={\"xres_uid\"" + ":\"" + xresUID +"\",";
		delete action.uri;
		url += JSON.stringify(action).replace(/^{/, "");
		return url;
	}
	
	//Copyright 2005-2008 Adobe Systems Incorporated.  All rights reserved.
	var isIE = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
	var isWin = (navigator.appVersion.toLowerCase().indexOf("win") != -1) ? true
			: false;
	var isOpera = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;
	function ControlVersion() {
		var version;
		var axo;
		var e;
		try {
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
			version = axo.GetVariable("$version");
		} catch (e) {
		}
		if (!version) {
			try {
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
				version = "WIN 6,0,21,0";
				axo.AllowScriptAccess = "always";
				version = axo.GetVariable("$version");
			} catch (e) {
			}
		}
		if (!version) {
			try {
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3");
				version = axo.GetVariable("$version");
			} catch (e) {
			}
		}
		if (!version) {
			try {
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3");
				version = "WIN 3,0,18,0";
			} catch (e) {
			}
		}
		if (!version) {
			try {
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
				version = "WIN 2,0,0,11";
			} catch (e) {
				version = -1;
			}
		}
		return version;
	}
	function GetSwfVer() {
		var flashVer = -1;
		if (navigator.plugins != null && navigator.plugins.length > 0) {
			if (navigator.plugins["Shockwave Flash 2.0"]
					|| navigator.plugins["Shockwave Flash"]) {
				var swVer2 = navigator.plugins["Shockwave Flash 2.0"] ? " 2.0" : "";
				var flashDescription = navigator.plugins["Shockwave Flash" + swVer2].description;
				var descArray = flashDescription.split(" ");
				var tempArrayMajor = descArray[2].split(".");
				var versionMajor = tempArrayMajor[0];
				var versionMinor = tempArrayMajor[1];
				var versionRevision = descArray[3];
				if (versionRevision == "") {
					versionRevision = descArray[4];
				}
				if (versionRevision[0] == "d") {
					versionRevision = versionRevision.substring(1);
				} else {
					if (versionRevision[0] == "r") {
						versionRevision = versionRevision.substring(1);
						if (versionRevision.indexOf("d") > 0) {
							versionRevision = versionRevision.substring(0,
									versionRevision.indexOf("d"));
						}
					}
				}
				var flashVer = versionMajor + "." + versionMinor + "."
						+ versionRevision;
			}
		} else {
			if (navigator.userAgent.toLowerCase().indexOf("webtv/2.6") != -1) {
				flashVer = 4;
			} else {
				if (navigator.userAgent.toLowerCase().indexOf("webtv/2.5") != -1) {
					flashVer = 3;
				} else {
					if (navigator.userAgent.toLowerCase().indexOf("webtv") != -1) {
						flashVer = 2;
					} else {
						if (isIE && isWin && !isOpera) {
							flashVer = ControlVersion();
						}
					}
				}
			}
		}
		return flashVer;
	}
	function DetectFlashVer(reqMajorVer, reqMinorVer, reqRevision) {
		versionStr = GetSwfVer();
		if (versionStr == -1) {
			return false;
		} else {
			if (versionStr != 0) {
				if (isIE && isWin && !isOpera) {
					tempArray = versionStr.split(" ");
					tempString = tempArray[1];
					versionArray = tempString.split(",");
				} else {
					versionArray = versionStr.split(".");
				}
				var versionMajor = versionArray[0];
				var versionMinor = versionArray[1];
				var versionRevision = versionArray[2];
				if (versionMajor > parseFloat(reqMajorVer)) {
					return true;
				} else {
					if (versionMajor == parseFloat(reqMajorVer)) {
						if (versionMinor > parseFloat(reqMinorVer)) {
							return true;
						} else {
							if (versionMinor == parseFloat(reqMinorVer)) {
								if (versionRevision >= parseFloat(reqRevision)) {
									return true;
								}
							}
						}
					}
				}
				return false;
			}
		}
	}
	function AC_AddExtension(src, ext) {
		if (src.indexOf("?") != -1) {
			return src.replace(/\?/, ext + "?");
		} else {
			return src + ext;
		}
	}
	function AC_Generateobj(objAttrs, params, embedAttrs) {
		var str = "";
		if (isIE && isWin && !isOpera) {
			str += "<object ";
			for ( var i in objAttrs) {
				str += i + '="' + objAttrs[i] + '" ';
			}
			str += ">";
			for ( var i in params) {
				str += '<param name="' + i + '" value="' + params[i] + '" /> ';
			}
			str += "</object>";
		} else {
			str += "<embed ";
			for ( var i in embedAttrs) {
				str += i + '="' + embedAttrs[i] + '" ';
			}
			str += "> </embed>";
		}
		var flashbox = document.createElement("DIV");
		flashbox.style.zIndex = 1;
		flashbox.style.position = "absolute";
		document.body.insertBefore(flashbox, null);
		flashbox.innerHTML = str;
	}
	function AC_FL_RunContent() {
		var ret = AC_GetArgs(
				arguments,
				".swf",
				"movie",
				"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000",
				"application/x-shockwave-flash");
		AC_Generateobj(ret.objAttrs, ret.params, ret.embedAttrs);
	}
	function AC_SW_RunContent() {
		var ret = AC_GetArgs(arguments, ".dcr", "src",
				"clsid:166B1BCA-3F9C-11CF-8075-444553540000", null);
		AC_Generateobj(ret.objAttrs, ret.params, ret.embedAttrs);
	}
	function AC_GetArgs(args, ext, srcParamName, classid, mimeType) {
		var ret = new Object();
		ret.embedAttrs = new Object();
		ret.params = new Object();
		ret.objAttrs = new Object();
		for ( var i = 0; i < args.length; i = i + 2) {
			var currArg = args[i].toLowerCase();
			switch (currArg) {
			case "classid":
				break;
			case "pluginspage":
				ret.embedAttrs[args[i]] = args[i + 1];
				break;
			case "src":
			case "movie":
				args[i + 1] = AC_AddExtension(args[i + 1], ext);
				ret.embedAttrs.src = args[i + 1];
				ret.params[srcParamName] = args[i + 1];
				break;
			case "onafterupdate":
			case "onbeforeupdate":
			case "onblur":
			case "oncellchange":
			case "onclick":
			case "ondblclick":
			case "ondrag":
			case "ondragend":
			case "ondragenter":
			case "ondragleave":
			case "ondragover":
			case "ondrop":
			case "onfinish":
			case "onfocus":
			case "onhelp":
			case "onmousedown":
			case "onmouseup":
			case "onmouseover":
			case "onmousemove":
			case "onmouseout":
			case "onkeypress":
			case "onkeydown":
			case "onkeyup":
			case "onload":
			case "onlosecapture":
			case "onpropertychange":
			case "onreadystatechange":
			case "onrowsdelete":
			case "onrowenter":
			case "onrowexit":
			case "onrowsinserted":
			case "onstart":
			case "onscroll":
			case "onbeforeeditfocus":
			case "onactivate":
			case "onbeforedeactivate":
			case "ondeactivate":
			case "type":
			case "codebase":
			case "id":
				ret.objAttrs[args[i]] = args[i + 1];
				break;
			case "width":
			case "height":
			case "align":
			case "vspace":
			case "hspace":
			case "class":
			case "title":
			case "accesskey":
			case "name":
			case "tabindex":
				ret.embedAttrs[args[i]] = ret.objAttrs[args[i]] = args[i + 1];
				break;
			default:
				ret.embedAttrs[args[i]] = ret.params[args[i]] = args[i + 1];
			}
		}
		ret.objAttrs.classid = classid;
		if (mimeType) {
			ret.embedAttrs.type = mimeType;
		}
		return ret;
	}
	function enCode(str) {
		var result = "";
		var count = str.length;
		if (result == 0 && count > 0) {
			var offset = 0;
			for ( var i = 0; i < count; i++) {
				if (i == 0) {
					result = str.charCodeAt(offset++);
				}else{
					result = result + " " + str.charCodeAt(offset++);
				}
			}
		}
		return result;
	}
	
	function deCode(code) {
		var str = "";
		var codeArray = code.split(" ");
		for ( var i = 0; i < codeArray.length; i++) {
			if (codeArray[i]) {
				str += String.fromCharCode(codeArray[i]);
			}
		}
		return str;
	}
	
	function hashCode(str) {
		var hash = 0;
		var count = str.length;
		if (hash == 0 && count > 0) {
			var offset = 0;
			for ( var i = 0; i < count; i++) {
				hash = 31*hash + str.charCodeAt(offset++);
			}
		}
		return hash;
	}
	
	function rdStr(length) {
		var rdStr = "";
		var flag = 0;
		for (var i = 0; i < length; i++){
			flag = Math.ceil(Math.random()*3);
			switch(flag){
				case 0:
				case 1:
					rdStr += String.fromCharCode(Math.ceil(Math.random()*25) + 97);
					break;
				case 2:
					rdStr += String.fromCharCode(Math.ceil(Math.random()*25) + 65);
					break;
				case 3:
					rdStr += Math.ceil(Math.random()*9);
					break;
			}			
		}
		return rdStr;
	}
	
	function setHttpCookie(val){
		var exprdate = new Date();
		var interval = 365 * 24 * 60 * 60 * 1000;
		exprdate.setTime(exprdate.getTime() + interval);
		document.cookie = "XResUID=" + encodeURIComponent(val) + ";expires="
				+ exprdate.toGMTString() + ";path=/;domain=.xiu.com";
	}
	
	function getHttpCookie(){
		var carray = document.cookie.match(new RegExp("(^| )XResUID=([^;]*)(;|$)"));
		if (carray != null) {
			xresUID = decodeURIComponent(carray[2]);
		}else {
			xresUID = "";
		}
	}
	
	function getFlashCookie(){
		if (!DetectFlashVer(10, 0, 0)) {
			xresUID = "";
		} else {
			try {
    			AC_FL_RunContent(
    				    "codebase",
    				    "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0",
    				    "width",
    				    "0",
    				    "height",
    				    "0",
    				    "src",
    				    xclk.swfUrl,
    				    "quality",
    				    "high",
    				    "pluginspage",
    				    "http://www.adobe.com/go/getflashplayer_cn",
    				    "align",
    				    "middle",
    				    "play",
    				    "true",
    				    "loop",
    				    "true",
    				    "scale",
    				    "showall",
    				    "wmode",
    				    "window",
    				    "devicefont",
    				    "false",
    				    "id",
    				    "xclk_fcf",
    				    "bgcolor",
    				    "#ffffff",
    				    "name",
    				    "xclk_fcf",
    				    "menu",
    				    "true",
    				    "allowFullScreen",
    				    "102 97 108 115 101",
    				    "allowScriptAccess",
    				    "always",
    				    "movie",
    				    xclk.swfUrlHome,
    				    "salign",
    				    "");
    			fcInd = fcInd + 1;
			}
			catch(e) {xresUID = "";}
		}
	}
	
	function getXresuid(){
		getHttpCookie();
		if (xresUID == "") {
			getFlashCookie();
			if (fcInd != 1) {
				xresUID = rdStr(20);
				setHttpCookie(xresUID);
				doneGet = true;
			}
		} else {
			doneGet = true;
		}
		return xresUID;
	}
	
	function addEvent(target, eventName, callbackHander) {
		if (target.attachEvent) {
			target.attachEvent("on" + eventName, callbackHander);
		} else {
			target.addEventListener(eventName, callbackHander, false);
		}
	}

	var fired = function() {
		if (!isfired) {
			isfired = true;
			var counter = 0;
			var intervalId = setInterval(function() {
				counter++;
				if (typeof (window.xclk.onLoad) == "function") {
					window.xclk.onLoad();
					clearInterval(intervalId);
				}
				if (counter >= 50){
					try{
						clearInterval(intervalId);
					}
					catch(e){
					}
				}
			}, 100);
		}
	};
	
	window[deCode("102 108 97 115 104 67 111 109 112 108 101 116 101 67 97 108 108 66 97 99 107")] = function() {
		xresUID = document.xclk_fcf.getFc();
		setHttpCookie(xresUID);
		doneGet = true;
		fired();
	};
	
	addEvent(window, "load", function() {
		getXresuid();
		if (fcInd == 0){
			fired();
		} else {
			setTimeout(function() {
				fired();
			}, 10000);
		}
	});

	function invoke(action) {
		try{
			if (doneGet == false) {
				getXresuid();
			}
			var url;
			if(action instanceof Recommend){
				if(action.userId === ""){
					action.userId = xresUID;
				}
				url = action.getUrl();
			}else{
				url = getInvokeURL(action);
			}
			var script = document.createElement('script');
			script.setAttribute('src', url);
			script.setAttribute("charset", "utf-8");
			document.getElementsByTagName('head')[0].appendChild(script);
		}
		catch(e){
		}		
	}
	
	function Recommend(callback){
		this.siteId = "";
		this.channelId = "";
		this.userId = "";
		this.callback = function(){
			if (callback === undefined || callback === null) {
				return "xclkCallBack";
	        }else{
	        	return callback;
	        }
		};
	}
	function RecommendVAV(callback){
		this.productId = "";
		this.catId = "";
		this.num = 10;
		this.uri = "/recommend/vav";
		Recommend.call(this,callback);
	}
	RecommendVAV.prototype = new Recommend(this.callback);
	RecommendVAV.prototype.getUrl = function(){
		return xres.server + this.uri+"?channelId="+this.channelId+"&userId="+this.userId+"&productId="+this.productId+"&catId="+this.catId+"&num="+this.num+"&callback="+this.callback();
	}
	function RecommendByViewUltiBought(callback){
		this.productId = "";
		this.uri = "/recommend/viewUltimateBought";
		Recommend.call(this,callback);
	}
	RecommendByViewUltiBought.prototype = new Recommend(this.callback);
	RecommendByViewUltiBought.prototype.getUrl = function(){
		return xres.server + this.uri +"?productId="+this.productId+"&num="+this.num+"&callback="+this.callback();
	}
	function RecommendByGuessYouLike(callback){
		this.catId = "";
		this.brandId = "";
		this.uri = "/recommend/guessYouLike";
		Recommend.call(this,callback);
	}
	RecommendByGuessYouLike.prototype = new Recommend(this.callback);
	RecommendByGuessYouLike.prototype.getUrl = function(){
		return xres.server + this.uri+"?channelId="+this.channelId+"&userId="+this.userId+"&catId="+this.catId+"&brandId="+this.brandId+"&num="+this.num+"&callback="+this.callback();
	}
	function RecommendByOfficialList(callback){
		this.uri = "/recommend/officialList";
		this.productList = "";
		this.brandId = "";
		this.catId = "";
		Recommend.call(this,callback);
	}
	RecommendByOfficialList.prototype = new Recommend(this.callback);
	RecommendByOfficialList.prototype.getUrl = function(){
		return xres.server + this.uri+"?channelId="+this.channelId+"&userId="+this.userId+"&productList="+this.productList+"&brandId="+this.brandId+"&catId="+this.catId+"&num="+this.num+"&callback="+this.callback();
	}
	function RecommendByCatBrowsingList(callback){
		this.catIdList = "";
		this.uri = "/recommend/catBrowsingList";
		Recommend.call(this,callback);
	}
	RecommendByCatBrowsingList.prototype = new Recommend(this.callback);
	RecommendByCatBrowsingList.prototype.getUrl = function(){
		return xres.server + this.uri+"?channelId="+this.channelId+"&catIdList="+this.catIdList+"&num="+this.num+"&callback="+this.callback();
	}
	function RecommendByBrandBrowsingList(callback){
		this.brandId = "";
		this.catId = "";
		this.uri = "/recommend/brandBrowsingList";
		Recommend.call(this,callback);
	}
	RecommendByBrandBrowsingList.prototype = new Recommend(this.callback);
	RecommendByBrandBrowsingList.prototype.getUrl = function(){
		return xres.server + this.uri+"?channelId="+this.channelId+"&brandId="+this.brandId+"&catId="+this.catId+"&num="+this.num+"&callback="+this.callback();
	}
})(window);
