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
                <li><a href="${pageContext.request.contextPath}/s/">Schedule</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container main">
    <form:form method="post" action="/m/list" modelAttribute="wrapper">
        <table id="match-list" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>Team # <span class="glyphicon glyphicon-sort"></span></th>
                <th>Match # <span class="glyphicon glyphicon-sort"></span></th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="match" items="${wrapper.matches}" varStatus="status">
                <tr>
                    <form:hidden path="matches[${status.index}].id" value="${match.id}"/>
                    <td class="block">${match.team}</td>
                    <td class="block">${match.matchNum}</td>
                    <td class="block">
                        <a class="btn btn-info"
                           href="${pageContext.request.contextPath}/m/add?teamNum=${match.team}&&matchNum=${match.matchNum}">Edit</a>
                        <input type="button" class="btn btn-danger delete" value="Delete"/>
                        <form:hidden path="deleted[${status.index}]" value="${false}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3"><input class="btn btn-success" type="submit" value="Save"/></td>
            </tr>
            </tfoot>
        </table>
    </form:form>
</div>
<script>
    $(document).ready(function () {
                $("#match-list").tablesorter({
                    headers: {
                        2: {
                            sorter: false
                        }
                    },
                    sortList: [
                        [0,0]
                    ]
                });
            }
    );
</script>
</body>
</html>
