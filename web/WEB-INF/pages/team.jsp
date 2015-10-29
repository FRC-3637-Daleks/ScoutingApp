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
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/t/">Back</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container main">
    <div>
        <p class="h2">Team: ${team.team}</p>
    </div>
    <div id="input" class="input container">
        <div class="row data-row">
            <div class="col-md-6">
                <label for="matchTags">Match Tags</label>
                <input type="text" class="form-control" id="matchTags" name="matchTags" readonly/>
            </div>
            <div class="col-md-6">
                <label for="teamTags">Team Tags</label>
                <input type="text" class="form-control" id="teamTags" name="teamTags" readonly/>
            </div>
        </div>
    </div>
    <div class="row data-row">
        <div class="col-md-4 col-md-offset-2 text-center">
            <label for="score">Average Score</label>
            <input id="score" name="score" class="form-control" type="text" value="${team.avgscore}" readonly/>
        </div>
        <div class="col-md-4 text-center">
            <label for="matches">Matches Played</label>
            <input id="matches" name="matches" class="form-control" type="text" value="${team.matches}" readonly/>
        </div>
    </div>
</div>
<script>
    var matchTags = [
        <c:forEach var="tag" items="${matchTags}">
        "${tag}",
        </c:forEach>
    ];
    var teamTags = [
        <c:forEach var="tag" items="${team.tags}">
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
    }).tokenfield('setTokens', matchTags);
    $('#matchTags').tokenfield('readonly');
    $('#teamTags').tokenfield({
        autocomplete: {
            delay: 100
        },
        showAutocompleteOnFocus: true
    }).tokenfield('setTokens', teamTags);
    $('#teamTags').tokenfield('readonly');
</script>
</body>
</html>
