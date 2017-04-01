<#import "spring.ftl" as spring />
<html>
<head>
<style>

body {
	background-color: #ebebe0;
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
    padding-right: 5px;
    padding-left: 5px;
}

.tagTableGray tr{
   background-color:#ebebe0;
}
  
  
.tagTableWhite tr{
   background-color:#ffffff;
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
    font-size:15px;
    margin-left: 10px;
    margin-right: 10px;
    padding-left:3px;  
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
<script src="../bootstrap/js/bootstrap.min.js"></script> 
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

function registerPopOvers()
{
  $("a.total-occurrences-popover-ajax").each(function() {
           $(this).popover({  
              trigger:"focus",
              placement: function (context, source) { 
                    var obj = $(source);
                    $.get(obj.data("url"),function(d) {
                              var jsonObject =  JSON.parse(d); 
                              var popoverHTML = '<div>'+obj.data("title")+'</div><table style="width: 100%"><tr><td width="50%">Match</td><td  width="50%">#</td></tr>';
                              $.each(jsonObject, function( key, val ) {
                                        popoverHTML+='<tr><td>' + val.match+'</td><td>'+val.occurrences+'</td></tr>';    
                              }); 
                             popoverHTML += '</table>';  
                             $(context).html(popoverHTML) ; 
                    });
              },
              content: "loading",
              html:true   
           });   
      });     
}

$(document).ready(function(){
   registerPopOvers();
});

</script>
</head>
<body style="background-color: #ebebe0">
<#if !export??>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand page-scroll" href="../">Team 3637 Scouting App</a> 
        </div>
        <div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav"> 
                <li><a href="../analytics/teamAnalyticsByMatch">By Match</a></li>
            </ul> 
            <ul class="nav navbar-nav"> 
                <li><a href="../analytics/exportTeamAnalytics">Export</a></li>
            </ul> 
            <ul class="nav navbar-nav"> 
                <li><a href="../">Back</a></li>
            </ul> 
        </div>
    </div>
</nav>
</#if>
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
    <th><p style="color:white; width:130px;">Avg. Score: ${team.avgScore}</p></th>
    <th><p style="color:white; width:130px;">Our Score: ${team.ourScore!}</p></th>
    <th><p style="color:white; width:120px;">W/L/T: ${team.wins}/${team.losses}/${team.ties}</p></th>
    <th><p style="color:white; width:150px;">Ranking Points: ${team.rankingpoints}</p></th>
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
   <td><a href="javascript:void(0);"  data-title="${matchStatistic.tag}" data-template='<div class="popover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>' data-url="/ScoutingApp/m/getMatchTags?team=${team}&tag=${matchStatistic.tag}" class="total-occurrences-popover-ajax">${matchStatistic.totalOccurrences}</a> </td>
   </tr>
<#else> 
   <tr>       
   <td> ${matchStatistic.tag} </td>  
   <td><a href="javascript:void(0);"  data-title="${matchStatistic.tag}"  data-template='<div class="popover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>' data-url="/ScoutingApp/m/getMatchTags?team=${team}&tag=${matchStatistic.tag}" class="total-occurrences-popover-ajax">${matchStatistic.totalOccurrences}</a> </td>
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
  <div class="sectionHeader">Scouting Comments</div>
 <textarea disabled  rows="3" cols="100" >${team.scoutingComments!}</textarea>
 </div>
  </div>
  </div>
  </#if>
 <#assign teamNum =  team.team>
 </#list>
 </div>
</body>
</html>