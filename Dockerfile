#FROM adoptopenjdk/openjdk11:alpine-slim
#
#WORKDIR /app
#
#COPY target/student-0.0.1-SNAPSHOT.jar /app/student-0.0.1-SNAPSHOT.jar
#
#CMD ["java", "-jar", "application.jar"]

# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]