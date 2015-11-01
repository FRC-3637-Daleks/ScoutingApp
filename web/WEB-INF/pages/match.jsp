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
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/s/">Back</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="main container">
    <form:form method="POST" modelAttribute="match" action="${pageContext.request.contextPath}/m/add">
        <form:hidden path="matchNum" value="${matchNum}"/>
        <form:hidden path="team" value="${teamNum}"/>
        <div>
            <p class="h2">Team: ${teamNum} - Match: ${matchNum}</p>
        </div>
        <div id="input" class="input container">
            <div class="row data-row">
                <div class="col-md-6">
                    <label for="matchTags">Match Tags <span id="matchTagsErr" class="error"></span></label>
                    <input type="text" class="form-control" id="matchTags"
                           name="matchTags" data-error="#matchTagsErr" required/>
                </div>
                <div class="col-md-6">
                    <label for="teamTags">Team Tags <span id="teamTagsErr" class="error"></span></label>
                    <input type="text" class="form-control" id="teamTags"
                           name="teamTags" data-error="#teamTagsErr" required/>
                </div>
            </div>
            <div class="row data-row">
                <div class="col-md-4 col-md-offset-4 text-center">
                    <label for="score">Score <span id="scoreErr" class="error"></span></label>
                    <input id="score" name="score" class="form-control" type="text"
                           value="${match.score}" data-error="#scoreErr" required/>

                </div>
            </div>
            <div class="row data-row">
                <div class="col-md-12">
                    <input type="submit" value="Save" class="btn btn-success">
                </div>
            </div>
        </div>
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
    var usedMatchTags = [
        <c:forEach var="tag" items="${match.tags}">
        "${tag}",
        </c:forEach>
    ];
    var usedTeamTags = [
        <c:forEach var="tag" items="${team.tags}">
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
    $('#match').validate({
        messages: {
            matchTags: "(Please enter at least 1 tag)",
            teamTags: "(Please enter at least 1 tag)",
            score: "(Please enter a score)"
        },
        errorPlacement: function(error, element) {
            var placement = $(element).data('error');
            if (placement) {
                $(placement).append(error)
            } else {
                error.insertAfter(element);
            }
        }
    });
</script>
</body>
</html>
