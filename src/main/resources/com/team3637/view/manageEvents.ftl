<#import "spring.ftl" as spring />
<html>
<head>
<style>
.body {
    background-color: #9fb7b7;
}

.sectionWrapper {
  display: inline-block;
}

.sectionParent {
  display: flex;
  flex-direction: column;
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

.tagHeader { 
    width:100%;
    border-spacing: 0px;
}
 
.tagTable th.icon {
    width:24px!important;
} 

.tagTable th{
    background-color:#00A22E;
    font-weight:bold; 
    text-align:left;
    padding-right:10px;  
    padding-left:10px;    
    border: 0px;    
    width:200px;    
}

.tagTable tr:nth-child(odd){  
        background: #eeeeee; 
    } 

.tagTable tr:nth-child(even){
        background: #ffffff;
    }
    
 .tagTable active{
        background: #25ff05;
 }
 
 .tagTable td{
    font-size: 12px;
    padding-right:10px;  
    padding-left:10px;        
    border-collapse: collapse;  
}
 
.tagInput { 
    width:50px;
    color:black;  
    font-weight:normal; 
    height: 25px;
    border: 1px solid gray !important;    
}    

.typeTitle { 
    text-align: 
    center; height: 20px; 
    background-color:#8F0000; 
    font-size:13px;  
    font-weight:bold; 
    padding-right:15px;
    padding-left:15px;    
    border: 1px solid gray;     
    color:white;    
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
 
function deleteEvent(eventId) {
      if (confirm("Are you sure you want to delete this event ("+      $( "#event-" + eventId )[0].innerHTML + ")?" ))
      {
           if (eventId < 0)
               $('#row-'+tagId).remove();
           else    
               $.ajax({
                  url: "../m/deleteEvent?id="+eventId,  
                  type: 'GET',
                  cache: false,
                  success: function (result) {
                       $('#row-'+tagId).remove();
                  },
                 error: function () { 
                       alert( "Error occurred deleting event." );
                 }
              });
       }
} 
 
function isInt(value) {
  return !isNaN(value) && 
         parseInt(Number(value)) == value && 
         !isNaN(parseInt(value, 10));
}   

var inputTypeOptions = ["checkbox", "incremental"]; 
var neweventId = 0;
 
function editEvent(eventId) {
      $( "#editIcon-" + eventId )[0].style.display =  'none';  
      $( "#event-" + eventId )[0].style.display =  'none';
      $( "#eventDate-" + eventId )[0].style.display =  'none';
      $( "#year-" + eventId )[0].style.display =  'none';
      $( "#active-" + eventId )[0].style.display =  'none';
      $( "#saveIcon-" + eventId )[0].style.display =  'block'; 
      $( "#event-edit-" + eventId )[0].style.display =  'block';
      $( "#eventDate-" + eventId )[0].style.display =  'block';
      $( "#year-" + eventId )[0].style.display =  'block';
      
} 

function saveEvent(eventId) {
     if (confirm("Are you sure you want to save this event ("+      $( "#event-edit-input-" + eventId )[0].value + ")?" ))
     { 
         var event = $( "#event-edit-input-" + eventId )[0].value; 
         var eventDate = $( "#eventDate-edit-input-" + eventId )[0].value;
         var year = $( "#year-edit-input-" + eventId )[0].value;
         var active = $( "#active-edit-input-" + eventId )[0].value;
         $.ajax({
              url: "../m/saveEvent?id="+eventId+"&event="+event+"&eventDate="+eventDate+"&year="+year+"&active="+active,
              type: 'GET',
              cache: false,
              success: function (result) {
                   var neweEventId = result;
                   if (eventId < 0)
                   {
                       $( "#row-" + eventId ).attr("id", "row-"+newEventId);    
                       $( "#editIcon-" + eventId ).attr("onclick", "editevent('"+neweventId+"','"+type+"');");       
                       $( "#editIcon-" + eventId ).attr("id", "editIcon-"+neweventId);    
                       $( "#saveIcon-" + eventId ).attr("onclick", "saveevent('"+neweventId+"','"+type+"');");                           
                       $( "#saveIcon-" + eventId ).attr("id", "saveIcon-"+neweventId);             
                       $( "#deleteIcon-" + eventId ).attr("onclick", "deleteevent('"+neweventId+"');");                            
                       $( "#deleteIcon-" + eventId ).attr("id", "deleteIcon-"+neweventId);          
                       $( "#event-" + eventId ).attr("id", "event-"+neweventId);  
                       $( "#eventDate-" + eventId ).attr("id", "eventDate-"+neweventId);  
                       $( "#year-" + eventId ).attr("id", "year-"+neweventId);   
                       $( "#active-" + eventId ).attr("id", "active-"+neweventId);
                       $( "#event-edit-" + eventId ).attr("id", "event-edit-"+neweventId);
                       $( "#eventDate-edit-" + eventId ).attr("id", "eventDate-edit-"+neweventId); 
                       $( "#year-edit-" + eventId ).attr("id", "year-edit-"+neweventId);
                       $( "#active-edit-" + eventId ).attr("id", "active-edit-"+neweventId);           
                       eventId = neweventId;
                   }             
                   $( "#event-" + eventId )[0].innerHTML = event;
                   $( "#eventDate-" + eventId )[0].innerHTML = eventDate;
                   $( "#year-" + eventId )[0].innerHTML = year;
                   $( "#active-" + eventId )[0].innerHTML = active;                 
              error: function () { 
                   alert( "Error occurred saving event." );
              }
          }); 
     }
     else
        cancelEditEvent(eventId);
} 

function cancelEditevent(eventId) {
      $( "#editIcon-" + eventId )[0].style.display =  'block'; 
      $( "#event-" + eventId )[0].style.display =  'block'; 
      $( "#eventDate-" + eventId )[0].style.display =  'block'; 
      $( "#year-" + eventId )[0].style.display =  'block'; 
      $( "#active-" + eventId )[0].style.display =  'block'; 
      $( "#saveIcon-" + eventId )[0].style.display =  'none'; 
      $( "#event-edit-" + eventId )[0].style.display =  'none'; 
      $( "#eventDate-edit-" + eventId )[0].style.display =  'none'; 
      $( "#year-edit-" + eventId )[0].style.display =  'none'; 
      $( "#active-edit-" + eventId )[0].style.display =  'none';        
} 

function createNewEvent() {
   var eventId = newEventId -1;
   var newRow = '<tr id="row-'+EventId+'">' +
  '<td><img id="editIcon-'+eventId+'" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editevent(\''+eventId\');"><img id="saveIcon-'+eventId+'" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveevent(\''+eventId\');"></td>' +
  '<td><div id="event-'+eventId+'"></div><div id="event-edit-'+eventId+'" style="display:none;"><input  id="event-edit-input-'+eventId+'"  type="text"></input></div></td>' +
  '<td><div id="eventDate-'+eventId+'"></div><div id="eventDate-edit-'+eventId+'" style="display:none;"><select  id="eventDate-edit-input-'+eventId+'"></select></div></td>' +
  '<td><div id="year-'+eventId+'"></div><div id="year-edit-'+eventId+'" style="display:none;"><input  id="year-edit-input-'+eventId+'"  type="text"></input></div></td>' +
  '<td><div id="active-'+eventId+'"></div><div id="active-edit-'+eventId+'" style="display:none;"><select  id="active-edit-input-'+eventId+'"></select></div></td>' +
  '</tr> '
   $('#events tr:first').after(newRow);
   editEvent(eventId);
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
                <li><a href="#" onclick="createNewMatchTag();">New Event</a></li>
           	</ul> 
            <ul class="nav navbar-nav"> 
                <li><a href="../">Back</a></li>
            </ul> 
        </div> 
    </div>
</nav> 
<form style="margin:0;">
  <div class="sectionWrapper">
  <div class="sectionParent">
<div class = "typeTitle"  onclick="toggle('events');">Events</div>
<table class = "tagTable" id="events"> 
  <tr>
    <th  class="icon"></th>      
    <th>Event</th>
    <th>Event Date</th>
    <th>Year</th> 
    <th>Active</th>
    <th class="icon"></th> 
  </tr> 
  <#list events as event>
   <#assign active = "No">
  <#if event.active == 1>
  	<#assign active = "Yes">
  </#if>
  <tr id="row-${event.id}" class = "active">
  <td><img id="editIcon-${event.id}" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editEvent(${event.id},'events');"><img id="saveIcon-${event.id}" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveTag(${event.id},'events');"></td>
  <td><div id="event-${event.id}">${event.eventId}</div><div id="event-edit-${event.id}" style="display:none;"><input  id="event-edit-input-${event.id}"  type="text" value="${event.id}"></input></div></td>
  <td><div id="eventDate-${event.id}">${event.eventDate}</div><div id="eventDate-edit-${event.id}" style="display:none;"><select  id="eventDate-edit-input-${event.id}"></select></div></td>
  <td><div id="year-${event.id}">${event.year}</div><div id="year-edit-${event.id}" style="display:none;"><input  id="year-edit-input-${event.id}"  type="text" value="${event.year}"></input></div></td>
  <td><div id="active-${event.id}">${active}</div><div id="active-edit-${event.id}" style="display:none;">
  <#if event.active == 1>
  	<input  id="active-edit-input-${event.id}"  type="checkbox" checked value="${event.active!0}"></input>
  <#else>
  	<input  id="active-edit-input-${event.id}"  type="checkbox" value="${event.active!0}"></input>
  </#if></div></td> 
 
  </div></td>
  <td><img id="deleteIcon-${event.id}" src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(${event.id});"></td>
  </tr> 
  </#list>
</table>
</div>
</div>
<br>
<br> 
</table>
</div>
</div>
</form>
</body>
</html>