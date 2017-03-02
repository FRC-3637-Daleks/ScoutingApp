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
           $.get("../m/deleteTag?id="+tagId);
} 

function isInt(value) {
  return !isNaN(value) && 
         parseInt(Number(value)) == value && 
         !isNaN(parseInt(value, 10));
}  

var inputTypeOptions = ["checkbox", "incremental"]; 
 
function editTag(tagId) {
      $( "#editIcon-" + tagId )[0].style.display =  'none'; 
      $( "#tag-" + tagId )[0].style.display =  'none';  
      $( "#category-" + tagId )[0].style.display =  'none'; 
      $( "#grouping-" + tagId )[0].style.display =  'none';  
      $( "#inputType-" + tagId )[0].style.display =  'none'; 
      $( "#saveIcon-" + tagId )[0].style.display =  'block'; 
      $( "#tag-edit-" + tagId )[0].style.display =  'block'; 
      $( "#category-edit-" + tagId )[0].style.display =  'block'; 
      $( "#grouping-edit-" + tagId )[0].style.display =  'block';   
       $( "#inputType-edit-" + tagId )[0].style.display =  'block';      
      var inputTypeSelect = $( "#inputType-edit-input-" + tagId )[0];
      $(inputTypeSelect).empty();
      $(inputTypeOptions).each(function(i, v){
          $(inputTypeSelect).append($("<option>", { value: v, html: v }));
      });
     $(inputTypeSelect).val($( "#inputType-" + tagId )[0].innerHTML);
} 

function saveTag(tagId) {
     if (confirm("Are you sure you want to save this tag ("+      $( "#tag-edit-input-" + tagId )[0].value + ")?" ))
     { 
         var tag = $( "#tag-edit-input-" + tagId )[0].value; 
         var grouping = $( "#grouping-edit-input-" + tagId )[0].value;
         var category = $( "#category-edit-input-" + tagId )[0].value;
         var inputType = $( "#inputType-edit-input-" + tagId )[0].value;
         $.get("../m/saveTag?id="+tagId+"&tag="+tag+"&grouping="+grouping+"&category="+category+"&inputType="+inputType);
         cancelEditTag(tagId); 
     }
     else
        cancelEditTag(tagId);
} 

function cancelEditTag(tagId) {
      $( "#editIcon-" + tagId )[0].style.display =  'block'; 
      $( "#tag-" + tagId )[0].style.display =  'block'; 
      $( "#category-" + tagId )[0].style.display =  'block'; 
      $( "#grouping-" + tagId )[0].style.display =  'block'; 
      $( "#inputType-" + tagId )[0].style.display =  'block'; 
      $( "#saveIcon-" + tagId )[0].style.display =  'none'; 
      $( "#tag-edit-" + tagId )[0].style.display =  'none'; 
      $( "#category-edit-" + tagId )[0].style.display =  'none'; 
      $( "#grouping-edit-" + tagId )[0].style.display =  'none'; 
      $( "#inputType-edit-" + tagId )[0].style.display =  'none';        
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
    <th  class="icon"></th>       
  </tr> 
  <#list matchTags as matchTag> 
  <tr>
  <td><img id="editIcon-${matchTag.id}" src="../images/pencil.png" style="width:24px;height:24px;" onClick="editTag(${matchTag.id});"><img id="saveIcon-${matchTag.id}" src="../images/save.png" style="width:24px;height:24px;display:none;" onClick="saveTag(${matchTag.id});"></td>
  <td><div id="tag-${matchTag.id}">${matchTag.tag}</div><div id="tag-edit-${matchTag.id}" style="display:none;"><input  id="tag-edit-input-${matchTag.id}"  type="text" value="${matchTag.tag}"></input></div></td>
  <td><div id="grouping-${matchTag.id}">${matchTag.grouping}</div><div id="grouping-edit-${matchTag.id}" style="display:none;"><input  id="grouping-edit-input-${matchTag.id}"  type="text" value="${matchTag.grouping}"></input></div></td>
  <td><div id="category-${matchTag.id}">${matchTag.category}</div><div id="category-edit-${matchTag.id}" style="display:none;"><input  id="category-edit-input-${matchTag.id}"  type="text" value="${matchTag.category}"></input></div></td>
  <td><div id="inputType-${matchTag.id}">${matchTag.inputType}</div><div id="inputType-edit-${matchTag.id}" style="display:none;"><select  id="inputType-edit-input-${matchTag.id}"></select></div></td> 
  <td><img src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(${matchTag.id});"></td>
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
    <th  class="icon"></th>         
  </tr> 
  <#list teamTags as teamTag>
  <tr>
  <td><img src="../images/pencil.png" style="width:24px;height:24px;"></td>
  <td>${teamTag.tag}</td>
  <td>${teamTag.grouping}</td>
  <td>${teamTag.category}</td> 
  <td>${teamTag.inputType}</td>
  <td><img src="../images/delete.png" style="width:24px;height:24px;" onClick="deleteTag(${teamTag.id});"></td>
  </tr>
  </#list> 
</table>
</div>
</div>
</form>
</body>
</html>