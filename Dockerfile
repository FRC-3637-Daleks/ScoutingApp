FROM team3637/scoutingappbase

MAINTAINER "Team 3637"

EXPOSE 22 80 8080

ENV CATALINA_HOME=/tomcat8
RUN mkdir /tmp/ScoutingApp
WORKDIR /tmp/ScoutingApp
RUN git clone https://github.com/FRC-3637-Daleks/ScoutingApp.git .
RUN ant
COPY /tmp/ScoutingApp/dist/ScoutingApp.war /tomcat8/webapps/ScoutingApp.war
WORKDIR /tomcat8
RUN rm -rf /tmp/ScoutingApp

CMD service mysql start
CMD /tomcat8/bin/catalina.sh run
