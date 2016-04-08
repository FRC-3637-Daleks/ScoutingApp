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
        <a class="btn btn-default" href="${pageContext.request.contextPath}/analytics/cache-scouting-report"
           target="_blank">View Scouting Report</a>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/analytics/scouting-report.html" download>Generate
            Scouting Report</a>
    </div>
    <div class="container text-left">

    </div>
</div>
</body>
</html>
