FROM tutum/tomcat:8.0
MAINTAINER "Team 3637"

ENV TOMCAT_PASS="turing"

WORKDIR /tomcat
COPY ScoutingApp.war webapps/ScoutingApp.war