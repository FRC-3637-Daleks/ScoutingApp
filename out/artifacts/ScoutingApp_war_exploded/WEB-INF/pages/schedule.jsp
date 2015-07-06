<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.html" %>
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
            <a class="navbar-brand page-scroll" href="/">Team 3637 Scouting App</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
            </ul>
            <form class="navbar-form navbar-right" action="/s/t/">
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
                        <td>${match.b1}</td>
                        <td>${match.b2}</td>
                        <td>${match.b3}</td>
                        <td>${match.r1}</td>
                        <td>${match.r2}</td>
                        <td>${match.r3}</td>
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