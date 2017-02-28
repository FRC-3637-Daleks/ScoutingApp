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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="includes.jsp" %>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
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
                <li><a href="${pageContext.request.contextPath}/s/">Schedule</a></li>
                <li><a href="${pageContext.request.contextPath}/m/list">Matches</a></li>
                <li><a href="${pageContext.request.contextPath}/t/">Teams</a></li>
                <li><a href="${pageContext.request.contextPath}/m/manageTags">Tags</a></li>
                <li><a href="${pageContext.request.contextPath}/io/">IO</a></li>
                <li><a href="${pageContext.request.contextPath}/analytics/">Analytics</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container main">
    <h1>Team 3637 Scouting App</h1>

    <p class="lead">Click "Start Scouting" when you ready to begin scouting</p>
    <section>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/s/">Start
            Scouting</a>
    </section>
</div>
</body>
</html>