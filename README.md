# Team 3637 ScoutingApp
A new scouting app for a new year

## How to install
#### With Docker Compose
 - Install [Docker](https://docs.docker.com/engine/installation/)
 - Install [Docker Compose](https://docs.docker.com/compose/install/)
 - Clone the repo `git clone https://github.com/FRC-3637-Daleks/ScoutingApp` and cd into it
 - Run the app `docker-compose up`
 
#### With Docker wo Compose
 - Install [Docker](https://docs.docker.com/engine/installation/)
 - Create a volume to store persistent data `docker volume create --name scoutingapp`
 - Create a network for them `docker network create --driver bridge scoutingapp`
 - Run MySQL `docker run -it -v scoutingapp:/var/lib/mysql --net=scoutingapp -e MYSQL_DATABASE=scoutingtags -e MYSQL_USER=team3637 -e MYSQL_PASSWORD=turing mysql`
 - Run the image `docker run -it -p 80:80 --net=scoutingapp team3637/scoutingapp`
 - Note swap `-it` for `-d` to run detached
 
#### Without Docker
##### Install Required Software
- Install [Apache Tomcat](https://tomcat.apache.org/download-80.cgi)
- Install [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

##### Setup Database
- Open command line and run `mysql -u root -p`
- Type the password you set during installation
- Enter the following commands
- `CREATE USER 'team3637'@'localhost' IDENTIFIED BY 'turing';`
- `create schema scoutingtags;`
- `grant all on scoutingtags.* to 'team3637'@'localhost';`
- `exit;`

##### Install Scouting App
- Build the app as specified below
- Copy `dist/ScoutingApp.war` into tomcat's `webapps` directory
- Start MySQL
- Start tomcat
- All done!
 
### Build
#### Build the Scouting App with <a href="https://ant.apache.org/">Apache Ant</a>
 - Install <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK 8</a>
 - Clone the repo `git clone https://github.com/FRC-3637-Daleks/ScoutingApp`
 - Cd into the directory `cd ScoutingApp`
 - Run ant `ant`
 - Also make sure to set the `CATALINA_HOME` enironment variable to the location tomcat installed.  This is need for building so ant can find the tomcat libraries. To this vairble permanity set I would recommed something like `echo "export CATALINA_HOME=/usr/share/tomcat7" > /etc/profile.d/tomcat.sh`.  Just correct to path to tomcat.