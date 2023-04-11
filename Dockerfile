FROM tomcat:9.0-jre11
RUN rm -rf /usr/local/tomcat/webapps/*
COPY build/libs/servlets-project-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/servlets-project.war
