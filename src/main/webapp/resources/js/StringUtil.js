var StringUtil = new Object(); 

StringUtil.isEmpty = function(val){
	if(val == null || val == ""){
		return true;
	} 
	return false;
};


StringUtil.getStr = function(val){
	if(val == null){
		return "";
	} 
	return val;
};

StringUtil.fillZero = function fillZero(sVal, iSize) 
{
    while(sVal.length < iSize)  
    {
		sVal = "0" + sVal;
    }
	return sVal;
};


StringUtil.isAlpha = function(sVal){
  // Alphabet
  var sAlphabet="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var iLen=sVal.length;

  for(var i=0;i<iLen;i++)
  {
    if(sAlphabet.indexOf(sVal.substring(i,i+1))<0)
    {
      alert("check the alphabet");				
      return false;
    }
  }
  return true;		
};

StringUtil.strLength = function(sVal)
{	
  var sBit = '';
  var iLen = 0;

  for (var i = 0 ; i < sVal.length ; i++ )
  {
    sBit = sVal.charAt(i);
    if ( escape( sBit ).length > 4 )
    {
      iLen = iLen + 2;
    }
	else
	{
      iLen = iLen + 1;
    }
  }
  return iLen;
};


StringUtil.chkStrLength = function(str,field) 
{
	iSize = str.getAttribute("Maxlength");

	if (field == null) 
		field = '';

	if ( strLength(str.value) > iSize)
	{
		alert(" "+field+" "+iSize+" "+Math.floor(iSize/2)+" ");

        str.select();
	    str.focus();
		return false;
	}

	return true;
};

StringUtil.toSymbol = function(str) 
{
	if(str == null || str == ""){
		return null;
	}
	 str.replace(/<br>/gi, "\r\n");
	 str.replace(/&nbsp;/gi, " ");
	 str.replace(/&lt;/gi, "<");
	 str.replace(/&gt;/gi, ">");
	 str.replace(/&quot;/gi, "\"");
	 str.replace(/&acute;/gi, "'");
	 str.replace(/&#40;/gi, "\\(");
	 str.replace(/&#41;/gi, "\\)");
	return str;
};