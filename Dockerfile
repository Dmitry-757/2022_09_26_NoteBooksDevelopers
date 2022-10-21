FROM tomcat:10-jdk17
ADD target/NoteBooksDevelopers-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/NoteBooksDevelopers.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
#
#FROM mysql
#ENV MYSQL_ROOT_PASSWORD=mypassword
#EXPOSE 3306
#CMD ["mysqld"]
