FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD cacerts.keystore /bin/cacerts.keystore
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]