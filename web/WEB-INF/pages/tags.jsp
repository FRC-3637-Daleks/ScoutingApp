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
            <ul class="nav navbar-nav  navbar-right">
                <li><a href="${pageContext.request.contextPath}/m//tags/mergeMatch">Merge Match Tags</a></li>
                <li><a href="${pageContext.request.contextPath}/m//tags/mergeTeam">Match Team Tags</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container main">
    <form:form method="post" action="${pageContext.request.contextPath}/m/tags" modelAttribute="tagWrapper">
        <div>
            <p class="h2">Tags</p>
            <p id="error" class="error"></p>
        </div>
        <div id="input" class="input container">
            <div class="row data-row">
                <div class="col-md-6">
                    <label for="matchTags">Match Tags</label>
                    <input type="text" class="form-control" id="matchTags"
                           name="matchTags" data-error="#error"/>
                </div>
                <div class="col-md-6">
                    <label for="teamTags">Team Tags <span id="teamTagsErr" class="error"></span></label>
                    <input type="text" class="form-control" id="teamTags"
                           name="teamTags" data-error="#error"/>
                </div>
            </div>
            <div class="row data-row">
                <div class="col-md-12">
                    <input type="submit" value="Save" class="btn btn-success">
                </div>
            </div>
        </div>
    </form:form>
</div>
<script>
    var usedMatchTags = [
        <c:forEach var="tag" items="${matchTags}">
        "${tag}",
        </c:forEach>
    ];
    var usedTeamTags = [
        <c:forEach var="tag" items="${teamTags}">
        "${tag}",
        </c:forEach>
    ];
</script>
<script>
    $('#matchTags').tokenfield({
        autocomplete: {
            delay: 100
        },
        showAutocompleteOnFocus: true
    }).tokenfield('setTokens', usedMatchTags);
    $('#teamTags').tokenfield({
        autocomplete: {
            delay: 100
        },
        showAutocompleteOnFocus: true
    }).tokenfield('setTokens', usedTeamTags);
</script>
</body>
</html>
