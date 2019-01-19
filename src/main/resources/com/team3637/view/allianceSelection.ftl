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
    background-color:#BC2132;
    font-weight:bold; 
    color:#E9E9E9;
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
    background-color:#ebebe0; 
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
    color:black; 
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
    background-color:#ADADAD;
    font-size:13px;  
    font-weight:bold;
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;        
}

.columnTitle {
     text-align: center
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

function changeEvent(eventId)
{
      document.location.href = "../analytics/teamAnalytics?event="+eventId+"&hideComments="+$('#hideComments').is(':checked');  
}

function changeAllianceSelection(team, alliance, order, oldteam)
{
      $.get("../analytics/saveAllianceSelection?event="+$('#eventSelector').val()+"&team="+team+"&alliance="+alliance+"&order="+order); 
      if (team != -1)
  	    toggle(team + '-team-all');
  	  if(oldteam != -1)
  	 	toggle(oldteam + '-team-all');
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
     document.location.href =href +"?event="+$('#eventSelector').val()+"&hideComments="+$('#hideComments').is(':checked');
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
                         <#if selectedReportType == "allianceSelection">
                            <option value="allianceSelection" selected>Alliance Selection</option>
                         <#else>   
                         	<option value="allianceSelection">Alliance Selection</option>
                         </#if>
                  	 </select>
                     <label for"eventSelector" style="color:#9d9d9d;">Event:</label>  
                     <select class="form-control input-sm"   id="eventSelector" onchange="changeEvent(this.value)"> 
                     <#list events as event>
                        <#if selectedEvent == event>   
                            <option value="${event}" selected>${event}</option>     
                        <#else>
                             <option value="${event}">${event}</option> 
                        </#if>    
                     </#list> 
                   </select>   
                    <label for"hideComments" style="color:#9d9d9d;">Hide Comments:</label>  
                    <#if hideComments>                     
                   <input type"checkbox" type="checkbox" id="hideComments" value="hide" class="form-control input-sm" onchange="toggleComments(this.checked);"  checked/> 
                   <#else>
                   <input type"checkbox" type="checkbox" id="hideComments" value="hide" class="form-control input-sm" onchange="toggleComments(this.checked);" /> 
                   </#if>
                   </div>
                   </form> 
                 </li>  
            </ul>              
        </div>
    </div>
</nav>
</#if>
<div>
<table class = "Table">
<tr>
<th>Alliance 1</th>
<th>Alliance 2</th>
<th>Alliance 3</th>
<th>Alliance 4</th>
<th>Alliance 5</th>
<th>Alliance 6</th>
<th>Alliance 7</th>
<th>Alliance 8</th>
</tr>
<tr>
<td> 
	<select class="form-control input-sm"   id="al1-1" onfocus ="this.oldvalue = this.value" onchange="changeAllianceSelection(this.value, 1, 1, this.oldvalue); this.oldvalue = this.value;"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
  		<#if k == 1>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance11 = value_list[seq_index] />
  		</#if>
	</#list>
    	<#list teams as team>
       		<#if team.team == alliance11.team1!0> 
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al2-1" onchange="changeAllianceSelection(this.value, 2, 1)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 2>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance21 = value_list[seq_index] />
  		</#if>
	</#list>
    	<#list teams as team>
       		<#if team.team == alliance21.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>
         </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al3-1" onchange="changeAllianceSelection(this.value, 3, 1)"> 
    	<option value="-1">-select-</option>'
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k> 
	  		<#if k == 3>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance31 = value_list[seq_index] />
  		</#if>
	</#list>
  <#list teams as team>
       		<#if team.team == alliance31.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al4-1" onchange="changeAllianceSelection(this.value, 4, 1)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 4>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance41 = value_list[seq_index] />
  		</#if>
	</#list> 
 <#list teams as team>
       		<#if team.team == alliance41.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al5-1" onchange="changeAllianceSelection(this.value, 5, 1)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k> 
	  		<#if k == 5>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance51 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance51.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al6-1" onchange="changeAllianceSelection(this.value, 6, 1)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 6>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance61 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance61.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al7-1" onchange="changeAllianceSelection(this.value, 7, 1)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 7>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance71 = value_list[seq_index] />
  		</#if>
	</#list> 
<#list teams as team>
       		<#if team.team == alliance71.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select> 
</td>
<td> 
	<select class="form-control input-sm"   id="al8-1" onchange="changeAllianceSelection(this.value, 8, 1)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 8>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance81 = value_list[seq_index] />
  		</#if>
	</#list>
 <#list teams as team>
       		<#if team.team == alliance81.team1!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
</tr>
<tr>
<td> 
	<select class="form-control input-sm"   id="al1-2" onchange="changeAllianceSelection(this.value, 1, 2)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 1>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance12 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance12.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al2-2" onchange="changeAllianceSelection(this.value, 2, 2)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k> 
	  		<#if k == 2>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance22 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance22.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al3-2" onchange="changeAllianceSelection(this.value, 3, 2)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 3>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance32 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance32.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al4-2" onchange="changeAllianceSelection(this.value, 4, 2)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 4>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance42 = value_list[seq_index] />
  		</#if>
	</#list> 
<#list teams as team>
       		<#if team.team == alliance42.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al5-2" onchange="changeAllianceSelection(this.value, 5, 2)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 5>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance52 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance52.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al6-2" onchange="changeAllianceSelection(this.value, 6, 2)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 6>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance62 = value_list[seq_index] />
  		</#if>
	</#list>
 <#list teams as team>
       		<#if team.team == alliance62.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al7-2" onchange="changeAllianceSelection(this.value, 7, 2)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 7>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance72 = value_list[seq_index] />
  		</#if>
	</#list> 
<#list teams as team>
       		<#if team.team == alliance72.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>    
</td>
<td> 
	<select class="form-control input-sm"   id="al8-2" onchange="changeAllianceSelection(this.value, 8, 2)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 8>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance82 = value_list[seq_index] />
  		</#if>
	</#list> 
<#list teams as team>
       		<#if team.team == alliance82.team2!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>      
</td>
</tr>
<tr>
<td> 
	<select class="form-control input-sm"   id="al1-3" onchange="changeAllianceSelection(this.value, 1, 3)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 1>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance13 = value_list[seq_index] />
  		</#if>
	</#list> 
<#list teams as team>
       		<#if team.team == alliance13.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al2-3" onchange="changeAllianceSelection(this.value, 2, 3)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 2>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance23 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance23.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al3-3" onchange="changeAllianceSelection(this.value, 3, 3)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 3>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance33 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance33.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al4-3" onchange="changeAllianceSelection(this.value, 4, 3)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 4>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance43 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance43.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al5-3" onchange="changeAllianceSelection(this.value, 5, 3)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 5>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance53 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance53.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al6-3" onchange="changeAllianceSelection(this.value, 6, 3)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 6>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance63 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance63.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al7-3" onchange="changeAllianceSelection(this.value, 7, 3)"> 
    	<option value="-1">-select-</option> 
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 7>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance73 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance73.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
        </select>   
</td>
<td> 
	<select class="form-control input-sm"   id="al8-3" onchange="changeAllianceSelection(this.value, 8, 3)"> 
    	<option value="-1">-select-</option>
    <#assign key_list = allianceSelection?keys />
    <#assign value_list = allianceSelection?values >
	<#list key_list as k>
	  		<#if k == 8>
  		  <#assign seq_index = key_list?seq_index_of(k) />
  		  <#assign alliance83 = value_list[seq_index] />
  		</#if>
	</#list>
<#list teams as team>
       		<#if team.team == alliance83.team3!0>   
        		<option value="${team}" selected>${team}</option>     
        	<#else>
        		<option value="${team}">${team}</option> 
        	</#if>  
        </#list> 
        </select>   
</td>
</tr>
</table>
</div>
<#assign teamNum = -1>
<#list teams as team>
<#if teamNum == -1 || teamNum != team.team>
<#if teamNum != -1>
</div>
</#if>
<#assign displayStyle = "display:block" />
<#assign key_list = allianceSelection?keys />
<#assign value_list = allianceSelection?values >
<#list key_list as k>
    <#assign seq_index = key_list?seq_index_of(k) />
    <#assign alliance = value_list[seq_index] />
  <#if (alliance.team1!0) == team.team || (alliance.team2!0) == team.team || (alliance.team3!0) == team.team> 
    <#assign displayStyle = "display:none" /> 
  </#if>
</#list>
<div id="${team}-team-all" style="${displayStyle}">
<div onclick="toggle('${team}-team')">
<table class = "teamHeader" cellspacing = "0">
  <tr>
    <th><p style="color:black; width:100px;">Team: <span class = "name">${team.team}</span></p></th>
    <th><p style="color:black; width:140px;">Matches Played: <span class = "name">${team.matches}</span></p></th>
    <th><p style="color:black; width:130px;">Avg. Score: <span class = "name">${team.avgScore}</span></p></th>
    <th><p style="color:black; width:150px;">Avg. Tag Score: <span class = "name">${team.avgTagScore!}</span></p></th>
    <th><p style="color:black; width:120px;">W/L/T: <span class = "name">${team.wins}/${team.losses}/${team.ties}</span></p></th>
    <th><p style="color:black; width:150px;">Ranking Score: <span class = "name">${team.rankingScore?string(",##0.00")}</span></p></th>
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
   <td><a href="javascript:void(0);"  data-title="${matchStatistic.tag}" data-template='<div class="popover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>' data-url="/ScoutingApp/m/getMatchTags?team=${team}&tag=${matchStatistic.tag}&event=${selectedEvent}" class="total-occurrences-popover-ajax">${matchStatistic.averageOccurrences?string(",##0.0")}</a> </td>
   </tr>
<#else> 
   <tr>       
   <td> ${matchStatistic.tag} </td>  
   <td><a href="javascript:void(0);"  data-title="${matchStatistic.tag}"  data-template='<div class="popover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>' data-url="/ScoutingApp/m/getMatchTags?team=${team}&tag=${matchStatistic.tag}&event=${selectedEvent}" class="total-occurrences-popover-ajax">${matchStatistic.averageOccurrences?string(",##0.0")}</a> </td>
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
 <#if hideComments>     
 <div class="teamComments" style="display:none;">
 <#else>
  <div class="teamComments">
 </#if>
 <div class="sectionHeader">Scouting Comments</div>
 <textarea disabled  rows="3" cols="100" >${team.scoutingComments!}</textarea>
 </div>   
 </div>
  </div>
  </div>
  </div>
  </#if>
 <#assign teamNum =  team.team>
 </#list>
 </div>
</body>
</html>