var validation = new Object();

validation.validateAll = function(){
	return validation.checkInputValidation($('input,textarea,select'));
};

validation.validate = function(mother){
	var elements = [];	
	
	$(mother).find('input,textarea,select').each(function(index,target){
		elements.push($(target));
	});
	
	return validation.checkInputValidation(elements);
};

validation.checkInputValidation = function(htmlElementList){

	if(htmlElementList != null){
		var cnt = htmlElementList.length;
		for(var i=0;i<cnt;i++){
			var jObj = $(htmlElementList[i]);
			if(StringUtil.isEmpty(jObj.attr("name")) == false ){
				
				if(jObj.hasClass("datepk")){
					if(!StringUtil.isEmpty(jObj.val())){
						if(validation.isDateCheck(jObj.val())== false){
							jObj.focus();
							alert("["+jObj.attr("title")+"] 형식에 맞지 않습니다.\n ex) 2016-01-01");
							return false;
						}
					}
				}
				
				if(StringUtil.isEmpty(jObj.attr("data-fixed")) ==false){
					
					if(jObj.attr("data-fixed") == "true"){
						
						// radio box
						if(jObj.attr("type") == "radio"){
							var radioArray = jObj.parent().find("[name="+jObj.attr("name")+"]").filter(":checked");
							if(radioArray.length == 0){
								radioArray = jObj.parent().parent().find("[name="+jObj.attr("name")+"]").filter(":checked");
							}							
							if(StringUtil.isEmpty(radioArray.val()) == true){
								jObj.focus();
								alert("["+jObj.attr("title")+"]를 선택해 주세요");
								return false;
							}
						}else if(jObj.attr("type") == "checkbox"){
							if(StringUtil.isEmpty(jObj.filter(":checked").val()) == true){
								jObj.focus();
								alert("["+jObj.attr("title")+"]를 선택해 주세요");
								return false;
							}	
						}else{
							if(StringUtil.isEmpty(jObj.val()) == true){
								jObj.focus();
								alert("["+jObj.attr("title")+"]를 입력해 주세요");
								return false;
							}	
						}
						
					}
				}
				if(StringUtil.isEmpty(jObj.attr("data-type")) == false){ 
					var data_type_full = jObj.attr("data-type");
					var data_type = jObj.attr("data-type").replace(/\s?\(\s?(size\:)?\s?(\d+)\s?,?\s?(\d+)?\s?\)\s?/gi,'');
										
					if(data_type == "alphaNumeric"){
						if(validation.isAlphaNumeric(jObj.val()) == false ){
							jObj.focus();
							alert("["+jObj.attr("title")+"]");
							return false;
						}
					}else if(data_type == "number"){
						if(validation.isNumber(jObj.val()) == false){
							jObj.focus();
							alert("["+jObj.attr("title")+"]");
							return false;
						}
										
					}else if(data_type == "email"){
						if(validation.isEmailCheck(jObj.val()) == false){
							jObj.focus();
							alert("["+jObj.attr("title")+"] 형식에 맞지 않습니다.\n ex) aa@bb.ccc");
							return false;
						}
					}else if(data_type == "telNum"){
						if(validation.isTelNumCheck(jObj.val()) == false){
							jObj.focus();
							alert("["+jObj.attr("title")+"] 형식에 맞지 않습니다.\n ex) 000-0000-0000");
							return false;
						}
					}
					if(data_type != data_type_full ){

						if(StringUtil.isEmpty(jObj.val())){
							continue;
						}
						var args = /.+\s?\(\s?(size\:)?\s?(\d+)\s?,?\s?(\d+)?\s?\)\s?/gi.exec(data_type_full);

						var len = jObj.val().length;
						var isSize = args[1] == 'size:' ? true : false;
						var min = args[2];
						var max = args[3];
						
						if(isSize){
							if(jObj.val() < min || (StringUtil.isEmpty(max) == false && jObj.val() > max)){
								jObj.focus();							
								alert("["+jObj.attr("title")+"]");
								return false;
							}
						} else {
							if(len != min && StringUtil.isEmpty(max)){
								jObj.focus();
								alert("["+jObj.attr("title")+"]");
								return false;
							}
							if(len < min || (StringUtil.isEmpty(max) == false && len > max)){
								jObj.focus();							
								alert("["+jObj.attr("title")+"]");
								return false;
							}
						}
						
					}
				}
			}
		}
	}
	
	return true;
};

validation.isNormal = function(sVal){
	if(StringUtil.isEmpty(sVal)){
		return true;
	}

	return true;
};

validation.isAlphaNumeric = function (sVal){
	if(StringUtil.isEmpty(sVal)){
		return true;
	}
	var regexp = /^[a-zA-Z0-9]+$/;
	if(sVal.match(regexp) == null){
		return false;
	}
	return true;
};

validation.isNumber = function(sVal)
{    
	if(StringUtil.isEmpty(sVal)){
		return true;
	}
	var regexp = /^[0-9]+$/;
	return regexp.test(sVal);
};

validation.isEmailCheck = function(sVal){
	if(StringUtil.isEmpty(sVal)){
		return true;
	}
	var regexp = /^[0-9a-zA-Z-_\.]*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,5}$/i;
	              
	if(sVal.match(regexp) == null){
		return false;
	}
	return true;
}; 

/* Telephone/mobile number check */
validation.isTelNumCheck = function(sVal){
	if(StringUtil.isEmpty(sVal)){
		return true;
	}
	var regexp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	              
	if(sVal.match(regexp) == null){
		return false;
	}
	return true;
};

validation.isDateCheck = function(sVal){
	if(StringUtil.isEmpty(sVal)){
		return true;
	}
	var regexp = /^(2)\d{3}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
	              
	if(sVal.match(regexp) == null){
		return false;
	}
	return true;
};

function makeArray(iSize)
{
  this.length = iSize;

  for (var i = 1; i <= iSize; i++)
  {
    this[i] = 0;
  }
  return this;
}


function isMoneyNumber(sVal)
{    
  var iAbit;
	
  if (sVal.length < 1) return true;
  for (var i=0; i<sVal.length; i++)
  {
    iAbit = parseInt(sVal.substring(i,i+1));
    if (!(('0' < iAbit) || ('9' > iAbit)))
    {
      if (sVal.substring(i, i+1) == ',' || sVal.substring(i, i+1) == '.' )
      {
      }
      else
      {
        return false;
      }
    }
  }
  return true;
}

function isMoneyNumber2(sVal)
{    
  var iAbit;
	
  if (sVal.length < 1) return true;
  for (var i=0; i<sVal.length; i++)
  {
    iAbit = parseInt(sVal.substring(i,i+1));
    if (!(('0' < iAbit) || ('9' > iAbit)))
    {
      if (sVal.substring(i, i+1) == ',')
      {
      }
      else
      {
        return false;
      }
    }
  }
  return true;
}

function isMoneyNumber3(sVal)
{    
  var iAbit;
  var deci_cnt = 0;
  if (sVal.length < 1) return true;
  for (var i=0; i<sVal.length; i++)
  {
    iAbit = parseInt(sVal.substring(i,i+1));
    if (!(('0' < iAbit) || ('9' > iAbit)))
    {
      if (sVal.substring(i, i+1) == '.' )
      {
		  deci_cnt = deci_cnt + 1;
      }
      else
      {
        return false;
      }
    }
  }
  if (deci_cnt > 1)
  {
	  return false;	
  }
  return true;
}

function isMoneyNumber4(sVal){    
  var deci_cnt = 0;
  for (var i=0; i<sVal.length; i++)
  {
    
      if (sVal.substring(i, i+1) == '.' ){
		  deci_cnt = deci_cnt + 1;
      }
   
  }
  if (deci_cnt > 0)
  {
	  return true;
  }
  return false;
}

function isMoneyNumber5(sVal, iSize1, iSize2) 
{
	if(isMoneyNumber(sVal))
	{
		var e = sVal.value;
		e = e.split(".");
	    e[0] = numOffMask(e[0]);
		if (!e[1]) {
			e[1] = 0;		    
		}		
		
		var aVal = e[0] + "." + e[1];

		if (isNumberDot(aVal)) {
			if (e[0].length > iSize1 || e[1].length > iSize2)
				return false;
			else
				return true;
		}
		else {
		    return false;
		}

	}
	else {
		return false;
	}
}


function getMoneyType(val)
{
  if (typeof val == "number")
  {
    val = val.toString();
  }
	
  var value = getOnlyNumberDot(val);
	
  var sResult = "";

  if (value.length == 0)
  {
    alert("0");
    return;
  }

  if (! isMoneyNumber(value))
  {
    alert("is not money number");
    return;
  }
	
  var nI;
  var nJ = -1;
  var subOne;
  var flag = false;

  for (nI = value.length - 1; nI >= 0; nI--)
  {
    subOne = value.substring(nI, nI + 1);
    sResult = subOne + sResult;


	if (subOne == '.')
	{
		flag = true;
	}

	if (flag == true)
	{
		nJ = nJ + 1;
	}

    if ((nJ % 3 == 0) && (nI != 0) && (nJ != 0))
    {
      sResult = "," + sResult;
    }
  }
  return sResult;
}

function getSignMoneyType(val)
{
  if (typeof val == "number")
  {
	 val = val.toString();
  }

  var s1	= val.substring(0,1);
  var slen	= val.length;
  var sign	= "";
  var ret		= "";
  if (val == "-Infinity")
  {
		return "0";
  }
	
  if(slen>1 )
  {
    if(s1 == "-")
    {
      sign = "-";
      ret = sign + getMoneyType(val.substring(1,slen));
     }
     else
     {
       ret = getMoneyType(val);
     }
   }
   else
   {
     ret = val;
   }	
   return  ret; 
}

function getOnlyNumber(val)
{    
  var value = "";
  var abit;

  if (typeof val != "number" && typeof val !="string") 
  {
    return "0";
  }
  if (val.length < 1)
  {
    return "0";
  }
  if (val == "NaN")
  {
    return "0";
  }
  if (val == "-Infinity") 
  {
    return "0";
  }
    
  for (var i=0; i<val.length; i++)
  {
    abit = parseInt(val.substring(i,i+1));
    if (('0' < abit) || ('9' > abit) )
    {
      value = value + abit;
    }

  }
  return value;
}

  function getOnlyNumberDot(val)
  {    
  
  	if (typeof val != "number" && typeof val !="string") 
  	{
  		return "0";
  	}
  	if (val.length < 1)
  	{
  		return "0";
  	}
  	if (val == "NaN")
  	{
  		return "0";
  	}
  	if (val == "-Infinity") 
  	{
  		return "0";
  	}
  
  	var value = "";
  	var abit;
  
  	var delimter = val.indexOf(".");
  	var numberInteger = "";
  	
  	if(delimter < 0) {
  		numberInteger = val;
  		abit ='';
  	} else {
  		numberInteger = val.substring(0,delimter);
  		abit = val.substring(delimter+1);
  	}
  
  	var number="";
  	var leng=numberInteger.length ;
  	for(var i=0 ; i<leng ; i++)
  	{
  		var tmp = numberInteger.substring(i,i+1);
  		if(tmp != ",")
  		{
  			number = number+tmp;
  		}
  	}
  	
  	if(abit.length==0)
  	{
  		value=number;
  	}
  	else 
  	{
  		value = number+"."+abit;
  	}
  	return value;
  
  }

function getOnlySignNumber(val)
{
  if (val == "-") return 0;
  var price = eval(getOnlyNumber(val));
  if (val.substring(0,1) == "-") 
  {
    price *= -1;
  }
  return price;
}

function Trim(sVal)
{
  return(LTrim(RTrim(sVal)));
}

function LTrim(sVal)
{
  var i;
  i = 0;
  while (sVal.substring(i,i+1) == ' ')
  {
    i++;
  }
  return sVal.substring(i);
}

function RTrim(sVal)
{
  var i = sVal.length - 1;
  while (i >= 0 && sVal.substring(i,i+1) == ' ') 
  {
    i--;
  }
  return sVal.substring(0,i+1);
}

function MTrim(sVal){
	var strOri = sVal;
	var space = " ";
	  
	while (strOri.indexOf(space) != -1){
		strOri = strOri.replace(space, "");
	}

	return strOri;
}

function isEmpty(sVal){
  if (MTrim(sVal) == '')
  {
    return true;
  }
  return false;
}

function focusMove(objCurrent, objNext)
{
  if ( objCurrent.getAttribute("Maxlength") == objCurrent.value.length)
  {
    objNext.focus();
  }
}

function focusMoveSelect(objCurrent, objNext)
{
  if ( objCurrent.getAttribute("Maxlength") == objCurrent.value.length)
  {
    objNext.focus();
    objNext.select();
  }
}

function calOnMask(me){

 if (event.keyCode<48||event.keyCode>57){
     event.returnValue=false;
 }
	if(me.length > 3 ) {
		var a1 = me.substring(0,4) + "/";
		var a2 = me.substr(4,me.length);
		var a3 = "";
		if (me.length > 5){
			a2 = me.substring(4,6) + "/";
			a3 = me.substr(6,me.length);
		}
	
		me= a1 + a2 + a3;
		
	}
	return me;
}


function calOffMask(me){
	var tmp=me.split("/");
	tmp=tmp.join("");
	return tmp;
}


function cal_value2(me){

 	if(me.length == 8 ) {
		var a1 = me.substring(0,4) + "/";
		var a2 = me.substring(4,6) + "/";
		var	a3 = me.substr(6,me.length);
		
		me= a1 + a2 + a3;
		
	}
	return me;
}


function todate() {
	var now=new Date();
	var jyear = now.getYear();
	var month=now.getMonth() + 1;
	var jmonth = month + "";
	if (jmonth.length < 2) {
		jmonth = "0" + jmonth; 
	}
	var dat=now.getDate();
	var jdate = dat + "";
	if (jdate.length < 2) {
		jdate = "0" + jdate;
	}
 
    var tdy = jyear+"/"+jmonth+"/"+jdate;
	return tdy;	
}

function comma_value(sval)
{
    if (event.keyCode != 9) 
    {
        var cur = sval.value;
        var setMinus = 0;

        if (cur.charAt(0) == "-") {
            setMinus = 1;
        }

        cur=cur.replace(/[^.0-9]/g ,"");
        cur=cur.replace(/[.]+/g ,".");

        if (setMinus == 1) 
            sval.value = "-" + formatNumbertoString(cur);
        else
            sval.value = formatNumbertoString(cur);
    }
}

function formatNumbertoString(cur)
{
    leftString = cur;
    rightString = ".";
    dotIndex = 0;
      
    for(var i = 0; i < cur.length; i++){

    	if(cur.charAt(i) == "." || (cur.length > 1 && cur.charAt(0) == "0" && cur.charAt(1) != "."))
		{
    		dotIndex = i;
    		if(dotIndex == 0)
			{
                if   (cur.charAt(0) == ".")   leftString="0.";
                else                          leftString="";
    			return leftString;
    		}
    		break;
    	}
    }
    
     if(dotIndex != 0)
    {
    	leftString = cur.substr(0, dotIndex);
    	rightString = cur.substr(dotIndex+1);
    	rightString = rightString.replace(/\./g,"");
    }
    else
    {
    	leftString = cur;
    }

    len=leftString.length-3;
    while(len>0) 
    {
        leftString=leftString.substr(0,len)+","+leftString.substr(len);
        len-=3;
    }           
    
    if(rightString != ".")
        return (leftString + "." + rightString); 
    else
        return leftString;
}

function numOnMask4(sVal,iSize) 
{
	if(isNumberDot(sVal))
	{
		var e = sVal;
		e = e.split(".");
		if (!e[1]) {			
			if (iSize == 0) {
			    sVal = numOnMask(e[0]);
				return sVal;
			}
			else {
				e[1] = "0";
			}
		}
		while (e[1].length < iSize) {
		    e[1] = e[1] + "0";
		}
		sVal = numOnMask(e[0]) + "." + e[1].substr(0,iSize);
		return sVal;
	}
	else {
		return false;
	}
}


function numOnMask5(sVal) 
{
	var e = sVal;
	e = e.split(".");

	if(!isMoneyNumber3(e[0]))
		e[0] = e[0].substring(1);

	return numOnMask(e[0]);
}

function numOnMask6(sVal) 
{
	var e = sVal;
	e = e.split(".");

	if(!isMoneyNumber3(e[0]))
		e[0] = e[0].substring(1);

	return numOnMask(e[0]) + "." + e[1];
}

function isSpecial(str){
	var re = /[~!@\#$%^&*\()\-=+_']/gi;
	if(re.test(str)){
		return true;
	}
	return false;
}

function inputCheckSpecial(strobj){
	var re = /[~!@\#$%^&*\()\-=+_']/gi;
	if(re.test(strobj.value)){
		alert("input check");
		strobj.value=strobj.value.replace(re,"");
	}
}

function win_open(wUrl,wTitle,wTop,wLeft,wWidth,wHeight,wSco) {
	window.open(wUrl,wTitle,"top="+wTop+",left="+wLeft+",width="+wWidth+",height="+wHeight+",scrollbars="+wSco);
}

function win_open2(wUrl,wTitle,wWidth,wHeight,wSco) {
	var top_po = (screen.availHeight/2) - (wHeight/2);
	var left_po = (screen.availWidth/2) - (wWidth/2);
	window.open(wUrl,wTitle,"top="+top_po+",left="+left_po+",width="+wWidth+",height="+wHeight+",scrollbars="+wSco);
}

function win_open3(wUrl,wTitle) {
	var wWidth = screen.availWidth;
	var wHeight = screen.availHeight;
	
	window.open(wUrl,wTitle,"top=0,left=0,width="+wWidth+",height="+wHeight+",scrollbars=0");
	
}

function email_chk(stremail, stremail2){
  var mail = stremail.value + "@" + stremail2.value;
  var t = escape(mail);
  if(t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null && t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null){
    alert("email check");
    this.focus();
    return;
  } 
}
