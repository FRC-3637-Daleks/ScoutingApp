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
    <link href="${pageContext.request.contextPath}/css/matchList.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/js/editor.js"></script>
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
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/t/search/">Search</a></li>
            </ul>
            <div class="navbar-form navbar-right navbar-input-group form-group">
                <input type="text" class="form-control" placeholder="Team #" name="teamNum">
                <button type="submit" class="btn btn-info" onclick="addTeam();">Add Team</button>
            </div>
        </div>
    </div>
</nav>
<div class="container main">
    <form:form method="post" action="${pageContext.request.contextPath}/t/view/" modelAttribute="teamWrapper">
        <table id="team-list" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>Team # <span class="glyphicon glyphicon-sort"></span></th>
                <th>Matches Played <span class="glyphicon glyphicon-sort"></span></th>
                <th>Average Score <span class="glyphicon glyphicon-sort"></span></th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="team" items="${teamWrapper.teams}" varStatus="status">
                <tr>
                    <form:hidden path="teams[${status.index}].id" value="${team.id}"/>
                    <td class="block">${team.team}</td>
                    <td class="block">${team.matches}</td>
                    <td class="block">${team.avgscore}</td>
                    <td class="block">
                        <a class="btn btn-info"
                           href="${pageContext.request.contextPath}/t/view/${team.team}">View</a>
                        <input type="button" class="btn btn-danger delete" value="Delete"/>
                        <form:hidden path="deleted[${status.index}]" value="${false}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="5"><input class="btn btn-success" type="submit" value="Save"/></td>
            </tr>
            </tfoot>
        </table>
    </form:form>
</div>
<script>
    $(document).ready(function () {
                $("#team-list").tablesorter({
                    headers: {
                        3: {
                            sorter: false
                        }
                    },
                    sortList: [
                        [0, 0, 0]
                    ]
                });
            }
    );
    function addTeam() {
        window.location = '${pageContext.request.contextPath}/t/view/' + $('input[name=teamNum]').val();
    }
</script>
</body>
</html>
