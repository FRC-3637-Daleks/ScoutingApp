<#import "spring.ftl" as spring />
<html>
<head>
<style>

.categoryTable table{
	width:100%;
	display:block;
	border-collapse:collapse;
}

.categoryTable td{
    border: 1px solid gray;
    vertical-align:top;
}

.sectionHeader {
    background-color:#53EA0C;
    font-weight:bold;
    text-align:center;
}

.teamHeader th{
    background-color:#005EFF;
    font-weight:bold;
    font-size:20px;
    padding-left:25px;
    padding-right:25px;
    border-collapse: collapse;
}
.categoryTitle {text-align: center; height: 20px; background-color:#FFD500; font-size:13px; font-weight:bold}
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
<#assign teamNum = -1>
<#list teams as team>
<#if teamNum == -1 || teamNum != team.team>
<#if teamNum != -1>
</div>
</#if>
<div onclick="toggle('${team}')">
<table class="teamHeader">
  <tr>
    <th><p style="color:white">Team: ${team.team}</p></th>
    <th><p style="color:white">Matches Played: ${team.matches}</p></th>
    <th><p style="color:white">Avg.Score: ${team.avgScore}</p></th>
    <th><p style="color:white">Our Score: ${team.ourScore!}</p></th>
    <th><p style="color:white">Win/Lose Ratio: ${team.wins}:${team.losses}</p></th>
  </tr>
</table>
</div>
<div id=${team} style = "display:none;">
</#if>
<#assign grouping = ""> 
<#assign category = ""> 
<#list team.matchStatistics as matchStatistic>
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
   <#assign grouping = matchStatistic.grouping> 
   <#assign tableId = team.team + "-" + grouping> 
  <div class="sectionHeader" onclick="toggle('${tableId}')">${grouping}</div>
  <table class="categoryTable" id="${tableId}">
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
   <div class="categoryTitle">${category}</div>
   <table>
   <tr>
   <td> ${matchStatistic.tag} </td>
   <td> ${matchStatistic.totalOccurences} </td>
   </tr>
<#else>
   <tr>
   <td> ${matchStatistic.tag} </td>
   <td> ${matchStatistic.totalOccurences} </td>
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
 <#assign teamNum =  team.team>
 </#list>
 </div>
</body>
</html>