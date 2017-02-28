<#import "spring.ftl" as spring />
<html>
<head>
<style>
.body {
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

.teamHeader { 
    width:100%;
    border-spacing: 0px;
}

.teamHeader th{
    background-color:#8F0000; 
    font-weight:bold;
    font-size:20px;
    padding-left:25px;
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

function saveMatchRankingPoints(rankingPoints) {
        if (isInt(rankingPoints)) 
           $.get("../m/saveMatchRankingPoints?team="+team+"&match="+match+"&rankingPoints="+rankingPoints);
        else 
           alert("Enter valid ranking.");   
}  

function saveMatchPenalty(penalty) {
        if (isInt(penalty)) 
           $.get("../m/saveMatchPenalty?team="+team+"&match="+match+"&penalty="+penalty);
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
<div class = "teamHeader">Match Tags</div>
<table class = "teamHeader">
  <tr>
    <th>Tag</th>
    <th>Grouping</th>
    <th>Category</th>
    <th>Input Type</th>
  </tr>
  <#list matchTags as matchTag>
  <tr>
  <td>${matchTag.tag}</td>
  <td>${matchTag.grouping}</td>
  <td>${matchTag.category}</td>
  <td>${matchTag.inputType}</td>
  </tr>
  </#list>
</table>
<div class = "teamHeader">Team Tags</div>
<table class = "teamHeader">
  <tr>
    <th>Tag</th>
    <th>Grouping</th>
    <th>Category</th>
    <th>Input Type</th>
  </tr>
  <#list teamTags as teamTag>
  <tr>
  <td>${teamTag.tag}</td>
  <td>${teamTag.grouping}</td>
  <td>${teamTag.category}</td>
  <td>${teamTag.inputType}</td>
  </tr>
  </#list>
</table>
</form>
</body>
</html>