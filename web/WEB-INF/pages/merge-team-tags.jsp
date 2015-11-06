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
    <form:form id="merge" method="post" action="/m/tags/mergeTeam">
        <div>
            <p class="h2">Merge Team Tags</p>
            <p id="error" class="error"></p>
        </div>
        <div id="input" class="input container">
            <div class="row data-row">
                <div class="col-md-6">
                    <label for="oldTag">Old Tag <span id="oldTagsErr" class="error"></span></label>
                    <input type="text" class="form-control" id="oldTag" name="oldTag"
                           data-limit="1" data-error="#oldTagsErr" required/>
                </div>
                <div class="col-md-6">
                    <label for="newTag">New Tags <span id="newTagsErr" class="error"></span></label>
                    <input type="text" class="form-control" id="newTag" name="newTag"
                           data-limit="1" data-error="#newTagsErr" required/>
                </div>
            </div>
            <div class="row data-row">
                <div class="col-md-12">
                    <input type="submit" value="Merge" class="btn btn-success">
                </div>
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
</script>
<script>
    $('#oldTag').tokenfield({
        autocomplete: {
            source: teamTags,
            delay: 100
        },
        showAutocompleteOnFocus: true
    });
    $('#newTag').tokenfield({
        autocomplete: {
            source: teamTags,
            delay: 100
        },
        showAutocompleteOnFocus: true
    });
    $('#merge').validate({
        rules: {
            score: {
                number: true
            }
        },
        messages: {
            matchTags: "(Please enter 1 tag)",
            teamTags: "(Please enter 1 tag)"
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
