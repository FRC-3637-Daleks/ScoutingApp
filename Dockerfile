FROM team3637/scoutingappbase

RUN git clone https://github.com/FRC-3637-Daleks/ScoutingApp.git /tomcat8/webapps/ScoutingApp

CMD service mysql start
CMD /tomcat8/bin/catalina.sh run
