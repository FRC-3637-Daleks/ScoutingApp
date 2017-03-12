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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <link href="${pageContext.request.contextPath}/css/schedule.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/js/editor.js"></script>
    <script>
       function save()
       {
    	   $( "#editForm" ).submit();
       }
       
       function deleteLast()
       {
    	   $("#deleted"+(num-1)).val("true");
           $( "#editForm" ).submit();
       }
       
       function add()
       {
    	   $.ajax({
               url: "../s/edit/add",
               type: 'GET',
               cache: false,
               success: function (result) {
                   location.reload();
               },
               error: function () { 
                    alert( "Error adding new match." );
               }
           });
       }       
    </script>
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
        <div id="navbar" class="collapse navbar-collapse">             
             <ul class="nav navbar-nav">
                <li><a href="#" onclick="add();">Add</a></li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="#" onclick="deleteLast();">Delete</a></li>
            </ul>
             <ul class="nav navbar-nav">
                <li><a href="#" onclick="save();">Save</a></li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/s/">Back</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container main">
    <c:choose>
        <c:when test="${true}">
            <form:form method="post" id="editForm" action="${pageContext.request.contextPath}/s/edit" modelAttribute="scheduleWrapper">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>Match #</th>
                        <th>Blue Team 1</th>
                        <th>Blue Team 2</th>
                        <th>Blue Team 3</th>
                        <th>Red Team 1</th>
                        <th>Red Team 2</th>
                        <th>Red Team 3</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="match" items="${scheduleWrapper.schedule}" varStatus="status">
                        <tr>
                            <form:hidden path="schedule[${status.index}].id" value="${match.id}"/>
                            <form:hidden path="deleted[${status.index}]"  value="${false}"/>
                            <td>${match.matchNum}<form:hidden path="schedule[${status.index}].matchNum" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].b1" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].b2" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].b3" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].r1" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].r2" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].r3" cssClass="form-control"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <script>var num = ${scheduleWrapper.schedule.size()};</script>
            </form:form>
        </c:when>
        <c:otherwise>
            <h1 class="h1 error">That team is not in the schedule</h1>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>