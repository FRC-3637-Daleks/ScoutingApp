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
    <div class="container text-left">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/bundle.zip" download>Export Bundle</a>
        <form action="${pageContext.request.contextPath}/io/bundle.zip" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="bundle">Import Bundle</label>
            <input type="file" name="file" id="bundle"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>
    </div>
    <div class="container text-left">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/tags.csv" download>Export
            Tags</a>
        <form action="${pageContext.request.contextPath}/io/tags.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="tags">Import Tags</label>
            <input type="file" name="file" id="tags"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>
        <form action="${pageContext.request.contextPath}/io/tags.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="tags">Delete and Import Tags</label>
            <input type="file" name="file" id="tags"/>
            <input type="hidden" name="delete" value="true"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>        
    </div>
    <div class="container text-left">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/schedule.csv" download>Export
            Schedule</a>
        <form action="${pageContext.request.contextPath}/io/schedule.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="schedule">Import Schedule</label>
            <input type="file" name="file" id="schedule"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>
    </div>
    <div class="container text-left">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/matches.csv" download>Export
            Matches</a>
        <form action="${pageContext.request.contextPath}/io/matches.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="matches">Import Matches</label>
            <input type="file" name="file" id="matches"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>
    </div>
    <div class="container text-left">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/teamTags.csv" download>Export
            Team Tags</a>
        <form action="${pageContext.request.contextPath}/io/teamTags.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="teams">Import Team Tags</label>
            <input type="file" name="file" id="teamTags"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>
    </div>
    <div class="container text-left">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/matchTags.csv" download>Export
            Match Tags</a>
        <form action="${pageContext.request.contextPath}/io/matchTags.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="teams">Import Match Tags</label>
            <input type="file" name="file" id="matchTags"/>
            <input class="btn btn-default" type="submit" value="Upload">
        </form>
    </div>
</div>
</body>
</html>
