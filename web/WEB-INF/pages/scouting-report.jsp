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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Scouting Data</title>

    <style>
        <%@include file="../../bootstrap/css/bootstrap.min.css"%>
    </style>

    <style>
        html {
            margin-top: 30px;
            margin-left: 50px;
        }

        p {
            font-weight: 700;
        }

        dd {
            text-indent: 1.5em;
            font-weight: 700;
        }

        ul {
            list-style: none;
        }

        a {
            font-weight: 700;
            color: #000;
            font-size: 1.2em;
        }

        #list a {
            font-size: 2.2em;
        }

        @media (max-width: 992px) {
            #list a {
                font-size: 5em;
                text-align: center;
            }

            a {
                font-size: 2.5em;
            }

            p, dt, dd {
                font-size: 1.2em;
            }

        }
        .table-nonfluid {
            width: auto !important;
        }
    </style>

</head>
<body>

<div id="tabs">

    <section id="list">
        <ul>
            <li><a href="javascript:window.location.href=window.location.pathname + '?team=coded'">All coded
                designations</a></li>
            <c:forEach var="report" items="${reports.reports}">
                <li><a href="javascript:window.location.href=window.location.pathname + '?team=${report.team.team}'">Team ${report.team.team}</a>
                </li>
            </c:forEach>
        </ul>
    </section>

    <section id="coded" class="hidden">
        <a href="javascript:window.location.href=window.location.pathname">Back</a><br/><br/>
        <p>
            <c:forEach var="report" items="${reports.reports}">
                ${report.codedDesignation}<br/>
            </c:forEach>
        </p>
    </section>

    <c:forEach var="report" items="${reports.reports}">
        <section id="${report.team.team}" class="hidden">
            <a href="javascript:window.location.href=window.location.pathname">Back</a><br/><br/>
            <p>${report.codedDesignation}</p>
            <p>${report.englishDesignation}</p>

            <table class="table table-striped table-bordered table-nonfluid">
                <tr>
                    <c:forEach var="tableHeader" items="${report.tableHeaders}">
                        <th>${tableHeader}</th>
                    </c:forEach>
                </tr>
                <c:forEach var="row" items="${report.tableData}">
                    <tr>
                        <c:forEach var="col" items="${row}">
                            <td>${col}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </c:forEach>

</div>

<script type="text/javascript">
    <%@include file="../../js/jquery.min.js"%>
</script>

<script type="text/javascript">
    function getParameterByName(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)", "i"),
                results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $(document).ready(function () {
        var team = getParameterByName('team');
        if (team != null) {
            $('#list').addClass('hidden');
            $('#' + team).removeClass('hidden');
        }
    });
</script>

</body>
</html>