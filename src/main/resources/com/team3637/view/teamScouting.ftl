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
    padding-left: 3px; 
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

.teamHeader { 
    width:100%;
    border-spacing: 0px;
}

.teamHeader th{
    background-color:#8F0000; 
    font-weight:bold;
    font-size:17px;
    margin-left: 10px;
    margin-right: 10px;
    padding-left:3px;  
    padding-right:25px;
    border-collapse:collapse;
    color:white;    
}

.teamHeader label{
    background-color:#8F0000;
    font-weight:bold;
    font-size:20px;
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
</style> 
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="../css/bootstrap-tokenfield.css" rel="stylesheet"/>
<link href="../css/jquery-ui.css" rel="stylesheet"/>
<link href="../css/bootstrap-slider.css" rel="stylesheet"/> 
<link href="../css/chosen.css" rel="stylesheet"/>
<link href="../css/main.css" rel="stylesheet"/>
<script src="../js/jquery.min.js"></script>
<script>
var team=${team.team};
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
		$.get("../t/incrementTag?tag="+target+"&team="+team);
	else
		$.get("../t/decrementTag?tag="+target+"&team="+team);
}

function incrementTag(target) {
	var incrementElement=document.getElementById(target+"-counter");
	var currentValue=incrementElement.value;
	if (!currentValue) {
		currentValue="1";  
	}
	else
		currentValue=Number(currentValue)+1;
	incrementElement.value=currentValue;
	$.get("../t/incrementTag?tag="+target+"&team="+team);
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
		$.get("../t/decrementTag?tag="+target+"&team="+team);
	}
	decrementElement.value=currentValue;
	
}

function saveTeamRankingPoints(rankingPoints) {
        if (isInt(score)) 
           $.get("../t/teamRankingPoints?team="+team+"&rankingPoints="+rankingPoints);
        else 
           alert("Enter valid ranking points.");   
}  

function isInt(value) {
  return !isNaN(value) && 
         parseInt(Number(value)) == value && 
         !isNaN(parseInt(value, 10));
}
</script>
</head>
<body  style="background-color: #ebebe0">       
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand page-scroll" href="../">Team 3637 Scouting App</a> 
        </div>
        <div id="navbar" class="collapse navbar-collapse"> 
            <ul class="nav navbar-nav"> 
                <li><a href="../t/">Save & Exit</a></li>
            </ul> 
        </div>
    </div>
</nav>
<form style="margin:0;"> 
<table class = "teamHeader">
  <tr>
    <th onclick="toggle('${team.team}')">Team: ${team.team}</th>
    <th>Avg. Score:  ${team.avgScore!?string("0.#")}</th>
    <th>Ranking Points: ${team.rankingPoints!}</th>  
    <th>Wins: ${team.wins}</th>
    <th>Ties: ${team.wins}</th>
    <th>Losses: ${team.wins}</th>       
  </tr>
</table>
</form>
<div id="${team.team}-team">  
<form>
<#assign grouping = ""> 
<#assign category = ""> 
<#list team.teamTags as teamTag>
<#if grouping == "" || grouping != teamTag.grouping> 
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
   <#assign grouping =  teamTag.grouping> 
  <div class="sectionWrapper">
  <div class="sectionParent">
  <div class="sectionHeader" onclick="toggle('${grouping}')">${grouping}</div>
  <table class="categoryTable" id="${grouping}">
    <tr>
    <#assign category = ""> 
</#if>
<#if category == "" || category != teamTag.category>
   <#if category != "">
   </tr>
   </table>	
   </td>
   </#if>
   <#assign category =  teamTag.category>
   <td>
   <div class="categoryTitle" onclick="toggle('${grouping}-${category}-table')">${category}</div>
   <table class="tagTable"  id="${grouping}-${category}-table">
</#if> 
   <tr>
   <td> ${teamTag.tag} </td>
   <td> 
   <#if teamTag.inputType == "checkbox">
   		<#if teamTag.occurrences != 0>
   			<input type="checkbox" onclick="toggleTag('${teamTag.tag}', this.checked);" checked>
   		<#else> 
   			<input type="checkbox" onclick="toggleTag('${teamTag.tag}', this.checked);">
   		</#if> 
   <#elseif teamTag.inputType == "incremental">
   		<input type="text" style="width: 20px;" id="${teamTag.tag}-counter" value="${teamTag.occurrences}" disabled />
   		<img src="../images/SmallPlus.png" onclick="incrementTag('${teamTag.tag}');" >
   		<img src="../images/SmallMinus.png" onclick="decrementTag('${teamTag.tag}');" >
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
  </div>
  </div>
  </#if>
  </form>
 </div>
</body>
</html>