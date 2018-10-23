FROM openjdk:8-jdk-alpine

VOLUME /tmp
EXPOSE 8080
ADD connect-four-challenge-server.jar connect-four-challenge-server.jar
ENTRYPOINT ["java","-jar","/connect-four-challenge-server.jar"]
