FROM team3637/scoutingappbase

MAINTAINER "Team 3637"

EXPOSE 22 80

ENV CATALINA_HOME=/tomcat8
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
RUN mkdir /tmp/ScoutingApp
WORKDIR /tmp/ScoutingApp
RUN git clone https://github.com/FRC-3637-Daleks/ScoutingApp.git .
RUN ant
COPY dist/ScoutingApp.war /tomcat8/webapps/ScoutingApp.war
COPY docker-run.sh /tomcat8/run.sh
WORKDIR /tomcat8
RUN rm -rf /tmp/ScoutingApp

CMD run.sh
