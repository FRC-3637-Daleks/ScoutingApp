<#import "spring.ftl" as spring />
<html>
<head>
<style>

#categoryTable table{
	width:100%;
	display:block;
}

#categoryTable td{
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
    <th><p style="color:white">Matches Played: ${matches}</p></th>
    <th><p style="color:white">Avg.Score: ${avgScore}</p></th>
    <th><p style="color:white">Our Score: ${ourScore}</p></th>
    <th><p style="color:white">Win/Lose Ratio: ${wins}:${losses}</p></th>
  </tr>
</table>
<div id=${team}>
<#assign grouping = ""> 
<#assign category = ""> 
<#list matchStatistics as matchStatistic>
<#if grouping == "" || grouping != matchStatistic.grouping> 
   <#if category != "">
   </tr>
   </table>	
   </td>
   </#if>
  <#if grouping != "">
     </tr>
  </table>   
  </#if>
   <#assign grouping =  matchStatistic.grouping> 
  <div class="sectionHeader" onclick="toggle('${grouping}')">${grouping}</div>
  <table class="categoryTable" id="${grouping}">
    <tr>
    <#assign category = ""> 
</#if>
<#if category == "" || category != matchStatistic.category>
   <#if category != "">
   </tr>
   </table>	
   </td>
   </#if>
   <#assign category =  matchStatistic.category>
   <td>
   <div class="cellTitle">${category}</div>
   <table>
   <tr>
   <td> ${matchStatistic.tag} </td>
   </tr>
<#else>
   <tr>
   <td> ${matchStatistic.tag} </td>
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
 </div>
</body>
</html>