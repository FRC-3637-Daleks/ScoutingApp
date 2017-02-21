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

.teamHeaderBlue th{
    background-color:#0000FF;
    font-weight:bold;
    font-size:20px;
    padding-left:25px;
    padding-right:25px;
    border-collapse:collapse;
}

.teamHeaderRed th{
    background-color:#FF0000;
    font-weight:bold;
    font-size:20px;
    padding-left:25px;
    padding-right:25px;
    border-collapse:collapse;
}

.matchHeader th{
    background-color:#000000;
    font-weight:bold;
    font-size:20px;
    padding-left:25px;
    padding-right:25px;
    border-collapse:collapse;
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
<#list matchTeamsList as matchTeams>
<div onclick="toggle('${matchTeams.match}-match')">
<table class = "matchHeader" cellspacing = "0">
  <tr>
    <th><p style="color:white; width:100px;">Match: ${matchTeams.match}</p></th>
  </tr>
</table>
</div>
<div id="${matchTeams.match}-match"  style = "display:none;">
<#assign teams = matchTeams.teams> 
<#assign teamNum = -1> 

<#assign teamHeaderStyle = "teamHeaderRed">
<#list teams as team>
<#if teamNum == -1 || teamNum != team.team>
<#if teamNum != -1>  
</div>
</#if>
<div onclick="toggle('${matchTeams.match}-match-${team}-team')">
<table class = "${teamHeaderStyle}" cellspacing = "0">
  <tr>
    <th><p style="color:white; width:100px;">Team: ${team.team}</p></th>
    <th><p style="color:white; width:170px;">Matches Played: ${team.matches}</p></th>
    <th><p style="color:white; width:130px;">Avg.Score: ${team.avgScore}</p></th>
    <th><p style="color:white; width:130px;">Our Score: ${team.ourScore!}</p></th>
    <th><p style="color:white; width:180px;">Win/Lose Ratio: ${team.wins}:${team.losses}</p></th>
  </tr>
</table>  
</div>
<div id="${matchTeams.match}-match-${team}-team" style = "display:none;">
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
  <div class="sectionHeader" onclick="toggle('${matchTeams.match}-match-${tableId}')">${grouping}</div>
  <table class="categoryTable" id="${matchTeams.match}-match-${tableId}')">
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
 </div>
 </div>
 </#list>
</body>
</html>