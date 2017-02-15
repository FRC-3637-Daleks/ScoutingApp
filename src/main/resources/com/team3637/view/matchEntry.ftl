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
   <tr>
   <td> ${matchTag.tag} </td>
   <td> 
   <#if matchTag.inputType == "checkbox">
   <input type="checkbox" />
   </#if>
   </td>
   </tr>
<#else>
   <tr>
   <td> ${matchTag.tag} </td>
   </tr>
</#if>
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