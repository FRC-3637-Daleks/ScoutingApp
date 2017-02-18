<#import "spring.ftl" as spring />
<html>
<head>
<style>

.categoryTable table{
	width:100%;
	display:block;
}

.categoryTable td{
    padding-right:2.38em;
    padding-top:0.8em;
    padding-bottom:0.8em;
    padding-left:0.8em;
    border: 1px solid gray;
}

.sectionHeader {
    background-color:#53EA0C;
    font-weight:bold;
    text-align:center;
}

#codxpl th{
    background-color:#005EFF;
    font-weight:bold;
    padding:0.8em;
    border: 1px solid;
}
.cellTitle {text-align: center; height: 50px}
.columnTitle {text-align: center}
</style>
<script src="../js/jquery.min.js"></script>
<script>
function show(target) {
    document.getElementById(target).style.display = 'block';
}

function hide(target) {
    document.getElementById(target).style.display = 'none';
}
function toggle(target) {
	if (document.getElementById(target).style.display == 'none') {
		show(target);
	}
	else if (document.getElementById(target).style.display == 'block' || document.getElementById(target).style.display == '') {
		hide(target);
	}
}

function toggleTag(target, value) {
	if (value)
		$.get("../m/incrementTag?tag="+target+"&team="+team+"&match="+match);
	else
		$.get("../m/decrementTag?tag="+target+"&team="+team+"&match="+match);
}

var team=${team};
var match=${match};
function incrementTag(target) {
	var incrementElement=document.getElementById(target+"-counter");
	var currentValue=incrementElement.value;
	if (!currentValue) {
		currentValue="1";
	}
	else
		currentValue=Number(currentValue)+1;
	incrementElement.value=currentValue;
	$.get("../m/incrementTag?tag="+target+"&team="+team+"&match="+match);
}

function decrementTag(target) {
	var decrementElement=document.getElementById(target+"-counter");
	var currentValue=decrementElement.value;
	if (!currentValue) {
		currentValue="1";
	}
	if (currentValue<="0") {
		currenValue="0"
		}
	else {
		currentValue=Number(currentValue)-1;
		$.get("../m/decrementTag?tag="+target+"&team="+team+"&match="+match);
	}
	decrementElement.value=currentValue;
	
}
</script>
</head>
<body>
<table id="codxpl">
  <tr>
    <th onclick="toggle('${team}')"><p style="color:white">Team: ${team}</p></th>
    <th><p style="color:white">Match: ${match}</p></th>
  </tr>
</table>
<div id=${team}>
<form>
<#assign grouping = ""> 
<#assign category = ""> 
<#list matchTags as matchTag>
<#if grouping == "" || grouping != matchTag.grouping> 
   <#if category != "">
   </tr>
   </table>	
   </td>
   </#if>
  <#if grouping != "">
     </tr>
  </table>   
  </#if>
   <#assign grouping =  matchTag.grouping> 
  <div class="sectionHeader" onclick="toggle('${grouping}')">${grouping}</div>
  <table class="categoryTable" id="${grouping}">
    <tr>
    <#assign category = ""> 
</#if>
<#if category == "" || category != matchTag.category>
   <#if category != "">
   </tr>
   </table>	
   </td>
   </#if>
   <#assign category =  matchTag.category>
   <td>
   <div class="cellTitle">${category}</div>
   <table>
</#if>
   <tr>
   <td> ${matchTag.tag} </td>
   <td> 
   <#if matchTag.inputType == "checkbox">
   		<#if matchTag.occurences != 0>
   			<input type="checkbox" onclick="toggleTag('${matchTag.tag}', this.checked);" checked>
   		<#else> 
   			<input type="checkbox" onclick="toggleTag('${matchTag.tag}', this.checked);">
   		</#if> 
   <#elseif matchTag.inputType == "incremental">
   		<input type="text" size="2" id="${matchTag.tag}-counter" value="${matchTag.occurences}" disabled />
   		<img src="../images/SmallPlus.png" onclick="incrementTag('${matchTag.tag}');" >
   		<img src="../images/SmallMinus.png" onclick="decrementTag('${matchTag.tag}');" >
   </#if>
   </td>
   </tr>
</#list>
 <#if category != "">
   </tr>
   </table>	
   </td>
   </#if>
  <#if grouping != "">
     </tr>
  </table>   
  </#if>
  </form>
 </div>
</body>
</html>