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
    <form:form method="POST" modelAttribute="match" action="${pageContext.request.contextPath}/m/add.jsp">
        <form:hidden path="matchNum" value="${matchNum}"/>
        <form:hidden path="team" value="${teamNum}"/>
        <form:hidden path="id" value="${match.id}"/>
        <div>
            <h2>Team: ${teamNum} - Match: ${matchNum}</h2>
        </div>
        <table id="input" class="input table">
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save" class="btn btn-success">
                </td>
            </tr>
        </table>
    </form:form>
</div>
<script>
    var teamTags = [
        <c:forEach var="tag" items="${team.tags}">
            "${tag}",
        </c:forEach>
    ];
    var matchTags = [
        <c:forEach var="tag" items="${match.tags}">
        "${tag}",
        </c:forEach>
    ];
</script>
</body>
</html>
