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
                <li><a href="${pageContext.request.contextPath}/t/">Back</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="main container">
    <form:form method="POST" id="search" action="${pageContext.request.contextPath}/t/search/">
        <div>
            <p class="h2">Search</p>
            <p id="error" class="error"></p>
        </div>
        <div id="input" class="input container">
            <div class="row data-row">
                <div class="col-md-6">
                    <label for="matchTags">Match Tags</label>
                    <input type="text" class="form-control" id="matchTags"
                           name="matchTags" data-error="#error"/>
                </div>
                <div class="col-md-6">
                    <label for="teamTags">Team Tags <span id="teamTagsErr" class="error"></span></label>
                    <input type="text" class="form-control" id="teamTags"
                           name="teamTags" data-error="#error"/>
                </div>
            </div>
            <div class="row data-row">
                <div class="col-md-4 col-md-offset-4 text-center">
                    <p class="text-center" style="font-weight: 700;">Score <span id="scoreErr" class="error"></span></p>
                    <input id="score" name="score" class="hidden" type="text" data-slider-min="${minScore}"
                           data-slider-max="${maxScore}" data-slider-setp="1" data-slider-value="[${minScore},
                           ${maxScore}]" data-error="#scoreErr" required title="score"/>
                </div>
            </div>
            <div class="row data-row">
                <div class="col-md-12">
                    <input type="submit" value="Search" class="btn btn-success">
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
</script>
<script>
    $('#matchTags').tokenfield({
        autocomplete: {
            source: matchTags,
            delay: 100
        },
        showAutocompleteOnFocus: true
    });
    $('#teamTags').tokenfield({
        autocomplete: {
            source: teamTags,
            delay: 100
        },
        showAutocompleteOnFocus: true
    });
    /*$('#search').validate({
        rules: {
            matchTags: {
                required: function(element) {
                    return $("#teamTags").tokenfield('getTokensList') == "";
                }
            },
            teamTags: {
                required: function(element) {
                    return $("#matchTags").tokenfield('getTokensList') == "";
                }
            }
        },
        messages: {
            matchTags: "(Please enter at least 1 tag)",
            teamTags: "(Please enter at least 1 tag)"
        },
        errorPlacement: function (error, element) {
            var placement = $(element).data('error');
            if (placement) {
                $(placement).html(error)
            } else {
                error.insertAfter(element);
            }
        }
    });*/
    var scoreSlider = $('#score').bootstrapSlider();
</script>
</body>
</html>
