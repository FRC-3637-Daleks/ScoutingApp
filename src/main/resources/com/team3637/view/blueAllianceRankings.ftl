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
function updatePage()
{
      document.location.href = "../analytics/blueAllianceRankings?event="+$('#eventSelector').val();
}
function goTo(href)
{
     document.location.href =href +"?event="+$('#eventSelector').val(); 
}
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
<#list teamRankingsList as teamRanking> 
<table class = "tagHeader" cellspacing = "0">
  <tr>
    <th><p>Rank: <span class = "name">${teamRanking.rank}</span></p></th>
    <th><p>Team: <span class = "name">${teamRanking.team}</span></p></th>
    <th><p>Matches Played: <span class = "name">${teamRanking.matchesPlayed}</span></p></th>
    <th><p>Qualification Average: <span class = "name">${teamRanking.qualAverage}</span></p></th>
    <th><p>Disqualifications: <span class = "name">${teamRanking.disqualifications}</span></p></th>
    <th><p>W/L/T: <span class = "name">${teamRanking.wins}/${teamRanking.losses}/${teamRanking.ties}</span></p></th>
  </tr>
</table>
 </#list>
</body>
</html>