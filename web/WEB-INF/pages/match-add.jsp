<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/includes.jsp" %>
    <link href="${pageContext.request.contextPath}/css/input-page.css" rel="stylesheet"/>
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
<div class="main container">
    <form:form method="POST" modelAttribute="match" action="${pageContext.request.contextPath}/m/add.jsp">
        <form:hidden path="matchNum" value="${matchNum}"/>
        <form:hidden path="team" value="${teamNum}"/>
        <table id="input" class="input table">
            <tr>
                <th colspan="2" class="large">Match Input</th>
            </tr>
            <tr>
                <td class="medium">Present</td>
                <td>
                    Robot was <b>not</b> present for match<input type="checkbox" name="showed" id="showed"/>
                </td>
            </tr>
            <tr>
                <td class="medium">Start Position</td>
                <td>
                    Left, middle, and right are in relation to the drivers of the team you are scouting<br/>
                    Robot starts at left yellow tote<form:checkbox path="startTL"/><br/>
                    Robot starts at middle yellow tote<form:checkbox path="startTM"/><br/>
                    Robot starts at right yellow tote<form:checkbox path="startTR"/><br/>
                    Robot starts at left side of landfill<form:checkbox path="startLL"/><br/>
                    Robot starts in middle of landfill<form:checkbox path="startLM"/><br/>
                    Robot starts at right side of landfill<form:checkbox path="startLR"/><br/>

                    <div id="startError" class="error"></div>
                </td>
            </tr>
            <tr>
                <td class="medium">Autonomous</td>
                <td>
                    Check all that apply<br/>
                    Robot moves forward during auton<form:checkbox path="autoRobot"/><br/>
                    Robot moves tote during auton<form:checkbox path="autoTote"/><br/>
                    Robot moves can during auton<form:checkbox path="autoCan"/>
                </td>
            </tr>
            <tr>
                <td class="medium">Play Style</td>
                <td>
                    Check all that apply<br/>
                    Robot works well at the human station<form:checkbox path="styleHS"/><br/>
                    Robot places cans on top of existing stacks<form:checkbox path="styleTopper"/><br/>
                    Robot puts litter in cans<form:checkbox path="styleLitterCan"/><br/>
                    Robot works well at landfill<form:checkbox path="styleLandfill"/><br/>
                    Human player throws litter<form:checkbox path="styleThrow"/><br/>
                    Robot picks up only one or two totes at a time to add to stack<form:checkbox
                        path="styleSingleStack"/><br/>
                    Robot makes entire stack in one shot<form:checkbox path="styleMakeAtOnce"/><br/>
                    Robot takes cans from the step<form:checkbox path="styleCanFromStep"/><br/>
                    Robot stopped working or did not work<form:checkbox path="dead"/>
                    <div id="styleError" class="error"></div>
                </td>
            </tr>
            <tr>
                <td class="medium">Cooperitition</td>
                <td>
                    Robot puts yellow tote on step<form:checkbox path="coopTote"/><br/>
                    Write number of totes robot puts on step
                    <form:input path="coopStep" style="height: 16px; width: 50px; background-color: white; text-align: center;" maxlength="3"/><!--<br/>
                        *if the robot puts no totes on the step type 0 or leave blank-->
                </td>
            </tr>
            <tr>
                <td class="medium">Problems</td>
                <td>
                    Check all that apply<br/>
                    Robot knocked a stack over<form:checkbox path="knockStack"/><br/>
                    Robot is unstable or wobbly<form:checkbox path="unstable"/><br/>
                    Robot tipped over<form:checkbox path="tip"/>
                </td>
            </tr>
            <tr>
                <td class="medium">Post Game</td>
                <td>
                    Comments<form:textarea path="comment" style="height: 30px; width: 400px; background-color: white"
                                           placeholder="Enter Comments Here" maxlength="255"/><br>
                    Score<form:input path="score"
                                     style="height: 16px; width: 50px; background-color: white; text-align: center;"
                                     maxlength="3"/>
                    <div id="scoreError" class="error"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit">
                </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
