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
 
function deleteTag(tagId) {
      if (confirm("Are you sure you want to delete this tag ("+      $( "#tag-" + tagId )[0].innerHTML + ")?" ))
      {
           if (tagId < 0)
               $('#row-'+tagId).remove();
           else    
               $.ajax({
                  url: "../m/deleteTag?id="+tagId,  
                  type: 'GET',
                  cache: false,
                  success: function (result) {
                       $('#row-'+tagId).remove();
                  },
                 error: function () { 
                       alert( "Error occurred deleting tag." );
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
var newTagId = 0;
var matchTagGroupingOptions;
var teamTagGroupingOptions;
getMatchTagGroupingOptions();
getTeamTagGroupingOptions();

function getMatchTagGroupingOptions() {
   $.ajax({
            url: "../m/matchTagGroupings",
            type: 'GET',
            cache: false,
            success: function (result) {
                if (result) {
                    matchTagGroupingOptions = result;
                }
            },
            error: function () { 
                 alert( "Error getting match tag groupings." );
            }
        });
}
 
 function getTeamTagGroupingOptions() {
   $.ajax({
            url: "../m/teamTagGroupings",
            type: 'GET',
            cache: false,
            success: function (result) {
                if (result) {
                    teamTagGroupingOptions = result;
                }
            },
            error: function () { 
                 alert( "Error getting team tag groupings." );
            }
        });
}
 
function editTag(tagId, type) {
      $( "#editIcon-" + tagId )[0].style.display =  'none';  
      $( "#tag-" + tagId )[0].style.display =  'none';  
      $( "#category-" + tagId )[0].style.display =  'none'; 
      $( "#grouping-" + tagId )[0].style.display =  'none';  
      $( "#inputType-" + tagId )[0].style.display =  'none'; 
      $( "#pointValue-" + tagId )[0].style.display =  'none';      
      $( "#saveIcon-" + tagId )[0].style.display =  'block'; 
      $( "#tag-edit-" + tagId )[0].style.display =  'block'; 
      $( "#category-edit-" + tagId )[0].style.display =  'block'; 
      $( "#grouping-edit-" + tagId )[0].style.display =  'block';   
      $( "#inputType-edit-" + tagId )[0].style.display =  'block';      
      $( "#pointValue-edit-" + tagId )[0].style.display =  'block';        
      var inputTypeSelect = $( "#inputType-edit-input-" + tagId )[0];
      $(inputTypeSelect).empty();
      $(inputTypeOptions).each(function(i, v){
          $(inputTypeSelect).append($("<option>", { value: v, html: v }));
      }); 
      $(inputTypeSelect).val($( "#inputType-" + tagId )[0].innerHTML);
      var groupingSelect = $( "#grouping-edit-input-" + tagId )[0];
      $(groupingSelect).empty();
      if (type == 'matches')
      {
       	 $( "#isRankingPoint-" + tagId )[0].style.display =  'none'; 
       	 $( "#isRankingPoint-edit-" + tagId )[0].style.display =  'block';  
         $(matchTagGroupingOptions).each(function(i, v){
             $(groupingSelect).append($("<option>", { value: v, html: v }));
         });
     }
     else
        $(teamTagGroupingOptions).each(function(i, v){
          $(groupingSelect).append($("<option>", { value: v, html: v }));
        });               
     $(groupingSelect).val($( "#grouping-" + tagId )[0].innerHTML);      

} 

function saveTag(tagId, type) {
     if (confirm("Are you sure you want to save this tag ("+      $( "#tag-edit-input-" + tagId )[0].value + ")?" ))
     { 
         var tag = $( "#tag-edit-input-" + tagId )[0].value; 
         var grouping = $( "#grouping-edit-input-" + tagId )[0].value;
         var category = $( "#category-edit-input-" + tagId )[0].value;
         var inputType = $( "#inputType-edit-input-" + tagId )[0].value;
         var pointValue = $( "#pointValue-edit-input-" + tagId )[0].value;
         var isRankingPoint = 0;
         var isRankingPointValue = "No";
         if (type == 'matches')
      	 {
         	if ($( "#isRankingPoint-edit-input-" + tagId )[0].checked)	{
         		isRankingPoint = 1;
         		isRankingPointValue = "Yes";	}
         }
         $.ajax({
              url: "../m/saveTag?id="+tagId+"&tag="+tag+"&grouping="+grouping+"&category="+category+"&inputType="+inputType+"&type="+type+"&pointValue="+pointValue+"&isRankingPoint="+isRankingPoint,
              type: 'GET',
              cache: false,
              success: function (result) {
                   var newTagId = result;
                   if (tagId < 0)
                   {
                       $( "#row-" + tagId ).attr("id", "row-"+newTagId);    
                       $( "#editIcon-" + tagId ).attr("onclick", "editTag('"+newTagId+"','"+type+"');");       
                       $( "#editIcon-" + tagId ).attr("id", "editIcon-"+newTagId);    
                       $( "#saveIcon-" + tagId ).attr("onclick", "saveTag('"+newTagId+"','"+type+"');");                           
                       $( "#saveIcon-" + tagId ).attr("id", "saveIcon-"+newTagId);             
                       $( "#deleteIcon-" + tagId ).attr("onclick", "deleteTag('"+newTagId+"');");                            
                       $( "#deleteIcon-" + tagId ).attr("id", "deleteIcon-"+newTagId);          
                       $( "#tag-" + tagId ).attr("id", "tag-"+newTagId);  
                       $( "#grouping-" + tagId ).attr("id", "grouping-"+newTagId);  
                       $( "#category-" + tagId ).attr("id", "category-"+newTagId);   
                       $( "#inputType-" + tagId ).attr("id", "inputType-"+newTagId);
                       $( "#pointValue-" + tagId ).attr("id", "pointValue-"+newTagId);
                       $( "#isRankingPoint-" + tagId ).attr("id", "isRankingPoint-"+newTagId)
                       $( "#tag-edit-" + tagId ).attr("id", "tag-edit-"+newTagId);
                       $( "#grouping-edit-" + tagId ).attr("id", "grouping-edit-"+newTagId); 
                       $( "#category-edit-" + tagId ).attr("id", "category-edit-"+newTagId);
                       $( "#inputType-edit-" + tagId ).attr("id", "inputType-edit-"+newTagId);  
                       $( "#pointValue-edit-" + tagId ).attr("id", "pointValue-edit-"+newTagId);
                       $( "#isRankingPoint-edit-" + tagId ).attr("id", "isRankingPoint-edit-"+newTagId);                       
                       $( "#tag-edit-input-" + tagId ).attr("id", "tag-edit-input-"+newTagId);
                       $( "#grouping-edit-input-" + tagId ).attr("id", "grouping-edit-input-"+newTagId);
                       $( "#category-edit-input-" + tagId ).attr("id", "category-edit-input-"+newTagId);
                       $( "#inputType-edit-input-" + tagId ).attr("id", "inputType-edit-input-"+newTagId);
                       $( "#pointValue-edit-input-" + tagId ).attr("id", "pointValue-edit-input-"+newTagId);
                       $( "#isRankingPoint-edit-input-" + tagId ).attr("id", "isRankingPoint-edit-input-"+newTagId);                   
                       tagId = newTagId;
                   }             
                   $( "#tag-" + tagId )[0].innerHTML = tag;
                   $( "#grouping-" + tagId )[0].innerHTML = grouping;
                   $( "#category-" + tagId )[0].innerHTML = category;
                   $( "#inputType-" + tagId )[0].innerHTML = inputType;                 
                   $( "#pointValue-" + tagId )[0].innerHTML = pointValue; 
                   if (type == 'matches')     
                   	 $( "#isRankingPoint-" + tagId )[0].innerHTML = isRankingPointValue;                                          
                   cancelEditTag(tagId, type);  
              },
              error: function () { 
                   alert( "Error occurred saving tag." );
              }
          }); 
     }
     else
        cancelEditTag(tagId, type);
} 

function cancelEditTag(tagId, type) {
      $( "#editIcon-" + tagId )[0].style.display =  'block'; 
      $( "#tag-" + tagId )[0].style.display =  'block'; 
      $( "#category-" + tagId )[0].style.display =  'block'; 
      $( "#grouping-" + tagId )[0].style.display =  'block'; 
      $( "#inputType-" + tagId )[0].style.display =  'block'; 
      $( "#pointValue-" + tagId )[0].style.display =  'block';        
      $( "#saveIcon-" + tagId )[0].style.display =  'none'; 
      $( "#tag-edit-" + tagId )[0].style.display =  'none'; 
      $( "#category-edit-" + tagId )[0].style.display =  'none'; 
      $( "#grouping-edit-" + tagId )[0].style.display =  'none'; 
      $( "#inputType-edit-" + tagId )[0].style.display =  'none';        
      $( "#pointValue-edit-" + tagId )[0].style.display =  'none';
      if(type == 'matches') 
      {   
      	$( "#isRankingPoint-" + tagId )[0].style.display =  'block';   
      	$( "#isRankingPoint-edit-" + tagId )[0].style.display =  'none';  
      }     
} 

function createNewMatchTag() {
   var tagId = newTagId -1;
   var newRow = '<tr id="row-'+tagId+'">' +
  '<td><img id="editIcon-'+tagId+'" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editTag(\''+tagId+'\',\'matches\');"><img id="saveIcon-'+tagId+'" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveTag(\''+tagId+'\',\'matches\');"></td>' +
  '<td><div id="tag-'+tagId+'"></div><div id="tag-edit-'+tagId+'" style="display:none;"><input  id="tag-edit-input-'+tagId+'"  type="text"></input></div></td>' +
  '<td><div id="grouping-'+tagId+'"></div><div id="grouping-edit-'+tagId+'" style="display:none;"><select  id="grouping-edit-input-'+tagId+'"></select></div></td>' +
  '<td><div id="category-'+tagId+'"></div><div id="category-edit-'+tagId+'" style="display:none;"><input  id="category-edit-input-'+tagId+'"  type="text"></input></div></td>' +
  '<td><div id="inputType-'+tagId+'"></div><div id="inputType-edit-'+tagId+'" style="display:none;"><select  id="inputType-edit-input-'+tagId+'"></select></div></td>' +
  '<td><div id="pointValue-'+tagId+'"></div><div id="pointValue-edit-'+tagId+'" style="display:none;"><input  id="pointValue-edit-input-'+tagId+'"  type="text"></input></div></td>' +    
  '<td><div id="isRankingPoint-'+tagId+'"></div><div id="isRankingPoint-edit-'+tagId+'" style="display:none;"><input  id="isRankingPoint-edit-input-'+tagId+'"  type="checkbox"></input></div></td>' +    
  '<td><img id="deleteIcon-'+tagId+'" src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(\''+tagId+'\');"></td>' +
  '</tr> '
   $('#matchTags tr:first').after(newRow);
   editTag(tagId,'matches');
}
   
function createNewTeamTag() {   
   var tagId = newTagId -1;
   var newRow = '<tr id="row-'+tagId+'">' +
  '<td><img id="editIcon-'+tagId+'" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editTag(\''+tagId+'\',\'teams\');"><img id="saveIcon-'+tagId+'" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveTag(\''+tagId+'\',\'teams\');"></td>' +
  '<td><div id="tag-'+tagId+'"></div><div id="tag-edit-'+tagId+'" style="display:none;"><input  id="tag-edit-input-'+tagId+'"  type="text"></input></div></td>' +
  '<td><div id="grouping-'+tagId+'"></div><div id="grouping-edit-'+tagId+'" style="display:none;"><select  id="grouping-edit-input-'+tagId+'"></select></div></td>' +
  '<td><div id="category-'+tagId+'"></div><div id="category-edit-'+tagId+'" style="display:none;"><input  id="category-edit-input-'+tagId+'"  type="text"></input></div></td>' +
  '<td><div id="inputType-'+tagId+'"></div><div id="inputType-edit-'+tagId+'" style="display:none;"><select  id="inputType-edit-input-'+tagId+'"></select></div></td>' +
  '<td><div id="pointValue-'+tagId+'"></div><div id="pointValue-edit-'+tagId+'" style="display:none;"><input  id="pointValue-edit-input-'+tagId+'"  type="text"></input></div></td>' +  
  '<td><img id="deleteIcon-'+tagId+'"  src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(\''+tagId+'\');"></td>' +
  '</tr> '
   $('#teamTags tr:first').after(newRow);
   editTag(tagId,'teams');
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
                <li><a href="#" onclick="createNewMatchTag();">New Match Tag</a></li>
           	</ul> 
           	<ul class="nav navbar-nav"> 
                <li><a href="#" onclick="createNewTeamTag();">New Team Tag</a></li>
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
<div class = "typeTitle"  onclick="toggle('matchTags');">Match Tags</div>
<table class = "tagTable" id="matchTags"> 
  <tr>
    <th  class="icon"></th>      
    <th>Tag</th>
    <th>Grouping</th>
    <th>Category</th> 
    <th>Input Type</th>  
    <th>Point Value</th>   
    <th>Is Ranking Point</th>   
    <th  class="icon"></th>       
  </tr> 
  <#list matchTags as matchTag>
  <#assign isRankingPoint = "No">
  <#if matchTag.isRankingPoint == 1>
  	<#assign isRankingPoint = "Yes">
  </#if>  
  <tr id="row-${matchTag.id}">
  <td><img id="editIcon-${matchTag.id}" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editTag(${matchTag.id},'matches');"><img id="saveIcon-${matchTag.id}" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveTag(${matchTag.id},'matches');"></td>
  <td><div id="tag-${matchTag.id}">${matchTag.tag}</div><div id="tag-edit-${matchTag.id}" style="display:none;"><input  id="tag-edit-input-${matchTag.id}"  type="text" value="${matchTag.tag}"></input></div></td>
  <td><div id="grouping-${matchTag.id}">${matchTag.grouping}</div><div id="grouping-edit-${matchTag.id}" style="display:none;"><select  id="grouping-edit-input-${matchTag.id}"></select></div></td>
  <td><div id="category-${matchTag.id}">${matchTag.category}</div><div id="category-edit-${matchTag.id}" style="display:none;"><input  id="category-edit-input-${matchTag.id}"  type="text" value="${matchTag.category}"></input></div></td>
  <td><div id="inputType-${matchTag.id}">${matchTag.inputType}</div><div id="inputType-edit-${matchTag.id}" style="display:none;"><select  id="inputType-edit-input-${matchTag.id}"></select></div></td> 
  <td><div id="pointValue-${matchTag.id}">${matchTag.pointValue?string["0.#"]}</div><div id="pointValue-edit-${matchTag.id}" style="display:none;"><input  id="pointValue-edit-input-${matchTag.id}"  type="text" value="${matchTag.pointValue?string["0.#"]}"></input></div></td>  
  <td><div id="isRankingPoint-${matchTag.id}">${isRankingPoint}</div><div id="isRankingPoint-edit-${matchTag.id}" style="display:none;">
  <#if matchTag.isRankingPoint == 1>
  	<input  id="isRankingPoint-edit-input-${matchTag.id}"  type="checkbox" checked value="${matchTag.isRankingPoint!0}"></input>
  <#else>
  	<input  id="isRankingPoint-edit-input-${matchTag.id}"  type="checkbox" value="${matchTag.isRankingPoint!0}"></input>
  </#if>  
  </div></td>
  <td><img id="deleteIcon-${matchTag.id}" src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(${matchTag.id});"></td>
  </tr> 
  </#list>
</table>
</div>
</div>
<br>
<br> 
<div class="sectionWrapper">
<div class="sectionParent">
<div class = "typeTitle" onclick="toggle('teamTags');">Team Tags</div>
<table class = "tagTable" id="teamTags">
  <tr>
    <th class="icon"></th>
    <th>Tag</th>
    <th>Grouping</th>
    <th>Category</th>
    <th>Input Type</th> 
    <th>Point Value</th>          
    <th  class="icon"></th>         
  </tr> 
  <#list teamTags as teamTag>
  <tr id="row-${teamTag.id}">
  <td><img id="editIcon-${teamTag.id}" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editTag(${teamTag.id}, 'teams');"><img id="saveIcon-${teamTag.id}" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveTag(${teamTag.id},'teams');"></td>
  <td><div id="tag-${teamTag.id}">${teamTag.tag}</div><div id="tag-edit-${teamTag.id}" style="display:none;"><input  id="tag-edit-input-${teamTag.id}"  type="text" value="${teamTag.tag}"></input></div></td>
  <td><div id="grouping-${teamTag.id}">${teamTag.grouping}</div><div id="grouping-edit-${teamTag.id}" style="display:none;"><input  id="grouping-edit-input-${teamTag.id}"  type="text" value="${teamTag.grouping}"></input></div></td>
  <td><div id="category-${teamTag.id}">${teamTag.category}</div><div id="category-edit-${teamTag.id}" style="display:none;"><input  id="category-edit-input-${teamTag.id}"  type="text" value="${teamTag.category}"></input></div></td>
  <td><div id="inputType-${teamTag.id}">${teamTag.inputType}</div><div id="inputType-edit-${teamTag.id}" style="display:none;"><select  id="inputType-edit-input-${teamTag.id}"></select></div></td>
  <td><div id="pointValue-${teamTag.id}">${teamTag.pointValue?string["0.#"]}</div><div id="pointValue-edit-${teamTag.id}" style="display:none;"><input  id="pointValue-edit-input-${teamTag.id}"  type="text" value="${teamTag.pointValue?string["0.#"]}"></input></div></td>
  <td><img id="deleteIcon-${teamTag.id}" src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(${teamTag.id});"></td>
  </tr>
  </#list> 
</table>
</div>
</div>
</form>
</body>
</html>