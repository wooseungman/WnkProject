var common = new Object();
/*
 * ################# CONSTANTS ####################
 */

/*
 * common
 */
/**
 * @see main.jsp
 */
common.CONTEXT_PATH = '';
/*
 * for ajax form
 */
common.SUCCESS_FLAG = 'SUCCESS_FLAG';
common.URL_FOR_REDIRECT = 'URL_FOR_REDIRECT';
common.MSG_FOR_ALERT = 'MSG_FOR_ALERT';

/**
 * common jquery plugin
 */
(function($){
		/**
		 * 일반 타입의 폼을 ajaxForm으로 변환
		 * @param callback callback function
		 */
		$.fn.prepareAsyncSubmit = function(callback,_beforeSubmit){
			var _this = this;
			var options = {
					beforeSubmit : function(){
						try{
							if(_beforeSubmit() == false){
								return false;
							}
						}catch(e){
							//ignore
						}
						return validation.validate(_this);
						},
					inheritedCallback : callback,
					success: common.asyncResponseHandler
					};
			this.ajaxForm(options);
		};
		/**
		 * textarea의 line을 제한한다. 크롬과 익스플로러의 개행문자 처리가 다른거 같음.
		 * @param limit 라인 수
		 */
		$.fn.resolveLineLimit = function(limit){
			var viewTextTmp = $(this).text().split(/\n(?:\r)?/);
			var viewText = '';

			_limit = viewTextTmp.length < limit ? viewTextTmp.length : limit;
			for(var i=0;i<_limit;i++){
				viewText += viewTextTmp[i] + '\r';
			}
			if(viewTextTmp.length>=limit){
				viewText += '...';
			}
			$(this).text(viewText);
		};
})(jQuery);

/*
 * ####################################################################################
 * 		global functions
 * ####################################################################################
 */

/**
 * CONTEXT PATH가 붙은 URI를 반환한다.
 * @param uri uri
 */
common.getUri = function(uri){
	return common.CONTEXT_PATH + '/' + uri.replace(/^\//,'');
};

/**
 * move page
 */
common.goLocationUri = function(uri){
	window.location.href=uri;
};

/**
 * 서버측에 AsyncResponseMap 타입의 callback을 처리할 수 있는 요청을 전송한다.
 * @see indvmemodiryForm.jsp
 * @param url Controller url
 */
common.asyncRequest = function(uri,json,callback){
	$.post(uri, json || {}, function(json,status, xhr,inheritedCallback){common.asyncResponseHandler(json,status, xhr,callback);});
};

common.parseJson = function(json){
	return JSON.parse(json.replace(/<pre>(.+)<\/pre>$/gi,'$1'));
};


/**
 * @param strParamName key
 */
common.getParameter = function(strParamName) {
	var arrResult = null;
	if (strParamName)
		arrResult = location.search.match(new RegExp("[&?]" + strParamName+"=(.*?)(&|$)"));
	return arrResult && arrResult[1] ? arrResult[1] : null;
 };

 /**
  * get JSON from parameters in uri
  */
common.createObjFromURI = function(uri) {
	uri = uri.replace(/^.+\?/, '');
	var chunks = uri.split('&');
	var params = Object();

    for (var i=0; i < chunks.length ; i++) {
        var chunk = chunks[i].split('=');
        if(chunk[0].search("\\[\\]") !== -1) {
            if( typeof params[chunk[0]] === 'undefined' ) {
                params[chunk[0]] = [chunk[1]];

            } else {
                params[chunk[0]].push(chunk[1]);
            }


        } else {
            params[chunk[0]] = chunk[1];
        }
    }

    return params;
};

/**
 * json 객체를 지정 폼에 populate 한다.
 * @param params json 객체
 * @param frm jQuery형식의 폼 객체
 */
common.populateJson = function(params,frm){
	$.each(params, function(key, value){
		var _input = $('<input type="hidden"/>');
		_input.attr('name',key);
		_input.attr('value',value);
		frm.append(_input);
	});
};


$(document).ready(function(){
	common.prepareDocumentReady();

});

common.prepareDocumentReady = function(){
	/**
	 * AJAX FORM
	 */
	$('form[data-async-form]').each(function(key,target){
		$(target).prepareAsyncSubmit(
			function(){return eval($(target).attr('data-async-callback'));},
			function(){return eval($(target).attr('data-beforesubmit'));}
		);
	});

};


/*
 * ####################################################################################
 * 		private functions
 * ####################################################################################
 */
common.asyncResponseHandler = function(json,status, xhr,inheritedCallback){
	//json = common.parseJson(json);
	if(StringUtil.isEmpty(json[common.MSG_FOR_ALERT]) == false){
		//var success = json[common.SUCCESS_FLAG] == null || json[common.SUCCESS_FLAG] == true;
		alert(json[common.MSG_FOR_ALERT]);
		common.asyncResponseRedirector(json,inheritedCallback);
	} else {
		common.asyncResponseRedirector(json);
	}
};

common.asyncResponseRedirector = function(json,inheritedCallback){
	if(typeof inheritedCallback == 'function'){
		inheritedCallback();
	}
	if(StringUtil.isEmpty(json[common.URL_FOR_REDIRECT]) == false){
		
		// java단에서 Redirect시 파라미터를 post로 변경하기 위해 파라메터가 있는 경우 form을 생성하여 submit 해줌.
		var submitRedirect = document.createElement("form");
		submitRedirect.action = json[common.URL_FOR_REDIRECT]; 
		submitRedirect.method = "post";
		submitRedirect.id ="redirectForm";
		submitRedirect.style.display ='none';
		
		var jsonCount = 0;
		$.each(json,function(key,val){
			if(key!="URL_FOR_REDIRECT"&&key!="MSG_FOR_ALERT"&&key!="SUCCESS_FLAG"){
				jsonCount = jsonCount+1;
				
				var jsonHidden = document.createElement("input");
				jsonHidden.type = "hidden";
				jsonHidden.value = val;
				jsonHidden.name = key;
				submitRedirect.appendChild(jsonHidden);
			}
		});
		
		if(jsonCount==0){
			location.href=json[common.URL_FOR_REDIRECT];
		}else{
			$("#container").append(submitRedirect);
			$("#redirectForm").submit();
		}
	}
};

/**
 * jquery.form.js를 이용한 ajax submit
 */
common.submit = function(elementID, callbackMethod){
	//비동기 파일 전송
	var frm = $('#' + elementID);
	if(common.acceptFileCheck(elementID) == false) return false;
	var checkYN = validation.validate(frm);
	if(checkYN){
		if(callbackMethod != null){
			frm.ajaxForm({
				dataType : "json" ,
				success : callbackMethod ,
				error : function(xhr, status, exception,exMap){
		        	var ex = jQuery.parseJSON(xhr.responseText);
		        	alert(ex.MESSAGE + "\n ("+status+")");
					return false;
		        }
			});
		}else{
			frm.ajaxForm({
				dataType : "json" ,
				success : common.ajaxResponse ,
				error : function(xhr, status, exception,exMap){
		        	var ex = jQuery.parseJSON(xhr.responseText);
		        	alert(ex.MESSAGE + "\n ("+status+")");
					return false;
		        }
			});
		}
		frm.submit();
	}
};

/**
 * ajax submit
 */
common.ajaxCallbackSubmit = function(url, params, callbackMethod){

	if(callbackMethod != null){
		$.ajax({
	        type : "POST",
	        url : url,
	        data : params,
	        dataType : "json",
	        success : callbackMethod,
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        error : function(xhr, status, exception,exMap){
	        	var ex = jQuery.parseJSON(xhr.responseText);
	        	alert(ex.MESSAGE + "\n ("+status+")");
				return false;
	        }
	    });
	}else{
		$.ajax({
	        type : "POST",
	        url : url,
	        data : params,
	        dataType : "json",
	        success : common.ajaxResponse,
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        error : function(xhr, status, exception, exMap){
	        	var ex = jQuery.parseJSON(xhr.responseText);
	        	alert(ex.MESSAGE + "\n ("+status+")");
				return false;
	        }
	    });
	}
};



/**
 * ajax submit
 */
common.ajaxCallbackSubmit2 = function(url, params, callbackMethod){

	if(callbackMethod != null){	 
		$.ajax({
			type : 'POST',
	        url : url,
	        data : params,
	        crossDomain: true,
	        dataType : "text",
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        success : callbackMethod,
	        error : function(xhr, status, exception,exMap){
	        	var ex = jQuery.parseJSON(xhr.responseText);
	        	alert(ex.MESSAGE + "\n ("+status+")");
				return false;
	        }
	    });
	}else{
		$.ajax({
			type : 'POST',
	        url : url,
	        data : params,
	        crossDomain: true,
	        dataType : "text",
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        success : common.ajaxResponse,   
	        error : function(xhr, status, exception, exMap){
	        	var ex = jQuery.parseJSON(xhr.responseText);
	        	alert(ex.MESSAGE + "\n ("+status+")");
				return false;
	        }
	    });
	}
};

/**
 * ajax response
 */
common.ajaxResponse = function(json, status, xhr, inheritedCallback){
	if(typeof json != 'object'){
		json = JSON.parse(json);
	}
	if(StringUtil.isEmpty(json[common.MSG_FOR_ALERT]) == false){
		alert(json[common.MSG_FOR_ALERT]);
	}
	if(status == "success"){
		if(StringUtil.isEmpty(json[common.URL_FOR_REDIRECT]) == false){
			location.href=json[common.URL_FOR_REDIRECT];
		}
	}
};

//popup
common.windowOpen = function(uri, width, height) {
	common.windowOpenWithName(uri, width, height, '_blank');
};

//popup -custom
common.windowOpenWithName = function(uri, width, height, popName) {
	if(uri == null || uri.length < 1){
		alert("주소가 없습니다.");
		return;
	}
	var intWidth = screen.width-50;
	var intHeight = screen.height-100;

	if(width != null){
		intWidth = width;
	}
	if(height != null){
		intHeight = height;
	}

	var opt = "scrollbars=yes,toolbar=no, titleBar=no, status=no, resizable=yes,width="+intWidth+",height="+intHeight+",left="+((screen.width-intWidth)/2)+",top="+((screen.height-intHeight)/2);

	window.open(uri, popName, opt);
};
//popup - post
common.windowOpenPost = function(uri, form_name, width, height) {
	if(uri == null || uri.length < 1){
		alert("주소가 없습니다.");
		return;
	}
	var intWidth = screen.width-50;
	var intHeight = screen.height-100;

	if(width != null){
		intWidth = width;
	}
	if(height != null){
		intHeight = height;
	}

	var opt = "scrollbars=yes,toolbar=no, titleBar=no, status=no, resizable=yes,width="+intWidth+",height="+intHeight+",left="+((screen.width-intWidth)/2)+",top="+((screen.height-intHeight)/2);

	window.open('', form_name, opt);
	
	$("form[name="+form_name+"]").attr("action",uri);
	$("form[name="+form_name+"]").attr("method","post");
	$("form[name="+form_name+"]").attr("target",form_name );
	
	$("form[name="+form_name+"]").submit();
};

common.specialStr = function(str){
	var special_str = /[1234567890~`!@#$%^&*()_\-+|{}[\].?\/]/g;

	if(special_str.test(str)) {
		return true;
	}else{
		return false;
	}
};

/**
 * 입력된 Form ID에 해당하는 Elements를 Reset 한다.
 */
common.formReset = function(formId){
	$("#"+formId).each(function(){
		this.reset();
	});
};

/*************************************************************************
 * 업로드 팝업을 오픈한다.
 **************************************************************************/
common.callAspUploadPop = function(uploadUrl, pageUrl,popupName){
	var returnUrl = "";
	var popName = "";
	if(pageUrl == undefined)
		returnUrl = document.location.origin + contextPath + "/returnTransferContentsResult.mvc";
	else
		returnUrl = document.location.origin + contextPath + pageUrl;
	
	if(popupName == undefined) popName = "aspUploadPop";
	
	//if(document.location.pathname.indexOf(contextPath) > 0)
		//returnUrl = document.location.origin + contextPath + "/returnTransferContentsResult.mvc";
	
	uploadUrl += "?return_url="+returnUrl;
	common.windowOpenWithName(uploadUrl, '440', '300', popupName);
};

/*************************************************************************
 * 이미지 업로드 팝업을 오픈한다.
 **************************************************************************/
common.callAspImgUploadPop = function(uploadUrl, pageUrl){
	var returnUrl = "";
	
	if(pageUrl == undefined)
		returnUrl = document.location.origin + contextPath + "/returnTransferContentsResult.mvc";
	else
		returnUrl = document.location.origin + contextPath + pageUrl;
	
	if(document.location.pathname.indexOf(contextPath) > 0)
		returnUrl = document.location.origin + contextPath + "/returnTransferContentsResult.mvc";
	
	uploadUrl += "?return_url="+returnUrl;
	common.windowOpenWithName(uploadUrl, '440', '300', 'aspUploadPop');
};

/**
 * 동영상 플레이어를 오픈한다.
 */
common.CallAspPlayPop = function(filePath,width,height,mode){
	/*if(path == 'null'){
		alert('등록된 URL이 올바르지 않습니다.');
		return false;
	}
	var paryUrl = "http://122.99.198.141/encoding/vod_pop.asp?idx="+path;
	common.windowOpenWithName(paryUrl, '720', '480', 'aspPlayPop');*/
	
};

/**
 * Json 데이터를 입력된 form에 Binding 한다.
 */
common.formDataBinding = function(map,fomrId){
	if(map != null){
		$.each(map, function(datakey, datavalue) {
			$(fomrId).find('input, textarea, select, checkbox, radio').each(function(index,target){
				if($(this).attr('id') != null){
					if($(this).attr('id').toUpperCase() == datakey || $(this).attr('name').toUpperCase() == datakey) {
	                    if($(this).attr("type") == 'radio')
	                    	if($(this).attr("value") == datavalue) $(this).attr("checked",true);
	                    
	                    if($(this).attr("type") == 'checkbox')
	                    	if($(this).attr("value") == datavalue) $(this).attr("checked",true);
						
	                    $(this).val(datavalue);
	                }
				}
            });
        });	
	}
};

function onlyNum(inputBox) {
    var inputVal = $(inputBox).val();
    inputVal = inputVal.replace(/[^0-9]/g,'');
    $(inputBox).val(inputVal);
}

function onlyNumAndComma(inputBox){
	var inputVal = $(inputBox).val();
    inputVal = inputVal.replace(/[^0-9]/g,'');
    $(inputBox).val(inputVal);
    
    inputVal = $(inputBox).val();
    var money = checkMoneyUnit(inputVal);
    $(inputBox).val(money);
}

function nr_num(this_s,type){ 
    /* 
    type 
    -> 'int' : 양의 정수 
    -> 'float' : 양의 실수 
    -> '-int' : 음의 정수 포함 
    -> '-int' : 음의 실수 포함 
    */ 
    temp_value = this_s.value.toString(); 
    regexp = /[^-\.0-9]/g; 
    repexp = ''; 
    temp_value = temp_value.replace(regexp,repexp); 
    regexp = ''; 
    repexp = ''; 
    switch(type){ 
        case 'int':     regexp = /[^0-9]/g; break; 
        case 'float':regexp = /^(-?)([0-9]*)(\.?)([^0-9]*)([0-9]*)([^0-9]*)/; break; 
        case '-int':    regexp = /^(-?)([0-9]*)([^0-9]*)([0-9]*)([^0-9]*)/;break; 
        case '-float':regexp = /^(-?)([0-9]*)(\.?)([^0-9]*)([0-9]*)([^0-9]*)/; break; 
        default : regexp = /[^0-9]/g; break; 
    } 
    switch(type){ 
        case 'int':repexp = '';break; 
        case 'float':repexp = '$2$3$5';break; 
        case '-int':    repexp = '$1$2$4';break; 
        case '-float':repexp = '$1$2$3$5'; break; 
        default : regexp = /[^0-9]/g; break; 
    } 
    temp_value = temp_value.replace(regexp,repexp); 
    this_s.value = temp_value; 
} 

/**
 * 입력받은 숫자를 금액으로 반환
 * @param
 * @returns
 */
function checkMoneyUnit(employInsurance) {
	var employ_insurance = employInsurance;
	var commaValue = "";
	
	//4자리에서 5자리 이상 숫자를 입력했을 때, 정확한 자리수 체크를 위해 초기화 해줌
	employ_insurance = $.trim(employ_insurance);
	for(var i=1; i<=employ_insurance.length; i++) {
		if(i > 1 && (i%3) == 1) { //3자리로 나눠서 남은 자리가 1이면 콤마(')처리
			commaValue = employ_insurance.charAt(employ_insurance.length-i) + "," + commaValue;
		} else {
			commaValue = employ_insurance.charAt(employ_insurance.length-i) + commaValue;
		}
	}
	
	return commaValue;
}

/**
 * 금액표시와 같이 숫자값에 세자리씩 콤마를 추가한다.
 * @param number : 숫자값
 */
function addComma(number) {
	number = '' + number;
	var number1 = "";
	var number2 = "";

	if (number.indexOf(".") > 0) {
		number1 = number.substring(0, number.indexOf("."));
		number2 = number.substring(number.indexOf("."));

	} else {
		number1 = number;
	}

	if (number1.length > 3) {
		var mod = number1.length % 3;
		var output = (mod > 0 ? (number1.substring(0, mod)) : '');

		for (var i=0 ; i < Math.floor(number1.length / 3); i++) {
			if ((mod == 0) && (i == 0)) {
				output += number1.substring(mod+ 3 * i, mod + 3 * i + 3);
			} else {
				output+= ',' + number1.substring(mod + 3 * i, mod + 3 * i + 3);
			}
		}

		return (output + number2);

	} else {
		return (number1 + number2);
	}
}

/**
 * 입력받은 숫자(또는 문자열)을 세자리 마다 콤마 추가
 * @param number
 * @returns
 */
function addCommaNumber(number) {
	number = '' + number;
	if (number.length > 3) {
	var mod = number.length % 3;
	var output = (mod > 0 ? (number.substring(0,mod)) : '');
		for (var i=0 ; i < Math.floor(number.length / 3); i++) {
		if ((mod == 0) && (i == 0))
			output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
		else
			output+= ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
		}
	return (output);
	}
	else return number;
}

function showConfirmPopup(confirmMessage){
	var addHTML = '';
	addHTML += '	<h1 class="tit">확인창</h1>';
	addHTML += '	<div class="lypop_con ac">';
	addHTML += '		<p class="mt50">'+confirmMessage+'</p>';
	addHTML += '		<div class="mt40">';
	addHTML += '			<button type="button" class="btn medium gray btn_lyclose">취소</button>';
	addHTML += '			<button type="button" class="btn medium brown");" onclick="return true">저장</button>';
	addHTML += '		</div>';
	addHTML += '	</div>';
	
	$(".lyAltConfirm").html(addHTML);
	
	$("html").css({overflow: "hidden"});
	$(".lyAltConfirm").dialog({
		modal: true,
		width: 500,
		height: 300,
		resizable: false,
		position: {my: "center", at: "center", of: window}
	});
	$(".lyAltConfirm").find(".elips").ellipsis();
	$(".lyAltConfirm"+" .btn_lyclose").on("click", function () {
		$(".lyAltConfirm").prev().find(".ui-dialog-titlebar-close").trigger("click");

	});
	$(".lyAltConfirm").prev().find(".ui-dialog-titlebar-close").on("click", function () {
		$("html").css({overflow: "visible"});
	});
	$(window).resize(function() {
		$(".lyAltConfirm").dialog("option", "position", {my: "center", at: "center", of: window});
	});
}

/**
 * 공통 레이어 팝업
 */
common.layerPopup = function(url, id){
	$.ajax({
        type : "POST",
        url : url,
        cache: false,
        success : function(data) {
        	common.popupCallback(data, id);
	    },
        error : function(xhr, status, exception){
        	alert("전송에 실패하였습니다. \n ("+status+")");
			return false;
        }
    });
};

common.layerPopupParam = function(url, id, params){
	$.ajax({
        type : "POST",
        url : url,
        cache: false,
        data : params,
        success : function(data) {
        	common.popupCallback(data, id);
	    },
        error : function(xhr, status, exception){
        	alert("전송에 실패하였습니다. \n ("+status+")");
			return false;
        }
    });
};

/**
 * 팝업콜백함수
 */
common.popupCallback = function(data, id, layerTemplateId) {
	var link =  document.location.href;
	var height = "";
	
	if(link.indexOf("/admin/") > 0) height = "auto";
	else height = inits.docH - 100;
	
	if(layerTemplateId) {
		$('#' + layerTemplateId).html(data);
		$(id).dialog({
			modal: true,
			width: "auto",
			height: height
		});
		
		$(window).resize(function() {
			$(id).dialog("option", "position", {my: "center", at: "center", of: window});
		});
		
		if($("[aria-describedby="+id.replace("#","")+"]").length > 0){
			$(document).on("click","[aria-describedby="+id.replace("#","")+"] .ui-dialog-titlebar-close",function(){
				$("[aria-describedby="+removeId+"]").eq(0).remove();
				$("#layer_template").find(id).remove();
			});
			$(id+" .btn_lyclose").on("click", function () {
				$("[aria-describedby="+removeId+"]").eq(0).remove();
				$("#layer_template").find(id).remove();
			});
		}else{
			$(document).on("click",".ui-dialog-titlebar-close",function(){
				$(".ui-dialog").remove();
				$("#layer_template").find(id).remove();
			});
		}
		
	} else {
		$("#layer_template").html(data);
		$(id).dialog({
			modal: true,
			width: "auto",
			height: height
		});
		
		$(window).resize(function() {
			$(id).dialog("option", "position", {my: "center", at: "center", of: window});
		});
		
		if($("[aria-describedby="+id.replace("#","")+"]").length > 0){
			$(document).on("click","[aria-describedby="+id.replace("#","")+"] .ui-dialog-titlebar-close",function(){
				$("[aria-describedby="+removeId+"]").eq(0).remove();
				$("#layer_template").find(id).remove();
				$("html").css({overflow: "visible"});
			});
			$(id+" .btn_lyclose").on("click", function () {
				$("[aria-describedby="+removeId+"]").eq(0).remove();
				$("#layer_template").find(id).remove();
				$("html").css({overflow: "visible"});
			});
		}else{
			$(document).on("click",".ui-dialog-titlebar-close",function(){
				$(".ui-dialog").remove();
				$("#layer_template").find(id).remove();
				$("html").css({overflow: "visible"});
			});
		}	
	}
	
	/*	이미 해당 Dialog가 등록되어 있다면 첫번째를 제거한다.	*/
	var removeId = id.replace("#","");
	if($("[aria-describedby="+removeId+"]").length > 1){
		$("[aria-describedby="+removeId+"]").eq(0).remove();
		$("#layer_template").find(id).remove();
	}
	$('.thumbLoad').onLoadThumb(); // 썸네일 이미지
	
};

function resizePopupLayer() {
	var documentHeight = $("body").height();
	var contentHeight = $(".lypop_con").height() || 0;
	
	if (contentHeight > documentHeight) {
		$(".lypop_con").css("max-height", documentHeight - 100 + "px");
	} else {
		$(".lypop_con").css("max-height", "100%");
	}
}

/***************************************************************************
 * 업로드 허용파일 체크
****************************************************************************/
common.acceptFileCheck = function(elementId){
	var fileExt = null;
	var fileExtArray = null;
	var acceptFileArray = ["jpg","gif","bmp","png","mp4","wmv","avi","doc","docx","ppt","xls","xlsx","txt","hwp","zip","pdf"];
	var elements = $("#"+elementId+" input:file");
	for(var i=0;i<$(elements).length;i++){
		if($(elements).eq(i).val() != ''){
			fileExtArray = $(elements).eq(i).val().split(".");
			fileExt = fileExtArray[fileExtArray.length -1];
			if($.inArray(fileExt, acceptFileArray) == -1){
				alert('업로드가 허용되지 않는 확장자 입니다.');
				return false;
			}
		}
	}
};

common.changeXssWord = function(xssStr){
	xssStr = xssStr.replace(/</gi, '');
	xssStr = xssStr.replace(/>/gi, '');
	xssStr = xssStr.replace(/"/gi, '');
	xssStr = xssStr.replace(/'/gi, '');
	return xssStr;
}

common.postGoto = function(url, parm, target, enctype) {		// JSON 으로 넘어온 값을 param으로 담아서 submit
	var frm = document.createElement("form");
	var objs, value;
	
	for (var key in parm) {
	    value = parm[key];
	    objs = document.createElement("input");
	    objs.setAttribute("type", "hidden");
	    objs.setAttribute("name", key);
	    objs.setAttribute("value", value);
	    frm.appendChild(objs);
	}
	
	if (target) {
	    frm.setAttribute("target", target);
	}
	if(enctype){				
		frm.setAttribute("enctype", "multipart/form-data");
	}
	frm.setAttribute("method", "post");
	frm.setAttribute("action", url);
	document.body.appendChild(frm);
	frm.submit();
}