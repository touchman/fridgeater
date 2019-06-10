FROM openjdk:8-jre-alpine
MAINTAINER java@gpsolutions.com
COPY target/fridgeater-backend-0.0.1-SNAPSHOT.jar /opt/backend-service.jar
RUN mkdir /opt/dir
ENV SPRING_PROFILES=prod
CMD exec java -jar /opt/backend-service.jar --spring.profiles.active=$SPRING_PROFILES