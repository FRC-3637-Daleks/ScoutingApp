<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <link href="${pageContext.request.contextPath}/css/schedule.css" rel="stylesheet"/>
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
                <li><a href="${pageContext.request.contextPath}/s/">Back</a></li>
            </ul>
            <form class="navbar-form navbar-right" action="${pageContext.request.contextPath}/s/edit/t/">
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
        <c:when test="${true}">
            <form:form method="post" action="/s/edit" modelAttribute="scheduleWrapper">
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
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="match" items="${scheduleWrapper.schedule}" varStatus="status">
                        <tr>
                            <form:hidden path="schedule[${status.index}].id" value="${match.id}"/>
                            <td><form:input path="schedule[${status.index}].matchNum" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].b1" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].b2" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].b3" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].r1" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].r2" cssClass="form-control"/></td>
                            <td><form:input path="schedule[${status.index}].r3" cssClass="form-control"/></td>
                            <td>
                                <input type="button" class="btn btn-danger delete" value="Delete"/>
                                <form:hidden path="deleted[${status.index}]" value="${false}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="7"><input type="submit" value="Save" class="btn btn-success"/></td>
                    </tr>
                    </tbody>
                </table>
                <script>var num = ${scheduleWrapper.schedule.size()};</script>
            </form:form>
        </c:when>
        <c:otherwise>
            <h1 class="h1 error">That team is not in the schedule</h1>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>