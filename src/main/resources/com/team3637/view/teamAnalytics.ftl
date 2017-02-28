<#import "spring.ftl" as spring />
<html>
<head>
<style>
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
    background-color:#53EA0C;
    font-weight:bold; 
    text-align:center;
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;            
} 

.teamHeader { 
    width:100%;
    border-spacing: 0px;
}

.teamHeader th{
    background-color:#005EFF; 
    font-weight:bold;
    font-size:17px;
    padding-left:25px;  
    padding-right:25px;
    border-collapse:collapse;
    color:white;     
    text-align:left;
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
    background-color:#FFD500; 
    font-size:13px;  
    font-weight:bold;
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;        
}

.columnTitle {
     text-align: center
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
<#assign teamNum = -1>
<#list teams as team>
<#if teamNum == -1 || teamNum != team.team>
<#if teamNum != -1>
</div>
</#if>
<div onclick="toggle('${team}-team')">
<table class = "teamHeader" cellspacing = "0">
  <tr>
    <th><p style="color:white; width:100px;">Team: ${team.team}</p></th>
    <th><p style="color:white; width:140px;">Matches Played: ${team.matches}</p></th>
    <th><p style="color:white; width:150px;">Avg.Score: ${team.avgScore}</p></th>
    <th><p style="color:white; width:130px;">Our Score: ${team.ourScore!}</p></th>
    <th><p style="color:white; width:180px;">W/L/T Ratio: ${team.wins}/${team.losses}/${team.ties}</p></th>
  </tr>
</table>
</div>
<div id="${team}-team" style = "display:none;">
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
  <div class="sectionHeader" onclick="toggle('${team}-${grouping}')">${grouping}</div>
  <table class="categoryTable" id="${team}-${grouping}">
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
   <div class="categoryTitle" onclick="toggle('${team}-${grouping}-${category}-table')">${category}</div>
   <table class="tagTable"  id="${team}-${grouping}-${category}-table">
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
  </div>
  </#if>
 <#assign teamNum =  team.team>
 </#list>
 </div>
</body>
</html>