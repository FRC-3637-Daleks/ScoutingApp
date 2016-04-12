<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
  ~  Copyright (C) 2016  Team 3637
  ~
  ~  This program is free software: you can redistribute it and/or modify
  ~  it under the terms of the GNU General Public License as published by
  ~  the Free Software Foundation, either version 3 of the License, or
  ~  (at your option) any later version.
  ~
  ~  This program is distributed in the hope that it will be useful,
  ~  but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  GNU General Public License for more details.
  ~
  ~  You should have received a copy of the GNU General Public License
  ~  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <style>
        a:visited, a:hover, a:active {
            color: black;
            text-decoration: none;
        }
    </style>
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
        <h2>Scouting Report</h2>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/analytics/cache-scouting-report"
           target="_blank">View Scouting Report</a>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/analytics/scouting-report.html" download>Download
            Scouting Report</a>
    </div>
    <div class="container text-left">
        <h2>Pre-Match Reports</h2>
        <p>Click the table row fo the desired match</p>
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
                <th>Download</th>
            </tr>
            </thead>
            <tbody id="matches">
            <c:forEach var="match" items="${schedule}">
                <tr data-matchnum="${match.matchNum}">
                    <td>${match.matchNum}</td>
                    <td><a>${match.b1}</a></td>
                    <td><a>${match.b2}</a></td>
                    <td><a>${match.b3}</a></td>
                    <td><a>${match.r1}</a></td>
                    <td><a>${match.r2}</a></td>
                    <td><a>${match.r3}</a></td>
                    <td class="download">
                        <a href="${pageContext.request.contextPath}/analytics/prematch-report-${matchNum}.html"
                            class="download" download><span class="glyphicon glyphicon-save download"
                                                            aria-hidden="true"></span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    $('#matches').find('tr').on('click', function (event) {
        if (!event.toElement.classList.contains("download")) {
            window.open('${pageContext.request.contextPath}/analytics/cache-prematch-report-' + $(this).attr('data-matchnum'));
        }
    });
</script>
</body>
</html>
