#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#COPY backend/target/proj-0.0.1-SNAPSHOT.jar ./
#CMD ["java", "-jar", "proj-0.0.1-SNAPSHOT.jar"]



FROM maven:3.6.3-ibmjava-8-alpine AS appWebServerBuild
WORKDIR /usr/src/server
COPY backend .
RUN mvn package -DskipTests


FROM openjdk:8-jdk-alpine AS appWebServerRuntime
WORKDIR /app
COPY --from=appWebServerBuild /usr/src/server/target/servers.jar ./
CMD java -jar servers.jar

