# Team 3637 ScoutingApp
A new scouting app for a new year

## Build and deploy
### Build
#### Build the Scouting App with <a href="https://ant.apache.org/">Apache Ant</a>
 - Install <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK 8</a>
 - Clone the repo `git clone https://github.com/FRC-3637-Daleks/ScoutingApp`
 - Cd into the directory `cd ScoutingApp`
 - Run ant `ant`
 - Also make sure to set the `CATALINA_HOME` enironment variable to the location tomcat installed.  This is need for building so ant can find the tomcat libraries. To this vairble permanity set I would recommed something like `echo "export CATALINA_HOME=/usr/share/tomcat7" > /etc/profile.d/tomcat.sh`.  Just correct to path to tomcat.

### Deploy
#### With Docker
Pull the image `docker pull team3637/scoutingapp`

###### Or
Clone the repo as done above and cd into it
Build the image `docker build - t team3637/scoutingapp .`

###### Then
Run the image `docker run -it -p 80:80 team3637/scoutingapp`

#### Manually
##### Install Required Software
Install <a href="https://tomcat.apache.org/download-80.cgi">Apache Tomcat</a>
Install <a href="https://dev.mysql.com/downloads/mysql/">MySQL Community Server</a>

##### Setup Database
Open command line and run `mysql -u root -p`
Type the password you set during installation
Enter the following commands
 - `CREATE USER 'team3637'@'localhost' IDENTIFIED BY 'turing';`
 - `create schema scoutingtags;`
 - `grant all on scoutingtags.* to 'team3637'@'localhost';`
 - `exit;`

##### Install Scouting App
Build the app as specified above
Copy `dist/ScoutingApp.war` into tomcat's `webapps` directory
Start MySQL
Start tomcat
All done!
