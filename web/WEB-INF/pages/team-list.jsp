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
    </div>
</nav>
<div class="container main">
    <form:form method="post" action="/t/" modelAttribute="teamWrapper">
        <table id="team-list" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>Team # <span class="glyphicon glyphicon-sort"></span></th>
                <th>Matches Played <span class="glyphicon glyphicon-sort"></span></th>
                <th>Average Score <span class="glyphicon glyphicon-sort"></span></th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="team" items="${teamWrapper.teams}" varStatus="status">
                <tr>
                    <form:hidden path="teams[${status.index}].id" value="${team.id}"/>
                    <td class="block">${team.team}</td>
                    <td class="block">${team.matches}</td>
                    <td class="block">${team.avgscore}</td>
                    <td class="block">
                        <a class="btn btn-info"
                           href="${pageContext.request.contextPath}/t/${team.team}">View</a>
                        <input type="button" class="btn btn-danger delete" value="Delete"/>
                        <form:hidden path="deleted[${status.index}]" value="${false}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="5"><input class="btn btn-success" type="submit" value="Save"/></td>
            </tr>
            </tfoot>
        </table>
    </form:form>
</div>
<script>
    $(document).ready(function () {
                $("#team-list").tablesorter({
                    headers: {
                        3: {
                            sorter: false
                        }
                    },
                    sortList: [
                        [0,0,0]
                    ]
                });
            }
    );
</script>
</body>
</html>
