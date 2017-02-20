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
    font-size:20px;
    padding-left:25px;
    padding-right:25px;
    border-collapse:collapse;
    color:white;    
}

.teamHeader label{
    background-color:#005EFF;
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
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="../css/bootstrap-tokenfield.css" rel="stylesheet"/>
<link href="../css/jquery-ui.css" rel="stylesheet"/>
<link href="../css/bootstrap-slider.css" rel="stylesheet"/> 
<link href="../css/chosen.css" rel="stylesheet"/>
<link href="../css/main.css" rel="stylesheet"/>
<script src="../js/jquery.min.js"></script>
<script>
var team=${teamMatchResult.team};
var match=${teamMatchResult.match};
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

function saveMatchResult(result) { 
        $.get("../m/saveMatchResult?team="+team+"&match="+match+"&result="+result);
}

function saveMatchScore(score) {
        if (isInt(score)) 
           $.get("../m/saveMatchScore?team="+team+"&match="+match+"&score="+score);
        else 
           alert("Enter valid score.");   
}  

function isInt(value) {
  return !isNaN(value) && 
         parseInt(Number(value)) == value && 
         !isNaN(parseInt(value, 10));
}
</script>
</head>
<body>   
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand page-scroll" href="../">Team 3637 Scouting App</a> 
        </div>
        <div id="navbar" class="collapse navbar-collapse"> 
            <ul class="nav navbar-nav"> 
                <li><a href="../s/">Back</a></li>
            </ul> 
        </div>
    </div>
</nav>
<form style="margin:0;">
<table class = "teamHeader">
  <tr>
    <th onclick="toggle('${teamMatchResult.team}')">Team: ${teamMatchResult.team}</th>
    <th>Match: ${teamMatchResult.match}</th>
    <th>Score: <input type="text" name="score" class="teamInput" onchange="saveMatchScore(this.value);" value="${teamMatchResult.score!}"></th>
    <th><input type="radio" name="result" id="radioWin" value="win" onclick="saveMatchResult(this.value)"  <#if teamMatchResult.win>checked</#if>><label for="radioWin">Win</label></th>
    <th><input type="radio" name="result" id="radioTie" value="tie"  onclick="saveMatchResult(this.value)"  <#if teamMatchResult.tie>checked</#if>><label for="radioTie">Tie</label></th>
    <th><input type="radio" name="result" id="radioLoss" value="loss"  onclick="saveMatchResult(this.value)"  <#if teamMatchResult.loss>checked</#if>><label for="radioLoss">Loss</label></th>    
  </tr>
</table>
</form>
<div id="${teamMatchResult.team}-team">  
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
  </div>
  </div>
  <br>  
  </#if>
   <#assign grouping =  matchTag.grouping> 
  <div class="sectionWrapper">
  <div class="sectionParent">
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
   <div class="categoryTitle" onclick="toggle('${grouping}-${category}-table')">${category}</div>
   <table class="tagTable"  id="${grouping}-${category}-table">
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
   		<input type="text" style="width: 20px;" id="${matchTag.tag}-counter" value="${matchTag.occurences}" disabled />
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
  </div>
  </div>
  </#if>
  </form>
 </div>
</body>
</html>