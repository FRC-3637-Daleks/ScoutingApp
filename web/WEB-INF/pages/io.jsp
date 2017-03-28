<%--Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
    Copyright (C) 2016  Team 3637

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand page-scroll" href="${pageContext.request.contextPath}/">Team 3637 Scouting App</a>
        </div>
    </div>
</nav>
<div class="main container">
  <table  class="table table-striped table-bordered">
  <tr>
    <th>Type</th>
    <th>Export</th>
    <th>Import</th>
  </tr>
    <tr>
        <th>
            Match Scouting Data
        </th>
        <td>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/matchData.zip" download>Export
          Match Scouting  Data</a>
        </td>
        <td>          
        <form action="${pageContext.request.contextPath}/io/matchData.zip" method="post" class="form-inline"
              enctype="multipart/form-data">
            <input type="file" class="filestyle" data-buttonText="Find file"  name="file" id="matchData" >         
            <input class="btn btn-default" type="submit" value="Import Match Scouting Data">
        </form>
        </td>
    </tr>  
    <tr>
        <th>
            Team Scouting Data
        </th>
        <td>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/teamData.zip" download>Export
            Team Scouting Data</a>
        </td>
        <td>            
        <form action="${pageContext.request.contextPath}/io/teamData.zip" method="post" class="form-inline"
              enctype="multipart/form-data">
            <input type="file" class="filestyle" data-buttonText="Find file" name="file"  id="teams" >                       
            <input class="btn btn-default" type="submit" value="Import Team Scouting Data">
        </form>
     </td>
    </tr>  
    <tr>    
        <th>
            Schedule
        </th>
        <td>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/schedule.csv" download>Export
            Schedule</a>
        </td>
        <td>            
        <form action="${pageContext.request.contextPath}/io/schedule.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <input type="file" class="filestyle" data-buttonText="Find file" name="file" id="schedule" >          
            <input class="btn btn-default" type="submit" value="Import Schedule Data">
        </form>
     </td>
    </tr>  
    <tr>
        <th>
            Tags
        </th>
        <td>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/tags.csv" download>Export
            Tags</a>
        </td>
        <td>
        <form action="${pageContext.request.contextPath}/io/tags.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
             <input type="file" class="filestyle" data-buttonText="Find file"  name="file"  id="tags" >        
             <input class="btn btn-default" type="submit" value="Import Tags">
             <input type="checkbox" name="delete" value="true" id="delete"/>
             <label style="font-weight:normal" for="delete">Delete tags before import</label>
        </form>
        </td>
    </tr>  
    <tr>
        <th>
            Bundle
        </th>
        <td>
           <a class="btn btn-default" href="${pageContext.request.contextPath}/io/bundle.zip" download>Export Bundle</a>
        </td>
        <td>
        <form action="${pageContext.request.contextPath}/io/bundle.zip" method="post" class="form-inline"
              enctype="multipart/form-data">
            <input type="file" class="filestyle" data-buttonText="Find file" name="file"   id="bundle" >        
            <input class="btn btn-default" type="submit" value="Import Bundle">
        </form>
        </td>
    </tr>
   </table>
</div>
</body>
</html>
