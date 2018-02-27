<#import "spring.ftl" as spring />
<html>
<head>
<style>


body {
	background-color: #ebebe0;
}

.dataNotImported {
    background-color: #cecec4;
}

.dataImported {
    background-color:#ebebe0 ; 
}

.categoryTable{
    border-spacing: 0px;
    font-size:15px;
}

.categoryTable table{
    width:100%;
    display:block;
    border-collapse:collapse;
}

.tagTable th{
    font-size:15px;
    padding-left:25px;
    padding-right:25px;
    border-collapse:collapse;
    border: 1px solid gray !important;
    background-color:#BC2132;
    color:#E9E9E9
}

.tagTable td{
    font-size: 12px;
    border-collapse: collapse;  
    border: 1px solid gray !important; 
    padding-right: 5px;
    padding-left: 5px;  
    text-align: center;
}
  
.categoryTable td{ 
    border: 0px solid gray; 
    border: 1px solid gray !important; 
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
    background-color:#BC2132;
    color:#E9E9E9;
    font-weight:bold; 
    text-align:center;
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;            
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
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;        
}

.columnTitle {
     text-align: center
}

.tagHeader th{
    font-size:15px;
    background-color:#ebebe0;
    width:1%;
    border-spacing: 0px;
    font-color:black;
}

hr.style1 {
	border-color:#000000;
    padding-right:0px;
    margin-top:5px;
    margin-bottom:5px;
}

span.name { color:#545454; }

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

function updatePage()
{
      document.location.href = "../analytics/awardAnalytics?event="+$('#eventSelector').val();
}

function toggleComments(hide)
{  
if (hide)
$('.teamComments').hide();
else  
$('.teamComments').show();
}

function goTo(href)
{
     document.location.href =href +"?event="+$('#eventSelector').val(); 
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
                <li> 
                   <form class="form-inline">    
                    <div class="control-group">  
                    <label for"reportSelector" style="color:#9d9d9d;">Report:</label>  
                     <select class="form-control input-sm"   id="reportSelector" onchange='goTo("../analytics/"+this.value)'>    
                         <#if selectedReportType == "teamAnalyticsByMatch">
                            <option value="teamAnalyticsByMatch" selected>By Match</option>
                         <#else>   
                         	<option value="teamAnalyticsByMatch">By Match</option>
                         </#if>
                         <#if selectedReportType == "teamAnalytics">
                            <option value="teamAnalytics" selected>All Teams</option>
                         <#else>   
                         	<option value="teamAnalytics">All Teams</option>
                         </#if>
                         <#if selectedReportType == "tagAnalytics">
                            <option value="tagAnalytics" selected>By Tag</option>
                         <#else>   
                         	<option value="tagAnalytics">By Tag</option>
                         </#if>
                          <#if selectedReportType == "awardAnalytics">
                            <option value="awardAnalytics" selected>Awards</option>
                         <#else>   
                         	<option value="awardAnalytics">Awards</option>
                         </#if>
                         <#if selectedReportType == "blueAllianceRankings">
                            <option value="blueAllianceRankings" selected>Blue Alliance Rankings</option>
                         <#else>   
                         	<option value="blueAllianceRankings">Blue Alliance Rankings</option>
                         </#if>
                  	 </select>
                     <label for"eventSelector" style="color:#9d9d9d;">Event:</label>  
                     <select class="form-control input-sm"   id="eventSelector" onchange="updatePage()"> 
                     <#list events as event>
                        <#if selectedEvent == event>   
                            <option value="${event}" selected>${event}</option>     
                        <#else>
                             <option value="${event}">${event}</option> 
                        </#if>    
                     </#list> 
                   </select>
                   	 </select>
                   	 </select>   
                   </div>
                   </form> 
                 </li>  
            </ul>              
        </div>
    </div>
</nav>
</#if>
<#list teamAwardsList as teamAward> 
<div onclick="toggle('${teamAward.team}-team')">
<table class = "tagHeader" cellspacing = "0">
  <tr>
    <th><p>Team: <span class = "name">${teamAward.team}</span></p></th>
    <th><p>Total Awards: <span class = "name">${teamAward.awardCount}</span></p></th>
  </tr>
</table>
</div>
<div id="${teamAward.team}-team" style = "display:none;">
  <div class="sectionHeader" onclick="toggle('${teamAward.team}-award')">Awards</div>
  <table class="categoryTable" border = 1px id="${teamAward.team}-">
   <tr>
   <th width = 500px style="background-color:#ADADAD; color:#333; text-align:center;">Award</th>
   <th width = 200px style="background-color:#ADADAD; color:#333; text-align:center;">Event</th>
   <th width = 200px style="background-color:#ADADAD; color:#333; text-align:center;">Year</th>
   </tr>
    <#list teamAward.awards as nextAward>
   <tr>
   <td>${nextAward.name}</td>
   <td>${nextAward.eventId}</td>
   <td>${nextAward.year}</td>
   </#list>
   </table>
   </div>
   </div>
 </#list>
 </div>
</body>
</html>