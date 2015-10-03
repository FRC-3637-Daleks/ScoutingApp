<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <link href="${pageContext.request.contextPath}/css/input-page.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/match.css" rel="stylesheet"/>
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
                <li><a href="${pageContext.request.contextPath}/s/">Schedule</a></li>
                <li><a href="${pageContext.request.contextPath}/m/list">Matches</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="main container">
    <form:form method="POST" modelAttribute="match" action="${pageContext.request.contextPath}/m/add">
        <form:hidden path="matchNum" value="${matchNum}"/>
        <form:hidden path="team" value="${teamNum}"/>
        <form:hidden path="id" value="${match.id}"/>
        <input type="hidden" value="${team.id}" name="teamId"/>
        <div>
            <h2>Team: ${teamNum} - Match: ${matchNum}</h2>
        </div>
        <table id="input" class="input table">
            <tr>
                <td>
                    <label for="matchTags">Match Tags</label>
                    <input type="text" class="form-control" id="matchTags" name="matchTags"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="teamTags">Team Tags</label>
                    <input type="text" class="form-control" id="teamTags" name="teamTags"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="score">Score</label>
                    <form:input path="score" cssClass="form-control" value="${match.score}"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save" class="btn btn-success">
                </td>
            </tr>
        </table>
    </form:form>
</div>
<script>
    var matchTags = [
        <c:forEach var="tag" items="${matchTags}">
        "${tag}",
        </c:forEach>
    ];
    var teamTags = [
        <c:forEach var="tag" items="${teamTags}">
        "${tag}",
        </c:forEach>
    ];
    var usedTeamTags = [
        <c:forEach var="tag" items="${team.tags}">
            "${tag}",
        </c:forEach>
    ];
    var usedMatchTags = [
        <c:forEach var="tag" items="${match.tags}">
        "${tag}",
        </c:forEach>
    ];
</script>
<script>
    $('#matchTags').tokenfield({
        autocomplete: {
            source: matchTags,
            delay: 100
        },
        showAutocompleteOnFocus: true
    }).tokenfield('setTokens', usedMatchTags);
    $('#teamTags').tokenfield({
        autocomplete: {
            source: teamTags,
            delay: 100
        },
        showAutocompleteOnFocus: true
    }).tokenfield('setTokens', usedTeamTags);
</script>
</body>
</html>
