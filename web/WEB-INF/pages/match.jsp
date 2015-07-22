<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <link href="${pageContext.request.contextPath}/css/input-page.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/match.css" rel="stylesheet"/>
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
    <form:form method="POST" modelAttribute="match" action="${pageContext.request.contextPath}/m/add.jsp">
        <form:hidden path="matchNum" value="${matchNum}"/>
        <form:hidden path="team" value="${teamNum}"/>
        <form:hidden path="id" value="${match.id}"/>
        <div>
            <h2>Team: ${teamNum} - Match: ${matchNum}</h2>
        </div>
        <table id="input" class="input table">
            <tr>
                <td class="medium">Present</td>
                <td>
                    <table id="present" class="table table-striped">
                        <tr>
                            <td>Robot was <b>not</b> present for match</td>
                            <td><form:checkbox path="showed"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="medium">Start Position</td>
                <td>
                    <table id="start" class="table table-striped">
                        <tr>
                            <th colspan="2">Left, middle, and right are in relation to the drivers of the team you are
                                scouting
                            </th>
                        </tr>
                        <tr>
                            <td>Robot starts at left yellow tote</td>
                            <td><form:checkbox path="startTL"/></td>
                        </tr>
                        <tr>
                            <td>Robot starts at middle yellow tote</td>
                            <td><form:checkbox path="startTM"/></td>
                        </tr>
                        <tr>
                            <td>Robot starts at right yellow tote</td>
                            <td><form:checkbox path="startTR"/></td>
                        </tr>
                        <tr>
                            <td>Robot starts at left side of landfill</td>
                            <td><form:checkbox path="startLL"/></td>
                        </tr>
                        <tr>
                            <td>Robot starts in middle of landfill</td>
                            <td><form:checkbox path="startLM"/></td>
                        </tr>
                        <tr>
                            <td>Robot starts at right side of landfill</td>
                            <td><form:checkbox path="startLR"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="medium">Autonomous</td>
                <td>
                    <table id="auton" class="table table-striped">
                        <tr>
                            <th colspan="2">Check all that apply</th>
                        </tr>
                        <tr>
                            <td>Robot moves forward during auton</td>
                            <td><form:checkbox path="autoRobot"/></td>
                        </tr>
                        <tr>
                            <td>Robot moves tote during auton</td>
                            <td><form:checkbox path="autoTote"/><br/></td>
                        </tr>
                        <tr>
                            <td>Robot moves can during auton</td>
                            <td><form:checkbox path="autoCan"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="medium">Play Style</td>
                <td>
                    <table id="style" class="table table-striped">
                        <tr>
                            <th colspan="2">Check all that apply</th>
                        </tr>
                        <tr>
                            <td>Robot works well at the human station</td>
                            <td><form:checkbox path="styleHS"/></td>
                        </tr>
                        <tr>
                            <td>Robot places cans on top of existing stacks</td>
                            <td><form:checkbox path="styleTopper"/></td>
                        </tr>
                        <tr>
                            <td>Robot puts litter in cans</td>
                            <td><form:checkbox path="styleLitterCan"/></td>
                        </tr>
                        <tr>
                            <td>Robot works well at landfill</td>
                            <td><form:checkbox path="styleLandfill"/></td>
                        </tr>
                        <tr>
                            <td>Human player throws litter</td>
                            <td><form:checkbox path="styleThrow"/></td>
                        </tr>
                        <tr>
                            <td>Robot picks up only one or two totes at a time to add to stack</td>
                            <td><form:checkbox path="styleSingleStack"/></td>
                        </tr>
                        <tr>
                            <td>Robot makes entire stack in one shot</td>
                            <td><form:checkbox path="styleMakeAtOnce"/></td>
                        </tr>
                        <tr>
                            <td>Robot takes cans from the step</td>
                            <td><form:checkbox path="styleCanFromStep"/></td>
                        </tr>
                        <tr>
                            <td>Robot stopped working or did not work</td>
                            <td><form:checkbox path="dead"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="medium">Cooperitition</td>
                <td>
                    <table id="coop" class="table table-striped">
                        <tr>
                            <td>Robot puts yellow tote on step</td>
                            <td><form:checkbox path="coopTote"/></td>
                        </tr>
                        <tr>
                            <td>Write number of totes robot puts on step</td>
                            <td><form:input path="coopStep" maxlength="3" cssClass="form-control"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="medium">Problems</td>
                <td>
                    <table id="prob" class="table table-striped">
                        <tr>
                            <th colspan="2">Check all that apply</th>
                        </tr>
                        <tr>
                            <td>Robot knocked a stack over</td>
                            <td><form:checkbox path="knockStack"/><br/></td>
                        </tr>
                        <tr>
                            <td>Robot is unstable or wobbly</td>
                            <td><form:checkbox path="unstable"/><br/></td>
                        </tr>
                        <tr>
                            <td>Robot tipped over</td>
                            <td><form:checkbox path="tip"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="medium">Post Game</td>
                <td>
                    <table id="post" class="table table-striped">
                        <tr>
                            <td>Comments</td>
                            <td><form:textarea path="comment" placeholder="Enter Comments Here" maxlength="255" cssClass="form-control"/></td>
                        </tr>
                        <tr>
                            <td>Score</td>
                            <td><form:input path="score" maxlength="3" cssClass="form-control"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save" class="btn btn-success">
                </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
