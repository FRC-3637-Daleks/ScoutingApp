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
        <a class="btn btn-default" href="${pageContext.request.contextPath}/io/teams.csv" download>Export
            Teams</a>
        <form action="${pageContext.request.contextPath}/io/teams.csv" method="post" class="form-inline"
              enctype="multipart/form-data">
            <label for="teams">Import Teams</label>
            <input type="file" name="file" id="teams"/>
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
    </div>
</div>
</body>
</html>
