<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <link href="${pageContext.request.contextPath}/css/schedule.css" rel="stylesheet"/>
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
            <li><a href="${pageContext.request.contextPath}/s/edit">Edit</a></li>
            </ul>
            <form class="navbar-form navbar-right" action="${pageContext.request.contextPath}/s/t/">
                <div class="form-group" role="search">
                    <input type="text" class="form-control" placeholder="Search" name="teamNum">
                </div>
                <button type="submit" class="btn btn-info">Search</button>
            </form>
        </div>
    </div>
</nav>
<div class="container main">
    <c:choose>
        <c:when test="${schedule.size() > 0}">
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
                <c:forEach var="match" items="${schedule}">
                    <tr>
                        <td>${match.matchNum}</td>
                        <td><a href="${pageContext.request.contextPath}/m/add?teamNum=${match.b1}&matchNum=${match.matchNum}">${match.b1}</a></td>
                        <td><a href="${pageContext.request.contextPath}/m/add?teamNum=${match.b2}&matchNum=${match.matchNum}">${match.b2}</a></td>
                        <td><a href="${pageContext.request.contextPath}/m/add?teamNum=${match.b3}&matchNum=${match.matchNum}">${match.b3}</a></td>
                        <td><a href="${pageContext.request.contextPath}/m/add?teamNum=${match.r1}&matchNum=${match.matchNum}">${match.r1}</a></td>
                        <td><a href="${pageContext.request.contextPath}/m/add?teamNum=${match.r2}&matchNum=${match.matchNum}">${match.r2}</a></td>
                        <td><a href="${pageContext.request.contextPath}/m/add?teamNum=${match.r3}&matchNum=${match.matchNum}">${match.r3}</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <h1 class="h1 error">That team is not in the schedule</h1>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>