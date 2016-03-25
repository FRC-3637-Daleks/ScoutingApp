#!/bin/bash
service haproxy start
service mysql start
/tomcat8/bin/catalina.sh run