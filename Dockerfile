FROM tomcat:8.0
MAINTAINER "Team 3637"

RUN apt-get install ant

ADD . /scBuild
WORKDIR /scBuild

RUN ant