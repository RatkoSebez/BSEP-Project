#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#COPY backend/target/proj-0.0.1-SNAPSHOT.jar ./
#CMD ["java", "-jar", "proj-0.0.1-SNAPSHOT.jar"]



#FROM maven:3.8.5-openjdk-18 AS appWebServerBuild
##WORKDIR /usr/src/server
#COPY backend .
#RUN mvn package -DskipTests
#
#
#FROM openjdk:18-jdk-alpine AS appWebServerRuntime
#WORKDIR /app
#COPY --from=appWebServerBuild /usr/src/server/target/servers.jar ./
#CMD java -jar servers.jar


FROM openjdk:17-jdk-alpine
ARG JAR_FILE=backend/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
