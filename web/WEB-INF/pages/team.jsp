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
                <li><a href="${pageContext.request.contextPath}/t/">Back</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container main">
<form:form id="team" method="POST" action="${pageContext.request.contextPath}/t/view/${team.team}">
    <div>
        <p class="h2">Team: ${team.team}</p>
    </div>
    <div id="input" class="input container">
        <div class="row data-row">
            <div class="col-md-6">
                <label for="matchTags">Match Tags</label>
                <select id="matchTags" class="chosen-select" multiple disabled>
                    <c:forEach var="tag" items="${matchTags}">
                        <option value="${tag}">${tag}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-6">
                <label for="teamTags">Team Tags <span id="teamTagsErr" class="error"></span></label>
                <select id="teamTags" class="chosen-select" name="teamTags"
                        data-error="#teamTagsErr" multiple required>
                    <c:forEach var="tag" items="${teamTags}">
                        <option value="${tag}">${tag}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="row data-row">
        <div class="col-md-4 col-md-offset-2 text-center">
            <label for="score">Average Score</label>
            <input id="score" name="score" class="form-control" type="text" value="${team.avgscore}" readonly/>
        </div>
        <div class="col-md-4 text-center">
            <label for="matches">Matches Played</label>
            <input id="matches" name="matches" class="form-control" type="text" value="${team.matches}" readonly/>
        </div>
    </div>
    <div class="row data-row">
        <div class="col-md-12">
            <input type="submit" value="Save" class="btn btn-success">
        </div>
    </div>
    </form:form>
</div>
<script>
    var teamTags = [
        <c:forEach var="tag" items="${teamTags}">
        "${tag}",
        </c:forEach>
    ];
    var usedMatchTags = [
        <c:forEach var="tag" items="${matchTags}">
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
    //Initalized the chosen widgets and set their options
    $('#matchTags').val(usedMatchTags).chosen({width: '100%'});
    $('#matchTags_chosen').removeClass('chosen-disabled');
    $('#teamTags').val(usedTeamTags).chosen({
        enable_split_word_search: false,
        max_selected_options: 50,
        no_results_text: 'No tags match',
        placeholder_text_multiple: 'Select some tags',
        single_backstroke_delete: false,
        width: '100%'
    });
</script>
<script>
    //Set up the data validator and set it's options
    $.validator.setDefaults({ ignore: ":hidden:not(.chosen-select)" });
    $('#team').validate({
        messages: {
            teamTags: "(Please enter at least 1 tag)"
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
