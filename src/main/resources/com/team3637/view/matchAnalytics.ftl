<#import "spring.ftl" as spring />
<html>
<head>
<style>

body {
	background-color: #9fb7b7;
}

.categoryTable{
    border-spacing: 0px;  
}

.categoryTable table{
    width:100%;
    display:block;
    border-collapse:collapse;
}

.tagTable td{
    font-size: 12px;
    border-collapse: collapse;  
    border: 1px solid gray !important; 
}
  
.categoryTable td{ 
    border: 0px solid gray; 
    vertical-align:top;
}

.sectionWrapper {
  display: inline-block;
}

.sectionParent {
  display: flex;
  flex-direction: column;
} 

.sectionHeader {
    background-color:#00A22E;
    font-weight:bold; 
    text-align:center;
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;            
} 

.teamHeaderRed th{
    background-color:#8F0000; 
    font-weight:bold;
    font-size:17px;
    margin-left: 10px;
    margin-right: 10px;
    padding-left:3px;  
    padding-right:25px;
    border-collapse:collapse;
    color:white;     
    text-align:left;
    width:100%;
    border-spacing: 0px;
}

.teamHeaderBlue th{
    background-color:1D1B9F; 
    font-weight:bold;
    font-size:17px;
    margin-left: 10px;
    margin-right: 10px;
    padding-left:3px;  
    padding-right:25px;
    border-collapse:collapse;
    color:white;     
    text-align:left;
    width:100%;
    border-spacing: 0px;
}

.teamHeader label{
    background-color:#005EFF;
    font-weight:bold;
    font-size:14px;
    padding-left:0px;
    padding-right:0px; 
    border-collapse:collapse; 
    color:white; 
} 

.teamInput {
    width:50px;
    color:black;
    font-weight:normal;
    height: 25px;
    border: 1px solid gray !important;    
}    

.categoryTitle {
    text-align: 
    center; height: 20px; 
    background-color:#CFA600; 
    font-size:13px;  
    font-weight:bold;
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;        
}

.columnTitle {
     text-align: center
}

.matchHeader th{
    background-color:#000000;
    font-weight:bold;
    font-size:20px;
    padding-left:25px;
    padding-right:25px;
    border-collapse:collapse;
}
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
    <th><p style="color:white; width:140px;">Matches Played: ${team.matches}</p></th>
    <th><p style="color:white; width:130px;">Avg. Score: ${team.avgScore}</p></th>
    <th><p style="color:white; width:130px;">Our Score: ${team.ourScore!}</p></th>
    <th><p style="color:white; width:120px;">W/L/T: ${team.wins}:${team.losses}/${team.ties}</p></th>
    <th><p style="color:white; width:150px;">Ranking Points: ${team.rankingpoints}</p></th>
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
   </div>
  </div>
  <br>   
  </#if>
   <#assign grouping = matchStatistic.grouping> 
   <#assign tableId = team.team + "-" + grouping> 
   <div class="sectionWrapper">
  <div class="sectionParent">
  <div class="sectionHeader" onclick="toggle('${matchTeams.match}-match-${tableId}')">${grouping}</div>
  <table class="categoryTable" id="${matchTeams.match}-match-${tableId}">
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
   <div class="categoryTitle" onclick="toggle('${matchTeams.match}-match-${team}-${grouping}-${category}-table')">${category}</div>
   <table class="tagTable"  id="${matchTeams.match}-match-${team}-${grouping}-${category}-table">
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
 </div>
 </div> 
  </#if>
 <#assign teamNum =  team.team>
 </#list>
 </div>
 </div>
 </#list>
 </div>
</body>
</html>