<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="includes.jsp" %>
</head>
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
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
            </ul>
        </div>
    </div>
</nav>

<div class="container main">
    <h1>Team 3637 Scouting App</h1>

    <p class="lead">Click "Start Scouting" when you ready to begin scouting</p>
    <section>
        <button class="btn btn-primary" onclick="window.location = '${pageContext.request.contextPath}/s/'">Start Scouting</button>
    </section>
<%--    <section>
        <jsp:include page="WEB-INF/pages/test.jsp">
            <jsp:param name="message" value="Cake"/>
        </jsp:include>
    </section>--%>
<%--    <section>
        <hr/>
        <button class="btn btn-default" id="toTop">Back to top</button>
    </section>--%>
</div>
</body>
</html>